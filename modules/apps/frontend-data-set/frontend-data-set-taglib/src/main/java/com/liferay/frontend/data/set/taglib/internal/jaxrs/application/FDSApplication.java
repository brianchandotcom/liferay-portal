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

package com.liferay.frontend.data.set.taglib.internal.jaxrs.application;

import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.FDSDataProviderRegistry;
import com.liferay.frontend.data.set.provider.search.FDSKeywordsFactory;
import com.liferay.frontend.data.set.provider.search.FDSKeywordsFactoryRegistry;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.frontend.data.set.taglib.internal.factory.FDSDataJSONFactory;
import com.liferay.frontend.data.set.taglib.internal.jaxrs.context.provider.PaginationContextProvider;
import com.liferay.frontend.data.set.taglib.internal.jaxrs.context.provider.SortContextProvider;
import com.liferay.frontend.data.set.taglib.internal.jaxrs.context.provider.ThemeDisplayContextProvider;
import com.liferay.frontend.data.set.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/frontend-data-set-taglib/app",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=Liferay.Frontend.FDS",
		"auth.verifier.auth.verifier.PortalSessionAuthVerifier.urls.includes=/*",
		"auth.verifier.guest.allowed=true", "liferay.oauth2=false"
	},
	service = Application.class
)
public class FDSApplication extends Application {

	@DELETE
	@Path("/fds/{fdsName}/custom-views/{fdsCustomViewId}")
	public Response deleteFDSCustomView(
		@PathParam("fdsName") String fdsName,
		@PathParam("fdsCustomViewId") String fdsCustomViewId,
		@Context HttpServletRequest httpServletRequest,
		@Context ThemeDisplay themeDisplay) {

		try {
			PortalPreferences portalPreferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					httpServletRequest);

			String fdsSettingsNamespace =
				ServletContextUtil.getFDSSettingsNamespace(
					httpServletRequest, fdsName);

			JSONObject customViewsJSONObject = _jsonFactory.createJSONObject(
				portalPreferences.getValue(
					fdsSettingsNamespace, "customViews", "{}"));

			customViewsJSONObject.remove(fdsCustomViewId);

			portalPreferences.setValue(
				fdsSettingsNamespace, "customViews",
				customViewsJSONObject.toString());

			return Response.noContent(
			).build();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return Response.status(
			Response.Status.INTERNAL_SERVER_ERROR
		).build();
	}

	@GET
	@Path("/data-set/{tableName}/{fdsDataProviderKey}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFDSData(
		@PathParam("fdsDataProviderKey") String fdsDataProviderKey,
		@PathParam("tableName") String tableName,
		@QueryParam("groupId") long groupId, @QueryParam("plid") long plid,
		@QueryParam("portletId") String portletId,
		@Context HttpServletRequest httpServletRequest,
		@Context HttpServletResponse httpServletResponse,
		@Context FDSPagination fdsPagination, @Context Sort sort,
		@Context ThemeDisplay themeDisplay, @Context UriInfo uriInfo) {

		FDSDataProvider fdsDataProvider =
			_fdsDataProviderRegistry.getFDSDataProvider(fdsDataProviderKey);

		if ((fdsDataProvider == null) && _log.isDebugEnabled()) {
			_log.debug(
				"No frontend data set data provider is associated with " +
					fdsDataProviderKey);
		}

		try {
			FDSKeywordsFactory fdsKeywordsFactory =
				_fdsKeywordsFactoryRegistry.getFDSKeywordsFactory(
					fdsDataProviderKey);

			return Response.ok(
				_fdsDataJSONFactory.create(
					groupId, tableName,
					fdsDataProvider.getItems(
						fdsKeywordsFactory.create(httpServletRequest),
						fdsPagination, httpServletRequest, sort),
					fdsDataProvider.getItemsCount(
						fdsKeywordsFactory.create(httpServletRequest),
						httpServletRequest),
					httpServletRequest),
				MediaType.APPLICATION_JSON
			).build();
		}
		catch (Exception exception) {
			_log.error(exception);
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

	@Path("/fds/{fdsName}/custom-views/{fdsCustomViewId}/label")
	@POST
	public Response renameFDSCustomView(
		@PathParam("fdsName") String fdsName,
		@PathParam("fdsCustomViewId") String fdsCustomViewId,
		@FormParam("customViewLabel") String fdsCustomViewLabel,
		@Context HttpServletRequest httpServletRequest,
		@Context ThemeDisplay themeDisplay) {

		try {
			PortalPreferences portalPreferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					httpServletRequest);

			String fdsSettingsNamespace =
				ServletContextUtil.getFDSSettingsNamespace(
					httpServletRequest, fdsName);

			JSONObject customViewsJSONObject = _jsonFactory.createJSONObject(
				portalPreferences.getValue(
					fdsSettingsNamespace, "customViews", "{}"));

			JSONObject customViewJSONObject =
				customViewsJSONObject.getJSONObject(fdsCustomViewId);

			if (customViewJSONObject == null) {
				return Response.status(
					Response.Status.NOT_FOUND
				).build();
			}

			customViewJSONObject.put("customViewLabel", fdsCustomViewLabel);

			customViewsJSONObject.put(fdsCustomViewId, customViewJSONObject);

			portalPreferences.setValue(
				fdsSettingsNamespace, "customViews",
				customViewsJSONObject.toString());

			return Response.ok(
			).build();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return Response.status(
			Response.Status.INTERNAL_SERVER_ERROR
		).build();
	}

	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/data-set/{id}/save-active-view-settings")
	@POST
	public Response saveActiveFDSViewSettings(
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
				ServletContextUtil.getFDSSettingsNamespace(
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
				ServletContextUtil.getFDSSettingsNamespace(
					httpServletRequest, id),
				"activeViewSettingsJSON",
				currentActiveViewSettingsJSONObject.toString());

			return Response.ok(
			).build();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return Response.status(
			Response.Status.NOT_FOUND
		).build();
	}

	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/fds/{fdsName}/custom-views")
	@POST
	public Response saveFDSCustomView(
		@PathParam("fdsName") String fdsName,
		@Context HttpServletRequest httpServletRequest,
		@Context ThemeDisplay themeDisplay, String customViewJSON) {

		try {
			PortalPreferences portalPreferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					httpServletRequest);

			String fdsSettingsNamespace =
				ServletContextUtil.getFDSSettingsNamespace(
					httpServletRequest, fdsName);

			JSONObject customViewsJSONObject = _jsonFactory.createJSONObject(
				portalPreferences.getValue(
					fdsSettingsNamespace, "customViews", "{}"));

			JSONObject customViewJSONObject = _jsonFactory.createJSONObject(
				customViewJSON);

			customViewsJSONObject.put(
				String.valueOf(customViewJSONObject.get("customViewId")),
				customViewJSONObject.get("viewState"));

			portalPreferences.setValue(
				fdsSettingsNamespace, "customViews",
				customViewsJSONObject.toString());

			return Response.ok(
			).build();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return Response.status(
			Response.Status.INTERNAL_SERVER_ERROR
		).build();
	}

	private static final Log _log = LogFactoryUtil.getLog(FDSApplication.class);

	@Reference
	private FDSDataJSONFactory _fdsDataJSONFactory;

	@Reference
	private FDSDataProviderRegistry _fdsDataProviderRegistry;

	@Reference
	private FDSKeywordsFactoryRegistry _fdsKeywordsFactoryRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private PaginationContextProvider _paginationContextProvider;

	@Reference
	private SortContextProvider _sortContextProvider;

	@Reference
	private ThemeDisplayContextProvider _themeDisplayContextProvider;

}