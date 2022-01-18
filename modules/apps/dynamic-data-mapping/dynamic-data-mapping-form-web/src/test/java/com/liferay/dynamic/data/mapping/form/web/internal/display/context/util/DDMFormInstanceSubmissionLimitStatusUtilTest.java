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

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Carolina Barbosa
 */
@RunWith(PowerMockRunner.class)
public class DDMFormInstanceSubmissionLimitStatusUtilTest extends PowerMockito {

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
						mock(DDMFormInstanceRecordVersion.class))),
				mock(User.class)));
	}

	@Test
	public void testIsSubmissionLimitReachedWithDefaultUser() throws Exception {
		User user = mock(User.class);

		when(
			user.isDefaultUser()
		).thenReturn(
			true
		);

		Assert.assertFalse(
			DDMFormInstanceSubmissionLimitStatusUtil.isSubmissionLimitReached(
				_mockDDMFormInstance(_mockDDMFormInstanceSettings(true)),
				_mockDDMFormInstanceRecordVersionLocalService(
					Collections.singletonList(
						mock(DDMFormInstanceRecordVersion.class))),
				user));
	}

	@Test
	public void testIsSubmissionLimitReachedWithNoRecord() throws Exception {
		Assert.assertFalse(
			DDMFormInstanceSubmissionLimitStatusUtil.isSubmissionLimitReached(
				_mockDDMFormInstance(_mockDDMFormInstanceSettings(true)),
				_mockDDMFormInstanceRecordVersionLocalService(
					Collections.emptyList()),
				mock(User.class)));
	}

	@Test
	public void testIsSubmissionLimitReachedWithStatusDraft() throws Exception {
		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion = mock(
			DDMFormInstanceRecordVersion.class);

		when(
			ddmFormInstanceRecordVersion.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_DRAFT
		);

		Assert.assertFalse(
			DDMFormInstanceSubmissionLimitStatusUtil.isSubmissionLimitReached(
				_mockDDMFormInstance(_mockDDMFormInstanceSettings(true)),
				_mockDDMFormInstanceRecordVersionLocalService(
					Collections.singletonList(ddmFormInstanceRecordVersion)),
				mock(User.class)));
	}

	private DDMFormInstance _mockDDMFormInstance(
			DDMFormInstanceSettings ddmFormInstanceSettings)
		throws Exception {

		DDMFormInstance ddmFormInstance = mock(DDMFormInstance.class);

		when(
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
			ddmFormInstanceRecordVersionLocalService = mock(
				DDMFormInstanceRecordVersionLocalService.class);

		when(
			ddmFormInstanceRecordVersionLocalService.
				getFormInstanceRecordVersions(
					Matchers.anyLong(), Matchers.anyLong())
		).thenReturn(
			ddmFormInstanceRecordVersions
		);

		return ddmFormInstanceRecordVersionLocalService;
	}

	private DDMFormInstanceSettings _mockDDMFormInstanceSettings(
		boolean limitToOneSubmissionPerUser) {

		DDMFormInstanceSettings ddmFormInstanceSettings = mock(
			DDMFormInstanceSettings.class);

		when(
			ddmFormInstanceSettings.limitToOneSubmissionPerUser()
		).thenReturn(
			limitToOneSubmissionPerUser
		);

		return ddmFormInstanceSettings;
	}

}