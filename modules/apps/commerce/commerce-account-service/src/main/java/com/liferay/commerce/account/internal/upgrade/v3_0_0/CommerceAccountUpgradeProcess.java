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

package com.liferay.commerce.account.internal.upgrade.v3_0_0;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.model.impl.CommerceAccountImpl;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.TypedModel;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.function.UnaryOperator;

/**
 * @author Drew Brokke
 */
public class CommerceAccountUpgradeProcess extends UpgradeProcess {

	public CommerceAccountUpgradeProcess(
		AccountEntryLocalService accountEntryLocalService,
		ClassNameLocalService classNameLocalService,
		ExpandoTableLocalService expandoTableLocalService,
		ExpandoValueLocalService expandoValueLocalService,
		GroupLocalService groupLocalService,
		ResourceLocalService resourceLocalService,
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService,
		WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService) {

		_accountEntryLocalService = accountEntryLocalService;
		_classNameLocalService = classNameLocalService;
		_expandoTableLocalService = expandoTableLocalService;
		_expandoValueLocalService = expandoValueLocalService;
		_groupLocalService = groupLocalService;
		_resourceLocalService = resourceLocalService;
		_workflowDefinitionLinkLocalService =
			workflowDefinitionLinkLocalService;
		_workflowInstanceLinkLocalService = workflowInstanceLinkLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String selectCommerceAccountSQL =
			"select * from CommerceAccount order by commerceAccountId asc";

		try (Statement selectStatement = connection.createStatement()) {
			ResultSet resultSet = selectStatement.executeQuery(
				selectCommerceAccountSQL);

			while (resultSet.next()) {
				long accountEntryId = resultSet.getLong("commerceAccountId");

				AccountEntry accountEntry =
					_accountEntryLocalService.createAccountEntry(
						accountEntryId);

				accountEntry.setExternalReferenceCode(
					resultSet.getString("externalReferenceCode"));

				long companyId = resultSet.getLong("companyId");

				accountEntry.setCompanyId(companyId);

				long userId = resultSet.getLong("userId");

				accountEntry.setUserId(userId);

				accountEntry.setUserName(resultSet.getString("userName"));
				accountEntry.setCreateDate(
					resultSet.getTimestamp("createDate"));
				accountEntry.setModifiedDate(
					resultSet.getTimestamp("modifiedDate"));
				accountEntry.setDefaultBillingAddressId(
					resultSet.getLong("defaultBillingAddressId"));
				accountEntry.setDefaultShippingAddressId(
					resultSet.getLong("defaultShippingAddressId"));
				accountEntry.setParentAccountEntryId(
					resultSet.getLong("parentCommerceAccountId"));
				accountEntry.setEmailAddress(resultSet.getString("email"));
				accountEntry.setLogoId(resultSet.getLong("logoId"));
				accountEntry.setName(resultSet.getString("name"));
				accountEntry.setTaxIdNumber(resultSet.getString("taxId"));
				accountEntry.setType(
					CommerceAccountImpl.toAccountEntryType(
						resultSet.getInt("type_")));
				accountEntry.setStatus(
					CommerceAccountImpl.toAccountEntryStatus(
						resultSet.getBoolean("active_")));

				_accountEntryLocalService.addAccountEntry(accountEntry);

				_resourceLocalService.addResources(
					companyId, 0, userId, AccountEntry.class.getName(),
					accountEntryId, false, false, false);

				_workflowDefinitionLinkLocalService.
					deleteWorkflowDefinitionLink(
						companyId, WorkflowConstants.DEFAULT_GROUP_ID,
						CommerceAccount.class.getName(), accountEntryId, 0);
				_workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
					companyId, WorkflowConstants.DEFAULT_GROUP_ID,
					CommerceAccount.class.getName(), accountEntryId);
			}
		}

		long accountEntryClassNameId = _classNameLocalService.getClassNameId(
			AccountEntry.class);
		long commerceAccountClassNameId = _classNameLocalService.getClassNameId(
			CommerceAccount.class);

		_updateClassNameId(
			_expandoTableLocalService.getActionableDynamicQuery(),
			accountEntryClassNameId, commerceAccountClassNameId,
			typedModel -> _expandoTableLocalService.updateExpandoTable(
				(ExpandoTable)typedModel));
		_updateClassNameId(
			_expandoValueLocalService.getActionableDynamicQuery(),
			accountEntryClassNameId, commerceAccountClassNameId,
			typedModel -> _expandoValueLocalService.updateExpandoValue(
				(ExpandoValue)typedModel));
		_updateClassNameId(
			_groupLocalService.getActionableDynamicQuery(),
			accountEntryClassNameId, commerceAccountClassNameId,
			typedModel -> _groupLocalService.updateGroup((Group)typedModel));
	}

	private void _updateClassNameId(
			ActionableDynamicQuery actionableDynamicQuery, long newClassNameId,
			long oldClassNameId, UnaryOperator<TypedModel> updateFunction)
		throws Exception {

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.eq("classNameId", oldClassNameId)));
		actionableDynamicQuery.setPerformActionMethod(
			(TypedModel typedModel) -> {
				typedModel.setClassNameId(newClassNameId);

				updateFunction.apply(typedModel);
			});

		actionableDynamicQuery.performActions();
	}

	private final AccountEntryLocalService _accountEntryLocalService;
	private final ClassNameLocalService _classNameLocalService;
	private final ExpandoTableLocalService _expandoTableLocalService;
	private final ExpandoValueLocalService _expandoValueLocalService;
	private final GroupLocalService _groupLocalService;
	private final ResourceLocalService _resourceLocalService;
	private final WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;
	private final WorkflowInstanceLinkLocalService
		_workflowInstanceLinkLocalService;

}