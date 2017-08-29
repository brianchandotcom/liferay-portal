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

package com.liferay.dynamic.data.lists.form.web.internal.portlet.action.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.portlet.PortletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.portlet.MockPortletRequest;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
@Sync
public class RecordSetFieldSettingsValidatorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		setUpRecordSetFieldSettingsValidator();
	}

	@Test
	public void testParagraphFieldWithInvalidSettings() throws Exception {
		MockPortletRequest mockPortletRequest = createPortletRequest();

		mockPortletRequest.setParameter(
			"serializedFormBuilderContext",
			read("form-context-with-one-paragraph-with-invalid-settings.json"));

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField paragraphDDMFormField = DDMFormTestUtil.createDDMFormField(
			"Paragraph", "Paragraph", "paragraph", "", true, false, false);

		ddmForm.addDDMFormField(paragraphDDMFormField);

		assertValidateExceptionMessage(
			mockPortletRequest, ddmForm, "Paragraph");
	}

	@Test
	public void testSelectFieldWithInvalidSettings() throws Exception {
		MockPortletRequest mockPortletRequest = createPortletRequest();

		mockPortletRequest.setParameter(
			"serializedFormBuilderContext",
			read("form-context-with-one-select-with-invalid-settings.json"));

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField selectDDMFormField = DDMFormTestUtil.createDDMFormField(
			"Select", "Select", "select", "string", true, false, false);

		selectDDMFormField.setProperty("dataSourceType", "data-provider");

		ddmForm.addDDMFormField(selectDDMFormField);

		assertValidateExceptionMessage(mockPortletRequest, ddmForm, "Select");
	}

	protected void assertValidateExceptionMessage(
		PortletRequest portletRequest, DDMForm ddmForm, String fieldName) {

		PortalException validateException = null;

		try {
			invokeValidate(portletRequest, ddmForm);
		}
		catch (Throwable t) {
			Class<? extends Throwable> throwableClass = t.getClass();

			String throwableClassName = throwableClass.getName();

			if (t instanceof PortalException &&
				throwableClassName.endsWith("MustSetValidValueForProperties")) {

				validateException = (PortalException)t;
			}
		}

		String expectedMessage = String.format(
			"Invalid value set for the properties of fields [%s]", fieldName);

		Assert.assertNotNull(validateException);

		Assert.assertEquals(expectedMessage, validateException.getMessage());
	}

	protected MockPortletRequest createPortletRequest() {
		MockPortletRequest mockPortletRequest = new MockPortletRequest();

		mockPortletRequest.setAttribute(
			PortletServlet.PORTLET_SERVLET_REQUEST,
			new MockHttpServletRequest());

		return mockPortletRequest;
	}

	protected void invokeValidate(
			PortletRequest portletRequest, DDMForm ddmForm)
		throws Throwable {

		Class<?> clazz = _recordSetFieldSettingsValidator.getClass();

		Method method = clazz.getDeclaredMethod(
			"validate", PortletRequest.class, DDMForm.class);

		method.setAccessible(true);

		try {
			method.invoke(
				_recordSetFieldSettingsValidator, portletRequest, ddmForm);
		}
		catch (InvocationTargetException ite) {
			throw ite.getCause();
		}
	}

	protected String read(String fileName) throws IOException {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	protected void setUpRecordSetFieldSettingsValidator() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		StringBundler sb = new StringBundler(3);

		sb.append("(objectClass=");
		sb.append(_CLASS_NAME);
		sb.append(")");

		Object[] servlets = registry.getServices(_CLASS_NAME, sb.toString());

		_recordSetFieldSettingsValidator = servlets[0];
	}

	private static final String _CLASS_NAME =
		"com.liferay.dynamic.data.lists.form.web.internal.portlet.action." +
			"util.RecordSetFieldSettingsValidator";

	private Object _recordSetFieldSettingsValidator;

}