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

package com.liferay.journal.web.util;

import com.liferay.document.library.kernel.document.conversion.DocumentConversionUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.ExportArticleHelper;
import com.liferay.journal.util.JournalContent;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component
public class ExportArticleHelperImpl implements ExportArticleHelper {

	@Override
	public void sendFile(
			String targetExtension, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws IOException {

		if (Validator.isNull(targetExtension)) {
			return;
		}

		long groupId = ParamUtil.getLong(portletRequest, "groupId");
		String articleId = ParamUtil.getString(portletRequest, "articleId");

		String languageId = LanguageUtil.getLanguageId(portletRequest);
		PortletRequestModel portletRequestModel = new PortletRequestModel(
			portletRequest, portletResponse);
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
		HttpServletRequest request = _portal.getHttpServletRequest(
			portletRequest);
		HttpServletResponse response = _portal.getHttpServletResponse(
			portletResponse);

		JournalArticle article = _journalArticleLocalService.fetchLatestArticle(
			groupId, articleId, WorkflowConstants.STATUS_APPROVED);

		JournalArticleDisplay articleDisplay = _journalContent.getDisplay(
			groupId, articleId, article.getVersion(), null, "export",
			languageId, 1, portletRequestModel, themeDisplay);

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
			articleDisplay = _journalContent.getDisplay(
				groupId, articleId, "export", languageId, i, themeDisplay);

			sb.append(articleDisplay.getContent());
		}

		sb.append("</body>");
		sb.append("</html>");

		String s = sb.toString();

		InputStream is = new UnsyncByteArrayInputStream(
			s.getBytes(StringPool.UTF8));

		String title = articleDisplay.getTitle();
		String sourceExtension = "html";

		String fileName = title.concat(StringPool.PERIOD).concat(
			sourceExtension);

		String contentType = ContentTypes.TEXT_HTML;

		sb = new StringBundler(3);

		sb.append(PrincipalThreadLocal.getUserId());
		sb.append(StringPool.UNDERLINE);

		String tempFileId = DLUtil.getTempFileId(
			articleDisplay.getId(), String.valueOf(articleDisplay.getVersion()),
			languageId);

		sb.append(tempFileId);

		File convertedFile = DocumentConversionUtil.convert(
			sb.toString(), is, sourceExtension, targetExtension);

		if (convertedFile != null) {
			targetExtension = StringUtil.toLowerCase(targetExtension);

			fileName = title.concat(StringPool.PERIOD).concat(targetExtension);

			contentType = MimeTypesUtil.getContentType(fileName);

			is = new FileInputStream(convertedFile);
		}

		ServletResponseUtil.sendFile(
			request, response, fileName, is, contentType);
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalContent _journalContent;

	@Reference
	private Portal _portal;

}