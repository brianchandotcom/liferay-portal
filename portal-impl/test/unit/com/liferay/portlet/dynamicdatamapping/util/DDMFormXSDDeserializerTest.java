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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Pablo Carvalho
 */
public class DDMFormXSDDeserializerTest extends BaseDDMFormDeserializer {

	@Test
	public void testAllFieldsTypesDeserialization() throws Exception {
		String xml = readXML("ddm-form-xsd-deserializer-test-data.xml");

		DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(xml);

		testAvailableLocales(ddmForm);
		testDefaultLocale(ddmForm);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		testBooleanDDMFormField(ddmFormFieldsMap.get("Boolean2282"));
		testDateDDMFormField(ddmFormFieldsMap.get("Date2510"));
		testDecimalDDMFormField(ddmFormFieldsMap.get("Decimal3479"));
		testDocumentLibraryDDMFormField(
			ddmFormFieldsMap.get("Documents_and_Media4036"));
		testNestedDDMFormFields(ddmFormFieldsMap.get("Text6980"));
		testRadioDDMFormField(ddmFormFieldsMap.get("Radio5699"));
	}

	@Test
	public void testDefaultLocaleDifferentFromSiteDefaultLocale()
		throws Exception {

		String xml = readXML(
			"ddm-form-xsd-deserializer-different-default-locale.xml");

		DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(xml);

		Assert.assertEquals(LocaleUtil.BRAZIL, ddmForm.getDefaultLocale());

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		DDMFormField selectDDMFormField = ddmFormFieldsMap.get("Select5979");

		LocalizedValue selectLabel = selectDDMFormField.getLabel();

		Assert.assertEquals(LocaleUtil.BRAZIL, selectLabel.getDefaultLocale());

		DDMFormFieldOptions ddmFormFieldOptions =
			selectDDMFormField.getDDMFormFieldOptions();

		for (String optionValue : ddmFormFieldOptions.getOptionsValues()) {
			LocalizedValue optionLabel = ddmFormFieldOptions.getOptionLabels(
				optionValue);

			Assert.assertEquals(
				LocaleUtil.BRAZIL, optionLabel.getDefaultLocale());
		}
	}

}