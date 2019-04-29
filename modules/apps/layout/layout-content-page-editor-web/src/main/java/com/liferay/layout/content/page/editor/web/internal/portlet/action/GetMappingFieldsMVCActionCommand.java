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

import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/get_mapping_fields"
	},
	service = MVCActionCommand.class
)
public class GetMappingFieldsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long classNameId = ParamUtil.getLong(actionRequest, "classNameId");

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		InfoDisplayContributor infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(
				_portal.getClassName(classNameId));

		if (infoDisplayContributor != null) {
			long classTypeId = ParamUtil.getLong(actionRequest, "classTypeId");
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			Set<InfoDisplayField> infoDisplayFields =
				infoDisplayContributor.getInfoDisplayFields(
					classTypeId, themeDisplay.getLocale());

			for (InfoDisplayField infoDisplayField : infoDisplayFields) {
				JSONObject jsonObject = JSONUtil.put(
					"key", infoDisplayField.getKey()
				).put(
					"label", infoDisplayField.getLabel()
				).put(
					"type", infoDisplayField.getType()
				);

				jsonArray.put(jsonObject);
			}
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonArray);
	}

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private Portal _portal;

}