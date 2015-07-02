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

package com.liferay.portlet.documentlibrary.lar.lifecycle;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lifecycle.BaseExportImportLifecycleListener;

import java.util.List;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
@OSGiBeanProperties
public class DLIndexingExportImportLifecycleListener
	extends BaseExportImportLifecycleListener {

	@Override
	public boolean isParallel() {
		return true;
	}

	@Override
	protected void onLayoutImportProcessFinished(
			PortletDataContext portletDataContext)
		throws Exception {

		reindex(portletDataContext);
	}

	@Override
	protected void onPortletImportProcessFinished(
			PortletDataContext portletDataContext)
		throws Exception {

		reindex(portletDataContext);
	}

	protected void reindex(PortletDataContext portletDataContext) {
		Map<String, Long> ddmStructureIds =
			(Map<String, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		if (MapUtil.isEmpty(ddmStructureIds)) {
			return;
		}

		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getDDMStructureFileEntries(
				portletDataContext.getGroupId(),
				ArrayUtil.toLongArray(ddmStructureIds.values()));

		final Map<Long, Long> dlFileEntryPrimaryKeysMap =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFileEntry.class);

		dlFileEntries = ListUtil.filter(
			dlFileEntries,
			new PredicateFilter<DLFileEntry>() {

				@Override
				public boolean filter(DLFileEntry dlFileEntry) {
					return !dlFileEntryPrimaryKeysMap.containsValue(
						dlFileEntry.getFileEntryId());
				}

			});

		Indexer<DLFileEntry> dlFileEntryIndexer =
			IndexerRegistryUtil.getIndexer(DLFileEntry.class);

		if (dlFileEntryIndexer == null) {
			return;
		}

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			try {
				dlFileEntryIndexer.reindex(dlFileEntry);
			}
			catch (SearchException se) {
				if (_log.isDebugEnabled() && (dlFileEntry != null)) {
					_log.debug(
						"Unable to reindex file entry " +
							dlFileEntry.getFileEntryId(),
						se);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLIndexingExportImportLifecycleListener.class);

}