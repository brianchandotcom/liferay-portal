/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.registration;

import com.liferay.petra.string.StringPool;

import jakarta.servlet.Servlet;

import org.eclipse.equinox.http.servlet.internal.context.ContextController;

import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.runtime.dto.ResourceDTO;

/**
 * @author Dante Wang
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

		String className = clazz.getName();

		_name = className.concat(
			StringPool.POUND
		).concat(
			resourceDTO.prefix
		);
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String[] getPatterns() {
		ResourceDTO resourceDTO = getD();

		return resourceDTO.patterns;
	}

	@Override
	public long getServiceId() {
		ResourceDTO resourceDTO = getD();

		return resourceDTO.serviceId;
	}

	private final String _name;

}