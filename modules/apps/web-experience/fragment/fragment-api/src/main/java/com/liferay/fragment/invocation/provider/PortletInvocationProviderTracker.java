/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.fragment.invocation.provider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = PortletInvocationProviderTracker.class)
public class PortletInvocationProviderTracker {

	public PortletInvocationProvider getPortletInvocationProvider(
		String alias) {

		return _portletInvocationProviders.get(alias);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		unbind = "unsetPortletInvocationProvider"
	)
	protected void setPortletInvocationProvider(
		PortletInvocationProvider portletInvocationProvider) {

		_portletInvocationProviders.put(
			portletInvocationProvider.getFragmentInvocationAlias(),
			portletInvocationProvider);
	}

	protected void unsetPortletInvocationProvider(
		PortletInvocationProvider portletInvocationProvider) {

		_portletInvocationProviders.remove(
			portletInvocationProvider.getFragmentInvocationAlias());
	}

	private final Map<String, PortletInvocationProvider>
		_portletInvocationProviders = new ConcurrentHashMap<>();

}