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
import com.liferay.object.rest.dto.v1_0.util.CreatorUtil;
import com.liferay.object.rest.manager.v1_0.BaseObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.storage.salesforce.internal.http.SalesforceHttp;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
public class SalesforceObjectEntryManagerImpl
	extends BaseObjectEntryManager implements ObjectEntryManager {

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

		JSONObject responseJSONObject1 = _salesforceHttp.get(
			companyId, getGroupId(objectDefinition, scopeKey),
			HttpComponentsUtil.addParameter(
				"query", "q",
				StringBundler.concat(
					"SELECT FIELDS(ALL) FROM ",
					_getSalesforceObjectName(objectDefinition.getName()),
					_getSalesforcePagination(pagination))));

		if ((responseJSONObject1 == null) ||
			(responseJSONObject1.length() == 0)) {

			return Page.of(Collections.emptyList());
		}

		JSONObject responseJSONObject2 = _salesforceHttp.get(
			companyId, getGroupId(objectDefinition, scopeKey),
			HttpComponentsUtil.addParameter(
				"query", "q",
				"SELECT COUNT(Id) FROM " +
					_getSalesforceObjectName(objectDefinition.getName())));

		JSONArray jsonArray = responseJSONObject2.getJSONArray("records");

		return Page.of(
			_toObjectEntries(
				companyId, responseJSONObject1.getJSONArray("records")),
			pagination,
			jsonArray.getJSONObject(
				0
			).getInt(
				"expr0"
			));
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

		return _toObjectEntry(
			companyId, _getDateFormat(),
			_salesforceHttp.get(
				companyId, getGroupId(objectDefinition, scopeKey),
				StringBundler.concat(
					"sobjects/",
					_getSalesforceObjectName(objectDefinition.getName()), "/",
					externalReferenceCode)));
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

	private DateFormat _getDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	}

	private String _getSalesforceObjectName(String objectDefinitionName) {
		return StringUtil.removeFirst(objectDefinitionName, "C_") + "__c";
	}

	private String _getSalesforcePagination(Pagination pagination) {
		return StringBundler.concat(
			" LIMIT ", pagination.getPageSize(), " OFFSET ",
			pagination.getStartPosition());
	}

	private List<ObjectEntry> _toObjectEntries(
			long companyId, JSONArray jsonArray)
		throws Exception {

		return JSONUtil.toList(
			jsonArray,
			jsonObject -> _toObjectEntry(
				companyId, _getDateFormat(), jsonObject));
	}

	private ObjectEntry _toObjectEntry(
			long companyId, DateFormat dateFormat, JSONObject jsonObject)
		throws Exception {

		ObjectEntry objectEntry = new ObjectEntry() {
			{
				actions = Collections.emptyMap();
				creator = CreatorUtil.toCreator(
					_portal, Optional.empty(),
					_userLocalService.fetchUserByExternalReferenceCode(
						companyId, jsonObject.getString("OwnerId")));
				dateCreated = dateFormat.parse(
					jsonObject.getString("CreatedDate"));
				dateModified = dateFormat.parse(
					jsonObject.getString("LastModifiedDate"));
				externalReferenceCode = jsonObject.getString("Id");
				status = new Status() {
					{
						code = 0;
						label = "approved";
						label_i18n = "Approved";
					}
				};
			}
		};

		Iterator<String> iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			String key = iterator.next();

			if (StringUtil.contains(key, "__c", StringPool.BLANK)) {
				String customFieldName = StringUtil.removeLast(key, "__c");

				customFieldName = StringUtil.removeSubstring(
					customFieldName, "_");

				customFieldName = StringUtil.lowerCaseFirstLetter(
					customFieldName);

				Map<String, Object> properties = objectEntry.getProperties();

				properties.put(
					customFieldName,
					jsonObject.isNull(key) ? null : jsonObject.get(key));
			}
		}

		return objectEntry;
	}

	@Reference
	private Portal _portal;

	@Reference
	private SalesforceHttp _salesforceHttp;

	@Reference
	private UserLocalService _userLocalService;

}