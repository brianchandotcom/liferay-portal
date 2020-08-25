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

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalContent;
import com.liferay.portal.events.ServicePreAction;
import com.liferay.portal.events.ThemeServicePreAction;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.util.UriInfoUtil;

import java.net.URI;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 */
public class RenderedContentValueUtil {

	public static String renderTemplate(
			DDMTemplateLocalService ddmTemplateLocalService,
			HttpServletRequest httpServletRequest,
			JournalArticleService journalArticleService,
			JournalContent journalContent, Locale locale,
			Long structuredContentId, Long templateId, UriInfo uriInfo)
		throws Exception {

		JournalArticle journalArticle = journalArticleService.getLatestArticle(
			structuredContentId);

		DDMTemplate ddmTemplate = ddmTemplateLocalService.fetchTemplate(
			templateId);

		JournalArticleDisplay journalArticleDisplay = journalContent.getDisplay(
			journalArticle.getGroupId(), journalArticle.getArticleId(),
			ddmTemplate.getTemplateKey(), null, LocaleUtil.toLanguageId(locale),
			_getThemeDisplay(httpServletRequest, journalArticle));

		String content = journalArticleDisplay.getContent();

		UriBuilder uriBuilder = UriInfoUtil.getBaseUriBuilder(uriInfo);

		URI uri = uriBuilder.replacePath(
			"/"
		).build();

		content = content.replaceAll(
			" srcset=\"/o/", " srcset=\"" + uri + "o/");
		content = content.replaceAll(" src=\"/", " src=\"" + uri);

		return content.replaceAll("[\\t\\n]", "");
	}

	private static ThemeDisplay _getThemeDisplay(
			HttpServletRequest httpServletRequest,
			JournalArticle journalArticle)
		throws Exception {

		ServicePreAction servicePreAction = new ServicePreAction();

		HttpServletResponse httpServletResponse =
			new DummyHttpServletResponse();

		servicePreAction.servicePre(
			httpServletRequest, httpServletResponse, false);

		ThemeServicePreAction themeServicePreAction =
			new ThemeServicePreAction();

		themeServicePreAction.run(httpServletRequest, httpServletResponse);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		themeDisplay.setScopeGroupId(journalArticle.getGroupId());
		themeDisplay.setSiteGroupId(journalArticle.getGroupId());

		return themeDisplay;
	}

}