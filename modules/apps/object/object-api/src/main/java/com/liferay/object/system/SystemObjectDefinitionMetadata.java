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

package com.liferay.object.system;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public interface SystemObjectDefinitionMetadata {

	public long addBaseModel(User user, Map<String, Object> values)
		throws Exception;

	public BaseModel<?> deleteBaseModel(BaseModel<?> baseModel)
		throws PortalException;

	public BaseModel<?> fetchBaseModelByExternalReferenceCode(
		String externalReferenceCode, long companyId);

	public BaseModel<?> getBaseModelByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException;

	public String getExternalReferenceCode(long primaryKey)
		throws PortalException;

	public JaxRsApplicationDescriptor getJaxRsApplicationDescriptor();

	public Map<Locale, String> getLabelMap();

	public Class<?> getModelClass();

	public String getModelClassName();

	public String getName();

	public List<ObjectField> getObjectFields();

	public Map<Locale, String> getPluralLabelMap();

	public Column<?, Long> getPrimaryKeyColumn();

	public String getRESTDTOIdPropertyName();

	public String getScope();

	public Table getTable();

	public String getTitleObjectFieldName();

	public default Map<String, Object> getVariables(
		String contentType, ObjectDefinition objectDefinition,
		boolean oldValues, JSONObject payloadJSONObject) {

		Class<?> modelClass = getModelClass();

		Object object = payloadJSONObject.get(
			"model" + modelClass.getSimpleName());

		if (oldValues) {
			object = payloadJSONObject.get(
				"original" + modelClass.getSimpleName());
		}

		if (object == null) {
			object = payloadJSONObject.get(
				StringUtil.lowerCaseFirstLetter(objectDefinition.getName()));
		}

		if (object == null) {
			return null;
		}

		Map<String, Object> variables = new HashMap<>();

		if (object instanceof JSONObject) {
			Map<String, Object> map = ObjectMapperUtil.readValue(
				Map.class, object);

			Map<String, Object> jsonObjectMap = (Map<String, Object>)map.get(
				"_jsonObject");

			variables.putAll((Map<String, Object>)jsonObjectMap.get("map"));
		}
		else if (object instanceof Map) {
			variables.putAll((Map<String, Object>)object);
		}

		Map<String, Object> map = (Map<String, Object>)payloadJSONObject.get(
			"modelDTO" + contentType);

		if (oldValues) {
			map = (Map<String, Object>)payloadJSONObject.get(
				"originalDTO" + contentType);
		}

		if (map != null) {
			variables.putAll(map);
		}

		Map<String, Object> extendedProperties =
			(Map<String, Object>)payloadJSONObject.get("extendedProperties");

		if (extendedProperties != null) {
			variables.putAll(extendedProperties);
		}

		return variables;
	}

	public int getVersion();

	public void updateBaseModel(
			long primaryKey, User user, Map<String, Object> values)
		throws Exception;

	public long upsertBaseModel(
			String externalReferenceCode, long companyId, User user,
			Map<String, Object> values)
		throws Exception;

}