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

package com.liferay.dynamic.data.mapping.form.field.type.internal.select.multi.language.option;

import com.liferay.dynamic.data.mapping.form.field.type.internal.select.SelectDDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Rodrigo Paulino
 */
@PrepareForTest({LanguageUtil.class, ResourceBundleUtil.class})
@RunWith(PowerMockRunner.class)
public class
	MultiLanguageOptionSelectDDMFormFieldTemplateContextContributorTest {

	@Before
	public void setUp() throws Exception {
		_setUpLanguageUtil();
		_setUpResourceBundleUtil();
	}

	@Test
	public void testGetParameters() throws Exception {
		_mockSelectDDMFormFieldTemplateContextContributor(
			HashMapBuilder.<String, Object>put(
				"options",
				ListUtil.fromArray(
					HashMapBuilder.put(
						"label", "Address"
					).put(
						"value", "address"
					).build(),
					HashMapBuilder.put(
						"label", "City"
					).put(
						"value", "city"
					).build())
			).build());

		Map<String, Object> parameters =
			_multiLanguageOptionSelectDDMFormFieldTemplateContextContributor.
				getParameters(
					PowerMockito.mock(DDMFormField.class),
					PowerMockito.mock(DDMFormFieldRenderingContext.class));

		Assert.assertEquals(
			ListUtil.fromArray(
				HashMapBuilder.<String, Object>put(
					"label",
					HashMapBuilder.put(
						"en_US", "Address"
					).put(
						"pt_BR", "Endereço"
					).build()
				).put(
					"value", "address"
				).build(),
				HashMapBuilder.<String, Object>put(
					"label",
					HashMapBuilder.put(
						"en_US", "City"
					).put(
						"pt_BR", "Cidade"
					).build()
				).put(
					"value", "city"
				).build()),
			parameters.get("options"));
	}

	private void _mockGetLanguageId(Locale locale) {
		PowerMockito.when(
			LanguageUtil.getLanguageId(Matchers.eq(locale))
		).thenReturn(
			LocaleUtil.toLanguageId(locale)
		);
	}

	private void _mockGetModuleAndPortalResourceBundle(
		Locale locale, Map<String, String> optionLabels) {

		ResourceBundle resourceBundle = _mockResourceBundle(optionLabels);

		PowerMockito.when(
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				Matchers.eq(locale), Matchers.any())
		).thenReturn(
			resourceBundle
		);
	}

	private ResourceBundle _mockResourceBundle(
		Map<String, String> optionLabels) {

		ResourceBundle resourceBundle = PowerMockito.mock(ResourceBundle.class);

		PowerMockito.when(
			LanguageUtil.get(Matchers.eq(resourceBundle), Matchers.anyString())
		).then(
			new Answer<String>() {

				public String answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					Object[] arguments = invocationOnMock.getArguments();

					return optionLabels.get((String)arguments[1]);
				}

			}
		);

		return resourceBundle;
	}

	private void _mockSelectDDMFormFieldTemplateContextContributor(
			Map<String, Object> optionsMap)
		throws Exception {

		SelectDDMFormFieldTemplateContextContributor
			mockSelectDDMFormFieldTemplateContextContributor =
				PowerMockito.mock(
					SelectDDMFormFieldTemplateContextContributor.class);

		PowerMockito.when(
			mockSelectDDMFormFieldTemplateContextContributor.getParameters(
				Matchers.any(DDMFormField.class),
				Matchers.any(DDMFormFieldRenderingContext.class))
		).thenReturn(
			optionsMap
		);

		MemberMatcher.field(
			MultiLanguageOptionSelectDDMFormFieldTemplateContextContributor.
				class,
			"_selectDDMFormFieldTemplateContextContributor"
		).set(
			_multiLanguageOptionSelectDDMFormFieldTemplateContextContributor,
			mockSelectDDMFormFieldTemplateContextContributor
		);
	}

	private void _setUpLanguageUtil() {
		PowerMockito.mockStatic(LanguageUtil.class);

		PowerMockito.when(
			LanguageUtil.getAvailableLocales()
		).thenReturn(
			SetUtil.fromArray(new Locale[] {LocaleUtil.BRAZIL, LocaleUtil.US})
		);

		_mockGetLanguageId(LocaleUtil.BRAZIL);
		_mockGetLanguageId(LocaleUtil.US);
	}

	private void _setUpResourceBundleUtil() {
		PowerMockito.mockStatic(ResourceBundleUtil.class);

		_mockGetModuleAndPortalResourceBundle(
			LocaleUtil.BRAZIL,
			HashMapBuilder.put(
				"address", "Endereço"
			).put(
				"city", "Cidade"
			).put(
				"country", "País"
			).put(
				"postal-code", "CEP"
			).put(
				"state", "Estado"
			).build());
		_mockGetModuleAndPortalResourceBundle(
			LocaleUtil.US,
			HashMapBuilder.put(
				"address", "Address"
			).put(
				"city", "City"
			).put(
				"country", "Country"
			).put(
				"postal-code", "Postal Code"
			).put(
				"state", "State"
			).build());
	}

	private final
		MultiLanguageOptionSelectDDMFormFieldTemplateContextContributor
			_multiLanguageOptionSelectDDMFormFieldTemplateContextContributor =
				new MultiLanguageOptionSelectDDMFormFieldTemplateContextContributor();

}