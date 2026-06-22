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
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureImpl;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.junit.After;
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

	@After
	public void tearDown() {
		_ddmFormInstanceRecordsCounts.clear();
	}

	@Test
	public void testGuestUserAnsweringForFifthTime() throws Exception {
		_addUploadField(true);

		_addDDMFormInstanceRecordsCount(
			_MAXIMUM_SUBMISSIONS - 1, _IP_ADDRESS_1);

		Assert.assertFalse(
			DDMFormGuestUploadFieldUtil.isMaximumSubmissionLimitReached(
				_mockDDMFormInstance(),
				_mockHttpServletRequest(_IP_ADDRESS_1, false),
				_MAXIMUM_SUBMISSIONS));
	}

	@Test
	public void testGuestUserAnsweringForSixthTime() throws Exception {
		_addUploadField(true);

		_addDDMFormInstanceRecordsCount(_MAXIMUM_SUBMISSIONS, _IP_ADDRESS_1);

		Assert.assertTrue(
			DDMFormGuestUploadFieldUtil.isMaximumSubmissionLimitReached(
				_mockDDMFormInstance(),
				_mockHttpServletRequest(_IP_ADDRESS_1, false),
				_MAXIMUM_SUBMISSIONS));
	}

	@Test
	public void testGuestUserAnsweringWithDifferentIPAddresses()
		throws Exception {

		_addUploadField(true);

		_addDDMFormInstanceRecordsCount(1, _IP_ADDRESS_1);
		_addDDMFormInstanceRecordsCount(
			_MAXIMUM_SUBMISSIONS - 1, _IP_ADDRESS_2);

		Assert.assertFalse(
			DDMFormGuestUploadFieldUtil.isMaximumSubmissionLimitReached(
				_mockDDMFormInstance(),
				_mockHttpServletRequest(_IP_ADDRESS_1, false),
				_MAXIMUM_SUBMISSIONS));
		Assert.assertFalse(
			DDMFormGuestUploadFieldUtil.isMaximumSubmissionLimitReached(
				_mockDDMFormInstance(),
				_mockHttpServletRequest(_IP_ADDRESS_2, false),
				_MAXIMUM_SUBMISSIONS));

		_addDDMFormInstanceRecordsCount(1, _IP_ADDRESS_2);

		Assert.assertFalse(
			DDMFormGuestUploadFieldUtil.isMaximumSubmissionLimitReached(
				_mockDDMFormInstance(),
				_mockHttpServletRequest(_IP_ADDRESS_1, false),
				_MAXIMUM_SUBMISSIONS));
		Assert.assertTrue(
			DDMFormGuestUploadFieldUtil.isMaximumSubmissionLimitReached(
				_mockDDMFormInstance(),
				_mockHttpServletRequest(_IP_ADDRESS_2, false),
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
				_mockDDMFormInstance(),
				_mockHttpServletRequest(_IP_ADDRESS_1, false),
				_MAXIMUM_SUBMISSIONS));
	}

	@Test
	public void testMaxLimitWithSignedInUser() throws Exception {
		Assert.assertFalse(
			DDMFormGuestUploadFieldUtil.isMaximumSubmissionLimitReached(
				_mockDDMFormInstance(),
				_mockHttpServletRequest(_IP_ADDRESS_1, true),
				_MAXIMUM_SUBMISSIONS));
	}

	protected static final JSONFactory jsonFactory = new JSONFactoryImpl();

	private void _addDDMFormInstanceRecordsCount(int count, String ipAddress) {
		int ddmFormInstanceRecordsCount = _ddmFormInstanceRecordsCounts.merge(
			ipAddress, count, Integer::sum);

		Mockito.when(
			_ddmFormInstanceRecordLocalService.getFormInstanceRecordsCount(
				_DDM_FORM_INSTANCE_ID, ipAddress)
		).thenReturn(
			ddmFormInstanceRecordsCount
		);
	}

	private void _addUploadField(boolean allowGuestUsers) {
		DDMFormField ddmFormField = new DDMFormField(
			"fieldName", "document_library");

		ddmFormField.setProperty("allowGuestUsers", allowGuestUsers);

		_ddmForm.addDDMFormField(ddmFormField);
	}

	private DDMFormInstance _mockDDMFormInstance() throws Exception {
		DDMFormInstance ddmFormInstance = Mockito.mock(DDMFormInstance.class);

		Mockito.when(
			ddmFormInstance.getFormInstanceId()
		).thenReturn(
			_DDM_FORM_INSTANCE_ID
		);

		DDMStructure ddmStructure = new DDMStructureImpl();

		ddmStructure.setDDMForm(_ddmForm);

		Mockito.when(
			ddmFormInstance.getStructure()
		).thenReturn(
			ddmStructure
		);

		return ddmFormInstance;
	}

	private HttpServletRequest _mockHttpServletRequest(
		String remoteAddr, boolean signedIn) {

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.isSignedIn()
		).thenReturn(
			signedIn
		);

		Mockito.when(
			(ThemeDisplay)httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			themeDisplay
		);

		Mockito.when(
			httpServletRequest.getRemoteAddr()
		).thenReturn(
			remoteAddr
		);

		return httpServletRequest;
	}

	private static final long _DDM_FORM_INSTANCE_ID =
		RandomTestUtil.randomLong();

	private static final String _IP_ADDRESS_1 = RandomTestUtil.randomString();

	private static final String _IP_ADDRESS_2 = RandomTestUtil.randomString();

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

	private final Map<String, Integer> _ddmFormInstanceRecordsCounts =
		new HashMap<>();

}