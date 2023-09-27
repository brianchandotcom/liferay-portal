/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.locked.layouts.web.internal.scheduler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.manager.LayoutLockManager;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.lock.model.Lock;
import com.liferay.portal.lock.service.LockLocalService;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockActionRequest;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@FeatureFlags("LPS-180328")
@RunWith(Arquillian.class)
public class UnlockLayoutsSchedulerJobConfigurationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_lock = _getLock();
	}

	@Test
	public void testUnlockLayouts() throws Exception {
		_testUnlockLayouts(false, _lock, 0);
		_testUnlockLayouts(true, _lock, _LOCK_MINUTE_ADDITION + 1);
		_testUnlockLayouts(true, null, _LOCK_MINUTE_ADDITION - 1);
	}

	private Layout _getDraftLayout() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Layout draftLayout = layout.fetchDraftLayout();

		draftLayout.setStatus(WorkflowConstants.STATUS_DRAFT);

		return _layoutLocalService.updateLayout(draftLayout);
	}

	private Lock _getLock() throws Exception {
		Layout draftLayout = _getDraftLayout();

		_lockLayout(draftLayout, TestPropsValues.getUser());

		Lock lock = _lockLocalService.fetchLock(
			Layout.class.getName(), draftLayout.getPlid());

		Assert.assertNotNull(lock);

		Date createDate = new Date(
			System.currentTimeMillis() - (_LOCK_MINUTE_ADDITION * Time.MINUTE));

		lock.setCreateDate(createDate);

		return _lockLocalService.updateLock(lock);
	}

	private void _lockLayout(Layout layout, User user) throws Exception {
		MockActionRequest mockActionRequest = new MockActionRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLayout(layout);
		themeDisplay.setUser(user);

		mockActionRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		_layoutLockManager.getLock(mockActionRequest);
	}

	private void _testUnlockLayouts(
			boolean allowAutomaticUnlockingProcess, Lock expectedLock,
			int timeWithoutAutosave)
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.layout.locked.layouts.web.internal." +
						"configuration.LockedLayoutsConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"allowAutomaticUnlockingProcess",
						allowAutomaticUnlockingProcess
					).put(
						"timeWithoutAutosave", timeWithoutAutosave
					).build())) {

			UnsafeRunnable<Exception> unsafeRunnable =
				_schedulerJobConfiguration.getJobExecutorUnsafeRunnable();

			unsafeRunnable.run();

			Lock lock = _lockLocalService.fetchLock(
				_lock.getClassName(), _lock.getKey());

			Assert.assertEquals(expectedLock, lock);
		}
	}

	private static final int _LOCK_MINUTE_ADDITION = 10;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutLockManager _layoutLockManager;

	private Lock _lock;

	@Inject
	private LockLocalService _lockLocalService;

	@Inject
	private LockManager _lockManager;

	@Inject(
		filter = "component.name=com.liferay.layout.locked.layouts.web.internal.scheduler.UnlockLayoutsSchedulerJobConfiguration"
	)
	private SchedulerJobConfiguration _schedulerJobConfiguration;

}