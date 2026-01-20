/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.search.spi.model.index.contributor;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.model.AccountGroupRelTable;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.account.service.AccountGroupRelLocalServiceUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.ReindexCacheThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class AccountGroupAccountEntryDocumentContributorUtil {

	public static void contribute(
		Document document, AccountEntry accountEntry) {

		document.addKeyword(
			"accountGroupIds",
			_getAccountGroupIds(accountEntry.getAccountEntryId()));
	}

	public static void contribute(
		Document document, AccountGroup accountGroup) {

		document.addKeyword(
			"accountEntryIds",
			_getAccountEntryIds(accountGroup.getAccountGroupId()));
	}

	private static long[] _getAccountEntryIds(Long accountGroupId) {
		AccountGroupAccountEntryMapping accountGroupAccountEntryMapping =
			_getAccountGroupAccountEntryMapping();

		if (accountGroupAccountEntryMapping == null) {
			return ListUtil.toLongArray(
				AccountGroupRelLocalServiceUtil.getAccountGroupRels(
					accountGroupId, AccountEntry.class.getName()),
				AccountGroupRel::getClassPK);
		}

		return accountGroupAccountEntryMapping.getAccountEntryIds(
			accountGroupId);
	}

	private static AccountGroupAccountEntryMapping
		_getAccountGroupAccountEntryMapping() {

		return ReindexCacheThreadLocal.getGlobalReindexCache(
			() -> -1,
			AccountGroupAccountEntryDocumentContributorUtil.class.getName(),
			count -> {
				Map<Long, List<Long>> accountEntryIdsMap = new HashMap<>();
				Map<Long, List<Long>> accountGroupIdsMap = new HashMap<>();

				AccountGroupRelLocalService accountGroupRelLocalService =
					AccountGroupRelLocalServiceUtil.getService();

				for (Object[] values :
						accountGroupRelLocalService.<List<Object[]>>dslQuery(
							DSLQueryFactoryUtil.select(
								AccountGroupRelTable.INSTANCE.classPK,
								AccountGroupRelTable.INSTANCE.accountGroupId
							).from(
								AccountGroupRelTable.INSTANCE
							).where(
								AccountGroupRelTable.INSTANCE.classNameId.eq(
									ClassNameLocalServiceUtil.getClassNameId(
										AccountEntry.class.getName()))
							),
							false)) {

					Long accountEntryId = (Long)values[0];

					Long accountGroupId = (Long)values[1];

					List<Long> accountEntryIds =
						accountEntryIdsMap.computeIfAbsent(
							accountGroupId, key -> new ArrayList<>());

					accountEntryIds.add(accountEntryId);

					List<Long> accountGroupIds =
						accountGroupIdsMap.computeIfAbsent(
							accountEntryId, key -> new ArrayList<>());

					accountGroupIds.add(accountGroupId);
				}

				return new AccountGroupAccountEntryMapping(
					accountEntryIdsMap, accountGroupIdsMap);
			});
	}

	private static long[] _getAccountGroupIds(Long accountEntryId) {
		AccountGroupAccountEntryMapping accountGroupAccountEntryMapping =
			_getAccountGroupAccountEntryMapping();

		if (accountGroupAccountEntryMapping == null) {
			return ListUtil.toLongArray(
				AccountGroupRelLocalServiceUtil.getAccountGroupRels(
					AccountEntry.class.getName(), accountEntryId),
				AccountGroupRel::getAccountGroupId);
		}

		return accountGroupAccountEntryMapping.getAccountGroupIds(
			accountEntryId);
	}

	private static class AccountGroupAccountEntryMapping {

		public long[] getAccountEntryIds(Long accountGroupId) {
			return _accountEntryIdsMap.get(accountGroupId);
		}

		public long[] getAccountGroupIds(Long accountEntryId) {
			return _accountGroupIdsMap.get(accountEntryId);
		}

		private AccountGroupAccountEntryMapping(
			Map<Long, List<Long>> accountEntryIdsMap,
			Map<Long, List<Long>> accountGroupIdsMap) {

			for (Map.Entry<Long, List<Long>> entry :
					accountEntryIdsMap.entrySet()) {

				_accountEntryIdsMap.put(
					entry.getKey(), ArrayUtil.toLongArray(entry.getValue()));
			}

			for (Map.Entry<Long, List<Long>> entry :
					accountGroupIdsMap.entrySet()) {

				_accountGroupIdsMap.put(
					entry.getKey(), ArrayUtil.toLongArray(entry.getValue()));
			}
		}

		private final Map<Long, long[]> _accountEntryIdsMap = new HashMap<>();
		private final Map<Long, long[]> _accountGroupIdsMap = new HashMap<>();

	}

}