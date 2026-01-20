/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.search.spi.model.index.contributor;

import com.liferay.account.model.AccountRole;
import com.liferay.account.model.AccountRoleTable;
import com.liferay.account.service.AccountRoleLocalServiceUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleTable;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.ReindexCacheThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class RoleAccountRoleDocumentContributorUtil {

	public static void contribue(Document document, AccountRole accountRole) {
		RoleAccountRoleMapping roleAccountRoleMapping =
			_getRoleAccountRoleMapping();

		if (roleAccountRoleMapping == null) {
			Role role = RoleLocalServiceUtil.fetchRole(accountRole.getRoleId());

			if (role != null) {
				document.addKeyword(
					Field.COMPANY_ID, accountRole.getCompanyId());
				document.addLocalizedText(
					Field.DESCRIPTION, role.getDescriptionMap());
				document.addText(Field.NAME, role.getName());
				document.addLocalizedText(Field.TITLE, role.getTitleMap());
				document.addKeyword(
					"accountEntryId", accountRole.getAccountEntryId());
			}

			return;
		}

		RoleData roleData = roleAccountRoleMapping.getRoleData(
			accountRole.getRoleId());

		if (roleData != null) {
			document.addKeyword(Field.COMPANY_ID, accountRole.getCompanyId());
			document.addLocalizedText(
				Field.DESCRIPTION, roleData._descriptionMap);
			document.addText(Field.NAME, roleData._name);
			document.addLocalizedText(Field.TITLE, roleData._titleMap);
			document.addKeyword(
				"accountEntryId", accountRole.getAccountEntryId());
		}
	}

	public static void contribute(Document document, Role role) {
		RoleAccountRoleMapping roleAccountRoleMapping =
			_getRoleAccountRoleMapping();

		if (roleAccountRoleMapping == null) {
			AccountRole accountRole =
				AccountRoleLocalServiceUtil.fetchAccountRole(role.getClassPK());

			if (accountRole != null) {
				document.addKeyword(
					"accountEntryId", accountRole.getAccountEntryId());
			}

			return;
		}

		Long accountEntryId = roleAccountRoleMapping.getAccountEntryId(
			role.getClassPK());

		if (accountEntryId != null) {
			document.addKeyword("accountEntryId", accountEntryId);
		}
	}

	private static RoleAccountRoleMapping _getRoleAccountRoleMapping() {
		return ReindexCacheThreadLocal.getGlobalReindexCache(
			() -> -1, RoleAccountRoleDocumentContributorUtil.class.getName(),
			count -> {
				RoleAccountRoleMapping roleAccountRoleMapping =
					new RoleAccountRoleMapping();

				RoleLocalService roleLocalService =
					RoleLocalServiceUtil.getService();

				for (Object[] values :
						roleLocalService.<List<Object[]>>dslQuery(
							DSLQueryFactoryUtil.select(
								RoleTable.INSTANCE.roleId,
								RoleTable.INSTANCE.description,
								RoleTable.INSTANCE.name,
								RoleTable.INSTANCE.title,
								RoleTable.INSTANCE.classPK,
								AccountRoleTable.INSTANCE.accountEntryId
							).from(
								RoleTable.INSTANCE
							).innerJoinON(
								AccountRoleTable.INSTANCE,
								RoleTable.INSTANCE.roleId.eq(
									AccountRoleTable.INSTANCE.roleId)
							),
							false)) {

					roleAccountRoleMapping.putAccountEntryId(
						(Long)values[4], (Long)values[5]);
					roleAccountRoleMapping.putRoleData(
						(Long)values[0],
						new RoleData(
							(String)values[1], (String)values[2],
							(String)values[3]));
				}

				return roleAccountRoleMapping;
			});
	}

	private static class RoleAccountRoleMapping {

		public Long getAccountEntryId(Long roleClassPK) {
			return _accountEntryIds.get(roleClassPK);
		}

		public RoleData getRoleData(Long accountRoleId) {
			return _roleDatas.get(accountRoleId);
		}

		public void putAccountEntryId(Long roleClassPK, Long accountEntryId) {
			_accountEntryIds.put(roleClassPK, accountEntryId);
		}

		public void putRoleData(Long accountRoleId, RoleData roleData) {
			_roleDatas.put(accountRoleId, roleData);
		}

		private final Map<Long, Long> _accountEntryIds = new HashMap<>();
		private final Map<Long, RoleData> _roleDatas = new HashMap<>();

	}

	private static class RoleData {

		private RoleData(String description, String name, String title) {
			_name = name;

			_descriptionMap = LocalizationUtil.getLocalizationMap(description);
			_titleMap = LocalizationUtil.getLocalizationMap(title);
		}

		private final Map<Locale, String> _descriptionMap;
		private final String _name;
		private final Map<Locale, String> _titleMap;

	}

}