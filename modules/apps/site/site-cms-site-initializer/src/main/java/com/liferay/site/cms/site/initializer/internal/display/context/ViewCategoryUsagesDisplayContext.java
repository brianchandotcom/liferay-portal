/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.cms.site.initializer.internal.util.CategorizationBreadcrumbUtil;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class ViewCategoryUsagesDisplayContext {

	public ViewCategoryUsagesDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;
	}

	public String getAPIURL() {
		StringBundler sb = new StringBundler(4);

		sb.append("/o/search/v1.0/search?emptySearch=true&");
		sb.append("filter=taxonomyCategoryIds in (");
		sb.append(ParamUtil.getLong(_httpServletRequest, "categoryId"));
		sb.append(")&nestedFields=embedded");

		return sb.toString();
	}

	public Map<String, Object> getBreadcrumbReactData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"breadcrumbItems",
			CategorizationBreadcrumbUtil.getUsagesBreadcrumbsJSONArray(
				ParamUtil.getLong(_httpServletRequest, "categoryId"),
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY))
		).build();
	}

	private final HttpServletRequest _httpServletRequest;

}