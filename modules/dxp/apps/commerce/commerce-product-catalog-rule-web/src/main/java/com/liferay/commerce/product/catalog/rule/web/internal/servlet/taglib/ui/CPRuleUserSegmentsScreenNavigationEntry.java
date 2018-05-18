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

package com.liferay.commerce.product.catalog.rule.web.internal.servlet.taglib.ui;

import com.liferay.commerce.product.model.CPRule;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

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
		"screen.navigation.entry.order:Integer=20"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CPRuleUserSegmentsScreenNavigationEntry
	implements ScreenNavigationCategory, ScreenNavigationEntry<CPRule> {

	@Override
	public String getCategoryKey() {
		return CPRuleScreenNavigationConstants.
			CATEGORY_KEY_COMMERCE_PRODUCT_RULE_USER_SEGMENTS;
	}

	@Override
	public String getEntryKey() {
		return CPRuleScreenNavigationConstants.
			ENTRY_KEY_COMMERCE_PRODUCT_RULE_USER_SEGMENTS;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle,
			CPRuleScreenNavigationConstants.
				ENTRY_KEY_COMMERCE_PRODUCT_RULE_USER_SEGMENTS);
	}

	@Override
	public String getScreenNavigationKey() {
		return CPRuleScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_PRODUCT_RULE_GENERAL;
	}

	@Override
	public boolean isVisible(User user, CPRule cpRule) {
		if (cpRule == null) {
			return false;
		}

		return true;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/rule/user_segment_rels.jsp");
	}

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.catalog.rule.web)"
	)
	private ServletContext _servletContext;

}