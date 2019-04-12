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

package com.liferay.portal.jmx;

import com.liferay.portal.jmx.bundle.jmxwhiteboard.JMXWhiteboardByDynamicMBean;
import com.liferay.portal.jmx.bundle.jmxwhiteboard.JMXWhiteboardByInterfaceMBean;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleClassTestRule;

import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class JMXWhiteboardTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleClassTestRule("bundle.jmxwhiteboard"));

	@Test
	public void testMBeanByDynamicMBean() throws Exception {
		ObjectName objectName = new ObjectName(
			JMXWhiteboardByDynamicMBean.OBJECT_NAME);

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

	@Test
	public void testMBeanBySuffix() throws Exception {
		ObjectName objectName = new ObjectName(
			JMXWhiteboardByInterfaceMBean.OBJECT_NAME);

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

	@Inject
	private static MBeanServer _mBeanServer;

}