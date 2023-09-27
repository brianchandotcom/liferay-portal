/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.product.model.CPOptionTable;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.model.CPOptionValueTable;
import com.liferay.commerce.product.service.persistence.CPOptionValuePersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = TableReferenceDefinition.class)
public class CPOptionValueTableReferenceDefinition
	implements TableReferenceDefinition<CPOptionValueTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CPOptionValueTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.resourcePermissionReference(
			CPOptionValueTable.INSTANCE.CPOptionValueId, CPOptionValue.class
		).singleColumnReference(
			CPOptionValueTable.INSTANCE.CPOptionId,
			CPOptionTable.INSTANCE.CPOptionId
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CPOptionValueTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			CPOptionValueTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			CPOptionValueTable.INSTANCE.CPOptionId,
			CPOptionTable.INSTANCE.CPOptionId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cpOptionValuePersistence;
	}

	@Override
	public CPOptionValueTable getTable() {
		return CPOptionValueTable.INSTANCE;
	}

	@Reference
	private CPOptionValuePersistence _cpOptionValuePersistence;

}