/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.internal.resource.v1_0;

import com.liferay.scim.rest.resource.v1_0.GroupResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Olivér Kecskeméty
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/group.properties",
	scope = ServiceScope.PROTOTYPE, service = GroupResource.class
)
public class GroupResourceImpl extends BaseGroupResourceImpl {
}