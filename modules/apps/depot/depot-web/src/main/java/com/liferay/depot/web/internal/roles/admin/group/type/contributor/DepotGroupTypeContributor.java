/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.roles.admin.group.type.contributor;

import com.liferay.depot.model.DepotEntry;
import com.liferay.roles.admin.group.type.contributor.GroupTypeContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = GroupTypeContributor.class)
public class DepotGroupTypeContributor implements GroupTypeContributor {

	@Override
	public String getClassName() {
		return DepotEntry.class.getName();
	}

}