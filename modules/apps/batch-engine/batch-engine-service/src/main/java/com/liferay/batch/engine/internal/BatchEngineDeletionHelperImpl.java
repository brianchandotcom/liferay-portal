/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal;

import com.liferay.batch.engine.BatchEngineDeletionHelper;
import com.liferay.batch.engine.internal.exportimport.data.handler.BatchEnginePortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Vendel Toreki
 */
@Component(service = BatchEngineDeletionHelper.class)
public class BatchEngineDeletionHelperImpl
	implements BatchEngineDeletionHelper {

	@Override
	public void addDeletionEvent(
		PortletDataContext portletDataContext, SystemEvent systemEvent) {

		String key =
			systemEvent.getClassName() + _BATCH_DELETE_CLASS_NAME_POSTFIX;

		Map<String, String> newPrimaryKeysMap =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(key);

		newPrimaryKeysMap.put(systemEvent.getClassExternalReferenceCode(), "");
	}

	@Override
	public void exportDeletions(PortletDataContext portletDataContext) {
		for (String key :
				portletDataContext.getNewPrimaryKeysMaps(
				).keySet()) {

			if (key.endsWith(_BATCH_DELETE_CLASS_NAME_POSTFIX)) {
				String className = key.substring(
					0,
					key.length() - _BATCH_DELETE_CLASS_NAME_POSTFIX.length());

				// TODO find the right BatchEnginePortletDataHandler for the
				//  className, and call exportDeletionSystemEvents() on it

				BatchEnginePortletDataHandler batchEnginePortletDataHandler =
					_getBatchEnginePortletDataHandler(className);

				if (batchEnginePortletDataHandler != null) {
					batchEnginePortletDataHandler.exportDeletionSystemEvents(
						portletDataContext);
				}
			}
		}
	}

	@Override
	public Set<String> getBatchDeleteSupportedClassNames() {

		// TODO get the classNames from osgi registry (find a better method)

		Set<String> classNames = new HashSet<>();

		try (ServiceTrackerList<PortletDataHandler> portletDataHandlers =
				ServiceTrackerListFactory.open(
					SystemBundleUtil.getBundleContext(),
					PortletDataHandler.class, "(javax.portlet.name=*)")) {

			for (PortletDataHandler portletDataHandler : portletDataHandlers) {
				if (portletDataHandler instanceof
						BatchEnginePortletDataHandler) {

					for (StagedModelType stagedModelType :
							portletDataHandler.
								getDeletionSystemEventStagedModelTypes()) {

						classNames.add(stagedModelType.getClassName());
					}
				}
			}
		}

		return classNames;
	}

	private BatchEnginePortletDataHandler _getBatchEnginePortletDataHandler(
		String className) {

		// TODO use a proper ServiceTracker method to find the class

		try (ServiceTrackerList<PortletDataHandler> portletDataHandlers =
				ServiceTrackerListFactory.open(
					SystemBundleUtil.getBundleContext(),
					PortletDataHandler.class, "(javax.portlet.name=*)")) {

			for (PortletDataHandler portletDataHandler : portletDataHandlers) {
				if (portletDataHandler instanceof
						BatchEnginePortletDataHandler) {

					for (StagedModelType stagedModelType :
							portletDataHandler.
								getDeletionSystemEventStagedModelTypes()) {

						if (Objects.equals(
								stagedModelType.getClassName(), className)) {

							return (BatchEnginePortletDataHandler)
								portletDataHandler;
						}
					}
				}
			}
		}

		return null;
	}

	private static final String _BATCH_DELETE_CLASS_NAME_POSTFIX =
		"_batchDeleteERCs";

}