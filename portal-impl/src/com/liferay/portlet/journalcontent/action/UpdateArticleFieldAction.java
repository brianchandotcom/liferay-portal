/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class UpdateArticleFieldAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			updateArticleField(request, response);

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	protected void updateArticleField(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		long groupId = ParamUtil.getLong(request, "groupId");
		String articleId = ParamUtil.getString(request, "articleId");
		double version = ParamUtil.getDouble(request, "version");

		String containerId = ParamUtil.getString(request, "containerId");

		if (Validator.isNotNull(containerId)) {
			int x = containerId.indexOf("_");
			int y = containerId.lastIndexOf("_");

			if ((x != -1) && (y != -1)) {
				groupId = GetterUtil.getLong(containerId.substring(0, x));
				articleId = containerId.substring(x + 1, y);
				version = GetterUtil.getDouble(
					containerId.substring(y, containerId.length()));
			}
		}

		String languageId = LanguageUtil.getLanguageId(request);

		String fieldName = ParamUtil.getString(request, "fieldName");
		String fieldData = ParamUtil.getString(request, "fieldData");

		if (fieldName.startsWith("journal-content-field-name-")) {
			fieldName = fieldName.substring(27);
		}

		JournalArticle article = JournalArticleLocalServiceUtil.getArticle(
			groupId, articleId, version);

		String content = article.getContent();

		Document doc = SAXReaderUtil.read(content);

		if (_log.isDebugEnabled()) {
			_log.debug("Before\n" + content);
		}

		String path =
			"/root/dynamic-element[@name='" + fieldName +
				"']/dynamic-content[@language-id='" + languageId + "']";

		Node node = doc.selectSingleNode(path);

		if (node == null) {
			path =
				"/root/dynamic-element[@name='" + fieldName +
					"']/dynamic-content";

			node = doc.selectSingleNode(path);
		}

		node.setText(fieldData);

		content = DDMXMLUtil.formatXML(doc);

		if (_log.isDebugEnabled()) {
			_log.debug("After\n" + content);
		}

		JournalArticleServiceUtil.updateContent(
			groupId, articleId, version, content);

		ServletResponseUtil.write(response, fieldData);
	}

	private static Log _log = LogFactoryUtil.getLog(
		UpdateArticleFieldAction.class);

}