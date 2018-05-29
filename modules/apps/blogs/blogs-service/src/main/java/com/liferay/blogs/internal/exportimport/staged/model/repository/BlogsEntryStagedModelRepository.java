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

package com.liferay.blogs.internal.exportimport.staged.model.repository;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.blogs.model.BlogsEntry",
	service = StagedModelRepository.class
)
public class BlogsEntryStagedModelRepository
	implements StagedModelRepository<BlogsEntry> {

	@Override
	public BlogsEntry addStagedModel(
			PortletDataContext portletDataContext, BlogsEntry blogsEntry)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(BlogsEntry blogsEntry)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public BlogsEntry fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public BlogsEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<BlogsEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public BlogsEntry getStagedModel(long entryId) throws PortalException {
		return _blogsEntryLocalService.getBlogsEntry(entryId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, BlogsEntry blogsEntry)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public BlogsEntry saveStagedModel(BlogsEntry blogsEntry)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public BlogsEntry updateStagedModel(
			PortletDataContext portletDataContext, BlogsEntry blogsEntry)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

}