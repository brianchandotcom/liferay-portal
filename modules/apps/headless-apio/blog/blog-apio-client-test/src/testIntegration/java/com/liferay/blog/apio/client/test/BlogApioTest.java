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
import com.liferay.portal.apio.test.util.FileTestUtil;
import com.liferay.portal.apio.test.util.MediaObjectTestUtil;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;

import org.junit.After;
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

		_contentSpaceURL = new URL(
			ContentSpaceApioTestUtil.getContentSpaceHref(
				rootEndpointURL.toExternalForm(),
				BlogApioTestBundleActivator.CONTENT_SPACE_NAME));

		_blogPostsURL = new URL(
			ApioClientBuilder.given(
			).basicAuth(
				"test@liferay.com", "test"
			).header(
				"Accept", "application/hal+json"
			).when(
			).get(
				_contentSpaceURL.toExternalForm()
			).then(
			).extract(
			).path(
				"_links.blogPosts.href"
			));
	}

	@After
	public void tearDown() {
		List<String> blogHrefs = ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_blogPostsURL.toExternalForm()
		).then(
		).extract(
		).path(
			"_embedded.BlogPosting._links.self.href"
		);

		if (blogHrefs != null) {
			Stream<String> blogHrefsStream = blogHrefs.stream();

			blogHrefsStream.forEach(this::_deleteBlog);
		}
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
			_contentSpaceURL.toExternalForm()
		).then(
		).statusCode(
			200
		).body(
			"_links.blogPosts.href", IsNull.notNullValue()
		);
	}

	@Test
	public void testCreateBlog() throws Exception {
		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			FileTestUtil.readFile("test-create-blog-posting.json", getClass())
		).when(
		).post(
			_blogPostsURL.toExternalForm()
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
	}

	@Test
	public void testCreateBlogPostWithImage() throws Exception {
		String documentHref = MediaObjectTestUtil.createDocumentInRootFolder(
			_contentSpaceURL.toExternalForm(),
			FileTestUtil.getFile("image.png", getClass()));

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).header(
			"Content-Type", "application/json"
		).body(
			FileTestUtil.readFile(
				"test-create-blog-posting-with-image.json", getClass(),
				Collections.singletonList(documentHref))
		).when(
		).post(
			_blogPostsURL.toExternalForm()
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
		).body(
			"_links.image", IsNull.notNullValue()
		).extract(
		).path(
			"_links.self.href"
		);
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
		_createBlog();

		ApioClientBuilder.given(
		).basicAuth(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/hal+json"
		).when(
		).get(
			_blogPostsURL.toExternalForm()
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
		).body(
			"_embedded.BlogPosting[0]._links",
			Matchers.not(Matchers.hasKey("image"))
		).body(
			"_embedded.BlogPosting[0]._links.aggregateRating",
			IsNull.notNullValue()
		).body(
			"_embedded.BlogPosting[0]._links.comment", IsNull.notNullValue()
		).body(
			"_embedded.BlogPosting[0]._links.contentSpace",
			IsNull.notNullValue()
		).body(
			"_embedded.BlogPosting[0]._links.creator", IsNull.notNullValue()
		);
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
			FileTestUtil.readFile("test-update-blog-posting.json", getClass())
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
			FileTestUtil.readFile("test-create-blog-posting.json", getClass())
		).when(
		).post(
			_blogPostsURL.toExternalForm()
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

	private URL _blogPostsURL;
	private URL _contentSpaceURL;

	@ArquillianResource
	private URL _url;

}