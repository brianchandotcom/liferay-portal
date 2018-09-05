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

package com.liferay.sharing.constants;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sergio González
 */
public class SharingEntryActionKeyTest {

	@Test
	public void testIsSupportedActionIdWithAddDiscussionActionId()
		throws Exception {

		Assert.assertTrue(
			SharingEntryActionKey.isSupportedActionId(
				SharingEntryActionKey.ADD_DISCUSSION.getActionId()));
	}

	@Test
	public void testIsSupportedActionIdWithUpdateActionId() throws Exception {
		Assert.assertTrue(
			SharingEntryActionKey.isSupportedActionId(
				SharingEntryActionKey.UPDATE.getActionId()));
	}

	@Test
	public void testIsSupportedActionIdWithViewActionId() throws Exception {
		Assert.assertTrue(
			SharingEntryActionKey.isSupportedActionId(
				SharingEntryActionKey.VIEW.getActionId()));
	}

	@Test
	public void testIsSupportedActionIdWithWrongActionId() throws Exception {
		Assert.assertFalse(
			SharingEntryActionKey.isSupportedActionId("UNSUPPORTED"));
	}

	@Test
	public void testParseFromActionIdWithAddDiscussionActionId()
		throws Exception {

		SharingEntryActionKey addDiscussionSharingEntryActionKey =
			SharingEntryActionKey.ADD_DISCUSSION;

		Assert.assertEquals(
			addDiscussionSharingEntryActionKey,
			SharingEntryActionKey.parseFromActionId(
				addDiscussionSharingEntryActionKey.getActionId()));
	}

	@Test
	public void testParseFromActionIdWithUpdateActionId() throws Exception {
		SharingEntryActionKey updateSharingEntryActionKey =
			SharingEntryActionKey.UPDATE;

		Assert.assertEquals(
			updateSharingEntryActionKey,
			SharingEntryActionKey.parseFromActionId(
				updateSharingEntryActionKey.getActionId()));
	}

	@Test
	public void testParseFromActionIdWithViewActionId() throws Exception {
		SharingEntryActionKey viewSharingEntryActionKey =
			SharingEntryActionKey.VIEW;

		Assert.assertEquals(
			viewSharingEntryActionKey,
			SharingEntryActionKey.parseFromActionId(
				viewSharingEntryActionKey.getActionId()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseFromActionIdWithWrongActionId() throws Exception {
		SharingEntryActionKey.parseFromActionId("UNSUPPORTED");
	}

	@Test
	public void testParseFromBitwiseValueWithAddDiscussionBitwiseValue()
		throws Exception {

		SharingEntryActionKey addDiscussionSharingEntryActionKey =
			SharingEntryActionKey.ADD_DISCUSSION;

		Assert.assertEquals(
			addDiscussionSharingEntryActionKey,
			SharingEntryActionKey.parseFromBitwiseValue(
				addDiscussionSharingEntryActionKey.getBitwiseValue()));
	}

	@Test
	public void testParseFromBitwiseValueWithUpdateBitwiseValue()
		throws Exception {

		SharingEntryActionKey updateSharingEntryActionKey =
			SharingEntryActionKey.UPDATE;

		Assert.assertEquals(
			updateSharingEntryActionKey,
			SharingEntryActionKey.parseFromBitwiseValue(
				updateSharingEntryActionKey.getBitwiseValue()));
	}

	@Test
	public void testParseFromBitwiseValueWithViewBitwiseValue()
		throws Exception {

		SharingEntryActionKey viewSharingEntryActionKey =
			SharingEntryActionKey.VIEW;

		Assert.assertEquals(
			viewSharingEntryActionKey,
			SharingEntryActionKey.parseFromBitwiseValue(
				viewSharingEntryActionKey.getBitwiseValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParseFromBitwiseValueWithWrongBitwiseValue()
		throws Exception {

		SharingEntryActionKey.parseFromActionId("8");
	}

}