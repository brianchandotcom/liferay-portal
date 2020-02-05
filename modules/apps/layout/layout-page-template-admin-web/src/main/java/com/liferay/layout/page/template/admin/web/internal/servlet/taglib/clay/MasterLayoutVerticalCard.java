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

package com.liferay.layout.page.template.admin.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseBaseClayCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.layout.page.template.admin.web.internal.constants.LayoutPageTemplateAdminWebKeys;
import com.liferay.layout.page.template.admin.web.internal.security.permission.resource.LayoutPageTemplateEntryPermission;
import com.liferay.layout.page.template.admin.web.internal.servlet.taglib.util.MasterLayoutActionDropdownItemsProvider;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class MasterLayoutVerticalCard
	extends BaseBaseClayCard implements VerticalCard {

	public MasterLayoutVerticalCard(
		BaseModel<?> baseModel, RenderRequest renderRequest,
		RenderResponse renderResponse, RowChecker rowChecker) {

		super(baseModel, rowChecker);

		_layoutPageTemplateEntry = (LayoutPageTemplateEntry)baseModel;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		try {
			MasterLayoutActionDropdownItemsProvider
				masterLayoutActionDropdownItemsProvider =
					new MasterLayoutActionDropdownItemsProvider(
						_layoutPageTemplateEntry, _renderRequest,
						_renderResponse);

			List<DropdownItem> actionDropdownItems =
				masterLayoutActionDropdownItemsProvider.
					getActionDropdownItems();

			if (!actionDropdownItems.isEmpty()) {
				return actionDropdownItems;
			}

			return null;
		}
		catch (Exception exception) {
		}

		return null;
	}

	@Override
	public String getDefaultEventHandler() {
		return LayoutPageTemplateAdminWebKeys.
			MASTER_LAYOUT_DROPDOWN_DEFAULT_EVENT_HANDLER;
	}

	@Override
	public String getHref() {
		try {
			if (!LayoutPageTemplateEntryPermission.contains(
					_themeDisplay.getPermissionChecker(),
					_layoutPageTemplateEntry, ActionKeys.UPDATE)) {

				return null;
			}

			Layout layout = LayoutLocalServiceUtil.getLayout(
				_layoutPageTemplateEntry.getPlid());

			Layout draftLayout = LayoutLocalServiceUtil.fetchLayout(
				PortalUtil.getClassNameId(Layout.class), layout.getPlid());

			String layoutFullURL = PortalUtil.getLayoutFullURL(
				draftLayout, _themeDisplay);

			layoutFullURL = HttpUtil.setParameter(
				layoutFullURL, "p_l_mode", Constants.EDIT);

			return HttpUtil.setParameter(
				layoutFullURL, "p_l_back_url", _themeDisplay.getURLCurrent());
		}
		catch (Exception exception) {
		}

		return null;
	}

	@Override
	public String getIcon() {
		return "page";
	}

	@Override
	public String getImageSrc() {
		return _layoutPageTemplateEntry.getImagePreviewURL(_themeDisplay);
	}

	@Override
	public List<LabelItem> getLabels() {
		return new LabelItemList() {
			{
				add(
					labelItem -> labelItem.setStatus(
						_layoutPageTemplateEntry.getStatus()));
			}
		};
	}

	@Override
	public String getStickerIcon() {
		if (_layoutPageTemplateEntry.getLayoutPageTemplateEntryId() <= 0) {
			LayoutPageTemplateEntry defaultLayoutPageTemplateEntry =
				LayoutPageTemplateEntryServiceUtil.
					fetchDefaultLayoutPageTemplateEntry(
						_themeDisplay.getScopeGroupId(),
						LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT,
						WorkflowConstants.STATUS_APPROVED);

			if (defaultLayoutPageTemplateEntry == null) {
				return "check-circle";
			}
		}

		if (_layoutPageTemplateEntry.isDefaultTemplate()) {
			return "check-circle";
		}

		return null;
	}

	@Override
	public String getSubtitle() {
		int layoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
			_themeDisplay.getScopeGroupId(),
			_layoutPageTemplateEntry.getPlid());

		return LanguageUtil.format(
			_httpServletRequest, "x-usages", layoutsCount);
	}

	@Override
	public String getTitle() {
		return HtmlUtil.escape(_layoutPageTemplateEntry.getName());
	}

	@Override
	public boolean isSelectable() {
		if (_layoutPageTemplateEntry.getLayoutPageTemplateEntryId() > 0) {
			return true;
		}

		return false;
	}

	private final HttpServletRequest _httpServletRequest;
	private final LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}