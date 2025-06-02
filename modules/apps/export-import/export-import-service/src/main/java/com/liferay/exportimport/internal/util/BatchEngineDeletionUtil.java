/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.util;

import com.liferay.exportimport.internal.data.handler.BatchEnginePortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Vendel Toreki
 */
public class BatchEngineDeletionUtil {

	public static void addDeletionEvent(
		PortletDataContext portletDataContext, SystemEvent systemEvent) {

		Map<String, String> newPrimaryKeysMap =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				systemEvent.getClassName() +
					BatchEnginePortletDataHandler.
						BATCH_DELETE_CLASS_NAME_POSTFIX);

		newPrimaryKeysMap.put(
			systemEvent.getClassExternalReferenceCode(), StringPool.BLANK);
	}

	public static void exportDeletions(PortletDataContext portletDataContext) {
		Map<String, Map<?, ?>> newPrimaryKeysMaps =
			portletDataContext.getNewPrimaryKeysMaps();

		for (String key : newPrimaryKeysMaps.keySet()) {
			if (!key.endsWith(
					BatchEnginePortletDataHandler.
						BATCH_DELETE_CLASS_NAME_POSTFIX)) {

				continue;
			}

			BatchEnginePortletDataHandler batchEnginePortletDataHandler =
				_getBatchEnginePortletDataHandler(
					StringUtil.removeLast(
						key,
						BatchEnginePortletDataHandler.
							BATCH_DELETE_CLASS_NAME_POSTFIX));

			if (batchEnginePortletDataHandler != null) {
				batchEnginePortletDataHandler.exportDeletionSystemEvents(
					portletDataContext);
			}
		}
	}

	public static void importDeletions(
			PortletDataContext portletDataContext, String portletId)
		throws Exception {

		PortletDataHandler portletDataHandler =
			_getPortletDataHandlerForPortlet(portletId);

		if (portletDataHandler != null) {
			portletDataHandler.deleteData(portletDataContext, portletId, null);
		}
	}

	public static boolean isBatchDeleteSupported(String className) {
		return _serviceTrackerMap.containsKey(className);
	}

	public static boolean isBatchPortlet(String portletId) {
		PortletDataHandler portletDataHandler =
			_portletIdServiceTrackerMap.getService(portletId);

		if (portletDataHandler instanceof BatchEnginePortletDataHandler) {
			return true;
		}

		return false;
	}

	private static BatchEnginePortletDataHandler
		_getBatchEnginePortletDataHandler(String className) {

		PortletDataHandler portletDataHandler = _serviceTrackerMap.getService(
			className);

		if (portletDataHandler instanceof BatchEnginePortletDataHandler) {
			return (BatchEnginePortletDataHandler)portletDataHandler;
		}

		return null;
	}

	private static PortletDataHandler _getPortletDataHandlerForPortlet(
		String portletId) {

		PortletDataHandler portletDataHandler =
			_portletIdServiceTrackerMap.getService(portletId);

		if (portletDataHandler instanceof BatchEnginePortletDataHandler) {
			return portletDataHandler;
		}

		return null;
	}

	private static final ServiceTrackerMap<String, PortletDataHandler>
		_portletIdServiceTrackerMap;
	private static final ServiceTrackerMap<String, PortletDataHandler>
		_serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(BatchEngineDeletionUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_portletIdServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, PortletDataHandler.class,
				"jakarta.portlet.name");
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, PortletDataHandler.class,
			"batch.engine.task.item.delegate.item.class.name");
	}

}