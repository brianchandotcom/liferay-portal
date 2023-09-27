/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.layout.model.LayoutLocalizationTable;
import com.liferay.layout.service.persistence.LayoutLocalizationPersistence;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = TableReferenceDefinition.class)
public class LayoutLocalizationTableReferenceDefinition
	implements TableReferenceDefinition<LayoutLocalizationTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<LayoutLocalizationTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<LayoutLocalizationTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			LayoutLocalizationTable.INSTANCE
		).singleColumnReference(
			LayoutLocalizationTable.INSTANCE.plid, LayoutTable.INSTANCE.plid
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _layoutLocalizationPersistence;
	}

	@Override
	public LayoutLocalizationTable getTable() {
		return LayoutLocalizationTable.INSTANCE;
	}

	@Reference
	private LayoutLocalizationPersistence _layoutLocalizationPersistence;

}