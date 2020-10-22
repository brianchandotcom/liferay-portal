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

package com.liferay.depot.web.internal.servlet.taglib;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.depot.web.internal.constants.DepotPortletKeys;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;
import com.liferay.portal.template.react.renderer.ReactRenderer;
import com.liferay.site.util.GroupURLProvider;
import com.liferay.staging.constants.StagingProcessesPortletKeys;
import com.liferay.taglib.util.HtmlTopTag;

import java.io.IOException;
import java.io.Writer;

import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = DynamicInclude.class)
public class StagingIndicatorDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			Group scopeGroup = themeDisplay.getScopeGroup();

			if (scopeGroup.isDepot() && scopeGroup.isStaged() &&
				_portletPermission.contains(
					themeDisplay.getPermissionChecker(),
					StagingProcessesPortletKeys.STAGING_PROCESSES,
					ActionKeys.VIEW)) {

				_includeStagingIndicator(
					httpServletRequest, httpServletResponse, themeDisplay);
			}
		}
		catch (JspException jspException) {
			ReflectionUtil.throwException(jspException);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"com.liferay.product.navigation.taglib#/page.jsp#pre");
	}

	private <T> JSONArray _createJSONArray(T... values) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (T value : values) {
			jsonArray.put(value);
		}

		return jsonArray;
	}

	private String _getDepotDashboardGroupURL(
			Group group, HttpServletRequest httpServletRequest)
		throws PortalException {

		if (_depotEntryLocalService == null) {
			return null;
		}

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest, group, DepotPortletKeys.DEPOT_ADMIN, 0, 0,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/depot/view_depot_dashboard");

		DepotEntry depotEntry = _depotEntryLocalService.getGroupDepotEntry(
			group.getGroupId());

		portletURL.setParameter(
			"depotEntryId", String.valueOf(depotEntry.getDepotEntryId()));

		return portletURL.toString();
	}

	private String _getLiveGroupURL(
			Group group, HttpServletRequest httpServletRequest)
		throws PortalException {

		if (group.isStagedRemotely()) {
			return _staging.getRemoteSiteURL(group, false);
		}
		else if (group.isStagingGroup()) {
			Group liveGroup = _staging.getLiveGroup(group.getGroupId());

			if (liveGroup != null) {
				return _groupURLProvider.getLiveGroupURL(
					liveGroup,
					(PortletRequest)httpServletRequest.getAttribute(
						JavaConstants.JAVAX_PORTLET_REQUEST));
			}
		}

		return null;
	}

	private String _getLiveKey(Group group) {
		if (group.isStagedRemotely()) {
			return "remote-live";
		}

		return "live";
	}

	private String _getPublishToLiveURL(
		Group group, HttpServletRequest httpServletRequest) {

		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			httpServletRequest, StagingProcessesPortletKeys.STAGING_PROCESSES,
			PortletRequest.RENDER_PHASE);

		liferayPortletURL.setParameter(
			"mvcRenderCommandName", "publishLayouts");

		String cmd = Constants.PUBLISH_TO_LIVE;

		if (group.isStagedRemotely()) {
			cmd = Constants.PUBLISH_TO_REMOTE;
		}

		liferayPortletURL.setParameter(Constants.CMD, cmd);

		liferayPortletURL.setParameter(
			"groupId", String.valueOf(group.getGroupId()));

		return liferayPortletURL.toString();
	}

	private Map<String, Object> _getReactData(
			HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay)
		throws PortalException {

		Group scopeGroup = themeDisplay.getScopeGroup();

		String liveGroupURL = _getLiveGroupURL(scopeGroup, httpServletRequest);

		if (Validator.isNotNull(liveGroupURL)) {
			return HashMapBuilder.<String, Object>put(
				"iconClass", "staging-indicator-icon-staging"
			).put(
				"iconName", "radio-button"
			).put(
				"items",
				_createJSONArray(
					JSONUtil.put(
						"href", liveGroupURL
					).put(
						"label",
						_language.get(
							httpServletRequest, _getLiveKey(scopeGroup))
					).put(
						"symbolLeft", "radio-button"
					),
					JSONUtil.put(
						"href",
						_getPublishToLiveURL(scopeGroup, httpServletRequest)
					).put(
						"label",
						_language.get(httpServletRequest, "publish-to-live")
					).put(
						"symbolLeft", "cards2"
					))
			).put(
				"title", _language.get(httpServletRequest, "staging")
			).build();
		}

		return HashMapBuilder.<String, Object>put(
			"iconClass", "staging-indicator-icon-live"
		).put(
			"iconName", "simple-circle"
		).put(
			"items",
			_createJSONArray(
				JSONUtil.put(
					"href",
					_getDepotDashboardGroupURL(
						scopeGroup.getStagingGroup(), httpServletRequest)
				).put(
					"label", _language.get(httpServletRequest, "staging")
				).put(
					"symbolLeft", "simple-circle"
				))
		).put(
			"title", _language.get(httpServletRequest, "live")
		).build();
	}

	private void _includeStagingIndicator(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, ThemeDisplay themeDisplay)
		throws IOException, JspException, PortalException {

		Writer writer = httpServletResponse.getWriter();

		HtmlTopTag htmlTopTag = new HtmlTopTag();

		htmlTopTag.setOutputKey("staging_indicator_css");
		htmlTopTag.setPosition("auto");

		htmlTopTag.doBodyTag(
			httpServletRequest, httpServletResponse,
			pageContext -> {
				try {
					writer.write("<link href=\"");
					writer.write(
						_portal.getStaticResourceURL(
							httpServletRequest,
							_servletContext.getContextPath() +
								"/dynamic_include/StagingIndicator.css"));
					writer.write("\" rel=\"stylesheet\" type=\"text/css\" />");
				}
				catch (IOException ioException) {
					ReflectionUtil.throwException(ioException);
				}
			});

		writer.write(
			"<div class=\"staging-indicator\"><div>" +
				"<button class=\"staging-indicator-button\">" +
					"<span className=\"staging-indicator-title\">");

		Group scopeGroup = themeDisplay.getScopeGroup();

		if (scopeGroup.isStagingGroup()) {
			writer.write(_language.get(httpServletRequest, "staging"));
		}
		else {
			writer.write(_language.get(httpServletRequest, "live"));
		}

		writer.write("</span></button></div>");

		String componentId =
			_portal.getPortletNamespace(DepotPortletKeys.DEPOT_ADMIN) +
				"IndicatorComponent";
		String module =
			_npmResolver.resolveModuleName("depot-web") +
				"/dynamic_include/StagingIndicator";

		_reactRenderer.renderReact(
			new ComponentDescriptor(module, componentId),
			_getReactData(httpServletRequest, themeDisplay), httpServletRequest,
			writer);

		writer.write("</div>");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagingIndicatorDynamicInclude.class);

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupURLProvider _groupURLProvider;

	@Reference
	private Language _language;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPermission _portletPermission;

	@Reference
	private ReactRenderer _reactRenderer;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.depot.web)")
	private ServletContext _servletContext;

	@Reference
	private Staging _staging;

}