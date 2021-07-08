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

package com.liferay.commerce.product.content.search.web.internal.portlet;

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	enabled = false, property = "javax.portlet.name=" + CPPortletKeys.CP_SORT,
	service = PortletSharedSearchContributor.class
)
public class CPSortPortletSharedSearchContributor
	implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		SearchContext searchContext =
			portletSharedSearchSettings.getSearchContext();

		QueryConfig queryConfig = portletSharedSearchSettings.getQueryConfig();

		queryConfig.setHighlightEnabled(false);

		RenderRequest renderRequest =
			portletSharedSearchSettings.getRenderRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		HttpServletRequest httpServletRequest =
			_portal.getOriginalServletRequest(themeDisplay.getRequest());

		String portletId = ParamUtil.getString(httpServletRequest, "p_p_id");

		String orderByCol = ParamUtil.getString(
			httpServletRequest,
			StringBundler.concat(
				StringPool.UNDERLINE, portletId, StringPool.UNDERLINE,
				SearchContainer.DEFAULT_ORDER_BY_COL_PARAM));

		if (orderByCol.equals("price-low-to-high")) {
			searchContext.setSorts(
				SortFactoryUtil.create(
					CPField.BASE_PRICE, Sort.DOUBLE_TYPE, false));
		}
		else if (orderByCol.equals("price-high-to-low")) {
			searchContext.setSorts(
				SortFactoryUtil.create(
					CPField.BASE_PRICE, Sort.DOUBLE_TYPE, true));
		}
		else if (orderByCol.equals("name-ascending")) {
			searchContext.setSorts(SortFactoryUtil.create(Field.NAME, false));
		}
		else if (orderByCol.equals("name-descending")) {
			searchContext.setSorts(SortFactoryUtil.create(Field.NAME, true));
		}
		else if (orderByCol.equals("new-items")) {
			searchContext.setSorts(
				SortFactoryUtil.create(Field.CREATE_DATE + "_sortable", true));
		}
		else {
			searchContext.setSorts(SortFactoryUtil.getDefaultSorts());
		}
	}

	@Reference
	private Portal _portal;

}