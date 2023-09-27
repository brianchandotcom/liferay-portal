/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package foo.service;

import foo.api.FooService;

import org.osgi.service.component.annotations.Component;

/**
 * @author Liferay
 */
@Component(service = FooService.class)
public class FooServiceImpl implements FooService {

	@Override
	public String bar() {
		return "foobar";
	}

}