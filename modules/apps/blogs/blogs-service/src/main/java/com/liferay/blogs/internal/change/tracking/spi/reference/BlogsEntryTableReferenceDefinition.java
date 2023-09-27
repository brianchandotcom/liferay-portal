/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.internal.change.tracking.spi.reference;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.model.BlogsEntryTable;
import com.liferay.blogs.service.persistence.BlogsEntryPersistence;
import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.friendly.url.model.FriendlyURLEntryTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = TableReferenceDefinition.class)
public class BlogsEntryTableReferenceDefinition
	implements TableReferenceDefinition<BlogsEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<BlogsEntryTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.assetEntryReference(
			BlogsEntryTable.INSTANCE.entryId, BlogsEntry.class
		).classNameReference(
			BlogsEntryTable.INSTANCE.entryId,
			FriendlyURLEntryTable.INSTANCE.classPK, BlogsEntry.class
		).resourcePermissionReference(
			BlogsEntryTable.INSTANCE.entryId, BlogsEntry.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<BlogsEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			BlogsEntryTable.INSTANCE
		).singleColumnReference(
			BlogsEntryTable.INSTANCE.userId, UserTable.INSTANCE.userId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _blogsEntryPersistence;
	}

	@Override
	public BlogsEntryTable getTable() {
		return BlogsEntryTable.INSTANCE;
	}

	@Reference
	private BlogsEntryPersistence _blogsEntryPersistence;

}