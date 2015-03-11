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

package com.liferay.portal.dao.orm.hibernate.jmx;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author Michael C. Han
 */
public class HibernateStatisticsJmxAdapter {

	public void afterPropertiesSet() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			MBeanServer.class, new MBeanServerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void destroy() {
		_serviceTracker.close();
	}

	public void setHibernateStatisticsService(
		HibernateStatisticsService hibernateStatisticsService) {

		_hibernateStatisticsService = hibernateStatisticsService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HibernateStatisticsJmxAdapter.class);

	private HibernateStatisticsService _hibernateStatisticsService;
	private ServiceTracker<MBeanServer, MBeanServer> _serviceTracker;

	private class MBeanServerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<MBeanServer, MBeanServer> {

		@Override
		public MBeanServer addingService(
			ServiceReference<MBeanServer> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			MBeanServer mBeanServer = registry.getService(serviceReference);

			try {
				mBeanServer.registerMBean(
					_hibernateStatisticsService,
					new ObjectName("Hibernate:name=statistics"));
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to register Hibernate stats", e);
				}
			}

			return mBeanServer;
		}

		@Override
		public void modifiedService(
			ServiceReference<MBeanServer> serviceReference,
			MBeanServer mBeanServer) {
		}

		@Override
		public void removedService(
			ServiceReference<MBeanServer> serviceReference,
			MBeanServer mBeanServer) {
		}

	}

}