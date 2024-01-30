/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.storage.sugarcrm.internal.rest.manager.v1_0;

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.dto.v1_0.Status;
import com.liferay.object.rest.dto.v1_0.util.CreatorUtil;
import com.liferay.object.rest.manager.exception.ObjectEntryManagerHttpException;
import com.liferay.object.rest.manager.v1_0.BaseObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.storage.sugarcrm.configuration.SugarCRMConfiguration;
import com.liferay.object.storage.sugarcrm.internal.web.cache.SugarCRMAccessTokenWebCacheItem;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.math.BigDecimal;

import java.net.HttpURLConnection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Maurice Sepe
 */
@Component(
	property = "object.entry.manager.storage.type=" + ObjectDefinitionConstants.STORAGE_TYPE_SUGARCRM,
	service = ObjectEntryManager.class
)
public class SugarCRMObjectEntryManagerImpl
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

		JSONObject responseJSONObject = _sugarcrmHttp.post(
			objectDefinition.getCompanyId(),
			getGroupId(objectDefinition, scopeKey),
			_getObjectLocation(objectDefinition),
			_toJSONObject(objectDefinition, objectEntry));

		return _toObjectEntry(
			objectDefinition.getCompanyId(), _getDateFormat(),
			dtoConverterContext, responseJSONObject, objectDefinition);
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

		_sugarcrmHttp.delete(
			companyId, getGroupId(objectDefinition, scopeKey),
			objectDefinition.getExternalReferenceCode() +
				StringPool.FORWARD_SLASH + externalReferenceCode);
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
			filterString, pagination, sorts);
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

		JSONObject jsonObject = _sugarcrmHttp.get(
			companyId, getGroupId(objectDefinition, scopeKey),
			StringBundler.concat(
				_getObjectLocation(objectDefinition), StringPool.FORWARD_SLASH,
				externalReferenceCode),
			null);

		return _toObjectEntry(
			companyId, _getDateFormat(), dtoConverterContext, jsonObject,
			objectDefinition);
	}

	@Override
	public String getStorageLabel(Locale locale) {
		return language.get(
			locale, ObjectDefinitionConstants.STORAGE_TYPE_SUGARCRM);
	}

	@Override
	public String getStorageType() {
		return ObjectDefinitionConstants.STORAGE_TYPE_SUGARCRM;
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

		// TODO Auto-generated method stub

		throw new UnsupportedOperationException(
			"Unimplemented method 'updateObjectEntry'");
	}

	private void _appendFilter(StringBuilder sb, String filterString) {
		if (!filterString.trim(
			).isEmpty()) {

			sb.append("&&");
			sb.append("filter");

			if (filterString.startsWith(StringPool.EQUAL)) {
				sb.append(StringPool.EQUAL);
				sb.append(URLCodec.encodeURL(filterString.substring(1)));
			}
			else {
				sb.append(StringUtil.replace(filterString, ' ', "%20"));
			}
		}
	}

	private void _appendPagination(StringBuilder sb, Pagination pagination) {
		int offset = (pagination.getPage() - 1) * pagination.getPageSize();
		int max_num = pagination.getPageSize();

		sb.append(
			StringBundler.concat("offset=", offset, "&&max_num=", max_num));
	}

	private void _appendSorts(
		StringBuilder sb, ObjectDefinition objectDefinition, Sort[] oldSorts) {

		if ((null == oldSorts) || (oldSorts.length == 0)) {
			return;
		}

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId());

		Sort[] newSorts = new Sort[oldSorts.length];

		for (int i = 0; i < oldSorts.length; i++) {
			int index = i;

			ObjectField objectField = null;

			for (ObjectField obj : objectFields) {
				if (Objects.equals(
						oldSorts[index].getFieldName(), obj.getName())) {

					objectField = obj;

					break;
				}
			}

			if (objectField != null) {
				Sort sort = new Sort(
					objectField.getExternalReferenceCode(),
					oldSorts[i].isReverse());

				newSorts[i] = sort;
			}
		}

		sb.append("order_by=");

		for (int i = 0; i < newSorts.length; i++) {
			if (i > 0) {
				sb.append(",");
			}

			sb.append(newSorts[i].getFieldName());
			sb.append(":");

			if (newSorts[i].isReverse()) {
				sb.append("DESC");
			}
			else {
				sb.append("ASC");
			}
		}

		sb.append("&&");
	}

	private String _generatePreparedLocation(
		ObjectDefinition objectDefinition, String filterString,
		Pagination pagination, Sort[] sorts, boolean count) {

		StringBuilder sb = new StringBuilder();

		sb.append(_getObjectLocation(objectDefinition));

		if (count) {
			sb.append(StringPool.FORWARD_SLASH);
			sb.append("count");
		}

		sb.append(StringPool.QUESTION);

		_appendSorts(sb, objectDefinition, sorts);

		_appendPagination(sb, pagination);

		_appendFilter(sb, filterString);

		/* TODO: Add implementation for search */

		return sb.toString();
	}

	private DateFormat _getDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
	}

	private Page<ObjectEntry> _getObjectEntries(
			long companyId, ObjectDefinition objectDefinition, String scopeKey,
			DTOConverterContext dtoConverterContext, String filterString,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		JSONObject responseJSONObject = _sugarcrmHttp.get(
			companyId, getGroupId(objectDefinition, scopeKey),
			_generatePreparedLocation(
				objectDefinition, filterString, pagination, sorts, false),
			null);

		if ((responseJSONObject == null) ||
			(responseJSONObject.length() == 0)) {

			return Page.of(Collections.emptyList());
		}

		return Page.of(
			_toObjectEntries(
				companyId, dtoConverterContext,
				responseJSONObject.getJSONArray("records"), objectDefinition),
			pagination,
			_getTotalCount(
				companyId, objectDefinition, scopeKey, filterString, pagination,
				sorts));
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

	private String _getObjectLocation(ObjectDefinition objectDefinition) {
		return objectDefinition.getExternalReferenceCode();
	}

	private int _getTotalCount(
			long companyId, ObjectDefinition objectDefinition, String scopeKey,
			String filterString, Pagination pagination, Sort[] sorts)
		throws Exception {

		JSONObject responseJSONObject = _sugarcrmHttp.get(
			companyId, getGroupId(objectDefinition, scopeKey),
			_generatePreparedLocation(
				objectDefinition, filterString, pagination, sorts, true),
			null);

		return responseJSONObject.getInt("record_count", 0);
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

			if (Objects.equals(
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
					"delete", Collections.<String, String>emptyMap()
				).build();
				creator = CreatorUtil.toCreator(
					_portal, null,
					_userLocalService.fetchUserByExternalReferenceCode(
						jsonObject.getString("created_by"), companyId));

				dateCreated = dateFormat.parse(
					jsonObject.getString("date_entered"));
				dateModified = dateFormat.parse(
					jsonObject.getString("date_modified"));
				externalReferenceCode = jsonObject.getString("id");
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

	private static final Log _log = LogFactoryUtil.getLog(
		SugarCRMObjectEntryManagerImpl.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private Portal _portal;

	private final SugarCRMHttp _sugarcrmHttp = new SugarCRMHttp();

	@Reference
	private UserLocalService _userLocalService;

	private class SugarCRMHttp {

		public JSONObject delete(
			long companyId, long groupId, String location) {

			try {
				return _invoke(
					companyId, groupId, location, Http.Method.DELETE, null);
			}
			catch (Exception exception) {
				return ReflectionUtil.throwException(exception);
			}
		}

		public JSONObject get(
			long companyId, long groupId, String location,
			JSONObject jsonObject) {

			try {
				return _invoke(
					companyId, groupId, location, Http.Method.GET, jsonObject);
			}
			catch (Exception exception) {
				return ReflectionUtil.throwException(exception);
			}
		}

		public JSONObject patch(
			long companyId, long groupId, String location,
			JSONObject bodyJSONObject) {

			try {
				return _invoke(
					companyId, groupId, location, Http.Method.PATCH,
					bodyJSONObject);
			}
			catch (Exception exception) {
				return ReflectionUtil.throwException(exception);
			}
		}

		public JSONObject post(
			long companyId, long groupId, String location,
			JSONObject bodyJSONObject) {

			try {
				return _invoke(
					companyId, groupId, location, Http.Method.POST,
					bodyJSONObject);
			}
			catch (Exception exception) {
				return ReflectionUtil.throwException(exception);
			}
		}

		private JSONObject _getSugarCRMAccessTokenJSONObject(
			SugarCRMConfiguration sugarCRMConfiguration) {

			JSONObject jSONObject = SugarCRMAccessTokenWebCacheItem.get(
				sugarCRMConfiguration);

			if (jSONObject == null) {
				throw new ObjectEntryManagerHttpException(
					"Unable to authenticate with SugarCRM");
			}

			return jSONObject;
		}

		private SugarCRMConfiguration _getSugarCRMConfiguration(
			long companyId, long groupId) {

			System.out.println(companyId + " - " + groupId);
			System.out.println(_configurationProvider);

			try {
				if (groupId == 0) {
					return _configurationProvider.getCompanyConfiguration(
						SugarCRMConfiguration.class, companyId);
				}

				return _configurationProvider.getGroupConfiguration(
					SugarCRMConfiguration.class, groupId);
			}
			catch (ConfigurationException configurationException) {
				return ReflectionUtil.throwException(configurationException);
			}
		}

		private JSONObject _invoke(
				long companyId, long groupId, String location,
				Http.Method method, JSONObject bodyJSONObject)
			throws Exception {

			byte[] bytes = _invokeAsBytes(
				companyId, groupId, location, method, bodyJSONObject);

			if (bytes == null) {
				return _jsonFactory.createJSONObject();
			}

			return _jsonFactory.createJSONObject(new String(bytes));
		}

		private byte[] _invokeAsBytes(
				long companyId, long groupId, String location,
				Http.Method method, JSONObject bodyJSONObject)
			throws Exception {

			Http.Options options = new Http.Options();

			if (bodyJSONObject != null) {
				options.addHeader(
					HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
			}

			SugarCRMConfiguration sugarCRMConfiguration =
				_getSugarCRMConfiguration(companyId, groupId);

			JSONObject jsonObject = _getSugarCRMAccessTokenJSONObject(
				sugarCRMConfiguration);

			options.addHeader(
				"Authorization",
				"Bearer " + jsonObject.getString("access_token"));

			if (bodyJSONObject != null) {
				options.setBody(
					bodyJSONObject.toString(), ContentTypes.APPLICATION_JSON,
					StringPool.UTF8);
			}

			options.setFollowRedirects(false);
			options.setLocation(sugarCRMConfiguration.baseURL() + location);
			options.setMethod(method);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"SugarCRM connector calling URL: " + options.getLocation());
			}

			byte[] bytes = _http.URLtoByteArray(options);

			Http.Response response = options.getResponse();

			if ((response.getResponseCode() < HttpURLConnection.HTTP_OK) ||
				(response.getResponseCode() >=
					HttpURLConnection.HTTP_MULT_CHOICE)) {

				throw new ObjectEntryManagerHttpException(
					StringBundler.concat(
						"Unexpected response code ", response.getResponseCode(),
						" with response message: ", new String(bytes)));
			}

			return bytes;
		}

	}

}