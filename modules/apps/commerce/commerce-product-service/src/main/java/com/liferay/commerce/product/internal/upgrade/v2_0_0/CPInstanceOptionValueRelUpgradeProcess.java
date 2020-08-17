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
import com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelModelImpl;
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
		if (!hasTable(CPInstanceOptionValueRelModelImpl.TABLE_NAME)) {
			runSQL(CPInstanceOptionValueRelModelImpl.TABLE_SQL_CREATE);
		}

		_importContentFromCPInstanceJsonField();

		dropColumn(CPInstanceModelImpl.TABLE_NAME, "json");
	}

	private PreparedStatement _cpDefinitionOptionRelIdPreparedStatement()
		throws SQLException {

		if (_preparedStatementCPDefinitionOptionRelId != null) {
			return _preparedStatementCPDefinitionOptionRelId;
		}

		_preparedStatementCPDefinitionOptionRelId = connection.prepareStatement(
			_SELECT_CP_DEFINITION_OPTION_REL_ID);

		return _preparedStatementCPDefinitionOptionRelId;
	}

	private PreparedStatement _cpDefinitionOptionValueRelIdPreparedStatement()
		throws SQLException {

		if (_preparedStatementCPDefinitionOptionValueRelId != null) {
			return _preparedStatementCPDefinitionOptionValueRelId;
		}

		_preparedStatementCPDefinitionOptionValueRelId =
			connection.prepareStatement(
				_SELECT_CP_DEFINITION_OPTION_VALUE_REL_ID);

		return _preparedStatementCPDefinitionOptionValueRelId;
	}

	private long _getCPDefinitionOptionRelId(
			long cpDefinitionId, String cpDefinitionOptionRelKey)
		throws SQLException {

		PreparedStatement ps = _cpDefinitionOptionRelIdPreparedStatement();

		ps.setLong(1, cpDefinitionId);
		ps.setString(2, cpDefinitionOptionRelKey);

		try (ResultSet resultSet = ps.executeQuery()) {
			if (resultSet.next()) {
				return resultSet.getLong("CPDefinitionOptionRelId");
			}
		}

		return 0;
	}

	private long _getCPDefinitionOptionValueRelId(
			long cpDefinitionOptionRelId, String cpDefinitionOptionValueKey)
		throws SQLException {

		PreparedStatement ps = _cpDefinitionOptionValueRelIdPreparedStatement();

		ps.setLong(1, cpDefinitionOptionRelId);
		ps.setString(2, cpDefinitionOptionValueKey);

		try (ResultSet resultSet = ps.executeQuery()) {
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

		try (PreparedStatement ps =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, insertCPInstanceOptionValueRelSQL);
			Statement s = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = s.executeQuery(
				"select CPInstanceId, groupId, companyId, userId, userName, " +
					"CPDefinitionId, json from CPInstance")) {

			while (rs.next()) {
				_queueInsertCPInstanceOptionValueRelCommands(ps, rs);
			}

			ps.executeBatch();
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
			PreparedStatement ps, ResultSet rs)
		throws JSONException, SQLException {

		long groupId = rs.getLong("groupId");
		long companyId = rs.getLong("companyId");
		long userId = rs.getLong("userId");

		String userName = rs.getString("userName");

		long cpInstanceId = rs.getLong("CPInstanceId");
		long cpDefinitionId = rs.getLong("CPDefinitionId");

		String json = rs.getString("json");

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

				ps.setString(1, uuid);
				ps.setLong(2, cpInstanceOptionValueRelId);
				ps.setLong(3, groupId);
				ps.setLong(4, companyId);
				ps.setLong(5, userId);
				ps.setString(6, userName);

				Date now = new Date(System.currentTimeMillis());

				ps.setDate(7, now);
				ps.setDate(8, now);

				ps.setLong(9, cpDefinitionOptionRelId);
				ps.setLong(10, cpDefinitionOptionValueRelId);
				ps.setLong(11, cpInstanceId);

				ps.addBatch();
			}
		}
	}

	private static final String _SELECT_CP_DEFINITION_OPTION_REL_ID =
		"select CPDefinitionOptionRelId from CPDefinitionOptionRel where " +
			"CPDefinitionId = ? and key_ = ?";

	private static final String _SELECT_CP_DEFINITION_OPTION_VALUE_REL_ID =
		"select CPDefinitionOptionValueRelId from CPDefinitionOptionValueRel " +
			"where CPDefinitionOptionRelId = ? and key_ = ?";

	private final JSONFactory _jsonFactory;
	private final PortalUUID _portalUUID;
	private PreparedStatement _preparedStatementCPDefinitionOptionRelId;
	private PreparedStatement _preparedStatementCPDefinitionOptionValueRelId;

}