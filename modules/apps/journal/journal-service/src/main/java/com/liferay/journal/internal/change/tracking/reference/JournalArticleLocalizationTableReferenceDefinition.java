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

package com.liferay.journal.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.journal.model.JournalArticleLocalizationTable;
import com.liferay.journal.model.JournalArticleTable;
import com.liferay.journal.service.persistence.JournalArticleLocalizationPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class JournalArticleLocalizationTableReferenceDefinition
	implements TableReferenceDefinition<JournalArticleLocalizationTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<JournalArticleLocalizationTable>
			tableTableReferenceInfoBuilder) {

		tableTableReferenceInfoBuilder.singleColumnReference(
			JournalArticleLocalizationTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			JournalArticleLocalizationTable.INSTANCE.articlePK,
			JournalArticleTable.INSTANCE.id
		).nonreferenceColumns(
			JournalArticleLocalizationTable.INSTANCE.title,
			JournalArticleLocalizationTable.INSTANCE.description,
			JournalArticleLocalizationTable.INSTANCE.languageId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _journalArticleLocalizationPersistence;
	}

	@Override
	public JournalArticleLocalizationTable getTable() {
		return JournalArticleLocalizationTable.INSTANCE;
	}

	@Reference
	private JournalArticleLocalizationPersistence
		_journalArticleLocalizationPersistence;

}