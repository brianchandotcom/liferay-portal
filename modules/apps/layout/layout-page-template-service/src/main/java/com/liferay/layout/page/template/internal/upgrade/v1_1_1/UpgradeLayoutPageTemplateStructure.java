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

package com.liferay.layout.page.template.internal.upgrade.v1_1_1;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

/**
 * @author Jürgen Kappler
 */
public class UpgradeLayoutPageTemplateStructure extends UpgradeProcess {

	public UpgradeLayoutPageTemplateStructure(
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService,
		LayoutPageTemplateStructureLocalService
			layoutPageTemplateStructureLocalService,
		Portal portal) {

		_fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
		_layoutPageTemplateStructureLocalService =
			layoutPageTemplateStructureLocalService;
		_portal = portal;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeSchema();

		updateContentLayouts();
		updateLayoutPageTemplates();
	}

	protected void updateContentLayouts() throws Exception {
		long classNameId = _portal.getClassNameId(Layout.class.getName());

		try (PreparedStatement ps = connection.prepareStatement(
				"select groupId, userId, plid from Layout where type_ = ?")) {

			ps.setString(1, "content");

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long groupId = rs.getLong("groupId");
					long userId = rs.getLong("userId");
					long plid = rs.getLong("plid");

					_updateLayoutPageTemplateStructure(
						classNameId, userId, groupId, plid);
				}
			}
		}
	}

	protected void updateLayoutPageTemplates() throws Exception {
		long classNameId = _portal.getClassNameId(
			LayoutPageTemplateEntry.class.getName());

		StringBuilder sb = new StringBuilder(6);

		sb.append("select groupId, userId, layoutPageTemplateEntryId from ");
		sb.append("LayoutPageTemplateEntry where type_ in (");
		sb.append(LayoutPageTemplateEntryTypeConstants.TYPE_BASIC);
		sb.append(", ");
		sb.append(LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE);
		sb.append(")");

		try (PreparedStatement ps =
				connection.prepareStatement(sb.toString())) {

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long groupId = rs.getLong("groupId");
					long userId = rs.getLong("userId");
					long layoutPageTemplateEntryId = rs.getLong(
						"layoutPageTemplateEntryId");

					_updateLayoutPageTemplateStructure(
						classNameId, userId, groupId,
						layoutPageTemplateEntryId);
				}
			}
		}
	}

	protected void upgradeSchema() throws Exception {
		String template = StringUtil.read(
			UpgradeLayoutPageTemplateStructure.class.getResourceAsStream(
				"dependencies/update.sql"));

		runSQLTemplateString(template, false, false);
	}

	private void _updateLayoutPageTemplateStructure(
			long classNameId, long userId, long groupId, long classPK)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(groupId, classNameId, classPK);

		if ((layoutPageTemplateStructure != null) &&
			Validator.isNotNull(layoutPageTemplateStructure.getData())) {

			return;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				groupId, classNameId, classPK);

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			jsonArray.put(fragmentEntryLink.getFragmentEntryLinkId());
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("structure", jsonArray);

		if (layoutPageTemplateStructure == null) {
			_layoutPageTemplateStructureLocalService.
				addLayoutPageTemplateStructure(
					userId, groupId, classNameId, classPK,
					jsonObject.toString(), new ServiceContext());
		}
		else {
			_layoutPageTemplateStructureLocalService.
				updateLayoutPageTemplateStructure(
					groupId, classNameId, classPK, jsonObject.toString());
		}
	}

	private final FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;
	private final LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;
	private final Portal _portal;

}