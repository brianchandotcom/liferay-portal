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

package com.liferay.portlet.layoutsadmin.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.service.ExportImportConfigurationLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Akos Thurzo
 */
public class ExportImportConfigurationUtil {

	public static List<ExportImportConfiguration> getExportImportConfigurations(
			Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<ExportImportConfiguration> exportImportConfigurations =
			new ArrayList<>(documents.size());

		for (Document document : documents) {
			long exportImportConfigurationId = GetterUtil.getLong(
				document.get("exportImportConfigurationId"));

			ExportImportConfiguration exportImportConfiguration =
				ExportImportConfigurationLocalServiceUtil.
					getExportImportConfiguration(exportImportConfigurationId);

			if (exportImportConfiguration == null) {
				exportImportConfigurations = null;

				Indexer indexer = IndexerRegistryUtil.getIndexer(
					ExportImportConfiguration.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (exportImportConfigurations != null) {
				exportImportConfigurations.add(exportImportConfiguration);
			}
		}

		return exportImportConfigurations;
	}

}