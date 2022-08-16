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

package com.liferay.layout.set.prototype.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.BaseVerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.layout.set.prototype.web.internal.servlet.taglib.util.LayoutSetPrototypeActionDropdownItemsProvider;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.LayoutSetPrototypePermissionUtil;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Eudaldo Alonso
 */
public class LayoutSetPrototypeVerticalCard extends BaseVerticalCard {

	public LayoutSetPrototypeVerticalCard(
		LayoutSetPrototype layoutSetPrototype, RenderRequest renderRequest,
		RenderResponse renderResponse, RowChecker rowChecker) {

		super(layoutSetPrototype, renderRequest, rowChecker);

		_layoutSetPrototype = layoutSetPrototype;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		LayoutSetPrototypeActionDropdownItemsProvider
			layoutSetPrototypeActionDropdownItemsProvider =
				new LayoutSetPrototypeActionDropdownItemsProvider(
					_layoutSetPrototype, _renderRequest, _renderResponse);

		try {
			return layoutSetPrototypeActionDropdownItemsProvider.
				getActionDropdownItems();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	@Override
	public String getHref() {
		try {
			Group group = _layoutSetPrototype.getGroup();

			if (LayoutSetPrototypePermissionUtil.contains(
					themeDisplay.getPermissionChecker(),
					_layoutSetPrototype.getLayoutSetPrototypeId(),
					ActionKeys.UPDATE) &&
				(group.getPrivateLayoutsPageCount() > 0)) {

				return group.getDisplayURL(themeDisplay, true);
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return null;
	}

	@Override
	public String getIcon() {
		return "site-template";
	}

	@Override
	public String getSubtitle() {
		if (_layoutSetPrototype.isActive()) {
			return LanguageUtil.get(themeDisplay.getLocale(), "active");
		}

		return LanguageUtil.get(themeDisplay.getLocale(), "not-active");
	}

	@Override
	public String getTitle() {
		return _layoutSetPrototype.getName(themeDisplay.getLocale());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetPrototypeVerticalCard.class);

	private final LayoutSetPrototype _layoutSetPrototype;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}