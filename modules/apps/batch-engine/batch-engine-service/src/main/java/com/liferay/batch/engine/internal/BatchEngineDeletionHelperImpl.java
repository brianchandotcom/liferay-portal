/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal;

import com.liferay.batch.engine.BatchEngineDeletionHelper;
import com.liferay.batch.engine.internal.exportimport.data.handler.BatchEnginePortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.SystemEvent;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Vendel Toreki
 */
@Component(service = BatchEngineDeletionHelper.class)
public class BatchEngineDeletionHelperImpl
	implements BatchEngineDeletionHelper {

	@Override
	public void addDeletionEvent(
		PortletDataContext portletDataContext, SystemEvent systemEvent) {

		Map<String, String> newPrimaryKeysMap =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				systemEvent.getClassName() +
					BatchEnginePortletDataHandler.
						BATCH_DELETE_CLASS_NAME_POSTFIX);

		newPrimaryKeysMap.put(
			systemEvent.getClassExternalReferenceCode(), StringPool.BLANK);
	}

	@Override
	public void exportDeletions(PortletDataContext portletDataContext) {
		Map<String, Map<?, ?>> newPrimaryKeysMaps =
			portletDataContext.getNewPrimaryKeysMaps();

		for (String key : newPrimaryKeysMaps.keySet()) {
			if (!key.endsWith(
					BatchEnginePortletDataHandler.
						BATCH_DELETE_CLASS_NAME_POSTFIX)) {

				continue;
			}

			int length =
				BatchEnginePortletDataHandler.BATCH_DELETE_CLASS_NAME_POSTFIX.
					length();

			String className = key.substring(0, key.length() - length);

			BatchEnginePortletDataHandler batchEnginePortletDataHandler =
				_getBatchEnginePortletDataHandler(className);

			if (batchEnginePortletDataHandler != null) {
				batchEnginePortletDataHandler.exportDeletionSystemEvents(
					portletDataContext);
			}
		}
	}

	@Override
	public void importDeletions(
			PortletDataContext portletDataContext, String portletId)
		throws Exception {

		PortletDataHandler portletDataHandler =
			_getPortletDataHandlerForPortlet(portletId);

		if (portletDataHandler != null) {
			portletDataHandler.deleteData(portletDataContext, portletId, null);
		}
	}

	@Override
	public boolean isBatchDeleteSupported(String className) {
		return _serviceTrackerMap.containsKey(className);
	}

	@Override
	public boolean isBatchPortlet(String portletId) {
		PortletDataHandler portletDataHandler =
			_portletIdServiceTrackerMap.getService(portletId);

		if (portletDataHandler instanceof BatchEnginePortletDataHandler) {
			return true;
		}

		return false;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, PortletDataHandler.class,
			"batch.engine.task.item.delegate.item.class.name");

		_portletIdServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, PortletDataHandler.class, "javax.portlet.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();

		_portletIdServiceTrackerMap.close();
	}

	private BatchEnginePortletDataHandler _getBatchEnginePortletDataHandler(
		String className) {

		PortletDataHandler portletDataHandler = _serviceTrackerMap.getService(
			className);

		if (portletDataHandler instanceof BatchEnginePortletDataHandler) {
			return (BatchEnginePortletDataHandler)portletDataHandler;
		}

		return null;
	}

	private PortletDataHandler _getPortletDataHandlerForPortlet(
		String portletId) {

		PortletDataHandler portletDataHandler =
			_portletIdServiceTrackerMap.getService(portletId);

		if (portletDataHandler instanceof BatchEnginePortletDataHandler) {
			return portletDataHandler;
		}

		return null;
	}

	private ServiceTrackerMap<String, PortletDataHandler>
		_portletIdServiceTrackerMap;
	private ServiceTrackerMap<String, PortletDataHandler> _serviceTrackerMap;

}