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

package com.liferay.journal.internal.model.listener;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.util.Portal;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Díaz
 */
@Component(service = ModelListener.class)
public class DDMStructureModelListener extends BaseModelListener<DDMStructure> {

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

	@Override
	public void onBeforeUpdate(
			DDMStructure originalDDMStructure, DDMStructure ddmStructure)
		throws ModelListenerException {

		if ((ddmStructure.getClassNameId() != _portal.getClassNameId(
				JournalArticle.class)) ||
			(Objects.equals(
				originalDDMStructure.getStructureKey(),
				ddmStructure.getStructureKey()) &&
			 Objects.equals(
				 originalDDMStructure.getDefinition(),
				 ddmStructure.getDefinition()))) {

			return;
		}

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
		actionableDynamicQuery.setGroupId(originalDDMStructure.getGroupId());

		if (!Objects.equals(
				originalDDMStructure.getStructureKey(),
				ddmStructure.getStructureKey())) {

			Indexer<JournalArticle> indexer =
				_indexerRegistry.nullSafeGetIndexer(JournalArticle.class);

			actionableDynamicQuery.setPerformActionMethod(
				(JournalArticle journalArticle) -> {
					try {
						indexer.reindex(journalArticle);
					}
					catch (Exception exception) {
						throw new PortalException(exception);
					}
				});
		}
		else {
			actionableDynamicQuery.setPerformActionMethod(
				(JournalArticle journalArticle) ->
					_ddmFieldLocalService.updateDDMFormValues(
						ddmStructure.getStructureId(), journalArticle.getId(),
						_fieldsToDDMFormValuesConverter.convert(
							ddmStructure,
							_journalConverter.getDDMFields(
								ddmStructure, journalArticle.getContent()))));
		}

		try {
			actionableDynamicQuery.performActions();
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
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
	private Portal _portal;

}