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

package com.liferay.exportimport.data.handler.base;

import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.model.ChangesetEntry;
import com.liferay.changeset.service.ChangesetCollectionLocalServiceUtil;
import com.liferay.changeset.service.ChangesetEntryLocalServiceUtil;
import com.liferay.changeset.util.ChangesetThreadLocal;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.content.processor.ExportImportContentProcessorRegistryUtil;
import com.liferay.exportimport.kernel.lar.ExportImportClassedModelUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.staging.StagingConstants;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.Collections;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Daniel Kocsis
 */
@ProviderType
public abstract class BaseStagedModelDataHandler<T extends StagedModel>
	extends com.liferay.exportimport.kernel.lar.BaseStagedModelDataHandler<T> {

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		StagedModelRepository<T> stagedModelRepository =
			getStagedModelRepository();

		if (stagedModelRepository == null) {
			return;
		}

		stagedModelRepository.deleteStagedModel(
			uuid, groupId, className, extraData);
	}

	@Override
	public void deleteStagedModel(T stagedModel) throws PortalException {
		StagedModelRepository<T> stagedModelRepository =
			getStagedModelRepository();

		if (stagedModelRepository == null) {
			return;
		}

		stagedModelRepository.deleteStagedModel(stagedModel);
	}

	@Override
	public void exportStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException {

		super.exportStagedModel(portletDataContext, stagedModel);

		if (ExportImportThreadLocal.isStagingInProcess()) {
			Element importDataRootElement =
				portletDataContext.getImportDataRootElement();

			Element importDataElement = null;

			try {
				portletDataContext.setImportDataRootElement(
					portletDataContext.getExportDataRootElement());

				importDataElement = portletDataContext.getImportDataElement(
					stagedModel);
			}
			finally {
				portletDataContext.setImportDataRootElement(
					importDataRootElement);
			}

			if (importDataElement == null) {
				return;
			}

			ChangesetCollection changesetCollection =
				ChangesetCollectionLocalServiceUtil.fetchChangesetCollection(
					portletDataContext.getScopeGroupId(),
					StagingConstants.
						RANGE_FROM_LAST_PUBLISH_DATE_CHANGESET_NAME);

			if (changesetCollection != null) {
				long classNameId = ClassNameLocalServiceUtil.getClassNameId(
					ExportImportClassedModelUtil.getClassName(stagedModel));

				ChangesetEntry changesetEntry =
					ChangesetEntryLocalServiceUtil.fetchChangesetEntry(
						changesetCollection.getChangesetCollectionId(),
						classNameId, (long)stagedModel.getPrimaryKeyObj());

				if (changesetEntry != null) {
					ChangesetThreadLocal.addExportedChangesetEntryId(
						changesetEntry.getChangesetEntryId());
				}
			}
		}
	}

	@Override
	public T fetchMissingReference(String uuid, long groupId) {
		StagedModelRepository<T> stagedModelRepository =
			getStagedModelRepository();

		if (stagedModelRepository == null) {
			return super.fetchMissingReference(uuid, groupId);
		}

		return stagedModelRepository.fetchMissingReference(uuid, groupId);
	}

	@Override
	public T fetchStagedModelByUuidAndGroupId(String uuid, long groupId) {
		StagedModelRepository<T> stagedModelRepository =
			getStagedModelRepository();

		if (stagedModelRepository == null) {
			return super.fetchStagedModelByUuidAndGroupId(uuid, groupId);
		}

		return stagedModelRepository.fetchStagedModelByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<T> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		StagedModelRepository<T> stagedModelRepository =
			getStagedModelRepository();

		if (stagedModelRepository == null) {
			return Collections.<T>emptyList();
		}

		return stagedModelRepository.fetchStagedModelsByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException {

		StagedModelRepository<T> stagedModelRepository =
			getStagedModelRepository();

		if (stagedModelRepository == null) {
			super.restoreStagedModel(portletDataContext, stagedModel);

			return;
		}

		stagedModelRepository.restoreStagedModel(
			portletDataContext, stagedModel);
	}

	protected ExportImportContentProcessor getExportImportContentProcessor(
		Class<T> clazz) {

		return ExportImportContentProcessorRegistryUtil.
			getExportImportContentProcessor(clazz.getName());
	}

	protected StagedModelRepository<T> getStagedModelRepository() {
		return null;
	}

}