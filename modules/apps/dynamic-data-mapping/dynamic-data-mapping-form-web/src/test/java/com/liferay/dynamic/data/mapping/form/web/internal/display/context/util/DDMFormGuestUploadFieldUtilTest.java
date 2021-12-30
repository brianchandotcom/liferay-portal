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

package com.liferay.dynamic.data.mapping.form.web.internal.display.context.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureImpl;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Carolina Barbosa
 */
@RunWith(PowerMockRunner.class)
public class DDMFormGuestUploadFieldUtilTest {

	@Before
	public void setUp() throws Exception {
		_setUpDDMForm();
		_setUpDDMFormInstanceRecordLocalService();
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
			_ddmFormGuestUploadFieldUtil.isMaximumSubmissionLimitReached(
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
			_ddmFormGuestUploadFieldUtil.isMaximumSubmissionLimitReached(
				_mockDDMFormInstance(), _mockHttpServletRequest(false),
				_MAXIMUM_SUBMISSIONS));
	}

	@Test
	public void testHasGuestUploadFieldAllowedForGuests() throws Exception {
		_addUploadField(true);

		Assert.assertTrue(
			_ddmFormGuestUploadFieldUtil.hasGuestUploadField(
				_mockDDMFormInstance()));
	}

	@Test
	public void testHasGuestUploadFieldNotAllowedForGuests() throws Exception {
		_addUploadField(false);

		Assert.assertFalse(
			_ddmFormGuestUploadFieldUtil.hasGuestUploadField(
				_mockDDMFormInstance()));
	}

	@Test
	public void testHasGuestUploadFieldWithNoUploadField() throws Exception {
		Assert.assertFalse(
			_ddmFormGuestUploadFieldUtil.hasGuestUploadField(
				_mockDDMFormInstance()));
	}

	@Test
	public void testMaxLimitWithGuestUserNotAllowed() throws Exception {
		_addUploadField(false);

		Assert.assertFalse(
			_ddmFormGuestUploadFieldUtil.isMaximumSubmissionLimitReached(
				_mockDDMFormInstance(), _mockHttpServletRequest(false),
				_MAXIMUM_SUBMISSIONS));
	}

	@Test
	public void testMaxLimitWithSignedInUser() throws Exception {
		Assert.assertFalse(
			_ddmFormGuestUploadFieldUtil.isMaximumSubmissionLimitReached(
				_mockDDMFormInstance(), _mockHttpServletRequest(true),
				_MAXIMUM_SUBMISSIONS));
	}

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

		Mockito.when(
			ddmFormInstance.getStructure()
		).thenReturn(
			_createDDMStructure()
		);

		return ddmFormInstance;
	}

	private void _mockDDMFormInstanceLocalService(
		List<DDMFormInstanceRecord> ddmFormInstanceRecords) {

		Mockito.when(
			_ddmFormInstanceRecordLocalService.getFormInstanceRecords(
				Matchers.eq(_DDM_FORM_INSTANCE_ID),
				Matchers.eq(WorkflowConstants.STATUS_ANY),
				Matchers.eq(QueryUtil.ALL_POS), Matchers.eq(QueryUtil.ALL_POS),
				Matchers.eq(null))
		).thenReturn(
			ddmFormInstanceRecords
		);
	}

	private DDMFormInstanceRecord _mockDDMFormInstanceRecord()
		throws Exception {

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

	private void _setUpDDMForm() {
		_ddmForm = DDMFormTestUtil.createDDMForm();
	}

	private void _setUpDDMFormInstanceRecordLocalService() throws Exception {
		MemberMatcher.field(
			DDMFormGuestUploadFieldUtil.class,
			"_ddmFormInstanceRecordLocalService"
		).set(
			_ddmFormGuestUploadFieldUtil, _ddmFormInstanceRecordLocalService
		);
	}

	private static final long _DDM_FORM_INSTANCE_ID =
		RandomTestUtil.randomLong();

	private static final String _IP_ADDRESS = RandomTestUtil.randomString();

	private static final int _MAXIMUM_SUBMISSIONS = 5;

	private DDMForm _ddmForm;
	private final DDMFormGuestUploadFieldUtil _ddmFormGuestUploadFieldUtil =
		new DDMFormGuestUploadFieldUtil();

	@Mock
	private DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

}