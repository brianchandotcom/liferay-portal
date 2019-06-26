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
import com.liferay.layout.page.template.internal.upgrade.v2_0_0.util.LayoutPageTemplateEntryTable;
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
public class UpgradeLayout extends UpgradeProcess {

	public UpgradeLayout(
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService,
		LayoutLocalService layoutLocalService,
		LayoutPrototypeLocalService layoutPrototypeLocalService) {

		_fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
		_layoutLocalService = layoutLocalService;
		_layoutPrototypeLocalService = layoutPrototypeLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeSchema();
		upgradeLayout();
	}

	protected void upgradeLayout() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("select layoutPageTemplateEntryId, userId, groupId, name, ");
		sb.append("type_, layoutPrototypeId from LayoutPageTemplateEntry ");
		sb.append("where plid is null or plid = 0");

		ServiceContext serviceContext = new ServiceContext();

		try (LoggingTimer loggingTimer = new LoggingTimer();
			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(sb.toString());
			PreparedStatement ps = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update LayoutPageTemplateEntry set plid = ? where " +
						"layoutPageTemplateEntryId = ?"))) {

			while (rs.next()) {
				long userId = rs.getLong("userId");
				long groupId = rs.getLong("groupId");
				String name = rs.getString("name");
				int type = rs.getInt("type_");
				long layoutPrototypeId = rs.getLong("layoutPrototypeId");

				long plid = _getPlid(
					userId, groupId, name, type, layoutPrototypeId,
					serviceContext);

				ps.setLong(1, plid);

				long layoutPageTemplateEntryId = rs.getLong(
					"layoutPageTemplateEntryId");

				ps.setLong(2, layoutPageTemplateEntryId);

				ps.addBatch();

				List<FragmentEntryLink> fragmentEntryLinks =
					_fragmentEntryLinkLocalService.getFragmentEntryLinks(
						groupId,
						PortalUtil.getClassNameId(
							LayoutPageTemplateEntry.class),
						layoutPageTemplateEntryId);

				Layout draftLayout = _layoutLocalService.fetchLayout(
					PortalUtil.getClassNameId(Layout.class), plid);

				for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
					fragmentEntryLink.setClassNameId(
						PortalUtil.getClassNameId(Layout.class));
					fragmentEntryLink.setClassPK(plid);

					_fragmentEntryLinkLocalService.updateFragmentEntryLink(
						fragmentEntryLink);

					_fragmentEntryLinkLocalService.addFragmentEntryLink(
						draftLayout.getUserId(), draftLayout.getGroupId(), 0,
						fragmentEntryLink.getFragmentEntryId(),
						PortalUtil.getClassNameId(Layout.class),
						draftLayout.getPlid(), fragmentEntryLink.getCss(),
						fragmentEntryLink.getHtml(), fragmentEntryLink.getJs(),
						fragmentEntryLink.getConfiguration(),
						fragmentEntryLink.getEditableValues(), StringPool.BLANK,
						fragmentEntryLink.getPosition(), null, serviceContext);
				}
			}

			ps.executeBatch();
		}
	}

	protected void upgradeSchema() throws Exception {
		alter(
			LayoutPageTemplateEntryTable.class,
			new AlterTableAddColumn("plid LONG"));
	}

	private long _getPlid(
			long userId, long groupId, String name, int type,
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
			userId, groupId, privateLayout, 0, titleMap, titleMap, null, null,
			null, layoutType, StringPool.BLANK, true, true, new HashMap<>(),
			serviceContext);

		_layoutLocalService.addLayout(
			layout.getUserId(), layout.getGroupId(), privateLayout,
			layout.getParentLayoutId(), PortalUtil.getClassNameId(Layout.class),
			layout.getPlid(), layout.getNameMap(), layout.getTitleMap(),
			layout.getDescriptionMap(), layout.getKeywordsMap(),
			layout.getRobotsMap(), layout.getType(), StringPool.BLANK, true,
			true, Collections.emptyMap(), serviceContext);

		return layout.getPlid();
	}

	private final FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;
	private final LayoutLocalService _layoutLocalService;
	private final LayoutPrototypeLocalService _layoutPrototypeLocalService;

}