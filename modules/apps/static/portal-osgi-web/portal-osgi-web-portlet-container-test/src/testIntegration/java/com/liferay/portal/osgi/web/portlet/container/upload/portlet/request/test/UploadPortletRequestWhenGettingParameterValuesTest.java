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

package com.liferay.portal.osgi.web.portlet.container.upload.portlet.request.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.osgi.web.portlet.container.test.util.PortletContainerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upload.LiferayServletRequest;
import com.liferay.portal.upload.UploadPortletRequestImpl;
import com.liferay.portal.upload.UploadServletRequestImpl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class UploadPortletRequestWhenGettingParameterValuesTest {

	@ClassRule
	@Rule
	public static final TestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_portletNamespace = RandomTestUtil.randomString();
	}

	@Test
	public void testShouldMergeRegularAndRequestParameters() throws Exception {
		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				10, _portletNamespace, _BYTES);

		Map<String, List<String>> regularParameters =
			PortletContainerTestUtil.getRegularParameters(10);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_portletNamespace, _BYTES);

		String parameter = RandomTestUtil.randomString();

		MockHttpServletRequest mockHttpServletRequest =
			(MockHttpServletRequest)liferayServletRequest.getRequest();

		mockHttpServletRequest.addParameter(parameter, parameter);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, regularParameters),
				null, _portletNamespace);

		for (Map.Entry<String, List<String>> entry :
				regularParameters.entrySet()) {

			String key = entry.getKey();

			String[] parameterValues = uploadPortletRequest.getParameterValues(
				key);

			List<String> parameterValuesList = ListUtil.fromArray(
				parameterValues);

			Assert.assertTrue(
				parameterValuesList.containsAll(entry.getValue()));
		}

		String[] requestParameterValues =
			uploadPortletRequest.getParameterValues(parameter);

		Assert.assertTrue(
			ArrayUtil.contains(requestParameterValues, parameter));

		for (Map.Entry<String, FileItem[]> entry : fileParameters.entrySet()) {
			String key = entry.getKey();

			String[] parameterValues = uploadPortletRequest.getParameterValues(
				key);

			List<String> parameterValuesList = ListUtil.fromArray(
				parameterValues);

			Assert.assertFalse(
				parameterValuesList.toString(),
				parameterValuesList.contains(key));
		}
	}

	private static final byte[] _BYTES =
		"Enterprise. Open Source. For Life.".getBytes();

	private static String _portletNamespace;

}