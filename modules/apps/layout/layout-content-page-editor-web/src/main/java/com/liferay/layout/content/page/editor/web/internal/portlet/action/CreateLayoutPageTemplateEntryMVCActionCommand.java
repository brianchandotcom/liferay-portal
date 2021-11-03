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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.page.template.exception.LayoutPageTemplateEntryNameException;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsEntryConstants;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/create_layout_page_template_entry"
	},
	service = MVCActionCommand.class
)
public class CreateLayoutPageTemplateEntryMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long layoutPageTemplateCollectionId = ParamUtil.getLong(
			actionRequest, "layoutPageTemplateCollectionId");
		String name = ParamUtil.getString(actionRequest, "name");
		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId",
			SegmentsEntryConstants.ID_DEFAULT);
		long sourcePlid = themeDisplay.getPlid();

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			LayoutPageTemplateEntry.class.getName(), actionRequest);

		Layout sourceLayout = _layoutLocalService.getLayout(sourcePlid);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryService.
					createLayoutPageTemplateEntryFromLayout(
						segmentsExperienceId, sourceLayout, name,
						layoutPageTemplateCollectionId, serviceContext);

			Layout layout = _layoutLocalService.getLayout(
				layoutPageTemplateEntry.getPlid());

			String url = _http.setParameter(
				_portal.getLayoutFullURL(
					layout.fetchDraftLayout(), themeDisplay),
				"p_l_back_url",
				_getBackURL(actionRequest, sourceLayout, themeDisplay));

			url = _http.addParameter(url, "p_l_mode", Constants.EDIT);

			jsonObject.put("url", url);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			String errorMessage = null;

			if (exception instanceof
					LayoutPageTemplateEntryNameException.MustNotBeDuplicate) {

				errorMessage = LanguageUtil.get(
					themeDisplay.getLocale(),
					"a-page-template-entry-with-that-name-already-exists");
			}
			else if (exception instanceof
						LayoutPageTemplateEntryNameException.MustNotBeNull) {

				errorMessage = LanguageUtil.get(
					themeDisplay.getLocale(), "name-must-not-be-empty");
			}
			else if (exception instanceof
						LayoutPageTemplateEntryNameException.
							MustNotContainInvalidCharacters) {

				LayoutPageTemplateEntryNameException.
					MustNotContainInvalidCharacters lptene =
						(LayoutPageTemplateEntryNameException.
							MustNotContainInvalidCharacters)exception;

				errorMessage = LanguageUtil.format(
					themeDisplay.getLocale(),
					"name-cannot-contain-the-following-invalid-character-x",
					lptene.character);
			}
			else if (exception instanceof
						LayoutPageTemplateEntryNameException.
							MustNotExceedMaximumSize) {

				int nameMaxLength = ModelHintsUtil.getMaxLength(
					LayoutPageTemplateEntry.class.getName(), "name");

				errorMessage = LanguageUtil.format(
					themeDisplay.getLocale(),
					"please-enter-a-name-with-fewer-than-x-characters",
					nameMaxLength);
			}

			if (Validator.isNull(errorMessage)) {
				errorMessage = LanguageUtil.get(
					themeDisplay.getLocale(), "an-unexpected-error-occurred");
			}

			jsonObject.put("error", errorMessage);
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private String _getBackURL(
			ActionRequest actionRequest, Layout layout,
			ThemeDisplay themeDisplay)
		throws PortalException {

		String url = _portal.getLayoutFullURL(layout, themeDisplay);

		url = _http.addParameter(url, "p_l_mode", Constants.EDIT);

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			actionRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		return _http.addParameter(url, "p_l_back_url", portletURL.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CreateLayoutPageTemplateEntryMVCActionCommand.class);

	@Reference
	private Http _http;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private Portal _portal;

}