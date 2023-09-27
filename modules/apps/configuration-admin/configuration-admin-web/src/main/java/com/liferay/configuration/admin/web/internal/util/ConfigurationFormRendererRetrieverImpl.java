/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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