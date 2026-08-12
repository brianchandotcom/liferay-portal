/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.site;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * @author Petteri Karttunen
 */
public interface ExportImportSiteProvider {

	public int getChildSiteCount(Group group);

	public String getDescriptiveName(Group group, Locale locale);

	public String getPath(Group group, Locale locale);

	public List<Group> getSupportedSites(
			long companyId, String search, Comparator<Group> comparator)
		throws PortalException;

	public boolean isSupported(Group group);

}