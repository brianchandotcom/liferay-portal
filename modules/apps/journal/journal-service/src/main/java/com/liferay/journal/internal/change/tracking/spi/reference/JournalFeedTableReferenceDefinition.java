/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLinkTable;
import com.liferay.dynamic.data.mapping.model.DDMStructureTable;
import com.liferay.dynamic.data.mapping.model.DDMTemplateTable;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.model.JournalFeedTable;
import com.liferay.journal.service.persistence.JournalFeedPersistence;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.PortletPreferencesTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class JournalFeedTableReferenceDefinition
	implements TableReferenceDefinition<JournalFeedTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<JournalFeedTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				DDMStructureLinkTable.INSTANCE
			).innerJoinON(
				JournalFeedTable.INSTANCE,
				JournalFeedTable.INSTANCE.id.eq(
					DDMStructureLinkTable.INSTANCE.classPK)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.value.eq(
					JournalFeed.class.getName()
				).and(
					ClassNameTable.INSTANCE.classNameId.eq(
						DDMStructureLinkTable.INSTANCE.classNameId)
				)
			)
		).resourcePermissionReference(
			JournalFeedTable.INSTANCE.id, JournalFeed.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<JournalFeedTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			JournalFeedTable.INSTANCE
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				DDMStructureTable.INSTANCE
			).innerJoinON(
				JournalFeedTable.INSTANCE,
				JournalFeedTable.INSTANCE.DDMStructureId.eq(
					DDMStructureTable.INSTANCE.structureId
				).and(
					JournalFeedTable.INSTANCE.companyId.eq(
						DDMStructureTable.INSTANCE.companyId)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.value.eq(
					JournalArticle.class.getName()
				).and(
					ClassNameTable.INSTANCE.classNameId.eq(
						DDMStructureTable.INSTANCE.classNameId)
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				DDMTemplateTable.INSTANCE
			).innerJoinON(
				JournalFeedTable.INSTANCE,
				JournalFeedTable.INSTANCE.DDMTemplateKey.eq(
					DDMTemplateTable.INSTANCE.templateKey
				).and(
					JournalFeedTable.INSTANCE.companyId.eq(
						DDMTemplateTable.INSTANCE.companyId)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.value.eq(
					DDMStructure.class.getName()
				).and(
					ClassNameTable.INSTANCE.classNameId.eq(
						DDMTemplateTable.INSTANCE.classNameId)
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				DDMTemplateTable.INSTANCE
			).innerJoinON(
				JournalFeedTable.INSTANCE,
				JournalFeedTable.INSTANCE.DDMRendererTemplateKey.eq(
					DDMTemplateTable.INSTANCE.templateKey
				).and(
					JournalFeedTable.INSTANCE.companyId.eq(
						DDMTemplateTable.INSTANCE.companyId)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.value.eq(
					DDMStructure.class.getName()
				).and(
					ClassNameTable.INSTANCE.classNameId.eq(
						DDMTemplateTable.INSTANCE.classNameId)
				)
			)
		).singleColumnReference(
			JournalFeedTable.INSTANCE.targetPortletId,
			PortletPreferencesTable.INSTANCE.portletId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _journalFeedPersistence;
	}

	@Override
	public JournalFeedTable getTable() {
		return JournalFeedTable.INSTANCE;
	}

	@Reference
	private JournalFeedPersistence _journalFeedPersistence;

}