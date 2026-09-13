/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.util;

import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Nathaly Gomes
 */
public class OpenAPISchemaUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	@TestInfo("LPD-103300")
	public void testGetReference() {
		Assert.assertEquals(
			_REFERENCE, OpenAPISchemaUtil.getReference(_getReferenceSchema()));

		ArraySchema arraySchema = new ArraySchema();

		arraySchema.setItems(_getReferenceSchema());

		Assert.assertEquals(
			_REFERENCE, OpenAPISchemaUtil.getReference(arraySchema));

		Assert.assertEquals(
			_REFERENCE,
			OpenAPISchemaUtil.getReference(
				OpenAPISchemaUtil.setDescription(
					_getReferenceSchema(), _DESCRIPTION)));
	}

	@Test
	@TestInfo("LPD-103300")
	public void testSetDescriptionWhenDescriptionIsNull() {
		Schema schema = _getReferenceSchema();

		Assert.assertSame(
			schema, OpenAPISchemaUtil.setDescription(schema, null));
		Assert.assertNull(schema.getAllOf());
		Assert.assertNull(schema.getDescription());
	}

	@Test
	@TestInfo("LPD-103300")
	public void testSetDescriptionWhenSchemaIsNotAReference() {
		Schema schema = new ObjectSchema();

		Assert.assertSame(
			schema, OpenAPISchemaUtil.setDescription(schema, _DESCRIPTION));
		Assert.assertNull(schema.getAllOf());
		Assert.assertEquals(_DESCRIPTION, schema.getDescription());
	}

	@Test
	@TestInfo("LPD-103300")
	public void testSetDescriptionWrapsTheReference() {
		Schema schema = _getReferenceSchema();

		schema.setExtensions(
			Collections.singletonMap("x-parent-map", "properties"));

		Schema wrapperSchema = OpenAPISchemaUtil.setDescription(
			schema, _DESCRIPTION);

		Assert.assertNotSame(schema, wrapperSchema);
		Assert.assertNull(wrapperSchema.get$ref());
		Assert.assertEquals(
			Collections.singletonList(schema), wrapperSchema.getAllOf());
		Assert.assertEquals(_DESCRIPTION, wrapperSchema.getDescription());
		Assert.assertEquals(
			schema.getExtensions(), wrapperSchema.getExtensions());
	}

	@Test
	@TestInfo("LPD-103300")
	public void testSetReferenceWhenDescriptionIsNotNull() {
		Schema schema = new ObjectSchema();

		schema.setDescription(_DESCRIPTION);

		OpenAPISchemaUtil.setReference(schema, _REFERENCE);

		Assert.assertNull(schema.get$ref());
		Assert.assertEquals(_DESCRIPTION, schema.getDescription());
		Assert.assertEquals(_REFERENCE, OpenAPISchemaUtil.getReference(schema));
	}

	@Test
	@TestInfo("LPD-103300")
	public void testSetReferenceWhenDescriptionIsNull() {
		Schema schema = new ObjectSchema();

		OpenAPISchemaUtil.setReference(schema, _REFERENCE);

		Assert.assertEquals(_REFERENCE, schema.get$ref());
		Assert.assertNull(schema.getAllOf());
	}

	private Schema _getReferenceSchema() {
		Schema schema = new ObjectSchema();

		schema.set$ref(_REFERENCE);

		return schema;
	}

	private static final String _DESCRIPTION =
		"This is a claim filed by a customer.";

	private static final String _REFERENCE = "#/components/schemas/Claim";

}