/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.reading.time.internal.change.tracking.spi.reference;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.model.BlogsEntryTable;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.reading.time.model.ReadingTimeEntryTable;
import com.liferay.reading.time.service.persistence.ReadingTimeEntryPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = TableReferenceDefinition.class)
public class ReadingTimeEntryTableReferenceDefinition
	implements TableReferenceDefinition<ReadingTimeEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<ReadingTimeEntryTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<ReadingTimeEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.classNameReference(
			ReadingTimeEntryTable.INSTANCE.classPK,
			BlogsEntryTable.INSTANCE.entryId, BlogsEntry.class
		).groupedModel(
			ReadingTimeEntryTable.INSTANCE
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _readingTimeEntryPersistence;
	}

	@Override
	public ReadingTimeEntryTable getTable() {
		return ReadingTimeEntryTable.INSTANCE;
	}

	@Reference
	private ReadingTimeEntryPersistence _readingTimeEntryPersistence;

}