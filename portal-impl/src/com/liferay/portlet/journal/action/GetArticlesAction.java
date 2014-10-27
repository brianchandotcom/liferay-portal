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

package com.liferay.portlet.journal.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.portlet.journal.util.comparator.ArticleDisplayDateComparator;
import com.liferay.portlet.journal.util.comparator.ArticleModifiedDateComparator;

import java.text.DateFormat;

import java.util.ArrayList;
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
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class GetArticlesAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		try {
			List<JournalArticle> articles = getArticles(request);

			String fileName = null;
			byte[] bytes = getContent(request, articles);

			ServletResponseUtil.sendFile(
				request, response, fileName, bytes, ContentTypes.TEXT_XML_UTF8);

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	protected List<JournalArticle> getArticles(HttpServletRequest request)
		throws Exception {

		long companyId = PortalUtil.getCompanyId(request);
		long groupId = ParamUtil.getLong(request, "groupId");

		long folderId = ParamUtil.getLong(request, "folderId");

		List<Long> folderIds = new ArrayList<Long>(1);

		folderIds.add(folderId);

		String articleId = null;
		Double version = null;
		String title = null;
		String description = null;
		String content = null;
		String[] ddmStructureKeys = StringUtil.split(
			ParamUtil.getString(request, "ddmStructureKey"));
		String[] ddmTemplateKeys = StringUtil.split(
			ParamUtil.getString(request, "ddmTemplateKey"));

		Date displayDateGT = null;

		String displayDateGTParam = ParamUtil.getString(
			request, "displayDateGT");

		if (Validator.isNotNull(displayDateGTParam)) {
			DateFormat displayDateGTFormat = DateUtil.getISOFormat(
				displayDateGTParam);

			displayDateGT = GetterUtil.getDate(
				displayDateGTParam, displayDateGTFormat);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("displayDateGT is " + displayDateGT);
		}

		Date displayDateLT = null;

		String displayDateLTParam = ParamUtil.getString(
			request, "displayDateLT");

		if (Validator.isNotNull(displayDateLTParam)) {
			DateFormat displayDateLTFormat = DateUtil.getISOFormat(
				displayDateLTParam);

			displayDateLT = GetterUtil.getDate(
				displayDateLTParam, displayDateLTFormat);
		}

		if (displayDateLT == null) {
			displayDateLT = new Date();
		}

		if (_log.isDebugEnabled()) {
			_log.debug("displayDateLT is " + displayDateLT);
		}

		int status = WorkflowConstants.STATUS_APPROVED;
		Date reviewDate = null;
		boolean andOperator = true;
		int start = 0;
		int end = ParamUtil.getInteger(request, "delta", 5);
		String orderBy = ParamUtil.getString(request, "orderBy");
		String orderByCol = ParamUtil.getString(request, "orderByCol", orderBy);
		String orderByType = ParamUtil.getString(request, "orderByType");
		boolean orderByAsc = orderByType.equals("asc");

		OrderByComparator<JournalArticle> obc =
			new ArticleModifiedDateComparator(orderByAsc);

		if (orderByCol.equals("display-date")) {
			obc = new ArticleDisplayDateComparator(orderByAsc);
		}

		return JournalArticleServiceUtil.search(
			companyId, groupId, folderIds, 0, articleId, version, title,
			description, content, ddmStructureKeys, ddmTemplateKeys,
			displayDateGT, displayDateLT, status, reviewDate, andOperator,
			start, end, obc);
	}

	protected byte[] getContent(
			HttpServletRequest request, List<JournalArticle> articles)
		throws Exception {

		long groupId = ParamUtil.getLong(request, "groupId");

		String languageId = LanguageUtil.getLanguageId(request);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> tokens = JournalUtil.getTokens(
			groupId, themeDisplay);

		Document resultsDoc = SAXReaderUtil.createDocument(StringPool.UTF8);

		Element resultSetEl = resultsDoc.addElement("result-set");

		for (JournalArticle article : articles) {
			Element resultEl = resultSetEl.addElement("result");

			Document articleDoc = SAXReaderUtil.read(
				article.getContentByLocale(languageId));

			resultEl.content().add(articleDoc.getRootElement().createCopy());

			resultEl = resultEl.element("root");

			JournalUtil.addAllReservedEls(
				resultEl, tokens, article, languageId, themeDisplay);
		}

		return DDMXMLUtil.formatXML(resultsDoc).getBytes(StringPool.UTF8);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetArticlesAction.class);

}