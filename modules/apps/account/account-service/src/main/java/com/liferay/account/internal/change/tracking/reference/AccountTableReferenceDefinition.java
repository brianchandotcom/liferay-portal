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

package com.liferay.account.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.AccountTable;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.AccountPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class AccountTableReferenceDefinition
	implements TableReferenceDefinition<AccountTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<AccountTable> tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.singleColumnReference(
			AccountTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			AccountTable.INSTANCE.userId, UserTable.INSTANCE.userId
		).nonreferenceColumns(
			AccountTable.INSTANCE.userName, AccountTable.INSTANCE.createDate,
			AccountTable.INSTANCE.modifiedDate
		).parentColumnReference(
			AccountTable.INSTANCE.accountId,
			AccountTable.INSTANCE.parentAccountId
		).nonreferenceColumns(
			AccountTable.INSTANCE.name, AccountTable.INSTANCE.legalName,
			AccountTable.INSTANCE.legalId, AccountTable.INSTANCE.legalType,
			AccountTable.INSTANCE.sicCode, AccountTable.INSTANCE.tickerSymbol,
			AccountTable.INSTANCE.industry, AccountTable.INSTANCE.type,
			AccountTable.INSTANCE.size
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _accountPersistence;
	}

	@Override
	public AccountTable getTable() {
		return AccountTable.INSTANCE;
	}

	@Reference
	private AccountPersistence _accountPersistence;

}