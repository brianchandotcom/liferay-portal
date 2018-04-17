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

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class FragmentEntryLinkDisplayContext {

	public FragmentEntryLinkDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public DropdownItemList getActionItemsDropdownItemList() {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			_renderRequest);

		return new DropdownItemList(request) {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref(
							"javascript:" + _renderResponse.getNamespace() +
								"propagate();");
						dropdownItem.setIcon("reload");
						dropdownItem.setLabel("propagate");
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public FragmentEntry getFragmentEntry() throws PortalException {
		if (Validator.isNotNull(_fragmentEntry)) {
			return _fragmentEntry;
		}

		_fragmentEntry = FragmentEntryLocalServiceUtil.getFragmentEntry(
			getFragmentEntryId());

		return _fragmentEntry;
	}

	public long getFragmentEntryId() {
		if (Validator.isNotNull(_fragmentEntryId)) {
			return _fragmentEntryId;
		}

		_fragmentEntryId = ParamUtil.getLong(_renderRequest, "fragmentEntryId");

		return _fragmentEntryId;
	}

	public String getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_renderRequest, "keywords", null);

		return _keywords;
	}

	public String getNavigation() {
		if (Validator.isNotNull(_navigation)) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(_renderRequest, "navigation", "all");

		return _navigation;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_renderRequest, "orderByCol", "name");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_renderRequest, "orderByType", "asc");

		return _orderByType;
	}

	public int getUsageCount(String navigation) throws PortalException {
		FragmentEntry fragmentEntry = getFragmentEntry();

		if (Objects.equals(navigation, "pages")) {
			return FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinksCount(
				fragmentEntry.getGroupId(), getFragmentEntryId(),
				PortalUtil.getClassNameId(Layout.class));
		}
		else if (Objects.equals(navigation, "page-templates")) {
			return FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinksCount(
				fragmentEntry.getGroupId(), getFragmentEntryId(),
				PortalUtil.getClassNameId(LayoutPageTemplateEntry.class));
		}

		return FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinksCount(
			fragmentEntry.getGroupId(), getFragmentEntryId());
	}

	private FragmentEntry _fragmentEntry;
	private Long _fragmentEntryId;
	private String _keywords;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}