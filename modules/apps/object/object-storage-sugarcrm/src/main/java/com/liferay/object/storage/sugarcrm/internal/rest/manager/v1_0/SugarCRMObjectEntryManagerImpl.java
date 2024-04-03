/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.storage.sugarcrm.internal.rest.manager.v1_0;

import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.rest.manager.exception.ObjectEntryManagerHttpException;
import com.liferay.object.rest.manager.http.BaseObjectEntryManagerHttp;
import com.liferay.object.rest.manager.http.ObjectEntryManagerHttp;
import com.liferay.object.rest.manager.v1_0.BaseObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.storage.sugarcrm.configuration.SugarCRMConfiguration;
import com.liferay.object.storage.sugarcrm.internal.web.cache.SugarCRMAccessTokenWebCacheItem;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collections;
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

		JSONObject responseJSONObject = _objectEntryManagerHttp.post(
			objectDefinition.getCompanyId(),
			getGroupId(objectDefinition, scopeKey),
			_getObjectLocation(objectDefinition),
			toJSONObject(
				dtoConverterContext, objectDefinition, objectEntry,
				_getUnsafeTriConsumer(objectDefinition)));

		return toObjectEntry(
			objectDefinition.getCompanyId(), getDateFormat(),
			_defaultObjectFieldNames, dtoConverterContext, responseJSONObject,
			objectDefinition);
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

		_objectEntryManagerHttp.delete(
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

		return toObjectEntry(
			companyId, getDateFormat(), _defaultObjectFieldNames,
			dtoConverterContext,
			_objectEntryManagerHttp.get(
				companyId, getGroupId(objectDefinition, scopeKey),
				StringBundler.concat(
					_getObjectLocation(objectDefinition),
					StringPool.FORWARD_SLASH, externalReferenceCode)),
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

		if (Validator.isNull(externalReferenceCode)) {
			return null;
		}

		JSONObject responseJSONObject = _objectEntryManagerHttp.put(
			objectDefinition.getCompanyId(),
			getGroupId(objectDefinition, scopeKey),
			StringBundler.concat(
				_getObjectLocation(objectDefinition), StringPool.FORWARD_SLASH,
				externalReferenceCode),
			toJSONObject(
				dtoConverterContext, objectDefinition, objectEntry,
				_getUnsafeTriConsumer(objectDefinition)));

		return toObjectEntry(
			objectDefinition.getCompanyId(), getDateFormat(),
			_defaultObjectFieldNames, dtoConverterContext, responseJSONObject,
			objectDefinition);
	}

	private void _appendFilter(
		StringBuilder sb, ObjectDefinition objectDefinition,
		String filterString) {

		filterString = StringUtil.trim(filterString);

		if (!filterString.isEmpty()) {
			sb.append(StringPool.AMPERSAND);
			sb.append("filter");
			sb.append(StringPool.EQUAL);
			sb.append(StringPool.OPEN_BRACKET);

			String filterSugarQueryString = _filterFactory.create(
				filterString, objectDefinition);

			sb.append(URLCodec.encodeURL(filterSugarQueryString));

			sb.append(StringPool.CLOSE_BRACKET);
		}
	}

	private void _appendPagination(StringBuilder sb, Pagination pagination) {
		sb.append("max_num=");
		sb.append(pagination.getPageSize());
		sb.append("&offset=");
		sb.append((pagination.getPage() - 1) * pagination.getPageSize());
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

		sb.append("&");
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

		_appendFilter(sb, objectDefinition, filterString);

		/* TODO: Add implementation for search */

		return sb.toString();
	}

	private Page<ObjectEntry> _getObjectEntries(
			long companyId, ObjectDefinition objectDefinition, String scopeKey,
			DTOConverterContext dtoConverterContext, String filterString,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		JSONObject responseJSONObject = _objectEntryManagerHttp.get(
			companyId, getGroupId(objectDefinition, scopeKey),
			_generatePreparedLocation(
				objectDefinition, filterString, pagination, sorts, false));

		if ((responseJSONObject == null) ||
			(responseJSONObject.length() == 0)) {

			return Page.of(Collections.emptyList());
		}

		return Page.of(
			toObjectEntries(
				companyId, _defaultObjectFieldNames, dtoConverterContext,
				responseJSONObject.getJSONArray("records"), objectDefinition),
			pagination,
			_getTotalCount(
				companyId, objectDefinition, scopeKey, filterString, pagination,
				sorts));
	}

	private String _getObjectLocation(ObjectDefinition objectDefinition) {
		return objectDefinition.getExternalReferenceCode();
	}

	private int _getTotalCount(
			long companyId, ObjectDefinition objectDefinition, String scopeKey,
			String filterString, Pagination pagination, Sort[] sorts)
		throws Exception {

		JSONObject responseJSONObject = _objectEntryManagerHttp.get(
			companyId, getGroupId(objectDefinition, scopeKey),
			_generatePreparedLocation(
				objectDefinition, filterString, pagination, sorts, true));

		return responseJSONObject.getInt("record_count", 0);
	}

	private UnsafeTriConsumer
		<Map<String, Object>, Object, ObjectField, Exception>
			_getUnsafeTriConsumer(ObjectDefinition objectDefinition) {

		return (map, value, objectField) -> {
			if (Objects.equals(
					objectField.getObjectFieldId(),
					objectDefinition.getTitleObjectFieldId())) {

				map.put("Name", value);
			}
		};
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	private final Map<String, String> _defaultObjectFieldNames =
		HashMapBuilder.put(
			"createDate", "date_entered"
		).put(
			"creator", "created_by"
		).put(
			"externalReferenceCode", "id"
		).put(
			"modifiedDate", "date_modified"
		).build();

	@Reference(
		target = "(filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_SUGARCRM + ")"
	)
	private FilterFactory<String> _filterFactory;

	private final ObjectEntryManagerHttp _objectEntryManagerHttp =
		new BaseObjectEntryManagerHttp() {

			@Override
			public JSONObject getAccessToken(long companyId, long groupId) {
				JSONObject jSONObject = SugarCRMAccessTokenWebCacheItem.get(
					_getSugarCRMConfiguration(companyId, groupId));

				if (jSONObject == null) {
					throw new ObjectEntryManagerHttpException(
						"Unable to authenticate with SugarCRM");
				}

				return jSONObject;
			}

			@Override
			public String getBaseURL(long companyId, long groupId) {
				SugarCRMConfiguration sugarCRMConfiguration =
					_getSugarCRMConfiguration(companyId, groupId);

				return sugarCRMConfiguration.baseURL();
			}

			private SugarCRMConfiguration _getSugarCRMConfiguration(
				long companyId, long groupId) {

				try {
					if (groupId == 0) {
						return _configurationProvider.getCompanyConfiguration(
							SugarCRMConfiguration.class, companyId);
					}

					return _configurationProvider.getGroupConfiguration(
						SugarCRMConfiguration.class, groupId);
				}
				catch (ConfigurationException configurationException) {
					return ReflectionUtil.throwException(
						configurationException);
				}
			}

		};

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}