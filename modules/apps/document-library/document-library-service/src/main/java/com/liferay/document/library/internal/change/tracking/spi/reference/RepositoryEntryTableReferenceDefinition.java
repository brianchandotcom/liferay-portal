/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.RepositoryEntryTable;
import com.liferay.portal.kernel.model.RepositoryTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.RepositoryEntryPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = TableReferenceDefinition.class)
public class RepositoryEntryTableReferenceDefinition
	implements TableReferenceDefinition<RepositoryEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<RepositoryEntryTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<RepositoryEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			RepositoryEntryTable.INSTANCE
		).singleColumnReference(
			RepositoryEntryTable.INSTANCE.repositoryId,
			RepositoryTable.INSTANCE.repositoryId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _repositoryEntryPersistence;
	}

	@Override
	public RepositoryEntryTable getTable() {
		return RepositoryEntryTable.INSTANCE;
	}

	@Reference
	private RepositoryEntryPersistence _repositoryEntryPersistence;

}