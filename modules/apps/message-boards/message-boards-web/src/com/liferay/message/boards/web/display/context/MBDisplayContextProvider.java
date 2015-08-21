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

package com.liferay.message.boards.web.display.context;

import com.liferay.message.boards.display.context.MBDisplayContextFactory;
import com.liferay.message.boards.display.context.MBHomeDisplayContext;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Iván Zaera
 */
@Component(service = MBDisplayContextProvider.class)
public class MBDisplayContextProvider {

	public MBHomeDisplayContext getMBHomeDisplayContext(
		HttpServletRequest request, HttpServletResponse response) {

		Collection<MBDisplayContextFactory> mbDisplayContextFactories =
			_mbDisplayContextFactories.values();

		MBHomeDisplayContext mbHomeDisplayContext =
			new DefaultMBHomeDisplayContext(request, response);

		for (MBDisplayContextFactory mbDisplayContextFactory :
				mbDisplayContextFactories) {

			mbHomeDisplayContext =
				mbDisplayContextFactory.getMBHomeDisplayContext(
					mbHomeDisplayContext, request, response);
		}

		return mbHomeDisplayContext;
	}

	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.RELUCTANT,
		service = MBDisplayContextFactory.class
	)
	protected void setMBDisplayContextFactory(
		ServiceReference<MBDisplayContextFactory> serviceReference) {

		MBDisplayContextFactory mbDisplayContextFactory = null;

		if (_bundleContext != null) {
			mbDisplayContextFactory = _bundleContext.getService(
				serviceReference);
		}

		_mbDisplayContextFactories.put(
			serviceReference, mbDisplayContextFactory);
	}

	protected void unsetMBDisplayContextFactory(
		ServiceReference<MBDisplayContextFactory> serviceReference) {

		_mbDisplayContextFactories.remove(serviceReference);
	}

	private BundleContext _bundleContext;
	private final Map<ServiceReference<MBDisplayContextFactory>,
		MBDisplayContextFactory> _mbDisplayContextFactories =
			new ConcurrentHashMap<>();

}