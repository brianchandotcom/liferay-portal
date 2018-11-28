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

package com.liferay.portal.odata.internal.filter;

import com.liferay.portal.odata.entity.CollectionEntityField;
import com.liferay.portal.odata.entity.ComplexEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.CsdlComplexType;
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
				_NAMESPACE, entityModel.getName(),
				_createCsdlProperties(
					_NAMESPACE, entityModel.getEntityFieldsMap()),
				_createCsdlComplexTypes(
					_NAMESPACE, entityModel.getEntityFieldsMap())));
	}

	private CsdlProperty _createCollectionCsdlProperty(
		EntityField entityField, FullQualifiedName fullQualifiedName) {

		CsdlProperty csdlProperty = new CsdlProperty();

		csdlProperty.setCollection(true);
		csdlProperty.setName(entityField.getName());
		csdlProperty.setType(fullQualifiedName);

		return csdlProperty;
	}

	private Optional<CsdlComplexType> _createCsdlComplexType(
		String namespace, EntityField entityField) {

		if (!Objects.equals(entityField.getType(), EntityField.Type.COMPLEX)) {
			return Optional.empty();
		}

		ComplexEntityField complexEntityField = (ComplexEntityField)entityField;

		CsdlComplexType csdlComplexType = new CsdlComplexType();

		csdlComplexType.setName(entityField.getName());

		Map<String, EntityField> entityFieldsMap =
			complexEntityField.getEntityFieldsMap();

		Collection<EntityField> entityFields = entityFieldsMap.values();

		Stream<EntityField> stream = entityFields.stream();

		csdlComplexType.setProperties(
			stream.map(
				nestedEntityField -> _createCsdlProperty(
					namespace, nestedEntityField)
			).flatMap(
				csdlPropertyOptional -> csdlPropertyOptional.map(
					Stream::of
				).orElseGet(
					Stream::empty
				)
			).collect(
				Collectors.toList()
			)
		);

		return Optional.of(csdlComplexType);
	}

	private List<CsdlComplexType> _createCsdlComplexTypes(
		String namespace, Map<String, EntityField> entityFieldsMap) {

		Collection<EntityField> entityFields = entityFieldsMap.values();

		Stream<EntityField> stream = entityFields.stream();

		return stream.map(
			entityField -> _createCsdlComplexType(namespace, entityField)
		).flatMap(
			csdlPropertyOptional -> csdlPropertyOptional.map(
				Stream::of
			).orElseGet(
				Stream::empty
			)
		).collect(
			Collectors.toList()
		);
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
		String namespace, Map<String, EntityField> entityFieldsMap) {

		Collection<EntityField> entityFields = entityFieldsMap.values();

		Stream<EntityField> stream = entityFields.stream();

		return stream.map(
			entityField -> _createCsdlProperty(namespace, entityField)
		).flatMap(
			csdlPropertyOptional -> csdlPropertyOptional.map(
				Stream::of
			).orElseGet(
				Stream::empty
			)
		).collect(
			Collectors.toList()
		);
	}

	private Optional<CsdlProperty> _createCsdlProperty(
		String namespace, EntityField entityField) {

		if (Objects.equals(entityField.getType(), EntityField.Type.COMPLEX)) {
			CsdlProperty csdlProperty = new CsdlProperty();

			csdlProperty.setName(entityField.getName());

			csdlProperty.setType(
				new FullQualifiedName(namespace, entityField.getName()));

			return Optional.of(csdlProperty);
		}
		else {
			Optional<FullQualifiedName> fullQualifiedNameOptional =
				_getFullQualifiedName(entityField);

			return fullQualifiedNameOptional.map(
				fullQualifiedName -> {
					if (Objects.equals(
							entityField.getType(),
							EntityField.Type.COLLECTION)) {

						return Optional.of(
							_createCollectionCsdlProperty(
								entityField, fullQualifiedName)
						);
					}

					return Optional.of(
						_createPrimitiveCsdlProperty(
							entityField, fullQualifiedName)
					);
				}
			).orElse(
				Optional.empty()
			);
		}
	}

	private CsdlSchema _createCsdlSchema(
		String namespace, String name, List<CsdlProperty> csdlProperties,
		List<CsdlComplexType> csdlComplexTypes) {

		CsdlSchema csdlSchema = new CsdlSchema();

		csdlSchema.setComplexTypes(csdlComplexTypes);
		csdlSchema.setEntityContainer(
			_createCsdlEntityContainer(namespace, name));
		csdlSchema.setEntityTypes(
			Collections.singletonList(
				_createCsdlEntityType(name, csdlProperties)));
		csdlSchema.setNamespace(namespace);

		return csdlSchema;
	}

	private CsdlProperty _createPrimitiveCsdlProperty(
		EntityField entityField, FullQualifiedName fullQualifiedName) {

		CsdlProperty csdlProperty = new CsdlProperty();

		csdlProperty.setName(entityField.getName());
		csdlProperty.setType(fullQualifiedName);

		return csdlProperty;
	}

	private Optional<FullQualifiedName> _getFullQualifiedName(
		EntityField entityField) {

		if (Objects.equals(entityField.getType(), EntityField.Type.BOOLEAN)) {
			return Optional.of(
				EdmPrimitiveTypeKind.Boolean.getFullQualifiedName());
		}
		else if (Objects.equals(
					entityField.getType(), EntityField.Type.COLLECTION)) {

			CollectionEntityField collectionEntityField =
				(CollectionEntityField)entityField;

			return _getFullQualifiedName(
				collectionEntityField.getEntityField());
		}
		else if (Objects.equals(entityField.getType(), EntityField.Type.DATE)) {
			return Optional.of(
				EdmPrimitiveTypeKind.Date.getFullQualifiedName());
		}
		else if (Objects.equals(
					entityField.getType(), EntityField.Type.DATE_TIME)) {

			return Optional.of(
				EdmPrimitiveTypeKind.DateTimeOffset.getFullQualifiedName());
		}
		else if (Objects.equals(
					entityField.getType(), EntityField.Type.DOUBLE)) {

			return Optional.of(
				EdmPrimitiveTypeKind.Double.getFullQualifiedName());
		}
		else if (Objects.equals(entityField.getType(), EntityField.Type.ID) ||
				 Objects.equals(
					 entityField.getType(), EntityField.Type.STRING)) {

			return Optional.of(
				EdmPrimitiveTypeKind.String.getFullQualifiedName());
		}
		else if (Objects.equals(
					entityField.getType(), EntityField.Type.INTEGER)) {

			return Optional.of(
				EdmPrimitiveTypeKind.Int64.getFullQualifiedName());
		}

		return Optional.empty();
	}

	private static final String _NAMESPACE = "HypermediaRestApis";

}