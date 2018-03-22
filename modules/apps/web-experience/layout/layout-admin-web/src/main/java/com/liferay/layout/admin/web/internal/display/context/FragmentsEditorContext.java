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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.editor.configuration.EditorConfiguration;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.template.soy.utils.SoyContext;

import java.util.ArrayList;
import java.util.Collections;
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

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		SoyContext soyContext = new SoyContext();

		soyContext.put(
			"addFragmentEntryLinkURL",
			_getFragmentEntryActionURL("/layout/add_fragment_entry_link"));
		soyContext.put("classNameId", _classNameId);
		soyContext.put("classPK", _classPK);

		EditorConfiguration editorConfiguration =
			EditorConfigurationFactoryUtil.getEditorConfiguration(
				PortletIdCodec.decodePortletName(portletDisplay.getId()),
				"fragmenEntryLinkEditor", StringPool.BLANK,
				Collections.emptyMap(), themeDisplay,
				RequestBackedPortletURLFactoryUtil.create(_request));

		soyContext.put(
			"defaultEditorConfiguration", editorConfiguration.getData());

		soyContext.put(
			"deleteFragmentEntryLinkURL",
			_getFragmentEntryActionURL("/layout/delete_fragment_entry_link"));
		soyContext.put(
			"editFragmentEntryLinkURL",
			_getFragmentEntryActionURL("/layout/edit_fragment_entry_link"));
		soyContext.put(
			"fragmentCollections", _getSoyContextFragmentCollections());
		soyContext.put(
			"fragmentEntryLinks", _getSoyContextFragmentEntryLinks());
		soyContext.put("portletNamespace", _renderResponse.getNamespace());
		soyContext.put(
			"renderFragmentEntryURL",
			_getFragmentEntryActionURL("/layout/render_fragment_entry"));
		soyContext.put(
			"spritemap",
			themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");
		soyContext.put(
			"updateFragmentEntryLinksURL",
			_getFragmentEntryActionURL("/layout/update_fragment_entry_links"));

		return soyContext;
	}

	private List<SoyContext> _getFragmentEntriesSoyContext(
		List<FragmentEntry> fragmentEntries) {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<SoyContext> soyContexts = new ArrayList<>();

		for (FragmentEntry fragmentEntry : fragmentEntries) {
			SoyContext soyContext = new SoyContext();

			soyContext.put(
				"fragmentEntryId", fragmentEntry.getFragmentEntryId());
			soyContext.put(
				"imagePreviewURL",
				fragmentEntry.getImagePreviewURL(themeDisplay));
			soyContext.put("name", fragmentEntry.getName());

			soyContexts.add(soyContext);
		}

		return soyContexts;
	}

	private String _getFragmentEntryActionURL(String action) {
		PortletURL actionURL = _renderResponse.createActionURL();

		actionURL.setParameter(ActionRequest.ACTION_NAME, action);

		return actionURL.toString();
	}

	private List<SoyContext> _getSoyContextFragmentCollections() {
		List<SoyContext> soyContexts = new ArrayList<>();

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

			SoyContext soyContext = new SoyContext();

			soyContext.put(
				"fragmentCollectionId",
				fragmentCollection.getFragmentCollectionId());
			soyContext.put(
				"fragmentEntries",
				_getFragmentEntriesSoyContext(fragmentEntries));
			soyContext.put("name", fragmentCollection.getName());

			soyContexts.add(soyContext);
		}

		return soyContexts;
	}

	private List<SoyContext> _getSoyContextFragmentEntryLinks()
		throws PortalException {

		List<SoyContext> soyContexts = new ArrayList<>();

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
					fragmentEntryLink, _request,
					PortalUtil.getHttpServletResponse(_renderResponse)));
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

			soyContexts.add(soyContext);
		}

		return soyContexts;
	}

	private final long _classNameId;
	private final long _classPK;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}