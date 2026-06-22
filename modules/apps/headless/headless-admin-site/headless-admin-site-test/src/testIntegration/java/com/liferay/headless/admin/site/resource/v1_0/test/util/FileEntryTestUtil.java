/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;

/**
 * @author Rubén Pulido
 */
public class FileEntryTestUtil {

	public static FileEntry addPreviewFileEntry(
			Group group, PortletFileRepository portletFileRepository,
			Class<?> resourceClass)
		throws Exception {

		return portletFileRepository.addPortletFileEntry(
			null, group.getGroupId(), TestPropsValues.getUserId(), null, 0,
			"com_liferay_layout_admin_web_portlet_GroupPagesPortlet",
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			resourceClass.getResourceAsStream("dependencies/thumbnail_1.png"),
			RandomTestUtil.randomString() + "_preview.png",
			ContentTypes.IMAGE_PNG, false);
	}

}