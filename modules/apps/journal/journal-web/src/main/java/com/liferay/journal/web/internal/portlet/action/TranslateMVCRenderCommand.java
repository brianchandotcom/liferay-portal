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

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.constants.JournalWebConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.translation.constants.TranslationActionKeys;
import com.liferay.translation.constants.TranslationConstants;
import com.liferay.translation.info.field.TranslationInfoFieldChecker;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.service.TranslationEntryLocalService;

import java.util.Arrays;
import java.util.Collection;
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

			InfoItemFieldValues sourceInfoItemFieldValues =
				_getSourceInfoItemFieldValues(article);

			renderRequest.setAttribute(
				JournalWebConstants.SOURCE_INFO_ITEM_FIELD_VALUES,
				sourceInfoItemFieldValues);

			String sourceLanguageId = ParamUtil.getString(
				renderRequest, "sourceLanguageId",
				article.getDefaultLanguageId());

			List<String> availableTargetLanguageIds =
				_getAvailableTargetLanguageIds(
					article, sourceLanguageId, themeDisplay);

			String targetLanguageId = ParamUtil.getString(
				renderRequest, "targetLanguageId",
				_getDefaultTargetLanguageId(availableTargetLanguageIds));

			renderRequest.setAttribute(
				JournalWebConstants.TARGET_INFO_ITEM_FIELD_VALUES,
				_getTargetInfoItemFieldValues(
					article, sourceInfoItemFieldValues, targetLanguageId));

			List<String> availableSourceLanguageIds = Arrays.asList(
				article.getAvailableLanguageIds());

			renderRequest.setAttribute(
				JournalWebConstants.AVAILABLE_SOURCE_LANGUAGE_IDS,
				availableSourceLanguageIds);

			renderRequest.setAttribute(
				JournalWebConstants.AVAILABLE_TARGET_LANGUAGE_IDS,
				availableTargetLanguageIds);
			renderRequest.setAttribute(
				JournalWebConstants.SOURCE_LANGUAGE_ID, sourceLanguageId);
			renderRequest.setAttribute(
				JournalWebConstants.TARGET_LANGUAGE_ID, targetLanguageId);
			renderRequest.setAttribute(
				TranslationInfoFieldChecker.class.getName(),
				_translationInfoFieldChecker);

			return "/translate.jsp";
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	private List<String> _getAvailableTargetLanguageIds(
			JournalArticle article, String sourceLanguageId,
			ThemeDisplay themeDisplay)
		throws PortalException {

		boolean hasUpdatePermission =
			_journalArticleModelResourcePermission.contains(
				themeDisplay.getPermissionChecker(), article,
				ActionKeys.UPDATE);

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales(
			themeDisplay.getSiteGroupId());

		Stream<Locale> stream = availableLocales.stream();

		return stream.map(
			LocaleUtil::toLanguageId
		).filter(
			languageId ->
				!Objects.equals(languageId, sourceLanguageId) &&
				(hasUpdatePermission ||
				 _hasTranslatePermission(languageId, themeDisplay))
		).collect(
			Collectors.toList()
		);
	}

	private String _getDefaultTargetLanguageId(
		List<String> availableTargetLanguageIds) {

		if (availableTargetLanguageIds.isEmpty()) {
			return StringPool.BLANK;
		}

		return availableTargetLanguageIds.get(0);
	}

	private InfoItemFieldValues _getSourceInfoItemFieldValues(
		JournalArticle article) {

		InfoItemFieldValuesProvider<JournalArticle>
			infoItemFieldValuesProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFieldValuesProvider.class,
					JournalArticle.class.getName());

		return infoItemFieldValuesProvider.getInfoItemFieldValues(article);
	}

	private InfoItemFieldValues _getTargetInfoItemFieldValues(
			JournalArticle article,
			InfoItemFieldValues journalArticleInfoItemFieldValues,
			String targetLanguageId)
		throws PortalException {

		TranslationEntry translationEntry =
			_translationEntryLocalService.fetchTranslationEntry(
				JournalArticle.class.getName(), article.getResourcePrimKey(),
				targetLanguageId);

		if (translationEntry == null) {
			return journalArticleInfoItemFieldValues;
		}

		InfoItemFieldValues translationEntryInfoItemFieldValues =
			_translationEntryLocalService.getInfoItemFieldValues(
				translationEntry.getGroupId(), translationEntry.getClassName(),
				translationEntry.getClassPK(), translationEntry.getContent());

		Collection<InfoFieldValue<Object>> infoFieldValues =
			journalArticleInfoItemFieldValues.getInfoFieldValues();

		Stream<InfoFieldValue<Object>> stream = infoFieldValues.stream();

		return InfoItemFieldValues.builder(
		).infoItemReference(
			journalArticleInfoItemFieldValues.getInfoItemReference()
		).infoFieldValues(
			stream.map(
				infoFieldValue -> new InfoFieldValue<>(
					infoFieldValue.getInfoField(),
					GetterUtil.getObject(
						_getValue(
							translationEntryInfoItemFieldValues,
							infoFieldValue.getInfoField()),
						infoFieldValue.getValue()))
			).collect(
				Collectors.toList()
			)
		).build();
	}

	private Object _getValue(
		InfoItemFieldValues translationEntryInfoItemFieldValues,
		InfoField infoField) {

		InfoFieldValue<Object> infoFieldValue =
			translationEntryInfoItemFieldValues.getInfoFieldValue(
				infoField.getName());

		if (infoFieldValue != null) {
			return infoFieldValue.getValue();
		}

		return null;
	}

	private boolean _hasTranslatePermission(
		String languageId, ThemeDisplay themeDisplay) {

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		String name = TranslationConstants.RESOURCE_NAME + "." + languageId;

		return permissionChecker.hasPermission(
			themeDisplay.getScopeGroup(), name, name,
			TranslationActionKeys.TRANSLATE);
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalArticle)"
	)
	private ModelResourcePermission<JournalArticle>
		_journalArticleModelResourcePermission;

	@Reference
	private TranslationEntryLocalService _translationEntryLocalService;

	@Reference
	private TranslationInfoFieldChecker _translationInfoFieldChecker;

}