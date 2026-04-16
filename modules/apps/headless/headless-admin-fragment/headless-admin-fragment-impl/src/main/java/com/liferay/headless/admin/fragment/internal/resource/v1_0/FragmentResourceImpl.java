/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.internal.resource.v1_0;

import com.liferay.headless.admin.fragment.resource.v1_0.FragmentResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rubén Pulido
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/fragment.properties",
	scope = ServiceScope.PROTOTYPE, service = FragmentResource.class
)
public class FragmentResourceImpl extends BaseFragmentResourceImpl {
}
// LIFERAY-REST-BUILDER-HASH:904853094