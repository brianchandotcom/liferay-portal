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

package com.liferay.layout.admin.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseVerticalCard;
import com.liferay.layout.admin.web.internal.security.permission.resource.LayoutPageTemplateEntryPermission;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.language.LanguageUtil;
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

		_layoutPageTemplateEntry = (LayoutPageTemplateEntry)baseModel;
		_request = PortalUtil.getHttpServletRequest(renderRequest);
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

				return layoutPrototypeGroup.getDisplayURL(themeDisplay, true);
			}

			Layout layout = LayoutLocalServiceUtil.getLayout(
				_layoutPageTemplateEntry.getPlid());

			String layoutFullURL = PortalUtil.getLayoutFullURL(
				layout, themeDisplay);

			return HttpUtil.setParameter(
				layoutFullURL, "p_l_mode", Constants.EDIT);
		}
		catch (Exception e) {
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
	public String getSubtitle() {
		if (Objects.equals(
				_layoutPageTemplateEntry.getType(),
				LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE)) {

			return LanguageUtil.get(_request, "widget-page-template");
		}

		return LanguageUtil.get(_request, "content-page-template");
	}

	@Override
	public String getTitle() {
		return _layoutPageTemplateEntry.getName();
	}

	private final LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final HttpServletRequest _request;

}