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

package com.liferay.portal.kernel.search.facet.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Raymond Augé
 */
public class FacetFactoryUtil {

	public static Facet create(
			SearchContext searchContext, FacetConfiguration facetConfiguration)
		throws Exception {

		String className = facetConfiguration.getClassName();

		FacetFactory facetFactory = _instance._facetFactories.get(className);

		if (facetFactory == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"FacetFactory not found for {className=" + className +
						"}. Ignoring!");
			}

			return null;
		}

		Facet facet = facetFactory.newInstance(searchContext);

		facet.setFacetConfiguration(facetConfiguration);

		return facet;
	}

	private static Log _log = LogFactoryUtil.getLog(FacetFactoryUtil.class);

	private static FacetFactoryUtil _instance = new FacetFactoryUtil();

	private Map<String, FacetFactory> _facetFactories =
		new ConcurrentHashMap<String, FacetFactory>();

}