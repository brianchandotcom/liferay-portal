/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.resource.v1_0.FriendlyUrlHistoryResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rubén Pulido
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/friendly-url-history.properties",
	scope = ServiceScope.PROTOTYPE, service = FriendlyUrlHistoryResource.class
)
public class FriendlyUrlHistoryResourceImpl
	extends BaseFriendlyUrlHistoryResourceImpl {
}