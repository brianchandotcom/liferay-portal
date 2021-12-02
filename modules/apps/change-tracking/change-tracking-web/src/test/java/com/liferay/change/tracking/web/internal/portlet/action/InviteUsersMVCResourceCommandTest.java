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

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.change.tracking.web.internal.constants.PublicationRoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Samuel Trong Tran
 */
public class InviteUsersMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testEditorRoleModelResourceActions() {
		String[] modelResourceActions = _getModelResourceActions(
			PublicationRoleConstants.ROLE_EDITOR);

		Assert.assertFalse(
			ArrayUtil.contains(modelResourceActions, ActionKeys.PERMISSIONS));
		Assert.assertTrue(
			ArrayUtil.contains(modelResourceActions, ActionKeys.UPDATE));
		Assert.assertTrue(
			ArrayUtil.contains(modelResourceActions, ActionKeys.VIEW));
		Assert.assertFalse(
			ArrayUtil.contains(modelResourceActions, CTActionKeys.PUBLISH));
	}

	@Test
	public void testInviterRoleModelResourceActions() {
		String[] modelResourceActions = _getModelResourceActions(
			PublicationRoleConstants.ROLE_ADMIN);

		Assert.assertTrue(
			ArrayUtil.contains(modelResourceActions, ActionKeys.PERMISSIONS));
		Assert.assertTrue(
			ArrayUtil.contains(modelResourceActions, ActionKeys.UPDATE));
		Assert.assertTrue(
			ArrayUtil.contains(modelResourceActions, ActionKeys.VIEW));
		Assert.assertTrue(
			ArrayUtil.contains(modelResourceActions, CTActionKeys.PUBLISH));
	}

	@Test
	public void testPublisherRoleModelResourceActions() {
		String[] modelResourceActions = _getModelResourceActions(
			PublicationRoleConstants.ROLE_PUBLISHER);

		Assert.assertFalse(
			ArrayUtil.contains(modelResourceActions, ActionKeys.PERMISSIONS));
		Assert.assertTrue(
			ArrayUtil.contains(modelResourceActions, ActionKeys.UPDATE));
		Assert.assertTrue(
			ArrayUtil.contains(modelResourceActions, ActionKeys.VIEW));
		Assert.assertTrue(
			ArrayUtil.contains(modelResourceActions, CTActionKeys.PUBLISH));
	}

	@Test
	public void testViewerRoleModelResourceActions() {
		String[] modelResourceActions = _getModelResourceActions(
			PublicationRoleConstants.ROLE_VIEWER);

		Assert.assertFalse(
			ArrayUtil.contains(modelResourceActions, ActionKeys.PERMISSIONS));
		Assert.assertFalse(
			ArrayUtil.contains(modelResourceActions, ActionKeys.UPDATE));
		Assert.assertTrue(
			ArrayUtil.contains(modelResourceActions, ActionKeys.VIEW));
		Assert.assertFalse(
			ArrayUtil.contains(modelResourceActions, CTActionKeys.PUBLISH));
	}

	private String[] _getModelResourceActions(int role) {
		return ReflectionTestUtil.invoke(
			new InviteUsersMVCResourceCommand(), "_getModelResourceActions",
			new Class<?>[] {int.class}, role);
	}

}