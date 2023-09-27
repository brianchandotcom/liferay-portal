/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.layoutconfiguration.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.LayoutTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Shuyang Zhou
 */
public class RuntimePageUtil {

	public static LayoutTemplate getLayoutTemplate(String templateId) {
		return _runtimePage.getLayoutTemplate(templateId);
	}

	public static StringBundler getProcessedTemplate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String portletId,
			String templateId, String content)
		throws Exception {

		return _runtimePage.getProcessedTemplate(
			httpServletRequest, httpServletResponse, portletId, templateId,
			content);
	}

	public static RuntimePage getRuntimePage() {
		return _runtimePage;
	}

	public static void processTemplate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String portletId,
			String templateId, String content)
		throws Exception {

		_runtimePage.processTemplate(
			httpServletRequest, httpServletResponse, portletId, templateId,
			content);
	}

	public static void processTemplate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String portletId,
			String templateId, String content, String langType)
		throws Exception {

		_runtimePage.processTemplate(
			httpServletRequest, httpServletResponse, portletId, templateId,
			content, langType);
	}

	public void setRuntimePage(RuntimePage runtimePage) {
		_runtimePage = runtimePage;
	}

	private static RuntimePage _runtimePage;

}