/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = StagedModelRepositoryHelper.class)
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
				catch (PortalException portalException) {
					if (_log.isDebugEnabled()) {
						_log.debug(portalException);
					}
				}
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
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