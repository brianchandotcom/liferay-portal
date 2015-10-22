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

package com.liferay.portal.portlet.toolbar.contributor.locator;

import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.portlet.toolbar.contributor.locator.PortletToolbarContributorLocator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.List;

import javax.portlet.PortletRequest;

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
			_serviceTrackerMap.getService(getKey(portletId, value));

		if (ListUtil.isEmpty(portletToolbarContributors)) {
			portletToolbarContributors = _serviceTrackerMap.getService(
				getKey(portletId, StringPool.STAR));
		}

		return portletToolbarContributors;
	}

	protected void activate() {
		_serviceTrackerMap = ServiceTrackerCollections.multiValueMap(
			PortletToolbarContributor.class, "(javax.portlet.name=*)",
			new ServiceReferenceMapper<String, PortletToolbarContributor>() {

				@Override
				public void map(
					ServiceReference<PortletToolbarContributor>
						serviceReference,
					Emitter<String> emitter) {

					String portletName = (String)serviceReference.getProperty(
						"javax.portlet.name");
					String value = (String)serviceReference.getProperty(
						getPropertyName());

					emitter.emit(getKey(portletName, value));
				}

			});

		_serviceTrackerMap.open();
	}

	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	protected String getKey(String portletId, String value) {
		StringBundler sb = new StringBundler(5);

		sb.append(portletId);
		sb.append(StringPool.PERIOD);
		sb.append(getPropertyName());
		sb.append(StringPool.PERIOD);
		sb.append(value);

		return sb.toString();
	}

	protected abstract String getParameterName();

	protected abstract String getPropertyName();

	private ServiceTrackerMap<String, List<PortletToolbarContributor>>
		_serviceTrackerMap;

}