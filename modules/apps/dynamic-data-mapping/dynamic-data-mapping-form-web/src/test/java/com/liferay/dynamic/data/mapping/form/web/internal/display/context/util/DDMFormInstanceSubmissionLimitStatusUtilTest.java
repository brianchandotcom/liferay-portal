/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.display.context.util;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Carolina Barbosa
 */
public class DDMFormInstanceSubmissionLimitStatusUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testIsLimitToOneSubmissionPerUser() throws Exception {
		Assert.assertFalse(
			DDMFormInstanceSubmissionLimitStatusUtil.
				isLimitToOneSubmissionPerUser(
					_mockDDMFormInstance(_mockDDMFormInstanceSettings(false))));
		Assert.assertTrue(
			DDMFormInstanceSubmissionLimitStatusUtil.
				isLimitToOneSubmissionPerUser(
					_mockDDMFormInstance(_mockDDMFormInstanceSettings(true))));
	}

	@Test
	public void testIsSubmissionLimitReached() throws Exception {
		Assert.assertTrue(
			DDMFormInstanceSubmissionLimitStatusUtil.isSubmissionLimitReached(
				_mockDDMFormInstance(_mockDDMFormInstanceSettings(true)),
				_mockDDMFormInstanceRecordVersionLocalService(
					Collections.singletonList(
						Mockito.mock(DDMFormInstanceRecordVersion.class))),
				Mockito.mock(User.class)));
	}

	@Test
	public void testIsSubmissionLimitReachedWithGuestUser() throws Exception {
		User user = Mockito.mock(User.class);

		Mockito.when(
			user.isGuestUser()
		).thenReturn(
			true
		);

		Assert.assertFalse(
			DDMFormInstanceSubmissionLimitStatusUtil.isSubmissionLimitReached(
				_mockDDMFormInstance(_mockDDMFormInstanceSettings(true)),
				_mockDDMFormInstanceRecordVersionLocalService(
					Collections.singletonList(
						Mockito.mock(DDMFormInstanceRecordVersion.class))),
				user));
	}

	@Test
	public void testIsSubmissionLimitReachedWithNoRecord() throws Exception {
		Assert.assertFalse(
			DDMFormInstanceSubmissionLimitStatusUtil.isSubmissionLimitReached(
				_mockDDMFormInstance(_mockDDMFormInstanceSettings(true)),
				_mockDDMFormInstanceRecordVersionLocalService(
					Collections.emptyList()),
				Mockito.mock(User.class)));
	}

	@Test
	public void testIsSubmissionLimitReachedWithStatusDraft() throws Exception {
		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			Mockito.mock(DDMFormInstanceRecordVersion.class);

		Mockito.when(
			ddmFormInstanceRecordVersion.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_DRAFT
		);

		Assert.assertFalse(
			DDMFormInstanceSubmissionLimitStatusUtil.isSubmissionLimitReached(
				_mockDDMFormInstance(_mockDDMFormInstanceSettings(true)),
				_mockDDMFormInstanceRecordVersionLocalService(
					Collections.singletonList(ddmFormInstanceRecordVersion)),
				Mockito.mock(User.class)));
	}

	private DDMFormInstance _mockDDMFormInstance(
			DDMFormInstanceSettings ddmFormInstanceSettings)
		throws Exception {

		DDMFormInstance ddmFormInstance = Mockito.mock(DDMFormInstance.class);

		Mockito.when(
			ddmFormInstance.getSettingsModel()
		).thenReturn(
			ddmFormInstanceSettings
		);

		return ddmFormInstance;
	}

	private DDMFormInstanceRecordVersionLocalService
		_mockDDMFormInstanceRecordVersionLocalService(
			List<DDMFormInstanceRecordVersion> ddmFormInstanceRecordVersions) {

		DDMFormInstanceRecordVersionLocalService
			ddmFormInstanceRecordVersionLocalService = Mockito.mock(
				DDMFormInstanceRecordVersionLocalService.class);

		Mockito.when(
			ddmFormInstanceRecordVersionLocalService.
				getFormInstanceRecordVersions(
					Mockito.anyLong(), Mockito.anyLong())
		).thenReturn(
			ddmFormInstanceRecordVersions
		);

		return ddmFormInstanceRecordVersionLocalService;
	}

	private DDMFormInstanceSettings _mockDDMFormInstanceSettings(
		boolean limitToOneSubmissionPerUser) {

		DDMFormInstanceSettings ddmFormInstanceSettings = Mockito.mock(
			DDMFormInstanceSettings.class);

		Mockito.when(
			ddmFormInstanceSettings.limitToOneSubmissionPerUser()
		).thenReturn(
			limitToOneSubmissionPerUser
		);

		return ddmFormInstanceSettings;
	}

}