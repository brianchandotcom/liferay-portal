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
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.webserver.WebServerServletTokenUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.service.JournalArticleImageLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.util.JournalConverterUtil;
import com.liferay.portlet.journal.util.JournalUtil;

import java.io.File;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class ViewArticleContentAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		UploadPortletRequest uploadPortletRequest = null;

		try {
			String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long groupId = ParamUtil.getLong(actionRequest, "groupId");
			String articleId = ParamUtil.getString(actionRequest, "articleId");
			double version = ParamUtil.getDouble(
				actionRequest, "version",
				JournalArticleConstants.VERSION_DEFAULT);

			String languageId = LanguageUtil.getLanguageId(actionRequest);

			String output = null;

			if (cmd.equals(Constants.PREVIEW)) {
				uploadPortletRequest = PortalUtil.getUploadPortletRequest(
					actionRequest);

				String title = ParamUtil.getString(
					uploadPortletRequest, "title");
				String description = ParamUtil.getString(
					uploadPortletRequest, "description");
				String type = ParamUtil.getString(uploadPortletRequest, "type");
				String structureId = ParamUtil.getString(
					uploadPortletRequest, "structureId");
				String templateId = ParamUtil.getString(
					uploadPortletRequest, "templateId");

				Date now = new Date();

				Date createDate = now;
				Date modifiedDate = now;
				Date displayDate = now;

				User user = PortalUtil.getUser(uploadPortletRequest);

				String content = null;

				if (Validator.isNotNull(structureId)) {
					DDMStructure ddmStructure =
						DDMStructureLocalServiceUtil.fetchStructure(
							groupId,
							PortalUtil.getClassNameId(JournalArticle.class),
							structureId);

					if (ddmStructure == null) {
						ddmStructure =
							DDMStructureLocalServiceUtil.fetchStructure(
								themeDisplay.getCompanyGroupId(),
								PortalUtil.getClassNameId(JournalArticle.class),
								structureId);
					}

					ServiceContext serviceContext =
						ServiceContextFactory.getInstance(
							JournalArticle.class.getName(),
							uploadPortletRequest);

					Fields fields = DDMUtil.getFields(
						ddmStructure.getStructureId(), serviceContext);

					content = JournalConverterUtil.getContent(
						ddmStructure, fields);
				}

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
				article.setContent(content);
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

			actionRequest.setAttribute(WebKeys.JOURNAL_ARTICLE_CONTENT, output);

			if (output.startsWith("<?xml ")) {
				setForward(
					actionRequest, "portlet.journal.raw_article_content");
			}
			else {
				setForward(
					actionRequest, "portlet.journal.view_article_content");
			}
		}
		catch (Exception e) {
			SessionErrors.add(actionRequest, e.getClass());

			setForward(actionRequest, "portlet.journal.error");
		}
		finally {
			if (uploadPortletRequest != null) {
				uploadPortletRequest.cleanUp();
			}
		}
	}

	protected void format(
			long groupId, String articleId, double version,
			String previewArticleId, Element root,
			UploadPortletRequest uploadPortletRequest)
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
				File file = uploadPortletRequest.getFile(
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
				uploadPortletRequest);
		}
	}

}