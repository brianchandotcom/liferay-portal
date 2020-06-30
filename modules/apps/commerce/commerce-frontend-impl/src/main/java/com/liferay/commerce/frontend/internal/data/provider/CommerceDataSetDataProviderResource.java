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

package com.liferay.commerce.frontend.internal.data.provider;

import com.liferay.commerce.frontend.CommerceDataProviderRegistry;
import com.liferay.commerce.frontend.CommerceDataSetDataProvider;
import com.liferay.commerce.frontend.FilterFactory;
import com.liferay.commerce.frontend.FilterFactoryRegistry;
import com.liferay.commerce.frontend.Pagination;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetDataJSONBuilder;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = CommerceDataSetDataProviderResource.class)
public class CommerceDataSetDataProviderResource {

	@GET
	@Path("/commerce-data-set/{groupId}/{tableName}/{dataProvider}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(
		@PathParam("groupId") long groupId,
		@PathParam("tableName") String tableName,
		@PathParam("dataProvider") String dataProvider,
		@QueryParam("plid") long plid,
		@QueryParam("portletId") String portletId, @Context UriInfo uriInfo,
		@Context Pagination pagination, @Context Sort sort,
		@Context HttpServletRequest httpServletRequest,
		@Context HttpServletResponse httpServletResponse) {

		CommerceDataSetDataProvider commerceDataProvider =
			_commerceDataProviderRegistry.getCommerceDataProvider(dataProvider);

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			if (plid > 0) {
				Layout layout = _layoutLocalService.fetchLayout(plid);

				themeDisplay.setLayout(layout);

				themeDisplay.setPlid(plid);
			}

			themeDisplay.setScopeGroupId(groupId);
			themeDisplay.setSiteGroupId(groupId);

			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			portletDisplay.setId(portletId);

			httpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, themeDisplay);

			FilterFactory filterFactory =
				_filterFactoryRegistry.getFilterFactory(dataProvider);

			List<Object> items = commerceDataProvider.getItems(
				httpServletRequest, filterFactory.create(httpServletRequest),
				pagination, sort);

			int totalItems = commerceDataProvider.countItems(
				httpServletRequest, filterFactory.create(httpServletRequest));

			String json = _clayDataSetDataJSONBuilder.build(
				groupId, tableName, items, totalItems, httpServletRequest);

			return Response.ok(
				json, MediaType.APPLICATION_JSON
			).build();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return Response.status(
			Response.Status.NOT_FOUND
		).build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDataSetDataProviderResource.class);

	@Reference
	private ClayDataSetDataJSONBuilder _clayDataSetDataJSONBuilder;

	@Reference
	private CommerceDataProviderRegistry _commerceDataProviderRegistry;

	@Reference
	private FilterFactoryRegistry _filterFactoryRegistry;

	@Reference
	private LayoutLocalService _layoutLocalService;

}