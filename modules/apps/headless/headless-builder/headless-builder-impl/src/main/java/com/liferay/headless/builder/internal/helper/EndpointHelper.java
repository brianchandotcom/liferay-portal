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

package com.liferay.headless.builder.internal.helper;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

	public Page<Map<String, Object>> getResponseEntityMapsPage(
			long companyId, APIApplication.Endpoint endpoint,
			Pagination pagination)
		throws Exception {

		APIApplication.Schema responseSchema = endpoint.getResponseSchema();

		Set<String> relationshipsNames = new HashSet<>();

		for (APIApplication.Property property :
				responseSchema.getProperties()) {

			relationshipsNames.addAll(property.getObjectRelationshipNames());
		}

		Page<ObjectEntry> objectEntriesPage =
			_objectEntryHelper.getObjectEntriesPage(
				companyId, _getODataFilterString(endpoint),
				ListUtil.fromCollection(relationshipsNames), pagination,
				responseSchema.getMainObjectDefinitionExternalReferenceCode());

		List<Map<String, Object>> responseEntityMaps = new ArrayList<>();

		for (ObjectEntry objectEntry : objectEntriesPage.getItems()) {
			Map<String, Object> objectEntryProperties =
				_getObjectEntryProperties(objectEntry);

			Map<String, Object> responseEntityMap = new HashMap<>();

			for (APIApplication.Property property :
					responseSchema.getProperties()) {

				List<String> objectRelationshipNames =
					property.getObjectRelationshipNames();

				if (objectRelationshipNames.isEmpty()) {
					responseEntityMap.put(
						property.getName(),
						objectEntryProperties.get(
							property.getSourceFieldName()));

					continue;
				}

				responseEntityMap.put(
					property.getName(),
					_getRelatedObjectValue(
						objectEntry, property, objectRelationshipNames));
			}

			responseEntityMaps.add(responseEntityMap);
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

	private String _getODataFilterString(APIApplication.Endpoint endpoint) {
		APIApplication.Filter filter = endpoint.getFilter();

		if (filter == null) {
			return null;
		}

		return filter.getODataFilterString();
	}

	private Object _getRelatedObjectValue(
		ObjectEntry objectEntry, APIApplication.Property property,
		List<String> relationshipsNames) {

		if (relationshipsNames.isEmpty()) {
			Map<String, Object> objectEntryProperties =
				objectEntry.getProperties();

			return objectEntryProperties.get(property.getSourceFieldName());
		}

		Map<String, Object> properties = objectEntry.getProperties();

		ObjectEntry[] relatedObjectEntries = (ObjectEntry[])properties.get(
			relationshipsNames.remove(0));

		List<Object> values = new ArrayList<>();

		for (ObjectEntry relatedObjectEntry : relatedObjectEntries) {
			values.add(
				_getRelatedObjectValue(
					relatedObjectEntry, property,
					new ArrayList<>(relationshipsNames)));
		}

		List<Object> flattenValues = new ArrayList<>();

		for (Object value : values) {
			if (value instanceof Collection<?>) {
				flattenValues.addAll((Collection<?>)value);

				continue;
			}

			flattenValues.add(value);
		}

		return flattenValues;
	}

	@Reference
	private ObjectEntryHelper _objectEntryHelper;

}