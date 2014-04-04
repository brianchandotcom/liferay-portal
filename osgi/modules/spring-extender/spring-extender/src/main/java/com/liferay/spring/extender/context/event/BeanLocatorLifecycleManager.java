/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.spring.extender.context.event;

import com.liferay.portal.bean.BeanLocatorImpl;
import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.spring.extender.classloader.BundleResolverClassLoader;

import org.eclipse.gemini.blueprint.context.event.OsgiBundleApplicationContextEvent;
import org.eclipse.gemini.blueprint.context.event.OsgiBundleApplicationContextListener;
import org.eclipse.gemini.blueprint.context.event.OsgiBundleContextClosedEvent;
import org.eclipse.gemini.blueprint.context.event.OsgiBundleContextRefreshedEvent;

import org.osgi.framework.Bundle;

/**
 * @author Miguel Pastor
 */
public class BeanLocatorLifecycleManager
	implements OsgiBundleApplicationContextListener {

	@Override
	public void onOsgiApplicationEvent(
		OsgiBundleApplicationContextEvent event) {

		String symbolicName = event.getBundle().getSymbolicName();

		if (event instanceof OsgiBundleContextRefreshedEvent) {
			PortletBeanLocatorUtil.setBeanLocator(
				symbolicName, _buildBeanLocator(event));
		}
		else if (event instanceof OsgiBundleContextClosedEvent) {
			OsgiBundleContextClosedEvent closedEvent =
				(OsgiBundleContextClosedEvent)event;

			Throwable error = closedEvent.getFailureCause();

			if (error != null) {
				PortletBeanLocatorUtil.setBeanLocator(symbolicName, null);
			}
		}
	}

	private BeanLocator _buildBeanLocator(
		OsgiBundleApplicationContextEvent event) {

		Bundle bundle = event.getBundle();

		ClassLoader classLoader = new BundleResolverClassLoader(bundle);

		return new BeanLocatorImpl(classLoader, event.getApplicationContext());
	}

}