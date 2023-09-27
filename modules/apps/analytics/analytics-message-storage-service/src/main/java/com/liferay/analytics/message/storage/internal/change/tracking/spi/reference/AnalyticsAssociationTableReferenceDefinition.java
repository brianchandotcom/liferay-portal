/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.storage.internal.change.tracking.spi.reference;

import com.liferay.analytics.message.storage.model.AnalyticsAssociationTable;
import com.liferay.analytics.message.storage.service.persistence.AnalyticsAssociationPersistence;
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
public class AnalyticsAssociationTableReferenceDefinition
	implements TableReferenceDefinition<AnalyticsAssociationTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<AnalyticsAssociationTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<AnalyticsAssociationTable>
			parentTableReferenceInfoBuilder) {
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _analyticsAssociationPersistence;
	}

	@Override
	public AnalyticsAssociationTable getTable() {
		return AnalyticsAssociationTable.INSTANCE;
	}

	@Reference
	private AnalyticsAssociationPersistence _analyticsAssociationPersistence;

}