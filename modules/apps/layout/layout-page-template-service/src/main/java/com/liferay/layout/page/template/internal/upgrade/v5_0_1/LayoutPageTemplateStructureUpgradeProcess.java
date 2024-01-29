/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v5_0_1;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Date;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class LayoutPageTemplateStructureUpgradeProcess extends UpgradeProcess {

	public LayoutPageTemplateStructureUpgradeProcess(
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService,
		SegmentsExperienceLocalService segmentsExperienceLocalService) {

		_fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
		_segmentsExperienceLocalService = segmentsExperienceLocalService;

		_classNameId = PortalUtil.getClassNameId(Layout.class);
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeLayouts();
	}

	private String _generateLayoutPageTemplateStructureData(
		long groupId, long plid) {

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
				groupId, plid);

		LayoutStructure layoutStructure = new LayoutStructure();

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.addRootLayoutStructureItem();

		if (!fragmentEntryLinks.isEmpty()) {
			LayoutStructureItem containerStyledLayoutStructureItem =
				layoutStructure.addContainerStyledLayoutStructureItem(
					rootLayoutStructureItem.getItemId(), 0);

			for (int i = 0; i < fragmentEntryLinks.size(); i++) {
				FragmentEntryLink fragmentEntryLink = fragmentEntryLinks.get(i);

				layoutStructure.addFragmentStyledLayoutStructureItem(
					fragmentEntryLink.getFragmentEntryLinkId(),
					containerStyledLayoutStructureItem.getItemId(), i);
			}
		}

		return layoutStructure.toString();
	}

	private boolean _hasLayoutPageTemplateStructure(long classPK, long groupId)
		throws UpgradeException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) from LayoutPageTemplateStructure where " +
					"groupId = ? and classPK = ?")) {

			preparedStatement.setLong(1, groupId);
			preparedStatement.setLong(2, classPK);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int count = resultSet.getInt(1);

					if (count > 0) {
						return true;
					}
				}

				return false;
			}
		}
		catch (SQLException sqlException) {
			throw new UpgradeException(sqlException);
		}
	}

	private void _upgradeLayouts() {
		String selectLayoutSQL =
			"select plid, groupId, companyId, userId, userName, createDate " +
				"from Layout where type_ = ? or type_ = ?";
		String insertLayoutPageTemplateStructureSQL = StringBundler.concat(
			"insert into LayoutPageTemplateStructure (uuid_, ",
			"layoutPageTemplateStructureId, groupId, companyId, userId, ",
			"userName, createDate, modifiedDate, classNameId, classPK) values ",
			"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		String insertLayoutPageTemplateStructureRelSQL = StringBundler.concat(
			"insert into LayoutPageTemplateStructureRel (uuid_, ",
			"lPageTemplateStructureRelId, groupId, companyId, userId, ",
			"userName, createDate, modifiedDate, ",
			"layoutPageTemplateStructureId, segmentsExperienceId, data_, ",
			"status, statusByUserId, statusByUserName, statusDate) values (?, ",
			"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				selectLayoutSQL);
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection, insertLayoutPageTemplateStructureSQL);
			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection, insertLayoutPageTemplateStructureRelSQL)) {

			preparedStatement1.setString(1, LayoutConstants.TYPE_ASSET_DISPLAY);
			preparedStatement1.setString(2, LayoutConstants.TYPE_CONTENT);

			ResultSet resultSet = preparedStatement1.executeQuery();

			while (resultSet.next()) {
				long plid = resultSet.getLong("plid");
				long groupId = resultSet.getLong("groupId");

				if (_hasLayoutPageTemplateStructure(plid, groupId)) {
					continue;
				}

				long companyId = resultSet.getLong("companyId");
				long userId = resultSet.getLong("userId");
				String userName = resultSet.getString("userName");
				Date createDate = resultSet.getDate("createDate");

				preparedStatement2.setString(1, PortalUUIDUtil.generate());

				long layoutPageTemplateStructureId = increment();

				preparedStatement2.setLong(2, layoutPageTemplateStructureId);

				preparedStatement2.setLong(3, groupId);
				preparedStatement2.setLong(4, companyId);
				preparedStatement2.setLong(5, userId);
				preparedStatement2.setString(6, userName);

				Timestamp timestamp = new Timestamp(createDate.getTime());

				preparedStatement2.setTimestamp(7, timestamp);
				preparedStatement2.setTimestamp(8, timestamp);

				preparedStatement2.setLong(9, _classNameId);
				preparedStatement2.setLong(10, plid);

				preparedStatement2.addBatch();

				preparedStatement3.setString(1, PortalUUIDUtil.generate());
				preparedStatement3.setLong(2, increment());
				preparedStatement3.setLong(3, groupId);
				preparedStatement3.setLong(4, companyId);
				preparedStatement3.setLong(5, userId);
				preparedStatement3.setString(6, userName);
				preparedStatement3.setTimestamp(7, timestamp);
				preparedStatement3.setTimestamp(8, timestamp);
				preparedStatement3.setLong(9, layoutPageTemplateStructureId);
				preparedStatement3.setLong(
					10,
					_segmentsExperienceLocalService.
						fetchDefaultSegmentsExperienceId(plid));
				preparedStatement3.setString(
					11,
					_generateLayoutPageTemplateStructureData(groupId, plid));
				preparedStatement3.setInt(
					12, WorkflowConstants.STATUS_APPROVED);
				preparedStatement3.setLong(13, userId);
				preparedStatement3.setString(14, userName);
				preparedStatement3.setTimestamp(15, timestamp);

				preparedStatement3.addBatch();
			}

			preparedStatement2.executeBatch();

			preparedStatement3.executeBatch();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateStructureUpgradeProcess.class);

	private final long _classNameId;
	private final FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;
	private final SegmentsExperienceLocalService
		_segmentsExperienceLocalService;

}