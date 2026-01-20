/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.search.spi.model.index.contributor;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.model.AccountEntryOrganizationRelModel;
import com.liferay.account.model.AccountEntryOrganizationRelTable;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalServiceUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.ReindexCacheThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class OrganizationAccountEntryDocumentContributorUtil {

	public static void contribute(
		Document document, AccountEntry accountEntry) {

		document.addKeyword(
			"organizationIds",
			_getOrganizationIds(accountEntry.getAccountEntryId()));
	}

	public static void contribute(
		Document document, Organization organization) {

		document.addKeyword(
			"accountEntryIds",
			_getAccountEntryIds(organization.getOrganizationId()));
	}

	private static long[] _getAccountEntryIds(Long organizationId) {
		OrganizationAccountEntryMapping organizationAccountEntryMapping =
			_getOrganizationAccountEntryMapping();

		if (organizationAccountEntryMapping == null) {
			return ListUtil.toLongArray(
				AccountEntryOrganizationRelLocalServiceUtil.
					getAccountEntryOrganizationRelsByOrganizationId(
						organizationId),
				AccountEntryOrganizationRel::getAccountEntryId);
		}

		return organizationAccountEntryMapping.getAccountEntryIds(
			organizationId);
	}

	private static OrganizationAccountEntryMapping
		_getOrganizationAccountEntryMapping() {

		return ReindexCacheThreadLocal.getGlobalReindexCache(
			() -> -1,
			OrganizationAccountEntryDocumentContributorUtil.class.getName(),
			count -> {
				Map<Long, List<Long>> accountEntryIdsMap = new HashMap<>();
				Map<Long, List<Long>> organizationIdsMap = new HashMap<>();

				AccountEntryOrganizationRelLocalService
					accountEntryOrganizationRelLocalService =
						AccountEntryOrganizationRelLocalServiceUtil.
							getService();

				for (Object[] values :
						accountEntryOrganizationRelLocalService.
							<List<Object[]>>dslQuery(
								DSLQueryFactoryUtil.select(
									AccountEntryOrganizationRelTable.INSTANCE.
										accountEntryId,
									AccountEntryOrganizationRelTable.INSTANCE.
										organizationId
								).from(
									AccountEntryOrganizationRelTable.INSTANCE
								),
								false)) {

					Long accountEntryId = (Long)values[0];

					Long organizationId = (Long)values[1];

					List<Long> accountEntryIds =
						accountEntryIdsMap.computeIfAbsent(
							organizationId, key -> new ArrayList<>());

					accountEntryIds.add(accountEntryId);

					List<Long> organizationIds =
						organizationIdsMap.computeIfAbsent(
							accountEntryId, key -> new ArrayList<>());

					organizationIds.add(organizationId);
				}

				return new OrganizationAccountEntryMapping(
					accountEntryIdsMap, organizationIdsMap);
			});
	}

	private static long[] _getOrganizationIds(Long accountEntryId) {
		OrganizationAccountEntryMapping organizationAccountEntryMapping =
			_getOrganizationAccountEntryMapping();

		if (organizationAccountEntryMapping == null) {
			return ListUtil.toLongArray(
				AccountEntryOrganizationRelLocalServiceUtil.
					getAccountEntryOrganizationRels(accountEntryId),
				AccountEntryOrganizationRelModel::getOrganizationId);
		}

		return organizationAccountEntryMapping.getOrganizationIds(
			accountEntryId);
	}

	private static class OrganizationAccountEntryMapping {

		public long[] getAccountEntryIds(Long organizationId) {
			return _accountEntryIdsMap.get(organizationId);
		}

		public long[] getOrganizationIds(Long accountEntryId) {
			return _organizationIdsMap.get(accountEntryId);
		}

		private OrganizationAccountEntryMapping(
			Map<Long, List<Long>> accountEntryIdsMap,
			Map<Long, List<Long>> organizationIdsMap) {

			for (Map.Entry<Long, List<Long>> entry :
					accountEntryIdsMap.entrySet()) {

				_accountEntryIdsMap.put(
					entry.getKey(), ArrayUtil.toLongArray(entry.getValue()));
			}

			for (Map.Entry<Long, List<Long>> entry :
					organizationIdsMap.entrySet()) {

				_organizationIdsMap.put(
					entry.getKey(), ArrayUtil.toLongArray(entry.getValue()));
			}
		}

		private final Map<Long, long[]> _accountEntryIdsMap = new HashMap<>();
		private final Map<Long, long[]> _organizationIdsMap = new HashMap<>();

	}

}