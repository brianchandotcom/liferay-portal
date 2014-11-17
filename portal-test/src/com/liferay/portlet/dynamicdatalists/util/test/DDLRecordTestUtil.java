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

package com.liferay.portlet.dynamicdatalists.util.test;

import com.liferay.portal.kernel.test.DependenciesTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue;
import com.liferay.portlet.dynamicdatamapping.model.Value;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * @author Marcellus Tavares
 * @author André de Oliveira
 */
public class DDLRecordTestUtil {

	public static DDMFormValues createDDMFormValues(DDMForm ddmForm) {
		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		Set<Locale> availableLocales = new HashSet<Locale>();

		availableLocales.add(LocaleUtil.US);

		ddmFormValues.setAvailableLocales(availableLocales);
		ddmFormValues.setDefaultLocale(LocaleUtil.US);

		return ddmFormValues;
	}

	public static DDMFormFieldValue createLocalizedTextDDMFormFieldValue(
		String name, String enValue) {

		Value localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.US, enValue);

		return createTextDDMFormFieldValue(name, localizedValue);
	}

	public static DDMFormFieldValue createTextDDMFormFieldValue(
		String name, Value value) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setInstanceId(StringUtil.randomString());
		ddmFormFieldValue.setName(name);
		ddmFormFieldValue.setValue(value);

		return ddmFormFieldValue;
	}

	public static DDMFormFieldValue createUnlocalizedTextDDMFormFieldValue(
		String name, String value) {

		return createTextDDMFormFieldValue(name, new UnlocalizedValue(value));
	}

	public static String getBasePath() {
		return "com/liferay/portlet/dynamicdatalists/dependencies/";
	}

	public static ServiceContext getServiceContext(int workflowAction)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setUserId(TestPropsValues.getUserId());
		serviceContext.setWorkflowAction(workflowAction);

		return serviceContext;
	}

	public static String readText(Class<?> testClass, String fileName)
		throws Exception {

		return DependenciesTestUtil.readText(
			testClass, getBasePath() + fileName);
	}

}