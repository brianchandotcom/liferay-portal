/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.change.tracking.internal.reference.portal;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.helper.TableReferenceDefinitionHelper;
import com.liferay.portal.kernel.model.AccountTable;
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
	public void defineTableReferences(
		TableReferenceDefinitionHelper<CompanyTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				AccountTable.INSTANCE
			).innerJoinON(
				CompanyTable.INSTANCE,
				CompanyTable.INSTANCE.accountId.eq(
					AccountTable.INSTANCE.accountId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			CompanyTable.INSTANCE.webId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			CompanyTable.INSTANCE.mx);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			CompanyTable.INSTANCE.homeURL);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				ImageTable.INSTANCE
			).innerJoinON(
				CompanyTable.INSTANCE,
				CompanyTable.INSTANCE.logoId.eq(ImageTable.INSTANCE.imageId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			CompanyTable.INSTANCE.system);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			CompanyTable.INSTANCE.maxUsers);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			CompanyTable.INSTANCE.active);
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