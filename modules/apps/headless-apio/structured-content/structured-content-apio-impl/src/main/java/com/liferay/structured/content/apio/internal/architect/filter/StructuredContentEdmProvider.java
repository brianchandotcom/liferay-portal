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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainer;
import org.apache.olingo.commons.api.edm.provider.CsdlEntitySet;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityType;
import org.apache.olingo.commons.api.edm.provider.CsdlProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlSchema;

/**
 * Provides the entity data model from the Indexed Entity (JournalArticle).
 *
 * @author Julio Camarero
 * @review
 */
public class StructuredContentEdmProvider
	extends SingleEntitySchemaBaseProvider {

	public StructuredContentEdmProvider() {
		addSchema(_createCsdlSchema());
	}

	@Override
	public String getSingleEntityTypeName() {
		return _ENTITY_NAME;
	}

	private CsdlEntityContainer _createCsdlEntityContainer() {
		CsdlEntityType csdlEntityType = _createCsdlEntityType();

		FullQualifiedName fullQualifiedName = new FullQualifiedName(
			_NAMESPACE, csdlEntityType.getName());

		CsdlEntitySet csdlEntitySet = new CsdlEntitySet();

		csdlEntitySet.setName(csdlEntityType.getName());
		csdlEntitySet.setType(fullQualifiedName);

		CsdlEntityContainer csdlEntityContainer = new CsdlEntityContainer();

		csdlEntityContainer.setEntitySets(
			Collections.singletonList(csdlEntitySet));
		csdlEntityContainer.setName(_ENTITY_NAME);

		return csdlEntityContainer;
	}

	private CsdlEntityType _createCsdlEntityType() {
		List<CsdlProperty> csdlProperties = new ArrayList<>();

		CsdlProperty csdlProperty = _createCsdlProperty(
			_TITLE_FIELD, EdmPrimitiveTypeKind.String.getFullQualifiedName());

		csdlProperties.add(csdlProperty);

		CsdlEntityType csdlEntityType = new CsdlEntityType();

		csdlEntityType.setName(_ENTITY_NAME);
		csdlEntityType.setProperties(csdlProperties);

		return csdlEntityType;
	}

	private CsdlProperty _createCsdlProperty(
		String name, FullQualifiedName fullQualifiedName) {

		CsdlProperty csdlProperty = new CsdlProperty();

		csdlProperty.setName(name);
		csdlProperty.setType(fullQualifiedName);

		return csdlProperty;
	}

	private CsdlSchema _createCsdlSchema() {
		CsdlSchema csdlSchema = new CsdlSchema();

		csdlSchema.setEntityContainer(_createCsdlEntityContainer());
		csdlSchema.setEntityTypes(
			Collections.singletonList(_createCsdlEntityType()));
		csdlSchema.setNamespace(_NAMESPACE);

		return csdlSchema;
	}

	private static final String _ENTITY_NAME = "StructuredContent";

	private static final String _NAMESPACE = "HypermediaRestApis";

	private static final String _TITLE_FIELD = "title";

}