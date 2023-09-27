/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.organization.web.internal.js.loader.modules.extender.npm;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Ethan Bustad
 */
@Component(service = NPMResolverProvider.class)
public class NPMResolverProvider {

	public static NPMResolver getNPMResolver() {
		if (_npmResolverProvider == null) {
			return null;
		}

		return _npmResolverProvider._npmResolver;
	}

	public NPMResolverProvider() {
		_npmResolverProvider = this;
	}

	private static NPMResolverProvider _npmResolverProvider;

	@Reference
	private NPMResolver _npmResolver;

}