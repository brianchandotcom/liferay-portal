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
import com.liferay.journal.model.JournalContentSearchTable;
import com.liferay.journal.service.persistence.JournalContentSearchPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.model.PortletTable;
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
	public void defineTableReferences(
		TableReferenceInfoBuilder<JournalContentSearchTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.singleColumnReference(
			JournalContentSearchTable.INSTANCE.groupId,
			GroupTable.INSTANCE.groupId
		).singleColumnReference(
			JournalContentSearchTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
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
			PortletTable.INSTANCE.portletId
		).nonreferenceColumn(
			JournalContentSearchTable.INSTANCE.articleId
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