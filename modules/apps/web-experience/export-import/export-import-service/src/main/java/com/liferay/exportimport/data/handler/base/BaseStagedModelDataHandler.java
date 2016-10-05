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

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.ExportImportProcessCallbackRegistryUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedGroupedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

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

		boolean updateLastPublishDate = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE);

		if (ExportImportThreadLocal.isStagingInProcess() &&
			updateLastPublishDate &&
			(stagedModel instanceof StagedGroupedModel)) {

			ExportImportProcessCallbackRegistryUtil.registerCallback(
				new UpdateStagedModelLastPublishDateCallable(
					stagedModel, portletDataContext.getDateRange()));
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

	/**
	 * @deprecated As of 4.0.0
	 */
	@Deprecated
	protected ExportImportContentProcessor getExportImportContentProcessor(
		Class<T> clazz) {

		return null;
	}

	protected StagedModelRepository<T> getStagedModelRepository() {
		return null;
	}

	private class UpdateStagedModelLastPublishDateCallable
		implements Callable<Void> {

		public UpdateStagedModelLastPublishDateCallable(
			T stagedModel, DateRange dateRange) {

			_companyId = stagedModel.getCompanyId();
			_dateRange = dateRange;
			_groupId = ((StagedGroupedModel)stagedModel).getGroupId();
			_uuid = stagedModel.getUuid();
		}

		@Override
		public Void call() throws PortalException {
			StagedModelRepository<T> stagedModelRepository =
				getStagedModelRepository();

			if (stagedModelRepository == null) {
				return null;
			}

			T stagedModel =
				stagedModelRepository.fetchStagedModelByUuidAndGroupId(
					_uuid, _groupId);

			if (stagedModel == null) {
				return null;
			}

			Date endDate = null;

			if (_dateRange != null) {
				endDate = _dateRange.getEndDate();
			}

			ExportImportDateUtil.updateLastPublishDate(
				(StagedGroupedModel)stagedModel, _dateRange, endDate);

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setModifiedDate(stagedModel.getModifiedDate());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			try {
				stagedModelRepository.saveStagedModel(stagedModel);
			}
			finally {
				ServiceContextThreadLocal.popServiceContext();
			}

			return null;
		}

		private final long _companyId;
		private final DateRange _dateRange;
		private final long _groupId;
		private final String _uuid;

	}

}