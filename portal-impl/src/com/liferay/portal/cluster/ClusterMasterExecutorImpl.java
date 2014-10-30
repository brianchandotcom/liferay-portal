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

package com.liferay.portal.cluster;

import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.AddressSerializerUtil;
import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.cluster.ClusterMasterTokenTransitionListener;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterNodeResponses;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.concurrent.NoticeableFutureConverter;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Lock;
import com.liferay.portal.service.LockLocalServiceUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class ClusterMasterExecutorImpl implements ClusterMasterExecutor {

	public void destroy() {
		if (!_enabled) {
			return;
		}

		try {
			_clusterExecutor.removeClusterEventListener(_clusterEventListener);

			LockLocalServiceUtil.unlock(
				_LOCK_CLASS_NAME, _LOCK_CLASS_NAME, _localClusterNodeAddress);
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to destroy the cluster master executor", se);
			}
		}
	}

	@Override
	public <T> NoticeableFuture<T> executeOnMaster(
		MethodHandler methodHandler) {

		if (!_enabled) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Executing on the local node because the cluster master " +
						"executor is disabled");
			}

			DefaultNoticeableFuture<T> defaultNoticeableFuture =
				new DefaultNoticeableFuture<T>();

			try {
				defaultNoticeableFuture.set((T)methodHandler.invoke());

				return defaultNoticeableFuture;
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
		}

		String masterAddressString = getMasterAddressString();

		final Address address = AddressSerializerUtil.deserialize(
			masterAddressString);

		ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
			methodHandler, address);

		try {
			return new NoticeableFutureConverter<T, ClusterNodeResponses>(
				_clusterExecutor.execute(clusterRequest)) {

					@Override
					protected T convert(
							ClusterNodeResponses clusterNodeResponses)
						throws Exception {

						ClusterNodeResponse clusterNodeResponse =
							clusterNodeResponses.getClusterResponse(address);

						return (T)clusterNodeResponse.getResult();
					}

				};
		}
		catch (Exception e) {
			throw new SystemException(
				"Unable to execute on master " + address.getDescription(), e);
		}
	}

	@Override
	public void initialize() {
		if (!_clusterExecutor.isEnabled()) {
			return;
		}

		try {
			_localClusterNodeAddress = AddressSerializerUtil.serialize(
				_clusterExecutor.getLocalClusterNodeAddress());

			_clusterEventListener =
				new ClusterMasterTokenClusterEventListener();

			_clusterExecutor.addClusterEventListener(_clusterEventListener);

			String masterAddressString = getMasterAddressString();

			_enabled = true;

			notifyMasterTokenTransitionListeners(
				_localClusterNodeAddress.equals(masterAddressString));
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to initialize cluster master executor", e);
		}
	}

	@Override
	public boolean isEnabled() {
		return _enabled;
	}

	@Override
	public boolean isMaster() {
		if (isEnabled()) {
			return _master;
		}

		return true;
	}

	@Override
	public void registerClusterMasterTokenTransitionListener(
		ClusterMasterTokenTransitionListener
			clusterMasterTokenTransitionListener) {

		_clusterMasterTokenTransitionListeners.add(
			clusterMasterTokenTransitionListener);
	}

	public void setClusterExecutor(ClusterExecutor clusterExecutor) {
		_clusterExecutor = clusterExecutor;
	}

	public void setClusterMasterTokenTransitionListeners(
		Set<ClusterMasterTokenTransitionListener>
			clusterMasterTokenTransitionListeners) {

		_clusterMasterTokenTransitionListeners.addAll(
			clusterMasterTokenTransitionListeners);
	}

	@Override
	public void unregisterClusterMasterTokenTransitionListener(
		ClusterMasterTokenTransitionListener
			clusterMasterTokenTransitionListener) {

		_clusterMasterTokenTransitionListeners.remove(
			clusterMasterTokenTransitionListener);
	}

	protected String getMasterAddressString() {
		String owner = null;

		while (true) {
			try {
				Lock lock = null;

				if (owner == null) {
					lock = LockLocalServiceUtil.lock(
						_LOCK_CLASS_NAME, _LOCK_CLASS_NAME,
						_localClusterNodeAddress);
				}
				else {
					lock = LockLocalServiceUtil.lock(
						_LOCK_CLASS_NAME, _LOCK_CLASS_NAME, owner,
						_localClusterNodeAddress);
				}

				owner = lock.getOwner();

				Address address = AddressSerializerUtil.deserialize(owner);

				if (_clusterExecutor.isClusterNodeAlive(address)) {
					break;
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to acquire memory scheduler cluster lock", e);
				}
			}

			if (_log.isInfoEnabled()) {
				if (Validator.isNotNull(owner)) {
					_log.info("Lock currently held by " + owner);
				}

				_log.info(
					"Reattempting to acquire memory scheduler cluster lock");
			}
		}

		boolean master = _localClusterNodeAddress.equals(owner);

		if (master == _master) {
			return owner;
		}

		_master = master;

		if (_enabled) {
			notifyMasterTokenTransitionListeners(master);
		}

		return owner;
	}

	protected void notifyMasterTokenTransitionListeners(
		boolean masterTokenAcquired) {

		for (ClusterMasterTokenTransitionListener
				clusterMasterTokenTransitionListener :
					_clusterMasterTokenTransitionListeners) {

			if (masterTokenAcquired) {
				clusterMasterTokenTransitionListener.masterTokenAcquired();
			}
			else {
				clusterMasterTokenTransitionListener.masterTokenReleased();
			}
		}
	}

	private static final String _LOCK_CLASS_NAME =
		ClusterMasterExecutorImpl.class.getName();

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterMasterExecutorImpl.class);

	private static volatile boolean _master;

	private ClusterEventListener _clusterEventListener;
	private ClusterExecutor _clusterExecutor;
	private final Set<ClusterMasterTokenTransitionListener>
		_clusterMasterTokenTransitionListeners =
			new HashSet<ClusterMasterTokenTransitionListener>();
	private volatile boolean _enabled;
	private volatile String _localClusterNodeAddress;

	private class ClusterMasterTokenClusterEventListener
		implements ClusterEventListener {

		@Override
		public void processClusterEvent(ClusterEvent clusterEvent) {
			try {
				getMasterAddressString();
			}
			catch (Exception e) {
				_log.error("Unable to update the cluster master lock", e);
			}
		}
	}

}