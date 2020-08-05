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

package com.liferay.frontend.taglib.clay.internal.jaxrs.application;

import com.liferay.frontend.taglib.clay.data.FilterFactory;
import com.liferay.frontend.taglib.clay.data.FilterFactoryRegistry;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDataJSONFactory;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetProviderRegistry;
import com.liferay.frontend.taglib.clay.internal.jaxrs.context.provider.PaginationContextProvider;
import com.liferay.frontend.taglib.clay.internal.jaxrs.context.provider.SortContextProvider;
import com.liferay.frontend.taglib.clay.internal.jaxrs.context.provider.ThemeDisplayContextProvider;
import com.liferay.frontend.taglib.clay.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Marco Leo
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/frontend-taglib-clay/app",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=Liferay.Frontend.Clay",
		"auth.verifier.auth.verifier.PortalSessionAuthVerifier.urls.includes=/*",
		"auth.verifier.guest.allowed=true", "liferay.oauth2=false"
	},
	service = Application.class
)
public class FrontendClayApplication extends Application {

	@GET
	@Path("/data-set/{tableName}/{dataProvider}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClayDataSetData(
		@PathParam("dataProvider") String dataProvider,
		@PathParam("tableName") String tableName,
		@QueryParam("groupId") long groupId, @QueryParam("plid") long plid,
		@QueryParam("portletId") String portletId,
		@Context HttpServletRequest httpServletRequest,
		@Context HttpServletResponse httpServletResponse,
		@Context Pagination pagination, @Context Sort sort,
		@Context ThemeDisplay themeDisplay, @Context UriInfo uriInfo) {

		ClayDataSetDataProvider clayDataSetDataProvider =
			_clayDataProviderRegistry.getClayDataSetProvider(dataProvider);

		if ((clayDataSetDataProvider == null) && _log.isDebugEnabled()) {
			_log.debug(
				"No Clay data set data provider registered with key " +
					dataProvider);
		}

		try {
			FilterFactory filterFactory =
				_filterFactoryRegistry.getFilterFactory(dataProvider);

			return Response.ok(
				_clayDataSetDataJSONFactory.create(
					groupId, tableName,
					clayDataSetDataProvider.getItems(
						httpServletRequest,
						filterFactory.create(httpServletRequest), pagination,
						sort),
					clayDataSetDataProvider.getItemsCount(
						httpServletRequest,
						filterFactory.create(httpServletRequest)),
					httpServletRequest),
				MediaType.APPLICATION_JSON
			).build();
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return Response.status(
			Response.Status.NOT_FOUND
		).build();
	}

	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<>();

		singletons.add(_paginationContextProvider);
		singletons.add(_sortContextProvider);
		singletons.add(_themeDisplayContextProvider);
		singletons.add(this);

		return singletons;
	}

	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/data-set/{id}/save-active-view-settings")
	@POST
	public Response saveActiveClayDataSetViewSettings(
		@PathParam("id") String id,
		@Context HttpServletRequest httpServletRequest,
		@Context HttpServletResponse httpServletResponse,
		@Context ThemeDisplay themeDisplay, @Context UriInfo uriInfo,
		String activeViewSettingsJSON) {

		try {
			PortalPreferences portalPreferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					httpServletRequest);

			String currentActiveViewSettingsJSON = portalPreferences.getValue(
				ServletContextUtil.getClayDataSetDisplaySettingsNamespace(
					httpServletRequest, id),
				"activeViewSettingsJSON", "{}");

			JSONObject currentActiveViewSettingsJSONObject =
				_jsonFactory.createJSONObject(currentActiveViewSettingsJSON);

			JSONObject activeViewSettingsJSONObject =
				_jsonFactory.createJSONObject(activeViewSettingsJSON);

			for (String key : activeViewSettingsJSONObject.keySet()) {
				currentActiveViewSettingsJSONObject.put(
					key, activeViewSettingsJSONObject.get(key));
			}

			portalPreferences.setValue(
				ServletContextUtil.getClayDataSetDisplaySettingsNamespace(
					httpServletRequest, id),
				"activeViewSettingsJSON",
				currentActiveViewSettingsJSONObject.toJSONString());

			return Response.ok(
			).build();
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return Response.status(
			Response.Status.NOT_FOUND
		).build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FrontendClayApplication.class);

	@Reference
	private ClayDataSetProviderRegistry _clayDataProviderRegistry;

	@Reference
	private ClayDataSetDataJSONFactory _clayDataSetDataJSONFactory;

	@Reference
	private FilterFactoryRegistry _filterFactoryRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private PaginationContextProvider _paginationContextProvider;

	@Reference
	private SortContextProvider _sortContextProvider;

	@Reference
	private ThemeDisplayContextProvider _themeDisplayContextProvider;

}