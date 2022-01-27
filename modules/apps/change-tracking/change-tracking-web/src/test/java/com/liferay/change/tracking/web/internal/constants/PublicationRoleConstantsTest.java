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

package com.liferay.change.tracking.web.internal.constants;

import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Samuel Trong Tran
 */
public class PublicationRoleConstantsTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testEditorRoleModelResourceActions() {
		String[] modelResourceActions =
			PublicationRoleConstants.getModelResourceActions(
				PublicationRoleConstants.ROLE_EDITOR);

		Assert.assertFalse(
			ArrayUtil.contains(modelResourceActions, ActionKeys.PERMISSIONS));
		Assert.assertFalse(
			ArrayUtil.contains(modelResourceActions, CTActionKeys.PUBLISH));

		Assert.assertTrue(
			ArrayUtil.contains(modelResourceActions, ActionKeys.UPDATE));
		Assert.assertTrue(
			ArrayUtil.contains(modelResourceActions, ActionKeys.VIEW));
	}

	@Test
	public void testInviterRoleModelResourceActions() {
		String[] modelResourceActions =
			PublicationRoleConstants.getModelResourceActions(
				PublicationRoleConstants.ROLE_INVITER);

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
		String[] modelResourceActions =
			PublicationRoleConstants.getModelResourceActions(
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
		String[] modelResourceActions =
			PublicationRoleConstants.getModelResourceActions(
				PublicationRoleConstants.ROLE_VIEWER);

		Assert.assertFalse(
			ArrayUtil.contains(modelResourceActions, ActionKeys.PERMISSIONS));
		Assert.assertFalse(
			ArrayUtil.contains(modelResourceActions, ActionKeys.UPDATE));
		Assert.assertFalse(
			ArrayUtil.contains(modelResourceActions, CTActionKeys.PUBLISH));

		Assert.assertTrue(
			ArrayUtil.contains(modelResourceActions, ActionKeys.VIEW));
	}

}