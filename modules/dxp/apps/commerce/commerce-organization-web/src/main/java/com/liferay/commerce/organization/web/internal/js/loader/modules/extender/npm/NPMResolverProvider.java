/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.organization.web.internal.js.loader.modules.extender.npm;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Ethan Bustad
 */
@Component(immediate = true, service = NPMResolverProvider.class)
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

	@Reference(unbind = "-")
	protected void setNPMResolver(NPMResolver npmResolver) {
		_npmResolver = npmResolver;
	}

	private static NPMResolverProvider _npmResolverProvider;

	private NPMResolver _npmResolver;

}