/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.util;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sergio González
 */
public class PortalImplGroupFriendlyURLTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGroupFriendlyURLIndexWithConflictiveLayoutFullURL1() {
		int[] groupFriendlyURLIndex = _portalImpl.getGroupFriendlyURLIndex(
			"/web/guest/web-content-page");

		Assert.assertEquals(4, groupFriendlyURLIndex[0]);
		Assert.assertEquals(10, groupFriendlyURLIndex[1]);
	}

	@Test
	public void testGroupFriendlyURLIndexWithConflictiveLayoutFullURL2() {
		int[] groupFriendlyURLIndex = _portalImpl.getGroupFriendlyURLIndex(
			"/web/guest/group-content-page");

		Assert.assertEquals(4, groupFriendlyURLIndex[0]);
		Assert.assertEquals(10, groupFriendlyURLIndex[1]);
	}

	@Test
	public void testGroupFriendlyURLIndexWithConflictiveLayoutFullURL3() {
		int[] groupFriendlyURLIndex = _portalImpl.getGroupFriendlyURLIndex(
			"/web/guest/user-content-page");

		Assert.assertEquals(4, groupFriendlyURLIndex[0]);
		Assert.assertEquals(10, groupFriendlyURLIndex[1]);
	}

	@Test
	public void testGroupFriendlyURLIndexWithConflictiveLayoutURL1() {
		Assert.assertNull(
			_portalImpl.getGroupFriendlyURLIndex("/web-content-page"));
	}

	@Test
	public void testGroupFriendlyURLIndexWithConflictiveLayoutURL2() {
		Assert.assertNull(
			_portalImpl.getGroupFriendlyURLIndex("/group-content-page"));
	}

	@Test
	public void testGroupFriendlyURLIndexWithConflictiveLayoutURL3() {
		Assert.assertNull(
			_portalImpl.getGroupFriendlyURLIndex("/user-content-page"));
	}

	@Test
	public void testGroupFriendlyURLIndexWithFullLayoutURL() {
		int[] groupFriendlyURLIndex = _portalImpl.getGroupFriendlyURLIndex(
			"/web/guest/home");

		Assert.assertEquals(4, groupFriendlyURLIndex[0]);
		Assert.assertEquals(10, groupFriendlyURLIndex[1]);
	}

	@Test
	public void testGroupFriendlyURLIndexWithLayoutURL() {
		Assert.assertNull(_portalImpl.getGroupFriendlyURLIndex("/home"));
	}

	private final PortalImpl _portalImpl = new PortalImpl();

}