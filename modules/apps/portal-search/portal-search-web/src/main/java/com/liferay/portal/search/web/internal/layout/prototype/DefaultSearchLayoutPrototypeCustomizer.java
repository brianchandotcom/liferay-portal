/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.layout.prototype;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.util.DefaultLayoutPrototypesUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.web.constants.SearchBarPortletKeys;
import com.liferay.portal.search.web.constants.SearchResultsPortletKeys;
import com.liferay.portal.search.web.internal.category.facet.constants.CategoryFacetPortletKeys;
import com.liferay.portal.search.web.internal.folder.facet.constants.FolderFacetPortletKeys;
import com.liferay.portal.search.web.internal.modified.facet.constants.ModifiedFacetPortletKeys;
import com.liferay.portal.search.web.internal.search.options.constants.SearchOptionsPortletKeys;
import com.liferay.portal.search.web.internal.site.facet.constants.SiteFacetPortletKeys;
import com.liferay.portal.search.web.internal.suggestions.constants.SuggestionsPortletKeys;
import com.liferay.portal.search.web.internal.tag.facet.constants.TagFacetPortletKeys;
import com.liferay.portal.search.web.internal.type.facet.constants.TypeFacetPortletKeys;
import com.liferay.portal.search.web.internal.user.facet.constants.UserFacetPortletKeys;
import com.liferay.portal.search.web.layout.prototype.SearchLayoutPrototypeCustomizer;

/**
 * @author André de Oliveira
 * @author Lino Alves
 */
public class DefaultSearchLayoutPrototypeCustomizer
	implements SearchLayoutPrototypeCustomizer {

	@Override
	public void customize(Layout layout) throws Exception {
		String portletInstanceId = PortletIdCodec.generateInstanceId();

		_addBorderlessPortlet(
			layout,
			PortletIdCodec.encode(
				SearchBarPortletKeys.SEARCH_BAR, portletInstanceId),
			"column-1");

		_addBorderlessPortlet(
			layout,
			PortletIdCodec.encode(
				SuggestionsPortletKeys.SUGGESTIONS, portletInstanceId),
			"column-1");

		_addBorderlessPortlet(
			layout,
			PortletIdCodec.encode(
				SearchResultsPortletKeys.SEARCH_RESULTS, portletInstanceId),
			"column-3");

		_addBorderlessPortlet(
			layout,
			PortletIdCodec.encode(
				SearchOptionsPortletKeys.SEARCH_OPTIONS, portletInstanceId),
			"column-3");

		_addBorderlessPortlet(
			layout,
			PortletIdCodec.encode(
				SiteFacetPortletKeys.SITE_FACET, portletInstanceId),
			"column-2");

		_addBorderlessPortlet(
			layout,
			PortletIdCodec.encode(
				TypeFacetPortletKeys.TYPE_FACET, portletInstanceId),
			"column-2");

		_addBorderlessPortlet(
			layout,
			PortletIdCodec.encode(
				TagFacetPortletKeys.TAG_FACET, portletInstanceId),
			"column-2");

		_addBorderlessPortlet(
			layout,
			PortletIdCodec.encode(
				CategoryFacetPortletKeys.CATEGORY_FACET, portletInstanceId),
			"column-2");

		_addBorderlessPortlet(
			layout,
			PortletIdCodec.encode(
				FolderFacetPortletKeys.FOLDER_FACET, portletInstanceId),
			"column-2");

		_addBorderlessPortlet(
			layout,
			PortletIdCodec.encode(
				UserFacetPortletKeys.USER_FACET, portletInstanceId),
			"column-2");

		_addBorderlessPortlet(
			layout,
			PortletIdCodec.encode(
				ModifiedFacetPortletKeys.MODIFIED_FACET, portletInstanceId),
			"column-2");
	}

	@Override
	public String getLayoutTemplateId() {
		return "1_2_columns_i";
	}

	private void _addBorderlessPortlet(
			Layout layout, String portletKey, String columnId)
		throws Exception {

		String portletId = DefaultLayoutPrototypesUtil.addPortletId(
			layout, portletKey, columnId);

		DefaultLayoutPrototypesUtil.updatePortletSetup(
			layout, portletId,
			HashMapBuilder.put(
				"portletSetupPortletDecoratorId", "barebone"
			).build());
	}

}