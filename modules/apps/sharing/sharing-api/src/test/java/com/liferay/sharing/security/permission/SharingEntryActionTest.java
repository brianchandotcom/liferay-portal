/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.security.permission;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sergio González
 */
public class SharingEntryActionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetSharingEntryActionsInABitwiseValue() throws Exception {
		_assertEquals(
			Collections.emptyList(),
			SharingEntryAction.getSharingEntryActions(0));

		_assertEquals(
			Arrays.asList(SharingEntryAction.VIEW),
			SharingEntryAction.getSharingEntryActions(
				SharingEntryAction.VIEW.getBitwiseValue()));

		_assertEquals(
			Arrays.asList(SharingEntryAction.VIEW, SharingEntryAction.UPDATE),
			SharingEntryAction.getSharingEntryActions(
				SharingEntryAction.VIEW.getBitwiseValue() |
				SharingEntryAction.UPDATE.getBitwiseValue()));

		_assertEquals(
			Arrays.asList(
				SharingEntryAction.VIEW, SharingEntryAction.UPDATE,
				SharingEntryAction.ADD_DISCUSSION),
			SharingEntryAction.getSharingEntryActions(
				SharingEntryAction.VIEW.getBitwiseValue() |
				SharingEntryAction.UPDATE.getBitwiseValue() |
				SharingEntryAction.ADD_DISCUSSION.getBitwiseValue()));
	}

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

	private <T> void _assertEquals(
		Collection<T> collection1, Collection<T> collection2) {

		Assert.assertEquals(
			new HashSet<>(collection1), new HashSet<>(collection2));
	}

}