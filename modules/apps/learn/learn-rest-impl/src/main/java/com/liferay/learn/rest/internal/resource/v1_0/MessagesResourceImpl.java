/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.learn.rest.internal.resource.v1_0;

import com.liferay.learn.rest.resource.v1_0.MessagesResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Thiago Buarque
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/messages.properties",
	scope = ServiceScope.PROTOTYPE, service = MessagesResource.class
)
public class MessagesResourceImpl extends BaseMessagesResourceImpl {
}