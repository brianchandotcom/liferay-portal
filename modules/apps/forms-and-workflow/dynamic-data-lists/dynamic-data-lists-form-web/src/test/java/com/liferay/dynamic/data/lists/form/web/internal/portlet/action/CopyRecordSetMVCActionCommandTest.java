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

package com.liferay.dynamic.data.lists.form.web.internal.portlet.action;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetSettings;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRequestParameterRetriever;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.form.values.factory.internal.DDMFormValuesFactoryImpl;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PortalImpl;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.portlet.MockActionRequest;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest(ResourceBundleUtil.class)
@RunWith(PowerMockRunner.class)
public class CopyRecordSetMVCActionCommandTest {

	@Before
	public void setUp() throws Exception {
		setUpActionRequest();
		setUpCopyRecordSetMVCActionCommand();
		setUpLanguageUtil();
		setUpPortalUtil();
		setUpResourceBundleUtil();
	}

	@Test
	public void testCreateRecordSetSettingsDDMFormValues() throws Exception {
		DDLRecordSet recordSet = mock(DDLRecordSet.class);

		String expectedStorageType = StringUtil.randomString();

		mockSaveRecordSetMVCCommandHelperGetStorageType(expectedStorageType);

		_copyRecordSetMVCActionCommand.saveRecordSetMVCCommandHelper =
			_saveRecordSetMVCCommandHelper;

		DDMFormValues recordSetSettingsDDMFormValues =
			_copyRecordSetMVCActionCommand.createRecordSetSettingsDDMFormValues(
				_actionRequest, recordSet);

		DDMForm expectedRecordSetSettingsDDMForm = DDMFormFactory.create(
			DDLRecordSetSettings.class);

		Assert.assertEquals(
			expectedRecordSetSettingsDDMForm,
			recordSetSettingsDDMFormValues.getDDMForm());

		List<DDMFormFieldValue> recordSetSettingsDDMFormFieldValues =
			recordSetSettingsDDMFormValues.getDDMFormFieldValues();

		Assert.assertEquals(
			getDDMFormFieldsSize(expectedRecordSetSettingsDDMForm),
			recordSetSettingsDDMFormFieldValues.size());

		DDMFormFieldValue storageTypeDDMFormFieldValue =
			_copyRecordSetMVCActionCommand.getStorageTypeDDMFormFieldValue(
				recordSetSettingsDDMFormValues);

		Assert.assertNotNull(storageTypeDDMFormFieldValue);

		Value storageTypeDDMFormFieldValueValue =
			storageTypeDDMFormFieldValue.getValue();

		Assert.assertEquals(
			expectedStorageType,
			storageTypeDDMFormFieldValueValue.getString(LocaleUtil.US));
	}

	protected DDMFormValuesFactory createDDMFormValuesFactory()
		throws Exception {

		DDMFormValuesFactory ddmFormValuesFactory =
			new DDMFormValuesFactoryImpl();

		mockStatic(ServiceTrackerMapFactory.class);

		when(
			_serviceTrackerMap.containsKey(Matchers.anyString())
		).thenReturn(
			false
		);

		Field field = ReflectionUtil.getDeclaredField(
			ddmFormValuesFactory.getClass(), "_serviceTrackerMap");

		field.set(ddmFormValuesFactory, _serviceTrackerMap);

		return ddmFormValuesFactory;
	}

	protected int getDDMFormFieldsSize(DDMForm ddmForm) {
		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		return ddmFormFields.size();
	}

	protected void mockSaveRecordSetMVCCommandHelperGetStorageType(
			String returnStorageType)
		throws Exception {

		when(
			_saveRecordSetMVCCommandHelper.getStorageType(
				Matchers.any(DDMFormValues.class))
		).thenReturn(
			returnStorageType
		);
	}

	protected void setUpActionRequest() {
		_actionRequest = new MockActionRequest();

		_actionRequest.setAttribute(
			PortletServlet.PORTLET_SERVLET_REQUEST,
			new MockHttpServletRequest());
	}

	protected void setUpCopyRecordSetMVCActionCommand() throws Exception {
		_copyRecordSetMVCActionCommand = new CopyRecordSetMVCActionCommand();

		_copyRecordSetMVCActionCommand.ddmFormValuesFactory =
			createDDMFormValuesFactory();
	}

	protected void setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = mock(Language.class);

		languageUtil.setLanguage(language);
	}

	protected void setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());
	}

	protected void setUpResourceBundleUtil() {
		mockStatic(ResourceBundleUtil.class);

		when(
			ResourceBundleUtil.getBundle(
				Matchers.anyString(), Matchers.any(Locale.class),
				Matchers.any(ClassLoader.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	private ActionRequest _actionRequest;
	private CopyRecordSetMVCActionCommand _copyRecordSetMVCActionCommand;

	@Mock
	private SaveRecordSetMVCCommandHelper _saveRecordSetMVCCommandHelper;

	@Mock
	private ServiceTrackerMap
		<String, DDMFormFieldValueRequestParameterRetriever> _serviceTrackerMap;

}