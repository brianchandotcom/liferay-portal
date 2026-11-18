/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.rest.internal.resource.v1_0;

import com.liferay.launch.rest.resource.v1_0.LaunchResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alberto Moreno
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/launch.properties",
	scope = ServiceScope.PROTOTYPE, service = LaunchResource.class
)
public class LaunchResourceImpl extends BaseLaunchResourceImpl {
}