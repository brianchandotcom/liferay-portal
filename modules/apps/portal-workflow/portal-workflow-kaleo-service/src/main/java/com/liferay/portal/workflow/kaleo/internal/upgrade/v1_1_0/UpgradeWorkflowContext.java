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

package com.liferay.portal.workflow.kaleo.internal.upgrade.v1_1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.PortletPreferencesIds;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.internal.upgrade.v1_3_0.WorkflowContextUpgradeHelper;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;

import java.io.Serializable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Map;

import org.jabsorb.JSONSerializer;
import org.jabsorb.serializer.AbstractSerializer;
import org.jabsorb.serializer.MarshallException;
import org.jabsorb.serializer.ObjectMatch;
import org.jabsorb.serializer.SerializerState;
import org.jabsorb.serializer.UnmarshallException;

import org.json.JSONObject;

/**
 * @author Jang Kim
 */
public class UpgradeWorkflowContext extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateTable("KaleoInstance", "kaleoInstanceId");
		updateTable("KaleoLog", "kaleoLogId");
		updateTable("KaleoTaskInstanceToken", "kaleoTaskInstanceTokenId");
	}

	protected JSONSerializer getJSONSerializer() throws Exception {
		if (_jsonSerializer == null) {
			_jsonSerializer = new JSONSerializer();

			_jsonSerializer.registerDefaultSerializers();

			_jsonSerializer.registerSerializer(
				new PortletPreferencesIdsSerializer());
		}

		return _jsonSerializer;
	}

	protected void updateTable(String tableName, String fieldName)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer(tableName);
			PreparedStatement ps = connection.prepareStatement(
				StringBundler.concat(
					"select ", fieldName, ", workflowContext from ", tableName,
					" where workflowContext is not null and workflowContext ",
					"not like '%serializable%'"));
			ResultSet rs = ps.executeQuery()) {

			JSONSerializer jsonSerializer = getJSONSerializer();

			while (rs.next()) {
				String workflowContextJSON = rs.getString("workflowContext");

				if (Validator.isNull(workflowContextJSON)) {
					continue;
				}

				long fieldValue = rs.getLong(fieldName);

				workflowContextJSON =
					_workflowContextUpgradeHelper.renamePortalClassNames(
						workflowContextJSON);

				Map<String, Serializable> workflowContext =
					(Map<String, Serializable>)jsonSerializer.fromJSON(
						workflowContextJSON);

				workflowContext =
					_workflowContextUpgradeHelper.renameEntryClassName(
						workflowContext);

				updateWorkflowContext(
					tableName, fieldName, fieldValue,
					WorkflowContextUtil.convert(workflowContext));
			}
		}
	}

	protected void updateWorkflowContext(
			String tableName, String primaryKeyName, long primaryKeyValue,
			String workflowContext)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				StringBundler.concat(
					"update ", tableName, " set workflowContext = ? where ",
					primaryKeyName, " = ?"))) {

			ps.setString(1, workflowContext);
			ps.setLong(2, primaryKeyValue);

			ps.executeUpdate();
		}
	}

	private JSONSerializer _jsonSerializer;
	private final WorkflowContextUpgradeHelper _workflowContextUpgradeHelper =
		new WorkflowContextUpgradeHelper();

	private static class PortletPreferencesIdsSerializer
		extends AbstractSerializer {

		@Override
		public Class<?>[] getJSONClasses() {
			return _JSON_CLASSES;
		}

		@Override
		public Class<?>[] getSerializableClasses() {
			return _SERIALIZABLE_CLASSES;
		}

		@Override
		public Object marshall(
				SerializerState serializerState, Object parentObject,
				Object object)
			throws MarshallException {

			throw new UnsupportedOperationException(
				"The marshall operation is unsupported");
		}

		@Override
		public ObjectMatch tryUnmarshall(
				SerializerState serializerState,
				@SuppressWarnings("rawtypes") Class clazz, Object object)
			throws UnmarshallException {

			JSONObject portletPreferencesIdsJSONObject = (JSONObject)object;

			ObjectMatch objectMatch = ObjectMatch.ROUGHLY_SIMILAR;

			if (portletPreferencesIdsJSONObject.has("companyId") &&
				portletPreferencesIdsJSONObject.has("ownerId") &&
				portletPreferencesIdsJSONObject.has("ownerType") &&
				portletPreferencesIdsJSONObject.has("plid") &&
				portletPreferencesIdsJSONObject.has("portletId")) {

				objectMatch = ObjectMatch.OKAY;
			}

			serializerState.setSerialized(object, objectMatch);

			return objectMatch;
		}

		@Override
		public Object unmarshall(
				SerializerState serializerState,
				@SuppressWarnings("rawtypes") Class clazz, Object object)
			throws UnmarshallException {

			JSONObject portletPreferencesIdsJSONObject = (JSONObject)object;

			long companyId = 0;

			try {
				companyId = portletPreferencesIdsJSONObject.getLong(
					"companyId");
			}
			catch (Exception e) {
				throw new UnmarshallException("companyId is undefined", e);
			}

			long ownerId = 0;

			try {
				ownerId = portletPreferencesIdsJSONObject.getLong("ownerId");
			}
			catch (Exception e) {
				throw new UnmarshallException("ownerId is undefined", e);
			}

			int ownerType = 0;

			try {
				ownerType = portletPreferencesIdsJSONObject.getInt("ownerType");
			}
			catch (Exception e) {
				throw new UnmarshallException("ownerType is undefined", e);
			}

			long plid = 0;

			try {
				plid = portletPreferencesIdsJSONObject.getLong("plid");
			}
			catch (Exception e) {
				throw new UnmarshallException("plid is undefined", e);
			}

			String portletId = null;

			try {
				portletId = portletPreferencesIdsJSONObject.getString(
					"portletId");
			}
			catch (Exception e) {
				throw new UnmarshallException("portletId is undefined", e);
			}

			PortletPreferencesIds portletPreferencesIds =
				new PortletPreferencesIds(
					companyId, ownerId, ownerType, plid, portletId);

			serializerState.setSerialized(object, portletPreferencesIds);

			return portletPreferencesIds;
		}

		private static final Class<?>[] _JSON_CLASSES = {JSONObject.class};

		private static final Class<?>[] _SERIALIZABLE_CLASSES = {
			PortletPreferencesIds.class
		};

	}

}