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

package com.liferay.dynamic.data.mapping.staging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormInstanceTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormStagingTestUtil;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Victor Ware
 */
@RunWith(Arquillian.class)
public class DDMFormInstanceStagingTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		setUpPermissionThreadLocal();
	}

	@Test
	public void testFormCopiedWhenLocalStagingActivated() throws Exception {
		_liveGroup = GroupTestUtil.addGroup();

		DDMFormInstance ddmFormInstance =
			DDMFormInstanceTestUtil.addDDMFormInstance(
				_liveGroup, TestPropsValues.getUserId());

		DDMFormStagingTestUtil.enableLocalStaging(_liveGroup, true);

		_stagingGroup = _liveGroup.getStagingGroup();

		Assert.assertEquals(
			1,
			DDMFormInstanceLocalServiceUtil.getFormInstancesCount(
				_liveGroup.getGroupId()));

		Assert.assertEquals(
			1,
			DDMFormInstanceLocalServiceUtil.getFormInstancesCount(
				_stagingGroup.getGroupId()));

		DDMFormInstanceTestUtil.deleteFormInstance(ddmFormInstance);
	}

	@Test
	public void testPublishFormToRemoteStagingSite() throws Exception {
		_remoteLiveGroup = GroupTestUtil.addGroup();
		_remoteStagingGroup = GroupTestUtil.addGroup();

		DDMFormStagingTestUtil.enableRemoteStaging(
			_remoteLiveGroup, _remoteStagingGroup);

		_remoteLiveGroup = GroupLocalServiceUtil.getGroup(
			_remoteLiveGroup.getGroupId());

		DDMFormInstance ddmFormInstance =
			DDMFormInstanceTestUtil.addDDMFormInstance(
				_remoteStagingGroup, TestPropsValues.getUserId());

		Assert.assertEquals(
			1,
			DDMFormInstanceLocalServiceUtil.getFormInstancesCount(
				_remoteStagingGroup.getGroupId()));
		Assert.assertEquals(
			0,
			DDMFormInstanceLocalServiceUtil.getFormInstancesCount(
				_remoteLiveGroup.getGroupId()));

		Map<String, String[]> parameters =
			ExportImportConfigurationParameterMapFactoryUtil.
				buildFullPublishParameterMap();

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), _remoteStagingGroup.getGroupId(),
			_remoteLiveGroup.getGroupId(), false, parameters);

		Assert.assertEquals(
			1,
			DDMFormInstanceLocalServiceUtil.getFormInstancesCount(
				_remoteStagingGroup.getGroupId()));
		Assert.assertEquals(
			1,
			DDMFormInstanceLocalServiceUtil.getFormInstancesCount(
				_remoteLiveGroup.getGroupId()));

		DDMFormInstanceTestUtil.deleteFormInstance(ddmFormInstance);
	}

	@Test(expected = PrincipalException.MustHavePermission.class)
	public void testUserCannotAddAFormOnLiveSite() throws Exception {
		_liveGroup = GroupTestUtil.addGroup();

		DDMFormStagingTestUtil.enableLocalStaging(_liveGroup, true);

		DDMFormInstanceServiceUtil.addFormInstance(
			_liveGroup.getGroupId(), null, null, null, null, null, null);
	}

	protected void setUpPermissionThreadLocal() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));
	}

	private Group _liveGroup;
	private Group _remoteLiveGroup;

	@DeleteAfterTestRun
	private Group _remoteStagingGroup;

	@DeleteAfterTestRun
	private Group _stagingGroup;

}