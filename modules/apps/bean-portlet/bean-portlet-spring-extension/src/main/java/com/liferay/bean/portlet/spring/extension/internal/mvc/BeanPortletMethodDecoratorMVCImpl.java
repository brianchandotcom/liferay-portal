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

package com.liferay.bean.portlet.spring.extension.internal.mvc;

import com.liferay.bean.portlet.extension.BeanPortletMethod;
import com.liferay.bean.portlet.extension.BeanPortletMethodDecorator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import javax.annotation.ManagedBean;

import javax.mvc.Controller;
import javax.mvc.MvcContext;
import javax.mvc.binding.BindingResult;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.validation.MessageInterpolator;
import javax.validation.Validator;

import javax.ws.rs.core.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @author Neil Griffin
 */
@ManagedBean("beanPortletMethodDecorator")
public class BeanPortletMethodDecoratorMVCImpl
	implements ApplicationContextAware, BeanPortletMethodDecorator {

	@Override
	public BeanPortletMethod getBeanMethod(
		BeanPortletMethod beanPortletMethod, PortletConfig portletConfig,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		boolean controller = _isController(beanPortletMethod);

		Object target = _getTarget(beanPortletMethod.getBeanType());

		return new ControllerInterceptor(
			_applicationEventPublisher,
			new BeanValidationInterceptor(
				new CsrfValidationInterceptor(
					beanPortletMethod, _configuration, controller),
				controller, _messageInterpolator,
				(MutableBindingResult)_bindingResult, _mvcContext, target,
				_validator),
			controller, portletRequest, portletResponse, target);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		_applicationContext = applicationContext;
	}

	private Object _getTarget(Class<?> beanClass) {
		Object target = _applicationContext.getBean(beanClass);

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());

			PropertyDescriptor[] propertyDescriptors =
				beanInfo.getPropertyDescriptors();

			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				String propertyDescriptorName = propertyDescriptor.getName();

				if (propertyDescriptorName.equals("targetObject")) {
					Method readMethod = propertyDescriptor.getReadMethod();

					target = readMethod.invoke(target);

					break;
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return target;
	}

	private boolean _isController(BeanPortletMethod beanPortletMethod) {
		Method method = beanPortletMethod.getMethod();

		if (method.isAnnotationPresent(Controller.class)) {
			return true;
		}

		Class<?> declaringClass = method.getDeclaringClass();

		if (declaringClass.isAnnotationPresent(Controller.class)) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BeanPortletMethodDecoratorMVCImpl.class);

	private ApplicationContext _applicationContext;

	@Autowired
	private ApplicationEventPublisher _applicationEventPublisher;

	@Autowired
	private BindingResult _bindingResult;

	@Autowired
	private Configuration _configuration;

	@Autowired
	@BeanValidationMessageInterpolator
	private MessageInterpolator _messageInterpolator;

	@Autowired
	private MvcContext _mvcContext;

	@Autowired
	@BeanValidationValidator
	private Validator _validator;

}