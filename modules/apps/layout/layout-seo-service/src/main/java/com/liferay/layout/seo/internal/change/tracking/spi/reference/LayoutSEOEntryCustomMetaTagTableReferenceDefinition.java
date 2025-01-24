/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.layout.seo.model.LayoutSEOEntryCustomMetaTagTable;
import com.liferay.layout.seo.model.LayoutSEOEntryTable;
import com.liferay.layout.seo.service.persistence.LayoutSEOEntryCustomMetaTagPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = TableReferenceDefinition.class)
public class LayoutSEOEntryCustomMetaTagTableReferenceDefinition
	implements TableReferenceDefinition<LayoutSEOEntryCustomMetaTagTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<LayoutSEOEntryCustomMetaTagTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<LayoutSEOEntryCustomMetaTagTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			LayoutSEOEntryCustomMetaTagTable.INSTANCE
		).singleColumnReference(
			LayoutSEOEntryCustomMetaTagTable.INSTANCE.layoutSEOEntryId,
			LayoutSEOEntryTable.INSTANCE.layoutSEOEntryId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _layoutLocalizationPersistence;
	}

	@Override
	public LayoutSEOEntryCustomMetaTagTable getTable() {
		return LayoutSEOEntryCustomMetaTagTable.INSTANCE;
	}

	@Reference
	private LayoutSEOEntryCustomMetaTagPersistence
		_layoutLocalizationPersistence;

}