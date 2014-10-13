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

package com.liferay.portal.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sergio González
 */
public class PortalImplGroupFriendlyURLTest {

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
		int[] groupFriendlyURLIndex = _portalImpl.getGroupFriendlyURLIndex(
			"/web-content-page");

		Assert.assertNull(groupFriendlyURLIndex);
	}

	@Test
	public void testGroupFriendlyURLIndexWithConflictiveLayoutURL2() {
		int[] groupFriendlyURLIndex = _portalImpl.getGroupFriendlyURLIndex(
			"/group-content-page");

		Assert.assertNull(groupFriendlyURLIndex);
	}

	@Test
	public void testGroupFriendlyURLIndexWithConflictiveLayoutURL3() {
		int[] groupFriendlyURLIndex = _portalImpl.getGroupFriendlyURLIndex(
			"/user-content-page");

		Assert.assertNull(groupFriendlyURLIndex);
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
		int[] groupFriendlyURLIndex = _portalImpl.getGroupFriendlyURLIndex(
			"/home");

		Assert.assertNull(groupFriendlyURLIndex);
	}

	private final PortalImpl _portalImpl = new PortalImpl();

}