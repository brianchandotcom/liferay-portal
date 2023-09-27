/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.multiple.internal.cluster.link;

import com.liferay.portal.cache.multiple.internal.PortalCacheClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.util.SerializableUtil;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Shuyang Zhou
 */
public class ClusterLinkPortalCacheClusterChannel
	implements PortalCacheClusterChannel, Runnable {

	public ClusterLinkPortalCacheClusterChannel(
		ClusterLink clusterLink, String destinationName,
		PortalCacheClusterEventQueue portalCacheClusterEventQueue,
		Priority priority) {

		_clusterLink = clusterLink;
		_destinationName = destinationName;
		_portalCacheClusterEventQueue = portalCacheClusterEventQueue;
		_priority = priority;

		_dispatchThread = new Thread(
			this,
			"PortalCacheClusterChannel dispatch thread-" +
				_dispatchThreadCounter.getAndIncrement());
	}

	@Override
	public void destroy() {
		_destroy = true;

		_dispatchThread.interrupt();
	}

	public void dispatchEvent(PortalCacheClusterEvent portalCacheClusterEvent) {
		Message message = new Message();

		message.setDestinationName(_destinationName);
		message.setPayload(SerializableUtil.serialize(portalCacheClusterEvent));

		_clusterLink.sendMulticastMessage(message, _priority);
	}

	@Override
	public long getCoalescedEventNumber() {
		return _portalCacheClusterEventQueue.coalescedCount();
	}

	@Override
	public int getPendingEventNumber() {
		return _portalCacheClusterEventQueue.pendingCount();
	}

	@Override
	public long getSentEventNumber() {
		return _sentEventCounter.get();
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (_destroy) {
					for (PortalCacheClusterEvent event :
							_portalCacheClusterEventQueue.takeSnapshot()) {

						dispatchEvent(event);

						_sentEventCounter.incrementAndGet();
					}

					break;
				}

				try {
					PortalCacheClusterEvent portalCacheClusterEvent =
						_portalCacheClusterEventQueue.take();

					dispatchEvent(portalCacheClusterEvent);

					_sentEventCounter.incrementAndGet();
				}
				catch (InterruptedException interruptedException) {
					if (_log.isDebugEnabled()) {
						_log.debug(interruptedException);
					}
				}
			}
			catch (Throwable throwable) {
				if (_log.isWarnEnabled()) {
					_log.warn("Please fix the unexpected throwable", throwable);
				}
			}
		}
	}

	@Override
	public void sendEvent(PortalCacheClusterEvent portalCacheClusterEvent) {
		if (!_started) {
			synchronized (this) {
				if (!_started) {
					_dispatchThread.start();

					_started = true;
				}
			}
		}

		if (_destroy) {
			dispatchEvent(portalCacheClusterEvent);
		}
		else {
			try {
				_portalCacheClusterEventQueue.put(portalCacheClusterEvent);
			}
			catch (InterruptedException interruptedException) {
				if (_log.isDebugEnabled()) {
					_log.debug(interruptedException);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterLinkPortalCacheClusterChannel.class);

	private static final AtomicInteger _dispatchThreadCounter =
		new AtomicInteger(0);

	private final ClusterLink _clusterLink;
	private final String _destinationName;
	private volatile boolean _destroy;
	private final Thread _dispatchThread;
	private final PortalCacheClusterEventQueue _portalCacheClusterEventQueue;
	private final Priority _priority;
	private final AtomicLong _sentEventCounter = new AtomicLong(0);
	private volatile boolean _started;

}