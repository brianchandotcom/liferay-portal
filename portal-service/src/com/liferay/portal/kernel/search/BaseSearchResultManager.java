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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PortalUtil;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseSearchResultManager implements SearchResultManager {

	@Override
	public SearchResult createSearchResult(Document document)
		throws PortalException {

		long classNameId = GetterUtil.getLong(
			document.get(Field.CLASS_NAME_ID));
		long classPK = GetterUtil.getLong(document.get(Field.CLASS_PK));

		if ((classPK > 0) && (classNameId > 0)) {
			String className = PortalUtil.getClassName(classNameId);

			return new SearchResult(className, classPK);
		}

		String entryClassName = GetterUtil.getString(
			document.get(Field.ENTRY_CLASS_NAME));
		long entryClassPK = GetterUtil.getLong(
			document.get(Field.ENTRY_CLASS_PK));

		return new SearchResult(entryClassName, entryClassPK);
	}

}