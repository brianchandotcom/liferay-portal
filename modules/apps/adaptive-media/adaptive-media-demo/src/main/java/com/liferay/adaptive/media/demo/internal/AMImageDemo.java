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

package com.liferay.adaptive.media.demo.internal;

import com.liferay.adaptive.media.demo.data.creator.AMImageConfigurationDemoDataCreator;
import com.liferay.document.library.demo.data.creator.FileEntryDemoDataCreator;
import com.liferay.document.library.demo.data.creator.RootFolderDemoDataCreator;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.users.admin.demo.data.creator.OmniAdminUserDemoDataCreator;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class AMImageDemo extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		User user = _omniAdminUserDemoDataCreator.create(
			company.getCompanyId(), "alejandro.hernandez@liferay.com");

		Group guestGroup = _groupLocalService.getGroup(
			company.getCompanyId(), "Guest");

		Folder nonAMFolder = _rootFolderDemoDataCreator.create(
			user.getUserId(), guestGroup.getGroupId(), "Non Adaptive Media");

		for (int i = 0; i < 5; i++) {
			FileEntry fileEntry = _fileEntryDemoDataCreator.create(
				user.getUserId(), nonAMFolder.getFolderId());

			if (_log.isInfoEnabled()) {
				_log.info(
					"Non Adaptive Media Image created with file entry id " +
						fileEntry.getFileEntryId());
			}
		}

		_amImageConfigurationDemoDataCreator.create(company.getCompanyId());

		Folder amFolder = _rootFolderDemoDataCreator.create(
			user.getUserId(), guestGroup.getGroupId(), "Adaptive Media");

		for (int i = 0; i < 5; i++) {
			FileEntry fileEntry = _fileEntryDemoDataCreator.create(
				user.getUserId(), amFolder.getFolderId());

			if (_log.isInfoEnabled()) {
				_log.info(
					"Adaptive Media Image created with file entry id " +
						fileEntry.getFileEntryId());
			}
		}
	}

	@Deactivate
	protected void deactivate() throws IOException, PortalException {
		_amImageConfigurationDemoDataCreator.delete();
		_fileEntryDemoDataCreator.delete();
		_rootFolderDemoDataCreator.delete();
		_omniAdminUserDemoDataCreator.delete();
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private static final Log _log = LogFactoryUtil.getLog(AMImageDemo.class);

	@Reference
	private AMImageConfigurationDemoDataCreator
		_amImageConfigurationDemoDataCreator;

	@Reference
	private FileEntryDemoDataCreator _fileEntryDemoDataCreator;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private OmniAdminUserDemoDataCreator _omniAdminUserDemoDataCreator;

	@Reference
	private RootFolderDemoDataCreator _rootFolderDemoDataCreator;

}