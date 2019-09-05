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

import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplateEntryImpl
	extends LayoutPageTemplateEntryBaseImpl {

	@Override
	public String getContent() throws PortalException {
		List<FragmentEntryLink> fragmentEntryLinks =
			FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
				getGroupId(),
				PortalUtil.getClassNameId(
					LayoutPageTemplateEntry.class.getName()),
				getLayoutPageTemplateEntryId());

		StringBundler cssSB = new StringBundler(fragmentEntryLinks.size());
		StringBundler htmlSB = new StringBundler(fragmentEntryLinks.size());
		StringBundler jsSB = new StringBundler(fragmentEntryLinks.size());

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			cssSB.append(fragmentEntryLink.getCss());
			htmlSB.append(fragmentEntryLink.getHtml());
			jsSB.append(fragmentEntryLink.getJs());
		}

		StringBundler sb = new StringBundler(7);

		sb.append("<html><head><style>");
		sb.append(cssSB.toString());
		sb.append("</style><script>");
		sb.append(jsSB.toString());
		sb.append("</script></head><body>");
		sb.append(htmlSB.toString());
		sb.append("</body></html>");

		return sb.toString();
	}

	@Override
	public String getImagePreviewURL(ThemeDisplay themeDisplay) {
		if (getPreviewFileEntryId() <= 0) {
			return StringPool.BLANK;
		}

		try {
			FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
				getPreviewFileEntryId());

			if (fileEntry == null) {
				return StringPool.BLANK;
			}

			return DLUtil.getImagePreviewURL(fileEntry, themeDisplay);
		}
		catch (Exception e) {
			_log.error("Unable to get preview entry image URL", e);
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateEntryImpl.class);

}