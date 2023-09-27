/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.internal.filter;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Map;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityType;
import org.apache.olingo.commons.api.edm.provider.CsdlProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlSchema;
import org.apache.olingo.commons.api.ex.ODataException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author David Arques
 */
public class EntityModelSchemaBasedEdmProviderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetSchemas() throws ODataException {
		String fieldName = "title";

		String entityName = "name";

		EntityModelSchemaBasedEdmProvider entityModelSchemaBasedEdmProvider =
			new EntityModelSchemaBasedEdmProvider(
				new EntityModel() {

					@Override
					public Map<String, EntityField> getEntityFieldsMap() {
						return HashMapBuilder.<String, EntityField>put(
							fieldName,
							new StringEntityField(
								fieldName, locale -> fieldName)
						).build();
					}

					@Override
					public String getName() {
						return entityName;
					}

				});

		List<CsdlSchema> csdlSchemas =
			entityModelSchemaBasedEdmProvider.getSchemas();

		Assert.assertEquals(csdlSchemas.toString(), 1, csdlSchemas.size());

		CsdlSchema csdlSchema = csdlSchemas.get(0);

		Assert.assertEquals("HypermediaRestApis", csdlSchema.getNamespace());

		List<CsdlEntityType> csdlEntityTypes = csdlSchema.getEntityTypes();

		Assert.assertEquals(
			csdlEntityTypes.toString(), 1, csdlEntityTypes.size());

		CsdlEntityType csdlEntityType = csdlEntityTypes.get(0);

		Assert.assertEquals(entityName, csdlEntityType.getName());

		CsdlProperty csdlProperty = csdlEntityType.getProperty(fieldName);

		Assert.assertNotNull(csdlProperty);
		Assert.assertEquals(
			EdmPrimitiveTypeKind.String.getFullQualifiedName(),
			csdlProperty.getTypeAsFQNObject());
	}

}