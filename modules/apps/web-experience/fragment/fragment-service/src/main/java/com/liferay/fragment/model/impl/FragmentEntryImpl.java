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

package com.liferay.fragment.model.impl;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.html.preview.model.HtmlPreviewEntry;
import com.liferay.html.preview.service.HtmlPreviewEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.portal.kernel.zip.ZipWriter;

import java.util.Optional;

/**
 * @author Eudaldo Alonso
 */
public class FragmentEntryImpl extends FragmentEntryBaseImpl {

	@Override
	public String getContent() throws PortalException {
		StringBundler sb = new StringBundler(7);

		sb.append("<html><head><style>");
		sb.append(getCss());
		sb.append("</style><script>");
		sb.append(getJs());
		sb.append("</script></head><body>");

		Optional<ServiceContext> serviceContextOptional = Optional.ofNullable(
			ServiceContextThreadLocal.getServiceContext());

		ServiceContext serviceContext = serviceContextOptional.orElse(
			new ServiceContext());

		String html = SanitizerUtil.sanitize(
			serviceContext.getCompanyId(), serviceContext.getScopeGroupId(),
			serviceContext.getUserId(), FragmentEntry.class.getName(),
			getPrimaryKey(), ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL,
			getHtml(), null);

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

	@Override
	public void populateZipWriter(ZipWriter zipWriter, String path)
		throws Exception {

		path = path + StringPool.SLASH + getFragmentEntryId();

		Element fragmentEntryElement = new Element("fragment-entry", false);

		fragmentEntryElement.addElement("name", getName());
		fragmentEntryElement.addElement("css-path", path + "/css.css");
		fragmentEntryElement.addElement("js-path", path + "/js.js");
		fragmentEntryElement.addElement("html-path", path + "/html.html");

		zipWriter.addEntry(
			path + "/definition.xml", fragmentEntryElement.toXMLString());

		zipWriter.addEntry(path + "/css.css", getCss());
		zipWriter.addEntry(path + "/js.js", getJs());
		zipWriter.addEntry(path + "/html.html", getHtml());
	}

}