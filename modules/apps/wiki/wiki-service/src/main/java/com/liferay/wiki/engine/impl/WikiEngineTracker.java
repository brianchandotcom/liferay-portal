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

package com.liferay.wiki.engine.impl;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.wiki.engine.WikiEngine;

import java.util.Collection;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Iván Zaera
 */
@Component(immediate = true, service = WikiEngineTracker.class)
public class WikiEngineTracker {

	public Collection<String> getFormats() {
		return _serviceTrackerMap.keySet();
	}

	public WikiEngine getWikiEngine(String format) {
		List<WikiEngine> wikiEngines = _serviceTrackerMap.getService(format);

		if (wikiEngines == null) {
			return null;
		}

		return wikiEngines.get(0);
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_bundleContext = bundleContext;

		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, WikiEngine.class, null,
			new ServiceReferenceMapper<String, WikiEngine>() {

				@Override
				public void map(
					ServiceReference<WikiEngine> serviceReference,
					Emitter<String> emitter) {

					WikiEngine wikiEngine = _bundleContext.getService(
						serviceReference);

					try {
						emitter.emit(wikiEngine.getFormat());
					}
					finally {
						_bundleContext.ungetService(serviceReference);
					}
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WikiEngineTracker.class);

	private BundleContext _bundleContext;
	private ServiceTrackerMap<String, List<WikiEngine>> _serviceTrackerMap;

}