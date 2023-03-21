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
import com.liferay.change.tracking.spi.constants.CTTimelineKeys;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.change.tracking.spi.history.CTCollectionHistoryProvider;
import com.liferay.change.tracking.web.internal.configuration.helper.CTSettingsConfigurationHelper;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.change.tracking.web.internal.security.permission.resource.CTCollectionPermission;
import com.liferay.change.tracking.web.internal.security.permission.resource.CTPermission;
import com.liferay.change.tracking.web.internal.timeline.CTCollectionHistoryProviderRegistry;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.template.react.renderer.ComponentDescriptor;
import com.liferay.portal.template.react.renderer.ReactRenderer;
import com.liferay.taglib.util.HtmlTopTag;

import java.io.IOException;
import java.io.Writer;

import java.text.Format;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;

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

		try {
			if (!_ctSettingsConfigurationHelper.isEnabled(
					themeDisplay.getCompanyId()) ||
				!_portletPermission.contains(
					themeDisplay.getPermissionChecker(),
					CTPortletKeys.PUBLICATIONS, ActionKeys.VIEW)) {

				return;
			}
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
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
									"/publications/css",
									"/ChangeTrackingIndicator.css")));
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

			CTPreferences ctPreferences =
				_ctPreferencesLocalService.fetchCTPreferences(
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
				_portal.getPortletNamespace(CTPortletKeys.PUBLICATIONS) +
					"IndicatorComponent";
			String module =
				_npmResolver.resolveModuleName("change-tracking-web") +
					"/publications/js/components/ChangeTrackingIndicator";

			_reactRenderer.renderReact(
				new ComponentDescriptor(module, componentId),
				_getReactData(
					httpServletRequest, ctCollection, ctPreferences,
					_ctSettingsConfigurationHelper.isSandboxEnabled(
						themeDisplay.getCompanyId()),
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

	private String _getDeleteHref(
		HttpServletRequest httpServletRequest, String redirect,
		long ctCollectionId, ThemeDisplay themeDisplay) {

		return StringBundler.concat(
			"javascript:Liferay.Util.openConfirmModal({message: '",
			_language.get(
				httpServletRequest,
				"are-you-sure-you-want-to-delete-this-publication"),
			"', onConfirm: (isConfirmed) => {if (isConfirmed) {",
			"submitForm(document.hrefFm, '",
			PortletURLBuilder.create(
				_portal.getControlPanelPortletURL(
					httpServletRequest, themeDisplay.getScopeGroup(),
					CTPortletKeys.PUBLICATIONS, 0, 0,
					PortletRequest.ACTION_PHASE)
			).setActionName(
				"/change_tracking/delete_ct_collection"
			).setRedirect(
				redirect
			).setParameter(
				"ctCollectionId", ctCollectionId
			).buildString(),
			"');} else {self.focus();}}});");
	}

	private Map<String, Object> _getReactData(
			HttpServletRequest httpServletRequest, CTCollection ctCollection,
			CTPreferences ctPreferences, boolean sandboxOnlyEnabled,
			ThemeDisplay themeDisplay)
		throws PortalException {

		PortletURL checkoutURL = PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, themeDisplay.getScopeGroup(),
				CTPortletKeys.PUBLICATIONS, 0, 0, PortletRequest.ACTION_PHASE)
		).setActionName(
			"/change_tracking/checkout_ct_collection"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).buildPortletURL();

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				httpServletRequest);

		Map<String, Object> data = HashMapBuilder.<String, Object>put(
			"getSelectPublicationsURL",
			() -> {
				ResourceURL getSelectPublicationsURL =
					(ResourceURL)_portal.getControlPanelPortletURL(
						httpServletRequest, themeDisplay.getScopeGroup(),
						CTPortletKeys.PUBLICATIONS, 0, 0,
						PortletRequest.RESOURCE_PHASE);

				getSelectPublicationsURL.setResourceID(
					"/change_tracking/get_select_publications");

				return getSelectPublicationsURL.toString();
			}
		).put(
			"orderByAscending",
			portalPreferences.getValue(
				CTPortletKeys.PUBLICATIONS, "select-order-by-ascending")
		).put(
			"orderByColumn",
			portalPreferences.getValue(
				CTPortletKeys.PUBLICATIONS, "select-order-by-column")
		).put(
			"preferencesPrefix", "select"
		).put(
			"saveDisplayPreferenceURL",
			() -> {
				ResourceURL saveDisplayPreferenceURL =
					(ResourceURL)_portal.getControlPanelPortletURL(
						httpServletRequest, themeDisplay.getScopeGroup(),
						CTPortletKeys.PUBLICATIONS, 0, 0,
						PortletRequest.RESOURCE_PHASE);

				saveDisplayPreferenceURL.setResourceID(
					"/change_tracking/save_display_preference");

				return saveDisplayPreferenceURL.toString();
			}
		).put(
			"spritemap", themeDisplay.getPathThemeSpritemap()
		).build();

		long ctCollectionId = CTConstants.CT_COLLECTION_ID_PRODUCTION;

		if (ctCollection != null) {
			ctCollectionId = ctCollection.getCtCollectionId();
		}

		if (ctCollection == null) {
			data.put("iconClass", "change-tracking-indicator-icon-production");
			data.put("iconName", "simple-circle");
			data.put(
				"title", _language.get(themeDisplay.getLocale(), "production"));
		}
		else {
			data.put("iconClass", "change-tracking-indicator-icon-publication");
			data.put("iconName", "radio-button");
			data.put("title", ctCollection.getName());
		}

		if (ctPreferences != null) {
			if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
				long previousCtCollectionId =
					ctPreferences.getPreviousCtCollectionId();

				CTCollection previousCTCollection =
					_ctCollectionLocalService.fetchCTCollection(
						previousCtCollectionId);

				if (previousCTCollection != null) {
					checkoutURL.setParameter(
						"ctCollectionId",
						String.valueOf(previousCtCollectionId));

					data.put(
						"checkoutDropdownItem",
						JSONUtil.put(
							"href", checkoutURL.toString()
						).put(
							"label",
							_language.format(
								themeDisplay.getLocale(), "work-on-x",
								previousCTCollection.getName(), false)
						).put(
							"symbolLeft", "radio-button"
						));
				}
			}
			else {
				if (!sandboxOnlyEnabled ||
					_portletPermission.contains(
						themeDisplay.getPermissionChecker(),
						CTPortletKeys.PUBLICATIONS,
						CTActionKeys.WORK_ON_PRODUCTION)) {

					checkoutURL.setParameter(
						"ctCollectionId",
						String.valueOf(
							CTConstants.CT_COLLECTION_ID_PRODUCTION));

					data.put(
						"checkoutDropdownItem",
						JSONUtil.put(
							"confirmationMessage",
							_language.get(
								themeDisplay.getLocale(),
								"any-changes-made-in-production-will-" +
									"immediately-be-live.-continue-to-" +
										"production")
						).put(
							"href", checkoutURL.toString()
						).put(
							"label",
							_language.get(
								themeDisplay.getLocale(), "work-on-production")
						).put(
							"symbolLeft", "simple-circle"
						));
				}
			}
		}

		if (CTPermission.contains(
				themeDisplay.getPermissionChecker(),
				CTActionKeys.ADD_PUBLICATION)) {

			data.put(
				"createDropdownItem",
				JSONUtil.put(
					"href",
					PortletURLBuilder.create(
						_portal.getControlPanelPortletURL(
							httpServletRequest, themeDisplay.getScopeGroup(),
							CTPortletKeys.PUBLICATIONS, 0, 0,
							PortletRequest.RENDER_PHASE)
					).setMVCRenderCommandName(
						"/change_tracking/add_ct_collection"
					).setRedirect(
						themeDisplay.getURLCurrent()
					).buildString()
				).put(
					"label",
					_language.get(
						themeDisplay.getLocale(), "create-new-publication")
				).put(
					"symbolLeft", "plus"
				));
		}

		if (ctCollection != null) {
			data.put(
				"reviewDropdownItem",
				JSONUtil.put(
					"href",
					PortletURLBuilder.create(
						_portal.getControlPanelPortletURL(
							httpServletRequest, themeDisplay.getScopeGroup(),
							CTPortletKeys.PUBLICATIONS, 0, 0,
							PortletRequest.RENDER_PHASE)
					).setMVCRenderCommandName(
						"/change_tracking/view_changes"
					).setParameter(
						"ctCollectionId", ctCollectionId
					).buildString()
				).put(
					"label",
					_language.get(themeDisplay.getLocale(), "review-changes")
				).put(
					"symbolLeft", "list-ul"
				));
		}

		_getTimelineData(ctCollection, data, httpServletRequest, themeDisplay);

		return data;
	}

	private String _getStatusMessage(
		CTCollection ctCollection, HttpServletRequest httpServletRequest) {

		if (ctCollection == null) {
			return StringPool.BLANK;
		}

		if (ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED) {
			Date statusDate = ctCollection.getStatusDate();

			return _language.format(
				httpServletRequest, "published-x-ago-by-x",
				new String[] {
					_language.getTimeDescription(
						httpServletRequest,
						System.currentTimeMillis() - statusDate.getTime(),
						true),
					_html.escape(ctCollection.getUserName())
				});
		}
		else if (ctCollection.getStatus() == WorkflowConstants.STATUS_DRAFT) {
			Date modifiedDate = ctCollection.getModifiedDate();

			return _language.format(
				httpServletRequest, "modified-x-ago-by-x",
				new String[] {
					_language.getTimeDescription(
						httpServletRequest,
						System.currentTimeMillis() - modifiedDate.getTime(),
						true),
					_html.escape(ctCollection.getUserName())
				});
		}
		else if (ctCollection.getStatus() ==
					WorkflowConstants.STATUS_SCHEDULED) {

			try {
				SchedulerResponse schedulerResponse =
					SchedulerEngineHelperUtil.getScheduledJob(
						String.valueOf(ctCollection.getCtCollectionId()),
						"liferay/ct_collection_scheduled_publish",
						StorageType.PERSISTED);

				if (schedulerResponse == null) {
					return null;
				}

				Date scheduledDate = SchedulerEngineHelperUtil.getStartTime(
					schedulerResponse);

				return _language.format(
					httpServletRequest, "schedule-to-publish-in-x-by-x",
					new String[] {
						_language.getTimeDescription(
							httpServletRequest,
							scheduledDate.getTime() -
								System.currentTimeMillis(),
							true),
						_html.escape(ctCollection.getUserName())
					});
			}
			catch (SchedulerException schedulerException) {
				_log.error(schedulerException);
			}
		}

		return StringPool.BLANK;
	}

	private void _getTimelineData(
			CTCollection currentCTCollection, Map<String, Object> data,
			HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay)
		throws PortalException {

		String className = (String)httpServletRequest.getAttribute(
			CTTimelineKeys.CLASS_NAME);

		long classPK = GetterUtil.getLong(
			httpServletRequest.getAttribute(CTTimelineKeys.CLASS_PK));

		if ((className == null) || (classPK == 0)) {
			Layout layout = themeDisplay.getLayout();

			if (!layout.isTypeControlPanel()) {
				className = Layout.class.getName();
				classPK = layout.getPlid();
			}
		}

		if ((className != null) && (classPK != 0)) {
			long classNameId = _portal.getClassNameId(className);

			CTCollectionHistoryProvider<?> ctCollectionHistoryProvider =
				CTCollectionHistoryProviderRegistry.
					getCTCollectionHistoryProvider(classNameId);

			List<CTCollection> ctCollections =
				ctCollectionHistoryProvider.getCTCollections(
					classNameId, classPK);

			JSONArray jsonArray = _jsonFactory.createJSONArray();

			Format format = _fastDateFormatFactory.getDate(
				themeDisplay.getLocale(), themeDisplay.getTimeZone());

			for (CTCollection ctCollection : ctCollections) {
				jsonArray.put(
					JSONUtil.put(
						"date",
						() -> {
							Date date = ctCollection.getStatusDate();

							if (date == null) {
								date = ctCollection.getModifiedDate();
							}

							return format.format(date);
						}
					).put(
						"description", ctCollection.getDescription()
					).put(
						"dropdownMenu",
						_getTimelineDropdownMenuData(
							ctCollection, httpServletRequest, themeDisplay)
					).put(
						"id", ctCollection.getCtCollectionId()
					).put(
						"name", ctCollection.getName()
					).put(
						"status", ctCollection.getStatus()
					).put(
						"statusMessage",
						_getStatusMessage(ctCollection, httpServletRequest)
					));
			}

			data.put("timelineIconClass", "change-tracking-timeline-icon");
			data.put("timelineIconName", "time");
			data.put("timelineItems", jsonArray);

			CTDisplayRenderer<?> ctDisplayRenderer =
				_ctDisplayRendererRegistry.getCTDisplayRenderer(classNameId);

			data.put(
				"timelineType",
				ctDisplayRenderer.getTypeName(themeDisplay.getLocale()));
		}
	}

	private Map<String, Object> _getTimelineDropdownMenuData(
			CTCollection ctCollection, HttpServletRequest httpServletRequest,
			ThemeDisplay themeDisplay)
		throws PortalException {

		Map<String, Object> data = new HashMap<>();

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if ((ctCollection.getStatus() != WorkflowConstants.STATUS_EXPIRED) &&
			CTCollectionPermission.contains(
				permissionChecker, ctCollection, ActionKeys.UPDATE)) {

			data.put(
				"editURL",
				PortletURLBuilder.create(
					_portal.getControlPanelPortletURL(
						httpServletRequest, themeDisplay.getScopeGroup(),
						CTPortletKeys.PUBLICATIONS, 0, 0,
						PortletRequest.RENDER_PHASE)
				).setMVCRenderCommandName(
					"/change_tracking/edit_ct_collection"
				).setRedirect(
					themeDisplay.getURLCurrent()
				).setParameter(
					"ctCollectionId", ctCollection.getCtCollectionId()
				).buildString());

			data.put(
				"revertURL",
				PortletURLBuilder.create(
					_portal.getControlPanelPortletURL(
						httpServletRequest, themeDisplay.getScopeGroup(),
						CTPortletKeys.PUBLICATIONS, 0, 0,
						PortletRequest.RENDER_PHASE)
				).setMVCRenderCommandName(
					"/change_tracking/undo_ct_collection"
				).setRedirect(
					themeDisplay.getURLCurrent()
				).setParameter(
					"ctCollectionId", ctCollection.getCtCollectionId()
				).setParameter(
					"revert", Boolean.TRUE
				).buildString());
		}

		data.put(
			"reviewURL",
			PortletURLBuilder.create(
				_portal.getControlPanelPortletURL(
					httpServletRequest, themeDisplay.getScopeGroup(),
					CTPortletKeys.PUBLICATIONS, 0, 0,
					PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/change_tracking/view_changes"
			).setRedirect(
				themeDisplay.getURLCurrent()
			).setParameter(
				"ctCollectionId", ctCollection.getCtCollectionId()
			).buildString());

		if ((ctCollection.getStatus() != WorkflowConstants.STATUS_APPROVED) &&
			CTCollectionPermission.contains(
				permissionChecker, ctCollection, ActionKeys.DELETE)) {

			data.put(
				"deleteURL",
				_getDeleteHref(
					httpServletRequest, themeDisplay.getURLCurrent(),
					ctCollection.getCtCollectionId(), themeDisplay));
		}

		return data;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ChangeTrackingIndicatorDynamicInclude.class);

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTDisplayRendererRegistry _ctDisplayRendererRegistry;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private CTSettingsConfigurationHelper _ctSettingsConfigurationHelper;

	@Reference
	private FastDateFormatFactory _fastDateFormatFactory;

	@Reference
	private Html _html;

	@Reference
	private JSONFactory _jsonFactory;

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