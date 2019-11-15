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

package com.liferay.batch.engine.internal.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.batch.engine.BatchEngineTaskFieldId;
import com.liferay.batch.engine.BatchEngineTaskMethod;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.headless.delivery.dto.v1_0.BlogPosting;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.resource.v1_0.BlogPostingResource;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Ivica Cardic
 */
public class BaseBatchEngineTaskExecutorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();

		user = UserTestUtil.addGroupAdminUser(group);

		baseDate = dateFormat.parse(dateFormat.format(new Date()));

		Bundle bundle = FrameworkUtil.getBundle(
			BatchEngineImportTaskExecutorTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		blogPostingResourceServiceRegistration = bundleContext.registerService(
			BlogPostingResource.class, new TestBlogPostingResourceImpl(),
			new HashMapDictionary<String, String>() {
				{
					put("api.version", "v1.0");
					put("osgi.jaxrs.resource", "true");
				}
			});
	}

	@After
	public void tearDown() throws Exception {
		blogsEntryLocalService.deleteEntries(group.getGroupId());

		blogPostingResourceServiceRegistration.unregister();
	}

	public abstract class BaseBlogPostingResourceImpl
		implements BlogPostingResource {

		@DELETE
		@Override
		@Path("/blog-postings/{blogPostingId}")
		@Produces("application/json")
		public void deleteBlogPosting(
				@PathParam("blogPostingId") Long blogPostingId)
			throws Exception {
		}

		@GET
		@Override
		@Path("/sites/{siteId}/blog-postings")
		@Produces({"application/json", "application/xml"})
		public Page<BlogPosting> getSiteBlogPostingsPage(
				@PathParam("siteId") Long siteId,
				@QueryParam("search") String search, @Context Filter filter,
				@Context Pagination pagination, @Context Sort[] sorts)
			throws Exception {

			return Page.of(Collections.emptyList());
		}

		@Consumes({"application/json", "application/xml"})
		@Override
		@Path("/sites/{siteId}/blog-postings")
		@POST
		@Produces({"application/json", "application/xml"})
		public BlogPosting postSiteBlogPosting(
				@PathParam("siteId") Long siteId, BlogPosting blogPosting)
			throws Exception {

			return new BlogPosting();
		}

		@Consumes({"application/json", "application/xml"})
		@Override
		@Path("/blog-postings/{blogPostingId}")
		@Produces({"application/json", "application/xml"})
		@PUT
		public BlogPosting putBlogPosting(
				@PathParam("blogPostingId") Long blogPostingId,
				BlogPosting blogPosting)
			throws Exception {

			return new BlogPosting();
		}

		protected AcceptLanguage contextAcceptLanguage;
		protected Company contextCompany;
		protected User contextUser;

	}

	public class TestBlogPostingResourceImpl
		extends BaseBlogPostingResourceImpl {

		@BatchEngineTaskMethod(
			batchEngineTaskOperation = BatchEngineTaskOperation.DELETE,
			itemClass = BlogPosting.class
		)
		@Override
		public void deleteBlogPosting(
				@BatchEngineTaskFieldId("id") Long blogPostingId)
			throws Exception {

			_initContextFields();

			blogPostingResource.deleteBlogPosting(blogPostingId);
		}

		@Override
		public void deleteBlogPostingMyRating(Long blogPostingId)
			throws Exception {
		}

		@Override
		public BlogPosting getBlogPosting(Long blogPostingId) throws Exception {
			return null;
		}

		@Override
		public Rating getBlogPostingMyRating(Long blogPostingId)
			throws Exception {

			return null;
		}

		@Override
		public BlogPosting patchBlogPosting(
				Long blogPostingId, BlogPosting blogPosting)
			throws Exception {

			return null;
		}

		@Override
		public Rating postBlogPostingMyRating(Long blogPostingId, Rating rating)
			throws Exception {

			return null;
		}

		@BatchEngineTaskMethod(
			batchEngineTaskOperation = BatchEngineTaskOperation.CREATE,
			itemClass = BlogPosting.class
		)
		@Override
		public BlogPosting postSiteBlogPosting(
				Long siteId, BlogPosting blogPosting)
			throws Exception {

			_initContextFields();

			return blogPostingResource.postSiteBlogPosting(siteId, blogPosting);
		}

		@BatchEngineTaskMethod(
			batchEngineTaskOperation = BatchEngineTaskOperation.UPDATE,
			itemClass = BlogPosting.class
		)
		@Override
		public BlogPosting putBlogPosting(
				@BatchEngineTaskFieldId("id") Long blogPostingId,
				BlogPosting blogPosting)
			throws Exception {

			_initContextFields();

			return blogPostingResource.putBlogPosting(
				blogPostingId, blogPosting);
		}

		@Override
		public Rating putBlogPostingMyRating(Long blogPostingId, Rating rating)
			throws Exception {

			return null;
		}

		@Override
		public void putSiteBlogPostingSubscribe(Long siteId) throws Exception {
		}

		@Override
		public void putSiteBlogPostingUnsubscribe(Long siteId)
			throws Exception {
		}

		@Override
		public void setContextCompany(Company contextCompany) {
		}

		@Override
		public void setContextUser(User contextUser) {
		}

		private void _initContextFields() {
			blogPostingResource.setContextAcceptLanguage(contextAcceptLanguage);
			blogPostingResource.setContextCompany(contextCompany);
			blogPostingResource.setContextUser(contextUser);
		}

	}

	protected List<BlogsEntry> addBlogsEntries() throws Exception {
		List<BlogsEntry> blogsEntries = new ArrayList<>();

		for (int i = 0; i < ROWS_COUNT; i++) {
			blogsEntries.add(
				blogsEntryLocalService.addEntry(
					user.getUserId(), "headline" + i, "alternativeHeadline" + i,
					null, "articleBody" + i, new Date(baseDate.getTime()),
					false, false, null, null, null, null,
					ServiceContextTestUtil.getServiceContext(
						user.getCompanyId(), group.getGroupId(),
						user.getUserId())));
		}

		return blogsEntries;
	}

	protected static final String[] FIELD_NAMES = {
		"alternativeHeadline", "articleBody", "datePublished", "headline",
		"siteId"
	};

	protected static final int ROWS_COUNT = 18;

	protected static final ObjectMapper objectMapper = new ObjectMapper();

	protected Date baseDate;

	@Inject
	protected BlogPostingResource blogPostingResource;

	protected ServiceRegistration<BlogPostingResource>
		blogPostingResourceServiceRegistration;

	@Inject
	protected BlogsEntryLocalService blogsEntryLocalService;

	protected final DateFormat dateFormat = new SimpleDateFormat(
		"yyyy-MM-dd'T'HH:mm:00.000Z");

	@DeleteAfterTestRun
	protected Group group;

	@DeleteAfterTestRun
	protected User user;

}