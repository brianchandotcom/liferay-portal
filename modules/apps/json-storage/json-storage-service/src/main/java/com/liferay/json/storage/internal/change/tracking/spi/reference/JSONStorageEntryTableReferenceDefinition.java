/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.json.storage.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.json.storage.model.JSONStorageEntryTable;
import com.liferay.json.storage.service.persistence.JSONStorageEntryPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class JSONStorageEntryTableReferenceDefinition
	implements TableReferenceDefinition<JSONStorageEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<JSONStorageEntryTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<JSONStorageEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			JSONStorageEntryTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).parentColumnReference(
			JSONStorageEntryTable.INSTANCE.jsonStorageEntryId,
			JSONStorageEntryTable.INSTANCE.parentJSONStorageEntryId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _jsonStorageEntryPersistence;
	}

	@Override
	public JSONStorageEntryTable getTable() {
		return JSONStorageEntryTable.INSTANCE;
	}

	@Reference
	private JSONStorageEntryPersistence _jsonStorageEntryPersistence;

}