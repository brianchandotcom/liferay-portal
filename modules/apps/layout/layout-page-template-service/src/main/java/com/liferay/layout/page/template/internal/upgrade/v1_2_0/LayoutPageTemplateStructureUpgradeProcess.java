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

package com.liferay.layout.page.template.internal.upgrade.v1_2_0;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.Date;
import java.util.List;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplateStructureUpgradeProcess extends UpgradeProcess {

	public LayoutPageTemplateStructureUpgradeProcess(
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService,
		LayoutLocalService layoutLocalService) {

		_fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
		_layoutLocalService = layoutLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeSchema();

		_upgradeLayoutPageTemplates();
		_upgradeLayouts();
	}

	private String _generateLayoutPageTemplateStructureData(
		long groupId, long classNameId, long classPK) {

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				groupId, classNameId, classPK);

		if (fragmentEntryLinks.isEmpty()) {
			LayoutStructure layoutStructure = new LayoutStructure();

			layoutStructure.addRootLayoutStructureItem();

			return layoutStructure.toString();
		}

		LayoutStructure layoutStructure = new LayoutStructure();

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.addRootLayoutStructureItem();

		LayoutStructureItem containerStyledLayoutStructureItem =
			layoutStructure.addContainerStyledLayoutStructureItem(
				rootLayoutStructureItem.getItemId(), 0);

		for (int i = 0; i < fragmentEntryLinks.size(); i++) {
			FragmentEntryLink fragmentEntryLink = fragmentEntryLinks.get(i);

			layoutStructure.addFragmentStyledLayoutStructureItem(
				fragmentEntryLink.getFragmentEntryLinkId(),
				containerStyledLayoutStructureItem.getItemId(), i);
		}

		return layoutStructure.toString();
	}

	private void _updateLayoutPageTemplateStructure(
		long groupId, long companyId, long userId, String userName,
		Timestamp createDate, long classNameId, long classPK) {

		String sql = StringBundler.concat(
			"insert into LayoutPageTemplateStructure (uuid_, ",
			"layoutPageTemplateStructureId, groupId, companyId, userId, ",
			"userName, createDate, modifiedDate, classNameId, classPK, data_) ",
			"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql)) {

			preparedStatement.setString(1, PortalUUIDUtil.generate());
			preparedStatement.setLong(2, increment());
			preparedStatement.setLong(3, groupId);
			preparedStatement.setLong(4, companyId);
			preparedStatement.setLong(5, userId);
			preparedStatement.setString(6, userName);
			preparedStatement.setTimestamp(7, createDate);
			preparedStatement.setTimestamp(8, createDate);
			preparedStatement.setLong(9, classNameId);
			preparedStatement.setLong(10, classPK);
			preparedStatement.setString(
				11,
				_generateLayoutPageTemplateStructureData(
					groupId, classNameId, classPK));

			preparedStatement.executeUpdate();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	private void _upgradeLayoutPageTemplates() throws Exception {
		long classNameId = PortalUtil.getClassNameId(
			LayoutPageTemplateEntry.class.getName());

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select layoutPageTemplateEntryId, groupId, companyId, ",
					"userId, userName, createDate from ",
					"LayoutPageTemplateEntry where type_ in (",
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, ", ",
					LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE,
					")"))) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long layoutPageTemplateEntryId = resultSet.getLong(
						"layoutPageTemplateEntryId");
					long groupId = resultSet.getLong("groupId");
					long companyId = resultSet.getLong("companyId");
					long userId = resultSet.getLong("userId");
					String userName = resultSet.getString("userName");
					Timestamp createDate = resultSet.getTimestamp("createDate");

					_updateLayoutPageTemplateStructure(
						groupId, companyId, userId, userName, createDate,
						classNameId, layoutPageTemplateEntryId);
				}
			}
		}
	}

	private void _upgradeLayouts() throws Exception {
		long classNameId = PortalUtil.getClassNameId(Layout.class.getName());

		ActionableDynamicQuery actionableDynamicQuery =
			_layoutLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.eq(
					"type", LayoutConstants.TYPE_CONTENT)));
		actionableDynamicQuery.setPerformActionMethod(
			(Layout layout) -> {
				Date createDate = layout.getCreateDate();

				_updateLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getCompanyId(),
					layout.getUserId(), layout.getUserName(),
					new Timestamp(createDate.getTime()), classNameId,
					layout.getPlid());
			});

		actionableDynamicQuery.performActions();
	}

	private void _upgradeSchema() throws Exception {
		String template = StringUtil.read(
			LayoutPageTemplateStructureUpgradeProcess.class.getResourceAsStream(
				"dependencies/update.sql"));

		runSQLTemplateString(template, false);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateStructureUpgradeProcess.class);

	private final FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;
	private final LayoutLocalService _layoutLocalService;

}