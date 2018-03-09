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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.template.soy.utils.SoyContext;

import java.util.ArrayList;
import java.util.List;

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

	public SoyContext getEditorContext() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		SoyContext editorContext = new SoyContext();

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
			"fragmentCollections", _getSoyContextFragmentCollections());
		editorContext.put(
			"fragmentEntryLinks", _getSoyContextFragmentEntryLinks());
		editorContext.put("portletNamespace", _renderResponse.getNamespace());
		editorContext.put(
			"renderFragmentEntryURL",
			_getFragmentEntryActionURL("/layout/render_fragment_entry"));
		editorContext.put(
			"spritemap",
			themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");
		editorContext.put(
			"updateFragmentEntryLinksURL",
			_getFragmentEntryActionURL("/layout/update_fragment_entry_links"));

		return editorContext;
	}

	private String _getFragmentEntryActionURL(String action) {
		PortletURL actionURL = _renderResponse.createActionURL();

		actionURL.setParameter(ActionRequest.ACTION_NAME, action);

		return actionURL.toString();
	}

	private List<SoyContext> _getSoyContextFragmentCollections() {
		List<SoyContext> soyContextFragmentCollections = new ArrayList();

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

			SoyContext fragmentCollectionSoyContext = new SoyContext();

			fragmentCollectionSoyContext.put(
				"fragmentCollectionId",
				fragmentCollection.getFragmentCollectionId());

			List<SoyContext> soyContextFragmentEntries = new ArrayList<>();

			for (FragmentEntry fragmentEntry : fragmentEntries) {
				SoyContext fragmentEntrySoyContext = new SoyContext();

				fragmentEntrySoyContext.put(
					"fragmentEntryId", fragmentEntry.getFragmentEntryId());
				fragmentEntrySoyContext.put(
					"imagePreviewURL",
					fragmentEntry.getImagePreviewURL(themeDisplay));
				fragmentEntrySoyContext.put("name", fragmentEntry.getName());

				soyContextFragmentEntries.add(fragmentEntrySoyContext);
			}

			fragmentCollectionSoyContext.put(
				"fragmentEntries", soyContextFragmentEntries);

			fragmentCollectionSoyContext.put(
				"name", fragmentCollection.getName());

			soyContextFragmentCollections.add(fragmentCollectionSoyContext);
		}

		return soyContextFragmentCollections;
	}

	private List<SoyContext> _getSoyContextFragmentEntryLinks()
		throws PortalException {

		List<SoyContext> soyContextFragmentEntryLinks = new ArrayList<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<FragmentEntryLink> fragmentEntryLinks =
			FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
				themeDisplay.getScopeGroupId(), _classNameId, _classPK);

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			FragmentEntry fragmentEntry =
				FragmentEntryServiceUtil.fetchFragmentEntry(
					fragmentEntryLink.getFragmentEntryId());

			SoyContext soyContext = new SoyContext();

			soyContext.putHTML(
				"content",
				FragmentEntryRenderUtil.renderFragmentEntryLink(
					fragmentEntryLink));
			soyContext.put(
				"editableValues",
				JSONFactoryUtil.createJSONObject(
					fragmentEntryLink.getEditableValues()));
			soyContext.put(
				"fragmentEntryId", fragmentEntry.getFragmentEntryId());
			soyContext.put(
				"fragmentEntryLinkId",
				fragmentEntryLink.getFragmentEntryLinkId());
			soyContext.put("name", fragmentEntry.getName());
			soyContext.put("position", fragmentEntryLink.getPosition());

			soyContextFragmentEntryLinks.add(soyContext);
		}

		return soyContextFragmentEntryLinks;
	}

	private final long _classNameId;
	private final long _classPK;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}