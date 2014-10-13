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

package com.liferay.portal.nio.intraband.proxy;

import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.rpc.IntrabandRPCUtil;
import com.liferay.portal.kernel.process.ProcessCallable;

import java.util.concurrent.Future;

/**
 * @author Shuyang Zhou
 */
public class StubHolder<T> {

	public StubHolder(
		T originalT, String stubId, RegistrationReference registrationReference,
		StubCreator<T> stubCreator) {

		_originalT = originalT;
		_stubId = stubId;
		_registrationReference = registrationReference;
		_stubCreator = stubCreator;
	}

	public T getStub() {
		if (_stub != null) {
			return doGetStub();
		}

		synchronized (_registrationReference) {
			if (_stub != null) {
				return doGetStub();
			}

			Future<Boolean> future = IntrabandRPCUtil.execute(
				_registrationReference, _startupFinishedProcessCallable);

			boolean startupFinished = false;

			try {
				startupFinished = future.get();
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to detect SPI's startup status", e);
				}
			}

			if (!startupFinished) {
				return _originalT;
			}

			try {
				_stub = _stubCreator.createStub(
					_stubId, _originalT, _registrationReference);

				return _stub;
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to create stub", e);
				}

				return _stubCreator.onCreationFailure(_stubId, _originalT, e);
			}
		}
	}

	public interface StubCreator<T> {

		public T createStub(
				String stubId, T originalT,
				RegistrationReference registrationReference)
			throws Exception;

		public T onCreationFailure(String stubId, T originalT, Exception e);

		public T onInvalidation(String stubId, T originalT, T stub);

	}

	protected T doGetStub() {
		if (_registrationReference.isValid()) {
			return _stub;
		}

		return _stubCreator.onInvalidation(_stubId, _stub, _originalT);
	}

	private static final Log _log = LogFactoryUtil.getLog(StubHolder.class);

	private static final ProcessCallable<Boolean>
		_startupFinishedProcessCallable = new StartupFinishedProcessCallable();

	private final T _originalT;
	private final RegistrationReference _registrationReference;
	private volatile T _stub;
	private final StubCreator<T> _stubCreator;
	private final String _stubId;

	private static class StartupFinishedProcessCallable
		implements ProcessCallable<Boolean> {

		@Override
		public Boolean call() {
			return StartupHelperUtil.isStartupFinished();
		}

		private static final long serialVersionUID = 1L;

	}

}