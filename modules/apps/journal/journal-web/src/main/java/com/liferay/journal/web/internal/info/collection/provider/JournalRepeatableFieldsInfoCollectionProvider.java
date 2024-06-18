/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.info.collection.provider;

import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.RepeatableFieldInfoItemCollectionProvider;
import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.RepeatableInfoFieldValue;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.RepeatableInfoFieldValueInfoItemIdentifier;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.RepeatableFieldsInfoItemFormProvider;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	property = "item.class.name=com.liferay.journal.model.JournalArticle",
	service = RepeatableFieldInfoItemCollectionProvider.class
)
public class JournalRepeatableFieldsInfoCollectionProvider
	implements RepeatableFieldInfoItemCollectionProvider {

	@Override
	public InfoPage<RepeatableInfoFieldValue> getCollectionInfoPage(
		CollectionQuery collectionQuery) {

		Object relatedItem = collectionQuery.getRelatedItem();

		if (!(relatedItem instanceof JournalArticle)) {
			return InfoPage.of(Collections.emptyList());
		}

		List<RepeatableInfoFieldValue> repeatableInfoFieldValues =
			new ArrayList<>();

		try {
			JournalArticle journalArticle = (JournalArticle)relatedItem;

			RepeatableFieldsInfoItemFormProvider<?>
				repeatableFieldsInfoItemFormProvider =
					_infoItemServiceRegistry.getFirstInfoItemService(
						RepeatableFieldsInfoItemFormProvider.class,
						JournalArticle.class.getName());

			InfoItemFieldValuesProvider<JournalArticle>
				infoItemFieldValuesProvider =
					_infoItemServiceRegistry.getFirstInfoItemService(
						InfoItemFieldValuesProvider.class,
						JournalArticle.class.getName());

			InfoItemFieldValues infoItemFieldValues =
				infoItemFieldValuesProvider.getInfoItemFieldValues(
					(JournalArticle)relatedItem);

			InfoForm infoForm =
				repeatableFieldsInfoItemFormProvider.
					getRepeatableFieldsInfoForm(
						String.valueOf(journalArticle.getDDMStructureId()));

			Map<String, String[]> configuration =
				collectionQuery.getConfiguration();

			String[] fieldNames = configuration.get("fieldNames");

			InfoFieldSetEntry mainInfoFieldSetEntry =
				infoForm.getInfoFieldSetEntry(fieldNames[0]);

			List<InfoFieldSetEntry> infoFieldSetEntries = new ArrayList<>();

			if (mainInfoFieldSetEntry instanceof InfoFieldSet) {
				InfoFieldSet infoFieldSet = (InfoFieldSet)mainInfoFieldSetEntry;

				infoFieldSetEntries.addAll(infoFieldSet.getAllInfoFields());
			}
			else {
				mainInfoFieldSetEntry = infoForm.getInfoField(fieldNames[0]);

				infoFieldSetEntries.add(mainInfoFieldSetEntry);
			}

			Map<String, List<InfoFieldValue<Object>>>
				infoFieldValuesByInfoFieldId = new HashMap<>();

			int numberOfRepeatableFields = 0;

			for (InfoFieldSetEntry infoFieldSetEntry : infoFieldSetEntries) {
				Collection<InfoFieldValue<Object>> infoFieldValues =
					infoItemFieldValues.getInfoFieldValues(
						infoFieldSetEntry.getUniqueId());

				infoFieldValuesByInfoFieldId.put(
					infoFieldSetEntry.getUniqueId(),
					new ArrayList<>(infoFieldValues));

				numberOfRepeatableFields = infoFieldValues.size();
			}

			for (int i = 0; i < numberOfRepeatableFields; i++) {
				List<String> infoFieldNames = new ArrayList<>();
				List<InfoFieldValue<Object>> infoFieldValues =
					new ArrayList<>();

				for (InfoFieldSetEntry infoFieldSetEntry :
						infoFieldSetEntries) {

					List<InfoFieldValue<Object>> currentInfoFieldValues =
						infoFieldValuesByInfoFieldId.get(
							infoFieldSetEntry.getUniqueId());

					infoFieldNames.add(infoFieldSetEntry.getUniqueId());
					infoFieldValues.add(currentInfoFieldValues.get(i));
				}

				repeatableInfoFieldValues.add(
					new RepeatableInfoFieldValue(
						new InfoItemReference(
							RepeatableInfoFieldValue.class.getName(),
							new RepeatableInfoFieldValueInfoItemIdentifier(
								infoFieldNames, i,
								new InfoItemReference(
									JournalArticle.class.getName(),
									journalArticle.getResourcePrimKey()))),
						infoFieldValues));
			}

			Pagination pagination = collectionQuery.getPagination();

			repeatableInfoFieldValues = ListUtil.subList(
				repeatableInfoFieldValues, pagination.getStart(),
				pagination.getEnd());

			return InfoPage.of(
				repeatableInfoFieldValues, collectionQuery.getPagination(),
				numberOfRepeatableFields);
		}
		catch (NoSuchFormVariationException noSuchFormVariationException) {
			_log.error(noSuchFormVariationException);

			return InfoPage.of(Collections.emptyList());
		}
	}

	@Override
	public String getLabel(Locale locale) {
		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalRepeatableFieldsInfoCollectionProvider.class);

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

}