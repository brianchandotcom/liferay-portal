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

package com.liferay.portal.kernel.seo;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Cristina González
 */
public class SEOUtil {

	public static List<SEOLink> getLocalizedSEOLinks(
			String canonicalURL, Map<Locale, String> alternateURLs)
		throws PortalException {

		return _seo.getLocalizedSEOLinks(canonicalURL, alternateURLs);
	}

	private static volatile SEO _seo =
		ServiceProxyFactory.newServiceTrackedInstance(
			SEO.class, SEOUtil.class, "_seo", false);

}