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

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.translation.service.TranslationEntryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia Garcia
 */
@Component(
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=/journal/update_translation"
	},
	service = MVCActionCommand.class
)
public class UpdateTranslationMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			long groupId = ParamUtil.getLong(actionRequest, "groupId");

			long classNameId = ParamUtil.getLong(actionRequest, "classNameId");

			String className = _portal.getClassName(classNameId);

			long classPK = ParamUtil.getLong(actionRequest, "classPK");

			InfoItemReference infoItemReference = new InfoItemReference(
				className, classPK);

			InfoItemObjectProvider<Object> infoItemObjectProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemObjectProvider.class,
					infoItemReference.getClassName());

			InfoItemFieldValues infoItemFieldValues =
				InfoItemFieldValues.builder(
				).infoItemReference(
					infoItemReference
				).infoFieldValues(
					_getInfoFieldValues(
						actionRequest, className,
						infoItemObjectProvider.getInfoItem(classPK))
				).build();

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			_translationEntryService.addOrUpdateTranslationEntry(
				groupId, _getTargetLanguageId(actionRequest), infoItemReference,
				infoItemFieldValues, serviceContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			SessionErrors.add(actionRequest, exception.getClass(), exception);

			actionResponse.setRenderParameter(
				"mvcRenderCommandName", "/journal/translate");
		}
	}

	private <T> List<InfoField> _getInfoFields(String className, T object) {
		InfoItemFormProvider<T> infoItemFormProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormProvider.class, className);

		InfoForm infoForm = infoItemFormProvider.getInfoForm(object);

		return infoForm.getAllInfoFields();
	}

	private <T> List<InfoFieldValue<Object>> _getInfoFieldValues(
		ActionRequest actionRequest, String className, T object) {

		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		UnicodeProperties infoFieldUnicodeProperties =
			PropertiesParamUtil.getProperties(actionRequest, "infoField--");
		InfoItemFieldValues infoItemFieldValues = _getInfoItemFieldValues(
			className, object);

		for (InfoField infoField : _getInfoFields(className, object)) {
			String value = infoFieldUnicodeProperties.get(infoField.getName());

			if (value != null) {
				Locale sourceLocale = _getSourceLocale(actionRequest);

				infoFieldValues.add(
					new InfoFieldValue<>(
						infoField,
						InfoLocalizedValue.builder(
						).value(
							_getTargetLocale(actionRequest), value
						).value(
							biConsumer -> {
								InfoFieldValue<Object> infoFieldValue =
									infoItemFieldValues.getInfoFieldValue(
										infoField.getName());

								biConsumer.accept(
									sourceLocale,
									infoFieldValue.getValue(sourceLocale));
							}
						).build()));
			}
		}

		return infoFieldValues;
	}

	private <T> InfoItemFieldValues _getInfoItemFieldValues(
		String className, T object) {

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, className);

		return infoItemFieldValuesProvider.getInfoItemFieldValues(object);
	}

	private String _getSourceLanguageId(ActionRequest actionRequest) {
		return ParamUtil.getString(actionRequest, "sourceLanguageId");
	}

	private Locale _getSourceLocale(ActionRequest actionRequest) {
		return LocaleUtil.fromLanguageId(_getSourceLanguageId(actionRequest));
	}

	private String _getTargetLanguageId(ActionRequest actionRequest) {
		return ParamUtil.getString(actionRequest, "targetLanguageId");
	}

	private Locale _getTargetLocale(ActionRequest actionRequest) {
		return LocaleUtil.fromLanguageId(_getTargetLanguageId(actionRequest));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateTranslationMVCActionCommand.class);

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private Portal _portal;

	@Reference
	private TranslationEntryService _translationEntryService;

}