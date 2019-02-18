/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.oauth2.provider.rest.internal.endpoint.liferay;

import com.liferay.portal.kernel.cluster.ClusterMasterExecutorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.cxf.rs.security.oauth2.common.Client;
import org.apache.cxf.rs.security.oauth2.common.UserSubject;
import org.apache.cxf.rs.security.oauth2.grants.code.ServerAuthorizationCodeGrant;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	property = "timeout:Integer=15", service = CodeGrantsClusterSupport.class
)
public class CodeGrantsClusterSupport {

	public ServerAuthorizationCodeGrant getCodeGrant(String code) {
		if (!ClusterMasterExecutorUtil.isEnabled() ||
			ClusterMasterExecutorUtil.isMaster()) {

			return _getCodeGrant(code);
		}

		Future<ServerAuthorizationCodeGrant> future =
			ClusterMasterExecutorUtil.executeOnMaster(
				new MethodHandler(_getCodeGrantMethodKey, code));

		try {
			return future.get(_timeout, TimeUnit.SECONDS);
		}
		catch (Exception e) {
			_log.error("Timeout getting code grant from master node");

			return null;
		}
	}

	public List<ServerAuthorizationCodeGrant> getCodeGrants(
		Client client, UserSubject userSubject) {

		if (!ClusterMasterExecutorUtil.isEnabled() ||
			ClusterMasterExecutorUtil.isMaster()) {

			return _getCodeGrants(client, userSubject);
		}

		Future<List<ServerAuthorizationCodeGrant>> future =
			ClusterMasterExecutorUtil.executeOnMaster(
				new MethodHandler(
					_getCodeGrantsMethodKey, client, userSubject));

		try {
			return future.get(_timeout, TimeUnit.SECONDS);
		}
		catch (Exception e) {
			_log.error("Timeout getting code grants from master node");

			return Collections.emptyList();
		}
	}

	public void putCodeGrant(
		ServerAuthorizationCodeGrant serverAuthorizationCodeGrant) {

		if (!ClusterMasterExecutorUtil.isEnabled() ||
			ClusterMasterExecutorUtil.isMaster()) {

			_putCodeGrant(serverAuthorizationCodeGrant);
		}
		else {
			Future<?> future = ClusterMasterExecutorUtil.executeOnMaster(
				new MethodHandler(
					_putGrantMethodKey, serverAuthorizationCodeGrant));

			try {
				future.get(_timeout, TimeUnit.SECONDS);
			}
			catch (Exception e) {
				_log.error("Timeout setting code grant to master node");
			}
		}
	}

