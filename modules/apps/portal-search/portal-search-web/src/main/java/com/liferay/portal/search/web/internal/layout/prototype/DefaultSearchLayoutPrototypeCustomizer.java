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

package com.liferay.portal.search.web.internal.layout.prototype;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.DefaultLayoutPrototypesUtil;
import com.liferay.portal.search.web.internal.category.facet.constants.CategoryFacetPortletKeys;
import com.liferay.portal.search.web.internal.folder.facet.constants.FolderFacetPortletKeys;
import com.liferay.portal.search.web.internal.modified.facet.constants.ModifiedFacetPortletKeys;
import com.liferay.portal.search.web.internal.search.bar.constants.SearchBarPortletKeys;
import com.liferay.portal.search.web.internal.search.options.constants.SearchOptionsPortletKeys;
import com.liferay.portal.search.web.internal.search.results.constants.SearchResultsPortletKeys;
import com.liferay.portal.search.web.internal.site.facet.constants.SiteFacetPortletKeys;
import com.liferay.portal.search.web.internal.suggestions.constants.SuggestionsPortletKeys;
import com.liferay.portal.search.web.internal.tag.facet.constants.TagFacetPortletKeys;
import com.liferay.portal.search.web.internal.type.facet.constants.TypeFacetPortletKeys;
import com.liferay.portal.search.web.internal.user.facet.constants.UserFacetPortletKeys;
import com.liferay.portal.search.web.layout.prototype.SearchLayoutPrototypeCustomizer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author André de Oliveira
 * @author Lino Alves
 */
public class DefaultSearchLayoutPrototypeCustomizer
	implements SearchLayoutPrototypeCustomizer {

	@Override
	public void customize(Layout layout) throws Exception {
		addBorderlessPortlet(
			layout, SearchBarPortletKeys.SEARCH_BAR, "column-1");

		addBorderlessPortlet(
			layout, SuggestionsPortletKeys.SUGGESTIONS, "column-1");

		addBorderlessPortlet(
			layout, SearchResultsPortletKeys.SEARCH_RESULTS, "column-3");

		addBorderlessPortlet(
			layout, SearchOptionsPortletKeys.SEARCH_OPTIONS, "column-3");

		addBorderlessPortlet(
			layout, SiteFacetPortletKeys.SITE_FACET, "column-2");

		addBorderlessPortlet(
			layout, TypeFacetPortletKeys.TYPE_FACET, "column-2");

		addBorderlessPortlet(layout, TagFacetPortletKeys.TAG_FACET, "column-2");

		addBorderlessPortlet(
			layout, CategoryFacetPortletKeys.CATEGORY_FACET, "column-2");

		addBorderlessPortlet(
			layout, FolderFacetPortletKeys.FOLDER_FACET, "column-2");

		addBorderlessPortlet(
			layout, UserFacetPortletKeys.USER_FACET, "column-2");

		addBorderlessPortlet(
			layout, ModifiedFacetPortletKeys.MODIFIED_FACET, "column-2");
	}

	@Override
	public String getLayoutTemplateId() {
		return "1_2_columns_i";
	}

	protected void addBorderlessPortlet(
			Layout layout, String portletKey, String columnId)
		throws Exception {

		String portletId = DefaultLayoutPrototypesUtil.addPortletId(
			layout, portletKey, columnId);

		Map<String, String> preferences = new HashMap<>();

		preferences.put("portletSetupPortletDecoratorId", "barebone");

		DefaultLayoutPrototypesUtil.updatePortletSetup(
			layout, portletId, preferences);
	}

}