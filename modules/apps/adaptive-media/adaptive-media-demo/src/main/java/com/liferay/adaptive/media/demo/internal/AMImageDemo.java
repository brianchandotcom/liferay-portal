/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = PortalInstanceLifecycleListener.class)
public class AMImageDemo extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		User user = _omniAdminUserDemoDataCreator.create(
			company.getCompanyId(), "alejandro.hernandez@liferay.com");
		Group group = _groupLocalService.getGroup(
			company.getCompanyId(), "Guest");

		Folder nonamFolder = _rootFolderDemoDataCreator.create(
			user.getUserId(), group.getGroupId(), "Non Adaptive Media");

		for (int i = 0; i < 5; i++) {
			FileEntry fileEntry = _fileEntryDemoDataCreator.create(
				user.getUserId(), nonamFolder.getFolderId());

			if (_log.isInfoEnabled()) {
				_log.info(
					"Non adaptive media image created with file entry " +
						fileEntry.getFileEntryId());
			}
		}

		_amImageConfigurationDemoDataCreator.create(company.getCompanyId());

		Folder amFolder = _rootFolderDemoDataCreator.create(
			user.getUserId(), group.getGroupId(), "Adaptive Media");

		for (int i = 0; i < 5; i++) {
			FileEntry fileEntry = _fileEntryDemoDataCreator.create(
				user.getUserId(), amFolder.getFolderId());

			if (_log.isInfoEnabled()) {
				_log.info(
					"Adaptive media image created with file entry " +
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

	private static final Log _log = LogFactoryUtil.getLog(AMImageDemo.class);

	@Reference
	private AMImageConfigurationDemoDataCreator
		_amImageConfigurationDemoDataCreator;

	@Reference
	private FileEntryDemoDataCreator _fileEntryDemoDataCreator;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private OmniAdminUserDemoDataCreator _omniAdminUserDemoDataCreator;

	@Reference
	private RootFolderDemoDataCreator _rootFolderDemoDataCreator;

}