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

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.NotCompliantMBeanException;
import javax.management.ReflectionException;
import javax.management.StandardMBean;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.stat.Statistics;

/**
 * @author Shuyang Zhou
 */
@OSGiBeanProperties(property = "jmx.objectname=Hibernate:name=statistics")
public class HibernateStatisticsService implements DynamicMBean {

	public void afterPropertiesSet() throws NotCompliantMBeanException {
		_dynamicMBean = new StandardMBean(_statistics, Statistics.class);
	}

	@Override
	public Object getAttribute(String attribute)
		throws AttributeNotFoundException, MBeanException, ReflectionException {

		return _dynamicMBean.getAttribute(attribute);
	}

	@Override
	public AttributeList getAttributes(String[] attributes) {
		return _dynamicMBean.getAttributes(attributes);
	}

	@Override
	public MBeanInfo getMBeanInfo() {
		return _dynamicMBean.getMBeanInfo();
	}

	@Override
	public Object invoke(String actionName, Object[] params, String[] signature)
		throws MBeanException, ReflectionException {

		return _dynamicMBean.invoke(actionName, params, signature);
	}

	@Override
	public void setAttribute(Attribute attribute)
		throws AttributeNotFoundException, InvalidAttributeValueException,
			   MBeanException, ReflectionException {

		_dynamicMBean.setAttribute(attribute);
	}

	@Override
	public AttributeList setAttributes(AttributeList attributes) {
		return _dynamicMBean.setAttributes(attributes);
	}

	public void setSessionFactoryImplementor(
		SessionFactoryImplementor sessionFactoryImplementor) {

		_statistics = sessionFactoryImplementor.getStatistics();

		_statistics.setStatisticsEnabled(
			PropsValues.HIBERNATE_GENERATE_STATISTICS);
	}

	private DynamicMBean _dynamicMBean;
	private Statistics _statistics;

}