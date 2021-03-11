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

package com.liferay.portal.workflow.kaleo.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceTokenTable;
import com.liferay.portal.workflow.kaleo.model.KaleoTimerInstanceTokenTable;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTaskInstanceTokenPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class KaleoTimerInstanceTokenTableReferenceDefinition
	implements TableReferenceDefinition<KaleoTimerInstanceTokenTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<KaleoTimerInstanceTokenTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<KaleoTimerInstanceTokenTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			KaleoTimerInstanceTokenTable.INSTANCE
		).singleColumnReference(
			KaleoTimerInstanceTokenTable.INSTANCE.kaleoInstanceTokenId,
			KaleoInstanceTokenTable.INSTANCE.kaleoInstanceTokenId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kaleoTimerInstanceTokenPersistence;
	}

	@Override
	public KaleoTimerInstanceTokenTable getTable() {
		return KaleoTimerInstanceTokenTable.INSTANCE;
	}

	@Reference
	private KaleoTaskInstanceTokenPersistence
		_kaleoTimerInstanceTokenPersistence;

}