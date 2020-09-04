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

package com.liferay.commerce.price.list.web.internal.servlet.taglib.ui;

import com.liferay.commerce.price.list.constants.CommercePriceListScreenNavigationConstants;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.portlet.action.CommercePriceListActionHelper;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.price.list.web.internal.display.context.CommercePriceEntryDisplayContext;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"screen.navigation.category.order:Integer=20",
		"screen.navigation.entry.order:Integer=20"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CommercePriceListEntriesScreenNavigationEntry
	implements ScreenNavigationCategory,
			   ScreenNavigationEntry<CommercePriceList> {

	@Override
	public String getCategoryKey() {
		return CommercePriceListScreenNavigationConstants.CATEGORY_KEY_ENTRIES;
	}

	@Override
	public String getEntryKey() {
		return CommercePriceListScreenNavigationConstants.CATEGORY_KEY_ENTRIES;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			locale,
			CommercePriceListScreenNavigationConstants.CATEGORY_KEY_ENTRIES);
	}

	@Override
	public String getScreenNavigationKey() {
		return CommercePriceListScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_PRICE_LIST_GENERAL;
	}

	@Override
	public boolean isVisible(User user, CommercePriceList commercePriceList) {
		if (commercePriceList == null) {
			return false;
		}

		boolean hasPermission = false;

		try {
			hasPermission = _commercePriceListModelResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				commercePriceList.getCommercePriceListId(), ActionKeys.VIEW);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return hasPermission;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			CommercePriceEntryDisplayContext commercePriceEntryDisplayContext =
				new CommercePriceEntryDisplayContext(
					_commercePriceListActionHelper, _commercePriceEntryService,
					_commercePriceListModelResourcePermission, _itemSelector,
					httpServletRequest);

			httpServletRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				commercePriceEntryDisplayContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		_jspRenderer.renderJSP(
			_setServletContext, httpServletRequest, httpServletResponse,
			"/price_entries.jsp");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceListEntriesScreenNavigationEntry.class);

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

	@Reference
	private CommercePriceListActionHelper _commercePriceListActionHelper;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.price.list.model.CommercePriceList)"
	)
	private ModelResourcePermission<CommercePriceList>
		_commercePriceListModelResourcePermission;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.price.list.web)"
	)
	private ServletContext _setServletContext;

}