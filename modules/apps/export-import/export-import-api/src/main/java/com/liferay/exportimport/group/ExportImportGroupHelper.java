/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.group;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;

/**
 * @author Petteri Karttunen
 */
public interface ExportImportGroupHelper {

	public int getChildGroupCount(Group group) throws PortalException;

	public String getGroupPath(Group group, Locale locale)
		throws PortalException;

	public List<Group> getSupportedGroups(
			long companyId, String keywords,
			OrderByComparator<Group> orderByComparator)
		throws PortalException;

	public boolean isGroupSupported(Group group);

}