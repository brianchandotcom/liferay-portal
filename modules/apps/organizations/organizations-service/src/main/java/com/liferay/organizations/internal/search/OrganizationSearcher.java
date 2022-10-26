/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.organizations.internal.search;

import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.Field;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Fabiano Nazar
 * @author Luan Maoski
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.model.Organization",
	service = BaseSearcher.class
)
public class OrganizationSearcher extends BaseSearcher {

	public static final String CLASS_NAME = Organization.class.getName();

	public OrganizationSearcher() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ORGANIZATION_ID, Field.UID);
		setPermissionAware(true);
		setStagingAware(false);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

}