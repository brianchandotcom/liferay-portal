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

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPDefinitionMedia;
import com.liferay.commerce.product.model.CPMediaType;
import com.liferay.commerce.product.service.base.CPDefinitionMediaLocalServiceBaseImpl;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class CPDefinitionMediaLocalServiceImpl
	extends CPDefinitionMediaLocalServiceBaseImpl {

	public static final String[] SELECTED_FIELD_NAMES =
		{Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID};

	@Override
	public CPDefinitionMedia addCPDefinitionMedia(
			long cpDefinitionId, long fileEntryId, String ddmContent,
			int priority, long cpMediaTypeId, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product definition media

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long cpDefinitionMediaId = counterLocalService.increment();

		CPDefinitionMedia cpDefinitionMedia =
			cpDefinitionMediaPersistence.create(cpDefinitionMediaId);

		cpDefinitionMedia.setUuid(serviceContext.getUuid());
		cpDefinitionMedia.setGroupId(groupId);
		cpDefinitionMedia.setCompanyId(user.getCompanyId());
		cpDefinitionMedia.setUserId(user.getUserId());
		cpDefinitionMedia.setUserName(user.getFullName());
		cpDefinitionMedia.setCPDefinitionId(cpDefinitionId);
		cpDefinitionMedia.setFileEntryId(fileEntryId);
		cpDefinitionMedia.setDDMContent(ddmContent);
		cpDefinitionMedia.setPriority(priority);
		cpDefinitionMedia.setCPMediaTypeId(cpMediaTypeId);

		cpDefinitionMediaPersistence.update(cpDefinitionMedia);

		// Resources

		resourceLocalService.addModelResources(
			cpDefinitionMedia, serviceContext);

		return cpDefinitionMedia;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPDefinitionMedia deleteCPDefinitionMedia(
			CPDefinitionMedia cpMediaType)
		throws PortalException {

		// Commerce product definition media

		cpMediaTypePersistence.remove(cpMediaType);

		// Attachments

		long fileEntryId = cpMediaType.getFileEntryId();

		if (fileEntryId != 0) {
			PortletFileRepositoryUtil.deletePortletFileEntry(fileEntryId);
		}

		return cpMediaType;
	}

	@Override
	public CPMediaType deleteCPMediaType(long cpMediaTypeId)
		throws PortalException {

		CPMediaType cpMediaType = cpMediaTypePersistence.findByPrimaryKey(
			cpMediaTypeId);

		return cpMediaTypeLocalService.deleteCPMediaType(cpMediaType);
	}

	@Override
	public List<CPDefinitionMedia> getDefinitionMedias(
		long cpDefinitionId, int start, int end) {

		return cpDefinitionMediaPersistence.findByCPDefinitionId(
			cpDefinitionId, start, end);
	}

	@Override
	public List<CPDefinitionMedia> getDefinitionMedias(
		long cpDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionMedia> orderByComparator) {

		return cpDefinitionMediaPersistence.findByCPDefinitionId(
			cpDefinitionId, start, end, orderByComparator);
	}

	@Override
	public int getDefinitionMediasCount(long cpDefinitionId) {
		return cpDefinitionMediaPersistence.countByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		try {
			Indexer<CPDefinitionMedia> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(CPDefinitionMedia.class);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public BaseModelSearchResult<CPDefinitionMedia> searchCPDefinitionMedias(
			long companyId, long groupId, long cpDefinitionId, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, cpDefinitionId, keywords, start, end, sort);

		return searchCPDefinitionMedia(searchContext);
	}

	@Override
	public CPDefinitionMedia updateCPDefinitionMedia(
			long cpDefinitionMediaId, String ddmContent, int priority,
			long cpMediaTypeId, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product definition media

		CPDefinitionMedia cpDefinitionMedia =
			cpDefinitionMediaPersistence.findByPrimaryKey(cpDefinitionMediaId);

		cpDefinitionMedia.setDDMContent(ddmContent);
		cpDefinitionMedia.setPriority(priority);
		cpDefinitionMedia.setCPMediaTypeId(cpMediaTypeId);

		cpDefinitionMediaPersistence.update(cpDefinitionMedia);

		return cpDefinitionMedia;
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, long cpDefinitionId, String keywords,
		int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("keywords", keywords);

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(Field.ENTRY_CLASS_PK, keywords);
		attributes.put(Field.TITLE, keywords);
		attributes.put("cpDefinitionId", cpDefinitionId);
		attributes.put("params", params);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setStart(start);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		return searchContext;
	}

	protected Folder doAddFolder(long userId, long groupId, String folderName)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			groupId, CPConstants.SERVICE_NAME, serviceContext);

		Folder folder = PortletFileRepositoryUtil.addPortletFolder(
			userId, repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, folderName,
			serviceContext);

		return folder;
	}

	protected BaseModelSearchResult<CPDefinitionMedia> searchCPDefinitionMedia(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CPDefinitionMedia> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CPDefinitionMedia.class);

		List<CPDefinitionMedia> cpDefinitionMedias = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, SELECTED_FIELD_NAMES);

			Document[] documents = hits.getDocs();

			for (Document document : documents) {
				long classPK = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				CPDefinitionMedia cpDefinitionMedia = getCPDefinitionMedia(
					classPK);

				cpDefinitionMedias.add(cpDefinitionMedia);
			}

			if (cpDefinitionMedias != null) {
				return new BaseModelSearchResult<>(
					cpDefinitionMedias, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

}