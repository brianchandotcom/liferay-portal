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

package com.liferay.portal.spring.extender.internal.context.event;

import com.liferay.portal.bean.BeanLocatorImpl;
import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.spring.bean.BeanReferenceRefreshUtil;
import com.liferay.portal.spring.extender.internal.classloader.BundleResolverClassLoader;

import java.beans.Introspector;

import org.eclipse.gemini.blueprint.context.event.OsgiBundleApplicationContextEvent;
import org.eclipse.gemini.blueprint.context.event.OsgiBundleApplicationContextListener;
import org.eclipse.gemini.blueprint.context.event.OsgiBundleContextClosedEvent;
import org.eclipse.gemini.blueprint.context.event.OsgiBundleContextRefreshedEvent;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;

import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.context.ApplicationContext;

/**
 * @author Miguel Pastor
 */
public class BeanLocatorLifecycleManager
	implements
		OsgiBundleApplicationContextListener
			<OsgiBundleApplicationContextEvent> {

	@Override
	public void onOsgiApplicationEvent(
		OsgiBundleApplicationContextEvent osgiBundleApplicationContextEvent) {

		Bundle bundle = osgiBundleApplicationContextEvent.getBundle();

		String symbolicName = bundle.getSymbolicName();

		if (osgiBundleApplicationContextEvent
				instanceof OsgiBundleContextRefreshedEvent) {

			PortletBeanLocatorUtil.setBeanLocator(
				symbolicName,
				_buildBeanLocator(osgiBundleApplicationContextEvent));

			_refreshContext(
				osgiBundleApplicationContextEvent.getApplicationContext());
		}
		else if (osgiBundleApplicationContextEvent
					instanceof OsgiBundleContextClosedEvent) {

			PortletBeanLocatorUtil.setBeanLocator(symbolicName, null);

			_cleanInstropectionCaches(bundle);
		}
	}

	private BeanLocator _buildBeanLocator(
		OsgiBundleApplicationContextEvent osgiBundleApplicationContextEvent) {

		Bundle bundle = osgiBundleApplicationContextEvent.getBundle();

		ClassLoader classLoader = new BundleResolverClassLoader(bundle);

		return new BeanLocatorImpl(
			classLoader,
			osgiBundleApplicationContextEvent.getApplicationContext());
	}

	private void _cleanInstropectionCaches(Bundle bundle) {
		ClassLoader classLoader = _getClassLoader(bundle);

		CachedIntrospectionResults.clearClassLoader(classLoader);

		Introspector.flushCaches();
	}

	private ClassLoader _getClassLoader(Bundle bundle) {
		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		return bundleWiring.getClassLoader();
	}

	private void _refreshContext(ApplicationContext applicationContext) {
		try {
			BeanReferenceRefreshUtil.refresh(
				applicationContext.getAutowireCapableBeanFactory());
		}
		catch (Exception e) {
			if ( _log.isErrorEnabled())
				_log.error(
					"Unexpected error while refreshing " +
						applicationContext.getDisplayName() + ". This could " +
							"cause some memory leaks on multiple " +
								" re-deployments");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BeanLocatorLifecycleManager.class);

}