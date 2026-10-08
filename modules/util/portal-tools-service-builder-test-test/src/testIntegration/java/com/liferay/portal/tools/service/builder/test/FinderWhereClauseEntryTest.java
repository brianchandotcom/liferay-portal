/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.tools.service.builder.test.model.FinderWhereClauseEntry;
import com.liferay.portal.tools.service.builder.test.service.persistence.FinderWhereClauseEntryPersistence;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class FinderWhereClauseEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.portal.tools.service.builder.test.service"));

	@Test
	public void testFindByH_N_N() throws Exception {
		long headId = RandomTestUtil.nextLong();

		try {
			long[] finderWhereClauseEntryIds = {
				_addFinderWhereClauseEntry(headId, null, null),
				_addFinderWhereClauseEntry(headId, null, ""),
				_addFinderWhereClauseEntry(headId, "", null),
				_addFinderWhereClauseEntry(headId, "", "")
			};

			_addFinderWhereClauseEntry(
				headId, RandomTestUtil.randomString(),
				RandomTestUtil.randomString());

			Set<Long> actualFinderWhereClauseEntryIds = new HashSet<>();

			for (FinderWhereClauseEntry finderWhereClauseEntry :
					_finderWhereClauseEntryPersistence.findByH_N_N(
						headId, "", "")) {

				actualFinderWhereClauseEntryIds.add(
					finderWhereClauseEntry.getFinderWhereClauseEntryId());
			}

			Assert.assertEquals(
				SetUtil.fromArray(finderWhereClauseEntryIds),
				actualFinderWhereClauseEntryIds);
			Assert.assertEquals(
				4,
				_finderWhereClauseEntryPersistence.countByH_N_N(
					headId, "", ""));
		}
		finally {
			DB db = DBManagerUtil.getDB();

			db.runSQL(
				"delete from FinderWhereClauseEntry where headId = " + headId);

			_finderWhereClauseEntryPersistence.clearCache();
		}
	}

	private long _addFinderWhereClauseEntry(
			long headId, String name, String nickname)
		throws Exception {

		long finderWhereClauseEntryId = RandomTestUtil.nextLong();

		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into FinderWhereClauseEntry " +
					"(finderWhereClauseEntryId, headId, name, nickname) " +
						"values (?, ?, ?, ?)")) {

			preparedStatement.setLong(1, finderWhereClauseEntryId);
			preparedStatement.setLong(2, headId);
			preparedStatement.setString(3, name);
			preparedStatement.setString(4, nickname);

			preparedStatement.executeUpdate();
		}

		return finderWhereClauseEntryId;
	}

	@Inject
	private FinderWhereClauseEntryPersistence
		_finderWhereClauseEntryPersistence;

}