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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.ExportImportConfigurationLocalServiceBaseImpl;
import com.liferay.portlet.trash.model.TrashEntry;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Kocsis
 * @author Akos Thurzo
 */
public class ExportImportConfigurationLocalServiceImpl
	extends ExportImportConfigurationLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ExportImportConfiguration addExportImportConfiguration(
			long userId, long groupId, String name, String description,
			int type, Map<String, Serializable> settingsMap, int status,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		long exportImportConfigurationId = counterLocalService.increment();

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationPersistence.create(
				exportImportConfigurationId);

		exportImportConfiguration.setGroupId(groupId);
		exportImportConfiguration.setCompanyId(user.getCompanyId());
		exportImportConfiguration.setUserId(userId);
		exportImportConfiguration.setUserName(user.getFullName());
		exportImportConfiguration.setCreateDate(
			serviceContext.getCreateDate(now));
		exportImportConfiguration.setModifiedDate(
			serviceContext.getModifiedDate(now));
		exportImportConfiguration.setName(name);
		exportImportConfiguration.setDescription(description);
		exportImportConfiguration.setType(type);

		if (settingsMap != null) {
			String settings = JSONFactoryUtil.serialize(settingsMap);

			exportImportConfiguration.setSettings(settings);
		}

		exportImportConfiguration.setStatus(status);
		exportImportConfiguration.setStatusByUserId(userId);
		exportImportConfiguration.setStatusByUserName(user.getScreenName());
		exportImportConfiguration.setStatusDate(now);

		return exportImportConfigurationPersistence.update(
			exportImportConfiguration);
	}

	@Override
	public ExportImportConfiguration addExportImportConfiguration(
			long userId, long groupId, String name, String description,
			int type, Map<String, Serializable> settingsMap,
			ServiceContext serviceContext)
		throws PortalException {

		return exportImportConfigurationLocalService.
			addExportImportConfiguration(
				userId, groupId, name, description, type, settingsMap,
				WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public ExportImportConfiguration deleteExportImportConfiguration(
			ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		exportImportConfigurationPersistence.remove(exportImportConfiguration);

		trashEntryLocalService.deleteEntry(
			ExportImportConfiguration.class.getName(),
			exportImportConfiguration.getExportImportConfigurationId());

		return exportImportConfiguration;
	}

	@Override
	public ExportImportConfiguration deleteExportImportConfiguration(
			long exportImportConfigurationId)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationPersistence.findByPrimaryKey(
				exportImportConfigurationId);

		return exportImportConfigurationLocalService.
			deleteExportImportConfiguration(exportImportConfiguration);
	}

	@Override
	public void deleteExportImportConfigurations(long groupId)
		throws PortalException {

		List<ExportImportConfiguration> exportImportConfigurations =
			exportImportConfigurationPersistence.findByGroupId(groupId);

		for (ExportImportConfiguration exportImportConfiguration :
				exportImportConfigurations) {

			exportImportConfigurationLocalService.
				deleteExportImportConfiguration(exportImportConfiguration);
		}
	}

	@Override
	public List<ExportImportConfiguration> getExportImportConfigurations(
			Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<ExportImportConfiguration> exportImportConfigurations =
			new ArrayList<>(documents.size());

		for (Document document : documents) {
			long exportImportConfigurationId = GetterUtil.getLong(
				document.get("exportImportConfigurationId"));

			ExportImportConfiguration exportImportConfiguration =
				exportImportConfigurationLocalService.
					getExportImportConfiguration(exportImportConfigurationId);

			if (exportImportConfiguration == null) {
				exportImportConfigurations = null;

				Indexer indexer = IndexerRegistryUtil.getIndexer(
					ExportImportConfiguration.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (exportImportConfigurations != null) {
				exportImportConfigurations.add(exportImportConfiguration);
			}
		}

		return exportImportConfigurations;
	}

	@Override
	public List<ExportImportConfiguration> getExportImportConfigurations(
		long groupId, int type) {

		return exportImportConfigurationPersistence.findByG_T_S(
			groupId, type, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public List<ExportImportConfiguration> getExportImportConfigurations(
		long groupId, int type, int start, int end,
		OrderByComparator<ExportImportConfiguration> orderByComparator) {

		return exportImportConfigurationPersistence.findByG_T_S(
			groupId, type, WorkflowConstants.STATUS_APPROVED, start, end,
			orderByComparator);
	}

	@Override
	public int getExportImportConfigurationsCount(long groupId) {
		return exportImportConfigurationPersistence.countByG_S(
			groupId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public int getExportImportConfigurationsCount(long groupId, int type) {
		return exportImportConfigurationPersistence.countByG_T_S(
			groupId, type, WorkflowConstants.STATUS_APPROVED);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ExportImportConfiguration moveExportImportConfigurationToTrash(
			long userId, long exportImportConfigurationId)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationPersistence.findByPrimaryKey(
				exportImportConfigurationId);

		int oldStatus = exportImportConfiguration.getStatus();

		exportImportConfiguration = updateStatus(
			userId, exportImportConfiguration.getExportImportConfigurationId(),
			WorkflowConstants.STATUS_IN_TRASH);

		trashEntryLocalService.addTrashEntry(
			userId, exportImportConfiguration.getGroupId(),
			ExportImportConfiguration.class.getName(),
			exportImportConfiguration.getExportImportConfigurationId(), null,
			null, oldStatus, null, null);

		return exportImportConfiguration;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ExportImportConfiguration restoreExportImportConfigurationFromTrash(
			long userId, long exportImportConfigurationId)
		throws PortalException {

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationPersistence.findByPrimaryKey(
				exportImportConfigurationId);

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			ExportImportConfiguration.class.getName(),
			exportImportConfigurationId);

		exportImportConfiguration = updateStatus(
			userId, exportImportConfiguration.getExportImportConfigurationId(),
			trashEntry.getStatus());

		trashEntryLocalService.deleteEntry(
			ExportImportConfiguration.class.getName(),
			exportImportConfiguration.getExportImportConfigurationId());

		return exportImportConfiguration;
	}

	@Override
	public BaseModelSearchResult<ExportImportConfiguration>
		searchExportImportConfigurations(
			long companyId, long groupId, int type, String keywords, int start,
			int end, Sort sort)
		throws PortalException {

		String description = null;
		String name = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			description = keywords;
			name = keywords;
		}
		else {
			andOperator = true;
		}

		return searchExportImportConfigurations(
			companyId, groupId, type, name, description, andOperator, start,
			end, sort);
	}

	@Override
	public BaseModelSearchResult<ExportImportConfiguration>
		searchExportImportConfigurations(
			long companyId, long groupId, int type, String name,
			String description, boolean andSearch, int start, int end,
			Sort sort)
		throws PortalException {

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			ExportImportConfiguration.class);

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, type, name, description, andSearch, start, end,
			sort);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<ExportImportConfiguration> exportImportConfigurations =
				exportImportConfigurationLocalService.
					getExportImportConfigurations(hits);

			if (exportImportConfigurations != null) {
				return new BaseModelSearchResult<>(
					exportImportConfigurations, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ExportImportConfiguration updateExportImportConfiguration(
			long userId, long exportImportConfigurationId, String name,
			String description, Map<String, Serializable> settingsMap,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationPersistence.findByPrimaryKey(
				exportImportConfigurationId);

		exportImportConfiguration.setUserId(userId);
		exportImportConfiguration.setUserName(user.getFullName());
		exportImportConfiguration.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		exportImportConfiguration.setName(name);
		exportImportConfiguration.setDescription(description);

		if (settingsMap != null) {
			String settings = JSONFactoryUtil.serialize(settingsMap);

			exportImportConfiguration.setSettings(settings);
		}

		return exportImportConfigurationPersistence.update(
			exportImportConfiguration);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ExportImportConfiguration updateStatus(
			long userId, long exportImportConfigurationId, int status)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		ExportImportConfiguration exportImportConfiguration =
			exportImportConfigurationPersistence.findByPrimaryKey(
				exportImportConfigurationId);

		exportImportConfiguration.setStatus(status);
		exportImportConfiguration.setStatusByUserId(userId);
		exportImportConfiguration.setStatusByUserName(user.getScreenName());
		exportImportConfiguration.setStatusDate(new Date());

		exportImportConfigurationPersistence.update(exportImportConfiguration);

		return exportImportConfiguration;
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, int type, String name, String description,
		boolean andSearch, int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(andSearch);

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put("description", description);
		attributes.put("groupId", groupId);
		attributes.put("name", name);
		attributes.put("type", type);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

}