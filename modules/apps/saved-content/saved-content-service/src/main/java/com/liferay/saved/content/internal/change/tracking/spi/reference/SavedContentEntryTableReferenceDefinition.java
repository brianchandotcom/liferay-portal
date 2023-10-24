/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.saved.content.model.SavedContentEntryTable;
import com.liferay.saved.content.service.persistence.SavedContentEntryPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(service = TableReferenceDefinition.class)
public class SavedContentEntryTableReferenceDefinition
	implements TableReferenceDefinition<SavedContentEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<SavedContentEntryTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<SavedContentEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			SavedContentEntryTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _savedContentEntryPersistence;
	}

	@Override
	public SavedContentEntryTable getTable() {
		return SavedContentEntryTable.INSTANCE;
	}

	@Reference
	private SavedContentEntryPersistence _savedContentEntryPersistence;

}