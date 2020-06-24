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

package com.liferay.journal.web.internal.portlet.action;

import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.constants.JournalWebKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.translation.info.field.TranslationInfoFieldChecker;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ambrín Chaudhary
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/translate"
	},
	service = MVCRenderCommand.class
)
public class TranslateMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			JournalArticle article = _journalArticleLocalService.getArticle(
				themeDisplay.getScopeGroupId(),
				ParamUtil.getString(renderRequest, "articleId"));

			InfoItemFieldValuesProvider<JournalArticle>
				infoItemFieldValuesProvider =
					_infoItemServiceTracker.getFirstInfoItemService(
						InfoItemFieldValuesProvider.class,
						JournalArticle.class.getName());

			renderRequest.setAttribute(
				InfoItemFieldValues.class.getName(),
				infoItemFieldValuesProvider.getInfoItemFieldValues(article));

			renderRequest.setAttribute(
				JournalWebKeys.JOURNAL_ARTICLES, article);
			renderRequest.setAttribute(
				TranslationInfoFieldChecker.class.getName(),
				_translationInfoFieldChecker);
		}
		catch (PortalException portalException) {
			throw new PortletException(portalException);
		}

		return "/translate.jsp";
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private TranslationInfoFieldChecker _translationInfoFieldChecker;

}