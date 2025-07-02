/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.internal.spi.reference;

import com.liferay.change.tracking.sample.model.ChildTable;
import com.liferay.change.tracking.sample.model.GrandParentTable;
import com.liferay.change.tracking.sample.model.ParentTable;
import com.liferay.change.tracking.sample.service.persistence.ParentPersistence;
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
public class ParentTableReferenceDefinition
	implements TableReferenceDefinition<ParentTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<ParentTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				ChildTable.INSTANCE
			).innerJoinON(
				ParentTable.INSTANCE,
				ParentTable.INSTANCE.grandParentId.eq(
					ChildTable.INSTANCE.grandParentId
				).and(
					ParentTable.INSTANCE.name.eq(ChildTable.INSTANCE.parentName)
				)
			));
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<ParentTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				GrandParentTable.INSTANCE
			).innerJoinON(
				ParentTable.INSTANCE,
				ParentTable.INSTANCE.grandParentId.eq(
					GrandParentTable.INSTANCE.grandParentId)
			));
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _parentPersistence;
	}

	@Override
	public ParentTable getTable() {
		return ParentTable.INSTANCE;
	}

	@Reference
	private ParentPersistence _parentPersistence;

}