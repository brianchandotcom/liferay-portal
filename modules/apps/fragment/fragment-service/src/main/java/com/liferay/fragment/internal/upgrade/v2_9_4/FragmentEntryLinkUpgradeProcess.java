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

package com.liferay.fragment.internal.upgrade.v2_9_4;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Eudaldo Alonso
 */
public class FragmentEntryLinkUpgradeProcess extends UpgradeProcess {

	public FragmentEntryLinkUpgradeProcess(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker) {

		_fragmentCollectionContributorTracker =
			fragmentCollectionContributorTracker;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("FragmentEntryLink", "type_")) {
			alterTableAddColumn("FragmentEntryLink", "type_", "INTEGER");
		}

		_updateFragmentEntryType();
	}

	private int _getFragmentEntryType(long fragmentEntryId) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select type_ from FragmentEntry where fragmentEntryId = ? ")) {

			preparedStatement.setLong(1, fragmentEntryId);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return resultSet.getInt("type_");
			}
		}

		return FragmentConstants.TYPE_COMPONENT;
	}

	private int _getFragmentEntryType(
			String editableValues, long fragmentEntryId, String rendererKey)
		throws Exception {

		if (Validator.isNotNull(editableValues)) {
			try {
				JSONObject editableValuesJSONObject =
					JSONFactoryUtil.createJSONObject(editableValues);

				if (editableValuesJSONObject.has("portletId")) {
					return FragmentConstants.TYPE_PORTLET;
				}
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}

		if (Validator.isNotNull(rendererKey)) {
			FragmentEntry fragmentEntry =
				_fragmentCollectionContributorTracker.getFragmentEntry(
					rendererKey);

			if (fragmentEntry != null) {
				return fragmentEntry.getType();
			}
		}

		return _getFragmentEntryType(fragmentEntryId);
	}

	private void _updateFragmentEntryType() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select fragmentEntryLinkId, fragmentEntryId, " +
					"editableValues, rendererKey from FragmentEntryLink");
			ResultSet resultSet1 = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update FragmentEntryLink set type_ = ? where " +
						"fragmentEntryLinkId = ?")) {

			while (resultSet1.next()) {
				long fragmentEntryLinkId = resultSet1.getLong(
					"fragmentEntryLinkId");

				long fragmentEntryId = resultSet1.getLong("fragmentEntryId");
				String editableValues = resultSet1.getString("editableValues");
				String rendererKey = resultSet1.getString("rendererKey");

				preparedStatement2.setInt(
					1,
					_getFragmentEntryType(
						editableValues, fragmentEntryId, rendererKey));

				preparedStatement2.setLong(2, fragmentEntryLinkId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryLinkUpgradeProcess.class);

	private final FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

}