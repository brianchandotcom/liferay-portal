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

package com.liferay.object.storage.salesforce.internal.rest.manager.v1_0;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.dto.v1_0.Status;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.storage.salesforce.internal.client.SalesforceClient;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Guilherme Camacho
 */
@Component(
	enabled = false, immediate = true,
	property = "object.entry.manager.storage.type=" + ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE,
	service = ObjectEntryManager.class
)
public class SalesforceObjectEntryManagerImpl implements ObjectEntryManager {

	@Override
	public ObjectEntry addObjectEntry(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, ObjectEntry objectEntry,
			String scopeKey)
		throws Exception {

		return null;
	}

	@Override
	public ObjectEntry addObjectRelationshipMappingTableValues(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, String objectRelationshipName,
			long primaryKey1, long primaryKey2)
		throws Exception {

		return null;
	}

	@Override
	public ObjectEntry addOrUpdateObjectEntry(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ObjectDefinition objectDefinition,
			ObjectEntry objectEntry, String scopeKey)
		throws Exception {

		return null;
	}

	@Override
	public void deleteObjectEntry(
			ObjectDefinition objectDefinition, long objectEntryId)
		throws Exception {
	}

	@Override
	public void deleteObjectEntry(
			String externalReferenceCode, long companyId,
			ObjectDefinition objectDefinition, String scopeKey)
		throws Exception {
	}

	@Override
	public ObjectEntry fetchObjectEntry(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, long objectEntryId)
		throws Exception {

		return null;
	}

	@Override
	public Page<ObjectEntry> getObjectEntries(
			long companyId, ObjectDefinition objectDefinition, String scopeKey,
			Aggregation aggregation, DTOConverterContext dtoConverterContext,
			Filter filter, Pagination pagination, String search, Sort[] sorts)
		throws Exception {

		JSONObject responseJSONObject = _salesforceClient.query(
			"SELECT FIELDS(ALL) FROM " +
				_getSalesforceObjectName(objectDefinition.getName()) +
					_getSalesforcePagination(pagination));

		if ((responseJSONObject == null) ||
			(responseJSONObject.length() == 0)) {

			return Page.of(Collections.emptyList());
		}

		return Page.of(
			_toObjectEntries(responseJSONObject.getJSONArray("records")),
			pagination, responseJSONObject.getInt("totalSize"));
	}

	@Override
	public Page<ObjectEntry> getObjectEntries(
			long companyId, ObjectDefinition objectDefinition, String scopeKey,
			Aggregation aggregation, DTOConverterContext dtoConverterContext,
			Pagination pagination, Predicate predicate, String search,
			Sort[] sorts)
		throws Exception {

		return null;
	}

	@Override
	public Page<ObjectEntry> getObjectEntries(
			long companyId, ObjectDefinition objectDefinition, String scopeKey,
			Aggregation aggregation, DTOConverterContext dtoConverterContext,
			String filterString, Pagination pagination, String search,
			Sort[] sorts)
		throws Exception {

		return null;
	}

	@Override
	public ObjectEntry getObjectEntry(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, long objectEntryId)
		throws Exception {

		return null;
	}

	@Override
	public ObjectEntry getObjectEntry(
			DTOConverterContext dtoConverterContext,
			String externalReferenceCode, long companyId,
			ObjectDefinition objectDefinition, String scopeKey)
		throws Exception {

		return null;
	}

	@Override
	public Page<ObjectEntry> getObjectEntryRelatedObjectEntries(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, Long objectEntryId,
			String objectRelationshipName, Pagination pagination)
		throws Exception {

		return null;
	}

	@Override
	public ObjectEntry updateObjectEntry(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, long objectEntryId,
			ObjectEntry objectEntry)
		throws Exception {

		return null;
	}

	private String _getSalesforceObjectName(String objectDefinitionName) {
		return StringUtil.removeFirst(objectDefinitionName, "C_") + "__c";
	}

	private String _getSalesforcePagination(Pagination pagination) {
		return StringBundler.concat(
			" LIMIT ", pagination.getPageSize(), " OFFSET ",
			pagination.getStartPosition());
	}

	private List<ObjectEntry> _toObjectEntries(JSONArray jsonArray)
		throws Exception {

		return JSONUtil.toList(
			jsonArray, jsonObject -> _toObjectEntry(jsonObject));
	}

	private ObjectEntry _toObjectEntry(JSONObject jsonObject) throws Exception {
		ObjectEntry objectEntry = new ObjectEntry();

		objectEntry.setActions(Collections.emptyMap());

		objectEntry.setDateCreated(
			_dateFormat.parse(jsonObject.getString("CreatedDate")));
		objectEntry.setDateModified(
			_dateFormat.parse(jsonObject.getString("LastModifiedDate")));

		Status status = new Status();

		status.setCode(0);
		status.setLabel("approved");
		status.setLabel_i18n("Approved");

		objectEntry.setStatus(status);

		Iterator<String> iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			String key = iterator.next();

			if (StringUtil.contains(key, "__c", StringPool.BLANK) &&
				Validator.isNotNull(jsonObject.get(key))) {

				String customFieldName = StringUtil.removeLast(key, "__c");

				customFieldName = StringUtil.removeSubstring(
					customFieldName, "_");

				customFieldName = StringUtil.lowerCaseFirstLetter(
					customFieldName);

				objectEntry.getProperties(
				).put(
					customFieldName, jsonObject.get(key)
				);
			}
			else if (key.equals("Id")) {
				objectEntry.setExternalReferenceCode(jsonObject.getString(key));
			}
		}

		return objectEntry;
	}

	private static final DateFormat _dateFormat = new SimpleDateFormat(
		"yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	@Reference
	private SalesforceClient _salesforceClient;

}