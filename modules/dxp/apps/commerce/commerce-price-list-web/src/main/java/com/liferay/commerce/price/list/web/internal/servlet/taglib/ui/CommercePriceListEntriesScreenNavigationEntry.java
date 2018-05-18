/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.price.list.web.internal.servlet.taglib.ui;

import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.price.list.web.internal.display.context.CommercePriceEntryDisplayContext;
import com.liferay.commerce.price.list.web.portlet.action.CommercePriceListActionHelper;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
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

		return true;
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
					_itemSelector, httpServletRequest);

			httpServletRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				commercePriceEntryDisplayContext);
		}
		catch (Exception e) {
			_log.error(e, e);
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

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.price.list.web)"
	)
	private ServletContext _setServletContext;

}