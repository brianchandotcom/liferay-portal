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

import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/update_fragments_highlighted_configuration"
	},
	service = MVCActionCommand.class
)
public class UpdateFragmentsHighlightedConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse,
			_updateFragmentsHighlightedConfiguration(actionRequest));
	}

	private JSONObject _updateFragmentsHighlightedConfiguration(
			ActionRequest actionRequest)
		throws Exception {

		String fragmentEntryKey = ParamUtil.getString(
			actionRequest, "fragmentEntryKey");

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		if (Validator.isNull(fragmentEntryKey)) {
			hideDefaultSuccessMessage(actionRequest);

			return JSONUtil.put(
				"error",
				_language.get(
					httpServletRequest, "an-unexpected-error-occurred"));
		}

		boolean highlighted = ParamUtil.getBoolean(
			actionRequest, "highlighted");

		PortalPreferences portalPreferences =
			_portletPreferencesFactory.getPortalPreferences(httpServletRequest);

		Set<String> highlightedFragmentEntryKeys = SetUtil.fromArray(
			portalPreferences.getValues(
				ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
				"highlightedFragmentEntryKeys", new String[0]));

		if (highlighted) {
			highlightedFragmentEntryKeys.add(fragmentEntryKey);
		}
		else {
			highlightedFragmentEntryKeys.remove(fragmentEntryKey);
		}

		portalPreferences.setValues(
			ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
			"highlightedFragmentEntryKeys",
			highlightedFragmentEntryKeys.toArray(new String[0]));

		return JSONUtil.put(
			"highlightedFragments", JSONFactoryUtil.createJSONArray());
	}

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPreferencesFactory _portletPreferencesFactory;

}