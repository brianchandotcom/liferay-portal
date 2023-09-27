/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import java.util.function.Function;

/**
 * @author Adolfo Pérez
 */
public class IdentityServiceContextFunction
	implements Function<String, ServiceContext> {

	public IdentityServiceContextFunction(ServiceContext serviceContext) {
		_serviceContext = serviceContext;
	}

	@Override
	public ServiceContext apply(String s) {
		return _serviceContext;
	}

	private final ServiceContext _serviceContext;

}