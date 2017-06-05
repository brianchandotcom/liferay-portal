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

package com.liferay.dynamic.data.lists.form.web.internal.converter.serializer;

import com.liferay.dynamic.data.lists.form.web.internal.converter.model.action.CalculateDDLFormRuleAction;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Leonardo Barros
 */
@PrepareForTest(ServiceContextThreadLocal.class)
@RunWith(PowerMockRunner.class)
public class CalculateDDLFormRuleActionSerializerTest extends PowerMockito {

	@Before
	public void setUp() {
		setUpServiceContextThreadLocal();
	}

	@Test
	public void testSerialize() {
		when(
			_calculateDDLFormRuleAction.getExpression()
		).thenReturn(
			"(text + text1) * 2"
		);

		when(
			_calculateDDLFormRuleAction.getTarget()
		).thenReturn(
			"text2"
		);

		DDMForm ddmForm = new DDMForm();

		DDMFormField ddmFormField1 = new DDMFormField("text", "string");
		DDMFormField ddmFormField2 = new DDMFormField("text1", "string");

		ddmForm.setDDMFormFields(Arrays.asList(ddmFormField1, ddmFormField2));

		when(
			_serviceContext.getAttribute("form")
		).thenReturn(
			ddmForm
		);

		CalculateDDLFormRuleActionSerializer
			calculateDDLFormRuleActionSerializer =
				new CalculateDDLFormRuleActionSerializer(
					_calculateDDLFormRuleAction);

		PowerMockito.when(
			_ddlFormRuleSerializerContext.getAttribute("form")
		).thenReturn(
			ddmForm
		);

		String result = calculateDDLFormRuleActionSerializer.serialize(
			_ddlFormRuleSerializerContext);

		Assert.assertEquals(
			"calculate('text2', (getValue('text') + getValue('text1')) * 2)",
			result);
	}

	protected void setUpServiceContextThreadLocal() {
		mockStatic(ServiceContextThreadLocal.class);

		when(
			ServiceContextThreadLocal.getServiceContext()
		).thenReturn(
			_serviceContext
		);
	}

	@Mock
	private CalculateDDLFormRuleAction _calculateDDLFormRuleAction;

	@Mock
	private DDLFormRuleSerializerContext _ddlFormRuleSerializerContext;

	@Mock
	private ServiceContext _serviceContext;

}