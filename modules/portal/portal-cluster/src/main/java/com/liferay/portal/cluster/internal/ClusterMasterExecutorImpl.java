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

package com.liferay.portal.cluster.internal;

import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.cluster.ClusterMasterTokenTransitionListener;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterNodeResponses;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.concurrent.NoticeableFutureConverter;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Release;

import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = ClusterMasterExecutor.class)
public class ClusterMasterExecutorImpl implements ClusterMasterExecutor {

	@Override
	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	public void addClusterMasterTokenTransitionListener(
		ClusterMasterTokenTransitionListener
			clusterMasterTokenTransitionListener) {

		_clusterMasterTokenTransitionListeners.add(
			clusterMasterTokenTransitionListener);
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
				new DefaultNoticeableFuture<>();

			try {
				defaultNoticeableFuture.set((T)methodHandler.invoke());

				return defaultNoticeableFuture;
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
		}

		final String masterClusterNodeId = getMasterClusterNodeId(true);

		ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
			methodHandler, masterClusterNodeId);

		try {
			return new NoticeableFutureConverter<T, ClusterNodeResponses>(
				_clusterExecutor.execute(clusterRequest)) {

					@Override
					protected T convert(
							ClusterNodeResponses clusterNodeResponses)
						throws Exception {

						ClusterNodeResponse clusterNodeResponse =
							clusterNodeResponses.getClusterResponse(
								masterClusterNodeId);

						return (T)clusterNodeResponse.getResult();
					}

				};
		}
		catch (Exception e) {
			throw new SystemException(
				"Unable to execute on master " + masterClusterNodeId, e);
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
	public void removeClusterMasterTokenTransitionListener(
		ClusterMasterTokenTransitionListener
			clusterMasterTokenTransitionListener) {

		_clusterMasterTokenTransitionListeners.remove(
			clusterMasterTokenTransitionListener);
	}

	@Activate
	protected synchronized void activate() {
		if (!_clusterExecutor.isEnabled() || SPIUtil.isSPI()) {
			return;
		}

		_clusterEventListener = new ClusterMasterTokenClusterEventListener();

		_clusterExecutor.addClusterEventListener(_clusterEventListener);

		ClusterNode localClusterNode = _clusterExecutor.getLocalClusterNode();

		_localClusterNodeId = localClusterNode.getClusterNodeId();

		_enabled = true;

		getMasterClusterNodeId(false);
	}

	@Deactivate
	protected void deactivate() {
		if (_clusterEventListener != null) {
			try {
				_clusterExecutor.removeClusterEventListener(
					_clusterEventListener);

				_lockManager.unlock(
					_LOCK_CLASS_NAME, _LOCK_CLASS_NAME, _localClusterNodeId);
			}
			catch (SystemException se) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to destroy the cluster master executor", se);
				}
			}
		}

		_clusterEventListener = null;
		_enabled = false;
		_localClusterNodeId = null;
	}

	protected String getMasterClusterNodeId(boolean notify) {
		String owner = null;

		while (true) {
			try {
				Lock lock = null;

				if (owner == null) {
					lock = _lockManager.lock(
						_LOCK_CLASS_NAME, _LOCK_CLASS_NAME,
						_localClusterNodeId);
				}
				else {
					lock = _lockManager.lock(
						_LOCK_CLASS_NAME, _LOCK_CLASS_NAME, owner,
						_localClusterNodeId);
				}

				owner = lock.getOwner();

				if (_clusterExecutor.isClusterNodeAlive(owner)) {
					break;
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to acquire the cluster master lock", e);
				}
			}

			if (_log.isInfoEnabled()) {
				if (Validator.isNotNull(owner)) {
					_log.info("Lock currently held by " + owner);
				}

				_log.info("Reattempting to acquire the cluster master lock");
			}
		}

		boolean master = _localClusterNodeId.equals(owner);

		if (master == _master) {
			return owner;
		}

		_master = master;

		if (_enabled && notify) {
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

	@Reference(unbind = "-")
	protected void setClusterExecutor(ClusterExecutor clusterExecutor) {
		_clusterExecutor = clusterExecutor;
	}

	protected void setClusterMasterTokenTransitionListeners(
		Set<ClusterMasterTokenTransitionListener>
			clusterMasterTokenTransitionListeners) {

		_clusterMasterTokenTransitionListeners.addAll(
			clusterMasterTokenTransitionListeners);
	}

	@Reference(unbind = "-")
	protected void setLockManager(LockManager lockManager) {
		_lockManager = lockManager;
	}

	@Reference(target = "(servlet.context.name=portal)", unbind = "-")
	protected void setRelease(Release release) {
	}

	private static final String _LOCK_CLASS_NAME =
		ClusterMasterExecutorImpl.class.getName();

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterMasterExecutorImpl.class);

	private static volatile boolean _master;

	private ClusterEventListener _clusterEventListener;
	private volatile ClusterExecutor _clusterExecutor;
	private final Set<ClusterMasterTokenTransitionListener>
		_clusterMasterTokenTransitionListeners = new HashSet<>();
	private boolean _enabled;
	private volatile String _localClusterNodeId;
	private volatile LockManager _lockManager;

	private class ClusterMasterTokenClusterEventListener
		implements ClusterEventListener {

		@Override
		public void processClusterEvent(ClusterEvent clusterEvent) {
			getMasterClusterNodeId(true);
		}

	}

}