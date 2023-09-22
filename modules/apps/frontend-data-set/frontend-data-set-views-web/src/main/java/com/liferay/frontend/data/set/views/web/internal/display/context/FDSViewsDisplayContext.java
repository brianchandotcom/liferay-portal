/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.views.web.internal.display.context;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.frontend.data.set.views.web.internal.constants.FDSViewsPortletKeys;
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
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

/**
 * @author Marko Cikos
 */
public class FDSViewsDisplayContext {

	public FDSViewsDisplayContext(
		CETManager cetManager,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		RenderRequest renderRequest, RenderResponse renderResponse,
		ServiceTrackerList<String> serviceTrackerList) {

		_cetManager = cetManager;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_serviceTrackerList = serviceTrackerList;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_fdsEntryObjectDefinition =
			objectDefinitionLocalService.fetchObjectDefinition(
				_themeDisplay.getCompanyId(), "FDSEntry");
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
				_renderRequest, FDSViewsPortletKeys.FDS_VIEWS,
				RenderRequest.RENDER_PHASE)
		).setMVCPath(
			"/fds_entries.jsp"
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

	public String getFDSViewsURL() {
		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_renderRequest, FDSViewsPortletKeys.FDS_VIEWS,
				RenderRequest.RENDER_PHASE)
		).setMVCPath(
			"/fds_views.jsp"
		).buildString();
	}

	public String getFDSViewsURL(String fdsEntryId, String fdsEntryLabel) {
		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_renderRequest, FDSViewsPortletKeys.FDS_VIEWS,
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
				_renderRequest, FDSViewsPortletKeys.FDS_VIEWS,
				RenderRequest.RENDER_PHASE)
		).setMVCPath(
			"/fds_view.jsp"
		).buildString();
	}

	public JSONArray getRESTApplicationsJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<String> restApplications = _serviceTrackerList.toList();

		Collections.sort(restApplications);

		for (String restApplication : restApplications) {
			jsonArray.put(restApplication);
		}

		return jsonArray;
	}

	public String getSaveFDSFieldsURL() {
		ResourceURL resourceURL =
			(ResourceURL)PortalUtil.getControlPanelPortletURL(
				_renderRequest, _themeDisplay.getScopeGroup(),
				FDSViewsPortletKeys.FDS_VIEWS, 0, 0,
				RenderRequest.RESOURCE_PHASE);

		resourceURL.setResourceID("/frontend_data_set_views/save_fds_fields");

		return resourceURL.toString();
	}

	private final CETManager _cetManager;
	private final ObjectDefinition _fdsEntryObjectDefinition;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ServiceTrackerList<String> _serviceTrackerList;
	private final ThemeDisplay _themeDisplay;

}