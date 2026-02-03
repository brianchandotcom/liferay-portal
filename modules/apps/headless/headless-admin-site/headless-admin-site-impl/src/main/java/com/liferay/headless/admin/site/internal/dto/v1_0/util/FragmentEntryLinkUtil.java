/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.DefaultFragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Lourdes Fernández Besada
 */
public class FragmentEntryLinkUtil {

	public static String getProcessedHTML(
			FragmentEntryLink fragmentEntryLink,
			FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry,
			ServiceContext serviceContext)
		throws PortalException {

		if (serviceContext == null) {
			return fragmentEntryLink.getHtml();
		}

		HttpServletRequest httpServletRequest = serviceContext.getRequest();
		HttpServletResponse httpServletResponse = serviceContext.getResponse();
		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		if ((httpServletRequest == null) && (themeDisplay != null)) {
			httpServletRequest = themeDisplay.getRequest();
		}

		if ((httpServletResponse == null) && (themeDisplay != null)) {
			httpServletResponse = themeDisplay.getResponse();
		}

		if ((httpServletRequest == null) || (httpServletResponse == null)) {
			return fragmentEntryLink.getHtml();
		}

		fragmentEntryLink.setEditableValues(null);

		FragmentEntryProcessorContext fragmentEntryProcessorContext =
			new DefaultFragmentEntryProcessorContext(
				fragmentEntryLink.getCompanyId(), httpServletRequest,
				httpServletResponse, LocaleUtil.getMostRelevantLocale(),
				FragmentEntryLinkConstants.EDIT,
				fragmentEntryLink.getGroupId());

		return fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
			fragmentEntryLink, fragmentEntryProcessorContext);
	}

}