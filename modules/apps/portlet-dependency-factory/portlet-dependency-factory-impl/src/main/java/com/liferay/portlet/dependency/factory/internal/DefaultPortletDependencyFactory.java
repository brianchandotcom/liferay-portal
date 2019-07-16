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

package com.liferay.portlet.dependency.factory.internal;

import com.liferay.portal.kernel.model.portlet.PortletDependency;
import com.liferay.portal.kernel.model.portlet.PortletDependencyFactory;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;

import javax.portlet.PortletRequest;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Neil Griffin
 */
@Component(
	property = Constants.SERVICE_RANKING + ":Integer=" + Integer.MIN_VALUE,
	service = PortletDependencyFactory.class
)
public class DefaultPortletDependencyFactory
	implements PortletDependencyFactory {

	@Override
	public PortletDependency createPortletDependency(
		String name, String scope, String version) {

		return new PortletDependencyImpl(name, scope, version, null, null);
	}

	@Override
	public PortletDependency createPortletDependency(
		String name, String scope, String version, String markup,
		PortletRequest portletRequest) {

		AbsolutePortalURLBuilder absolutePortalURLBuilder =
			_absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
				_portal.getHttpServletRequest(portletRequest));

		return new PortletDependencyImpl(
			name, scope, version, markup, absolutePortalURLBuilder);
	}

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

	@Reference
	private Portal _portal;

}