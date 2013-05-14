/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.poller.comet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.util.PropsValues;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Edward Han
 */
public class PollerCometDelayedJobImpl
	extends BaseMessageListener implements PollerCometDelayedJob {

	@Override
	public void addPollerCometDelayedTask(
		PollerCometDelayedTask pollerCometDelayedTask) {

		synchronized (_pollerCometDelayedTasks) {
			if (_timer == null) {
				_timer = new Timer(PollerCometDelayedJobImpl.class.getName());

				_timer.schedule(
					new PollerCometTimerTask(),
					PropsValues.POLLER_NOTIFICATIONS_TIMEOUT);
			}

			_pollerCometDelayedTasks.add(pollerCometDelayedTask);
		}
	}

	@Override
	protected synchronized void doReceive(Message message) throws Exception {
		synchronized (_pollerCometDelayedTasks) {
			for (PollerCometDelayedTask pollerCometDelayedTask :
					_pollerCometDelayedTasks) {

				try {
					pollerCometDelayedTask.executeTask();
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to do task" + e);
					}
				}
			}

			_pollerCometDelayedTasks.clear();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PollerCometDelayedJob.class);

	private List<PollerCometDelayedTask> _pollerCometDelayedTasks =
		new LinkedList<PollerCometDelayedTask>();
	private Timer _timer;

	private class PollerCometTimerTask extends TimerTask {

		@Override
		public void run() {
			synchronized (_pollerCometDelayedTasks) {
				for (PollerCometDelayedTask pollerCometDelayedTask :
						_pollerCometDelayedTasks) {

					try {
						pollerCometDelayedTask.executeTask();
					}
					catch (Exception e) {
						if (_log.isWarnEnabled()) {
							_log.warn("Unable to do task" + e);
						}
					}
				}

				_pollerCometDelayedTasks.clear();
			}
		}
	}

}