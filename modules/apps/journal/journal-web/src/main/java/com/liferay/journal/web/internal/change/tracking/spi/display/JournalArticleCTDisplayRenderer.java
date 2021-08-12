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
import com.liferay.change.tracking.spi.display.context.DisplayContext;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.JournalContent;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.diff.CompareVersionsException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(immediate = true, service = CTDisplayRenderer.class)
public class JournalArticleCTDisplayRenderer
	extends BaseCTDisplayRenderer<JournalArticle> {

	@Override
	public JournalArticle fetchLatestVersionedModel(
		JournalArticle journalArticle) {

		return _journalArticleLocalService.fetchLatestArticle(
			journalArticle.getResourcePrimKey());
	}

	@Override
	public String[] getAvailableLanguageIds(JournalArticle journalArticle) {
		return journalArticle.getAvailableLanguageIds();
	}

	@Override
	public String getContent(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Locale locale,
			JournalArticle journalArticle)
		throws Exception {

		return getJournalArticleContent(
			journalArticle, _journalArticleLocalService,
			_language.getLanguageId(locale), httpServletRequest,
			httpServletResponse);
	}

	@Override
	public String getDefaultLanguageId(JournalArticle journalArticle) {
		return journalArticle.getDefaultLanguageId();
	}

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

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, group, JournalPortletKeys.JOURNAL, 0, 0,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/edit_article.jsp"
		).setRedirect(
			_portal.getCurrentURL(httpServletRequest)
		).setParameter(
			"articleId", journalArticle.getArticleId()
		).setParameter(
			"groupId", journalArticle.getGroupId()
		).setParameter(
			"version", journalArticle.getVersion()
		).buildString();
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
	public String getVersionName(JournalArticle journalArticle) {
		return String.valueOf(journalArticle.getVersion());
	}

	protected static String getJournalArticleContent(
			JournalArticle journalArticle,
			JournalArticleLocalService journalArticleLocalService,
			String languageId, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		PortletRequest portletRequest =
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
		PortletResponse portletResponse =
			(PortletResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		PortletRequestModel portletRequestModel = new PortletRequestModel(
			portletRequest, portletResponse);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!journalArticleLocalService.isRenderable(
				journalArticle, portletRequestModel, themeDisplay)) {

			throw new CompareVersionsException(journalArticle.getVersion());
		}

		JournalArticleDisplay journalArticleDisplay =
			journalArticleLocalService.getArticleDisplay(
				journalArticle, null, Constants.VIEW, languageId, 1,
				portletRequestModel, themeDisplay);

		return journalArticleDisplay.getContent();
	}

	@Override
	protected void buildDisplay(DisplayBuilder<JournalArticle> displayBuilder) {
		JournalArticle journalArticle = displayBuilder.getModel();

		Locale locale = displayBuilder.getLocale();

		DisplayContext<JournalArticle> displayContext =
			displayBuilder.getDisplayContext();

		HttpServletRequest httpServletRequest =
			displayContext.getHttpServletRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		JournalArticleDisplay journalArticleDisplay =
			_journalContent.getDisplay(
				journalArticle, "", "", _language.getLanguageId(locale), 1,
				null, themeDisplay);

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
			"content", journalArticleDisplay.getContent(), false
		);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalContent _journalContent;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}