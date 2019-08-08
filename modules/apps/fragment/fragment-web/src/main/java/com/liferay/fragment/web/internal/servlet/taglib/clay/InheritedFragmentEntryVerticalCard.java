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
import com.liferay.fragment.web.internal.security.permission.resource.FragmentPermission;
import com.liferay.fragment.web.internal.servlet.taglib.util.InheritedFragmentEntryActionDropdownItemsProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Jürgen Kappler
 */
public class InheritedFragmentEntryVerticalCard
	extends FragmentEntryVerticalCard {

	public InheritedFragmentEntryVerticalCard(
		FragmentEntry fragmentEntry, RenderRequest renderRequest,
		RenderResponse renderResponse, RowChecker rowChecker) {

		super(fragmentEntry, renderRequest, rowChecker);

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		InheritedFragmentEntryActionDropdownItemsProvider
			inheritedFragmentEntryActionDropdownItemsProvider =
				new InheritedFragmentEntryActionDropdownItemsProvider(
					fragmentEntry, _renderRequest, _renderResponse);

		try {
			return inheritedFragmentEntryActionDropdownItemsProvider.
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

	private static final Log _log = LogFactoryUtil.getLog(
		InheritedFragmentEntryVerticalCard.class);

	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}