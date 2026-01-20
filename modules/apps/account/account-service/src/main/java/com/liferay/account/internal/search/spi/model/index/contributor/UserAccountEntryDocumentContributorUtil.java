/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.search.spi.model.index.contributor;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.model.AccountEntryUserRelTable;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalServiceUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.ReindexCacheThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Shuyang Zhou
 */
public class UserAccountEntryDocumentContributorUtil {

	public static void contribute(
		Document document, AccountEntry accountEntry) {

		String type = accountEntry.getType();

		document.addKeyword(Field.TYPE, type);

		long[] accountUserIds = _getAccountUserIds(
			accountEntry.getAccountEntryId());

		document.addKeyword("accountUserIds", accountUserIds);
		document.addKeyword(
			"allowNewUserMembership",
			_isAllowNewUserMembership(accountUserIds, type));
	}

	public static void contribute(Document document, User user) {
		long[] accountEntryIds = _getAccountEntryIds(user.getUserId());

		if (ArrayUtil.isNotEmpty(accountEntryIds)) {
			document.addKeyword("accountEntryIds", accountEntryIds);
			document.addKeyword(
				"emailAddressDomain", _getEmailAddressDomain(user));
		}
	}

	private static long[] _getAccountEntryIds(long userId) {
		UserAccountEntryMapping userAccountEntryMapping =
			_getUserAccountEntryMapping();

		if (userAccountEntryMapping == null) {
			return ListUtil.toLongArray(
				AccountEntryUserRelLocalServiceUtil.
					getAccountEntryUserRelsByAccountUserId(userId),
				AccountEntryUserRel::getAccountEntryId);
		}

		return userAccountEntryMapping.getAccountEntryIds(userId);
	}

	private static long[] _getAccountUserIds(long accountEntryId) {
		UserAccountEntryMapping userAccountEntryMapping =
			_getUserAccountEntryMapping();

		if (userAccountEntryMapping == null) {
			return ListUtil.toLongArray(
				AccountEntryUserRelLocalServiceUtil.
					getAccountEntryUserRelsByAccountEntryId(accountEntryId),
				AccountEntryUserRel::getAccountUserId);
		}

		return userAccountEntryMapping.getUserIds(accountEntryId);
	}

	private static String _getEmailAddressDomain(User user) {
		String emailAddress = user.getEmailAddress();

		return emailAddress.substring(emailAddress.indexOf(StringPool.AT) + 1);
	}

	private static UserAccountEntryMapping _getUserAccountEntryMapping() {
		return ReindexCacheThreadLocal.getGlobalReindexCache(
			() -> -1, UserAccountEntryDocumentContributorUtil.class.getName(),
			count -> {
				Map<Long, List<Long>> accountEntryIdsMap = new HashMap<>();
				Map<Long, List<Long>> userIdsMap = new HashMap<>();

				AccountEntryUserRelLocalService
					accountEntryUserRelLocalService =
						AccountEntryUserRelLocalServiceUtil.getService();

				for (Object[] values :
						accountEntryUserRelLocalService.
							<List<Object[]>>dslQuery(
								DSLQueryFactoryUtil.select(
									AccountEntryUserRelTable.INSTANCE.
										accountEntryId,
									AccountEntryUserRelTable.INSTANCE.
										accountUserId
								).from(
									AccountEntryUserRelTable.INSTANCE
								),
								false)) {

					Long accountEntryId = (Long)values[0];

					Long userId = (Long)values[1];

					List<Long> accountEntryIds =
						accountEntryIdsMap.computeIfAbsent(
							userId, key -> new ArrayList<>());

					accountEntryIds.add(accountEntryId);

					List<Long> userIds = userIdsMap.computeIfAbsent(
						accountEntryId, key -> new ArrayList<>());

					userIds.add(userId);
				}

				return new UserAccountEntryMapping(
					accountEntryIdsMap, userIdsMap);
			});
	}

	private static boolean _isAllowNewUserMembership(
		long[] accountUserIds, String type) {

		if (Objects.equals(type, AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON) &&
			ArrayUtil.isNotEmpty(accountUserIds)) {

			return false;
		}

		return true;
	}

	private static class UserAccountEntryMapping {

		public long[] getAccountEntryIds(Long userId) {
			return _accountEntryIdsMap.get(userId);
		}

		public long[] getUserIds(Long accountEntryId) {
			return _userIdsMap.get(accountEntryId);
		}

		private UserAccountEntryMapping(
			Map<Long, List<Long>> accountEntryIdsMap,
			Map<Long, List<Long>> userIdsMap) {

			for (Map.Entry<Long, List<Long>> entry :
					accountEntryIdsMap.entrySet()) {

				_accountEntryIdsMap.put(
					entry.getKey(), ArrayUtil.toLongArray(entry.getValue()));
			}

			for (Map.Entry<Long, List<Long>> entry : userIdsMap.entrySet()) {
				_userIdsMap.put(
					entry.getKey(), ArrayUtil.toLongArray(entry.getValue()));
			}
		}

		private final Map<Long, long[]> _accountEntryIdsMap = new HashMap<>();
		private final Map<Long, long[]> _userIdsMap = new HashMap<>();

	}

}