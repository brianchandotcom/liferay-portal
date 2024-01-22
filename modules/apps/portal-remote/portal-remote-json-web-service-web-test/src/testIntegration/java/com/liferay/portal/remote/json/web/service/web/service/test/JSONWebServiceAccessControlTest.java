/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.aop.AopInvocationHandler;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.lang.reflect.InvocationHandler;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * @author Istvan Sajtos
 */
@RunWith(Arquillian.class)
public class JSONWebServiceAccessControlTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAccessControlled() throws InvalidSyntaxException {
		Bundle bundle = FrameworkUtil.getBundle(
			JSONWebServiceAccessControlTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		for (ServiceReference<?> serviceReference :
				_getJSONWebServiceReferences(bundleContext)) {

			Class<?> clazz = _getTargetClass(
				bundleContext.getService(serviceReference));

			if (!_isAccessControlRequired(clazz)) {
				continue;
			}

			Assert.assertTrue(_isAccessControlled(clazz));
		}
	}

	@Test
	public void testAopEnabled() throws InvalidSyntaxException {
		Bundle bundle = FrameworkUtil.getBundle(
			JSONWebServiceAccessControlTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		for (ServiceReference<?> serviceReference :
				_getJSONWebServiceReferences(bundleContext)) {

			Object service = bundleContext.getService(serviceReference);

			if (!_isAccessControlRequired(_getTargetClass(service))) {
				continue;
			}

			Assert.assertTrue(service instanceof AopService);
		}
	}

	private ServiceReference<?>[] _getJSONWebServiceReferences(
			BundleContext bundleContext)
		throws InvalidSyntaxException {

		return bundleContext.getServiceReferences(
			(String)null,
			"(&(json.web.service.context.path=*)(component.name=*))");
	}

	private Class<?> _getTargetClass(Object service) {
		while (ProxyUtil.isProxyClass(service.getClass())) {
			InvocationHandler invocationHandler =
				ProxyUtil.getInvocationHandler(service);

			if (invocationHandler instanceof AopInvocationHandler) {
				AopInvocationHandler aopInvocationHandler =
					(AopInvocationHandler)invocationHandler;

				service = aopInvocationHandler.getTarget();
			}
			else if (invocationHandler instanceof ClassLoaderBeanHandler) {
				ClassLoaderBeanHandler classLoaderBeanHandler =
					(ClassLoaderBeanHandler)invocationHandler;

				Object bean = classLoaderBeanHandler.getBean();

				if (bean instanceof ServiceWrapper) {
					ServiceWrapper<?> serviceWrapper = (ServiceWrapper<?>)bean;

					service = serviceWrapper.getWrappedService();
				}
				else {
					service = bean;
				}
			}
		}

		return service.getClass();
	}

	private boolean _isAccessControlled(Class<?> clazz) {
		while (clazz != null) {
			if (clazz.isAnnotationPresent(AccessControlled.class)) {
				return true;
			}

			for (Class<?> iface : clazz.getInterfaces()) {
				if (iface.isAnnotationPresent(AccessControlled.class)) {
					return true;
				}
			}

			clazz = clazz.getSuperclass();
		}

		return false;
	}

	private boolean _isAccessControlRequired(Class<?> clazz) {
		String className = clazz.getName();

		if (className.contains("example") || className.contains("test")) {
			return false;
		}

		return true;
	}

}