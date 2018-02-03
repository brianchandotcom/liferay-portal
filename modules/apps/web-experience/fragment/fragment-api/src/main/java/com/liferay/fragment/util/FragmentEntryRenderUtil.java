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

package com.liferay.fragment.util;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import org.jsoup.nodes.Element;

/**
 * @author Pablo Molina
 */
public class FragmentEntryRenderUtil {

	public static String renderFragmentEntry(FragmentEntry fragmentEntry) {
		return renderFragmentEntry(
			fragmentEntry.getFragmentEntryId(), 0, fragmentEntry.getCss(),
			fragmentEntry.getHtml(), fragmentEntry.getJs());
	}

	public static String renderFragmentEntry(
		long fragmentEntryId, long fragmentEntryInstanceId) {

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(fragmentEntryId);

		return renderFragmentEntry(
			fragmentEntryId, fragmentEntryInstanceId, fragmentEntry.getCss(),
			fragmentEntry.getHtml(), fragmentEntry.getJs());
	}

	public static String renderFragmentEntry(
		long fragmentEntryId, long fragmentEntryInstanceId, String css,
		String html, String js) {

		try {
			Element divElement = new Element("div");

			StringBundler sb = new StringBundler(4);

			sb.append("fragment-");
			sb.append(fragmentEntryId);
			sb.append("-");
			sb.append(fragmentEntryInstanceId);

			divElement.attr("id", sb.toString());

			divElement.prepend(_sanitize(fragmentEntryId, html));

			Element styleElement = divElement.prependElement("style");

			styleElement.prepend(css);

			Element scriptElement = divElement.prependElement("script");

			scriptElement.prependText("(function(){" + js + ";}());");

			return divElement.toString();
		}
		catch (SanitizerException se) {
			throw new SystemException(se);
		}
	}

	public static String renderFragmentEntry(
		long fragmentEntryId, String css, String html, String js) {

		return renderFragmentEntry(fragmentEntryId, 0, css, html, js);
	}

	public static String renderFragmentEntryLink(
		long fragmentEntryLinkId, long position) {

		FragmentEntryLink fragmentEntryLink =
			FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(
				fragmentEntryLinkId);

		return renderFragmentEntry(
			fragmentEntryLinkId, position, fragmentEntryLink.getCss(),
			fragmentEntryLink.getHtml(), fragmentEntryLink.getJs());
	}

	private static String _sanitize(long fragmentEntryId, String html)
		throws SanitizerException {

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(fragmentEntryId);

		if (fragmentEntry == null) {
			return StringPool.BLANK;
		}

		return SanitizerUtil.sanitize(
			fragmentEntry.getCompanyId(), fragmentEntry.getGroupId(),
			fragmentEntry.getUserId(), FragmentEntry.class.getName(),
			fragmentEntryId, ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL, html,
			null);
	}

}