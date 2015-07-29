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

import com.liferay.portal.kernel.search.HitsOpenSearchImpl;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

/**
 * @author Brian Wing Shun Chan
 */
@OSGiBeanProperties
public class DLOpenSearchImpl extends HitsOpenSearchImpl {

	public static final String TITLE = "Liferay Documents and Media Search: ";

	@Override
	public String getClassName() {
		return DLFileEntry.class.getName();
	}

	@Override
	public Indexer<DLFileEntry> getIndexer() {
		return IndexerRegistryUtil.getIndexer(DLFileEntry.class);
	}

	@Override
	public String getSearchPath() {
		return StringPool.BLANK;
	}

	@Override
	public String getTitle(String keywords) {
		return TITLE + keywords;
	}

}