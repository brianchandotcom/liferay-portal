/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.wiki.model.WikiPageResource;
import com.liferay.wiki.model.WikiPageResourceTable;
import com.liferay.wiki.model.WikiPageTable;
import com.liferay.wiki.service.persistence.WikiPageResourcePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noor Najjar
 */
@Component(service = TableReferenceDefinition.class)
public class WikiPageResourceTableReferenceDefinition
	implements TableReferenceDefinition<WikiPageResourceTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<WikiPageResourceTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.assetEntryReference(
			WikiPageResourceTable.INSTANCE.resourcePrimKey,
			WikiPageResource.class
		).resourcePermissionReference(
			WikiPageResourceTable.INSTANCE.resourcePrimKey,
			WikiPageResource.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<WikiPageResourceTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			WikiPageResourceTable.INSTANCE
		).singleColumnReference(
			WikiPageResourceTable.INSTANCE.resourcePrimKey,
			WikiPageTable.INSTANCE.resourcePrimKey
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _wikiPageResourcePersistence;
	}

	@Override
	public WikiPageResourceTable getTable() {
		return WikiPageResourceTable.INSTANCE;
	}

	@Reference
	private WikiPageResourcePersistence _wikiPageResourcePersistence;

}