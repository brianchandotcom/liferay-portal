/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.product.model.CPInstanceOptionValueRelTable;
import com.liferay.commerce.product.model.CPInstanceTable;
import com.liferay.commerce.product.service.persistence.CPInstanceOptionValueRelPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = TableReferenceDefinition.class)
public class CPInstanceOptionValueRelTableReferenceDefinition
	implements TableReferenceDefinition<CPInstanceOptionValueRelTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CPInstanceOptionValueRelTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CPInstanceOptionValueRelTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			CPInstanceOptionValueRelTable.INSTANCE
		).singleColumnReference(
			CPInstanceOptionValueRelTable.INSTANCE.CPInstanceId,
			CPInstanceTable.INSTANCE.CPInstanceId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cpInstanceOptionValueRelPersistence;
	}

	@Override
	public CPInstanceOptionValueRelTable getTable() {
		return CPInstanceOptionValueRelTable.INSTANCE;
	}

	@Reference
	private CPInstanceOptionValueRelPersistence
		_cpInstanceOptionValueRelPersistence;

}