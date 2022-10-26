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

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.configuration.admin.display.ConfigurationFormRenderer;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge Ferrer
 */
@Component(service = ConfigurationFormRendererRetriever.class)
public class ConfigurationFormRendererRetrieverImpl
	implements ConfigurationFormRendererRetriever {

	@Override
	public ConfigurationFormRenderer getConfigurationFormRenderer(String pid) {
		ConfigurationFormRenderer configurationFormRenderer =
			_configurationFormRendererServiceTrackerMap.getService(pid);

		if (configurationFormRenderer == null) {
			configurationFormRenderer = _CONFIGURATION_FORM_RENDERER;
		}

		return configurationFormRenderer;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_configurationFormRendererServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ConfigurationFormRenderer.class, null,
				(serviceReference, emitter) -> {
					ConfigurationFormRenderer configurationFormRenderer =
						bundleContext.getService(serviceReference);

					emitter.emit(configurationFormRenderer.getPid());
				});
	}

	private static final ConfigurationFormRenderer
		_CONFIGURATION_FORM_RENDERER = new DefaultConfigurationFormRenderer();

	private ServiceTrackerMap<String, ConfigurationFormRenderer>
		_configurationFormRendererServiceTrackerMap;

}