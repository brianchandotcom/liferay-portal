/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.search.spi.model.index.contributor;

import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.kernel.model.User",
	service = ModelDocumentContributor.class
)
public class UserModelDocumentContributor
	implements ModelDocumentContributor<User> {

	@Override
	public void contribute(Document document, User user) {
		try {
			long[] accountEntryIds = getAccountEntryIds(user);

			if (ArrayUtil.isNotEmpty(accountEntryIds)) {
				document.addKeyword("accountEntryIds", accountEntryIds);
				document.addKeyword(
					"emailAddressDomain", _getEmailAddressDomain(user));
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to index user " + user.getUserId(), exception);
			}
		}
	}

	protected long[] getAccountEntryIds(User user) throws Exception {
		Set<Long> accountEntryIds = new HashSet<>();

		DynamicQuery dynamicQuery =
			accountEntryUserRelLocalService.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("accountUserId", user.getUserId()));

		List<AccountEntryUserRel> accountEntryUserRels =
			accountEntryUserRelLocalService.dynamicQuery(dynamicQuery);

		for (AccountEntryUserRel accountEntryUserRel : accountEntryUserRels) {
			accountEntryIds.add(accountEntryUserRel.getAccountEntryId());
		}

		return ArrayUtil.toLongArray(accountEntryIds);
	}

	@Reference
	protected AccountEntryUserRelLocalService accountEntryUserRelLocalService;

	private String _getEmailAddressDomain(User user) {
		String emailAddress = user.getEmailAddress();

		return emailAddress.substring(emailAddress.indexOf(StringPool.AT) + 1);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserModelDocumentContributor.class);

}