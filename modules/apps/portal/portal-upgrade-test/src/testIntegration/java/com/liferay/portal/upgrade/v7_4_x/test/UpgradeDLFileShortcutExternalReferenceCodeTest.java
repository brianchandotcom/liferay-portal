/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalService;
import com.liferay.document.library.test.util.DLTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ExternalReferenceCodeModel;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.BaseExternalReferenceCodeUpgradeProcessTestCase;
import com.liferay.portal.upgrade.v7_4_x.UpgradeDLFileShortcutExternalReferenceCode;

import org.junit.runner.RunWith;

/**
 * @author Balázs Sáfrány-Kovalik
 */
@RunWith(Arquillian.class)
public class UpgradeDLFileShortcutExternalReferenceCodeTest
	extends BaseExternalReferenceCodeUpgradeProcessTestCase {

	@Override
	protected ExternalReferenceCodeModel[] addExternalReferenceCodeModels(
			String tableName)
		throws PortalException {

		DLFolder dlFolder = DLTestUtil.addDLFolder(group.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addDLFileEntry(
			dlFolder.getFolderId());

		DLFileShortcut dlFileShortcut =
			_dlFileShortcutLocalService.addFileShortcut(
				null, TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
				TestPropsValues.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				dlFileEntry.getFileEntryId(),
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		return new ExternalReferenceCodeModel[] {dlFileShortcut};
	}

	@Override
	protected ExternalReferenceCodeModel fetchExternalReferenceCodeModel(
			ExternalReferenceCodeModel externalReferenceCodeModel,
			String tableName)
		throws PortalException {

		DLFileShortcut dlFileShortcut =
			(DLFileShortcut)externalReferenceCodeModel;

		return _dlFileShortcutLocalService.fetchDLFileShortcut(
			dlFileShortcut.getFileShortcutId());
	}

	@Override
	protected String[] getTableNames() {
		return new String[] {"DLFileShortcut"};
	}

	@Override
	protected UpgradeDLFileShortcutExternalReferenceCode getUpgradeProcess() {
		return new UpgradeDLFileShortcutExternalReferenceCode();
	}

	@Override
	protected UpgradeStepRegistrator getUpgradeStepRegistrator() {
		return null;
	}

	@Override
	protected Version getVersion() {
		return null;
	}

	@Inject
	private DLFileShortcutLocalService _dlFileShortcutLocalService;

}