/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.internal.spi.reference;

import com.liferay.change.tracking.sample.model.ChildTable;
import com.liferay.change.tracking.sample.model.GrandParentTable;
import com.liferay.change.tracking.sample.service.persistence.ChildPersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = TableReferenceDefinition.class)
public class ChildTableReferenceDefinition
	implements TableReferenceDefinition<ChildTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<ChildTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<ChildTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				GrandParentTable.INSTANCE
			).innerJoinON(
				ChildTable.INSTANCE,
				ChildTable.INSTANCE.grandParentId.eq(
					GrandParentTable.INSTANCE.grandParentId)
			)
		).referenceInnerJoin(
			fromStep -> {
				ChildTable aliasChildTable = ChildTable.INSTANCE.as(
					"aliasChildTable");

				return fromStep.from(
					aliasChildTable
				).innerJoinON(
					ChildTable.INSTANCE,
					ChildTable.INSTANCE.parentChildId.eq(
						aliasChildTable.childId)
				);
			}
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _childPersistence;
	}

	@Override
	public ChildTable getTable() {
		return ChildTable.INSTANCE;
	}

	@Reference
	private ChildPersistence _childPersistence;

}