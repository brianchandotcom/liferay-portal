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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.DocumentConversionUtil;
import com.liferay.portlet.journal.model.JournalArticleDisplay;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eudaldo Alonso
 */
public class ExportArticleUtil extends PortletAction {

	public static void sendFile(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		long groupId = ParamUtil.getLong(portletRequest, "groupId");
		String articleId = ParamUtil.getString(portletRequest, "articleId");

		String targetExtension = ParamUtil.getString(
			portletRequest, "targetExtension");

		PortletPreferences portletPreferences = portletRequest.getPreferences();

		String[] allowedExtensions = StringUtil.split(
			portletPreferences.getValue("extensions", null));

		String languageId = LanguageUtil.getLanguageId(portletRequest);
		PortletRequestModel portletRequestModel = new PortletRequestModel(
			portletRequest, portletResponse);
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);
		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			portletResponse);

		JournalArticleDisplay articleDisplay =
			JournalContentUtil.getDisplay(
				groupId, articleId, null, "export", languageId, 1,
				portletRequestModel, themeDisplay);

		int pages = articleDisplay.getNumberOfPages();

		StringBundler sb = new StringBundler(pages + 12);

		sb.append("<html>");

		sb.append("<head>");
		sb.append("<meta content=\"");
		sb.append(ContentTypes.TEXT_HTML_UTF8);
		sb.append("\" http-equiv=\"content-type\" />");
		sb.append("<base href=\"");
		sb.append(themeDisplay.getPortalURL());
		sb.append("\" />");
		sb.append("</head>");

		sb.append("<body>");

		sb.append(articleDisplay.getContent());

		for (int i = 2; i <= pages; i++) {
			articleDisplay = JournalContentUtil.getDisplay(
				groupId, articleId, "export", languageId, i, themeDisplay);

			sb.append(articleDisplay.getContent());
		}

		sb.append("</body>");
		sb.append("</html>");

		InputStream is = new UnsyncByteArrayInputStream(
			sb.toString().getBytes(StringPool.UTF8));

		String title = articleDisplay.getTitle();
		String sourceExtension = "html";

		String fileName = title.concat(StringPool.PERIOD).concat(
			sourceExtension);

		String contentType = MimeTypesUtil.getContentType(fileName);

		if (Validator.isNull(targetExtension) ||
			!ArrayUtil.contains(allowedExtensions, targetExtension)) {

			ServletResponseUtil.sendFile(
				request, response, fileName, is, contentType);

			return;
		}

		String id = DLUtil.getTempFileId(
			articleDisplay.getId(), String.valueOf(articleDisplay.getVersion()),
			languageId);

		File convertedFile = DocumentConversionUtil.convert(
			id, is, sourceExtension, targetExtension);

		if (convertedFile != null) {
			fileName = title.concat(StringPool.PERIOD).concat(targetExtension);

			is = new FileInputStream(convertedFile);
		}

		ServletResponseUtil.sendFile(
			request, response, fileName, is, contentType);
	}

}