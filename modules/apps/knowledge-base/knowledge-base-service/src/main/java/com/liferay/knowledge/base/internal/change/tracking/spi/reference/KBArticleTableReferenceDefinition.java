/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBArticleTable;
import com.liferay.knowledge.base.service.persistence.KBArticlePersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Vy Bui
 */
@Component(service = TableReferenceDefinition.class)
public class KBArticleTableReferenceDefinition
	implements TableReferenceDefinition<KBArticleTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<KBArticleTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.assetEntryReference(
			KBArticleTable.INSTANCE.resourcePrimKey, KBArticle.class
		).resourcePermissionReference(
			KBArticleTable.INSTANCE.resourcePrimKey, KBArticle.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<KBArticleTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(KBArticleTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kbArticlePersistence;
	}

	@Override
	public KBArticleTable getTable() {
		return KBArticleTable.INSTANCE;
	}

	@Reference
	private KBArticlePersistence _kbArticlePersistence;

}