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

package com.liferay.fragment.web.internal.display.context;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.fragment.web.internal.constants.FragmentWebKeys;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class RenderFragmentEntryDisplayContext {

	public RenderFragmentEntryDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;

		_fragmentCollectionContributorTracker =
			(FragmentCollectionContributorTracker)
				_httpServletRequest.getAttribute(
					FragmentWebKeys.FRAGMENT_COLLECTION_CONTRIBUTOR_TRACKER);
	}

	public DefaultFragmentRendererContext getDefaultFragmentRendererContext() {
		FragmentEntry fragmentEntry = _getFragmentEntry();

		String css = BeanParamUtil.getString(
			fragmentEntry, _httpServletRequest, "css");
		String html = BeanParamUtil.getString(
			fragmentEntry, _httpServletRequest, "html");
		String js = BeanParamUtil.getString(
			fragmentEntry, _httpServletRequest, "js");
		String configuration = BeanParamUtil.getString(
			fragmentEntry, _httpServletRequest, "configuration");

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.createFragmentEntryLink(0);

		long fragmentEntryId = 0;

		if (fragmentEntry != null) {
			fragmentEntryId = fragmentEntry.getFragmentEntryId();
		}

		fragmentEntryLink.setFragmentEntryId(fragmentEntryId);

		fragmentEntryLink.setCss(css);
		fragmentEntryLink.setHtml(html);
		fragmentEntryLink.setJs(js);
		fragmentEntryLink.setConfiguration(configuration);

		DefaultFragmentRendererContext defaultFragmentRendererContext =
			new DefaultFragmentRendererContext(fragmentEntryLink);

		defaultFragmentRendererContext.setMode(FragmentEntryLinkConstants.VIEW);

		return defaultFragmentRendererContext;
	}

	private FragmentEntry _getFragmentEntry() {
		long fragmentCollectionId = ParamUtil.getLong(
			_httpServletRequest, "fragmentCollectionId");
		long fragmentEntryId = ParamUtil.getLong(
			_httpServletRequest, "fragmentEntryId");
		String fragmentEntryKey = ParamUtil.getString(
			_httpServletRequest, "fragmentEntryKey");

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(fragmentEntryId);

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.fetchFragmentCollection(
				fragmentCollectionId);

		if ((fragmentEntry == null) && (fragmentCollection != null)) {
			fragmentEntry = FragmentEntryLocalServiceUtil.fetchFragmentEntry(
				fragmentCollection.getGroupId(), fragmentEntryKey);
		}

		if (fragmentEntry == null) {
			fragmentEntry =
				_fragmentCollectionContributorTracker.getFragmentEntry(
					fragmentEntryKey);
		}

		return fragmentEntry;
	}

	private final FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;
	private final HttpServletRequest _httpServletRequest;

}