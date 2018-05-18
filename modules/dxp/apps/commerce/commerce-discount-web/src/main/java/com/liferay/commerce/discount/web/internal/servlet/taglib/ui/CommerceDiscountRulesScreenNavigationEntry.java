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

package com.liferay.commerce.discount.web.internal.servlet.taglib.ui;

import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleTypeJSPContributorRegistry;
import com.liferay.commerce.discount.rule.type.CommerceDiscountRuleTypeRegistry;
import com.liferay.commerce.discount.service.CommerceDiscountRuleService;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.commerce.discount.service.CommerceDiscountUserSegmentRelService;
import com.liferay.commerce.discount.target.CommerceDiscountTargetRegistry;
import com.liferay.commerce.discount.web.internal.display.context.CommerceDiscountRuleDisplayContext;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
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
		"screen.navigation.category.order:Integer=20",
		"screen.navigation.entry.order:Integer=10"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CommerceDiscountRulesScreenNavigationEntry
	implements ScreenNavigationCategory,
			   ScreenNavigationEntry<CommerceDiscount> {

	@Override
	public String getCategoryKey() {
		return CommerceDiscountScreenNavigationConstants.
			CATEGORY_KEY_COMMERCE_DISCOUNT_RULES;
	}

	@Override
	public String getEntryKey() {
		return CommerceDiscountScreenNavigationConstants.
			ENTRY_KEY_COMMERCE_DISCOUNT_RULES;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle,
			CommerceDiscountScreenNavigationConstants.
				ENTRY_KEY_COMMERCE_DISCOUNT_RULES);
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceDiscountScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_DISCOUNT_GENERAL;
	}

	@Override
	public boolean isVisible(User user, CommerceDiscount commerceDiscount) {
		if (commerceDiscount == null) {
			return false;
		}

		return true;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		CommerceDiscountRuleDisplayContext commerceDiscountRuleDisplayContext =
			new CommerceDiscountRuleDisplayContext(
				_commerceCurrencyService, _commerceDiscountRuleService,
				_commerceDiscountRuleTypeJSPContributorRegistry,
				_commerceDiscountRuleTypeRegistry, _commerceDiscountService,
				_commerceDiscountTargetRegistry,
				_commerceDiscountUserSegmentRelService, _commercePriceFormatter,
				_cpDefinitionService, httpServletRequest, _itemSelector);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceDiscountRuleDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/discount/rules.jsp");
	}

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private CommerceDiscountRuleService _commerceDiscountRuleService;

	@Reference
	private CommerceDiscountRuleTypeJSPContributorRegistry
		_commerceDiscountRuleTypeJSPContributorRegistry;

	@Reference
	private CommerceDiscountRuleTypeRegistry _commerceDiscountRuleTypeRegistry;

	@Reference
	private CommerceDiscountService _commerceDiscountService;

	@Reference
	private CommerceDiscountTargetRegistry _commerceDiscountTargetRegistry;

	@Reference
	private CommerceDiscountUserSegmentRelService
		_commerceDiscountUserSegmentRelService;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.discount.web)"
	)
	private ServletContext _servletContext;

}