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
import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.translation.info.item.updater.InfoItemFieldValuesUpdater;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.service.TranslationEntryLocalService;
import com.liferay.translation.util.TranslationEntryInfoItemFieldValuesHelper;

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
			JournalArticle article = ActionUtil.getArticle(actionRequest);

			InfoItemClassPKReference infoItemClassPKReference =
				new InfoItemClassPKReference(
					JournalArticle.class.getName(),
					article.getResourcePrimKey());

			InfoItemFieldValues infoItemFieldValues =
				InfoItemFieldValues.builder(
				).infoItemClassPKReference(
					infoItemClassPKReference
				).infoFieldValues(
					_getInfoFieldValues(actionRequest, article)
				).build();

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			if (serviceContext.getWorkflowAction() ==
					WorkflowConstants.ACTION_SAVE_DRAFT) {

				_translationEntryInfoItemFieldValuesHelper.
					addOrUpdateTranslationEntry(
						article.getGroupId(), infoItemClassPKReference,
						infoItemFieldValues,
						_getTargetLanguageId(actionRequest), serviceContext);
			}
			else {
				_journalArticleInfoItemFieldValuesUpdater.
					updateFromInfoItemFieldValues(article, infoItemFieldValues);

				TranslationEntry translationEntry =
					_translationEntryLocalService.fetchTranslationEntry(
						infoItemClassPKReference.getClassName(),
						infoItemClassPKReference.getClassPK(),
						_getTargetLanguageId(actionRequest));

				if (translationEntry != null) {
					_translationEntryLocalService.deleteTranslationEntry(
						translationEntry);
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			SessionErrors.add(actionRequest, exception.getClass(), exception);

			actionResponse.setRenderParameter(
				"mvcRenderCommandName", "/journal/translate");
		}
	}

	private List<InfoField> _getInfoFields(JournalArticle article) {
		InfoItemFormProvider<JournalArticle> infoItemFormProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormProvider.class, JournalArticle.class.getName());

		InfoForm infoForm = infoItemFormProvider.getInfoForm(article);

		return infoForm.getAllInfoFields();
	}

	private List<InfoFieldValue<Object>> _getInfoFieldValues(
		ActionRequest actionRequest, JournalArticle article) {

		UnicodeProperties infoFieldUnicodeProperties =
			PropertiesParamUtil.getProperties(actionRequest, "infoField--");

		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		for (InfoField infoField : _getInfoFields(article)) {
			String value = infoFieldUnicodeProperties.get(infoField.getName());

			if (value != null) {
				infoFieldValues.add(
					new InfoFieldValue<>(
						infoField,
						InfoLocalizedValue.builder(
						).value(
							_getTargetLocale(actionRequest), value
						).build()));
			}
		}

		return infoFieldValues;
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

	@Reference(
		target = "(item.class.name=com.liferay.journal.model.JournalArticle)"
	)
	private InfoItemFieldValuesUpdater<JournalArticle>
		_journalArticleInfoItemFieldValuesUpdater;

	@Reference
	private TranslationEntryInfoItemFieldValuesHelper
		_translationEntryInfoItemFieldValuesHelper;

	@Reference
	private TranslationEntryLocalService _translationEntryLocalService;

}