/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.locked.layouts.web.internal.events.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.manager.LayoutLockManager;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class UnlockLayoutsSessionActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testProcessLifecycleEvent() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Layout draftLayout = layout.fetchDraftLayout();

		_layoutLockManager.getLock(draftLayout, TestPropsValues.getUserId());

		Lock lock = _lockManager.fetchLock(
			Layout.class.getName(), draftLayout.getPlid());

		Assert.assertNotNull(lock);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.USER, TestPropsValues.getUser());

		MockHttpSession mockHttpSession = new MockHttpSession();

		mockHttpSession.setAttribute(
			WebKeys.USER_ID, TestPropsValues.getUserId());

		_lifecycleAction.processLifecycleEvent(
			new LifecycleEvent(
				null, mockHttpServletRequest, new MockHttpServletResponse(),
				mockHttpSession));

		lock = _lockManager.fetchLock(
			Layout.class.getName(), draftLayout.getPlid());

		Assert.assertNull(lock);
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLockManager _layoutLockManager;

	@Inject(
		filter = "component.name=com.liferay.layout.locked.layouts.web.internal.events.UnlockLayoutsSessionAction"
	)
	private LifecycleAction _lifecycleAction;

	@Inject
	private LockManager _lockManager;

}