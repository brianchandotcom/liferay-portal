/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.display.context.util;

import com.liferay.dynamic.data.mapping.internal.io.DDMFormJSONDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureImpl;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Carolina Barbosa
 */
public class DDMFormGuestUploadFieldUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		Mockito.when(
			FrameworkUtil.getBundle(Mockito.any())
		).thenReturn(
			bundleContext.getBundle()
		);

		_ddmForm = DDMFormTestUtil.createDDMForm();

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("ddm.form.deserializer.type", "json");

		_ddmFormDeserializerServiceRegistration = bundleContext.registerService(
			DDMFormDeserializer.class, _ddmFormDeserializer, properties);

		_ddmFormInstanceRecordLocalServiceServiceRegistration =
			bundleContext.registerService(
				DDMFormInstanceRecordLocalService.class,
				_ddmFormInstanceRecordLocalService, null);
	}

	@AfterClass
	public static void tearDownClass() {
		_ddmFormDeserializerServiceRegistration.unregister();

		_ddmFormInstanceRecordLocalServiceServiceRegistration.unregister();

		_frameworkUtilMockedStatic.close();
	}

	@Test
	public void testGuestUserAnsweringForFifthTime() throws Exception {
		_addUploadField(true);

		List<DDMFormInstanceRecord> ddmFormInstanceRecords = new ArrayList<>();

		for (int i = 0; i < (_MAXIMUM_SUBMISSIONS - 1); i++) {
			ddmFormInstanceRecords.add(_mockDDMFormInstanceRecord());
		}

		_mockDDMFormInstanceLocalService(ddmFormInstanceRecords);

		Assert.assertFalse(
			DDMFormGuestUploadFieldUtil.isMaximumSubmissionLimitReached(
				_mockDDMFormInstance(), _mockHttpServletRequest(false),
				_MAXIMUM_SUBMISSIONS));
	}

	@Test
	public void testGuestUserAnsweringForSixthTime() throws Exception {
		_addUploadField(true);

		List<DDMFormInstanceRecord> ddmFormInstanceRecords = new ArrayList<>();

		for (int i = 0; i < _MAXIMUM_SUBMISSIONS; i++) {
			ddmFormInstanceRecords.add(_mockDDMFormInstanceRecord());
		}

		_mockDDMFormInstanceLocalService(ddmFormInstanceRecords);

		Assert.assertTrue(
			DDMFormGuestUploadFieldUtil.isMaximumSubmissionLimitReached(
				_mockDDMFormInstance(), _mockHttpServletRequest(false),
				_MAXIMUM_SUBMISSIONS));
	}

	@Test
	public void testHasGuestUploadFieldAllowedForGuests() throws Exception {
		_addUploadField(true);

		Assert.assertTrue(
			DDMFormGuestUploadFieldUtil.hasGuestUploadField(
				_mockDDMFormInstance()));
	}

	@Test
	public void testHasGuestUploadFieldNotAllowedForGuests() throws Exception {
		_addUploadField(false);

		Assert.assertFalse(
			DDMFormGuestUploadFieldUtil.hasGuestUploadField(
				_mockDDMFormInstance()));
	}

	@Test
	public void testHasGuestUploadFieldWithNoUploadField() throws Exception {
		Assert.assertFalse(
			DDMFormGuestUploadFieldUtil.hasGuestUploadField(
				_mockDDMFormInstance()));
	}

	@Test
	public void testMaxLimitWithGuestUserNotAllowed() throws Exception {
		_addUploadField(false);

		Assert.assertFalse(
			DDMFormGuestUploadFieldUtil.isMaximumSubmissionLimitReached(
				_mockDDMFormInstance(), _mockHttpServletRequest(false),
				_MAXIMUM_SUBMISSIONS));
	}

	@Test
	public void testMaxLimitWithSignedInUser() throws Exception {
		Assert.assertFalse(
			DDMFormGuestUploadFieldUtil.isMaximumSubmissionLimitReached(
				_mockDDMFormInstance(), _mockHttpServletRequest(true),
				_MAXIMUM_SUBMISSIONS));
	}

	protected static final JSONFactory jsonFactory = new JSONFactoryImpl();

	private void _addUploadField(boolean allowGuestUsers) {
		DDMFormField ddmFormField = new DDMFormField(
			"fieldName", "document_library");

		ddmFormField.setProperty("allowGuestUsers", allowGuestUsers);

		_ddmForm.addDDMFormField(ddmFormField);
	}

	private DDMStructure _createDDMStructure() {
		DDMStructure ddmStructure = new DDMStructureImpl();

		ddmStructure.setDDMForm(_ddmForm);

		return ddmStructure;
	}

	private DDMFormInstance _mockDDMFormInstance() throws Exception {
		DDMFormInstance ddmFormInstance = Mockito.mock(DDMFormInstance.class);

		Mockito.when(
			ddmFormInstance.getFormInstanceId()
		).thenReturn(
			_DDM_FORM_INSTANCE_ID
		);

		DDMStructure ddmStructure = _createDDMStructure();

		Mockito.when(
			ddmFormInstance.getStructure()
		).thenReturn(
			ddmStructure
		);

		return ddmFormInstance;
	}

	private void _mockDDMFormInstanceLocalService(
		List<DDMFormInstanceRecord> ddmFormInstanceRecords) {

		Mockito.when(
			_ddmFormInstanceRecordLocalService.getFormInstanceRecords(
				Mockito.eq(_DDM_FORM_INSTANCE_ID),
				Mockito.eq(WorkflowConstants.STATUS_ANY),
				Mockito.eq(QueryUtil.ALL_POS), Mockito.eq(QueryUtil.ALL_POS),
				Mockito.eq(null))
		).thenReturn(
			ddmFormInstanceRecords
		);
	}

	private DDMFormInstanceRecord _mockDDMFormInstanceRecord() {
		DDMFormInstanceRecord ddmFormInstanceRecord = Mockito.mock(
			DDMFormInstanceRecord.class);

		Mockito.when(
			ddmFormInstanceRecord.getIpAddress()
		).thenReturn(
			_IP_ADDRESS
		);

		return ddmFormInstanceRecord;
	}

	private HttpServletRequest _mockHttpServletRequest(boolean signedIn) {
		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		ThemeDisplay themeDisplay = _mockThemeDisplay(signedIn);

		Mockito.when(
			(ThemeDisplay)httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		Mockito.when(
			httpServletRequest.getRemoteAddr()
		).thenReturn(
			_IP_ADDRESS
		);

		return httpServletRequest;
	}

	private ThemeDisplay _mockThemeDisplay(boolean signedIn) {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.isSignedIn()
		).thenReturn(
			signedIn
		);

		return themeDisplay;
	}

	private static final long _DDM_FORM_INSTANCE_ID =
		RandomTestUtil.randomLong();

	private static final String _IP_ADDRESS = RandomTestUtil.randomString();

	private static final int _MAXIMUM_SUBMISSIONS = 5;

	private static DDMForm _ddmForm;
	private static final DDMFormDeserializer _ddmFormDeserializer =
		new DDMFormJSONDeserializer();
	private static ServiceRegistration<DDMFormDeserializer>
		_ddmFormDeserializerServiceRegistration;
	private static final DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService = Mockito.mock(
			DDMFormInstanceRecordLocalService.class);
	private static ServiceRegistration<DDMFormInstanceRecordLocalService>
		_ddmFormInstanceRecordLocalServiceServiceRegistration;
	private static final MockedStatic<FrameworkUtil>
		_frameworkUtilMockedStatic = Mockito.mockStatic(FrameworkUtil.class);

}