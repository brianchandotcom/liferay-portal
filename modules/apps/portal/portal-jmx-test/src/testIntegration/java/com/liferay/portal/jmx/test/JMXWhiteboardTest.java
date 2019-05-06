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

package com.liferay.portal.jmx.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import javax.management.DynamicMBean;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.StandardMBean;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
public class JMXWhiteboardTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(JMXWhiteboardTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testMBeanByDynamicMBean() throws Exception {
		_serviceRegistration = _bundleContext.registerService(
			DynamicMBean.class, new JMXWhiteboardByDynamicMBean(),
			MapUtil.singletonDictionary(
				"jmx.objectname", JMXWhiteboardByDynamicMBean.OBJECT_NAME));

		_assertMBean(new ObjectName(JMXWhiteboardByDynamicMBean.OBJECT_NAME));
	}

	@Test
	public void testMBeanBySuffix() throws Exception {
		_serviceRegistration = _bundleContext.registerService(
			JMXWhiteboardByInterfaceMBean.class, new JMXWhiteboardByInterface(),
			MapUtil.singletonDictionary(
				"jmx.objectname", JMXWhiteboardByInterfaceMBean.OBJECT_NAME));

		_assertMBean(new ObjectName(JMXWhiteboardByInterfaceMBean.OBJECT_NAME));
	}

	public interface JMXWhiteboardByInterfaceMBean {

		public static final String OBJECT_NAME =
			"JMXWhiteboard:name=com.liferay.portal.jmx.JMXWhiteboardTest." +
				"JMXWhiteboardByInterfaceMBean";

		public String returnValue(String value);

	}

	private void _assertMBean(ObjectName objectName) throws Exception {
		MBeanInfo mBeanInfo = _mBeanServer.getMBeanInfo(objectName);

		Assert.assertNotNull(mBeanInfo);

		MBeanOperationInfo[] operations = mBeanInfo.getOperations();

		MBeanOperationInfo mBeanOperationInfo = operations[0];

		MBeanParameterInfo[] signatureParameters =
			mBeanOperationInfo.getSignature();

		String[] sinature = new String[signatureParameters.length];

		for (int i = 0; i < signatureParameters.length; i++) {
			MBeanParameterInfo mBeanParameterInfo = signatureParameters[i];

			sinature[i] = mBeanParameterInfo.getType();
		}

		Object result = _mBeanServer.invoke(
			objectName, mBeanOperationInfo.getName(), new Object[] {"Hello!"},
			sinature);

		Assert.assertEquals("{Hello!}", result);
	}

	private static BundleContext _bundleContext;

	@Inject
	private static MBeanServer _mBeanServer;

	private static ServiceRegistration<?> _serviceRegistration;

	private class JMXWhiteboardByDynamicMBean
		extends StandardMBean implements JMXWhiteboardByInterfaceMBean {

		public static final String OBJECT_NAME =
			"JMXWhiteboard:name=com.liferay.portal.jmx.JMXWhiteboardTest." +
				"JMXWhiteboardByDynamicMBean";

		public JMXWhiteboardByDynamicMBean() throws NotCompliantMBeanException {
			super(JMXWhiteboardByInterfaceMBean.class);
		}

		@Override
		public String returnValue(String value) {
			return "{" + value + "}";
		}

	}

	private class JMXWhiteboardByInterface
		implements JMXWhiteboardByInterfaceMBean {

		@Override
		public String returnValue(String value) {
			return "{" + value + "}";
		}

	}

}