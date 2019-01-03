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

package com.liferay.exportimport.internal.staged.model.repository;

import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedGroupedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.service.GroupLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(immediate = true, service = StagedModelRepositoryHelper.class)
public class StagedModelRepositoryHelperImpl
	implements StagedModelRepositoryHelper {

	@Override
	public <T extends StagedModel> T fetchMissingReference(
		String uuid, long groupId,
		StagedModelRepository<T> stagedModelRepository) {

		// Try to fetch the existing staged model from the importing group

		T existingStagedModel =
			stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				uuid, groupId);

		if ((existingStagedModel != null) &&
			!isStagedModelInTrash(existingStagedModel)) {

			return existingStagedModel;
		}

		try {

			// Try to fetch the existing staged model from parent sites

			Group originalGroup = _groupLocalService.getGroup(groupId);

			Group group = originalGroup.getParentGroup();

			while (group != null) {
				existingStagedModel =
					stagedModelRepository.fetchStagedModelByUuidAndGroupId(
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

			List<T> existingStagedModels =
				stagedModelRepository.fetchStagedModelsByUuidAndCompanyId(
					uuid, originalGroup.getCompanyId());

			for (T stagedModel : existingStagedModels) {
				try {
					if (stagedModel instanceof StagedGroupedModel) {
						StagedGroupedModel stagedGroupedModel =
							(StagedGroupedModel)stagedModel;

						group = _groupLocalService.getGroup(
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
	public boolean isStagedModelInTrash(StagedModel stagedModel) {
		if (!(stagedModel instanceof TrashedModel)) {
			return false;
		}

		TrashedModel trashedModel = (TrashedModel)stagedModel;

		return trashedModel.isInTrash();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagedModelRepositoryHelperImpl.class);

	@Reference
	private GroupLocalService _groupLocalService;

}