	public ServerAuthorizationCodeGrant removeCodeGrant(String code) {
		if (!ClusterMasterExecutorUtil.isEnabled() ||
			ClusterMasterExecutorUtil.isMaster()) {

			return _removeCodeGrant(code);
		}

		Future<ServerAuthorizationCodeGrant> future =
			ClusterMasterExecutorUtil.executeOnMaster(
				new MethodHandler(_removeGrantMethodKey, code));

		try {
			return future.get(_timeout, TimeUnit.SECONDS);
		}
		catch (Exception e) {
			_log.error("Timeout removing code grant from master node");

			return null;
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_timeout = MapUtil.getInteger(properties, "timeout");
	}

	private static void _cleanup() {
		while (_serverAuthorizationCodeGrantDelayeds.poll() != null);
	}

	private static ServerAuthorizationCodeGrant _getCodeGrant(String code) {
		_cleanup();

		for (ServerAuthorizationCodeGrantDelayed
				serverAuthorizationCodeGrantDelayed :
					_serverAuthorizationCodeGrantDelayeds) {

			ServerAuthorizationCodeGrant serverAuthorizationCodeGrant =
				serverAuthorizationCodeGrantDelayed.
					getServerAuthorizationCodeGrant();

			if (code.equals(serverAuthorizationCodeGrant.getCode())) {
				return serverAuthorizationCodeGrant;
			}
		}

		return null;
	}

	private static List<ServerAuthorizationCodeGrant> _getCodeGrants(
		Client client, UserSubject userSubject) {

		_cleanup();

		List<ServerAuthorizationCodeGrant> serverAuthorizationCodeGrants =
			new ArrayList<>();

		for (ServerAuthorizationCodeGrantDelayed
				serverAuthorizationCodeGrantDelayed :
					_serverAuthorizationCodeGrantDelayeds) {

			ServerAuthorizationCodeGrant serverAuthorizationCodeGrant =
				serverAuthorizationCodeGrantDelayed.
					getServerAuthorizationCodeGrant();

			if (client.equals(serverAuthorizationCodeGrant.getClient()) &&
				userSubject.equals(serverAuthorizationCodeGrant.getSubject())) {

				serverAuthorizationCodeGrants.add(serverAuthorizationCodeGrant);
			}
		}

		return serverAuthorizationCodeGrants;
	}

	private static void _putCodeGrant(
		ServerAuthorizationCodeGrant serverAuthorizationCodeGrant) {

		_cleanup();

		_serverAuthorizationCodeGrantDelayeds.add(
			new ServerAuthorizationCodeGrantDelayed(
				serverAuthorizationCodeGrant));
	}

	private static ServerAuthorizationCodeGrant _removeCodeGrant(String code) {
		_cleanup();

		AtomicReference<ServerAuthorizationCodeGrant>
			serverAuthorizationCodeGrantAtomicReference =
				new AtomicReference<>();

		_serverAuthorizationCodeGrantDelayeds.removeIf(
			serverAuthorizationCodeGrantDelayed -> {
				ServerAuthorizationCodeGrant serverAuthorizationCodeGrant =
					serverAuthorizationCodeGrantDelayed.
						getServerAuthorizationCodeGrant();

				if (code.equals(serverAuthorizationCodeGrant.getCode())) {
					serverAuthorizationCodeGrantAtomicReference.compareAndSet(
						null, serverAuthorizationCodeGrant);

					return true;
				}

				return false;
			});

		return serverAuthorizationCodeGrantAtomicReference.get();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CodeGrantsClusterSupport.class);

	private static final MethodKey _getCodeGrantMethodKey = new MethodKey(
		CodeGrantsClusterSupport.class, "_getCodeGrant", String.class);
	private static final MethodKey _getCodeGrantsMethodKey = new MethodKey(
		CodeGrantsClusterSupport.class, "_getCodeGrants", String.class);
	private static final MethodKey _putGrantMethodKey = new MethodKey(
		CodeGrantsClusterSupport.class, "_putCodeGrant",
		ServerAuthorizationCodeGrant.class);
	private static final MethodKey _removeGrantMethodKey = new MethodKey(
		CodeGrantsClusterSupport.class, "_removeCodeGrant", String.class);
	private static final DelayQueue<ServerAuthorizationCodeGrantDelayed>
		_serverAuthorizationCodeGrantDelayeds = new DelayQueue<>();

	private int _timeout;

	private static class ServerAuthorizationCodeGrantDelayed
		implements Delayed {

		public ServerAuthorizationCodeGrantDelayed(
			ServerAuthorizationCodeGrant serverAuthorizationCodeGrant) {

			_serverAuthorizationCodeGrant = serverAuthorizationCodeGrant;
		}

		@Override
		public int compareTo(Delayed delayed) {
			return _comparator.compare(
				this, (ServerAuthorizationCodeGrantDelayed)delayed);
		}

		@Override
		public long getDelay(TimeUnit unit) {
			long expirationTime = TimeUnit.MILLISECONDS.convert(
				_getExpirationTime(), TimeUnit.SECONDS);

			return unit.convert(
				expirationTime - System.currentTimeMillis(),
				TimeUnit.MILLISECONDS);
		}

		public ServerAuthorizationCodeGrant getServerAuthorizationCodeGrant() {
			return _serverAuthorizationCodeGrant;
		}

		private long _getExpirationTime() {
			return _serverAuthorizationCodeGrant.getIssuedAt() +
				_serverAuthorizationCodeGrant.getExpiresIn();
		}

		private static final Comparator<ServerAuthorizationCodeGrantDelayed>
			_comparator = Comparator.comparing(
				ServerAuthorizationCodeGrantDelayed::_getExpirationTime);

		private final ServerAuthorizationCodeGrant
			_serverAuthorizationCodeGrant;

	}

}