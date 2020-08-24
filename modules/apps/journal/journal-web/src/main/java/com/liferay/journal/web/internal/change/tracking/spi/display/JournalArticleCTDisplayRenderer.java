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

package com.liferay.journal.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.util.JournalContent;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class JournalArticleCTDisplayRenderer
	extends BaseCTDisplayRenderer<JournalArticle> {

	@Override
	public String getEditURL(
			HttpServletRequest httpServletRequest,
			JournalArticle journalArticle)
		throws PortalException {

		Group group = _groupLocalService.getGroup(journalArticle.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest, group, JournalPortletKeys.JOURNAL, 0, 0,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/edit_article.jsp");
		portletURL.setParameter(
			"redirect", _portal.getCurrentURL(httpServletRequest));
		portletURL.setParameter(
			"groupId", String.valueOf(journalArticle.getGroupId()));
		portletURL.setParameter("articleId", journalArticle.getArticleId());
		portletURL.setParameter(
			"version", String.valueOf(journalArticle.getVersion()));

		return portletURL.toString();
	}

	@Override
	public Class<JournalArticle> getModelClass() {
		return JournalArticle.class;
	}

	@Override
	public String getTitle(Locale locale, JournalArticle journalArticle) {
		return journalArticle.getTitle(locale);
	}

	@Override
	protected void buildDisplay(DisplayBuilder<JournalArticle> displayBuilder) {
		JournalArticle journalArticle = displayBuilder.getModel();

		Locale locale = displayBuilder.getLocale();

		JournalArticleDisplay journalContentDisplay =
			_journalContent.getDisplay(
				journalArticle, "", "", _language.getLanguageId(locale), 1,
				null, null);

		displayBuilder.display(
			"name", journalArticle.getTitle(locale)
		).display(
			"description", journalArticle.getDescription(locale)
		).display(
			"created-by",
			() -> {
				String userName = journalArticle.getUserName();

				if (Validator.isNotNull(userName)) {
					return userName;
				}

				return null;
			}
		).display(
			"create-date", journalArticle.getCreateDate()
		).display(
			"last-modified", journalArticle.getModifiedDate()
		).display(
			"version", journalArticle.getVersion()
		).display(
			"structure",
			() -> {
				DDMStructure ddmStructure = journalArticle.getDDMStructure();

				return ddmStructure.getName(locale);
			}
		).display(
			"template",
			() -> {
				DDMTemplate ddmTemplate = journalArticle.getDDMTemplate();

				return ddmTemplate.getName(locale);
			}
		).display(
			"content", journalContentDisplay.getContent(), false
		);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JournalContent _journalContent;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}