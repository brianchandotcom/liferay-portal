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
import com.liferay.change.tracking.reference.helper.TableReferenceDefinitionHelper;
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
		TableReferenceDefinitionHelper<AccountTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				AccountTable.INSTANCE,
				AccountTable.INSTANCE.companyId.eq(
					CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				AccountTable.INSTANCE,
				AccountTable.INSTANCE.userId.eq(UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AccountTable.INSTANCE.userName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AccountTable.INSTANCE.createDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AccountTable.INSTANCE.modifiedDate);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> {
				AccountTable aliasAccountTable = AccountTable.INSTANCE.as(
					"aliasAccountTable");

				return fromStep.from(
					aliasAccountTable
				).innerJoinON(
					AccountTable.INSTANCE,
					AccountTable.INSTANCE.parentAccountId.eq(
						aliasAccountTable.accountId)
				);
			});

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AccountTable.INSTANCE.name);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AccountTable.INSTANCE.legalName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AccountTable.INSTANCE.legalId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AccountTable.INSTANCE.legalType);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AccountTable.INSTANCE.sicCode);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AccountTable.INSTANCE.tickerSymbol);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AccountTable.INSTANCE.industry);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AccountTable.INSTANCE.type);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			AccountTable.INSTANCE.size);
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