/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v5_9_0;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.util.CommerceAccountHelper;
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
		CommerceAccountHelper commerceAccountHelper,
		ExpandoTableLocalService expandoTableLocalService,
		ExpandoValueLocalService expandoValueLocalService,
		GroupLocalService groupLocalService,
		ResourceLocalService resourceLocalService,
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService,
		WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService) {

		_accountEntryLocalService = accountEntryLocalService;
		_classNameLocalService = classNameLocalService;
		_commerceAccountHelper = commerceAccountHelper;
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
					_toAccountEntryType(resultSet.getInt("type_")));
				accountEntry.setStatus(
					_commerceAccountHelper.toAccountEntryStatus(
						resultSet.getBoolean("active_")));

				_accountEntryLocalService.addAccountEntry(accountEntry);

				_resourceLocalService.addResources(
					companyId, 0, userId, AccountEntry.class.getName(),
					accountEntryId, false, false, false);

				_workflowDefinitionLinkLocalService.
					deleteWorkflowDefinitionLink(
						companyId, WorkflowConstants.DEFAULT_GROUP_ID,
						"com.liferay.commerce.account.model.CommerceAccount",
						accountEntryId, 0);
				_workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
					companyId, WorkflowConstants.DEFAULT_GROUP_ID,
					"com.liferay.commerce.account.model.CommerceAccount",
					accountEntryId);
			}
		}

		long accountEntryClassNameId = _classNameLocalService.getClassNameId(
			AccountEntry.class);
		long commerceAccountClassNameId = _classNameLocalService.getClassNameId(
			"com.liferay.commerce.account.model.CommerceAccount");

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

	private String _toAccountEntryType(int commerceAccountType) {
		if (commerceAccountType == 2) {
			return AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS;
		}
		else if (commerceAccountType == 0) {
			return AccountConstants.ACCOUNT_ENTRY_TYPE_GUEST;
		}
		else if (commerceAccountType == 1) {
			return AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON;
		}

		return null;
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
	private final CommerceAccountHelper _commerceAccountHelper;
	private final ExpandoTableLocalService _expandoTableLocalService;
	private final ExpandoValueLocalService _expandoValueLocalService;
	private final GroupLocalService _groupLocalService;
	private final ResourceLocalService _resourceLocalService;
	private final WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;
	private final WorkflowInstanceLinkLocalService
		_workflowInstanceLinkLocalService;

}