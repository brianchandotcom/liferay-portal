/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v2_9_4;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Eudaldo Alonso
 */
public class FragmentEntryLinkUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateFragmentEntryType();
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"FragmentEntryLink", "type_ INTEGER")
		};
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
			String editableValues, long fragmentEntryId)
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

		return _getFragmentEntryType(fragmentEntryId);
	}

	private void _updateFragmentEntryType() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select fragmentEntryLinkId, fragmentEntryId, editableValues " +
					"from FragmentEntryLink");
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

				preparedStatement2.setInt(
					1, _getFragmentEntryType(editableValues, fragmentEntryId));

				preparedStatement2.setLong(2, fragmentEntryLinkId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryLinkUpgradeProcess.class);

}