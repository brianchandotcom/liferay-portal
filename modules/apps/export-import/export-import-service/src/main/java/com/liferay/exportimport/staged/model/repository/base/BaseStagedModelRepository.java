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

package com.liferay.exportimport.staged.model.repository.base;

import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedGroupedModel;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.exportimport.lar.ExportImportClassedModelUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;
import com.liferay.trash.kernel.util.TrashUtil;

import java.util.List;

/**
 * @author Daniel Kocsis
 */
public abstract class BaseStagedModelRepository<T extends StagedModel>
	implements StagedModelRepository<T> {

	@Override
	public abstract T addStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortalException;

	@Override
	public abstract void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException;

	@Override
	public abstract void deleteStagedModel(T stagedModel)
		throws PortalException;

	@Override
	public abstract void deleteStagedModels(
			PortletDataContext portletDataContext)
		throws PortalException;

	@Override
	public T fetchMissingReference(String uuid, long groupId) {

		// Try to fetch the existing staged model from the importing group

		T existingStagedModel = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if ((existingStagedModel != null) &&
			!isStagedModelInTrash(existingStagedModel)) {

			return existingStagedModel;
		}

		try {

			// Try to fetch the existing staged model from parent sites

			Group originalGroup = GroupLocalServiceUtil.getGroup(groupId);

			Group group = originalGroup.getParentGroup();

			while (group != null) {
				existingStagedModel = fetchStagedModelByUuidAndGroupId(
					uuid, group.getGroupId());

				if (existingStagedModel != null) {
					break;
				}

				group = group.getParentGroup();
			}

			if ((existingStagedModel != null) &&
				!isStagedModelInTrash(existingStagedModel)) {

				return existingStagedModel;
			}

			List<T> existingStagedModels = fetchStagedModelsByUuidAndCompanyId(
				uuid, originalGroup.getCompanyId());

			for (T stagedModel : existingStagedModels) {
				try {
					if (stagedModel instanceof StagedGroupedModel) {
						StagedGroupedModel stagedGroupedModel =
							(StagedGroupedModel)stagedModel;

						group = GroupLocalServiceUtil.getGroup(
							stagedGroupedModel.getGroupId());

						if (!group.isStagingGroup() &&
							!isStagedModelInTrash(stagedModel)) {

							return stagedModel;
						}
					}
					else if (!isStagedModelInTrash(stagedModel)) {
						return stagedModel;
					}
				}
				catch (PortalException pe) {
					if (_log.isDebugEnabled()) {
						_log.debug(pe, pe);
					}
				}
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to fetch missing reference staged model from " +
						"group " + groupId);
			}
		}

		return null;
	}

	@Override
	public T fetchStagedModelByUuidAndGroupId(String uuid, long groupId) {
		return null;
	}

	@Override
	public abstract List<T> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId);

	@Override
	public abstract ExportActionableDynamicQuery
		getExportActionableDynamicQuery(PortletDataContext portletDataContext);

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortletDataException {
	}

	@Override
	public abstract T saveStagedModel(T stagedModel) throws PortalException;

	@Override
	public abstract T updateStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortalException;

	protected boolean isStagedModelInTrash(T stagedModel) {
		String className = ExportImportClassedModelUtil.getClassName(
			stagedModel);
		long classPK = ExportImportClassedModelUtil.getClassPK(stagedModel);

		try {
			return TrashUtil.isInTrash(className, classPK);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseStagedModelRepository.class);

}