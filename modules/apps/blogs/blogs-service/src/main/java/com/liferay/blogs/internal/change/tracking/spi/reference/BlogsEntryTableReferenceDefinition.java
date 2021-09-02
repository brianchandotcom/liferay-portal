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