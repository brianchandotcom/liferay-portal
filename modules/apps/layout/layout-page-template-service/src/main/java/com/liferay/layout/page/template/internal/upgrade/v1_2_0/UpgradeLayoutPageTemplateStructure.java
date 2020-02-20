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
import com.liferay.layout.page.template.util.LayoutPageTemplateStructureHelperUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
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
public class UpgradeLayoutPageTemplateStructure extends UpgradeProcess {

	public UpgradeLayoutPageTemplateStructure(
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService,
		LayoutLocalService layoutLocalService) {

		_fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
		_layoutLocalService = layoutLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeSchema();

		upgradeLayoutPageTemplates();
		upgradeLayouts();
	}

	protected void upgradeLayoutPageTemplates() throws Exception {
		long classNameId = PortalUtil.getClassNameId(
			LayoutPageTemplateEntry.class.getName());

		StringBundler sb = new StringBundler(7);

		sb.append("select layoutPageTemplateEntryId, groupId, companyId, ");
		sb.append("userId, userName, createDate from LayoutPageTemplateEntry ");
		sb.append("where type_ in (");
		sb.append(LayoutPageTemplateEntryTypeConstants.TYPE_BASIC);
		sb.append(", ");
		sb.append(LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE);
		sb.append(")");

		try (PreparedStatement ps = connection.prepareStatement(
				sb.toString())) {

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long layoutPageTemplateEntryId = rs.getLong(
						"layoutPageTemplateEntryId");
					long groupId = rs.getLong("groupId");
					long companyId = rs.getLong("companyId");
					long userId = rs.getLong("userId");
					String userName = rs.getString("userName");
					Timestamp createDate = rs.getTimestamp("createDate");

					_updateLayoutPageTemplateStructure(
						groupId, companyId, userId, userName, createDate,
						classNameId, layoutPageTemplateEntryId);
				}
			}
		}
	}

	protected void upgradeLayouts() throws PortalException {
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

	protected void upgradeSchema() throws Exception {
		String template = StringUtil.read(
			UpgradeLayoutPageTemplateStructure.class.getResourceAsStream(
				"dependencies/update.sql"));

		runSQLTemplateString(template, false);
	}

	private JSONObject _generateLayoutPageTemplateStructureData(
		long groupId, long classNameId, long classPK) {

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				groupId, classNameId, classPK);

		return LayoutPageTemplateStructureHelperUtil.
			generateContentLayoutStructure(fragmentEntryLinks);
	}

	private void _updateLayoutPageTemplateStructure(
		long groupId, long companyId, long userId, String userName,
		Timestamp createDate, long classNameId, long classPK) {

		JSONObject jsonObject = _generateLayoutPageTemplateStructureData(
			groupId, classNameId, classPK);

		StringBundler sb = new StringBundler(4);

		sb.append("insert into LayoutPageTemplateStructure (uuid_, ");
		sb.append("layoutPageTemplateStructureId, groupId, companyId, ");
		sb.append("userId, userName, createDate, modifiedDate, classNameId, ");
		sb.append("classPK, data_) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		String sql = sb.toString();

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(sql);

			ps.setString(1, PortalUUIDUtil.generate());
			ps.setLong(2, increment());
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, userId);
			ps.setString(6, userName);
			ps.setTimestamp(7, createDate);
			ps.setTimestamp(8, createDate);
			ps.setLong(9, classNameId);
			ps.setLong(10, classPK);
			ps.setString(11, jsonObject.toString());

			ps.executeUpdate();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeLayoutPageTemplateStructure.class);

	private final FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;
	private final LayoutLocalService _layoutLocalService;

}