/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.content.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.document.library.content.model.DLContentTable;
import com.liferay.document.library.content.service.persistence.DLContentPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DLContentTableReferenceDefinition
	implements TableReferenceDefinition<DLContentTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DLContentTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DLContentTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(DLContentTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _dlContentPersistence;
	}

	@Override
	public DLContentTable getTable() {
		return DLContentTable.INSTANCE;
	}

	@Reference
	private DLContentPersistence _dlContentPersistence;

}