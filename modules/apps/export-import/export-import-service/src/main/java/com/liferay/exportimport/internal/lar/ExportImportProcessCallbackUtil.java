/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			catch (Exception exception) {
				ExportImportRuntimeException exportImportRuntimeException =
					new ExportImportRuntimeException(
						exception.getLocalizedMessage(), exception);

				Class<?> clazz = callable.getClass();

				exportImportRuntimeException.setClassName(clazz.getName());

				throw exportImportRuntimeException;
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