/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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