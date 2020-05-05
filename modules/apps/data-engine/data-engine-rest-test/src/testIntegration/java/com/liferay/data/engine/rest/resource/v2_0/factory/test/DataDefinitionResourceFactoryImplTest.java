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

package com.liferay.data.engine.rest.resource.v2_0.factory.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class DataDefinitionResourceFactoryImplTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testHttpServletRequestLocale() throws Exception {
		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).httpServletRequest(
				new MockHttpServletRequest() {

					@Override
					public Object getAttribute(String name) {
						if (StringUtil.equals(name, WebKeys.LOCALE)) {
							return LocaleUtil.BRAZIL;
						}

						return super.getAttribute(name);
					}

				}
			).user(
				_user
			).build();

		_assertTranslations(
			_jsonFactory.createJSONArray(
				dataDefinitionResource.
					getDataDefinitionDataDefinitionFieldFieldTypes()),
			LocaleUtil.BRAZIL);
	}

	@Test
	public void testPreferredLocale() throws Exception {
		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).preferredLocale(
				LocaleUtil.BRAZIL
			).user(
				_user
			).build();

		_assertTranslations(
			_jsonFactory.createJSONArray(
				dataDefinitionResource.
					getDataDefinitionDataDefinitionFieldFieldTypes()),
			LocaleUtil.BRAZIL);
	}

	@Test
	public void testUserLocale() throws Exception {
		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).user(
				_user
			).build();

		_assertTranslations(
			_jsonFactory.createJSONArray(
				dataDefinitionResource.
					getDataDefinitionDataDefinitionFieldFieldTypes()),
			LocaleUtil.US);
	}

	private void _assertTranslations(JSONArray jsonArray, Locale locale) {
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			Map<String, Object> properties =
				_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeProperties(
					jsonObject.getString("name"));

			Assert.assertEquals(
				_translate(
					MapUtil.getString(
						properties, "ddm.form.field.type.description"),
					_getResourceBundle(jsonObject.getString("name"), locale)),
				jsonObject.getString("description"));
		}
	}

	private ResourceBundle _getResourceBundle(
		String ddmFormFieldTypeName, Locale locale) {

		DDMFormFieldType ddmFormFieldType =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldType(
				ddmFormFieldTypeName);

		return new AggregateResourceBundle(
			ResourceBundleUtil.getBundle(
				"content.Language", locale, getClass()),
			ResourceBundleUtil.getBundle(
				"content.Language", locale, ddmFormFieldType.getClass()),
			_portal.getResourceBundle(locale));
	}

	private String _translate(String key, ResourceBundle resourceBundle) {
		if (Validator.isNull(key)) {
			return StringPool.BLANK;
		}

		return GetterUtil.getString(
			ResourceBundleUtil.getString(resourceBundle, key), key);
	}

	@Inject
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private Portal _portal;

	@DeleteAfterTestRun
	private User _user;

}