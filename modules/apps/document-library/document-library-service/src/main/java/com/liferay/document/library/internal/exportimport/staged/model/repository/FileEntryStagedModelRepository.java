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

package com.liferay.document.library.internal.exportimport.staged.model.repository;

import com.liferay.document.library.exportimport.data.handler.DLExportableRepositoryPublisher;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = StagedModelRepository.class
)
public class FileEntryStagedModelRepository
	implements StagedModelRepository<FileEntry> {

	@Override
	public FileEntry addStagedModel(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(FileEntry fileEntry) throws PortalException {
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
	public FileEntry fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public FileEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<FileEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		Collection<Long> exportableRepositoryIds = _getExportableRepositoryIds(
			portletDataContext);

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_dlFileEntryLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		exportActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				DynamicQuery fileVersionDynamicQuery =
					DynamicQueryFactoryUtil.forClass(
						DLFileVersion.class, "dlFileVersion",
						PortalClassLoaderUtil.getClassLoader());

				fileVersionDynamicQuery.setProjection(
					ProjectionFactoryUtil.property("fileEntryId"));

				fileVersionDynamicQuery.add(
					RestrictionsFactoryUtil.eqProperty(
						"dlFileVersion.fileEntryId", "this.fileEntryId"));
				fileVersionDynamicQuery.add(
					RestrictionsFactoryUtil.eqProperty(
						"dlFileVersion.version", "this.version"));

				Property fileVersionStatusProperty =
					PropertyFactoryUtil.forName("dlFileVersion.status");

				StagedModelDataHandler<?> stagedModelDataHandler =
					StagedModelDataHandlerRegistryUtil.
						getStagedModelDataHandler(DLFileEntry.class.getName());

				fileVersionDynamicQuery.add(
					fileVersionStatusProperty.in(
						stagedModelDataHandler.getExportableStatuses()));

				Criterion fileVersionStatusDateCriterion =
					portletDataContext.getDateRangeCriteria(
						"dlFileVersion.statusDate");
				Criterion modifiedDateCriterion =
					portletDataContext.getDateRangeCriteria(
						"this.modifiedDate");

				if ((fileVersionStatusDateCriterion != null) &&
					(modifiedDateCriterion != null)) {

					Disjunction disjunction =
						RestrictionsFactoryUtil.disjunction();

					Criterion fileVersionModifiedDateCriterion =
						portletDataContext.getDateRangeCriteria(
							"dlFileVersion.modifiedDate");

					disjunction.add(fileVersionModifiedDateCriterion);

					disjunction.add(fileVersionStatusDateCriterion);
					disjunction.add(modifiedDateCriterion);

					fileVersionDynamicQuery.add(disjunction);
				}

				Property fileEntryIdProperty = PropertyFactoryUtil.forName(
					"fileEntryId");

				dynamicQuery.add(
					fileEntryIdProperty.in(fileVersionDynamicQuery));

				Property repositoryIdProperty = PropertyFactoryUtil.forName(
					"repositoryId");

				dynamicQuery.add(
					repositoryIdProperty.in(exportableRepositoryIds));
			});
		exportActionableDynamicQuery.setPerformActionMethod(
			(DLFileEntry dlFileEntry) -> {
				FileEntry fileEntry = _dlAppLocalService.getFileEntry(
					dlFileEntry.getFileEntryId());

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, fileEntry);
			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(DLFileEntryConstants.getClassName()));

		return exportActionableDynamicQuery;
	}

	@Override
	public FileEntry getStagedModel(long fileEntryId) throws PortalException {
		return _dlAppLocalService.getFileEntry(fileEntryId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public FileEntry saveStagedModel(FileEntry fileEntry)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public FileEntry updateStagedModel(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_dlExportableRepositoryPublishers = ServiceTrackerListFactory.open(
			bundleContext, DLExportableRepositoryPublisher.class);
	}

	@Deactivate
	protected void deactivate() {
		if (_dlExportableRepositoryPublishers != null) {
			_dlExportableRepositoryPublishers.close();
		}
	}

	private Collection<Long> _getExportableRepositoryIds(
		PortletDataContext portletDataContext) {

		Collection<Long> exportableRepositoryIds = new HashSet<>();

		exportableRepositoryIds.add(portletDataContext.getScopeGroupId());

		for (DLExportableRepositoryPublisher dlExportableRepositoryPublisher :
				_dlExportableRepositoryPublishers) {

			dlExportableRepositoryPublisher.publish(
				portletDataContext.getScopeGroupId(),
				exportableRepositoryIds::add);
		}

		return exportableRepositoryIds;
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

	private ServiceTrackerList
		<DLExportableRepositoryPublisher, DLExportableRepositoryPublisher>
			_dlExportableRepositoryPublishers;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

}