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