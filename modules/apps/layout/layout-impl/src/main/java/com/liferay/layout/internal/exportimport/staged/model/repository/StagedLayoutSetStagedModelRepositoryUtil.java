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

package com.liferay.layout.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.layout.set.model.adapter.StagedLayoutSet;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;

import java.util.List;

/**
 * @author Joao Victor Alves
 */
public class StagedLayoutSetStagedModelRepositoryUtil {

	public static List<StagedModel> fetchChildrenStagedModels(
		PortletDataContext portletDataContext,
		StagedLayoutSet stagedLayoutSet) {

		LayoutSet layoutSet = stagedLayoutSet.getLayoutSet();

		LayoutLocalService layoutLocalService =
			_layoutLocalServiceSnapshot.get();

		return TransformUtil.transform(
			layoutLocalService.getLayouts(
				stagedLayoutSet.getGroupId(), layoutSet.isPrivateLayout()),
			layout -> {
				ExportImportHelper exportImportHelper =
					_exportImportHelperSnapshot.get();

				if (exportImportHelper.isLayoutRevisionInReview(layout)) {
					return null;
				}

				return (StagedModel)layout;
			});
	}

	public static StagedLayoutSet fetchExistingLayoutSet(
		long groupId, boolean privateLayout) {

		StagedLayoutSet stagedLayoutSet = null;

		try {
			LayoutSetLocalService layoutSetLocalService =
				_layoutSetLocalServiceSnapshot.get();

			stagedLayoutSet = ModelAdapterUtil.adapt(
				layoutSetLocalService.getLayoutSet(groupId, privateLayout),
				LayoutSet.class, StagedLayoutSet.class);
		}
		catch (PortalException portalException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return stagedLayoutSet;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagedLayoutSetStagedModelRepositoryUtil.class);

	private static final Snapshot<ExportImportHelper>
		_exportImportHelperSnapshot = new Snapshot<>(
			StagedLayoutSetStagedModelRepositoryUtil.class,
			ExportImportHelper.class);
	private static final Snapshot<LayoutLocalService>
		_layoutLocalServiceSnapshot = new Snapshot<>(
			StagedLayoutSetStagedModelRepositoryUtil.class,
			LayoutLocalService.class);
	private static final Snapshot<LayoutSetLocalService>
		_layoutSetLocalServiceSnapshot = new Snapshot<>(
			StagedLayoutSetStagedModelRepositoryUtil.class,
			LayoutSetLocalService.class);

}