/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.storage.salesforce.internal.rest.manager.v1_0;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.list.type.entry.util.ListTypeEntryUtil;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.dto.v1_0.Status;
import com.liferay.object.rest.dto.v1_0.util.CreatorUtil;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.rest.manager.v1_0.BaseObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.storage.salesforce.internal.http.SalesforceHttp;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.InlineSQLHelper;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ListUtil;
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
import java.util.List;
import java.util.Locale;
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

		checkPortletResourcePermission(
			ObjectActionKeys.ADD_OBJECT_ENTRY, objectDefinition, scopeKey,
			dtoConverterContext.getUser());

		JSONObject responseJSONObject = _salesforceHttp.post(
			objectDefinition.getCompanyId(),
			getGroupId(objectDefinition, scopeKey),
			"sobjects/" + objectDefinition.getExternalReferenceCode(),
			_toJSONObject(dtoConverterContext, objectDefinition, objectEntry));

		return getObjectEntry(
			objectDefinition.getCompanyId(), dtoConverterContext,
			responseJSONObject.getString("id"), objectDefinition, scopeKey);
	}

	@Override
	public void deleteObjectEntry(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ObjectDefinition objectDefinition,
			String scopeKey)
		throws Exception {

		checkPortletResourcePermission(
			ActionKeys.DELETE, objectDefinition, scopeKey,
			dtoConverterContext.getUser());

		_salesforceHttp.delete(
			companyId, getGroupId(objectDefinition, scopeKey),
			StringBundler.concat(
				"sobjects/", objectDefinition.getExternalReferenceCode(), "/",
				externalReferenceCode));
	}

	@Override
	public Page<ObjectEntry> getObjectEntries(
			long companyId, ObjectDefinition objectDefinition, String scopeKey,
			Aggregation aggregation, DTOConverterContext dtoConverterContext,
			String filterString, Pagination pagination, String search,
			Sort[] sorts)
		throws Exception {

		checkPortletResourcePermission(
			ActionKeys.VIEW, objectDefinition, scopeKey,
			dtoConverterContext.getUser());

		return _getObjectEntries(
			companyId, objectDefinition, scopeKey, dtoConverterContext,
			pagination, filterString, search, sorts);
	}

	@Override
	public ObjectEntry getObjectEntry(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ObjectDefinition objectDefinition,
			String scopeKey)
		throws Exception {

		checkPortletResourcePermission(
			ActionKeys.VIEW, objectDefinition, scopeKey,
			dtoConverterContext.getUser());

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
	public String getStorageLabel(Locale locale) {
		return language.get(
			locale, ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE);
	}

	@Override
	public String getStorageType() {
		return ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE;
	}

	@Override
	public ObjectEntry updateObjectEntry(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ObjectDefinition objectDefinition,
			ObjectEntry objectEntry, String scopeKey)
		throws Exception {

		checkPortletResourcePermission(
			ActionKeys.UPDATE, objectDefinition, scopeKey,
			dtoConverterContext.getUser());

		_salesforceHttp.patch(
			companyId, getGroupId(objectDefinition, scopeKey),
			StringBundler.concat(
				"sobjects/", objectDefinition.getExternalReferenceCode(), "/",
				externalReferenceCode),
			_toJSONObject(dtoConverterContext, objectDefinition, objectEntry));

		return getObjectEntry(
			companyId, dtoConverterContext, externalReferenceCode,
			objectDefinition, scopeKey);
	}

	private String _getAccountRestrictionSOSQLString(
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
			objectField.getExternalReferenceCode(), " IN ('",
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
				"', '"),
			"')");
	}

	private DateFormat _getDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	}

	private ListEntry _getListEntry(
		DTOConverterContext dtoConverterContext, String externalReferenceCode,
		ObjectDefinition objectDefinition, ObjectField objectField) {

		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.
				fetchListTypeEntryByExternalReferenceCode(
					externalReferenceCode, objectDefinition.getCompanyId(),
					objectField.getListTypeDefinitionId());

		if (listTypeEntry == null) {
			return null;
		}

		return new ListEntry() {
			{
				key = listTypeEntry.getKey();
				name = listTypeEntry.getName(dtoConverterContext.getLocale());
				name_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					listTypeEntry.getNameMap());
			}
		};
	}

	private String _getLocation(
		ObjectDefinition objectDefinition, Pagination pagination,
		String predicateString, String search, Sort[] sorts) {

		if (Validator.isNotNull(search)) {
			return HttpComponentsUtil.addParameter(
				"search", "q",
				StringBundler.concat(
					"FIND {`", search, "`} IN ALL FIELDS RETURNING ",
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
			String filterString, String search, Sort[] sorts)
		throws Exception {

		JSONObject responseJSONObject = _salesforceHttp.get(
			companyId, getGroupId(objectDefinition, scopeKey),
			_getLocation(
				objectDefinition, pagination,
				_getSOSQLString(
					companyId, dtoConverterContext, objectDefinition,
					filterString, scopeKey),
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
				_getSOSQLString(
					companyId, dtoConverterContext, objectDefinition,
					filterString, scopeKey),
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

	private String _getSOSQLString(
			long companyId, DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, String filterString,
			String scopeKey)
		throws Exception {

		String accountRestrictionSOSQLString =
			_getAccountRestrictionSOSQLString(
				companyId, dtoConverterContext, objectDefinition, scopeKey);

		String filterSOSQLString = _filterFactory.create(
			filterString, objectDefinition);

		String sosqlString = StringPool.BLANK;

		if (Validator.isNull(accountRestrictionSOSQLString) &&
			Validator.isNotNull(filterSOSQLString)) {

			sosqlString = " WHERE " + filterSOSQLString;
		}
		else if (Validator.isNotNull(accountRestrictionSOSQLString) &&
				 Validator.isNull(filterSOSQLString)) {

			sosqlString = " WHERE " + accountRestrictionSOSQLString;
		}
		else if (Validator.isNotNull(accountRestrictionSOSQLString) &&
				 Validator.isNotNull(filterSOSQLString)) {

			sosqlString = StringBundler.concat(
				" WHERE ", filterSOSQLString, " AND ",
				accountRestrictionSOSQLString);
		}

		return sosqlString;
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

	private JSONObject _toJSONObject(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, ObjectEntry objectEntry)
		throws Exception {

		Map<String, Object> map = new HashMap<>();

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId());

		Map<String, Object> properties = objectEntry.getProperties();

		for (String key : properties.keySet()) {
			ObjectField objectField = _getObjectFieldByName(key, objectFields);

			if (objectField == null) {
				continue;
			}

			ObjectFieldBusinessType objectFieldBusinessType =
				_objectFieldBusinessTypeRegistry.getObjectFieldBusinessType(
					objectField.getBusinessType());

			Object value = objectFieldBusinessType.getValue(
				objectField, dtoConverterContext.getUserId(), properties);

			if (objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST)) {

				StringBundler sb = new StringBundler();

				List<String> listTypeEntryKeys = null;

				if (value instanceof List) {
					listTypeEntryKeys = (List<String>)value;
				}
				else {
					listTypeEntryKeys = ListUtil.fromString(
						GetterUtil.getString(value),
						StringPool.COMMA_AND_SPACE);
				}

				for (String listTypeEntryKey : listTypeEntryKeys) {
					String listTypeEntryExternalReferenceCode =
						ListTypeEntryUtil.getListTypeEntryExternalReferenceCode(
							objectField.getListTypeDefinitionId(),
							listTypeEntryKey);

					if (Validator.isNull(listTypeEntryExternalReferenceCode)) {
						continue;
					}

					sb.append(listTypeEntryExternalReferenceCode);
					sb.append(StringPool.SEMICOLON);
				}

				if (sb.index() > 1) {
					sb.setIndex(sb.index() - 1);
				}

				value = sb.toString();
			}
			else if (objectField.compareBusinessType(
						ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

				String listTypeEntryKey = GetterUtil.getString(value);

				if (value instanceof Map) {
					Map<String, String> valueMap =
						(HashMap<String, String>)value;

					listTypeEntryKey = valueMap.get("key");
				}

				value = ListTypeEntryUtil.getListTypeEntryExternalReferenceCode(
					objectField.getListTypeDefinitionId(), listTypeEntryKey);
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

		return new ObjectEntry() {
			{
				actions = HashMapBuilder.put(
					"delete",
					addDeleteAction(
						objectDefinition, scopeKey,
						dtoConverterContext.getUser())
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
				properties = _toProperties(
					dtoConverterContext, jsonObject, objectDefinition,
					_objectFieldLocalService.getObjectFields(
						objectDefinition.getObjectDefinitionId()));
				status = new Status() {
					{
						code = 0;
						label = "approved";
						label_i18n = "Approved";
					}
				};
			}
		};
	}

	private Map<String, Object> _toProperties(
			DTOConverterContext dtoConverterContext, JSONObject jsonObject,
			ObjectDefinition objectDefinition, List<ObjectField> objectFields)
		throws Exception {

		Map<String, Object> properties = new HashMap<>();

		for (String key : jsonObject.keySet()) {
			ObjectField objectField = _getObjectFieldByExternalReferenceCode(
				key, objectFields);

			if (objectField == null) {
				continue;
			}

			if (jsonObject.isNull(key)) {
				properties.put(objectField.getName(), null);

				continue;
			}

			Object value = jsonObject.get(key);

			if (objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_DATE_TIME)) {

				String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS";

				if (StringUtil.equals(
						ObjectFieldSettingUtil.getValue(
							ObjectFieldSettingConstants.NAME_TIME_STORAGE,
							objectField),
						ObjectFieldSettingConstants.VALUE_CONVERT_TO_UTC)) {

					pattern += "Z";
				}

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					pattern);

				value = simpleDateFormat.format(
					simpleDateFormat.parse(GetterUtil.getString(value)));
			}
			else if (objectField.compareBusinessType(
						ObjectFieldConstants.BUSINESS_TYPE_INTEGER) ||
					 objectField.compareBusinessType(
						 ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER)) {

				if (value instanceof BigDecimal) {
					BigDecimal bigDecimalValue = (BigDecimal)value;

					value = bigDecimalValue.toBigInteger();
				}
			}
			else if (objectField.compareBusinessType(
						ObjectFieldConstants.
							BUSINESS_TYPE_MULTISELECT_PICKLIST)) {

				value = TransformUtil.transformToList(
					StringUtil.split(
						GetterUtil.getString(value), StringPool.SEMICOLON),
					listTypeEntryExternalReferenceCode -> _getListEntry(
						dtoConverterContext, listTypeEntryExternalReferenceCode,
						objectDefinition, objectField));
			}
			else if (objectField.compareBusinessType(
						ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

				value = _getListEntry(
					dtoConverterContext, GetterUtil.getString(value),
					objectDefinition, objectField);
			}

			properties.put(objectField.getName(), value);
		}

		return properties;
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

	@Reference(
		target = "(filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE + ")"
	)
	private FilterFactory<String> _filterFactory;

	@Reference
	private InlineSQLHelper _inlineSQLHelper;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectFieldBusinessTypeRegistry _objectFieldBusinessTypeRegistry;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SalesforceHttp _salesforceHttp;

	@Reference
	private UserLocalService _userLocalService;

}