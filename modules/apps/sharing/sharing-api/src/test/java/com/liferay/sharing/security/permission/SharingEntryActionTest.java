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

package com.liferay.sharing.security.permission;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sergio González
 */
public class SharingEntryActionTest {

	@Test
	public void testIsSupportedActionIdWithAddDiscussionActionId()
		throws Exception {

		Assert.assertTrue(
			SharingEntryAction.isSupportedActionId(
				SharingEntryAction.ADD_DISCUSSION.getActionId()));
	}

	@Test
	public void testIsSupportedActionIdWithUpdateActionId() throws Exception {
		Assert.assertTrue(
			SharingEntryAction.isSupportedActionId(
				SharingEntryAction.UPDATE.getActionId()));
	}

	@Test
	public void testIsSupportedActionIdWithViewActionId() throws Exception {
		Assert.assertTrue(
			SharingEntryAction.isSupportedActionId(
				SharingEntryAction.VIEW.getActionId()));
	}

	@Test
	public void testIsSupportedActionIdWithWrongActionId() throws Exception {
		Assert.assertFalse(
			SharingEntryAction.isSupportedActionId("UNSUPPORTED"));
	}

	@Test
	public void testParseFromActionIdWithAddDiscussionActionId()
		throws Exception {

		SharingEntryAction addDiscussionSharingEntryAction =
			SharingEntryAction.ADD_DISCUSSION;

		Assert.assertEquals(
			addDiscussionSharingEntryAction,
			SharingEntryAction.parseFromActionId(
				addDiscussionSharingEntryAction.getActionId()));
	}

	@Test
	public void testParseFromActionIdWithUpdateActionId() throws Exception {
		SharingEntryAction updateSharingEntryAction = SharingEntryAction.UPDATE;

		Assert.assertEquals(
			updateSharingEntryAction,
			SharingEntryAction.parseFromActionId(
				updateSharingEntryAction.getActionId()));
	}

	@Test
	public void testParseFromActionIdWithViewActionId() throws Exception {
		SharingEntryAction viewSharingEntryAction = SharingEntryAction.VIEW;

		Assert.assertEquals(
			viewSharingEntryAction,
			SharingEntryAction.parseFromActionId(
				viewSharingEntryAction.getActionId()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseFromActionIdWithWrongActionId() throws Exception {
		SharingEntryAction.parseFromActionId("UNSUPPORTED");
	}

	@Test
	public void testParseFromBitwiseValueWithAddDiscussionBitwiseValue()
		throws Exception {

		SharingEntryAction addDiscussionSharingEntryAction =
			SharingEntryAction.ADD_DISCUSSION;

		Assert.assertEquals(
			addDiscussionSharingEntryAction,
			SharingEntryAction.parseFromBitwiseValue(
				addDiscussionSharingEntryAction.getBitwiseValue()));
	}

	@Test
	public void testParseFromBitwiseValueWithUpdateBitwiseValue()
		throws Exception {

		SharingEntryAction updateSharingEntryAction = SharingEntryAction.UPDATE;

		Assert.assertEquals(
			updateSharingEntryAction,
			SharingEntryAction.parseFromBitwiseValue(
				updateSharingEntryAction.getBitwiseValue()));
	}

	@Test
	public void testParseFromBitwiseValueWithViewBitwiseValue()
		throws Exception {

		SharingEntryAction viewSharingEntryAction = SharingEntryAction.VIEW;

		Assert.assertEquals(
			viewSharingEntryAction,
			SharingEntryAction.parseFromBitwiseValue(
				viewSharingEntryAction.getBitwiseValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseFromBitwiseValueWithWrongBitwiseValue()
		throws Exception {

		SharingEntryAction.parseFromActionId("8");
	}

}