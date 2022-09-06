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
import com.liferay.layout.content.page.editor.web.internal.util.FragmentManager;
import com.liferay.layout.content.page.editor.web.internal.util.PortletCategoryManager;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.util.structure.DropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.util.List;

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
		"mvc.command.name=/layout_content_page_editor/update_fragment_portlet_sets_sort_configuration"
	},
	service = MVCActionCommand.class
)
public class UpdateFragmentPortletSetsSortConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse,
			_updateFragmentPortletSetsSortConfiguration(actionRequest));
	}

	private DropZoneLayoutStructureItem _getMasterDropZoneLayoutStructureItem(
		ThemeDisplay themeDisplay) {

		Layout layout = themeDisplay.getLayout();

		if (layout.getMasterLayoutPlid() <= 0) {
			return null;
		}

		try {
			LayoutStructure masterLayoutStructure =
				LayoutStructureUtil.getLayoutStructure(
					layout.getGroupId(), layout.getMasterLayoutPlid(),
					SegmentsExperienceConstants.KEY_DEFAULT);

			LayoutStructureItem layoutStructureItem =
				masterLayoutStructure.getDropZoneLayoutStructureItem();

			if (layoutStructureItem == null) {
				return null;
			}

			return (DropZoneLayoutStructureItem)layoutStructureItem;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get master layout structure", exception);
			}
		}

		return null;
	}

	private Object _updateFragmentPortletSetsSortConfiguration(
			ActionRequest actionRequest)
		throws Exception {

		String fragmentCollectionKeys = ParamUtil.getString(
			actionRequest, "fragmentCollectionKeys");

		String portletCategoryKeys = ParamUtil.getString(
			actionRequest, "portletCategoryKeys");

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		if (Validator.isNull(fragmentCollectionKeys) &&
			Validator.isNull(portletCategoryKeys)) {

			hideDefaultSuccessMessage(actionRequest);

			return JSONUtil.put(
				"error",
				_language.get(
					httpServletRequest, "an-unexpected-error-occurred"));
		}

		JSONArray fragmentCollectionKeysJSONArray = null;
		JSONArray portletCategoryKeysJSONArray = null;

		try {
			if (Validator.isNotNull(fragmentCollectionKeys)) {
				fragmentCollectionKeysJSONArray =
					JSONFactoryUtil.createJSONArray(fragmentCollectionKeys);
			}

			if (Validator.isNotNull(portletCategoryKeys)) {
				portletCategoryKeysJSONArray = JSONFactoryUtil.createJSONArray(
					portletCategoryKeys);
			}
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			hideDefaultSuccessMessage(actionRequest);

			return JSONUtil.put(
				"error",
				_language.get(
					httpServletRequest, "an-unexpected-error-occurred"));
		}

		PortalPreferences portalPreferences =
			_portletPreferencesFactory.getPortalPreferences(httpServletRequest);

		if (fragmentCollectionKeysJSONArray != null) {
			List<String> sortedFragmentCollectionKeys = JSONUtil.toStringList(
				fragmentCollectionKeysJSONArray);

			portalPreferences.setValues(
				ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
				"sortedFragmentCollectionKeys",
				sortedFragmentCollectionKeys.toArray(new String[0]));
		}

		if (portletCategoryKeysJSONArray != null) {
			List<String> sortedPortletCategoryKeys = JSONUtil.toStringList(
				portletCategoryKeysJSONArray);

			portalPreferences.setValues(
				ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
				"sortedPortletCategoryKeys",
				sortedPortletCategoryKeys.toArray(new String[0]));
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return JSONUtil.put(
			"fragmentCollections",
			_fragmentManager.getFragmentCollectionMapsList(
				themeDisplay.getScopeGroupId(), httpServletRequest, false, true,
				_getMasterDropZoneLayoutStructureItem(themeDisplay),
				themeDisplay)
		).put(
			"portletCategories",
			_portletCategoryManager.getPortletsJSONArray(
				httpServletRequest, themeDisplay)
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateFragmentPortletSetsSortConfigurationMVCActionCommand.class);

	@Reference
	private FragmentManager _fragmentManager;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private PortletCategoryManager _portletCategoryManager;

	@Reference
	private PortletPreferencesFactory _portletPreferencesFactory;

}