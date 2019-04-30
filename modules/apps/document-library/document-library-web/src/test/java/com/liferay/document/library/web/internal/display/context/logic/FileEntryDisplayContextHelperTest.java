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

package com.liferay.document.library.web.internal.display.context.logic;

import com.liferay.document.library.web.internal.security.permission.resource.DLFileEntryPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author Cristina González
 */
public class FileEntryDisplayContextHelperTest {

	@Test
	public void testIsCancelCheckoutDocumentActionAvailableWithCheckedOutAndOverridePermissionAndMoreThat1Version()
		throws PortalException {

		FileEntry fileEntry = Mockito.mock(FileEntry.class);

		Mockito.when(
			fileEntry.getFileVersionsCount(Mockito.anyInt())
		).thenReturn(
			2
		);

		Mockito.when(
			fileEntry.isCheckedOut()
		).thenReturn(
			true
		);

		_configureDLFileEntryPermission(true, false);

		FileEntryDisplayContextHelper fileEntryDisplayContextHelper =
			new FileEntryDisplayContextHelper(null, fileEntry);

		Assert.assertTrue(
			fileEntryDisplayContextHelper.
				isCancelCheckoutDocumentActionAvailable());
	}

	@Test
	public void testIsCancelCheckoutDocumentActionAvailableWithLockedFileAnd1Version()
		throws PortalException {

		FileEntry fileEntry = Mockito.mock(FileEntry.class);

		Mockito.when(
			fileEntry.getFileVersionsCount(Mockito.anyInt())
		).thenReturn(
			1
		);

		Mockito.when(
			fileEntry.hasLock()
		).thenReturn(
			true
		);

		Mockito.when(
			fileEntry.isSupportsLocking()
		).thenReturn(
			true
		);

		_configureDLFileEntryPermission(true, true);

		FileEntryDisplayContextHelper fileEntryDisplayContextHelper =
			new FileEntryDisplayContextHelper(null, fileEntry);

		Assert.assertFalse(
			fileEntryDisplayContextHelper.
				isCancelCheckoutDocumentActionAvailable());
	}

	@Test
	public void testIsCancelCheckoutDocumentActionAvailableWithLockedFileAndMoreThan1Version()
		throws PortalException {

		FileEntry fileEntry = Mockito.mock(FileEntry.class);

		Mockito.when(
			fileEntry.getFileVersionsCount(Mockito.anyInt())
		).thenReturn(
			2
		);

		Mockito.when(
			fileEntry.hasLock()
		).thenReturn(
			true
		);

		Mockito.when(
			fileEntry.isSupportsLocking()
		).thenReturn(
			true
		);

		_configureDLFileEntryPermission(true, true);

		FileEntryDisplayContextHelper fileEntryDisplayContextHelper =
			new FileEntryDisplayContextHelper(null, fileEntry);

		Assert.assertTrue(
			fileEntryDisplayContextHelper.
				isCancelCheckoutDocumentActionAvailable());
	}

	@Test
	public void testIsCancelCheckoutDocumentActionAvailableWithNullFileEntry()
		throws PortalException {

		_configureDLFileEntryPermission(true, true);

		FileEntryDisplayContextHelper fileEntryDisplayContextHelper =
			new FileEntryDisplayContextHelper(null, null);

		Assert.assertFalse(
			fileEntryDisplayContextHelper.
				isCancelCheckoutDocumentActionAvailable());
	}

	public class MockModelResourcePermission
		implements ModelResourcePermission<FileEntry> {

		public MockModelResourcePermission(
			boolean overrideCheckOutPermission, boolean updatePermission) {

			_overrideCheckOutPermission = overrideCheckOutPermission;
			_updatePermission = updatePermission;
		}

		@Override
		public void check(
				PermissionChecker permissionChecker, FileEntry model,
				String actionId)
			throws PortalException {
		}

		@Override
		public void check(
				PermissionChecker permissionChecker, long primaryKey,
				String actionId)
			throws PortalException {
		}

		@Override
		public boolean contains(
				PermissionChecker permissionChecker, FileEntry model,
				String actionId)
			throws PortalException {

			return _hasPermission(actionId);
		}

		@Override
		public boolean contains(
				PermissionChecker permissionChecker, long primaryKey,
				String actionId)
			throws PortalException {

			return _hasPermission(actionId);
		}

		@Override
		public String getModelName() {
			return null;
		}

		@Override
		public PortletResourcePermission getPortletResourcePermission() {
			return null;
		}

		private boolean _hasPermission(String actionId) {
			if (Objects.equals(actionId, ActionKeys.OVERRIDE_CHECKOUT)) {
				return _overrideCheckOutPermission;
			}
			else if (Objects.equals(actionId, ActionKeys.UPDATE)) {
				return _updatePermission;
			}

			return false;
		}

		private final boolean _overrideCheckOutPermission;
		private final boolean _updatePermission;

	}

	private void _configureDLFileEntryPermission(
		boolean overrideCheckOutPermission, boolean updatePermission) {

		ReflectionTestUtils.setField(
			_dlFileEntryPermission, "_dlFileEntryModelResourcePermission",
			new MockModelResourcePermission(
				overrideCheckOutPermission, updatePermission));

		ReflectionTestUtils.setField(
			_dlFileEntryPermission, "_fileEntryModelResourcePermission",
			new MockModelResourcePermission(
				overrideCheckOutPermission, updatePermission));
	}

	private final DLFileEntryPermission _dlFileEntryPermission =
		new DLFileEntryPermission();

}