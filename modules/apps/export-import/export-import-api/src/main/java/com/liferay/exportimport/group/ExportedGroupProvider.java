/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.group;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.List;

/**
 * @author Petteri Karttunen
 */
public interface ExportedGroupProvider {

	public List<ExportedGroup> getExportedGroups(FileEntry fileEntry)
		throws Exception;

	public List<ExportedGroup> getExportedGroups(
			PortletDataContext portletDataContext)
		throws Exception;

}