/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.internal.search;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.Field;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luan Maoski
 * @author Lucas Marques
 */
@Component(
	property = "model.class.name=com.liferay.asset.kernel.model.AssetTag",
	service = BaseSearcher.class
)
public class AssetTagSearcher extends BaseSearcher {

	public static final String CLASS_NAME = AssetTag.class.getName();

	public AssetTagSearcher() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_PK, Field.ENTRY_CLASS_NAME,
			Field.GROUP_ID, Field.UID);
		setFilterSearch(true);
		setPermissionAware(false);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

}