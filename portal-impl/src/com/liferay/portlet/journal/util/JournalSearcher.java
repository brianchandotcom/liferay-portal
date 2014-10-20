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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;

/**
 * @author Julio Camarero
 * @author Eudaldo Alonso
 */
public class JournalSearcher extends BaseSearcher {

	public static final String[] CLASS_NAMES = {
		JournalArticle.class.getName(), JournalFolder.class.getName()
	};

	public static Indexer getInstance() {
		return new JournalSearcher();
	}

	public JournalSearcher() {
		setDefaultSelectedFieldNames(
			Field.ARTICLE_ID, Field.COMPANY_ID, Field.DEFAULT_LANGUAGE_ID,
			Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK, Field.GROUP_ID,
			Field.VERSION, Field.UID);
		setDefaultSelectedLocalizedFieldNames(
			Field.CONTENT, Field.DESCRIPTION, Field.TITLE);
		setFilterSearch(true);
		setPermissionAware(true);
		setSelectAllLocales(true);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

}