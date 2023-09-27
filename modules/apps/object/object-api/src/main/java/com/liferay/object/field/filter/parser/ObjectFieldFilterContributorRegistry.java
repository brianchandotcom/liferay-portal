/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.field.filter.parser;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Feliphe Marinho
 */
public interface ObjectFieldFilterContributorRegistry {

	public ObjectFieldFilterContributor getObjectFieldFilterContributor(
			ObjectFieldFilterContext objectFieldFilterContext)
		throws PortalException;

}