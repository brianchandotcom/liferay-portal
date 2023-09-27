/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.uad.exporter;

import com.liferay.object.internal.uad.constants.ObjectUADConstants;
import com.liferay.object.internal.uad.util.ObjectEntryUADUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.user.associated.data.exporter.DynamicQueryUADExporter;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

/**
 * @author Carolina Barbosa
 */
public class ObjectEntryUADExporter
	extends DynamicQueryUADExporter<ObjectEntry> {

	public ObjectEntryUADExporter(
		ObjectDefinition objectDefinition,
		ObjectEntryLocalService objectEntryLocalService) {

		_objectDefinition = objectDefinition;
		_objectEntryLocalService = objectEntryLocalService;
	}

	@Override
	public Class<ObjectEntry> getTypeClass() {
		return ObjectEntry.class;
	}

	@Override
	public String getTypeKey() {
		return _objectDefinition.getClassName();
	}

	@Override
	protected ActionableDynamicQuery doGetActionableDynamicQuery() {
		return ObjectEntryUADUtil.addActionableDynamicQueryCriteria(
			_objectEntryLocalService.getActionableDynamicQuery(),
			_objectDefinition.getObjectDefinitionId());
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return ObjectUADConstants.USER_ID_FIELD_NAMES_OBJECT_ENTRY;
	}

	@Override
	protected ActionableDynamicQuery getActionableDynamicQuery(long userId) {
		return ObjectEntryUADUtil.addActionableDynamicQueryCriteria(
			doGetActionableDynamicQuery(), doGetUserIdFieldNames(), userId);
	}

	@Override
	protected String toXmlString(ObjectEntry objectEntry) {
		StringBundler sb = new StringBundler();

		sb.append(
			StringBundler.concat(
				"<model><model-name>", _objectDefinition.getClassName(),
				"</model-name>"));
		sb.append(
			_getColumn(
				"objectEntryId",
				String.valueOf(objectEntry.getObjectEntryId())));
		sb.append(
			_getColumn(
				"statusByUserId",
				String.valueOf(objectEntry.getStatusByUserId())));
		sb.append(
			_getColumn("statusByUserName", objectEntry.getStatusByUserName()));
		sb.append(
			_getColumn("userId", String.valueOf(objectEntry.getUserId())));
		sb.append(_getColumn("userName", objectEntry.getUserName()));

		Map<String, Serializable> values = objectEntry.getValues();

		for (Map.Entry<String, Serializable> entry : values.entrySet()) {
			if (Objects.equals(
					entry.getKey(), _objectDefinition.getPKObjectFieldName())) {

				continue;
			}

			sb.append(
				_getColumn(entry.getKey(), String.valueOf(entry.getValue())));
		}

		sb.append("</model>");

		return sb.toString();
	}

	private String _getColumn(String columnName, String columnValue) {
		return StringBundler.concat(
			"<column><column-name>", columnName,
			"</column-name><column-value><![CDATA[", columnValue,
			"]]></column-value></column>");
	}

	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryLocalService _objectEntryLocalService;

}