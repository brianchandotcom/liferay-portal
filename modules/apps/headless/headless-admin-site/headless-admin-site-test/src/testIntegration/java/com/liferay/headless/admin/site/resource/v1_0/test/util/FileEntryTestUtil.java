/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.File;

/**
 * @author Rubén Pulido
 */
public class FileEntryTestUtil {

	public static FileEntry addPreviewFileEntry(
			Group group, PortletFileRepository portletFileRepository,
			Class<?> resourceClass)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository = portletFileRepository.addPortletRepository(
			group.getGroupId(),
			"com_liferay_layout_admin_web_portlet_GroupPagesPortlet",
			serviceContext);

		File file = FileUtil.createTempFile(
			StreamUtil.toByteArray(
				resourceClass.getResourceAsStream(
					"dependencies/thumbnail1.png")));

		try {
			String fileName = RandomTestUtil.randomString() + "_preview.png";

			return DLAppLocalServiceUtil.addFileEntry(
				null, serviceContext.getUserId(), repository.getRepositoryId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
				ContentTypes.IMAGE_PNG, fileName, null, null, null, file, null,
				null, null, serviceContext);
		}
		finally {
			FileUtil.delete(file);
		}
	}

}