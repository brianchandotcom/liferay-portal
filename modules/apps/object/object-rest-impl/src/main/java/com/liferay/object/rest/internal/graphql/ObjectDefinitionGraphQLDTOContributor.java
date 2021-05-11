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

package com.liferay.object.rest.internal.graphql;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.internal.manager.ObjectEntryManager;
import com.liferay.object.rest.internal.odata.entity.ObjectEntryEntityModel;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.graphql.dto.GraphQLDTOContributor;
import com.liferay.portal.vulcan.graphql.dto.GraphQLDTOProperty;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.math.BigDecimal;

import java.sql.Blob;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Javier de Arcos
 */
public class ObjectDefinitionGraphQLDTOContributor
	implements GraphQLDTOContributor<ObjectEntry, Map<String, Object>> {

	public static ObjectDefinitionGraphQLDTOContributor of(
		ObjectDefinition objectDefinition,
		ObjectEntryManager objectEntryManager, List<ObjectField> objectFields) {

		List<GraphQLDTOProperty> properties = new ArrayList<>();

		properties.add(
			GraphQLDTOProperty.of(
				objectDefinition.getPrimaryKeyColumnName(), Long.class));

		for (ObjectField objectField : objectFields) {
			properties.add(
				GraphQLDTOProperty.of(
					objectField.getName(),
					_mappedTypeToClass.getOrDefault(
						objectField.getType(), Object.class)));
		}

		return new ObjectDefinitionGraphQLDTOContributor(
			new ObjectEntryEntityModel(objectFields),
			objectDefinition.getName(),
			objectDefinition.getObjectDefinitionId(), objectEntryManager,
			objectDefinition.getPrimaryKeyColumnName(), properties);
	}

	@Override
	public Map<String, Object> createDTO(
			ObjectEntry objectEntry, DTOConverterContext dtoConverterContext)
		throws Exception {

		return _objectEntryToMap(
			_objectEntryManager.addObjectEntry(
				dtoConverterContext.getUserId(),
				(Long)dtoConverterContext.getAttribute("siteId"),
				_objectDefinitionId, objectEntry, dtoConverterContext));
	}

	@Override
	public boolean deleteDTO(long id) throws Exception {
		_objectEntryManager.deleteObjectEntry(id);

		return true;
	}

	@Override
	public Map<String, Object> getDTO(
			long id, DTOConverterContext dtoConverterContext)
		throws Exception {

		return _objectEntryToMap(
			_objectEntryManager.getObjectEntry(id, dtoConverterContext));
	}

	@Override
	public Page<Map<String, Object>> getDTOs(
			Aggregation aggregation, Filter filter, Pagination pagination,
			String search, Sort[] sorts,
			DTOConverterContext dtoConverterContext)
		throws Exception {

		Page<ObjectEntry> page = _objectEntryManager.getObjectEntries(
			(Long)dtoConverterContext.getAttribute("companyId"),
			_objectDefinitionId, aggregation, filter, pagination, search, sorts,
			dtoConverterContext);

		Collection<ObjectEntry> items = page.getItems();

		Stream<ObjectEntry> stream = items.stream();

		return Page.of(
			page.getActions(), page.getFacets(),
			stream.map(
				this::_objectEntryToMap
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
	public String getIdName() {
		return _primaryKeyPropertyName;
	}

	@Override
	public List<GraphQLDTOProperty> getProperties() {
		return _properties;
	}

	@Override
	public String getResourceName() {
		return _name;
	}

	@Override
	public Map<String, Object> updateDTO(
			long id, ObjectEntry objectEntry,
			DTOConverterContext dtoConverterContext)
		throws Exception {

		return _objectEntryToMap(
			_objectEntryManager.updateObjectEntry(
				dtoConverterContext.getUserId(), id, objectEntry,
				dtoConverterContext));
	}

	private ObjectDefinitionGraphQLDTOContributor(
		EntityModel entityModel, String name, long objectDefinitionId,
		ObjectEntryManager objectEntryManager, String primaryKeyPropertyName,
		List<GraphQLDTOProperty> properties) {

		_entityModel = entityModel;
		_name = name;
		_objectDefinitionId = objectDefinitionId;
		_objectEntryManager = objectEntryManager;
		_primaryKeyPropertyName = primaryKeyPropertyName;
		_properties = properties;
	}

	private Map<String, Object> _objectEntryToMap(ObjectEntry objectEntry) {
		Map<String, Object> properties = objectEntry.getProperties();

		properties.put(getIdName(), objectEntry.getId());

		return properties;
	}

	private static final Map<String, Class<?>> _mappedTypeToClass =
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

	private final EntityModel _entityModel;
	private final String _name;
	private final long _objectDefinitionId;
	private final ObjectEntryManager _objectEntryManager;
	private final String _primaryKeyPropertyName;
	private final List<GraphQLDTOProperty> _properties;

}