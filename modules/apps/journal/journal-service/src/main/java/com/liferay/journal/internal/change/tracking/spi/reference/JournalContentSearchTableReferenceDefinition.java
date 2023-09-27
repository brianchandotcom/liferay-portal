/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.journal.model.JournalContentSearchTable;
import com.liferay.journal.service.persistence.JournalContentSearchPersistence;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.model.PortletPreferencesTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class JournalContentSearchTableReferenceDefinition
	implements TableReferenceDefinition<JournalContentSearchTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<JournalContentSearchTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<JournalContentSearchTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			JournalContentSearchTable.INSTANCE
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				LayoutTable.INSTANCE
			).innerJoinON(
				JournalContentSearchTable.INSTANCE,
				JournalContentSearchTable.INSTANCE.groupId.eq(
					LayoutTable.INSTANCE.groupId
				).and(
					LayoutTable.INSTANCE.privateLayout.eq(
						JournalContentSearchTable.INSTANCE.privateLayout)
				).and(
					JournalContentSearchTable.INSTANCE.layoutId.eq(
						LayoutTable.INSTANCE.layoutId)
				)
			)
		).singleColumnReference(
			JournalContentSearchTable.INSTANCE.portletId,
			PortletPreferencesTable.INSTANCE.portletId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _journalContentSearchPersistence;
	}

	@Override
	public JournalContentSearchTable getTable() {
		return JournalContentSearchTable.INSTANCE;
	}

	@Reference
	private JournalContentSearchPersistence _journalContentSearchPersistence;

}