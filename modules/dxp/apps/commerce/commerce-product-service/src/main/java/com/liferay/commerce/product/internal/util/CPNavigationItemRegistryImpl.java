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

package com.liferay.commerce.product.internal.util;

import com.liferay.commerce.product.internal.util.comparator.CPNavigationItemServiceWrapperOrderComparator;
import com.liferay.commerce.product.util.CPNavigationItem;
import com.liferay.commerce.product.util.CPNavigationItemRegistry;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.portlet.PortletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = CPNavigationItemRegistry.class)
public class CPNavigationItemRegistryImpl implements CPNavigationItemRegistry {

	@Override
	public List<NavigationItem> getNavigationItems(
			PortletRequest portletRequest)
		throws PortalException {

		List<NavigationItem> navigationItems = new ArrayList<>();

		List<ServiceWrapper<CPNavigationItem>> cpNavigationItemServiceWrappers =
			ListUtil.fromCollection(_serviceTrackerMap.values());

		Collections.sort(
			cpNavigationItemServiceWrappers,
			_cpNavigationItemServiceWrapperOrderComparator);

		for (ServiceWrapper<CPNavigationItem> cpNavigationItemServiceWrapper :
				cpNavigationItemServiceWrappers) {

			CPNavigationItem cpNavigationItem =
				cpNavigationItemServiceWrapper.getService();

			navigationItems.add(
				cpNavigationItem.getNavigationItem(portletRequest));
		}

		return navigationItems;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CPNavigationItem.class,
			"commerce.product.navigation.item.key",
			ServiceTrackerCustomizerFactory.<CPNavigationItem>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Comparator<ServiceWrapper<CPNavigationItem>>
		_cpNavigationItemServiceWrapperOrderComparator =
			new CPNavigationItemServiceWrapperOrderComparator();

	private ServiceTrackerMap<String, ServiceWrapper<CPNavigationItem>>
		_serviceTrackerMap;

}