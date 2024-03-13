/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.resource.v1_0.test;

import com.liferay.batch.engine.BaseBatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Vendel Toreki
 */
@Component(
	property = "batch.engine.task.item.delegate.name=user-account-performance-test",
	service = BatchEngineTaskItemDelegate.class
)
public class UserAccountPerformanceTestBatchEngineTaskItemDelegate
	extends BaseBatchEngineTaskItemDelegate<UserAccount> {

	public void generateTestData(int count) {
		for (int i = 0; i < count; i++) {
			UserAccount userAccount = new UserAccount();

			String alternateName = RandomTestUtil.randomString(
				NumericStringRandomizerBumper.INSTANCE,
				UniqueStringRandomizerBumper.INSTANCE);

			userAccount.setAlternateName(alternateName);
			userAccount.setEmailAddress(alternateName + "@liferay.com");

			String familyName = StringUtil.getTitleCase(
				RandomTestUtil.randomString(), true, "");
			String givenName = StringUtil.getTitleCase(
				RandomTestUtil.randomString(), true, "");

			userAccount.setFamilyName(familyName);
			userAccount.setGivenName(givenName);
			userAccount.setName(givenName + " " + familyName);

			_items.add(userAccount);
		}
	}

	@Override
	public Page<UserAccount> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		int endPos = pagination.getEndPosition();

		if (endPos > _items.size()) {
			endPos = _items.size();
		}

		List<UserAccount> items = _items.subList(
			pagination.getStartPosition(), endPos);

		return Page.of(
			items,
			Pagination.of(pagination.getPage(), pagination.getPageSize()),
			_items.size());
	}

	private final List<UserAccount> _items = new ArrayList<>();

}