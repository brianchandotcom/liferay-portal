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

import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.jmx.StatisticsService;
import org.hibernate.jmx.StatisticsServiceMBean;

/**
 * @author Shuyang Zhou
 */
@OSGiBeanProperties(
	property = "jmx.objectname=Hibernate:name=statistics",
	service = DynamicMBean.class
)
public class HibernateStatisticsService
	extends StatisticsService implements DynamicMBean {

	public HibernateStatisticsService() throws NotCompliantMBeanException {
		setStatisticsEnabled(PropsValues.HIBERNATE_GENERATE_STATISTICS);

		_standardMBean = new StandardMBean(this, StatisticsServiceMBean.class);
	}

	@Override
	public Object getAttribute(String attribute)
		throws AttributeNotFoundException, MBeanException, ReflectionException {

		return _standardMBean.getAttribute(attribute);
	}

	@Override
	public AttributeList getAttributes(String[] attributes) {
		return _standardMBean.getAttributes(attributes);
	}

	@Override
	public MBeanInfo getMBeanInfo() {
		return _standardMBean.getMBeanInfo();
	}

	@Override
	public Object invoke(String actionName, Object[] params, String[] signature)
		throws MBeanException, ReflectionException {

		return _standardMBean.invoke(actionName, params, signature);
	}

	@Override
	public void setAttribute(Attribute attribute)
		throws AttributeNotFoundException, InvalidAttributeValueException,
			   MBeanException, ReflectionException {

		_standardMBean.setAttribute(attribute);
	}

	@Override
	public AttributeList setAttributes(AttributeList attributes) {
		return _standardMBean.setAttributes(attributes);
	}

	public void setSessionFactoryImplementor(
		SessionFactoryImplementor sessionFactoryImplementor) {

		super.setSessionFactory(sessionFactoryImplementor);
	}

	private final StandardMBean _standardMBean;

}