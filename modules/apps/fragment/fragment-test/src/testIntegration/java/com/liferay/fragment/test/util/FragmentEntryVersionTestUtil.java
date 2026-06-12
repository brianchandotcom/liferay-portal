/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.test.util;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryVersion;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.fragment.service.persistence.FragmentEntryVersionUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rubén Pulido
 */
public class FragmentEntryVersionTestUtil {

	public static final int FRAGMENT_ENTRY_VERSIONS_COUNT_MAX = 10;

	public static FragmentEntry addFragmentEntry(long groupId)
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(groupId);

		return FragmentEntryLocalServiceUtil.addFragmentEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(), groupId,
			fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), false, StringPool.BLANK, null, 0,
			false, false, FragmentConstants.TYPE_COMPONENT, null,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId()));
	}

	public static int countVersions(
			long ctCollectionId, FragmentEntry fragmentEntry)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) as count from FragmentEntryVersion where " +
					"ctCollectionId = ? and fragmentEntryId = ?")) {

			preparedStatement.setLong(1, ctCollectionId);
			preparedStatement.setLong(2, fragmentEntry.getFragmentEntryId());

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return (int)resultSet.getLong("count");
				}

				return 0;
			}
		}
	}

	public static List<Integer> getVersions(FragmentEntry fragmentEntry)
		throws Throwable {

		return TransactionInvokerUtil.invoke(
			_transactionConfig,
			() -> TransformUtil.transform(
				FragmentEntryVersionUtil.findByFragmentEntryId(
					fragmentEntry.getFragmentEntryId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS,
					OrderByComparatorFactoryUtil.create(
						"FragmentEntryVersion", "version", true)),
				FragmentEntryVersion::getVersion));
	}

	public static List<Integer> getVersions(
			long ctCollectionId, FragmentEntry fragmentEntry)
		throws Exception {

		List<Integer> versions = new ArrayList<>();

		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				"select version from FragmentEntryVersion where " +
					"ctCollectionId = ? and fragmentEntryId = ? order by " +
						"version")) {

			preparedStatement.setLong(1, ctCollectionId);
			preparedStatement.setLong(2, fragmentEntry.getFragmentEntryId());

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					versions.add(resultSet.getInt("version"));
				}
			}
		}

		return versions;
	}

	public static List<Integer> insertFragmentEntryVersions(
			int count, long ctCollectionId, FragmentEntry fragmentEntry)
		throws Exception {

		List<Integer> versions = new ArrayList<>(count);

		long now = System.currentTimeMillis();

		Timestamp timestamp = new Timestamp(now);

		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					StringBundler.concat(
						"insert into FragmentEntryVersion (mvccVersion, ",
						"ctCollectionId, fragmentEntryVersionId, version, ",
						"fragmentEntryId, groupId, companyId, userId, ",
						"createDate, modifiedDate, name, status) values (0, ",
						"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"))) {

			String className = FragmentEntryVersion.class.getName();
			int startVersion = _getStartVersion(connection, fragmentEntry);

			for (int i = 0; i < count; i++) {
				int version = startVersion + i;

				versions.add(version);

				preparedStatement.setLong(1, ctCollectionId);
				preparedStatement.setLong(
					2, CounterLocalServiceUtil.increment(className));
				preparedStatement.setInt(3, version);
				preparedStatement.setLong(
					4, fragmentEntry.getFragmentEntryId());
				preparedStatement.setLong(5, fragmentEntry.getGroupId());
				preparedStatement.setLong(6, fragmentEntry.getCompanyId());
				preparedStatement.setLong(7, fragmentEntry.getUserId());
				preparedStatement.setTimestamp(8, timestamp);
				preparedStatement.setTimestamp(9, new Timestamp(now + i));
				preparedStatement.setString(10, RandomTestUtil.randomString());
				preparedStatement.setInt(11, WorkflowConstants.STATUS_APPROVED);

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}

		FragmentEntryVersionUtil.clearCache();

		return versions;
	}

	private static int _getStartVersion(
			Connection connection, FragmentEntry fragmentEntry)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select max(version) as maxVersion from FragmentEntryVersion " +
					"where fragmentEntryId = ?")) {

			preparedStatement.setLong(1, fragmentEntry.getFragmentEntryId());

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				resultSet.next();

				return resultSet.getInt("maxVersion") + 1;
			}
		}
	}

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

}