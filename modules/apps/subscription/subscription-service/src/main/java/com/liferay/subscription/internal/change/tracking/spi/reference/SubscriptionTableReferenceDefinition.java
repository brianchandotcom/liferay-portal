/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.subscription.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.subscription.model.SubscriptionTable;
import com.liferay.subscription.service.persistence.SubscriptionPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class SubscriptionTableReferenceDefinition
	implements TableReferenceDefinition<SubscriptionTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<SubscriptionTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<SubscriptionTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			SubscriptionTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _subscriptionPersistence;
	}

	@Override
	public SubscriptionTable getTable() {
		return SubscriptionTable.INSTANCE;
	}

	@Reference
	private SubscriptionPersistence _subscriptionPersistence;

}