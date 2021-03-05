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

package com.liferay.bookmarks.web.internal.asset.model;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class BookmarksEntryAssetRendererTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testHasViewPermissionReturnsFalseOnFailure() throws Exception {
		Mockito.when(
			_modelResourcePermission.contains(
				Mockito.any(PermissionChecker.class),
				Mockito.any(BookmarksEntry.class), Mockito.anyString())
		).thenThrow(
			new PrincipalException()
		);

		AssetRenderer<BookmarksEntry> assetRenderer =
			new BookmarksEntryAssetRenderer(
				_bookmarksEntry, _modelResourcePermission);

		Assert.assertFalse(assetRenderer.hasViewPermission(_permissionChecker));
	}

	@Test
	public void testHasViewPermissionReturnsFalseWhenUserDoesNotHavePermission()
		throws Exception {

		Mockito.when(
			_modelResourcePermission.contains(
				Mockito.any(PermissionChecker.class),
				Mockito.any(BookmarksEntry.class), Mockito.anyString())
		).thenReturn(
			false
		);

		AssetRenderer<BookmarksEntry> assetRenderer =
			new BookmarksEntryAssetRenderer(
				_bookmarksEntry, _modelResourcePermission);

		Assert.assertFalse(assetRenderer.hasViewPermission(_permissionChecker));
	}

	@Test
	public void testHasViewPermissionReturnsTrueWhenUserHasPermission()
		throws Exception {

		Mockito.when(
			_modelResourcePermission.contains(
				Mockito.any(PermissionChecker.class),
				Mockito.any(BookmarksEntry.class), Mockito.anyString())
		).thenReturn(
			true
		);

		AssetRenderer<BookmarksEntry> assetRenderer =
			new BookmarksEntryAssetRenderer(
				_bookmarksEntry, _modelResourcePermission);

		Assert.assertTrue(assetRenderer.hasViewPermission(_permissionChecker));
	}

	private final BookmarksEntry _bookmarksEntry = Mockito.mock(
		BookmarksEntry.class);
	private final ModelResourcePermission<BookmarksEntry>
		_modelResourcePermission = Mockito.mock(ModelResourcePermission.class);
	private final PermissionChecker _permissionChecker = Mockito.mock(
		PermissionChecker.class);

}