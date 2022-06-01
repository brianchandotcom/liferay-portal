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

package com.liferay.fragment.internal.upgrade.v2_9_1;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Lourdes Fernández Besada
 */
public class FragmentEntryLinkUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select fragmentEntryLinkId, editableValues from " +
					"FragmentEntryLink");
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update FragmentEntryLink set editableValues = ? " +
							"where fragmentEntryLinkId = ?"))) {

			while (resultSet.next()) {
				long fragmentEntryLinkId = resultSet.getLong(
					"fragmentEntryLinkId");

				String editableValues = resultSet.getString("editableValues");

				preparedStatement2.setString(
					1, _stripEmptyFragmentEntryProcessor(editableValues));

				preparedStatement2.setLong(2, fragmentEntryLinkId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private String _stripEmptyFragmentEntryProcessor(String editableValues) {
		try {
			JSONObject editableValuesJSONObject =
				JSONFactoryUtil.createJSONObject(editableValues);

			for (String fragmentEntryProcessorKey :
					_FRAGMENT_ENTRY_PROCESSOR_KEYS) {

				JSONObject fragmentEntryProcessorJSONObject =
					editableValuesJSONObject.getJSONObject(
						fragmentEntryProcessorKey);

				if ((fragmentEntryProcessorJSONObject != null) &&
					(fragmentEntryProcessorJSONObject.length() == 0)) {

					editableValuesJSONObject.remove(fragmentEntryProcessorKey);
				}
			}

			return editableValuesJSONObject.toString();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return editableValues;
	}

	private static final String[] _FRAGMENT_ENTRY_PROCESSOR_KEYS = {
		"com.liferay.fragment.entry.processor.background.image." +
			"BackgroundImageFragmentEntryProcessor",
		"com.liferay.fragment.entry.processor.editable." +
			"EditableFragmentEntryProcessor",
		"com.liferay.fragment.entry.processor.freemarker." +
			"FreeMarkerFragmentEntryProcessor"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryLinkUpgradeProcess.class);

}