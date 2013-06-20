/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journalcontent.action;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.DocumentConversionUtil;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.util.portlet.PortletRequestUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno Farache
 */
public class ExportArticleAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		try {
			long groupId = ParamUtil.getLong(actionRequest, "groupId");
			String articleId = ParamUtil.getString(actionRequest, "articleId");

			String targetExtension = ParamUtil.getString(
				actionRequest, "targetExtension");

			PortletPreferences portletPreferences =
				actionRequest.getPreferences();

			String[] allowedExtensions = portletPreferences.getValues(
				"extensions", null);

			String languageId = LanguageUtil.getLanguageId(actionRequest);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			String xmlRequest = PortletRequestUtil.toXML(
				actionRequest, actionResponse);

			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				actionRequest);
			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				actionResponse);

			getFile(
				groupId, articleId, targetExtension, allowedExtensions,
				languageId, themeDisplay, xmlRequest, request, response);

			setForward(actionRequest, ActionConstants.COMMON_NULL);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, actionRequest, actionResponse);
		}
	}

	protected void getFile(
			long groupId, String articleId, String targetExtension,
			String[] allowedExtensions, String languageId,
			ThemeDisplay themeDisplay, String xmlRequest,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		try {
			JournalArticleDisplay articleDisplay =
				JournalContentUtil.getDisplay(
					groupId, articleId, null, "export", languageId,
					themeDisplay, 1, xmlRequest);

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
					groupId, articleId, "export", languageId, themeDisplay, i);

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

			if (Validator.isNotNull(targetExtension) &&
				ArrayUtil.contains(allowedExtensions, targetExtension)) {

				String id = DLUtil.getTempFileId(
					articleDisplay.getId(),
					String.valueOf(articleDisplay.getVersion()), languageId);

				File convertedFile = DocumentConversionUtil.convert(
					id, is, sourceExtension, targetExtension);

				if (convertedFile != null) {
					fileName = title.concat(StringPool.PERIOD).concat(
						targetExtension);

					is = new FileInputStream(convertedFile);
				}
			}

			String contentType = MimeTypesUtil.getContentType(fileName);

			ServletResponseUtil.sendFile(
				request, response, fileName, is, contentType);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

	private static Log _log = LogFactoryUtil.getLog(ExportArticleAction.class);

}