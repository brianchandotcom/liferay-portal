/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.search;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.Field;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luan Maoski
 */
@Component(
	property = "model.class.name=com.liferay.message.boards.model.MBMessage",
	service = BaseSearcher.class
)
public class MBMessageSearcher extends BaseSearcher {

	public static final String CLASS_NAME = MBMessage.class.getName();

	public MBMessageSearcher() {
		setDefaultSelectedFieldNames(
			Field.ASSET_TAG_NAMES, Field.CLASS_NAME_ID, Field.CLASS_PK,
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.GROUP_ID, Field.MODIFIED_DATE, Field.SCOPE_GROUP_ID,
			Field.UID);
		setDefaultSelectedLocalizedFieldNames(Field.CONTENT, Field.TITLE);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

}