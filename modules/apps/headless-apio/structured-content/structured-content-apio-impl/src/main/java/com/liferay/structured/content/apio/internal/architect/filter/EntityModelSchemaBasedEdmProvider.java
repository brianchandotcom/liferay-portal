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

package com.liferay.structured.content.apio.internal.architect.filter;

import com.liferay.structured.content.apio.architect.entity.EntityField;
import com.liferay.structured.content.apio.architect.entity.EntityModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainer;
import org.apache.olingo.commons.api.edm.provider.CsdlEntitySet;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityType;
import org.apache.olingo.commons.api.edm.provider.CsdlProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlSchema;
import org.apache.olingo.server.core.SchemaBasedEdmProvider;

/**
 * Provides the Common Schema Definition Language (CSDL) for an Entity Data
 * Model (EDM) used by a service.
 *
 * <p>
 * To formalize the description of the resources they expose, OData services use
 * EDM as their underlying abstract data model. CSDL defines the entity model's
 * XML-based representation.
 * </p>
 *
 * @author David Arques
 */
public class EntityModelSchemaBasedEdmProvider extends SchemaBasedEdmProvider {

	public EntityModelSchemaBasedEdmProvider(EntityModel entityModel) {
		addSchema(
			_createCsdlSchema(
				"HypermediaRestApis", entityModel.getName(),
				_createCsdlProperties(entityModel.getEntityFieldsMap())));
	}

	private CsdlEntityContainer _createCsdlEntityContainer(
		String namespace, String name) {

		CsdlEntityContainer csdlEntityContainer = new CsdlEntityContainer();

		csdlEntityContainer.setEntitySets(
			_createCsdlEntitySets(namespace, name));
		csdlEntityContainer.setName(name);

		return csdlEntityContainer;
	}

	private List<CsdlEntitySet> _createCsdlEntitySets(
		String namespace, String entityNameType) {

		CsdlEntitySet csdlEntitySet = new CsdlEntitySet();

		csdlEntitySet.setName(entityNameType);
		csdlEntitySet.setType(new FullQualifiedName(namespace, entityNameType));

		return Collections.singletonList(csdlEntitySet);
	}

	private CsdlEntityType _createCsdlEntityType(
		String name, List<CsdlProperty> csdlProperties) {

		CsdlEntityType csdlEntityType = new CsdlEntityType();

		csdlEntityType.setName(name);
		csdlEntityType.setProperties(csdlProperties);

		return csdlEntityType;
	}

	private List<CsdlProperty> _createCsdlProperties(
		Map<String, EntityField> entityFieldsMap) {

		Collection<EntityField> entityFields = entityFieldsMap.values();

		Stream<EntityField> stream = entityFields.stream();

		return stream.map(
			this::_createCsdlProperty
		).collect(
			Collectors.toList()
		);
	}

	private CsdlProperty _createCsdlProperty(EntityField entityField) {
		CsdlProperty csdlProperty = new CsdlProperty();

		csdlProperty.setName(entityField.getName());

		FullQualifiedName fullQualifiedName = null;

		if (Objects.equals(entityField.getType(), EntityField.Type.DATE)) {
			fullQualifiedName =
				EdmPrimitiveTypeKind.DateTimeOffset.getFullQualifiedName();
		}
		else if (Objects.equals(
					entityField.getType(), EntityField.Type.STRING)) {

			fullQualifiedName =
				EdmPrimitiveTypeKind.String.getFullQualifiedName();
		}

		csdlProperty.setType(fullQualifiedName);

		return csdlProperty;
	}

	private CsdlSchema _createCsdlSchema(
		String namespace, String name, List<CsdlProperty> csdlProperties) {

		CsdlSchema csdlSchema = new CsdlSchema();

		csdlSchema.setEntityContainer(
			_createCsdlEntityContainer(namespace, name));
		csdlSchema.setEntityTypes(
			Collections.singletonList(
				_createCsdlEntityType(name, csdlProperties)));
		csdlSchema.setNamespace(namespace);

		return csdlSchema;
	}

}