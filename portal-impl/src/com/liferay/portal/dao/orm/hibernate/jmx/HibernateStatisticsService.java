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

import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.util.PropsValues;

import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.jmx.StatisticsService;

/**
 * @author Shuyang Zhou
 */
@OSGiBeanProperties(property = {"jmx.objectname=Hibernate:name=statistics"})
public class HibernateStatisticsService extends StatisticsService {

	public HibernateStatisticsService() {
		setStatisticsEnabled(PropsValues.HIBERNATE_GENERATE_STATISTICS);
	}

	public void setSessionFactoryImplementor(
		SessionFactoryImplementor sessionFactoryImplementor) {

		super.setSessionFactory(sessionFactoryImplementor);
	}

}