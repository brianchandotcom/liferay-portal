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
import com.liferay.commerce.product.content.render.list.CPContentListRendererRegistry;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRendererRegistry;
import com.liferay.commerce.product.content.search.web.internal.display.context.CPSearchResultsDisplayContext;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
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
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-cp-sorting",
		"com.liferay.portlet.display-category=commerce",
		"com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.restore-current-view=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Sort", "javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/sort/view.jsp",
		"javax.portlet.name=" + CPPortletKeys.CP_SORT,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user"
	},
	service = {Portlet.class, PortletSharedSearchContributor.class}
)
public class CPSortPortlet
	extends MVCPortlet implements PortletSharedSearchContributor {

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

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		PortletSharedSearchResponse portletSharedSearchResponse =
			_portletSharedSearchRequest.search(renderRequest);

		try {
			CPSearchResultsDisplayContext cpSearchResultsDisplayContext =
				new CPSearchResultsDisplayContext(
					_cpContentListEntryRendererRegistry,
					_cpContentListRendererRegistry, _cpDefinitionHelper,
					_cpTypeServicesTracker, httpServletRequest,
					portletSharedSearchResponse);

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT, cpSearchResultsDisplayContext);
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException, configurationException);
		}

		super.render(renderRequest, renderResponse);
	}

	private static final Log _log = LogFactoryUtil.getLog(CPSortPortlet.class);

	@Reference
	private CPContentListEntryRendererRegistry
		_cpContentListEntryRendererRegistry;

	@Reference
	private CPContentListRendererRegistry _cpContentListRendererRegistry;

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

	@Reference
	private CPTypeServicesTracker _cpTypeServicesTracker;

	@Reference
	private Portal _portal;

	@Reference
	private PortletSharedSearchRequest _portletSharedSearchRequest;

}