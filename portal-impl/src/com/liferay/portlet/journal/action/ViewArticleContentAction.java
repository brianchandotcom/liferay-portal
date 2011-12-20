/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.webserver.WebServerServletTokenUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.service.JournalArticleImageLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;

import java.io.File;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class ViewArticleContentAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			String cmd = ParamUtil.getString(request, Constants.CMD);

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(request, "groupId");
			String articleId = ParamUtil.getString(request, "articleId");
			double version = ParamUtil.getDouble(
				request, "version", JournalArticleConstants.VERSION_DEFAULT);

			String languageId = LanguageUtil.getLanguageId(request);

			String output = null;

			if (cmd.equals(Constants.PREVIEW)) {
				JournalArticle article = JournalArticleServiceUtil.getArticle(
					groupId, articleId, version);

				output = JournalArticleLocalServiceUtil.getArticleContent(
					article, article.getTemplateId(), null, languageId,
					themeDisplay);
			}
			else {
				output = JournalArticleServiceUtil.getArticleContent(
					groupId, articleId, version, languageId, themeDisplay);
			}

			request.setAttribute(WebKeys.JOURNAL_ARTICLE_CONTENT, output);

			if (output.startsWith("<?xml ")) {
				return mapping.findForward(
					"portlet.journal.raw_article_content");
			}
			else {
				return mapping.findForward(
					"portlet.journal.view_article_content");
			}
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	protected void format(
			long groupId, String articleId, double version,
			String previewArticleId, Element root,
			UploadServletRequest uploadServletRequest)
		throws Exception {

		Iterator<Element> itr = root.elements().iterator();

		while (itr.hasNext()) {
			Element el = itr.next();

			Element dynamicContent = el.element("dynamic-content");

			String elInstanceId = el.attributeValue(
				"instance-id", StringPool.BLANK);
			String elName = el.attributeValue("name", StringPool.BLANK);
			String elType = el.attributeValue("type", StringPool.BLANK);
			String elContent = StringPool.BLANK;
			String elLanguage = StringPool.BLANK;

			if (dynamicContent != null) {
				elContent = dynamicContent.getTextTrim();

				elLanguage = dynamicContent.attributeValue(
					"language-id", StringPool.BLANK);

				if (!elLanguage.equals(StringPool.BLANK)) {
					elLanguage = "_" + elLanguage;
				}
			}

			if (elType.equals("image") && Validator.isNull(elContent)) {
				File file = uploadServletRequest.getFile(
					"structure_image_" + elName + elLanguage);
				byte[] bytes = FileUtil.getBytes(file);

				if ((bytes != null) && (bytes.length > 0)) {
					long imageId =
						JournalArticleImageLocalServiceUtil.getArticleImageId(
							groupId, previewArticleId, version, elInstanceId,
							elName, elLanguage, true);

					String token = WebServerServletTokenUtil.getToken(imageId);

					dynamicContent.setText(
						"/image/journal/article?img_id=" + imageId + "&t=" +
							token);

					ImageLocalServiceUtil.updateImage(imageId, bytes);
				}
				else {
					if (Validator.isNotNull(articleId)) {
						long imageId = JournalArticleImageLocalServiceUtil.
							getArticleImageId(
								groupId, articleId, version, elInstanceId,
								elName, elLanguage);

						String token = WebServerServletTokenUtil.getToken(
							imageId);

						dynamicContent.setText(
							"/image/journal/article?img_id=" + imageId +
								"&t=" + token);
					}
				}
			}

			format(
				groupId, articleId, version, previewArticleId, el,
				uploadServletRequest);
		}
	}

}