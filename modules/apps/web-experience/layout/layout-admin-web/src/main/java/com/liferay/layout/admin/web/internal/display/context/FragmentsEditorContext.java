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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionServiceUtil;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryServiceUtil;
import com.liferay.fragment.util.FragmentEntryRenderUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class FragmentsEditorContext {

	public FragmentsEditorContext(
		HttpServletRequest request, RenderResponse renderResponse,
		String className, long classPK) {

		_request = request;
		_renderResponse = renderResponse;
		_classPK = classPK;

		_classNameId = PortalUtil.getClassNameId(className);
	}

	public Map<String, Object> getEditorContext() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, Object> editorContext = new HashMap<>();

		editorContext.put(
			"addFragmentEntryLinkURL",
			_getFragmentEntryActionURL("/layout/add_fragment_entry_link"));
		editorContext.put("classNameId", _classNameId);
		editorContext.put("classPK", _classPK);
		editorContext.put(
			"deleteFragmentEntryLinkURL",
			_getFragmentEntryActionURL("/layout/delete_fragment_entry_link"));
		editorContext.put(
			"editFragmentEntryLinkURL",
			_getFragmentEntryActionURL("/layout/edit_fragment_entry_link"));
		editorContext.put(
			"fragmentCollections", _getFragmentCollectionsJSONArray());
		editorContext.put(
			"fragmentEntryLinks", _getFragmentEntryLinksJSONArray());
		editorContext.put("portletNamespace", _renderResponse.getNamespace());
		editorContext.put(
			"renderFragmentEntryURL",
			_getFragmentEntryActionURL("/layout/render_fragment_entry"));
		editorContext.put(
			"spritemap",
			themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");
		editorContext.put(
			"swapFragmentEntryLinksURL",
			_getFragmentEntryActionURL("/layout/swap_fragment_entry_links"));

		return editorContext;
	}

	private JSONArray _getFragmentCollectionsJSONArray() {
		JSONArray fragmentCollectionsJSONArray =
			JSONFactoryUtil.createJSONArray();

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<FragmentCollection> fragmentCollections =
			FragmentCollectionServiceUtil.getFragmentCollections(
				themeDisplay.getScopeGroupId());

		for (FragmentCollection fragmentCollection : fragmentCollections) {
			List<FragmentEntry> fragmentEntries =
				FragmentEntryServiceUtil.getFragmentEntries(
					fragmentCollection.getFragmentCollectionId(),
					WorkflowConstants.STATUS_APPROVED);

			if (ListUtil.isEmpty(fragmentEntries)) {
				continue;
			}

			JSONObject fragmentCollectionJSONObject =
				JSONFactoryUtil.createJSONObject();

			fragmentCollectionJSONObject.put(
				"fragmentCollectionId",
				fragmentCollection.getFragmentCollectionId());

			JSONArray fragmentEntriesJSONArray =
				JSONFactoryUtil.createJSONArray();

			for (FragmentEntry fragmentEntry : fragmentEntries) {
				JSONObject fragmentEntryJSONObject =
					JSONFactoryUtil.createJSONObject();

				fragmentEntryJSONObject.put(
					"fragmentEntryId", fragmentEntry.getFragmentEntryId());
				fragmentEntryJSONObject.put(
					"imagePreviewURL",
					fragmentEntry.getImagePreviewURL(themeDisplay));
				fragmentEntryJSONObject.put("name", fragmentEntry.getName());

				fragmentEntriesJSONArray.put(fragmentEntryJSONObject);
			}

			fragmentCollectionJSONObject.put(
				"fragmentEntries", fragmentEntriesJSONArray);

			fragmentCollectionJSONObject.put(
				"name", fragmentCollection.getName());

			fragmentCollectionsJSONArray.put(fragmentCollectionJSONObject);
		}

		return fragmentCollectionsJSONArray;
	}

	private String _getFragmentEntryActionURL(String action) {
		PortletURL renderFragmentEntryURL = _renderResponse.createActionURL();

		renderFragmentEntryURL.setParameter(ActionRequest.ACTION_NAME, action);

		return renderFragmentEntryURL.toString();
	}

	private JSONArray _getFragmentEntryLinksJSONArray() throws PortalException {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<FragmentEntryLink> fragmentEntryLinks =
			FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
				themeDisplay.getScopeGroupId(), _classNameId, _classPK);

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			FragmentEntry fragmentEntry =
				FragmentEntryServiceUtil.fetchFragmentEntry(
					fragmentEntryLink.getFragmentEntryId());

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put(
				"content",
				FragmentEntryRenderUtil.renderFragmentEntryLink(
					fragmentEntryLink));
			jsonObject.put(
				"editableValues",
				JSONFactoryUtil.createJSONObject(
					fragmentEntryLink.getEditableValues()));
			jsonObject.put(
				"fragmentEntryId", fragmentEntry.getFragmentEntryId());
			jsonObject.put(
				"fragmentEntryLinkId",
				fragmentEntryLink.getFragmentEntryLinkId());
			jsonObject.put("name", fragmentEntry.getName());
			jsonObject.put("position", fragmentEntryLink.getPosition());

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private final long _classNameId;
	private final long _classPK;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}