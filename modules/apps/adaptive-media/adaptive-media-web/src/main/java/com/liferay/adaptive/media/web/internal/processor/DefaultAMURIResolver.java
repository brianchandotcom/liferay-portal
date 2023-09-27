/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.web.internal.processor;

import com.liferay.adaptive.media.AMURIResolver;
import com.liferay.adaptive.media.web.internal.constants.AMWebConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Portal;

import java.net.URI;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = AMURIResolver.class)
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