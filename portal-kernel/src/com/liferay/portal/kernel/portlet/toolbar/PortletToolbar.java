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

package com.liferay.portal.kernel.portlet.toolbar;

import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.portlet.toolbar.contributor.locator.PortletToolbarContributorLocator;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * Provides elements to be rendered in the portlet toolbar. To obtain those
 * elements, it delegates the task to the {@link
 * PortletToolbarContributorLocator} instances registered in OSGI.
 *
 * @author Sergio González
 */
public class PortletToolbar {

	public PortletToolbar() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			PortletToolbarContributorLocator.class,
			new PortletToolbarServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public List<Menu> getPortletTitleMenus(
		String portletId, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		if ((portletRequest == null) || (portletResponse == null) ||
			Validator.isNull(portletId)) {

			return Collections.emptyList();
		}

		List<Menu> portletTitleMenus = new ArrayList<>();

		for (PortletToolbarContributorLocator portletToolbarContributorLocator :
				_portletToolbarContributorLocators) {

			List<PortletToolbarContributor> portletToolbarContributors =
				portletToolbarContributorLocator.getPortletToolbarContributors(
					portletId, portletRequest);

			if (portletToolbarContributors == null) {
				continue;
			}

			for (PortletToolbarContributor portletToolbarContributor :
					portletToolbarContributors) {

				List<Menu> curPortletTitleMenus =
					portletToolbarContributor.getPortletTitleMenus(
						portletRequest, portletResponse);

				if (ListUtil.isEmpty(curPortletTitleMenus)) {
					continue;
				}

				portletTitleMenus.addAll(curPortletTitleMenus);
			}
		}

		return portletTitleMenus;
	}

	private static final List<PortletToolbarContributorLocator>
		_portletToolbarContributorLocators = new CopyOnWriteArrayList<>();

	private final ServiceTracker
		<PortletToolbarContributorLocator, PortletToolbarContributorLocator>
			_serviceTracker;

	private static class PortletToolbarServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<PortletToolbarContributorLocator,
			 PortletToolbarContributorLocator> {

		@Override
		public PortletToolbarContributorLocator addingService(
			ServiceReference<PortletToolbarContributorLocator>
				serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			PortletToolbarContributorLocator portletToolbarContributorLocator =
				registry.getService(serviceReference);

			_portletToolbarContributorLocators.add(
				portletToolbarContributorLocator);

			return portletToolbarContributorLocator;
		}

		@Override
		public void modifiedService(
			ServiceReference<PortletToolbarContributorLocator> serviceReference,
			PortletToolbarContributorLocator portletToolbarContributorLocator) {
		}

		@Override
		public void removedService(
			ServiceReference<PortletToolbarContributorLocator> serviceReference,
			PortletToolbarContributorLocator portletToolbarContributorLocator) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_portletToolbarContributorLocators.remove(
				portletToolbarContributorLocator);
		}

	}

}