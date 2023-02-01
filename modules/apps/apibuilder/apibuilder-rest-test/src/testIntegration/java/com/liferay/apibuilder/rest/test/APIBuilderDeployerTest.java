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

package com.liferay.apibuilder.rest.test;

import com.liferay.apibuilder.operation.Operation;
import com.liferay.apibuilder.operation.registrator.OperationRegistrator;
import com.liferay.apibuilder.rest.provider.OpenAPIYAMLOperationsProvider;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.test.util.BlogsTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.vulcan.yaml.YAMLUtil;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;

import java.io.InputStream;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carlos Correa
 */
@RunWith(Arquillian.class)
public class APIBuilderDeployerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-171047", "true"
			).build());

		_blogsEntry = BlogsTestUtil.addEntryWithWorkflow(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), true,
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId()));
	}

	@After
	public void tearDown() throws Exception {
		_unregisterOperations();

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-171047", "false"
			).build());
	}

	@Test
	public void testDeployAPIBuilderApplication() throws Exception {
		JSONObject jsonObject = _invoke(
			"api-builder/v1.0/blogs/" + _blogsEntry.getEntryId(),
			Http.Method.GET);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));

		_registerOperations(
			TestPropsValues.getCompanyId(), "blogs-entry-openapi.yaml");

		jsonObject = _invoke(
			"api-builder/v1.0/blogs/" + _blogsEntry.getEntryId(),
			Http.Method.GET);

		Assert.assertEquals(_blogsEntry.getEntryId(), jsonObject.getLong("id"));
	}

	@Test
	public void testDeployAPIBuilderApplicationForADifferentCompany()
		throws Exception {

		_testCompany = CompanyTestUtil.addCompany();

		JSONObject jsonObject = _invoke(
			"api-builder/v1.0/blogs/" + _blogsEntry.getEntryId(),
			Http.Method.GET);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));

		_registerOperations(
			_testCompany.getCompanyId(), "blogs-entry-openapi.yaml");

		jsonObject = _invoke(
			"api-builder/v1.0/blogs/" + _blogsEntry.getEntryId(),
			Http.Method.GET);

		Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));
	}

	private JSONObject _invoke(String endpoint, Http.Method method)
		throws Exception {

		Http.Options options = new Http.Options();

		options.addHeader(HttpHeaders.ACCEPT, "*/*");
		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		options.addHeader(
			"Authorization",
			"Basic " + Base64.encode("test@liferay.com:test".getBytes()));
		options.setLocation("http://localhost:8080/o/" + endpoint);
		options.setMethod(method);

		return JSONFactoryUtil.createJSONObject(HttpUtil.URLtoString(options));
	}

	private OpenAPIYAML _readOpenAPIYAML(String yamlFile) throws Exception {
		try (InputStream inputStream = getClass().getResourceAsStream(
				yamlFile)) {

			return YAMLUtil.loadOpenAPIYAML(StringUtil.read(inputStream));
		}
	}

	private void _registerOperations(long companyId, String openAPIFileName)
		throws Exception {

		OpenAPIYAML openAPIYAML = _readOpenAPIYAML(
			StringPool.SLASH + openAPIFileName);

		_operations = _openAPIYAMLOperationsProvider.getOperations(
			companyId, openAPIYAML);

		for (Operation operation : _operations) {
			_operationRegistrator.register(operation);
		}
	}

	private void _unregisterOperations() throws Exception {
		for (Operation operation : _operations) {
			_operationRegistrator.unregister(operation);
		}
	}

	private BlogsEntry _blogsEntry;

	@Inject
	private OpenAPIYAMLOperationsProvider _openAPIYAMLOperationsProvider;

	@Inject
	private OperationRegistrator _operationRegistrator;

	private List<Operation> _operations;
	private Company _testCompany;

}