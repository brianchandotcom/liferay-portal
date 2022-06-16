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

package com.liferay.style.book.web.internal.display.context;

import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.petra.portlet.url.builder.ResourceURLBuilder;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.style.book.constants.StyleBookPortletKeys;
import com.liferay.style.book.web.internal.constants.StyleBookWebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rubén Pulido
 */
public class PreviewFragmentCollectionDisplayContext {

	public PreviewFragmentCollectionDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;

		_fragmentCollectionContributorTracker =
			(FragmentCollectionContributorTracker)
				httpServletRequest.getAttribute(
					StyleBookWebKeys.FRAGMENT_COLLECTION_CONTRIBUTOR_TRACKER);
	}

	public String getFragmentCollectionKey() {
		if (_fragmentCollectionKey != null) {
			return _fragmentCollectionKey;
		}

		_fragmentCollectionKey = ParamUtil.getString(
			_httpServletRequest, "fragmentCollectionKey");

		return _fragmentCollectionKey;
	}

	public JSONArray getFragmentsJSONArray() throws Exception {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.fetchFragmentCollection(
				_getGroupId(), getFragmentCollectionKey());

		List<FragmentEntry> fragmentEntries = new ArrayList<>();

		if (fragmentCollection != null) {
			fragmentEntries = FragmentEntryLocalServiceUtil.getFragmentEntries(
				fragmentCollection.getFragmentCollectionId());
		}

		FragmentCollectionContributor fragmentCollectionContributor =
			_fragmentCollectionContributorTracker.
				getFragmentCollectionContributor(getFragmentCollectionKey());

		if (fragmentCollectionContributor != null) {
			fragmentEntries = fragmentCollectionContributor.getFragmentEntries(
				_httpServletRequest.getLocale());
		}

		for (FragmentEntry fragmentEntry : fragmentEntries) {
			jsonArray.put(
				JSONUtil.put(
					"configuration",
					JSONFactoryUtil.createJSONObject(
						fragmentEntry.getConfiguration())
				).put(
					"fragmentEntryKey", fragmentEntry.getFragmentEntryKey()
				).put(
					"label",
					LanguageUtil.get(
						_httpServletRequest.getLocale(),
						fragmentEntry.getName())
				).put(
					"previewURL",
					_getFragmentEntryRenderURL(
						_getGroupId(), fragmentEntry.getFragmentEntryKey())
				));
		}

		return jsonArray;
	}

	public String getStyleBookPortletNamespace() {
		return StyleBookPortletKeys.STYLE_BOOK;
	}

	private String _getFragmentEntryRenderURL(
		long groupId, String fragmentEntryKey) {

		PortletResponse portletResponse =
			(PortletResponse)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		String url = ResourceURLBuilder.createResourceURL(
			PortalUtil.getLiferayPortletResponse(portletResponse)
		).setResourceID(
			"/style_book/render_fragment_entry_link"
		).buildString();

		String portletNamespace = PortalUtil.getPortletNamespace(
			getStyleBookPortletNamespace());

		url = HttpComponentsUtil.addParameter(
			url, portletNamespace + "groupId", groupId);

		return HttpComponentsUtil.addParameter(
			url, portletNamespace + "fragmentEntryKey", fragmentEntryKey);
	}

	private long _getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = ParamUtil.getLong(_httpServletRequest, "groupId");

		return _groupId;
	}

	private final FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;
	private String _fragmentCollectionKey;
	private Long _groupId;
	private final HttpServletRequest _httpServletRequest;

}