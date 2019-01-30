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

package com.liferay.change.tracking.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.CTEngineManager;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
public class CTProcessLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		if (_ctEngineManager.isChangeTrackingEnabled(
				TestPropsValues.getCompanyId())) {

			_originallyEnabled = true;

			_ctEngineManager.disableChangeTracking(
				TestPropsValues.getCompanyId());
		}
	}

	@After
	public void tearDown() throws Exception {
		if (_originallyEnabled) {
			_ctEngineManager.enableChangeTracking(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId());
		}
		else {
			_ctEngineManager.disableChangeTracking(
				TestPropsValues.getCompanyId());
		}
	}

	@Test
	public void testAddCTProcess() throws Exception {
		_ctEngineManager.enableChangeTracking(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId());

		_ctCollection = _ctCollectionLocalService.addCTCollection(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new ServiceContext());

		_ctEntryLocalService.addCTEntry(
			TestPropsValues.getUserId(), _portal.getClassNameId(CTEntry.class),
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			_ctCollection.getCtCollectionId(),
			CTConstants.CT_CHANGE_TYPE_ADDITION, new ServiceContext());

		CTProcess ctProcess = _ctProcessLocalService.addCTProcess(
			TestPropsValues.getUserId(), _ctCollection.getCtCollectionId(),
			new ServiceContext());

		Assert.assertNotNull(ctProcess);
		Assert.assertEquals(
			_ctCollection.getCtCollectionId(), ctProcess.getCtCollectionId());
		Assert.assertEquals(TestPropsValues.getUserId(), ctProcess.getUserId());
		Assert.assertNotEquals(0, ctProcess.getBackgroundTaskId());
		Assert.assertEquals(
			BackgroundTaskConstants.STATUS_SUCCESSFUL, ctProcess.getStatus());

		BackgroundTask backgroundTask =
			BackgroundTaskManagerUtil.getBackgroundTask(
				ctProcess.getBackgroundTaskId());

		Assert.assertNotNull(backgroundTask);
		Assert.assertEquals(1, backgroundTask.getAttachmentsFileEntriesCount());
	}

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private CTEngineManager _ctEngineManager;

	@Inject
	private CTEntryLocalService _ctEntryLocalService;

	@Inject
	private CTProcessLocalService _ctProcessLocalService;

	private boolean _originallyEnabled;

	@Inject
	private Portal _portal;

}