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

package com.liferay.knowledge.base.web.internal.search;

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;

/**
 * @author Alicia García
 */
public class KBSearcher extends BaseSearcher {

	public static final String[] CLASS_NAMES = {
		KBArticle.class.getName(), KBFolder.class.getName()
	};

	public static Indexer<?> getInstance() {
		return new KBSearcher();
	}

	public KBSearcher() {
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