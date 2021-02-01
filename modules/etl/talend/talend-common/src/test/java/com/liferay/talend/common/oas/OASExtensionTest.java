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

package com.liferay.talend.common.oas;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class OASExtensionTest {

	@Test
	public void testGetI18nFieldName() {
		OASExtensions oasExtensions = new OASExtensions();

		Assert.assertEquals(
			"test_i18n", oasExtensions.getI18nFieldName("test_i18n"));

		Assert.assertEquals(
			"test_i18n",
			oasExtensions.getI18nFieldName("parent_nestedParent_test_i18n"));

		Assert.assertEquals(
			OASException.class,
			_getI18nFieldNameExceptionClass("parent_test_i18n_notAllowed"));

		Assert.assertEquals(
			OASException.class, _getI18nFieldNameExceptionClass("notAllowed"));
	}

	@Test
	public void testIsI18nFieldName() {
		OASExtensions oasExtensions = new OASExtensions();

		Assert.assertTrue(oasExtensions.isI18nFieldName("test_i18n"));

		Assert.assertTrue(oasExtensions.isI18nFieldName("parent_test_i18n"));

		Assert.assertTrue(
			oasExtensions.isI18nFieldName("parent_parent2_test_i18n"));

		Assert.assertFalse(oasExtensions.isI18nFieldName("test"));

		Assert.assertEquals(
			OASException.class,
			_isI18nFieldNameExceptionClass("parent_test_i18n_notAllowed"));

		Assert.assertEquals(
			OASException.class, _isI18nFieldNameExceptionClass("_i18n"));

		Assert.assertEquals(
			OASException.class, _isI18nFieldNameExceptionClass("_i18n_test"));

		Assert.assertEquals(
			OASException.class, _isI18nFieldNameExceptionClass("_i18ntest"));

		Assert.assertEquals(
			OASException.class,
			_isI18nFieldNameExceptionClass("test_i18n_test"));
	}

	@Test
	public void testIsI18nFieldNameNested() {
		OASExtensions oasExtensions = new OASExtensions();

		Assert.assertTrue(
			oasExtensions.isI18nFieldNameNested(
				"nestedParent_nestedParent2_test_i18n"));

		Assert.assertTrue(
			oasExtensions.isI18nFieldNameNested("nestedParent_test_i18n"));

		Assert.assertFalse(oasExtensions.isI18nFieldNameNested("test_i18n"));

		Assert.assertFalse(oasExtensions.isI18nFieldNameNested("test"));

		Assert.assertFalse(oasExtensions.isI18nFieldNameNested("_i18nest"));

		Assert.assertFalse(
			oasExtensions.isI18nFieldNameNested("nestedParent_attribute"));
	}

	private Class<? extends Exception> _getI18nFieldNameExceptionClass(
		String invalidI18Name) {

		OASExtensions oasExtensions = new OASExtensions();

		try {
			oasExtensions.getI18nFieldName(invalidI18Name);

			return Exception.class;
		}
		catch (Exception exception) {
			return exception.getClass();
		}
	}

	private Class<? extends Exception> _isI18nFieldNameExceptionClass(
		String invalidI18Name) {

		OASExtensions oasExtensions = new OASExtensions();

		try {
			oasExtensions.isI18nFieldName(invalidI18Name);

			return Exception.class;
		}
		catch (Exception exception) {
			return exception.getClass();
		}
	}

}