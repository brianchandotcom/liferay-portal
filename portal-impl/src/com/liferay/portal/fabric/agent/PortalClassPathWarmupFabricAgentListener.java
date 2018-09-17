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

package com.liferay.portal.fabric.agent;

import com.liferay.petra.concurrent.BaseFutureListener;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessException;
import com.liferay.portal.fabric.worker.FabricWorker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PortalClassPathUtil;

import java.io.Serializable;

import java.util.concurrent.Future;

/**
 * @author Shuyang Zhou
 */
public class PortalClassPathWarmupFabricAgentListener
	implements FabricAgentListener {

	@Override
	public void registered(FabricAgent fabricAgent) {
		try {
			long startTime = System.currentTimeMillis();

			FabricWorker<Serializable> fabricWorker = fabricAgent.execute(
				PortalClassPathUtil.getPortalProcessConfig(),
				_warmupProcessCallable);

			NoticeableFuture<Serializable> noticeableFuture =
				fabricWorker.getProcessNoticeableFuture();

			noticeableFuture.addFutureListener(
				new FinishFutureListener(startTime));
		}
		catch (ProcessException pe) {
			_log.error(
				"Unable to start portal class path warmup fabric worker", pe);
		}
	}

	@Override
	public void unregistered(FabricAgent fabricAgent) {
	}

	protected class FinishFutureListener
		extends BaseFutureListener<Serializable> {

		public FinishFutureListener(long startTime) {
			_startTime = startTime;
		}

		@Override
		public void completeWithCancel(Future<Serializable> future) {
			_log.error("Portal class path warmup cancelled");
		}

		@Override
		public void completeWithException(
			Future<Serializable> future, Throwable throwable) {

			_log.error("Portal class path warmup failed", throwable);
		}

		@Override
		public void completeWithResult(
			Future<Serializable> future, Serializable result) {

			if (_log.isInfoEnabled()) {
				_log.info(
					"Portal class path warmup finished successfully in " +
						(System.currentTimeMillis() - _startTime) + "ms");
			}
		}

		private final long _startTime;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalClassPathWarmupFabricAgentListener.class);

	private static final ProcessCallable<Serializable> _warmupProcessCallable =
		new ProcessCallable<Serializable>() {

			@Override
			public String call() {
				if (_log.isInfoEnabled()) {
					_log.info("Portal class path warmup successful");
				}

				return null;
			}

			private static final long serialVersionUID = 1L;

		};

}