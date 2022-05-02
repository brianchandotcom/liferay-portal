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

package com.liferay.layout.page.template.internal.upgrade.v5_1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Eudaldo Alonso
 */
public class LayoutPageTemplateStructureUpgradeProcess extends UpgradeProcess {

	public LayoutPageTemplateStructureUpgradeProcess(
		LayoutLocalService layoutLocalService,
		SegmentsExperienceLocalService segmentsExperienceLocalService,
		UserLocalService userLocalService) {

		_layoutLocalService = layoutLocalService;
		_segmentsExperienceLocalService = segmentsExperienceLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select layoutPageTemplateStructureId, companyId, classPK," +
					"userId from LayoutPageTemplateStructure")) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					long layoutPageTemplateStructureId = resultSet.getLong(
						"layoutPageTemplateStructureId");
					long companyId = resultSet.getLong("companyId");
					long classPK = resultSet.getLong("classPK");
					long userId = resultSet.getLong("userId");

					_addDefaultSegmentsExperience(
						classPK, companyId, layoutPageTemplateStructureId,
						userId);
				}
			}
		}
	}

	private void _addDefaultSegmentsExperience(
			long classPK, long companyId, long layoutPageTemplateStructureId,
			long userId)
		throws Exception {

		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				classPK);

		if (defaultSegmentsExperienceId <= 0) {
			User user = _userLocalService.fetchUser(userId);

			if (user == null) {
				userId = _userLocalService.getDefaultUserId(companyId);
			}

			SegmentsExperience defaultSegmentsExperience =
				_segmentsExperienceLocalService.addDefaultSegmentsExperience(
					userId, classPK, new ServiceContext());

			defaultSegmentsExperienceId =
				defaultSegmentsExperience.getSegmentsExperienceId();
		}

		long draftClassPK = 0;
		long publishedClassPK = 0;

		Layout layout = _layoutLocalService.fetchLayout(classPK);

		if (layout.isDraftLayout()) {
			draftClassPK = layout.getPlid();
			publishedClassPK = layout.getClassPK();
		}
		else {
			Layout draftLayout = _layoutLocalService.fetchDraftLayout(
				layout.getPlid());

			if (draftLayout != null) {
				draftClassPK = draftLayout.getPlid();
			}

			publishedClassPK = layout.getPlid();
		}

		_updateFragmentEntryLinks(
			defaultSegmentsExperienceId, draftClassPK, publishedClassPK);

		_updateLayoutPageTemplateStructureRels(
			defaultSegmentsExperienceId, layoutPageTemplateStructureId);

		_updateSegmentsExperiments(
			defaultSegmentsExperienceId, draftClassPK, publishedClassPK);

		_updateSegmentsExperimentRels(
			defaultSegmentsExperienceId, draftClassPK, publishedClassPK);
	}

	private void _updateFragmentEntryLinks(
			long defaultSegmentsExperienceId, long draftPlid,
			long publishedPlid)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update FragmentEntryLink set segmentsExperienceId = ? where " +
					"segmentsExperienceId = 0 and (plid = ? or plid = ?)")) {

			preparedStatement.setLong(1, defaultSegmentsExperienceId);
			preparedStatement.setLong(2, draftPlid);
			preparedStatement.setLong(3, publishedPlid);

			preparedStatement.executeUpdate();
		}
	}

	private void _updateLayoutPageTemplateStructureRels(
			long defaultSegmentsExperienceId,
			long layoutPageTemplateStructureId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update LayoutPageTemplateStructureRel set " +
					"segmentsExperienceId = ? where segmentsExperienceId = 0 " +
						"and layoutPageTemplateStructureId = ?")) {

			preparedStatement.setLong(1, defaultSegmentsExperienceId);
			preparedStatement.setLong(2, layoutPageTemplateStructureId);

			preparedStatement.executeUpdate();
		}
	}

	private void _updateSegmentsExperimentRels(
			long defaultSegmentsExperienceId, long draftPlid,
			long publishedPlid)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"update SegmentsExperimentRel set segmentsExperienceId = ",
					"? where segmentsExperienceId = 0 and ",
					"segmentsExperimentId in (select ",
					"SegmentsExperiment.segmentsExperimentId from ",
					"SegmentsExperiment where classPK = ? or classPK = ?)"))) {

			preparedStatement.setLong(1, defaultSegmentsExperienceId);
			preparedStatement.setLong(2, draftPlid);
			preparedStatement.setLong(3, publishedPlid);

			preparedStatement.executeUpdate();
		}
	}

	private void _updateSegmentsExperiments(
			long defaultSegmentsExperienceId, long draftPlid,
			long publishedPlid)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update SegmentsExperiment set segmentsExperienceId = ? " +
					"where segmentsExperienceId = 0 and (classPK = ? or " +
						"classPK = ?)")) {

			preparedStatement.setLong(1, defaultSegmentsExperienceId);
			preparedStatement.setLong(2, draftPlid);
			preparedStatement.setLong(3, publishedPlid);

			preparedStatement.executeUpdate();
		}
	}

	private final LayoutLocalService _layoutLocalService;
	private final SegmentsExperienceLocalService
		_segmentsExperienceLocalService;
	private final UserLocalService _userLocalService;

}