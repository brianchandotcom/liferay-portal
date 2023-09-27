/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.taglib.servlet.taglib;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.soy.servlet.taglib.ComponentRendererTag;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class SearchResultsTag extends ComponentRendererTag {

	@Override
	public int doStartTag() {
		putValue("queryString", StringPool.BLANK);

		HttpServletRequest httpServletRequest = getRequest();

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		try {
			AccountEntry accountEntry = commerceContext.getAccountEntry();

			if (accountEntry != null) {
				putValue("commerceAccountId", accountEntry.getAccountEntryId());
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		putValue(
			"searchAPI",
			PortalUtil.getPortalURL(httpServletRequest) +
				PortalUtil.getPathContext() + "/o/commerce-ui/search/");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		putValue("spritemap", themeDisplay.getPathThemeSpritemap());

		putValue("visible", false);

		setTemplateNamespace("SearchResults.render");

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		NPMResolver npmResolver = ServletContextUtil.getNPMResolver();

		if (npmResolver == null) {
			return StringPool.BLANK;
		}

		return npmResolver.resolveModuleName(
			"commerce-frontend-taglib/search_results/SearchResults.es");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchResultsTag.class);

}