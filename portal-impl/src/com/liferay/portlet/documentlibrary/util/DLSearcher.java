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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;

/**
 * @author Julio Camarero
 * @author Eudaldo Alonso
 */
public class DLSearcher extends BaseSearcher {

	public static final String[] CLASS_NAMES = {
		DLFileEntry.class.getName(), DLFolder.class.getName()
	};

	public static Indexer<?> getInstance() {
		return new DLSearcher();
	}

	public DLSearcher() {
		setDefaultSelectedFieldNames(
			Field.CLASS_NAME_ID, Field.CLASS_PK, Field.COMPANY_ID,
			Field.CONTENT, Field.DESCRIPTION, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.TITLE, Field.VERSION, Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String[] getSearchClassNames() {
		return CLASS_NAMES;
	}

}