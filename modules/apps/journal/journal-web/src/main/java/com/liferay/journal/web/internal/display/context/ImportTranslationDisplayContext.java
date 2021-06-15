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

package com.liferay.journal.web.internal.display.context;

import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class ImportTranslationDisplayContext {

	public ImportTranslationDisplayContext(
			HttpServletRequest httpServletRequest,
			JournalDisplayContext journalDisplayContext,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		_httpServletRequest = httpServletRequest;
		_journalDisplayContext = journalDisplayContext;
		_liferayPortletResponse = liferayPortletResponse;

		_journalEditArticleDisplayContext =
			new JournalEditArticleDisplayContext(
				httpServletRequest, liferayPortletResponse,
				journalDisplayContext.getArticle());
	}

	public PortletURL getImportTranslationURL() throws PortalException {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/journal/import_translation"
		).setParameter(
			"classNameId", _getClassNameId()
		).setParameter(
			"classPK", _getClassPK()
		).setParameter(
			"groupId", _getGroupId()
		).build();
	}

	public String getPublishButtonLabel() throws PortalException {
		return _journalEditArticleDisplayContext.getPublishButtonLabel();
	}

	public String getRedirect() {
		if (Validator.isNotNull(_redirect)) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		return _redirect;
	}

	public String getSaveButtonLabel() {
		return _journalEditArticleDisplayContext.getSaveButtonLabel();
	}

	public String getTitle() throws PortalException {
		JournalArticle article = _journalDisplayContext.getArticle();

		return article.getTitle();
	}

	public boolean isPending() throws PortalException {
		return _journalEditArticleDisplayContext.isPending();
	}

	private long _getClassNameId() {
		return PortalUtil.getClassNameId(JournalArticle.class.getName());
	}

	private long _getClassPK() throws PortalException {
		JournalArticle article = _journalDisplayContext.getArticle();

		return article.getResourcePrimKey();
	}

	private long _getGroupId() throws PortalException {
		JournalArticle article = _journalDisplayContext.getArticle();

		return article.getGroupId();
	}

	private final HttpServletRequest _httpServletRequest;
	private final JournalDisplayContext _journalDisplayContext;
	private final JournalEditArticleDisplayContext
		_journalEditArticleDisplayContext;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _redirect;

}