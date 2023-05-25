/*******************************************************************************
 * Copyright (c) 2014 Raymond Augé and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 ******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.registration;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.osgi.web.http.servlet.internal.context.ContextController;

import javax.servlet.Servlet;

import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.runtime.dto.ResourceDTO;

/**
 * @author Raymond Augé
 */
public class ResourceRegistration extends EndpointRegistration<ResourceDTO> {

	public ResourceRegistration(
		ContextController.ServiceHolder<Servlet> serviceHolder,
		ResourceDTO resourceDTO, ServletContextHelper servletContextHelper,
		ContextController contextController, ClassLoader legacyTCCL) {

		super(
			serviceHolder, resourceDTO, servletContextHelper, contextController,
			legacyTCCL);

		Servlet servlet = serviceHolder.get();

		Class<?> clazz = servlet.getClass();

		_name = StringBundler.concat(clazz.getName(), "#", getD().prefix);
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String[] getPatterns() {
		return getD().patterns;
	}

	@Override
	public long getServiceId() {
		return getD().serviceId;
	}

	private final String _name;

}