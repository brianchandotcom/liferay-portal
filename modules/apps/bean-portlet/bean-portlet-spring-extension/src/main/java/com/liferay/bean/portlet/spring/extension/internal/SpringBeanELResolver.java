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

package com.liferay.bean.portlet.spring.extension.internal;

import java.beans.FeatureDescriptor;

import java.util.Iterator;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;

import javax.portlet.PortletRequest;

import org.springframework.beans.factory.BeanFactory;

/**
 * @author Neil Griffin
 */
public class SpringBeanELResolver extends ELResolver {

	public SpringBeanELResolver(BeanFactory beanFactory) {
		_beanFactory = beanFactory;
	}

	@Override
	public Class<?> getCommonPropertyType(ELContext elContext, Object base) {
		return null;
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(
		ELContext elContext, Object base) {

		return null;
	}

	@Override
	public Class<?> getType(ELContext elContext, Object base, Object property)
		throws ELException, NullPointerException, PropertyNotFoundException {

		return null;
	}

	@Override
	public Object getValue(ELContext elContext, Object base, Object property)
		throws ELException, NullPointerException, PropertyNotFoundException {

		if ((base == null) && (property != null)) {
			String beanName = property.toString();

			if (_beanFactory.containsBean(beanName)) {
				PortletRequest portletRequest = _beanFactory.getBean(
					"portletRequest", PortletRequest.class);

				Object bean = portletRequest.getAttribute(beanName);

				if (bean == null) {
					bean = _beanFactory.getBean(beanName);
				}

				if (bean != null) {
					elContext.setPropertyResolved(true);

					return bean;
				}
			}
		}

		return null;
	}

	@Override
	public boolean isReadOnly(ELContext elContext, Object base, Object property)
		throws ELException, NullPointerException, PropertyNotFoundException {

		return false;
	}

	@Override
	public void setValue(
			ELContext elContext, Object base, Object property, Object value)
		throws ELException, NullPointerException, PropertyNotFoundException,
			   PropertyNotWritableException {
	}

	private final BeanFactory _beanFactory;

}