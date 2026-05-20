/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInvoker;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Carolina Barbosa
 */
public class DDMFormFieldOptionsFactoryImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpDDMDataProviderInvoker();
		_setUpDDMFormField();
		_setUpDDMFormFieldRenderingContext();
		_setUpHttpServletRequest();
		_setUpJSONFactory();
		_setUpPortal();
	}

	@Test
	public void testCreate() {
		Mockito.when(
			_ddmFormFieldRenderingContext.getLocale()
		).thenReturn(
			LocaleUtil.BRAZIL
		);

		_mockDDMDataProviderResponse(
			ListUtil.fromArray(
				new KeyValuePair("key1", "value1"),
				new KeyValuePair("key2", "value2")));

		DDMFormFieldOptions ddmFormFieldOptions =
			_ddmFormFieldOptionsFactoryImpl.create(
				_ddmFormField, _ddmFormFieldRenderingContext);

		Assert.assertEquals(
			"value1", _getString(ddmFormFieldOptions.getOptionLabels("key1")));
		Assert.assertEquals(
			"value2", _getString(ddmFormFieldOptions.getOptionLabels("key2")));
		Assert.assertNull(ddmFormFieldOptions.getOptionReference("key1"));
		Assert.assertNull(ddmFormFieldOptions.getOptionReference("key2"));

		ArgumentCaptor<DDMDataProviderRequest> argumentCaptor =
			ArgumentCaptor.forClass(DDMDataProviderRequest.class);

		Mockito.verify(
			_ddmDataProviderInvoker
		).invoke(
			argumentCaptor.capture()
		);

		DDMDataProviderRequest ddmDataProviderRequest =
			argumentCaptor.getValue();

		Assert.assertEquals(
			LocaleUtil.BRAZIL, ddmDataProviderRequest.getLocale());

		Mockito.clearInvocations(_ddmDataProviderInvoker);

		Mockito.when(
			_ddmFormFieldRenderingContext.getLocale()
		).thenReturn(
			LocaleUtil.SPAIN
		);

		_mockDDMDataProviderResponse(
			ListUtil.fromArray(
				new KeyValuePair("key1", "value1"),
				new KeyValuePair("key2", "value2")));

		_ddmFormFieldOptionsFactoryImpl.create(
			_ddmFormField, _ddmFormFieldRenderingContext);

		argumentCaptor = ArgumentCaptor.forClass(DDMDataProviderRequest.class);

		Mockito.verify(
			_ddmDataProviderInvoker
		).invoke(
			argumentCaptor.capture()
		);

		ddmDataProviderRequest = argumentCaptor.getValue();

		Assert.assertEquals(
			LocaleUtil.SPAIN, ddmDataProviderRequest.getLocale());

		_mockDDMDataProviderResponse(null);

		ddmFormFieldOptions = _ddmFormFieldOptionsFactoryImpl.create(
			_ddmFormField, _ddmFormFieldRenderingContext);

		Assert.assertEquals(
			"label1",
			_getString(ddmFormFieldOptions.getOptionLabels("value1")));
		Assert.assertEquals(
			"label2",
			_getString(ddmFormFieldOptions.getOptionLabels("value2")));
		Assert.assertEquals(
			"reference1", ddmFormFieldOptions.getOptionReference("value1"));
		Assert.assertEquals(
			"reference2", ddmFormFieldOptions.getOptionReference("value2"));
	}

	private String _getString(LocalizedValue localizedValue) {
		return localizedValue.getString(LocaleUtil.US);
	}

	private void _mockDDMDataProviderResponse(Object output) {
		Mockito.when(
			_ddmDataProviderResponse.getOutput(
				"ddmDataProviderInstanceOutput", List.class)
		).thenReturn(
			output
		);
	}

	private void _setUpDDMDataProviderInvoker() {
		Mockito.when(
			_ddmDataProviderInvoker.invoke(
				Mockito.any(DDMDataProviderRequest.class))
		).thenReturn(
			_ddmDataProviderResponse
		);

		ReflectionTestUtil.setFieldValue(
			_ddmFormFieldOptionsFactoryImpl, "ddmDataProviderInvoker",
			_ddmDataProviderInvoker);
	}

	private void _setUpDDMFormField() {
		Mockito.when(
			_ddmFormField.getDataSourceType()
		).thenReturn(
			"data-provider"
		);

		Mockito.when(
			_ddmFormField.getProperty("ddmDataProviderInstanceId")
		).thenReturn(
			"[\"ddmDataProviderInstanceId\"]"
		);

		Mockito.when(
			_ddmFormField.getProperty("ddmDataProviderInstanceOutput")
		).thenReturn(
			"[\"ddmDataProviderInstanceOutput\"]"
		);
	}

	private void _setUpDDMFormFieldRenderingContext() {
		Mockito.when(
			_ddmFormFieldRenderingContext.getHttpServletRequest()
		).thenReturn(
			_httpServletRequest
		);

		Mockito.when(
			_ddmFormFieldRenderingContext.getLocale()
		).thenReturn(
			LocaleUtil.US
		);

		Mockito.when(
			_ddmFormFieldRenderingContext.getProperty("options")
		).thenReturn(
			ListUtil.fromArray(
				HashMapBuilder.put(
					"label", "label1"
				).put(
					"reference", "reference1"
				).put(
					"value", "value1"
				).build(),
				HashMapBuilder.put(
					"label", "label2"
				).put(
					"reference", "reference2"
				).put(
					"value", "value2"
				).build())
		);
	}

	private void _setUpHttpServletRequest() {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getScopeGroupId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		_httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);
	}

	private void _setUpJSONFactory() {
		ReflectionTestUtil.setFieldValue(
			_ddmFormFieldOptionsFactoryImpl, "jsonFactory",
			new JSONFactoryImpl());
	}

	private void _setUpPortal() {
		Mockito.when(
			_portal.getCompanyId(_httpServletRequest)
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		ReflectionTestUtil.setFieldValue(
			_ddmFormFieldOptionsFactoryImpl, "portal", _portal);
	}

	private final DDMDataProviderInvoker _ddmDataProviderInvoker = Mockito.mock(
		DDMDataProviderInvoker.class);
	private final DDMDataProviderResponse _ddmDataProviderResponse =
		Mockito.mock(DDMDataProviderResponse.class);
	private final DDMFormField _ddmFormField = Mockito.mock(DDMFormField.class);
	private final DDMFormFieldOptionsFactoryImpl
		_ddmFormFieldOptionsFactoryImpl = new DDMFormFieldOptionsFactoryImpl();
	private final DDMFormFieldRenderingContext _ddmFormFieldRenderingContext =
		Mockito.mock(DDMFormFieldRenderingContext.class);
	private final HttpServletRequest _httpServletRequest =
		new MockHttpServletRequest();
	private final Portal _portal = Mockito.mock(Portal.class);

}