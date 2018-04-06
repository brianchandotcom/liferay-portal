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

package com.liferay.portal.kernel.portlet.toolbar.bundle.portlettoolbar;

import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.portlet.toolbar.contributor.locator.PortletToolbarContributorLocator;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Philip Jones
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE
)
public class TestPortletToolbarContributorLocator
	implements PortletToolbarContributorLocator {

	@Override
	public List<PortletToolbarContributor> getPortletToolbarContributors(
		String portletId, PortletRequest portletRequest) {

		List<PortletToolbarContributor> portletToolbarContributors =
			new ArrayList<>();

		TestPortletToolbarContributor testPortletToolbarContributor =
			new TestPortletToolbarContributor();

		portletToolbarContributors.add(testPortletToolbarContributor);

		return portletToolbarContributors;
	}

}