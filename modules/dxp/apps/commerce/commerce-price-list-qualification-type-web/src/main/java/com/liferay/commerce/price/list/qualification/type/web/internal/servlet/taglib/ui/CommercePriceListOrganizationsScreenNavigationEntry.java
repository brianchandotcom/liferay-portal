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

package com.liferay.commerce.price.list.qualification.type.web.internal.servlet.taglib.ui;

import com.liferay.commerce.model.CommercePriceList;
import com.liferay.commerce.model.CommercePriceListQualificationTypeRel;
import com.liferay.commerce.price.list.qualification.type.service.CommercePriceListUserRelService;
import com.liferay.commerce.price.list.qualification.type.web.internal.display.context.OrganizationCommercePriceListQualificationTypeDisplayContext;
import com.liferay.commerce.price.list.qualification.type.web.internal.price.OrganizationCommercePriceListQualificationTypeImpl;
import com.liferay.commerce.price.list.web.portlet.action.CommercePriceListActionHelper;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

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
		"screen.navigation.category.order:Integer=40",
		"screen.navigation.entry.order:Integer=40"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CommercePriceListOrganizationsScreenNavigationEntry
	implements ScreenNavigationCategory,
			   ScreenNavigationEntry<CommercePriceList> {

	@Override
	public String getCategoryKey() {
		return "organizations";
	}

	@Override
	public String getEntryKey() {
		return "organizations";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "organizations");
	}

	@Override
	public String getScreenNavigationKey() {
		return "commerce.price.list.general";
	}

	@Override
	public boolean isVisible(User user, CommercePriceList commercePriceList) {
		if (commercePriceList == null) {
			return false;
		}
		else {
			CommercePriceListQualificationTypeRel
				commercePriceListQualificationTypeRel =
					commercePriceList.
						fetchCommercePriceListQualificationTypeRel(
							OrganizationCommercePriceListQualificationTypeImpl.
								KEY);

			if (commercePriceListQualificationTypeRel != null) {
				return true;
			}

			return false;
		}
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		OrganizationCommercePriceListQualificationTypeDisplayContext
			organizationCommercePriceListQualificationTypeDisplayContext =
				new OrganizationCommercePriceListQualificationTypeDisplayContext(
					_commercePriceListActionHelper,
					_commercePriceListUserRelService, _itemSelector,
					httpServletRequest, _organizationLocalService);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			organizationCommercePriceListQualificationTypeDisplayContext);

		_jspRenderer.renderJSP(
			_setServletContext, httpServletRequest, httpServletResponse,
			"/price_list_organizations.jsp");
	}

	@Reference
	private CommercePriceListActionHelper _commercePriceListActionHelper;

	@Reference
	private CommercePriceListUserRelService _commercePriceListUserRelService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.price.list.qualification.type.web)"
	)
	private ServletContext _setServletContext;

}