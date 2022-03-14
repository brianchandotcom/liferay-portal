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

package com.liferay.object.rest.internal.graphql.dto.v1_0;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.dto.v1_0.Status;
import com.liferay.object.rest.internal.odata.entity.v1_0.ObjectEntryEntityModel;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.graphql.dto.GraphQLDTOContributor;
import com.liferay.portal.vulcan.graphql.dto.GraphQLDTOProperty;
import com.liferay.portal.vulcan.graphql.dto.v1_0.Creator;
import com.liferay.portal.vulcan.list.type.ListEntry;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.math.BigDecimal;

import java.sql.Blob;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Javier de Arcos
 */
public class ObjectDefinitionGraphQLDTOContributor
	implements GraphQLDTOContributor<Map<String, Object>, Map<String, Object>> {

	public static ObjectDefinitionGraphQLDTOContributor of(
		ObjectDefinition objectDefinition,
		ObjectEntryManager objectEntryManager, List<ObjectField> objectFields,
		ObjectScopeProvider objectScopeProvider) {

		List<GraphQLDTOProperty> graphQLDTOProperties = new ArrayList<>();

		graphQLDTOProperties.add(
			GraphQLDTOProperty.of(
				objectDefinition.getPKObjectFieldName(), true, Long.class));
		graphQLDTOProperties.add(
			GraphQLDTOProperty.of("creator", true, Creator.class));
		graphQLDTOProperties.add(
			GraphQLDTOProperty.of("dateCreated", true, Date.class));
		graphQLDTOProperties.add(
			GraphQLDTOProperty.of("dateModified", true, Date.class));
		graphQLDTOProperties.add(
			GraphQLDTOProperty.of("externalReferenceCode", String.class));
		graphQLDTOProperties.add(
			GraphQLDTOProperty.of("status", true, String.class));

		List<GraphQLDTOProperty> relationshipGraphQLDTOProperties =
			new ArrayList<>();

		for (ObjectField objectField : objectFields) {
			if (objectField.getListTypeDefinitionId() != 0) {
				graphQLDTOProperties.add(
					GraphQLDTOProperty.of(
						objectField.getName(), ListEntry.class));
			}
			else if (Objects.equals(
						objectField.getRelationshipType(), "oneToMany")) {

				String objectFieldName = objectField.getName();

				String relationshipIdName = objectFieldName.substring(
					objectFieldName.lastIndexOf(StringPool.UNDERLINE) + 1);

				graphQLDTOProperties.add(
					GraphQLDTOProperty.of(relationshipIdName, Long.class));

				String relationshipName = StringUtil.replaceLast(
					relationshipIdName, "Id", "");

				relationshipGraphQLDTOProperties.add(
					GraphQLDTOProperty.of(relationshipName, Map.class));
			}
			else {
				graphQLDTOProperties.add(
					GraphQLDTOProperty.of(
						objectField.getName(),
						_typedClasses.getOrDefault(
							objectField.getDBType(), Object.class)));
			}
		}

		return new ObjectDefinitionGraphQLDTOContributor(
			objectDefinition.getCompanyId(),
			new ObjectEntryEntityModel(objectFields), graphQLDTOProperties,
			StringUtil.removeSubstring(
				objectDefinition.getPKObjectFieldName(), "c_"),
			objectDefinition, objectEntryManager, objectScopeProvider,
			relationshipGraphQLDTOProperties, objectDefinition.getShortName(),
			objectDefinition.getName());
	}

	@Override
	public Map<String, Object> createDTO(
			Map<String, Object> dto, DTOConverterContext dtoConverterContext)
		throws Exception {

		return _toMap(
			_objectEntryManager.addObjectEntry(
				dtoConverterContext, _objectDefinition, _toObjectEntry(dto),
				(String)dtoConverterContext.getAttribute("scopeKey")));
	}

	@Override
	public boolean deleteDTO(long id) throws Exception {
		_objectEntryManager.deleteObjectEntry(_objectDefinition, id);

		return true;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public Map<String, Object> getDTO(
			DTOConverterContext dtoConverterContext, long id)
		throws Exception {

		return _toMap(
			_objectEntryManager.getObjectEntry(
				dtoConverterContext, _objectDefinition, id));
	}

	@Override
	public Page<Map<String, Object>> getDTOs(
			Aggregation aggregation, DTOConverterContext dtoConverterContext,
			Filter filter, Pagination pagination, String search, Sort[] sorts)
		throws Exception {

		Page<ObjectEntry> page = _objectEntryManager.getObjectEntries(
			(Long)dtoConverterContext.getAttribute("companyId"),
			_objectDefinition,
			(String)dtoConverterContext.getAttribute("scopeKey"), aggregation,
			dtoConverterContext, filter, pagination, search, sorts);

		Collection<ObjectEntry> items = page.getItems();

		Stream<ObjectEntry> stream = items.stream();

		return Page.of(
			page.getActions(), page.getFacets(),
			stream.map(
				this::_toMap
			).collect(
				Collectors.toList()
			),
			pagination, page.getTotalCount());
	}

	@Override
	public EntityModel getEntityModel() {
		return _entityModel;
	}

	@Override
	public List<GraphQLDTOProperty> getGraphQLDTOProperties() {
		return _graphQLDTOProperties;
	}

	@Override
	public String getIdName() {
		return _idName;
	}

	@Override
	public List<GraphQLDTOProperty> getRelationshipGraphQLDTOProperties() {
		return _relationshipGraphQLDTOProperties;
	}

	@Override
	public <T> T getRelationshipValue(
			DTOConverterContext dtoConverterContext, long id,
			Class<T> relationshipClass, String relationshipName)
		throws Exception {

		if (!Objects.equals(relationshipClass, Map.class)) {
			return null;
		}

		ObjectEntry objectEntry = _objectEntryManager.getObjectEntry(
			dtoConverterContext, _objectDefinition, id);

		Map<String, Object> properties = objectEntry.getProperties();

		String relationshipIdName = relationshipName + "Id";

		Object relationshipId = properties.get(relationshipIdName);

		if (!(relationshipId instanceof Long)) {
			return null;
		}

		return (T)_toMap(
			_objectEntryManager.fetchObjectEntry(
				dtoConverterContext, null, (long)relationshipId),
			relationshipIdName);
	}

	@Override
	public String getResourceName() {
		return _resourceName;
	}

	@Override
	public String getTypeName() {
		return _typeName;
	}

	@Override
	public boolean hasScope() {
		return _objectScopeProvider.isGroupAware();
	}

	@Override
	public Map<String, Object> updateDTO(
			Map<String, Object> dto, DTOConverterContext dtoConverterContext,
			long id)
		throws Exception {

		return _toMap(
			_objectEntryManager.updateObjectEntry(
				dtoConverterContext, _objectDefinition, id,
				_toObjectEntry(dto)));
	}

	private ObjectDefinitionGraphQLDTOContributor(
		long companyId, EntityModel entityModel,
		List<GraphQLDTOProperty> graphQLDTOProperties, String idName,
		ObjectDefinition objectDefinition,
		ObjectEntryManager objectEntryManager,
		ObjectScopeProvider objectScopeProvider,
		List<GraphQLDTOProperty> relationshipGraphQLDTOProperties,
		String resourceName, String typeName) {

		_companyId = companyId;
		_entityModel = entityModel;
		_graphQLDTOProperties = graphQLDTOProperties;
		_idName = idName;
		_objectDefinition = objectDefinition;
		_objectEntryManager = objectEntryManager;
		_objectScopeProvider = objectScopeProvider;
		_relationshipGraphQLDTOProperties = relationshipGraphQLDTOProperties;
		_resourceName = resourceName;
		_typeName = typeName;
	}

	private Map<String, Object> _toMap(ObjectEntry objectEntry) {
		return _toMap(objectEntry, getIdName());
	}

	private Map<String, Object> _toMap(
		ObjectEntry objectEntry, String objectEntryIdName) {

		if (objectEntry == null) {
			return null;
		}

		Map<String, Object> properties = objectEntry.getProperties();

		properties.put(objectEntryIdName, objectEntry.getId());

		properties.put("creator", objectEntry.getCreator());
		properties.put("dateCreated", objectEntry.getDateCreated());
		properties.put("dateModified", objectEntry.getDateModified());
		properties.put(
			"externalReferenceCode", objectEntry.getExternalReferenceCode());

		Status status = objectEntry.getStatus();

		properties.put("status", status.getLabel());

		return properties;
	}

	private ObjectEntry _toObjectEntry(Map<String, Object> map) {
		ObjectEntry objectEntry = new ObjectEntry();

		if (map == null) {
			return objectEntry;
		}

		objectEntry.setId((Long)map.get(getIdName()));

		Map<String, Object> properties = objectEntry.getProperties();

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			properties.put(entry.getKey(), entry.getValue());
		}

		return objectEntry;
	}

	private static final Map<String, Class<?>> _typedClasses =
		HashMapBuilder.<String, Class<?>>put(
			"BigDecimal", BigDecimal.class
		).put(
			"Blob", Blob.class
		).put(
			"Boolean", Boolean.class
		).put(
			"Date", Date.class
		).put(
			"Double", Double.class
		).put(
			"Integer", Integer.class
		).put(
			"Long", Long.class
		).put(
			"String", String.class
		).build();

	private final long _companyId;
	private final EntityModel _entityModel;
	private final List<GraphQLDTOProperty> _graphQLDTOProperties;
	private final String _idName;
	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryManager _objectEntryManager;
	private final ObjectScopeProvider _objectScopeProvider;
	private final List<GraphQLDTOProperty> _relationshipGraphQLDTOProperties;
	private final String _resourceName;
	private final String _typeName;

}