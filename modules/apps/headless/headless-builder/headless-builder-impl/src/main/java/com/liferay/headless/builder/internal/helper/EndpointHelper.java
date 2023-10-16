/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.helper;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.constants.HeadlessBuilderConstants;
import com.liferay.object.exception.NoSuchObjectEntryException;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 * @author Carlos Correa
 * @author Alejandro Tardín
 */
@Component(service = EndpointHelper.class)
public class EndpointHelper {

	public Map<String, Object> getResponseEntityMap(
			long companyId, String pathParameter, String pathParameterValue,
			APIApplication.Schema schema, String scopeKey)
		throws Exception {

		ObjectEntry objectEntry = null;

		Set<String> relationshipsNames = new HashSet<>();

		for (APIApplication.Property property : schema.getProperties()) {
			relationshipsNames.addAll(property.getObjectRelationshipNames());
		}

		if (Objects.equals(
				pathParameter, HeadlessBuilderConstants.PATH_PARAMETER_ERC)) {

			objectEntry = _objectEntryHelper.getObjectEntry(
				companyId, ListUtil.fromCollection(relationshipsNames),
				schema.getMainObjectDefinitionExternalReferenceCode(),
				pathParameterValue, scopeKey);
		}
		else if (Objects.equals(
					pathParameter,
					HeadlessBuilderConstants.PATH_PARAMETER_ID)) {

			objectEntry = _objectEntryHelper.getObjectEntry(
				companyId, ListUtil.fromCollection(relationshipsNames),
				GetterUtil.getLong(pathParameterValue),
				schema.getMainObjectDefinitionExternalReferenceCode());
		}
		else {
			String filterString = StringBundler.concat(
				pathParameter, " eq '", pathParameterValue, "'");

			List<ObjectEntry> objectEntries =
				_objectEntryHelper.getObjectEntries(
					companyId, filterString,
					ListUtil.fromCollection(relationshipsNames),
					schema.getMainObjectDefinitionExternalReferenceCode(),
					scopeKey);

			if (objectEntries.isEmpty()) {
				throw new NoSuchObjectEntryException(
					"No object entry exists with the filter " + filterString);
			}

			objectEntry = objectEntries.get(0);
		}

		return _getResponseEntityMap(objectEntry, schema);
	}

	public Page<Map<String, Object>> getResponseEntityMapsPage(
			AcceptLanguage acceptLanguage, long companyId,
			APIApplication.Endpoint endpoint, String filterString,
			Pagination pagination, String scopeKey, String sortString)
		throws Exception {

		List<Map<String, Object>> responseEntityMaps = new ArrayList<>();

		Set<String> relationshipsNames = new HashSet<>();

		APIApplication.Schema schema = endpoint.getResponseSchema();

		for (APIApplication.Property property : schema.getProperties()) {
			relationshipsNames.addAll(property.getObjectRelationshipNames());
		}

		Page<ObjectEntry> objectEntriesPage =
			_objectEntryHelper.getObjectEntriesPage(
				companyId,
				_filterExpressionHelper.getExpression(
					companyId, endpoint, filterString),
				ListUtil.fromCollection(relationshipsNames), pagination,
				schema.getMainObjectDefinitionExternalReferenceCode(), scopeKey,
				_sortsHelper.getSorts(
					acceptLanguage, companyId, endpoint, sortString));

		for (ObjectEntry objectEntry : objectEntriesPage.getItems()) {
			responseEntityMaps.add(_getResponseEntityMap(objectEntry, schema));
		}

		return Page.of(
			responseEntityMaps, pagination, objectEntriesPage.getTotalCount());
	}

	private Map<String, Object> _getObjectEntryProperties(
		ObjectEntry objectEntry) {

		return HashMapBuilder.<String, Object>putAll(
			objectEntry.getProperties()
		).put(
			"createDate", objectEntry.getDateCreated()
		).put(
			"externalReferenceCode", objectEntry.getExternalReferenceCode()
		).put(
			"modifiedDate", objectEntry.getDateModified()
		).build();
	}

	private Object _getRelatedObjectValue(
		ObjectEntry objectEntry, APIApplication.Property property,
		List<String> relationshipsNames) {

		if (relationshipsNames.isEmpty()) {
			return objectEntry.getValue(property.getSourceFieldName());
		}

		List<Object> values = new ArrayList<>();

		ObjectEntry[] relatedObjectEntries = null;

		Map<String, Object> properties = objectEntry.getProperties();

		Object relationshipNameValue = properties.get(
			relationshipsNames.remove(0));

		if (relationshipNameValue instanceof ObjectEntry[]) {
			relatedObjectEntries = (ObjectEntry[])relationshipNameValue;
		}
		else {
			relatedObjectEntries = new ObjectEntry[] {
				(ObjectEntry)relationshipNameValue
			};
		}

		for (ObjectEntry relatedObjectEntry : relatedObjectEntries) {
			Object value = _getRelatedObjectValue(
				relatedObjectEntry, property,
				new ArrayList<>(relationshipsNames));

			if (value instanceof Collection<?>) {
				values.addAll((Collection<?>)value);
			}
			else {
				values.add(value);
			}
		}

		return values;
	}

	private Map<String, Object> _getResponseEntityMap(
			ObjectEntry objectEntry, APIApplication.Schema schema)
		throws Exception {

		Map<String, Object> responseEntityMap = new HashMap<>();

		Map<String, Object> objectEntryProperties = _getObjectEntryProperties(
			objectEntry);

		for (APIApplication.Property property : schema.getProperties()) {
			List<String> objectRelationshipNames =
				property.getObjectRelationshipNames();

			if (objectRelationshipNames.isEmpty()) {
				responseEntityMap.put(
					property.getName(),
					objectEntryProperties.get(property.getSourceFieldName()));

				continue;
			}

			responseEntityMap.put(
				property.getName(),
				_getRelatedObjectValue(
					objectEntry, property, objectRelationshipNames));
		}

		return responseEntityMap;
	}

	@Reference
	private FilterExpressionHelper _filterExpressionHelper;

	@Reference
	private ObjectEntryHelper _objectEntryHelper;

	@Reference
	private SortsHelper _sortsHelper;

}