/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import javax.portlet.PortletRequest;

import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Alessio Antonio Rendina
 */
public class CPNavigationItemRegistryUtil {

	public static List<NavigationItem> getNavigationItems(
			PortletRequest portletRequest)
		throws PortalException {

		CPNavigationItemRegistry cpNavigationItemRegistry =
			_serviceTracker.getService();

		return cpNavigationItemRegistry.getNavigationItems(portletRequest);
	}

	private static final ServiceTracker<?, CPNavigationItemRegistry>
		_serviceTracker = ServiceTrackerFactory.open(
			CPNavigationItemRegistry.class);

}