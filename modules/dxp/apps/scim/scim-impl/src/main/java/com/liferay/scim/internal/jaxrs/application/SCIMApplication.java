/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.internal.jaxrs.application;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;

/**
 * @author Olivér Kecskeméty
 */
@Component(
	property = {
		"liferay.auth.verifier=false", "osgi.jaxrs.application.base=/scim",
		"osgi.jaxrs.name=Liferay.SCIM.Application"
	},
	service = Application.class
)
public class SCIMApplication extends Application {
}