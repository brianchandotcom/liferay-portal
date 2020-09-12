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

package com.liferay.change.tracking.web.internal.servlet.taglib;

import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;
import com.liferay.portal.template.react.renderer.ReactRenderer;
import com.liferay.taglib.util.HtmlTopTag;

import java.io.IOException;
import java.io.Writer;

import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = DynamicInclude.class)
public class ChangeTrackingIndicatorDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(
				themeDisplay.getCompanyId(), 0);

		try {
			if ((ctPreferences == null) ||
				!_portletPermission.contains(
					themeDisplay.getPermissionChecker(),
					CTPortletKeys.CHANGE_LISTS, ActionKeys.VIEW)) {

				return;
			}
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException, portalException);
			}

			return;
		}

		Writer writer = httpServletResponse.getWriter();

		HtmlTopTag htmlTopTag = new HtmlTopTag();

		htmlTopTag.setOutputKey("change_tracking_indicator_css");
		htmlTopTag.setPosition("auto");

		try {
			htmlTopTag.doBodyTag(
				httpServletRequest, httpServletResponse,
				pageContext -> {
					try {
						writer.write("<link href=\"");
						writer.write(
							_portal.getStaticResourceURL(
								httpServletRequest,
								StringBundler.concat(
									_servletContext.getContextPath(),
									"/dynamic_include/ChangeTrackingIndicator.",
									"css")));
						writer.write(
							"\" rel=\"stylesheet\" type=\"text/css\" />");
					}
					catch (IOException ioException) {
						ReflectionUtil.throwException(ioException);
					}
				});

			writer.write(
				"<div class=\"change-tracking-indicator\"><div>" +
					"<button class=\"change-tracking-indicator-button\">" +
						"<span className=\"change-tracking-indicator-title\">");

			CTCollection ctCollection = null;

			ctPreferences = _ctPreferencesLocalService.fetchCTPreferences(
				themeDisplay.getCompanyId(), themeDisplay.getUserId());

			if (ctPreferences != null) {
				ctCollection = _ctCollectionLocalService.fetchCTCollection(
					ctPreferences.getCtCollectionId());
			}

			if (ctCollection == null) {
				writer.write(
					_language.get(themeDisplay.getLocale(), "production"));
			}
			else {
				writer.write(_html.escape(ctCollection.getName()));
			}

			writer.write("</span></button></div>");

			String componentId =
				_portal.getPortletNamespace(CTPortletKeys.CHANGE_LISTS) +
					"IndicatorComponent";
			String module =
				_npmResolver.resolveModuleName("change-tracking-web") +
					"/dynamic_include/ChangeTrackingIndicator";

			_reactRenderer.renderReact(
				new ComponentDescriptor(module, componentId),
				_getReactData(
					httpServletRequest, ctCollection, ctPreferences,
					themeDisplay),
				httpServletRequest, writer);

			writer.write("</div>");
		}
		catch (JspException | PortalException exception) {
			ReflectionUtil.throwException(exception);
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"com.liferay.product.navigation.taglib#/page.jsp#pre");
	}

	private Map<String, Object> _getReactData(
			HttpServletRequest httpServletRequest, CTCollection ctCollection,
			CTPreferences ctPreferences, ThemeDisplay themeDisplay)
		throws PortalException {

		PortletURL checkoutURL = _portal.getControlPanelPortletURL(
			httpServletRequest, themeDisplay.getScopeGroup(),
			CTPortletKeys.CHANGE_LISTS, 0, 0, PortletRequest.ACTION_PHASE);

		checkoutURL.setParameter(
			ActionRequest.ACTION_NAME, "/change_lists/checkout_ct_collection");
		checkoutURL.setParameter(
			"redirect", _portal.getCurrentURL(httpServletRequest));

		PortletURL selectURL = _portal.getControlPanelPortletURL(
			httpServletRequest, themeDisplay.getScopeGroup(),
			CTPortletKeys.CHANGE_LISTS, 0, 0, PortletRequest.RENDER_PHASE);

		selectURL.setParameter(
			"mvcPath", "/change_lists/select_change_list.jsp");

		try {
			selectURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			ReflectionUtil.throwException(windowStateException);
		}

		Map<String, Object> data = HashMapBuilder.<String, Object>put(
			"checkoutURL", checkoutURL.toString()
		).put(
			"namespace", _portal.getPortletNamespace(CTPortletKeys.CHANGE_LISTS)
		).put(
			"selectURL", selectURL.toString()
		).build();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			themeDisplay.getLocale(),
			ChangeTrackingIndicatorDynamicInclude.class);

		long ctCollectionId = CTConstants.CT_COLLECTION_ID_PRODUCTION;

		if (ctCollection != null) {
			ctCollectionId = ctCollection.getCtCollectionId();
		}

		if (ctCollection == null) {
			data.put("iconClass", "change-tracking-indicator-icon-production");
			data.put("iconName", "simple-circle");
			data.put("title", _language.get(resourceBundle, "production"));
		}
		else {
			data.put("iconClass", "change-tracking-indicator-icon-change-list");
			data.put("iconName", "radio-button");
			data.put("title", ctCollection.getName());
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (ctPreferences != null) {
			if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
				long previousCtCollectionId =
					ctPreferences.getPreviousCtCollectionId();

				CTCollection previousCtCollection =
					_ctCollectionLocalService.fetchCTCollection(
						previousCtCollectionId);

				if (previousCtCollection != null) {
					checkoutURL.setParameter(
						"ctCollectionId",
						String.valueOf(previousCtCollectionId));

					jsonArray.put(
						JSONUtil.put(
							"href", checkoutURL.toString()
						).put(
							"label",
							_language.format(
								resourceBundle, "work-on-x",
								previousCtCollection.getName(), false)
						).put(
							"symbolLeft", "radio-button"
						));
				}
			}
			else {
				checkoutURL.setParameter(
					"ctCollectionId",
					String.valueOf(CTConstants.CT_COLLECTION_ID_PRODUCTION));

				jsonArray.put(
					JSONUtil.put(
						"href", checkoutURL.toString()
					).put(
						"label",
						_language.get(resourceBundle, "work-on-production")
					).put(
						"symbolLeft", "simple-circle"
					));
			}
		}

		jsonArray.put(
			JSONUtil.put(
				"href",
				StringBundler.concat(
					"javascript:Liferay.fire('",
					_portal.getPortletNamespace(CTPortletKeys.CHANGE_LISTS),
					"openDialog', {}); void(0);")
			).put(
				"label", _language.get(resourceBundle, "select-a-publication")
			).put(
				"symbolLeft", "cards2"
			));

		PortletURL addURL = _portal.getControlPanelPortletURL(
			httpServletRequest, themeDisplay.getScopeGroup(),
			CTPortletKeys.CHANGE_LISTS, 0, 0, PortletRequest.RENDER_PHASE);

		addURL.setParameter(
			"mvcRenderCommandName", "/change_lists/add_ct_collection");

		PortletURL backURL = _portal.getControlPanelPortletURL(
			httpServletRequest, themeDisplay.getScopeGroup(),
			CTPortletKeys.CHANGE_LISTS, 0, 0, PortletRequest.RENDER_PHASE);

		addURL.setParameter("redirect", backURL.toString());

		jsonArray.put(
			JSONUtil.put(
				"href", addURL.toString()
			).put(
				"label", _language.get(resourceBundle, "create-new-publication")
			).put(
				"symbolLeft", "plus"
			));

		if (ctCollection != null) {
			PortletURL reviewURL = _portal.getControlPanelPortletURL(
				httpServletRequest, themeDisplay.getScopeGroup(),
				CTPortletKeys.CHANGE_LISTS, 0, 0, PortletRequest.RENDER_PHASE);

			reviewURL.setParameter(
				"mvcRenderCommandName", "/change_lists/view_changes");
			reviewURL.setParameter("backURL", backURL.toString());
			reviewURL.setParameter(
				"ctCollectionId", String.valueOf(ctCollectionId));

			jsonArray.put(
				JSONUtil.put("type", "divider")
			).put(
				JSONUtil.put(
					"href", reviewURL.toString()
				).put(
					"label", _language.get(resourceBundle, "review-changes")
				).put(
					"symbolLeft", "list-ul"
				)
			);

			int count = _ctEntryLocalService.getCTCollectionCTEntriesCount(
				ctCollection.getCtCollectionId());

			if ((count > 0) &&
				_ctCollectionModelResourcePermission.contains(
					themeDisplay.getPermissionChecker(), ctCollection,
					CTActionKeys.PUBLISH)) {

				jsonArray.put(JSONUtil.put("type", "divider"));

				PortletURL publishURL = _portal.getControlPanelPortletURL(
					httpServletRequest, themeDisplay.getScopeGroup(),
					CTPortletKeys.CHANGE_LISTS, 0, 0,
					PortletRequest.RENDER_PHASE);

				publishURL.setParameter(
					"mvcRenderCommandName", "/change_lists/view_conflicts");
				publishURL.setParameter(
					"ctCollectionId",
					String.valueOf(ctCollection.getCtCollectionId()));

				jsonArray.put(
					JSONUtil.put(
						"href", publishURL.toString()
					).put(
						"label", _language.get(resourceBundle, "publish")
					).put(
						"symbolLeft", "change"
					));

				publishURL.setParameter(
					"ctCollectionId", Boolean.TRUE.toString());

				jsonArray.put(
					JSONUtil.put(
						"href", publishURL.toString()
					).put(
						"label", _language.get(resourceBundle, "schedule")
					).put(
						"symbolLeft", "calendar"
					));
			}
		}

		data.put("items", jsonArray);

		return data;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ChangeTrackingIndicatorDynamicInclude.class);

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.change.tracking.model.CTCollection)"
	)
	private ModelResourcePermission<CTCollection>
		_ctCollectionModelResourcePermission;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private Html _html;

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

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.change.tracking.web)"
	)
	private ServletContext _servletContext;

}