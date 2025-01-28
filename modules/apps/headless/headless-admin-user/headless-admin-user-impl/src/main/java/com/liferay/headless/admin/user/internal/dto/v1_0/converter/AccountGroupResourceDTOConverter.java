/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.dto.v1_0.converter;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.headless.admin.user.dto.v1_0.AccountBrief;
import com.liferay.headless.admin.user.dto.v1_0.AccountGroup;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PermissionService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedFieldsSupplier;
import com.liferay.portal.vulcan.permission.Permission;
import com.liferay.portal.vulcan.permission.PermissionUtil;

import java.util.Collection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian I. Kim
 */
@Component(
	property = {
		"application.name=Liferay.Headless.Admin.User",
		"dto.class.name=com.liferay.account.model.AccountGroup"
	},
	service = DTOConverter.class
)
public class AccountGroupResourceDTOConverter
	implements DTOConverter
		<com.liferay.account.model.AccountGroup, AccountGroup> {

	@Override
	public String getContentType() {
		return AccountGroup.class.getSimpleName();
	}

	@Override
	public com.liferay.account.model.AccountGroup getObject(
			String externalReferenceCode)
		throws Exception {

		com.liferay.account.model.AccountGroup accountGroup =
			_accountGroupLocalService.fetchAccountGroupByExternalReferenceCode(
				externalReferenceCode, CompanyThreadLocal.getCompanyId());

		if (accountGroup == null) {
			accountGroup = _accountGroupLocalService.getAccountGroup(
				GetterUtil.getLong(externalReferenceCode));
		}

		return accountGroup;
	}

	@Override
	public AccountGroup toDTO(
		DTOConverterContext dtoConverterContext,
		com.liferay.account.model.AccountGroup accountGroup) {

		if (accountGroup == null) {
			return null;
		}

		return new AccountGroup() {
			{
				setAccountBriefs(
					() -> NestedFieldsSupplier.supply(
						"accountBriefs",
						fieldName -> TransformUtil.transformToArray(
							_accountGroupRelLocalService.getAccountGroupRels(
								accountGroup.getAccountGroupId(),
								AccountEntry.class.getName()),
							accountGroupRel -> _toAccountBrief(accountGroupRel),
							AccountBrief.class)));
				setCreator(
					() -> NestedFieldsSupplier.supply(
						"creator",
						fieldName -> CreatorUtil.toCreator(
							_portal,
							_userLocalService.fetchUser(
								accountGroup.getUserId()))));
				setCustomFields(
					() -> CustomFieldsUtil.toCustomFields(
						dtoConverterContext.isAcceptAllLanguages(),
						com.liferay.account.model.AccountGroup.class.getName(),
						accountGroup.getAccountGroupId(),
						accountGroup.getCompanyId(),
						dtoConverterContext.getLocale()));
				setDateCreated(accountGroup::getCreateDate);
				setDateModified(accountGroup::getModifiedDate);
				setDescription(accountGroup::getDescription);
				setExternalReferenceCode(
					accountGroup::getExternalReferenceCode);
				setId(accountGroup::getAccountGroupId);
				setName(accountGroup::getName);
				setPermissions(
					() -> NestedFieldsSupplier.supply(
						"permissions",
						nestedFieldNames -> {
							Company company = _companyLocalService.getCompany(
								accountGroup.getCompanyId());

							_permissionService.checkPermission(
								company.getGroupId(),
								com.liferay.account.model.AccountGroup.class.
									getName(),
								accountGroup.getAccountGroupId());

							Collection<Permission> permissions =
								PermissionUtil.getPermissions(
									accountGroup.getCompanyId(),
									_resourceActionLocalService.
										getResourceActions(
											com.liferay.account.model.
												AccountGroup.class.getName()),
									accountGroup.getAccountGroupId(),
									com.liferay.account.model.AccountGroup.
										class.getName(),
									null);

							return permissions.toArray(new Permission[0]);
						}));
			}
		};
	}

	private AccountBrief _toAccountBrief(AccountGroupRel accountGroupRel) {
		AccountEntry accountEntry = _accountEntryLocalService.fetchAccountEntry(
			accountGroupRel.getClassPK());

		if (accountEntry == null) {
			return null;
		}

		return new AccountBrief() {
			{
				setExternalReferenceCode(
					accountEntry::getExternalReferenceCode);
				setId(accountEntry::getAccountEntryId);
				setName(accountEntry::getName);
			}
		};
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

	@Reference
	private AccountGroupRelLocalService _accountGroupRelLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private PermissionService _permissionService;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}