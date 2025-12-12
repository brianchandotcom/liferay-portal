/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.util;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;

import java.util.Objects;

/**
 * @author Adolfo Pérez
 */
public class JournalDDMStructureHelper {

	public JournalDDMStructureHelper(
		DDMFieldLocalService ddmFieldLocalService,
		FieldsToDDMFormValuesConverter fieldsToDDMFormValuesConverter,
		IndexerRegistry indexerRegistry,
		JournalArticleLocalService journalArticleLocalService,
		JournalConverter journalConverter) {

		_ddmFieldLocalService = ddmFieldLocalService;
		_fieldsToDDMFormValuesConverter = fieldsToDDMFormValuesConverter;
		_indexerRegistry = indexerRegistry;
		_journalArticleLocalService = journalArticleLocalService;
		_journalConverter = journalConverter;
	}

	public void updateDDMStructureJournalArticles(
			DDMStructure originalDDMStructure, DDMStructure ddmStructure)
		throws ModelListenerException {

		ActionableDynamicQuery actionableDynamicQuery =
			_journalArticleLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property ddmStructureIdProperty = PropertyFactoryUtil.forName(
					"DDMStructureId");

				dynamicQuery.add(
					ddmStructureIdProperty.eq(
						originalDDMStructure.getStructureId()));
			});
		actionableDynamicQuery.setCompanyId(
			originalDDMStructure.getCompanyId());

		ActionableDynamicQuery.PerformActionMethod<?> performActionMethod =
			null;

		if (Objects.equals(
				originalDDMStructure.getDefinition(),
				ddmStructure.getDefinition())) {

			Indexer<JournalArticle> indexer =
				_indexerRegistry.nullSafeGetIndexer(JournalArticle.class);

			performActionMethod = (JournalArticle journalArticle) -> {
				try {
					indexer.reindex(journalArticle);
				}
				catch (Exception exception) {
					throw new PortalException(exception);
				}
			};
		}
		else {
			performActionMethod = (JournalArticle journalArticle) -> {
				try (SafeCloseable safeCloseable =
						CTCollectionThreadLocal.
							setCTCollectionIdWithSafeCloseable(
								ddmStructure.getCtCollectionId())) {

					_ddmFieldLocalService.updateDDMFormValues(
						ddmStructure.getStructureId(), journalArticle.getId(),
						_fieldsToDDMFormValuesConverter.convert(
							ddmStructure,
							_journalConverter.getDDMFields(
								ddmStructure, journalArticle.getContent())));
				}
			};
		}

		actionableDynamicQuery.setParallel(true);
		actionableDynamicQuery.setPerformActionMethod(performActionMethod);

		try {
			actionableDynamicQuery.performActions();
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	private final DDMFieldLocalService _ddmFieldLocalService;
	private final FieldsToDDMFormValuesConverter
		_fieldsToDDMFormValuesConverter;
	private final IndexerRegistry _indexerRegistry;
	private final JournalArticleLocalService _journalArticleLocalService;
	private final JournalConverter _journalConverter;

}