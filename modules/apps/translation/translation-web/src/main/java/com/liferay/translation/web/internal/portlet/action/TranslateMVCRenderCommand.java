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

package com.liferay.translation.web.internal.portlet.action;

import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.translation.constants.TranslationActionKeys;
import com.liferay.translation.constants.TranslationConstants;
import com.liferay.translation.constants.TranslationPortletKeys;
import com.liferay.translation.info.field.TranslationInfoFieldChecker;
import com.liferay.translation.info.item.provider.InfoItemLanguagesProvider;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.service.TranslationEntryLocalService;
import com.liferay.translation.translator.Translator;
import com.liferay.translation.translator.TranslatorRegistry;
import com.liferay.translation.web.internal.display.context.TranslateDisplayContext;
import com.liferay.translation.web.internal.helper.TranslationRequestHelper;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
	property = {
		"javax.portlet.name=" + TranslationPortletKeys.TRANSLATION,
		"mvc.command.name=/translation/translate"
	},
	service = MVCRenderCommand.class
)
public class TranslateMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			long segmentsExperienceId = ParamUtil.getLong(
				renderRequest, "segmentsExperienceId",
				SegmentsExperienceConstants.ID_DEFAULT);

			TranslationRequestHelper translationRequestHelper =
				new TranslationRequestHelper(
					_infoItemServiceTracker, renderRequest);

			String className = translationRequestHelper.getClassName(
				segmentsExperienceId);

			long classPK = translationRequestHelper.getClassPK(
				segmentsExperienceId);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			_translator = _translatorRegistry.getCompanyTranslator(
				themeDisplay.getCompanyId());

			Object object = _getInfoItem(className, classPK);

			if (object == null) {
				return _getErrorJSP(
					renderRequest, renderResponse, translationRequestHelper);
			}

			InfoItemLanguagesProvider<Object> infoItemLanguagesProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemLanguagesProvider.class, className);

			if (infoItemLanguagesProvider == null) {
				return _getErrorJSP(
					renderRequest, renderResponse, translationRequestHelper);
			}

			List<String> availableSourceLanguageIds = Arrays.asList(
				infoItemLanguagesProvider.getAvailableLanguageIds(object));

			String sourceLanguageId = ParamUtil.getString(
				renderRequest, "sourceLanguageId",
				infoItemLanguagesProvider.getDefaultLanguageId(object));

			List<String> availableTargetLanguageIds =
				_getAvailableTargetLanguageIds(
					className, object, sourceLanguageId, themeDisplay);

			InfoItemFormProvider<Object> infoItemFormProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFormProvider.class, className);

			if (infoItemFormProvider == null) {
				return _getErrorJSP(
					renderRequest, renderResponse, translationRequestHelper);
			}

			InfoForm infoForm = infoItemFormProvider.getInfoForm(object);

			InfoItemFieldValues sourceInfoItemFieldValues =
				_getSourceInfoItemFieldValues(className, object);

			if (sourceInfoItemFieldValues == null) {
				return _getErrorJSP(
					renderRequest, renderResponse, translationRequestHelper);
			}

			String targetLanguageId = ParamUtil.getString(
				renderRequest, "targetLanguageId",
				_getDefaultTargetLanguageId(availableTargetLanguageIds));

			InfoItemFieldValues targetInfoItemFieldValues =
				_getTargetInfoItemFieldValues(
					className, classPK, sourceInfoItemFieldValues,
					targetLanguageId);

			renderRequest.setAttribute(
				TranslateDisplayContext.class.getName(),
				new TranslateDisplayContext(
					availableSourceLanguageIds, availableTargetLanguageIds,
					() -> _translator != null,
					translationRequestHelper.getModelClassName(),
					translationRequestHelper.getModelClassPK(), infoForm,
					_portal.getLiferayPortletRequest(renderRequest),
					_portal.getLiferayPortletResponse(renderResponse), object,
					segmentsExperienceId, sourceInfoItemFieldValues,
					sourceLanguageId, targetInfoItemFieldValues,
					targetLanguageId, _translationInfoFieldChecker));

			return "/translate.jsp";
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	private <T> List<String> _getAvailableTargetLanguageIds(
			String className, T object, String sourceLanguageId,
			ThemeDisplay themeDisplay)
		throws PortalException {

		InfoItemPermissionProvider infoItemPermissionProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemPermissionProvider.class, className);

		if (infoItemPermissionProvider == null) {
			return Collections.emptyList();
		}

		boolean hasUpdatePermission = infoItemPermissionProvider.hasPermission(
			themeDisplay.getPermissionChecker(), object, ActionKeys.UPDATE);

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

	private String _getErrorJSP(
			RenderRequest renderRequest, RenderResponse renderResponse,
			TranslationRequestHelper translationRequestHelper)
		throws PortalException {

		renderRequest.setAttribute(
			TranslateDisplayContext.class.getName(),
			new TranslateDisplayContext(
				Collections.emptyList(), Collections.emptyList(),
				() -> _translator != null,
				translationRequestHelper.getModelClassName(),
				translationRequestHelper.getModelClassPK(), null,
				_portal.getLiferayPortletRequest(renderRequest),
				_portal.getLiferayPortletResponse(renderResponse), null,
				SegmentsExperienceConstants.ID_DEFAULT, null, null, null, null,
				_translationInfoFieldChecker));

		return "/translate.jsp";
	}

	private Object _getInfoItem(String className, long classPK) {
		try {
			InfoItemObjectProvider<Object> infoItemObjectProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemObjectProvider.class, className);

			if (infoItemObjectProvider == null) {
				return null;
			}

			return infoItemObjectProvider.getInfoItem(classPK);
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchInfoItemException);
			}

			return null;
		}
	}

	private <T> InfoItemFieldValues _getSourceInfoItemFieldValues(
		String className, T object) {

		InfoItemFieldValuesProvider<T> infoItemFieldValuesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, className);

		if (infoItemFieldValuesProvider == null) {
			return null;
		}

		return infoItemFieldValuesProvider.getInfoItemFieldValues(object);
	}

	private <T> InfoItemFieldValues _getTargetInfoItemFieldValues(
			String className, long classPK,
			InfoItemFieldValues infoItemFieldValues, String targetLanguageId)
		throws PortalException {

		TranslationEntry translationEntry =
			_translationEntryLocalService.fetchTranslationEntry(
				className, classPK, targetLanguageId);

		if ((translationEntry == null) || translationEntry.isApproved()) {
			return infoItemFieldValues;
		}

		InfoItemFieldValues translationEntryInfoItemFieldValues =
			_translationEntryLocalService.getInfoItemFieldValues(
				translationEntry.getGroupId(), translationEntry.getClassName(),
				translationEntry.getClassPK(), translationEntry.getContent());

		Collection<InfoFieldValue<Object>> infoFieldValues =
			infoItemFieldValues.getInfoFieldValues();

		Stream<InfoFieldValue<Object>> stream = infoFieldValues.stream();

		return InfoItemFieldValues.builder(
		).infoItemReference(
			infoItemFieldValues.getInfoItemReference()
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

	private static final Log _log = LogFactoryUtil.getLog(
		TranslateMVCRenderCommand.class);

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private Portal _portal;

	@Reference
	private TranslationEntryLocalService _translationEntryLocalService;

	@Reference
	private TranslationInfoFieldChecker _translationInfoFieldChecker;

	private Translator _translator;

	@Reference
	private TranslatorRegistry _translatorRegistry;

}