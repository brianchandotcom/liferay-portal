/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.admin.web.internal.display.context;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.frontend.data.set.admin.web.internal.constants.FDSAdminPortletKeys;
import com.liferay.frontend.data.set.admin.web.internal.portlet.FDSAdminPortlet;
import com.liferay.frontend.data.set.resolver.FDSAPIURLResolver;
import com.liferay.frontend.data.set.resolver.FDSAPIURLResolverRegistry;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

/**
 * @author Marko Cikos
 */
public class FDSAdminDisplayContext {

	public FDSAdminDisplayContext(
		CETManager cetManager,
		FDSAPIURLResolverRegistry
			fdsAPIURLResolverRegistry,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		RenderRequest renderRequest, RenderResponse renderResponse,
		ServiceTrackerList<FDSAdminPortlet.CompanyScopedOpenAPIResource>
			serviceTrackerList) {

		_cetManager = cetManager;
		_fdsAPIURLResolverRegistry =
			fdsAPIURLResolverRegistry;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_serviceTrackerList = serviceTrackerList;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_fdsEntryObjectDefinition =
			objectDefinitionLocalService.fetchObjectDefinition(
				_themeDisplay.getCompanyId(), "FDSEntry");
		_fdsViewObjectDefinition =
			objectDefinitionLocalService.fetchObjectDefinition(
				_themeDisplay.getCompanyId(), "FDSView");
	}

