/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.search;

import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.Field;

import org.osgi.service.component.annotations.Component;

/**
 * @author Máté Thurzó
 * @author Akos Thurzo
 * @author Luan Maoski
 */
@Component(
	property = "model.class.name=com.liferay.exportimport.kernel.model.ExportImportConfiguration",
	service = BaseSearcher.class
)
public class ExportImportConfigurationSearcher extends BaseSearcher {

	public static final String CLASS_NAME =
		ExportImportConfiguration.class.getName();

	public ExportImportConfigurationSearcher() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.UID, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK);
		setFilterSearch(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

}