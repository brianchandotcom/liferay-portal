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
	immediate = true,
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
					"emailAddressDomain", getEmailAddressDomain(user));
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to index user " + user.getUserId(), e);
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

	protected String getEmailAddressDomain(User user) {
		String emailAddress = user.getEmailAddress();

		return emailAddress.substring(emailAddress.indexOf(StringPool.AT) + 1);
	}

	@Reference
	protected AccountEntryUserRelLocalService accountEntryUserRelLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		UserModelDocumentContributor.class);

}