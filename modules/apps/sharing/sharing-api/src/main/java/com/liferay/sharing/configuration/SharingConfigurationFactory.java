/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.configuration;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;

/**
 * @author Adolfo Pérez
 */
public interface SharingConfigurationFactory {

	public SharingConfiguration getCompanySharingConfiguration(Company company);

	public SharingConfiguration getGroupSharingConfiguration(Group group);

	public SharingConfiguration getSystemSharingConfiguration();

}