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
import com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipientTable;
import com.liferay.portal.workflow.kaleo.model.KaleoNotificationTable;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoNotificationPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class KaleoNotificationTableReferenceDefinition
	implements TableReferenceDefinition<KaleoNotificationTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<KaleoNotificationTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			KaleoNotificationTable.INSTANCE.kaleoNotificationId,
			KaleoNotificationRecipientTable.INSTANCE.kaleoNotificationId);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<KaleoNotificationTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			KaleoNotificationTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kaleoNotificationPersistence;
	}

	@Override
	public KaleoNotificationTable getTable() {
		return KaleoNotificationTable.INSTANCE;
	}

	@Reference
	private KaleoNotificationPersistence _kaleoNotificationPersistence;

}