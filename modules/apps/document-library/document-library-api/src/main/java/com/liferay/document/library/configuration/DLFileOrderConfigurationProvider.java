/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.configuration;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Sam Ziemer
 */
@ProviderType
public interface DLFileOrderConfigurationProvider {

	public String getCompanyOrderByColumn(long companyId);

	public String getCompanySortBy(long companyId);

	public String getGroupOrderByColumn(long groupId);

	public String getGroupSortBy(long groupId);

	public String getSystemOrderByColumn();

	public String getSystemSortBy();

}