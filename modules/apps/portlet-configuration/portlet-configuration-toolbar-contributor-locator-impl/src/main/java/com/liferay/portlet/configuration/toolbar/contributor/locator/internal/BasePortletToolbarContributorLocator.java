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

package com.liferay.portlet.configuration.toolbar.contributor.locator.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.util.StringPlus;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.portlet.toolbar.contributor.locator.PortletToolbarContributorLocator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

import javax.portlet.PortletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Sergio González
 */
public abstract class BasePortletToolbarContributorLocator
	implements PortletToolbarContributorLocator {

	@Override
	public List<PortletToolbarContributor> getPortletToolbarContributors(
		String portletId, PortletRequest portletRequest) {

		String value = ParamUtil.getString(
			portletRequest, getParameterName(), "-");

		List<PortletToolbarContributor> portletToolbarContributors =
			_serviceTrackerMap.getService(_getKey(portletId, value));

		if (ListUtil.isEmpty(portletToolbarContributors)) {
			portletToolbarContributors = _serviceTrackerMap.getService(
				_getKey(portletId, StringPool.STAR));
		}

		return portletToolbarContributors;
	}

	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, PortletToolbarContributor.class,
			"(javax.portlet.name=*)",
			new ServiceReferenceMapper<String, PortletToolbarContributor>() {

				@Override
				public void map(
					ServiceReference<PortletToolbarContributor>
						serviceReference,
					Emitter<String> emitter) {

					List<String> portletNames = StringPlus.asList(
						serviceReference.getProperty("javax.portlet.name"));
					List<String> values = StringPlus.asList(
						serviceReference.getProperty(getPropertyName()));

					for (String portletName : portletNames) {
						for (String value : values) {
							emitter.emit(_getKey(portletName, value));
						}
					}
				}

			});
	}

	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	protected abstract String getParameterName();

	protected abstract String getPropertyName();

	private String _getKey(String portletId, String value) {
		return StringBundler.concat(
			portletId, StringPool.PERIOD, getPropertyName(), StringPool.PERIOD,
			value);
	}

	private ServiceTrackerMap<String, List<PortletToolbarContributor>>
		_serviceTrackerMap;

}