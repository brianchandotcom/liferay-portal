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

package com.liferay.dynamic.data.mapping.form.field.type.radio.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Rafael Praxedes
 */
@RunWith(PowerMockRunner.class)
public class RadioDDMFormFieldValueRequestParameterRetrieverTest
	extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		_radioDDMFormFieldValueRequestParameterRetriever =
			new RadioDDMFormFieldValueRequestParameterRetriever();

		field(
			RadioDDMFormFieldValueRequestParameterRetriever.class,
			"_jsonFactory"
		).set(
			_radioDDMFormFieldValueRequestParameterRetriever,
			new JSONFactoryImpl()
		);
	}

	@Test
	public void testEmptySubmitWithoutPredefinedValue() {
		String fieldValue =
			_radioDDMFormFieldValueRequestParameterRetriever.get(
				new MockHttpServletRequest(), "radio0", "[]");

		Assert.assertEquals(StringPool.BLANK, fieldValue);
	}

	private RadioDDMFormFieldValueRequestParameterRetriever
		_radioDDMFormFieldValueRequestParameterRetriever;

}