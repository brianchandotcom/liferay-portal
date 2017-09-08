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

package com.liferay.adaptive.media.web.internal.processor;

import com.liferay.adaptive.media.AMURIResolver;
import com.liferay.adaptive.media.web.internal.constants.AMWebConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;

import java.net.URI;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = AMURIResolver.class)
public class DefaultAMURIResolver implements AMURIResolver {

	@Override
	public URI resolveURI(URI relativeURI) {
		String pathModule = _portal.getPathModule();

		if (!pathModule.endsWith(StringPool.SLASH)) {
			pathModule += StringPool.SLASH;
		}

		String servletPath =
			pathModule + AMWebConstants.SERVLET_PATH + StringPool.SLASH;

		URI moduleURI = URI.create(servletPath);

		return moduleURI.resolve(relativeURI);
	}

	@Reference
	private Portal _portal;

}