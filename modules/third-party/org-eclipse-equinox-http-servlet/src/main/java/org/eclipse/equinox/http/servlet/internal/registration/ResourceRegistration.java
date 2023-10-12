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

package org.eclipse.equinox.http.servlet.internal.registration;

import javax.servlet.Servlet;
import org.eclipse.equinox.http.servlet.internal.context.ContextController;
import org.eclipse.equinox.http.servlet.internal.context.ContextController.ServiceHolder;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.runtime.dto.ResourceDTO;
/**
 * @author Raymond Augé
 */
public class ResourceRegistration extends EndpointRegistration<ResourceDTO> {

	public ResourceRegistration(
		ServiceHolder<Servlet> servletHolder, ResourceDTO resourceDTO,
		ServletContextHelper servletContextHelper,
		ContextController contextController, ClassLoader legacyTCCL) {

		super(servletHolder, resourceDTO, servletContextHelper, contextController, legacyTCCL);

		name = servletHolder.get().getClass().getName().concat("#").concat(getD().prefix); //$NON-NLS-1$
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String[] getPatterns() {
		return getD().patterns;
	}

	@Override
	public long getServiceId() {
		return getD().serviceId;
	}

	private final String name;

}
