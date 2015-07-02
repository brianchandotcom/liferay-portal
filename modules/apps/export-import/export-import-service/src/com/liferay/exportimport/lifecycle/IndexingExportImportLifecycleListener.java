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

package com.liferay.exportimport.lifecycle;

import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.EVENT_LAYOUT_IMPORT_SUCCEEDED;
import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.EVENT_PORTLET_IMPORT_SUCCEEDED;
import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS;
import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS;
import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.PROCESS_FLAG_PORTLET_IMPORT_IN_PROCESS;
import static com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleConstants.PROCESS_FLAG_PORTLET_STAGING_IN_PROCESS;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerKeys;
import com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleEvent;
import com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleListener;

import java.io.Serializable;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = ExportImportLifecycleListener.class)
public class IndexingExportImportLifecycleListener
	implements ExportImportLifecycleListener {

	@Override
	public boolean isParallel() {
		return true;
	}

	@Override
	public void onExportImportLifecycleEvent(
			ExportImportLifecycleEvent exportImportLifecycleEvent)
		throws Exception {

		if (((exportImportLifecycleEvent.getCode() !=
				EVENT_LAYOUT_IMPORT_SUCCEEDED) ||
			 ((exportImportLifecycleEvent.getProcessFlag() !=
				 PROCESS_FLAG_LAYOUT_IMPORT_IN_PROCESS) &&
			  (exportImportLifecycleEvent.getProcessFlag() !=
				  PROCESS_FLAG_LAYOUT_STAGING_IN_PROCESS))) &&
			((exportImportLifecycleEvent.getCode() !=
				EVENT_PORTLET_IMPORT_SUCCEEDED) ||
			 ((exportImportLifecycleEvent.getProcessFlag() !=
				 PROCESS_FLAG_PORTLET_IMPORT_IN_PROCESS) &&
			  (exportImportLifecycleEvent.getProcessFlag() !=
				  PROCESS_FLAG_PORTLET_STAGING_IN_PROCESS)))) {

			return;
		}

		List<Serializable> attributes =
			exportImportLifecycleEvent.getAttributes();

		if (ListUtil.isEmpty(attributes)) {
			return;
		}

		PortletDataContext portletDataContext =
			(PortletDataContext)attributes.get(0);

		if (portletDataContext == null) {
			return;
		}

		long userId = 0;

		if (attributes.size() == 2) {
			userId = GetterUtil.getLong(attributes.get(1));
		}

		reindex(portletDataContext, userId);
	}

	protected void reindex(PortletDataContext portletDataContext, long userId) {
		boolean importPermissions = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.PERMISSIONS);

		if (importPermissions) {
			if (userId > 0) {
				Indexer<User> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					User.class);

				try {
					User user = _userLocalService.fetchUser(userId);

					if (user != null) {
						indexer.reindex(user);
					}
				}
				catch (SearchException se) {
					if (_log.isDebugEnabled()) {
						_log.debug("Unable to reindex user " + userId, se);
					}
				}
			}
		}

		Indexer<PortletDataContext> portletDataContextIndexer =
			IndexerRegistryUtil.getIndexer(PortletDataContext.class);

		try {
			portletDataContextIndexer.reindex(portletDataContext);
		}
		catch (SearchException se) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to reindex portlet data context", se);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexingExportImportLifecycleListener.class);

	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	private UserLocalService _userLocalService;

}