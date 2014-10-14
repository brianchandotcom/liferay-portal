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

package com.liferay.portlet.assetpublisher.util;

import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.model.AssetEntry;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class AssetIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {AssetEntry.class.getName()};

	public static final String PORTLET_ID = PortletKeys.ASSET_PUBLISHER;

	public AssetIndexer() {
		setDefaultSelectedFieldNames(
			Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK, Field.UID);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		if (searchContext.getAttributes() == null) {
			return;
		}

		addSearchTerm(searchQuery, searchContext, Field.DESCRIPTION, false);
		addSearchTerm(searchQuery, searchContext, Field.TITLE, false);
		addSearchTerm(searchQuery, searchContext, Field.USER_NAME, false);
	}

	@Override
	protected void doDelete(Object obj) {
	}

	@Override
	protected Document doGetDocument(Object obj) {
		return null;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet, PortletURL portletURL,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return null;
	}

	@Override
	protected void doReindex(Object obj) {
	}

	@Override
	protected void doReindex(String className, long classPK) {
	}

	@Override
	protected void doReindex(String[] ids) {
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

}