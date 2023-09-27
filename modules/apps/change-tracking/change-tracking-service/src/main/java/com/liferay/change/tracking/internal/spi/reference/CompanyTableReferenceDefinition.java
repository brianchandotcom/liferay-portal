/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.ImageTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.CompanyPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class CompanyTableReferenceDefinition
	implements TableReferenceDefinition<CompanyTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CompanyTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			CompanyTable.INSTANCE.logoId, ImageTable.INSTANCE.imageId);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CompanyTable>
			parentTableReferenceInfoBuilder) {
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _companyPersistence;
	}

	@Override
	public CompanyTable getTable() {
		return CompanyTable.INSTANCE;
	}

	@Reference
	private CompanyPersistence _companyPersistence;

}