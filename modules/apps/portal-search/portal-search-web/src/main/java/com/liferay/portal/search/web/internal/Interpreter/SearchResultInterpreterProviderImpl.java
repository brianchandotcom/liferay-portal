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

package com.liferay.portal.search.web.internal.Interpreter;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.search.web.interpreter.SearchResultInterpreter;
import com.liferay.portal.search.web.interpreter.SearchResultInterpreterProvider;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 */
@Component(service = SearchResultInterpreterProvider.class)
public class SearchResultInterpreterProviderImpl
	implements SearchResultInterpreterProvider {

	@Override
	public SearchResultInterpreter getSearchResultInterpreter(
		String portletName) {

		SearchResultInterpreter searchResultInterpreter =
			_serviceTrackerMap.getService(portletName);

		if (searchResultInterpreter == null) {
			return _assetRendererSearchResultInterpreter;
		}

		return searchResultInterpreter;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, SearchResultInterpreter.class, "javax.portlet.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Reference
	private AssetRendererSearchResultInterpreter
		_assetRendererSearchResultInterpreter;

	private ServiceTrackerMap<String, SearchResultInterpreter>
		_serviceTrackerMap;

}