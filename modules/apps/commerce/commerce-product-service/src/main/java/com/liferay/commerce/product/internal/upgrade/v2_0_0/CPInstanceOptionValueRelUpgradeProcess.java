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

package com.liferay.commerce.product.internal.upgrade.v2_0_0;

import com.liferay.commerce.product.internal.upgrade.base.BaseCommerceProductServiceUpgradeProcess;
import com.liferay.commerce.product.model.impl.CPInstanceModelImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matija Petanjek
 * @author Igor Beslic
 */
public class CPInstanceOptionValueRelUpgradeProcess
	extends BaseCommerceProductServiceUpgradeProcess {

	public CPInstanceOptionValueRelUpgradeProcess(
		JSONFactory jsonFactory, PortalUUID portalUUID) {

		_jsonFactory = jsonFactory;
		_portalUUID = portalUUID;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_importContentFromCPInstanceJsonField();

		dropColumn(CPInstanceModelImpl.TABLE_NAME, "json");
	}

	private PreparedStatement _cpDefinitionOptionRelIdPreparedStatement()
		throws SQLException {

		if (_cpDefinitionOptionRelIdPreparedStatement != null) {
			return _cpDefinitionOptionRelIdPreparedStatement;
		}

		_cpDefinitionOptionRelIdPreparedStatement = connection.prepareStatement(
			_SELECT_CP_DEFINITION_OPTION_REL_ID);

		return _cpDefinitionOptionRelIdPreparedStatement;
	}

	private PreparedStatement _cpDefinitionOptionValueRelIdPreparedStatement()
		throws SQLException {

		if (_cpDefinitionOptionValueRelIdPreparedStatement != null) {
			return _cpDefinitionOptionValueRelIdPreparedStatement;
		}

		_cpDefinitionOptionValueRelIdPreparedStatement =
			connection.prepareStatement(
				_SELECT_CP_DEFINITION_OPTION_VALUE_REL_ID);

		return _cpDefinitionOptionValueRelIdPreparedStatement;
	}

	private long _getCPDefinitionOptionRelId(
			long cpDefinitionId, String cpDefinitionOptionRelKey)
		throws SQLException {

		PreparedStatement preparedStatement =
			_cpDefinitionOptionRelIdPreparedStatement();

		preparedStatement.setLong(1, cpDefinitionId);
		preparedStatement.setString(2, cpDefinitionOptionRelKey);

		try (ResultSet resultSet = preparedStatement.executeQuery()) {
			if (resultSet.next()) {
				return resultSet.getLong("CPDefinitionOptionRelId");
			}
		}

		return 0;
	}

	private long _getCPDefinitionOptionValueRelId(
			long cpDefinitionOptionRelId, String cpDefinitionOptionValueKey)
		throws SQLException {

		PreparedStatement preparedStatement =
			_cpDefinitionOptionValueRelIdPreparedStatement();

		preparedStatement.setLong(1, cpDefinitionOptionRelId);
		preparedStatement.setString(2, cpDefinitionOptionValueKey);

		try (ResultSet resultSet = preparedStatement.executeQuery()) {
			if (resultSet.next()) {
				return resultSet.getLong("CPDefinitionOptionValueRelId");
			}
		}

		return 0;
	}

	private void _importContentFromCPInstanceJsonField() throws Exception {
		String insertCPInstanceOptionValueRelSQL = StringBundler.concat(
			"insert into CPInstanceOptionValueRel(uuid_, ",
			"CPInstanceOptionValueRelId, groupId, companyId, userId, ",
			"userName, createDate, modifiedDate, CPDefinitionOptionRelId, ",
			"CPDefinitionOptionValueRelId, CPInstanceId) values (?, ?, ?, ?, ",
			"?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, insertCPInstanceOptionValueRelSQL);
			Statement s = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = s.executeQuery(
				"select CPInstanceId, groupId, companyId, userId, userName, " +
					"CPDefinitionId, json from CPInstance")) {

			while (resultSet.next()) {
				_queueInsertCPInstanceOptionValueRelCommands(
					preparedStatement, resultSet);
			}

			preparedStatement.executeBatch();
		}
	}

	private boolean _isDuplicatedCPInstanceOption(
		Map<String, String> processedCPInstanceOptions,
		String cpDefinitionOptionRelKey, String cpDefinitionOptionValueKey) {

		String processedCPDefinitionOptionValueKey =
			processedCPInstanceOptions.get(cpDefinitionOptionRelKey);

		if ((processedCPDefinitionOptionValueKey != null) &&
			processedCPDefinitionOptionValueKey.equals(
				cpDefinitionOptionValueKey)) {

			return true;
		}

		return false;
	}

	private void _queueInsertCPInstanceOptionValueRelCommands(
			PreparedStatement preparedStatement, ResultSet resultSet)
		throws JSONException, SQLException {

		long groupId = resultSet.getLong("groupId");
		long companyId = resultSet.getLong("companyId");
		long userId = resultSet.getLong("userId");

		String userName = resultSet.getString("userName");

		long cpInstanceId = resultSet.getLong("CPInstanceId");
		long cpDefinitionId = resultSet.getLong("CPDefinitionId");

		String json = resultSet.getString("json");

		Map<String, String> processedCPInstanceOptions = new HashMap<>();

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String cpDefinitionOptionRelKey = jsonObject.getString("key");

			long cpDefinitionOptionRelId = _getCPDefinitionOptionRelId(
				cpDefinitionId, cpDefinitionOptionRelKey);

			JSONArray cpDefinitionOptionValueRelJSONArray =
				jsonObject.getJSONArray("value");

			for (int j = 0; j < cpDefinitionOptionValueRelJSONArray.length();
				 j++) {

				String cpDefinitionOptionValueRelKey =
					cpDefinitionOptionValueRelJSONArray.getString(j);

				if (_isDuplicatedCPInstanceOption(
						processedCPInstanceOptions, cpDefinitionOptionRelKey,
						cpDefinitionOptionValueRelKey)) {

					continue;
				}

				processedCPInstanceOptions.put(
					cpDefinitionOptionRelKey, cpDefinitionOptionValueRelKey);

				long cpDefinitionOptionValueRelId =
					_getCPDefinitionOptionValueRelId(
						cpDefinitionOptionRelId, cpDefinitionOptionValueRelKey);

				String uuid = _portalUUID.generate();
				long cpInstanceOptionValueRelId = increment();

				preparedStatement.setString(1, uuid);
				preparedStatement.setLong(2, cpInstanceOptionValueRelId);
				preparedStatement.setLong(3, groupId);
				preparedStatement.setLong(4, companyId);
				preparedStatement.setLong(5, userId);
				preparedStatement.setString(6, userName);

				Date date = new Date(System.currentTimeMillis());

				preparedStatement.setDate(7, date);
				preparedStatement.setDate(8, date);

				preparedStatement.setLong(9, cpDefinitionOptionRelId);
				preparedStatement.setLong(10, cpDefinitionOptionValueRelId);
				preparedStatement.setLong(11, cpInstanceId);

				preparedStatement.addBatch();
			}
		}
	}

	private static final String _SELECT_CP_DEFINITION_OPTION_REL_ID =
		"select CPDefinitionOptionRelId from CPDefinitionOptionRel where " +
			"CPDefinitionId = ? and key_ = ?";

	private static final String _SELECT_CP_DEFINITION_OPTION_VALUE_REL_ID =
		"select CPDefinitionOptionValueRelId from CPDefinitionOptionValueRel " +
			"where CPDefinitionOptionRelId = ? and key_ = ?";

	private PreparedStatement _cpDefinitionOptionRelIdPreparedStatement;
	private PreparedStatement _cpDefinitionOptionValueRelIdPreparedStatement;
	private final JSONFactory _jsonFactory;
	private final PortalUUID _portalUUID;

}