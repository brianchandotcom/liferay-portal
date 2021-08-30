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

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseVerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.layout.page.template.admin.web.internal.security.permission.resource.LayoutPageTemplateEntryPermission;
import com.liferay.layout.page.template.admin.web.internal.servlet.taglib.util.LayoutPageTemplateEntryActionDropdownItemsProvider;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutPrototypeServiceUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;
import java.util.Objects;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutPageTemplateEntryVerticalCard extends BaseVerticalCard {

	public LayoutPageTemplateEntryVerticalCard(
		BaseModel<?> baseModel, RenderRequest renderRequest,
		RenderResponse renderResponse, RowChecker rowChecker) {

		super(baseModel, renderRequest, rowChecker);

		_renderResponse = renderResponse;

		_layoutPageTemplateEntry = (LayoutPageTemplateEntry)baseModel;
		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		LayoutPageTemplateEntryActionDropdownItemsProvider
			layoutPageTemplateEntryActionDropdownItemsProvider =
				new LayoutPageTemplateEntryActionDropdownItemsProvider(
					_layoutPageTemplateEntry, renderRequest, _renderResponse);

		try {
			return layoutPageTemplateEntryActionDropdownItemsProvider.
				getActionDropdownItems();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return null;
	}

	@Override
	public String getHref() {
		try {
			if (!LayoutPageTemplateEntryPermission.contains(
					themeDisplay.getPermissionChecker(),
					_layoutPageTemplateEntry, ActionKeys.UPDATE)) {

				return null;
			}

			if (Objects.equals(
					_layoutPageTemplateEntry.getType(),
					LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE)) {

				LayoutPrototype layoutPrototype =
					LayoutPrototypeServiceUtil.fetchLayoutPrototype(
						_layoutPageTemplateEntry.getLayoutPrototypeId());

				if (layoutPrototype == null) {
					return null;
				}

				Group layoutPrototypeGroup = layoutPrototype.getGroup();

				String layoutFullURL = layoutPrototypeGroup.getDisplayURL(
					themeDisplay, true);

				return HttpUtil.setParameter(
					layoutFullURL, "p_l_back_url",
					themeDisplay.getURLCurrent());
			}

			Layout draftLayout = LayoutLocalServiceUtil.fetchDraftLayout(
				_layoutPageTemplateEntry.getPlid());

			String layoutFullURL = PortalUtil.getLayoutFullURL(
				draftLayout, themeDisplay);

			layoutFullURL = HttpUtil.setParameter(
				layoutFullURL, "p_l_mode", Constants.EDIT);

			return HttpUtil.setParameter(
				layoutFullURL, "p_l_back_url", themeDisplay.getURLCurrent());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return null;
	}

	@Override
	public String getIcon() {
		if (Objects.equals(
				_layoutPageTemplateEntry.getType(),
				LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE)) {

			return "page-template";
		}

		return "page";
	}

	@Override
	public String getImageSrc() {
		return _layoutPageTemplateEntry.getImagePreviewURL(themeDisplay);
	}

	@Override
	public List<LabelItem> getLabels() {
		if (Objects.equals(
				_layoutPageTemplateEntry.getType(),
				LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE)) {

			return super.getLabels();
		}

		Layout draftLayout = LayoutLocalServiceUtil.fetchDraftLayout(
			_layoutPageTemplateEntry.getPlid());

		if (draftLayout == null) {
			return super.getLabels();
		}

		return LabelItemListBuilder.add(
			labelItem -> labelItem.setStatus(draftLayout.getStatus())
		).build();
	}

	@Override
	public String getSubtitle() {
		if (Objects.equals(
				_layoutPageTemplateEntry.getType(),
				LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE)) {

			return LanguageUtil.get(
				_httpServletRequest, "widget-page-template");
		}

		return LanguageUtil.get(_httpServletRequest, "content-page-template");
	}

	@Override
	public String getTitle() {
		return _layoutPageTemplateEntry.getName();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateEntryVerticalCard.class);

	private final HttpServletRequest _httpServletRequest;
	private final LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final RenderResponse _renderResponse;

}