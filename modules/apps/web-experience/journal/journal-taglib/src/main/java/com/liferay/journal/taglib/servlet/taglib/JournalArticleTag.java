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

package com.liferay.journal.taglib.servlet.taglib;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.taglib.util.IncludeTag;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class JournalArticleTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		PortletRequestModel portletRequestModel = new PortletRequestModel(
			portletRequest, portletResponse);

		_article = JournalArticleLocalServiceUtil.fetchLatestArticle(
			_groupId, _articleId, WorkflowConstants.STATUS_APPROVED);

		try {
			_articleDisplay = JournalArticleLocalServiceUtil.getArticleDisplay(
				_article.getGroupId(), _article.getArticleId(),
				_article.getVersion(), _ddmTemplateKey, Constants.VIEW,
				getLanguageId(), 1, portletRequestModel, themeDisplay);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get journal article display", pe);
			}

			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	public void setArticleId(String articleId) {
		_articleId = articleId;
	}

	public void setDdmTemplateKey(String ddmTemplateKey) {
		_ddmTemplateKey = ddmTemplateKey;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setShowTitle(boolean showTitle) {
		_showTitle = showTitle;
	}

	@Override
	protected void cleanUp() {
		_article = null;
		_articleId = null;
		_ddmTemplateKey = null;
		_groupId = 0;
		_languageId = null;
		_showTitle = false;
	}

	protected String getDdmTemplateKey() {
		if (Validator.isNotNull(_ddmTemplateKey)) {
			return _ddmTemplateKey;
		}

		return _article.getDDMTemplateKey();
	}

	protected String getLanguageId() {
		if (Validator.isNotNull(_languageId)) {
			return _languageId;
		}

		return LanguageUtil.getLanguageId(request);
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-journal:journal-article:articleDisplay", _articleDisplay);
		request.setAttribute(
			"liferay-journal:journal-article:showTitle",
			String.valueOf(_showTitle));
	}

	private static final String _PAGE = "/journal_article/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleTag.class);

	private JournalArticle _article;
	private JournalArticleDisplay _articleDisplay;
	private String _articleId;
	private String _ddmTemplateKey;
	private long _groupId;
	private String _languageId;
	private boolean _showTitle;

}