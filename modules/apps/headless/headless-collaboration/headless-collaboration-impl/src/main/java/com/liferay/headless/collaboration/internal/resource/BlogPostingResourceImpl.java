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

package com.liferay.headless.collaboration.internal.resource;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.headless.collaboration.dto.BlogPosting;
import com.liferay.headless.collaboration.dto.BlogPostingCollection;
import com.liferay.headless.collaboration.resource.BlogPostingResource;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.context.Pagination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=headless-collaboration-application.rest)",
		JaxrsWhiteboardConstants.JAX_RS_RESOURCE + "=true", "api.version=1.0.0"
	},
	scope = ServiceScope.PROTOTYPE, service = BlogPostingResource.class
)
@Generated("")
public class BlogPostingResourceImpl implements BlogPostingResource {

	@Override
	public BlogPostingCollection<BlogPosting> getBlogPostingCollection(
			Pagination pagination, String size)
		throws Exception {

		Company company = _companyService.getCompanyByWebId(
			PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID));

		Group group = company.getGroup();

		List<BlogsEntry> blogsEntries = _blogsEntryService.getGroupEntries(
			group.getGroupId(), WorkflowConstants.STATUS_APPROVED,
			pagination.getStartPosition(), pagination.getEndPosition());

		List<BlogPosting> blogPostings = new ArrayList<>(blogsEntries.size());

		for (BlogsEntry blogsEntry : blogsEntries) {
			BlogPosting blogPosting = new BlogPosting();

			blogPosting.setId(blogsEntry.getEntryId());

			blogPostings.add(blogPosting);
		}
		
		int count = _blogsEntryService.getGroupEntriesCount(
			group.getGroupId(), WorkflowConstants.STATUS_APPROVED);

		return new BlogPostingCollection<>(blogPostings, count);
	}

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private CompanyService _companyService;
}