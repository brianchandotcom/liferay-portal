/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.internal.spi.reference;

import com.liferay.change.tracking.sample.model.ChildTable;
import com.liferay.change.tracking.sample.model.GrandParentTable;
import com.liferay.change.tracking.sample.service.persistence.GrandParentPersistence;
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
public class GrandParentTableReferenceDefinition
	implements TableReferenceDefinition<GrandParentTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<GrandParentTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				ChildTable.INSTANCE
			).innerJoinON(
				GrandParentTable.INSTANCE,
				GrandParentTable.INSTANCE.grandParentId.eq(
					ChildTable.INSTANCE.grandParentId)
			));
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<GrandParentTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.parentColumnReference(
			GrandParentTable.INSTANCE.grandParentId,
			GrandParentTable.INSTANCE.parentGrandParentId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _grandParentPersistence;
	}

	@Override
	public GrandParentTable getTable() {
		return GrandParentTable.INSTANCE;
	}

	@Reference
	private GrandParentPersistence _grandParentPersistence;

}