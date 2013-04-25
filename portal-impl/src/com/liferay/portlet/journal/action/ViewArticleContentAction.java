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

package com.liferay.portlet.journal.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.webserver.WebServerServletTokenUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.service.JournalArticleImageLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.util.PwdGenerator;

import java.io.File;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

		UploadServletRequest uploadServletRequest = null;

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
				uploadServletRequest = PortalUtil.getUploadServletRequest(
					request);

				String title = ParamUtil.getString(
					uploadServletRequest, "title");
				String description = ParamUtil.getString(
					uploadServletRequest, "description");
				String type = ParamUtil.getString(uploadServletRequest, "type");
				String structureId = ParamUtil.getString(
					uploadServletRequest, "structureId");
				String templateId = ParamUtil.getString(
					uploadServletRequest, "templateId");

				Date now = new Date();

				Date createDate = now;
				Date modifiedDate = now;
				Date displayDate = now;

				User user = PortalUtil.getUser(uploadServletRequest);

				String xml = ParamUtil.getString(uploadServletRequest, "xml");

				Document doc = SAXReaderUtil.read(xml);

				Element root = doc.getRootElement();

				String previewArticleId =
					"PREVIEW_" +
						PwdGenerator.getPassword(PwdGenerator.KEY3, 10);

				format(
					groupId, articleId, version, previewArticleId, root,
					uploadServletRequest);

				Map<String, String> tokens = JournalUtil.getTokens(
					groupId, themeDisplay);

				tokens.put("article_resource_pk", "-1");

				JournalArticle article = new JournalArticleImpl();

				article.setGroupId(groupId);
				article.setCompanyId(user.getCompanyId());
				article.setUserId(user.getUserId());
				article.setUserName(user.getFullName());
				article.setCreateDate(createDate);
				article.setModifiedDate(modifiedDate);
				article.setArticleId(articleId);
				article.setVersion(version);
				article.setTitle(title);
				article.setDescription(description);
				article.setContent(xml);
				article.setType(type);
				article.setStructureId(structureId);
				article.setTemplateId(templateId);
				article.setDisplayDate(displayDate);

				output = JournalArticleLocalServiceUtil.getArticleContent(
					article, templateId, null, languageId, themeDisplay);
			}
			else if (cmd.equals(Constants.VIEW)) {
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
		finally {
			if (uploadServletRequest != null) {
				uploadServletRequest.cleanUp();
			}
		}
	}

	protected void format(
			long groupId, String articleId, double version,
			String previewArticleId, Element root,
			UploadServletRequest uploadServletRequest)
		throws Exception {

		List<Element> elements = root.elements();

		for (Element element : elements) {
			Element dynamicContent = element.element("dynamic-content");

			String elInstanceId = element.attributeValue(
				"instance-id", StringPool.BLANK);
			String elName = element.attributeValue("name", StringPool.BLANK);
			String elType = element.attributeValue("type", StringPool.BLANK);
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
						long imageId =
							JournalArticleImageLocalServiceUtil.
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
				groupId, articleId, version, previewArticleId, element,
				uploadServletRequest);
		}
	}

}