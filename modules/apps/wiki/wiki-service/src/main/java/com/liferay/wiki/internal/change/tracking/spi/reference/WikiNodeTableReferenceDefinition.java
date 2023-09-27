/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiNodeTable;
import com.liferay.wiki.service.persistence.WikiNodePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noor Najjar
 */
@Component(service = TableReferenceDefinition.class)
public class WikiNodeTableReferenceDefinition
	implements TableReferenceDefinition<WikiNodeTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<WikiNodeTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.assetEntryReference(
			WikiNodeTable.INSTANCE.nodeId, WikiNode.class
		).resourcePermissionReference(
			WikiNodeTable.INSTANCE.nodeId, WikiNode.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<WikiNodeTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(WikiNodeTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _wikiNodePersistence;
	}

	@Override
	public WikiNodeTable getTable() {
		return WikiNodeTable.INSTANCE;
	}

	@Reference
	private WikiNodePersistence _wikiNodePersistence;

}