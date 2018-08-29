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

package com.liferay.structured.content.apio.internal.provider;

import com.liferay.structured.content.apio.internal.architect.filter.StructuredContentEdmProvider;

import java.util.List;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityType;
import org.apache.olingo.commons.api.edm.provider.CsdlProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlSchema;
import org.apache.olingo.commons.api.ex.ODataException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author David Arques
 */
public class StructuredContentEdmProviderTest {

	@Test
	public void testGetSingleEntityTypeName() {
		StructuredContentEdmProvider structuredContentEdmProvider =
			new StructuredContentEdmProvider();

		Assert.assertEquals(
			"StructuredContent",
			structuredContentEdmProvider.getSingleEntityTypeName());
	}

	@Test
	public void testModelCreation() throws ODataException {
		StructuredContentEdmProvider structuredContentEdmProvider =
			new StructuredContentEdmProvider();

		List<CsdlSchema> schemas = structuredContentEdmProvider.getSchemas();

		Assert.assertEquals(schemas.toString(), 1, schemas.size());

		CsdlSchema schema = schemas.get(0);

		Assert.assertEquals("HypermediaRestApis", schema.getNamespace());

		List<CsdlEntityType> entityTypes = schema.getEntityTypes();

		Assert.assertEquals(entityTypes.toString(), 1, entityTypes.size());

		CsdlEntityType entityType = entityTypes.get(0);

		Assert.assertEquals("StructuredContent", entityType.getName());

		CsdlProperty titleProperty = entityType.getProperty("title");

		Assert.assertNotNull(titleProperty);
		Assert.assertEquals(
			EdmPrimitiveTypeKind.String.getFullQualifiedName(),
			titleProperty.getTypeAsFQNObject());
	}

}