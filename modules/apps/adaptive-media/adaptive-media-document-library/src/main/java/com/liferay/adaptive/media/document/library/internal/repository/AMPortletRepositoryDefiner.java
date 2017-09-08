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

package com.liferay.adaptive.media.document.library.internal.repository;

import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = RepositoryDefiner.class)
public class AMPortletRepositoryDefiner
	extends BaseOverridingRepositoryDefiner {

	@Activate
	protected void activate() {
		initializeOverridenRepositoryDefiner(_CLASS_NAME);
	}

	@Deactivate
	protected void deactivate() {
		restoreOverridenRepositoryDefiner(_CLASS_NAME);
	}

	private static final String _CLASS_NAME =
		"com.liferay.portal.repository.portletrepository.PortletRepository";

}