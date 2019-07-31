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

package com.liferay.fragment.web.internal.servlet.taglib.clay;

import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.web.internal.constants.FragmentWebKeys;
import com.liferay.fragment.web.internal.security.permission.resource.FragmentPermission;
import com.liferay.fragment.web.internal.servlet.taglib.util.BasicFragmentEntryActionDropdownItemsProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class BasicFragmentEntryVerticalCard extends FragmentEntryVerticalCard {

	public BasicFragmentEntryVerticalCard(
		FragmentEntry fragmentEntry, RenderRequest renderRequest,
		RenderResponse renderResponse, RowChecker rowChecker) {

		super(fragmentEntry, renderRequest, rowChecker);

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		BasicFragmentEntryActionDropdownItemsProvider
			basicFragmentEntryActionDropdownItemsProvider =
				new BasicFragmentEntryActionDropdownItemsProvider(
					fragmentEntry, _renderRequest, _renderResponse);

		try {
			return basicFragmentEntryActionDropdownItemsProvider.
				getActionDropdownItems();
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return null;
	}

	@Override
	public String getDefaultEventHandler() {
		return FragmentWebKeys.FRAGMENT_ENTRY_DROPDOWN_DEFAULT_EVENT_HANDLER;
	}

	@Override
	public String getHref() {
		if (!FragmentPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(),
				FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES)) {

			return null;
		}

		PortletURL editFragmentEntryURL = _renderResponse.createRenderURL();

		editFragmentEntryURL.setParameter(
			"mvcRenderCommandName", "/fragment/edit_fragment_entry");
		editFragmentEntryURL.setParameter(
			"redirect", themeDisplay.getURLCurrent());
		editFragmentEntryURL.setParameter(
			"fragmentCollectionId",
			String.valueOf(fragmentEntry.getFragmentCollectionId()));
		editFragmentEntryURL.setParameter(
			"fragmentEntryId",
			String.valueOf(fragmentEntry.getFragmentEntryId()));

		return editFragmentEntryURL.toString();
	}

	@Override
	public List<LabelItem> getLabels() {
		return new LabelItemList() {
			{
				add(
					labelItem -> labelItem.setStatus(
						fragmentEntry.getStatus()));
			}
		};
	}

	@Override
	public String getSubtitle() {
		Date statusDate = fragmentEntry.getStatusDate();

		String statusDateDescription = LanguageUtil.getTimeDescription(
			_httpServletRequest,
			System.currentTimeMillis() - statusDate.getTime(), true);

		return LanguageUtil.format(
			_httpServletRequest, "x-ago", statusDateDescription);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BasicFragmentEntryVerticalCard.class);

	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}