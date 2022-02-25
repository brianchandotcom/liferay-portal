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

package com.liferay.layout.page.template.internal.upgrade.v2_1_0;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Pavel Savinov
 */
public class LayoutUpgradeProcess extends UpgradeProcess {

	public LayoutUpgradeProcess(
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService,
		LayoutLocalService layoutLocalService,
		LayoutPrototypeLocalService layoutPrototypeLocalService) {

		_fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
		_layoutLocalService = layoutLocalService;
		_layoutPrototypeLocalService = layoutPrototypeLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeSchema();
		_upgradeLayout();
	}

	private long _getPlid(
			long companyId, long userId, long groupId, String name, int type,
			long layoutPrototypeId, ServiceContext serviceContext)
		throws Exception {

		if ((type == LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE) &&
			(layoutPrototypeId > 0)) {

			LayoutPrototype layoutPrototype =
				_layoutPrototypeLocalService.getLayoutPrototype(
					layoutPrototypeId);

			Layout layout = layoutPrototype.getLayout();

			return layout.getPlid();
		}

		boolean privateLayout = false;
		String layoutType = LayoutConstants.TYPE_ASSET_DISPLAY;

		if (type == LayoutPageTemplateEntryTypeConstants.TYPE_BASIC) {
			layoutType = LayoutConstants.TYPE_CONTENT;
			privateLayout = true;
		}

		Map<Locale, String> titleMap = Collections.singletonMap(
			LocaleUtil.getSiteDefault(), name);

		serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);

		Layout layout = _layoutLocalService.addLayout(
			PortalUtil.getValidUserId(companyId, userId), groupId,
			privateLayout, 0, titleMap, titleMap, null, null, null, layoutType,
			StringPool.BLANK, true, true, new HashMap<>(), serviceContext);

		return layout.getPlid();
	}

	private void _upgradeLayout() throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		try (LoggingTimer loggingTimer = new LoggingTimer();
			Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(
				StringBundler.concat(
					"select layoutPageTemplateEntryId, groupId, companyId, ",
					"userId, name, type_, layoutPrototypeId, companyId from ",
					"LayoutPageTemplateEntry where plid is null or plid = 0"));
			PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update LayoutPageTemplateEntry set plid = ? where " +
							"layoutPageTemplateEntryId = ?"))) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong("companyId");
				long userId = resultSet.getLong("userId");
				long groupId = resultSet.getLong("groupId");
				String name = resultSet.getString("name");
				int type = resultSet.getInt("type_");
				long layoutPrototypeId = resultSet.getLong("layoutPrototypeId");

				long plid = _getPlid(
					companyId, userId, groupId, name, type, layoutPrototypeId,
					serviceContext);

				preparedStatement.setLong(1, plid);

				long layoutPageTemplateEntryId = resultSet.getLong(
					"layoutPageTemplateEntryId");

				preparedStatement.setLong(2, layoutPageTemplateEntryId);

				preparedStatement.addBatch();

				List<FragmentEntryLink> fragmentEntryLinks =
					_fragmentEntryLinkLocalService.getFragmentEntryLinks(
						groupId,
						PortalUtil.getClassNameId(
							LayoutPageTemplateEntry.class),
						layoutPageTemplateEntryId);

				Layout draftLayout = _layoutLocalService.fetchDraftLayout(plid);

				for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
					fragmentEntryLink.setClassNameId(
						PortalUtil.getClassNameId(Layout.class));
					fragmentEntryLink.setClassPK(plid);
					fragmentEntryLink.setPlid(plid);

					_fragmentEntryLinkLocalService.updateFragmentEntryLink(
						fragmentEntryLink);

					_fragmentEntryLinkLocalService.addFragmentEntryLink(
						draftLayout.getUserId(), draftLayout.getGroupId(), 0,
						fragmentEntryLink.getFragmentEntryId(), 0,
						draftLayout.getPlid(), fragmentEntryLink.getCss(),
						fragmentEntryLink.getHtml(), fragmentEntryLink.getJs(),
						fragmentEntryLink.getConfiguration(),
						fragmentEntryLink.getEditableValues(), StringPool.BLANK,
						fragmentEntryLink.getPosition(), null, serviceContext);
				}
			}

			preparedStatement.executeBatch();
		}
	}

	private void _upgradeSchema() throws Exception {
		if (!hasColumn("LayoutPageTemplateEntry", "plid")) {
			alterTableAddColumn("LayoutPageTemplateEntry", "plid", "LONG");
		}
	}

	private final FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;
	private final LayoutLocalService _layoutLocalService;
	private final LayoutPrototypeLocalService _layoutPrototypeLocalService;

}