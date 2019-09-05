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

package com.liferay.exportimport.internal.lar;

import com.liferay.exportimport.kernel.exception.ExportImportRuntimeException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Daniel Kocsis
 */
public class ExportImportProcessCallbackUtil {

	public static List<Callable<?>> popCallbackList(String processId) {
		List<List<Callable<?>>> callbackListList = _callbackListListMap.get(
			processId);

		if (callbackListList == null) {
			return Collections.<Callable<?>>emptyList();
		}

		return callbackListList.remove(callbackListList.size() - 1);
	}

	public static void pushCallbackList(String processId) {
		List<List<Callable<?>>> callbackListList = _callbackListListMap.get(
			processId);

		if (callbackListList == null) {
			callbackListList = new ArrayList<>();

			_callbackListListMap.put(processId, callbackListList);
		}

		callbackListList.add(Collections.<Callable<?>>emptyList());
	}

	public static void registerCallback(
		String processId, Callable<?> callable) {

		List<List<Callable<?>>> callbackListList = _callbackListListMap.get(
			processId);

		if (ListUtil.isEmpty(callbackListList)) {

			// Not within a process boundary, ignore the callback

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Calling export import process callback immediately, " +
						"because there is no active process with ID " +
							processId);
			}

			try {
				callable.call();
			}
			catch (Exception e) {
				ExportImportRuntimeException eire =
					new ExportImportRuntimeException(
						e.getLocalizedMessage(), e);

				Class<?> clazz = callable.getClass();

				eire.setClassName(clazz.getName());

				throw eire;
			}

			return;
		}

		int index = callbackListList.size() - 1;

		List<Callable<?>> callableList = callbackListList.get(index);

		if (callableList == Collections.<Callable<?>>emptyList()) {
			callableList = new ArrayList<>();

			callbackListList.set(index, callableList);
		}

		callableList.add(callable);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportProcessCallbackUtil.class);

	private static final Map<String, List<List<Callable<?>>>>
		_callbackListListMap = new ConcurrentHashMap<>();

}