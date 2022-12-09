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

package com.liferay.wiki.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.wiki.model.WikiNodeTable;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageTable;
import com.liferay.wiki.service.persistence.WikiPagePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noor Najjar
 */
@Component(service = TableReferenceDefinition.class)
public class WikiPageTableReferenceDefinition
	implements TableReferenceDefinition<WikiPageTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<WikiPageTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.assetEntryReference(
			WikiPageTable.INSTANCE.resourcePrimKey, WikiPage.class
		).resourcePermissionReference(
			WikiPageTable.INSTANCE.resourcePrimKey, WikiPage.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<WikiPageTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			WikiPageTable.INSTANCE
		).singleColumnReference(
			WikiPageTable.INSTANCE.nodeId, WikiNodeTable.INSTANCE.nodeId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _wikiPagePersistence;
	}

	@Override
	public WikiPageTable getTable() {
		return WikiPageTable.INSTANCE;
	}

	@Reference
	private WikiPagePersistence _wikiPagePersistence;

}