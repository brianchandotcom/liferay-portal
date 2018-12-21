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

package com.liferay.blog.apio.client.test;

import com.liferay.blog.apio.client.test.internal.activator.BlogApioTestBundleActivator;
import com.liferay.oauth2.provider.test.util.OAuth2ProviderTestUtil;
import com.liferay.portal.apio.test.util.ApioClientBuilder;
import com.liferay.portal.apio.test.util.ContentSpaceApioTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Arrays;

import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Víctor Galán
 */
@RunAsClient
@RunWith(Arquillian.class)
public class BlogApioTest {

	@Deployment
	public static Archive<?> getArchive() throws Exception {
		return OAuth2ProviderTestUtil.getArchive(
			BlogApioTestBundleActivator.class);
	}

	@Before
	public void setUp() throws MalformedURLException {
		URL rootEndpointURL = new URL(_url, "/o/api");

		_contentSpaceHref = new URL(
			ContentSpaceApioTestUtil.getContentSpaceHref(
				rootEndpointURL.toExternalForm(),
				BlogApioTestBundleActivator.CONTENT_SPACE_NAME));

		_blogPostsHref = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_contentSpaceHref.toExternalForm()
		).then(
		).extract(
		).path(
			"_links.blogPosts.href"
		);
	}

	@Test
	public void testBlogPostsLinkExistsInContentSpace() {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_contentSpaceHref.toExternalForm()
		).then(
		).statusCode(
			200
		).body(
			"_links.blogPosts.href", IsNull.notNullValue()
		);
	}

	@Test
	public void testCreateBlog() throws Exception {
		String blogHref = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			_read("test-create-blog-posting.json")
		).when(
		).post(
			_blogPostsHref
		).then(
		).statusCode(
			200
		).body(
			"alternativeHeadline", IsEqual.equalTo("alternativeHeadline")
		).body(
			"articleBody", IsEqual.equalTo("articleBody")
		).body(
			"caption", IsEqual.equalTo("caption")
		).body(
			"dateCreated", IsNull.notNullValue()
		).body(
			"dateModified", IsNull.notNullValue()
		).body(
			"datePublished", IsNull.notNullValue()
		).body(
			"description", IsEqual.equalTo("description")
		).body(
			"encodingFormat", IsEqual.equalTo("text/html")
		).body(
			"friendlyUrlPath", IsEqual.equalTo("friendlyurlpath")
		).body(
			"headline", IsEqual.equalTo("headline")
		).body(
			"keywords",
			IsEqual.equalTo(Arrays.asList("keyword1", "keyword2", "keyword3"))
		).extract(
		).path(
			"_links.self.href"
		);

		_deleteBlog(blogHref);
	}

	@Test
	public void testDeleteBlog() throws Exception {
		String blogHref = _createBlog();

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).delete(
			blogHref
		).then(
		).statusCode(
			Matchers.isOneOf(200, 204)
		);
	}

	@Test
	public void testGetBlogPosts() throws Exception {
		String blogHref = _createBlog();

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_blogPostsHref
		).then(
		).statusCode(
			200
		).body(
			"_embedded.BlogPosting[0].alternativeHeadline",
			IsEqual.equalTo("alternativeHeadline")
		).body(
			"_embedded.BlogPosting[0].articleBody",
			IsEqual.equalTo("articleBody")
		).body(
			"_embedded.BlogPosting[0].caption", IsEqual.equalTo("caption")
		).body(
			"_embedded.BlogPosting[0].dateCreated", IsNull.notNullValue()
		).body(
			"_embedded.BlogPosting[0].dateModified", IsNull.notNullValue()
		).body(
			"_embedded.BlogPosting[0].datePublished", IsNull.notNullValue()
		).body(
			"_embedded.BlogPosting[0].description",
			IsEqual.equalTo("description")
		).body(
			"_embedded.BlogPosting[0].encodingFormat",
			IsEqual.equalTo("text/html")
		).body(
			"_embedded.BlogPosting[0].friendlyUrlPath",
			IsEqual.equalTo("friendlyurlpath")
		).body(
			"_embedded.BlogPosting[0].headline", IsEqual.equalTo("headline")
		).body(
			"_embedded.BlogPosting[0].keywords",
			IsEqual.equalTo(Arrays.asList("keyword1", "keyword2", "keyword3"))
		);

		_deleteBlog(blogHref);
	}

	@Test
	public void testUpdateBlog() throws Exception {
		String blogHref = _createBlog();

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			_read("test-update-blog-posting.json")
		).when(
		).put(
			blogHref
		).then(
		).statusCode(
			200
		).body(
			"alternativeHeadline", IsEqual.equalTo("alternativeHeadline")
		).body(
			"articleBody", IsEqual.equalTo("articleBody")
		).body(
			"caption", IsEqual.equalTo("caption")
		).body(
			"dateCreated", IsNull.notNullValue()
		).body(
			"dateModified", IsNull.notNullValue()
		).body(
			"datePublished", IsNull.notNullValue()
		).body(
			"description", IsEqual.equalTo("description")
		).body(
			"encodingFormat", IsEqual.equalTo("text/html")
		).body(
			"friendlyUrlPath", IsEqual.equalTo("friendlyurlpath")
		).body(
			"headline", IsEqual.equalTo("updatedHeadline")
		).body(
			"keywords",
			IsEqual.equalTo(Arrays.asList("keyword1", "keyword2", "keyword3"))
		);

		_deleteBlog(blogHref);
	}

	private String _createBlog() throws Exception {
		return ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			_read("test-create-blog-posting.json")
		).when(
		).post(
			_blogPostsHref
		).then(
		).extract(
		).path(
			"_links.self.href"
		);
	}

	private void _deleteBlog(String blogHref) {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).delete(
			blogHref
		).then(
		).statusCode(
			Matchers.isOneOf(200, 204)
		);
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		URL url = clazz.getResource(fileName);

		return StringUtil.read(url.openStream());
	}

	private String _blogPostsHref;
	private URL _contentSpaceHref;

	@ArquillianResource
	private URL _url;

}