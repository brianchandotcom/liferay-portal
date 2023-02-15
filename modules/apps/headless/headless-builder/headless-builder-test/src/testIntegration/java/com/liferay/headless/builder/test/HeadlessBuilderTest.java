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

package com.liferay.headless.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.test.util.BlogsTestUtil;
import com.liferay.headless.builder.application.HeadlessBuilderApplication;
import com.liferay.headless.builder.application.HeadlessBuilderApplicationFactory;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
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

import java.text.SimpleDateFormat;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Carlos Correa
 */
@RunWith(Arquillian.class)
public class HeadlessBuilderTest {

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
	public void tearDown() {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-171047", "false"
			).build());
	}

	@Test
	public void testHeadlessBuilderApplication() throws Exception {
		_withHeadlessBuilderApplication(
			TestPropsValues.getCompanyId(), "blogs-entry-openapi.yaml",
			() -> {
				JSONObject jsonObject = _invoke(
					"headless-builder/v1.0/blogs/" + _blogsEntry.getEntryId(),
					Http.Method.GET);

				JSONAssert.assertEquals(
					JSONUtil.put(
						"alternativeHeadline", _blogsEntry.getSubtitle()
					).put(
						"articleBody", _blogsEntry.getContent()
					).put(
						"dateCreated", _formatDate(_blogsEntry.getCreateDate())
					).put(
						"dateModified",
						_formatDate(_blogsEntry.getModifiedDate())
					).put(
						"datePublished",
						_formatDate(_blogsEntry.getDisplayDate())
					).put(
						"description", _blogsEntry.getDescription()
					).put(
						"externalReferenceCode",
						_blogsEntry.getExternalReferenceCode()
					).put(
						"friendlyUrlPath", _blogsEntry.getUrlTitle()
					).put(
						"headline", _blogsEntry.getTitle()
					).put(
						"id", (int)_blogsEntry.getEntryId()
					).put(
						"siteId", (int)_blogsEntry.getGroupId()
					).toString(),
					jsonObject.toString(), true);
			});
	}

	@Test
	public void testHeadlessBuilderApplicationOnADifferentCompany()
		throws Exception {

		_withHeadlessBuilderApplication(
			0, "blogs-entry-openapi.yaml",
			() -> {
				JSONObject jsonObject = _invoke(
					"headless-builder/v1.0/blogs/" + _blogsEntry.getEntryId(),
					Http.Method.GET);

				JSONAssert.assertEquals(
					JSONUtil.put(
						"status", "NOT_FOUND"
					).put(
						"title", "Operation not found"
					).toString(),
					jsonObject.toString(), true);
			});
	}

	@Test
	public void testMissingHeadlessBuilderApplication() throws Exception {
		JSONObject jsonObject = _invoke(
			"headless-builder/v1.0/test/1234", Http.Method.GET);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "NOT_FOUND"
			).put(
				"title", "Operation not found"
			).toString(),
			jsonObject.toString(), true);
	}

	private String _formatDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		return simpleDateFormat.format(date);
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

	private void _withHeadlessBuilderApplication(
			long companyId, String openApiYamlFile,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		HeadlessBuilderApplication headlessBuilderApplication =
			_headlessBuilderApplicationFactory.getHeadlessBuilderApplication(
				companyId,
				_readOpenAPIYAML(StringPool.SLASH + openApiYamlFile));

		HeadlessBuilderApplication.Handle handle =
			headlessBuilderApplication.deploy();

		try {
			unsafeRunnable.run();
		}
		finally {
			handle.undeploy();
		}
	}

	private BlogsEntry _blogsEntry;

	@Inject
	private HeadlessBuilderApplicationFactory
		_headlessBuilderApplicationFactory;

}