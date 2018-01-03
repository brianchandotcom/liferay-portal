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

package com.liferay.layout.page.template.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.fragment.model.FragmentEntryInstanceLink;
import com.liferay.fragment.service.FragmentEntryInstanceLinkLocalServiceUtil;
import com.liferay.html.preview.model.HtmlPreviewEntry;
import com.liferay.html.preview.service.HtmlPreviewEntryLocalServiceUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;
import java.util.Optional;

/**
 * @author Jürgen Kappler
 */
@ProviderType
public class LayoutPageTemplateEntryImpl
	extends LayoutPageTemplateEntryBaseImpl {

	@Override
	public String getContent() throws PortalException {
		List<FragmentEntryInstanceLink> fragmentEntryInstanceLinks =
			FragmentEntryInstanceLinkLocalServiceUtil.
				getFragmentEntryInstanceLinks(
					getGroupId(), getLayoutPageTemplateEntryId());

		StringBundler cssSB = new StringBundler(
			fragmentEntryInstanceLinks.size());
		StringBundler htmlSB = new StringBundler(
			fragmentEntryInstanceLinks.size());
		StringBundler jsSB = new StringBundler(
			fragmentEntryInstanceLinks.size());

		for (FragmentEntryInstanceLink fragmentEntryInstanceLink :
				fragmentEntryInstanceLinks) {

			cssSB.append(fragmentEntryInstanceLink.getCss());
			htmlSB.append(fragmentEntryInstanceLink.getHtml());
			jsSB.append(fragmentEntryInstanceLink.getJs());
		}

		StringBundler sb = new StringBundler(7);

		sb.append("<html><head><style>");
		sb.append(cssSB.toString());
		sb.append("</style><script>");
		sb.append(jsSB.toString());
		sb.append("</script></head><body>");

		Optional<ServiceContext> serviceContextOptional = Optional.ofNullable(
			ServiceContextThreadLocal.getServiceContext());

		ServiceContext serviceContext = serviceContextOptional.orElse(
			new ServiceContext());

		String html = SanitizerUtil.sanitize(
			serviceContext.getCompanyId(), serviceContext.getScopeGroupId(),
			serviceContext.getUserId(), LayoutPageTemplateEntry.class.getName(),
			getPrimaryKey(), ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
			htmlSB.toString(), null);

		sb.append(html);

		sb.append("</body></html>");

		return sb.toString();
	}

	@Override
	public String getImagePreviewURL(ThemeDisplay themeDisplay) {
		if (getHtmlPreviewEntryId() <= 0) {
			return StringPool.BLANK;
		}

		HtmlPreviewEntry htmlPreviewEntry =
			HtmlPreviewEntryLocalServiceUtil.fetchHtmlPreviewEntry(
				getHtmlPreviewEntryId());

		if (htmlPreviewEntry == null) {
			return StringPool.BLANK;
		}

		return htmlPreviewEntry.getImagePreviewURL(themeDisplay);
	}

}