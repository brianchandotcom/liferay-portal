/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Brian Wing Shun Chan
 */
public class OrganizationConstants {

	public static final int ANY_PARENT_ORGANIZATION_ID = -1;

	public static final int DEFAULT_PARENT_ORGANIZATION_ID = 0;

	public static final String TYPE_LOCATION = "location";

	public static final String TYPE_REGULAR_ORGANIZATION =
		"regular-organization";

	public static StringBundler buildTreePath(Organization organization)
		throws PortalException, SystemException {

		return buildTreePath(new StringBundler(), organization);
	}

	public static StringBundler buildTreePath(
			StringBundler sb, Organization organization)
		throws PortalException, SystemException {

		if (organization == null) {
			sb.append(StringPool.SLASH);

			return sb;
		}

		buildTreePath(sb, organization.getParentOrganization());

		sb.append(organization.getOrganizationId());
		sb.append(StringPool.SLASH);

		return sb;
	}

}