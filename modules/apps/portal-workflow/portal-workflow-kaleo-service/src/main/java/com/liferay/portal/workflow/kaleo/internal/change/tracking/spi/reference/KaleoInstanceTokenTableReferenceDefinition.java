/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceTable;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceTokenTable;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoInstanceTokenPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class KaleoInstanceTokenTableReferenceDefinition
	implements TableReferenceDefinition<KaleoInstanceTokenTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<KaleoInstanceTokenTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<KaleoInstanceTokenTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			KaleoInstanceTokenTable.INSTANCE
		).parentColumnReference(
			KaleoInstanceTokenTable.INSTANCE.kaleoInstanceTokenId,
			KaleoInstanceTokenTable.INSTANCE.parentKaleoInstanceTokenId
		).singleColumnReference(
			KaleoInstanceTokenTable.INSTANCE.kaleoInstanceId,
			KaleoInstanceTable.INSTANCE.kaleoInstanceId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kaleoInstanceTokenPersistence;
	}

	@Override
	public KaleoInstanceTokenTable getTable() {
		return KaleoInstanceTokenTable.INSTANCE;
	}

	@Reference
	private KaleoInstanceTokenPersistence _kaleoInstanceTokenPersistence;

}