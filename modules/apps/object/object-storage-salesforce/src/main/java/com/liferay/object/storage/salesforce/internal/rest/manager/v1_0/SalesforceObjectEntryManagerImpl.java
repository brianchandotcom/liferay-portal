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

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.dto.v1_0.Status;
import com.liferay.object.rest.dto.v1_0.util.CreatorUtil;
import com.liferay.object.rest.manager.v1_0.BaseObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.storage.salesforce.internal.http.SalesforceHttp;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.InlineSQLHelper;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionRegistryUtil;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Guilherme Camacho
 */
@Component(
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

		_checkPortletResourcePermission(
			objectDefinition, scopeKey, dtoConverterContext.getUser(),
			ObjectActionKeys.ADD_OBJECT_ENTRY);

		JSONObject responseJSONObject = _salesforceHttp.post(
			objectDefinition.getCompanyId(),
			getGroupId(objectDefinition, scopeKey),
			"sobjects/" + objectDefinition.getExternalReferenceCode(),
			_toJSONObject(objectDefinition, objectEntry));

		return getObjectEntry(
			objectDefinition.getCompanyId(), dtoConverterContext,
			responseJSONObject.getString("id"), objectDefinition, scopeKey);
	}

	@Override
	public ObjectEntry addObjectRelationshipMappingTableValues(
			DTOConverterContext dtoConverterContext,
			ObjectRelationship objectRelationship, long primaryKey1,
			long primaryKey2)
		throws Exception {

		return null;
	}

	@Override
	public Object addSystemObjectRelationshipMappingTableValues(
			ObjectDefinition objectDefinition,
			ObjectRelationship objectRelationship, long primaryKey1,
			long primaryKey2)
		throws Exception {

		return null;
	}

	@Override
	public void deleteObjectEntry(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ObjectDefinition objectDefinition,
			String scopeKey)
		throws Exception {

		_checkPortletResourcePermission(
			objectDefinition, scopeKey, dtoConverterContext.getUser(),
			ActionKeys.DELETE);

		_salesforceHttp.delete(
			companyId, getGroupId(objectDefinition, scopeKey),
			StringBundler.concat(
				"sobjects/", objectDefinition.getExternalReferenceCode(), "/",
				externalReferenceCode));
	}

	@Override
	public void deleteObjectEntry(
			ObjectDefinition objectDefinition, long objectEntryId)
		throws Exception {
	}

	@Override
	public void executeObjectAction(
			DTOConverterContext dtoConverterContext, String objectActionName,
			ObjectDefinition objectDefinition, long objectEntryId)
		throws Exception {
	}

	@Override
	public void executeObjectAction(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, String objectActionName,
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

		_checkPortletResourcePermission(
			objectDefinition, scopeKey, dtoConverterContext.getUser(),
			ActionKeys.VIEW);

		return _getObjectEntries(
			companyId, objectDefinition, scopeKey, dtoConverterContext,
			pagination, search, sorts);
	}

	@Override
	public Page<ObjectEntry> getObjectEntries(
			long companyId, ObjectDefinition objectDefinition, String scopeKey,
			Aggregation aggregation, DTOConverterContext dtoConverterContext,
			Pagination pagination, Predicate predicate, String search,
			Sort[] sorts)
		throws Exception {

		_checkPortletResourcePermission(
			objectDefinition, scopeKey, dtoConverterContext.getUser(),
			ActionKeys.VIEW);

		return _getObjectEntries(
			companyId, objectDefinition, scopeKey, dtoConverterContext,
			pagination, search, sorts);
	}

	@Override
	public Page<ObjectEntry> getObjectEntries(
			long companyId, ObjectDefinition objectDefinition, String scopeKey,
			Aggregation aggregation, DTOConverterContext dtoConverterContext,
			String filterString, Pagination pagination, String search,
			Sort[] sorts)
		throws Exception {

		_checkPortletResourcePermission(
			objectDefinition, scopeKey, dtoConverterContext.getUser(),
			ActionKeys.VIEW);

		return _getObjectEntries(
			companyId, objectDefinition, scopeKey, dtoConverterContext,
			pagination, search, sorts);
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
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ObjectDefinition objectDefinition,
			String scopeKey)
		throws Exception {

		_checkPortletResourcePermission(
			objectDefinition, scopeKey, dtoConverterContext.getUser(),
			ActionKeys.VIEW);

		if (Validator.isNull(externalReferenceCode)) {
			return null;
		}

		return _toObjectEntry(
			companyId, _getDateFormat(), dtoConverterContext,
			_salesforceHttp.get(
				companyId, getGroupId(objectDefinition, scopeKey),
				StringBundler.concat(
					"sobjects/", objectDefinition.getExternalReferenceCode(),
					"/", externalReferenceCode)),
			objectDefinition);
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
	public Page<Object> getRelatedSystemObjectEntries(
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

	@Override
	public ObjectEntry updateObjectEntry(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ObjectDefinition objectDefinition,
			ObjectEntry objectEntry, String scopeKey)
		throws Exception {

		_checkPortletResourcePermission(
			objectDefinition, scopeKey, dtoConverterContext.getUser(),
			ActionKeys.UPDATE);

		_salesforceHttp.patch(
			companyId, getGroupId(objectDefinition, scopeKey),
			StringBundler.concat(
				"sobjects/", objectDefinition.getExternalReferenceCode(), "/",
				externalReferenceCode),
			_toJSONObject(objectDefinition, objectEntry));

		return getObjectEntry(
			companyId, dtoConverterContext, externalReferenceCode,
			objectDefinition, scopeKey);
	}

	private void _checkPortletResourcePermission(
			ObjectDefinition objectDefinition, String scopeKey, User user,
			String actionId)
		throws Exception {

		PortletResourcePermission portletResourcePermission =
			_getPortletResourcePermission(objectDefinition);

		portletResourcePermission.check(
			_permissionCheckerFactory.create(user),
			getGroupId(objectDefinition, scopeKey), actionId);
	}

	private String _getAccountRestrictionPredicateString(
			long companyId, DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, String scopeKey)
		throws Exception {

		if (!_inlineSQLHelper.isEnabled(
				companyId, getGroupId(objectDefinition, scopeKey)) ||
			!objectDefinition.isAccountEntryRestricted()) {

			return StringPool.BLANK;
		}

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectDefinition.getAccountEntryRestrictedObjectFieldId());

		return StringBundler.concat(
			" WHERE ", objectField.getExternalReferenceCode(), " IN ('",
			StringUtil.merge(
				TransformUtil.transform(
					_accountEntryUserRelLocalService.
						getAccountEntryUserRelsByAccountUserId(
							dtoConverterContext.getUserId()),
					accountEntryUserRel -> {
						AccountEntry accountEntry =
							accountEntryUserRel.getAccountEntry();

						return accountEntry.getExternalReferenceCode();
					}),
				", '"),
			"')");
	}

	private DateFormat _getDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	}

	private String _getLocation(
		ObjectDefinition objectDefinition, Pagination pagination,
		String predicateString, String search, Sort[] sorts) {

		if (Validator.isNotNull(search)) {
			return HttpComponentsUtil.addParameter(
				"search", "q",
				StringBundler.concat(
					"FIND {", search, "} IN ALL FIELDS RETURNING ",
					objectDefinition.getExternalReferenceCode(), "(FIELDS(ALL)",
					predicateString,
					_getSorts(objectDefinition.getObjectDefinitionId(), sorts),
					_getSalesforcePagination(pagination), ")"));
		}

		return HttpComponentsUtil.addParameter(
			"query", "q",
			StringBundler.concat(
				"SELECT FIELDS(ALL) FROM ",
				objectDefinition.getExternalReferenceCode(), predicateString,
				_getSorts(objectDefinition.getObjectDefinitionId(), sorts),
				_getSalesforcePagination(pagination)));
	}

	private Page<ObjectEntry> _getObjectEntries(
			long companyId, ObjectDefinition objectDefinition, String scopeKey,
			DTOConverterContext dtoConverterContext, Pagination pagination,
			String search, Sort[] sorts)
		throws Exception {

		JSONObject responseJSONObject = _salesforceHttp.get(
			companyId, getGroupId(objectDefinition, scopeKey),
			_getLocation(
				objectDefinition, pagination,
				_getAccountRestrictionPredicateString(
					companyId, dtoConverterContext, objectDefinition, scopeKey),
				search, sorts));

		if ((responseJSONObject == null) ||
			(responseJSONObject.length() == 0)) {

			return Page.of(Collections.emptyList());
		}

		JSONArray jsonArray = Validator.isNotNull(search) ?
			responseJSONObject.getJSONArray("searchRecords") :
				responseJSONObject.getJSONArray("records");

		return Page.of(
			_toObjectEntries(
				companyId, dtoConverterContext, jsonArray, objectDefinition),
			pagination,
			_getTotalCount(
				companyId, objectDefinition,
				_getAccountRestrictionPredicateString(
					companyId, dtoConverterContext, objectDefinition, scopeKey),
				scopeKey, search));
	}

	private ObjectField _getObjectFieldByExternalReferenceCode(
		String externalReferenceCode, List<ObjectField> objectFields) {

		for (ObjectField objectField : objectFields) {
			if (Objects.equals(
					externalReferenceCode,
					objectField.getExternalReferenceCode())) {

				return objectField;
			}
		}

		return null;
	}

	private ObjectField _getObjectFieldByName(
		String name, List<ObjectField> objectFields) {

		for (ObjectField objectField : objectFields) {
			if (Objects.equals(name, objectField.getName())) {
				return objectField;
			}
		}

		return null;
	}

	private PortletResourcePermission _getPortletResourcePermission(
		ObjectDefinition objectDefinition) {

		ModelResourcePermission<com.liferay.object.model.ObjectEntry>
			modelResourcePermission =
				ModelResourcePermissionRegistryUtil.getModelResourcePermission(
					objectDefinition.getClassName());

		return modelResourcePermission.getPortletResourcePermission();
	}

	private String _getSalesforcePagination(Pagination pagination) {
		return StringBundler.concat(
			" LIMIT ", pagination.getPageSize(), " OFFSET ",
			pagination.getStartPosition());
	}

	private String _getSorts(long objectDefinitionId, Sort[] sorts) {
		if (ArrayUtil.isEmpty(sorts)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(objectDefinitionId);

		for (Sort sort : sorts) {
			String fieldName = sort.getFieldName();

			if (fieldName.startsWith("nestedFieldArray.")) {
				String[] parts = StringUtil.split(
					sort.getFieldName(), StringPool.POUND);

				fieldName = parts[1];
			}

			if (Objects.equals(fieldName, "status")) {
				continue;
			}

			if (sb.length() == 0) {
				sb.append(" ORDER BY ");
			}
			else {
				sb.append(StringPool.COMMA_AND_SPACE);
			}

			String defaultFieldName = _defaultObjectFieldNames.get(fieldName);

			if (defaultFieldName != null) {
				sb.append(defaultFieldName);
			}
			else {
				ObjectField objectField = _getObjectFieldByName(
					fieldName, objectFields);

				if (objectField == null) {
					continue;
				}

				sb.append(objectField.getExternalReferenceCode());
			}

			if (sort.isReverse()) {
				sb.append(" DESC");
			}
		}

		return sb.toString();
	}

	private int _getTotalCount(
		long companyId, ObjectDefinition objectDefinition,
		String predicateString, String scopeKey, String search) {

		if (Validator.isNotNull(search)) {
			JSONObject responseJSONObject = _salesforceHttp.get(
				companyId, getGroupId(objectDefinition, scopeKey),
				_getLocation(
					objectDefinition, Pagination.of(1, 200), predicateString,
					search, null));

			JSONArray jsonArray = responseJSONObject.getJSONArray(
				"searchRecords");

			return jsonArray.length();
		}

		JSONObject responseJSONObject = _salesforceHttp.get(
			companyId, getGroupId(objectDefinition, scopeKey),
			HttpComponentsUtil.addParameter(
				"query", "q",
				StringBundler.concat(
					"SELECT COUNT(Id) FROM ",
					objectDefinition.getExternalReferenceCode(),
					predicateString)));

		JSONArray jsonArray = responseJSONObject.getJSONArray("records");

		return jsonArray.getJSONObject(
			0
		).getInt(
			"expr0"
		);
	}

	private boolean _hasPortletResourcePermission(
			ObjectDefinition objectDefinition, String scopeKey, User user,
			String actionId)
		throws Exception {

		PortletResourcePermission portletResourcePermission =
			_getPortletResourcePermission(objectDefinition);

		return portletResourcePermission.contains(
			_permissionCheckerFactory.create(user),
			getGroupId(objectDefinition, scopeKey), actionId);
	}

	private JSONObject _toJSONObject(
			ObjectDefinition objectDefinition, ObjectEntry objectEntry)
		throws Exception {

		Map<String, Object> map = new HashMap<>();

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId());

		Map<String, Object> properties = objectEntry.getProperties();

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			ObjectField objectField = _getObjectFieldByName(
				entry.getKey(), objectFields);

			if (objectField == null) {
				continue;
			}

			Object value = entry.getValue();

			if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

				Map<String, String> valueMap = (HashMap<String, String>)value;

				ListTypeEntry listTypeEntry =
					_listTypeEntryLocalService.getListTypeEntry(
						objectField.getListTypeDefinitionId(),
						valueMap.get("key"));

				value = listTypeEntry.getExternalReferenceCode();
			}

			map.put(
				objectField.getExternalReferenceCode(),
				Objects.equals(value, StringPool.BLANK) ? null : value);

			if (StringUtil.endsWith(
					objectDefinition.getExternalReferenceCode(),
					_CUSTOM_OBJECT_SUFFIX) &&
				Objects.equals(
					objectField.getObjectFieldId(),
					objectDefinition.getTitleObjectFieldId())) {

				map.put("Name", value);
			}
		}

		return _jsonFactory.createJSONObject(_jsonFactory.looseSerialize(map));
	}

	private List<ObjectEntry> _toObjectEntries(
			long companyId, DTOConverterContext dtoConverterContext,
			JSONArray jsonArray, ObjectDefinition objectDefinition)
		throws Exception {

		DateFormat dateFormat = _getDateFormat();

		return JSONUtil.toList(
			jsonArray,
			jsonObject -> _toObjectEntry(
				companyId, dateFormat, dtoConverterContext, jsonObject,
				objectDefinition));
	}

	private ObjectEntry _toObjectEntry(
			long companyId, DateFormat dateFormat,
			DTOConverterContext dtoConverterContext, JSONObject jsonObject,
			ObjectDefinition objectDefinition)
		throws Exception {

		ObjectEntry objectEntry = new ObjectEntry() {
			{
				actions = HashMapBuilder.put(
					"delete",
					() -> {
						if (!_hasPortletResourcePermission(
								objectDefinition, scopeKey,
								dtoConverterContext.getUser(),
								ActionKeys.DELETE)) {

							return null;
						}

						return Collections.<String, String>emptyMap();
					}
				).build();
				creator = CreatorUtil.toCreator(
					_portal, null,
					_userLocalService.fetchUserByExternalReferenceCode(
						jsonObject.getString("OwnerId"), companyId));
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

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId());

		Iterator<String> iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			String key = iterator.next();

			ObjectField objectField = _getObjectFieldByExternalReferenceCode(
				key, objectFields);

			if (objectField == null) {
				continue;
			}

			Map<String, Object> properties = objectEntry.getProperties();

			if (jsonObject.isNull(key)) {
				properties.put(objectField.getName(), null);

				continue;
			}

			Object value = jsonObject.get(key);

			if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_INTEGER) ||
				Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER)) {

				if (value instanceof BigDecimal) {
					BigDecimal bigDecimalValue = (BigDecimal)value;

					value = bigDecimalValue.toBigInteger();
				}
			}
			else if (Objects.equals(
						objectField.getBusinessType(),
						ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

				ListTypeEntry listTypeEntry =
					_listTypeEntryLocalService.
						fetchListTypeEntryByExternalReferenceCode(
							(String)value, objectDefinition.getCompanyId(),
							objectField.getListTypeDefinitionId());

				if (listTypeEntry == null) {
					continue;
				}

				value = new ListEntry() {
					{
						key = listTypeEntry.getKey();
						name = listTypeEntry.getName(
							dtoConverterContext.getLocale());
						name_i18n = LocalizedMapUtil.getI18nMap(
							dtoConverterContext.isAcceptAllLanguages(),
							listTypeEntry.getNameMap());
					}
				};
			}

			properties.put(objectField.getName(), value);
		}

		return objectEntry;
	}

	private static final String _CUSTOM_OBJECT_SUFFIX = "__c";

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	private final Map<String, String> _defaultObjectFieldNames =
		HashMapBuilder.put(
			"createDate", "CreatedDate"
		).put(
			"creator", "OwnerId"
		).put(
			"externalReferenceCode", "Id"
		).put(
			"id", "Id"
		).put(
			"modifiedDate", "LastModifiedDate"
		).put(
			"userName", "OwnerId"
		).build();

	@Reference
	private InlineSQLHelper _inlineSQLHelper;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private Portal _portal;

	@Reference
	private SalesforceHttp _salesforceHttp;

	@Reference
	private UserLocalService _userLocalService;

}