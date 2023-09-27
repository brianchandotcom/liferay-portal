/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleTable;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.translation.model.TranslationEntryTable;
import com.liferay.translation.service.persistence.TranslationEntryPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = TableReferenceDefinition.class)
public class TranslationEntryTableReferenceDefinition
	implements TableReferenceDefinition<TranslationEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<TranslationEntryTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<TranslationEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				JournalArticleTable.INSTANCE
			).innerJoinON(
				TranslationEntryTable.INSTANCE,
				TranslationEntryTable.INSTANCE.classPK.eq(
					JournalArticleTable.INSTANCE.resourcePrimKey)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.value.eq(
					JournalArticle.class.getName()
				).and(
					ClassNameTable.INSTANCE.classNameId.eq(
						TranslationEntryTable.INSTANCE.classPK)
				)
			));
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _translationEntryPersistence;
	}

	@Override
	public TranslationEntryTable getTable() {
		return TranslationEntryTable.INSTANCE;
	}

	@Reference
	private TranslationEntryPersistence _translationEntryPersistence;

}