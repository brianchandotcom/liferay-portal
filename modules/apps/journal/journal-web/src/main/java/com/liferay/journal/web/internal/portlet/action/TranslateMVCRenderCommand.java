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

import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.constants.JournalWebConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.translation.info.field.TranslationInfoFieldChecker;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
			InfoItemFormProvider<JournalArticle> infoItemFormProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFormProvider.class, JournalArticle.class.getName());

			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			JournalArticle article = ActionUtil.getArticle(renderRequest);

			renderRequest.setAttribute(
				InfoForm.class.getName(),
				infoItemFormProvider.getInfoForm(article));

			InfoItemFieldValuesProvider<JournalArticle>
				infoItemFieldValuesProvider =
					_infoItemServiceTracker.getFirstInfoItemService(
						InfoItemFieldValuesProvider.class,
						JournalArticle.class.getName());

			renderRequest.setAttribute(
				InfoItemFieldValues.class.getName(),
				infoItemFieldValuesProvider.getInfoItemFieldValues(article));

			List<String> availableSourceLanguageIds = Arrays.asList(
				article.getAvailableLanguageIds());

			renderRequest.setAttribute(
				JournalWebConstants.AVAILABLE_SOURCE_LANGUAGE_IDS,
				availableSourceLanguageIds);

			String sourceLanguageId = ParamUtil.getString(
				renderRequest, "sourceLanguageId",
				article.getDefaultLanguageId());

			List<String> availableTargetLanguageIds =
				_getSiteAvailableLanguageIds(sourceLanguageId, themeDisplay);

			renderRequest.setAttribute(
				JournalWebConstants.AVAILABLE_TARGET_LANGUAGE_IDS,
				availableTargetLanguageIds);

			renderRequest.setAttribute(
				JournalWebConstants.SOURCE_LANGUAGE_ID, sourceLanguageId);
			renderRequest.setAttribute(
				JournalWebConstants.TARGET_LANGUAGE_ID,
				ParamUtil.getString(
					renderRequest, "targetLanguageId",
					availableTargetLanguageIds.get(0)));
			renderRequest.setAttribute(
				TranslationInfoFieldChecker.class.getName(),
				_translationInfoFieldChecker);
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}

		return "/translate.jsp";
	}

	private List<String> _getSiteAvailableLanguageIds(
		String sourceLanguageId, ThemeDisplay themeDisplay) {

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales(
			themeDisplay.getSiteGroupId());

		Stream<Locale> stream = availableLocales.stream();

		return stream.map(
			LocaleUtil::toLanguageId
		).filter(
			languageId -> !Objects.equals(languageId, sourceLanguageId)
		).collect(
			Collectors.toList()
		);
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private TranslationInfoFieldChecker _translationInfoFieldChecker;

}