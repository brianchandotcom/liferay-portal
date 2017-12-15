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

package com.liferay.apio.architect;

import static com.openpojo.reflection.impl.PojoClassFactory.getPojoClass;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.related.RelatedCollection;
import com.liferay.apio.architect.related.RelatedModel;
import com.liferay.apio.architect.single.model.SingleModel;

import com.openpojo.reflection.utils.AttributeHelper;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.NoFieldShadowingRule;
import com.openpojo.validation.rule.impl.NoPublicFieldsRule;
import com.openpojo.validation.rule.impl.NoStaticExceptFinalRule;
import com.openpojo.validation.test.impl.DefaultValuesNullTester;
import com.openpojo.validation.test.impl.GetterTester;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class POJOTest {

	@Before
	public void setUp() {
		AttributeHelper.registerFieldPrefix("_");

		_validator = ValidatorBuilder.create(
		).with(
			new GetterMustExistRule()
		).with(
			new NoStaticExceptFinalRule()
		).with(
			new NoFieldShadowingRule()
		).with(
			new NoPublicFieldsRule()
		).with(
			new GetterTester()
		).with(
			new DefaultValuesNullTester()
		).build();
	}

	@Test
	public void testIdentifier() {
		_validator.validate(getPojoClass(RelatedCollection.class));
		_validator.validate(getPojoClass(RelatedModel.class));
	}

	@Test
	public void testPagination() {
		_validator.validate(getPojoClass(PageItems.class));
	}

	@Test
	public void testSingleModel() {
		_validator.validate(getPojoClass(SingleModel.class));
	}

	@Test
	public void testURI() {
		_validator.validate("com.liferay.apio.architect.uri");
	}

	private Validator _validator;

}