/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.product.model.CommerceChannelRelTable;
import com.liferay.commerce.product.model.CommerceChannelTable;
import com.liferay.commerce.product.service.persistence.CommerceChannelRelPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = TableReferenceDefinition.class)
public class CommerceChannelRelTableReferenceDefinition
	implements TableReferenceDefinition<CommerceChannelRelTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CommerceChannelRelTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			CommerceChannelRelTable.INSTANCE.commerceChannelId,
			CommerceChannelTable.INSTANCE.commerceChannelId);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CommerceChannelRelTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			CommerceChannelRelTable.INSTANCE.commerceChannelId,
			CommerceChannelTable.INSTANCE.commerceChannelId
		).singleColumnReference(
			CommerceChannelRelTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _commerceChannelRelPersistence;
	}

	@Override
	public CommerceChannelRelTable getTable() {
		return CommerceChannelRelTable.INSTANCE;
	}

	@Reference
	private CommerceChannelRelPersistence _commerceChannelRelPersistence;

}