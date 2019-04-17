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

package com.liferay.portal.remote.axis.extender.client.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.remote.axis.extender.test.service.http.CalcServiceSoap;
import com.liferay.portal.remote.axis.extender.test.service.http.CalcServiceSoapService;
import com.liferay.portal.remote.axis.extender.test.service.http.CalcServiceSoapServiceLocator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.net.URL;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carlos Sierra Andrés
 */
@RunWith(Arquillian.class)
public class AxisExtenderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGreeter() throws Exception {
		URL url = new URL(
			"http://localhost:8080/o/com.liferay.portal.remote.axis.extender." +
				"test/api/axis/CalcService?wsdl");

		CalcServiceSoapService calcServiceSoapService =
			new CalcServiceSoapServiceLocator();

		CalcServiceSoap calcServiceSoap =
			calcServiceSoapService.getCalcServiceSoapPort(url);

		Assert.assertEquals(5, calcServiceSoap.sum(2, 3));
	}

}