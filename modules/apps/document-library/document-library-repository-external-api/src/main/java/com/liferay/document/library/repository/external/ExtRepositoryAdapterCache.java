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

package com.liferay.document.library.repository.external;

import com.liferay.document.library.repository.external.model.ExtRepositoryModelAdapter;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Iván Zaera
 */
public class ExtRepositoryAdapterCache implements Cloneable {

	public static ExtRepositoryAdapterCache getInstance() {
		return _extRepositoryAdapterThreadLocal.get();
	}

	public void clear() {
		_extRepositoryAdapters.clear();
	}

	@Override
	public ExtRepositoryAdapterCache clone() {
		if (_log.isInfoEnabled()) {
			Thread currentThread = Thread.currentThread();

			_log.info("Create " + currentThread.getName());
		}

		try {
			return (ExtRepositoryAdapterCache)super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			throw new RuntimeException(cnse);
		}
	}

	public <T extends ExtRepositoryModelAdapter<?>> T get(
		String extRepositoryModelKey) {

		T extRepositoryAdapter = (T)_extRepositoryAdapters.get(
			extRepositoryModelKey);

		if (extRepositoryAdapter != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Hit " + extRepositoryModelKey);
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Miss " + extRepositoryModelKey);
			}
		}

		return extRepositoryAdapter;
	}

	public void put(ExtRepositoryModelAdapter<?> extRepositoryModelAdapter) {
		ExtRepositoryModel extRepositoryModel =
			extRepositoryModelAdapter.getExtRepositoryModel();

		String extRepositoryModelKey =
			extRepositoryModel.getExtRepositoryModelKey();

		if (_log.isInfoEnabled()) {
			_log.info("Put " + extRepositoryModelKey);
		}

		_extRepositoryAdapters.put(
			extRepositoryModelKey, extRepositoryModelAdapter);
	}

	public void remove(String extRepositoryModelKey) {
		if (_log.isInfoEnabled()) {
			_log.info("Remove " + extRepositoryModelKey);
		}

		_extRepositoryAdapters.remove(extRepositoryModelKey);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExtRepositoryAdapterCache.class);

	private static final ThreadLocal<ExtRepositoryAdapterCache>
		_extRepositoryAdapterThreadLocal = new CentralizedThreadLocal<>(
			ExtRepositoryAdapterCache.class.getName(),
			ExtRepositoryAdapterCache::new);

	private final Map<String, ExtRepositoryModelAdapter<?>>
		_extRepositoryAdapters = new HashMap<>();

}