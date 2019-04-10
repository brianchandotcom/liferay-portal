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

package com.liferay.sharing.demo.internal;

import com.liferay.document.library.demo.data.creator.FileEntryDemoDataCreator;
import com.liferay.document.library.demo.data.creator.RootFolderDemoDataCreator;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.users.admin.demo.data.creator.BasicUserDemoDataCreator;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import java.util.Arrays;
import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class SharingDemo extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		User sharerUser = _basicUserDemoDataCreator.create(
			company.getCompanyId(), "sharing.sharer@liferay.com");
		Group group = _groupLocalService.getGroup(
			company.getCompanyId(), "Guest");

		Folder folder = _rootFolderDemoDataCreator.create(
			sharerUser.getUserId(), group.getGroupId(), "Sharing");

		FileEntry fileEntry = _fileEntryDemoDataCreator.create(
			sharerUser.getUserId(), folder.getFolderId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(group.getGroupId());
		serviceContext.setUserId(sharerUser.getUserId());

		for (int i = 0; i < 15; i++) {
			User user = _basicUserDemoDataCreator.create(
				company.getCompanyId());

			_sharingEntryLocalService.addSharingEntry(
				sharerUser.getUserId(), user.getUserId(),
				_portal.getClassNameId(DLFileEntry.class),
				fileEntry.getFileEntryId(), group.getGroupId(), (i % 3) == 0,
				Arrays.asList(SharingEntryAction.VIEW),
				((i % 2) == 0) ? _getExpirationDate(i + 1) : null,
				serviceContext);
		}

		User testUser = _userLocalService.fetchUserByEmailAddress(
			company.getCompanyId(), "test@liferay.com");

		if (testUser != null) {
			_sharingEntryLocalService.addSharingEntry(
				sharerUser.getUserId(), testUser.getUserId(),
				_portal.getClassNameId(DLFileEntry.class),
				fileEntry.getFileEntryId(), group.getGroupId(), true,
				Arrays.asList(
					SharingEntryAction.UPDATE, SharingEntryAction.VIEW),
				null, serviceContext);
		}
	}

	@Deactivate
	protected void deactivate() throws PortalException {
		_basicUserDemoDataCreator.delete();
		_fileEntryDemoDataCreator.delete();
		_rootFolderDemoDataCreator.delete();
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private Date _getExpirationDate(int daysFromNow) {
		LocalDateTime localDateTime = LocalDateTime.now();

		localDateTime = localDateTime.plusDays(daysFromNow);

		return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
	}

	@Reference
	private BasicUserDemoDataCreator _basicUserDemoDataCreator;

	@Reference
	private FileEntryDemoDataCreator _fileEntryDemoDataCreator;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RootFolderDemoDataCreator _rootFolderDemoDataCreator;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}