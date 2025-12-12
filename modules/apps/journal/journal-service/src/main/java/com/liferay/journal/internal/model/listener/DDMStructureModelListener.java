/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.model.listener;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.internal.constants.JournalDestinationNames;
import com.liferay.journal.internal.util.JournalDDMStructureHelper;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Díaz
 */
@Component(service = ModelListener.class)
public class DDMStructureModelListener extends BaseModelListener<DDMStructure> {

	@Override
	public void onAfterUpdate(
			DDMStructure originalDDMStructure, DDMStructure ddmStructure)
		throws ModelListenerException {

		try {
			if ((ddmStructure.getClassNameId() != _portal.getClassNameId(
					JournalArticle.class)) ||
				(Objects.equals(
					originalDDMStructure.getStructureKey(),
					ddmStructure.getStructureKey()) &&
				 Objects.equals(
					 originalDDMStructure.getDefinition(),
					 ddmStructure.getDefinition())) ||
				_hasModifiedPredefinedValue(
					originalDDMStructure.getDDMForm(),
					ddmStructure.getDDMForm())) {

				return;
			}

			JournalServiceConfiguration journalServiceConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					JournalServiceConfiguration.class,
					originalDDMStructure.getCompanyId());

			if (journalServiceConfiguration.
					updateStructureArticlesAsynchronously()) {

				TransactionCommitCallbackUtil.registerCallback(
					() -> {
						Message message = new Message();

						message.setValues(
							HashMapBuilder.<String, Object>put(
								"ddmStructure", ddmStructure
							).put(
								"originalDDMStructure", originalDDMStructure
							).build());

						_messageBus.sendMessage(
							JournalDestinationNames.
								UPDATE_DDM_STRUCTURE_JOURNAL_ARTICLES,
							message);

						return null;
					});
			}
			else {
				JournalDDMStructureHelper journalDDMStructureHelper =
					new JournalDDMStructureHelper(
						_ddmFieldLocalService, _fieldsToDDMFormValuesConverter,
						_indexerRegistry, _journalArticleLocalService,
						_journalConverter);

				journalDDMStructureHelper.updateDDMStructureJournalArticles(
					originalDDMStructure, ddmStructure);
			}
		}
		catch (ConfigurationException configurationException) {
			throw new ModelListenerException(configurationException);
		}
	}

	@Override
	public void onBeforeRemove(DDMStructure ddmStructure)
		throws ModelListenerException {

		try {
			_journalArticleLocalService.deleteArticles(
				ddmStructure.getGroupId(), DDMStructure.class.getName(),
				ddmStructure.getStructureId());
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private boolean _hasModifiedPredefinedValue(
		DDMForm ddmForm1, DDMForm ddmForm2) {

		Map<String, DDMFormField> ddmFormFieldsMap1 =
			ddmForm1.getDDMFormFieldsMap(true);

		Map<String, DDMFormField> ddmFormFieldsMap2 =
			ddmForm2.getDDMFormFieldsMap(true);

		if (ddmFormFieldsMap1.size() != ddmFormFieldsMap2.size()) {
			return false;
		}

		for (Map.Entry<String, DDMFormField> entry :
				ddmFormFieldsMap1.entrySet()) {

			DDMFormField ddmFormField2 = ddmFormFieldsMap2.get(entry.getKey());

			if (ddmFormField2 == null) {
				return false;
			}

			DDMFormField ddmFormField1 = entry.getValue();

			if (!Objects.equals(
					ddmFormField1.getPredefinedValue(),
					ddmFormField2.getPredefinedValue())) {

				return true;
			}
		}

		return false;
	}

	@Reference
	private DDMFieldLocalService _ddmFieldLocalService;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private MessageBus _messageBus;

	@Reference
	private Portal _portal;

}