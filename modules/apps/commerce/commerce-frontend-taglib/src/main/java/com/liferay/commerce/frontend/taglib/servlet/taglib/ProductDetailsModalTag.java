/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.taglib.servlet.taglib;

import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.soy.servlet.taglib.ComponentRendererTag;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Fabio Diego Mastrorilli
 */
public class ProductDetailsModalTag extends ComponentRendererTag {

	@Override
	public int doStartTag() {
		putValue("addToOrderLink", null);
		putValue("availability", "inStock");
		putValue("detailsLink", "/details/AR351184");
		putValue("name", "Lorem Ipsum Dolor Sit");
		putValue(
			"pictureUrl",
			"http://image.superstreetonline.com/f" +
				"/243817358+w+h+q80+re0+cr1+ar0+st0" +
					"/006-fully-built-engine-by-the-experts.jpg");
		putValue("settings", null);
		putValue("sku", "AR351184");

		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		putValue("spritemap", themeDisplay.getPathThemeSpritemap());

		setTemplateNamespace("ProductDetailsModal.render");

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		NPMResolver npmResolver = ServletContextUtil.getNPMResolver();

		if (npmResolver == null) {
			return StringPool.BLANK;
		}

		return npmResolver.resolveModuleName(
			"commerce-frontend-taglib/product_details_modal" +
				"/ProductDetailsModal.es");
	}

}