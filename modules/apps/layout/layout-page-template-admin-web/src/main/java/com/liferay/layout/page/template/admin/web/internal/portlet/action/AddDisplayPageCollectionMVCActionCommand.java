/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.portlet.action;

import com.liferay.layout.page.template.admin.constants.LayoutPageTemplateAdminPortletKeys;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.exception.DuplicateLayoutPageTemplateCollectionException;
import com.liferay.layout.page.template.exception.LayoutPageTemplateCollectionNameException;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yurena Cabrera
 */
@Component(
	property = {
		"javax.portlet.name=" + LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES,
		"mvc.command.name=/layout_page_template_admin/add_display_page_collection"
	},
	service = MVCActionCommand.class
)
public class AddDisplayPageCollectionMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					themeDisplay.getScopeGroupId(),
					ParamUtil.getLong(
						actionRequest, "layoutPageTemplateCollectionId",
						LayoutPageTemplateConstants.
							PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT),
					ParamUtil.getString(actionRequest, "name"),
					ParamUtil.getString(actionRequest, "description"),
					LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE,
					ServiceContextFactory.getInstance(actionRequest));

			jsonObject.put(
				"redirectURL", ParamUtil.getString(actionRequest, "redirect"));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			if (exception instanceof
					DuplicateLayoutPageTemplateCollectionException) {

				jsonObject.put(
					"error",
					_language.get(
						_portal.getHttpServletRequest(actionRequest),
						"please-enter-a-unique-folder-name"));
			}
			else if (exception instanceof
						LayoutPageTemplateCollectionNameException) {

				jsonObject.put(
					"error",
					_language.get(
						_portal.getHttpServletRequest(actionRequest),
						"please-enter-a-valid-folder-name"));
			}
			else {
				jsonObject.put(
					"error",
					_language.get(
						_portal.getHttpServletRequest(actionRequest),
						"an-unexpected-error-occurred"));
			}

			jsonObject.put("success", false);
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddDisplayPageCollectionMVCActionCommand.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

	@Reference
	private Portal _portal;

}