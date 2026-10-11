/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.responsive.image.ResponsiveImageURLTransformer;
import com.liferay.responsive.image.ResponsiveImageURLTransformerProvider;

import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Daniel Sanz
 */
@Component(service = ResponsiveImageURLTransformerProvider.class)
public class ResponsiveImageURLTransformerProviderImpl
	implements ResponsiveImageURLTransformerProvider {

	@Override
	public ResponsiveImageURLTransformer getResponsiveImageURLTransformer(
		String responsiveImageURLTransformerName) {

		if (Validator.isBlank(responsiveImageURLTransformerName)) {
			return null;
		}

		ResponsiveImageURLTransformer responsiveImageURLTransformer =
			_serviceTrackerMap.getService(responsiveImageURLTransformerName);

		if ((responsiveImageURLTransformer == null) && _log.isDebugEnabled()) {
			_log.debug(
				"No image URL transformer is named " +
					responsiveImageURLTransformerName);
		}

		return responsiveImageURLTransformer;
	}

	@Override
	public Set<String> getResponsiveImageURLTransformerNames() {
		return _serviceTrackerMap.keySet();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ResponsiveImageURLTransformer.class,
			"responsive.image.url.transformer.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResponsiveImageURLTransformerProviderImpl.class);

	private ServiceTrackerMap<String, ResponsiveImageURLTransformer>
		_serviceTrackerMap;

}