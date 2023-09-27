/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.product.model.CPDefinitionTable;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.model.CProductTable;
import com.liferay.commerce.product.service.persistence.CProductPersistence;
import com.liferay.friendly.url.model.FriendlyURLEntryTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = TableReferenceDefinition.class)
public class CProductTableReferenceDefinition
	implements TableReferenceDefinition<CProductTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CProductTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.classNameReference(
			CProductTable.INSTANCE.CProductId,
			FriendlyURLEntryTable.INSTANCE.classPK, CProduct.class);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CProductTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			CProductTable.INSTANCE
		).singleColumnReference(
			CProductTable.INSTANCE.CProductId,
			CPDefinitionTable.INSTANCE.CProductId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cProductPersistence;
	}

	@Override
	public CProductTable getTable() {
		return CProductTable.INSTANCE;
	}

	@Reference
	private CProductPersistence _cProductPersistence;

}