	public String getEditDataSetURL() {
		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_renderRequest, FDSAdminPortletKeys.FDS_ADMIN,
				RenderRequest.RENDER_PHASE)
		).setMVCPath(
			"/data_set.jsp"
		).setBackURL(
			_themeDisplay.getURLCurrent()
		).buildString();
	}

	public JSONArray getFDSCellRendererCETsJSONArray() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return JSONUtil.toJSONArray(
			_cetManager.getCETs(
				themeDisplay.getCompanyId(), null,
				ClientExtensionEntryConstants.TYPE_FDS_CELL_RENDERER,
				Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS), null),
			fdsCellRendererCET -> JSONUtil.put(
				"externalReferenceCode",
				fdsCellRendererCET.getExternalReferenceCode()
			).put(
				"name", fdsCellRendererCET.getName(themeDisplay.getLocale())
			));
	}

	public String getFDSEntriesURL() {
		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_renderRequest, FDSAdminPortletKeys.FDS_ADMIN,
				RenderRequest.RENDER_PHASE)
		).setMVCPath(
			"/data_sets.jsp"
		).buildString();
	}

	public String getFDSEntryPermissionsURL() {
		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_renderRequest,
				"com_liferay_portlet_configuration_web_portlet_" +
					"PortletConfigurationPortlet",
				ActionRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_permissions.jsp"
		).setRedirect(
			PortletURLUtil.getCurrent(_renderRequest, _renderResponse)
		).setParameter(
			"modelResource", _fdsEntryObjectDefinition.getClassName()
		).setParameter(
			"modelResourceDescription",
			_fdsEntryObjectDefinition.getLabel(_themeDisplay.getLocale())
		).setParameter(
			"resourcePrimKey", "{id}"
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public JSONArray getFDSFilterCETsJSONArray() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return JSONUtil.toJSONArray(
			_cetManager.getCETs(
				themeDisplay.getCompanyId(), null,
				ClientExtensionEntryConstants.TYPE_FDS_FILTER,
				Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS), null),
			fdsFilterCET -> JSONUtil.put(
				"externalReferenceCode", fdsFilterCET.getExternalReferenceCode()
			).put(
				"name", fdsFilterCET.getName(themeDisplay.getLocale())
			));
	}

	public String getFDSViewPermissionsURL() {
		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_renderRequest,
				"com_liferay_portlet_configuration_web_portlet_" +
					"PortletConfigurationPortlet",
				ActionRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_permissions.jsp"
		).setRedirect(
			PortletURLUtil.getCurrent(_renderRequest, _renderResponse)
		).setParameter(
			"modelResource", _fdsViewObjectDefinition.getClassName()
		).setParameter(
			"modelResourceDescription",
			_fdsViewObjectDefinition.getLabel(_themeDisplay.getLocale())
		).setParameter(
			"resourcePrimKey", "{id}"
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public String getFDSViewsURL() {
		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_renderRequest, FDSAdminPortletKeys.FDS_ADMIN,
				RenderRequest.RENDER_PHASE)
		).setMVCPath(
			"/fds_views.jsp"
		).buildString();
	}

	public String getFDSViewsURL(String fdsEntryId, String fdsEntryLabel) {
		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_renderRequest, FDSAdminPortletKeys.FDS_ADMIN,
				RenderRequest.RENDER_PHASE)
		).setMVCPath(
			"/fds_views.jsp"
		).setParameter(
			"fdsEntryId", fdsEntryId
		).setParameter(
			"fdsEntryLabel", fdsEntryLabel
		).buildString();
	}

	public String getFDSViewURL() {
		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_renderRequest, FDSAdminPortletKeys.FDS_ADMIN,
				RenderRequest.RENDER_PHASE)
		).setMVCPath(
			"/fds_view.jsp"
		).buildString();
	}

	public JSONArray getRESTApplicationResolvedSchemasJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<FDSAPIURLResolver> resolvers =
			_fdsAPIURLResolverRegistry.getResolvers();

		for (FDSAPIURLResolver resolver : resolvers) {
			jsonArray.put(resolver.getSchema());
		}

		return jsonArray;
	}

	public JSONArray getRESTApplicationsJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<FDSAdminPortlet.CompanyScopedOpenAPIResource>
			companyScopedOpenAPIResources = _serviceTrackerList.toList();

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		companyScopedOpenAPIResources = ListUtil.filter(
			companyScopedOpenAPIResources,
			companyScopedOpenAPIResource ->
				companyScopedOpenAPIResource.matches(
					themeDisplay.getCompanyId()));

		Collections.sort(
			companyScopedOpenAPIResources,
			Comparator.comparing(
				FDSAdminPortlet.CompanyScopedOpenAPIResource::
					getOpenAPIResourcePath,
				String::compareTo));

		for (FDSAdminPortlet.CompanyScopedOpenAPIResource
				companyScopedOpenAPIResource : companyScopedOpenAPIResources) {

			jsonArray.put(
				companyScopedOpenAPIResource.getOpenAPIResourcePath());
		}

		return jsonArray;
	}

	public String getSaveFDSFieldsURL() {
		ResourceURL resourceURL =
			(ResourceURL)PortalUtil.getControlPanelPortletURL(
				_renderRequest, _themeDisplay.getScopeGroup(),
				FDSAdminPortletKeys.FDS_ADMIN, 0, 0,
				RenderRequest.RESOURCE_PHASE);

		resourceURL.setResourceID("/frontend_data_set_admin/save_fds_fields");

		return resourceURL.toString();
	}

	public String getSaveFDSSortURL() {
		ResourceURL resourceURL =
			(ResourceURL)PortalUtil.getControlPanelPortletURL(
				_renderRequest, _themeDisplay.getScopeGroup(),
				FDSAdminPortletKeys.FDS_ADMIN, 0, 0,
				RenderRequest.RESOURCE_PHASE);

		resourceURL.setResourceID("/frontend_data_set_admin/save_fds_sort");

		return resourceURL.toString();
	}

	private final CETManager _cetManager;
	private final ObjectDefinition _fdsEntryObjectDefinition;
	private final FDSAPIURLResolverRegistry
		_fdsAPIURLResolverRegistry;
	private final ObjectDefinition _fdsViewObjectDefinition;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ServiceTrackerList
		<FDSAdminPortlet.CompanyScopedOpenAPIResource> _serviceTrackerList;
	private final ThemeDisplay _themeDisplay;

}