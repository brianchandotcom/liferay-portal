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

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.layout.constants.LayoutConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Pavel Savinov
 */
public class UpgradeLayout extends UpgradeProcess {

	public UpgradeLayout(
		LayoutLocalService layoutLocalService,
		LayoutPrototypeLocalService layoutPrototypeLocalService) {

		_layoutLocalService = layoutLocalService;
		_layoutPrototypeLocalService = layoutPrototypeLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeLayout();
	}

	protected void upgradeLayout() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("select layoutPageTemplateEntryId, userId, groupId, ");
		sb.append("classNameId, classTypeId, layoutPrototypeId, name, type_ ");
		sb.append("from LayoutPageTemplateEntry where plid is null or plid=0");

		ServiceContext serviceContext = new ServiceContext();

		try (LoggingTimer loggingTimer = new LoggingTimer();
			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(sb.toString());
			PreparedStatement ps = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update LayoutPageTemplateEntry set plid = ? where " +
						"layoutPageTemplateEntryId = ?"))) {

			while (rs.next()) {
				long classNameId = rs.getLong("classNameId");
				long classTypeId = rs.getLong("classTypeId");
				long groupId = rs.getLong("groupId");
				long layoutPageTemplateEntryId = rs.getLong(
					"layoutPageTemplateEntryId");
				long layoutPrototypeId = rs.getLong("layoutPrototypeId");
				String name = rs.getString("name");
				int type = rs.getInt("type_");
				long userId = rs.getLong("userId");

				ps.setLong(
					1,
					_getPlid(
						name, userId, groupId, classNameId, classTypeId,
						layoutPrototypeId, type, serviceContext));

				ps.setLong(2, layoutPageTemplateEntryId);

				ps.addBatch();
			}

			ps.executeBatch();
		}
	}

	private long _getPlid(
			String name, long userId, long groupId, long classNameId,
			long classTypeId, long layoutPrototypeId, int type,
			ServiceContext serviceContext)
		throws Exception {

		if ((type == LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE) &&
			(layoutPrototypeId > 0)) {

			LayoutPrototype layoutPrototype =
				_layoutPrototypeLocalService.getLayoutPrototype(
					layoutPrototypeId);

			Layout layout = layoutPrototype.getLayout();

			return layout.getPlid();
		}

		Map<Locale, String> titleMap = Collections.singletonMap(
			LocaleUtil.getSiteDefault(), name);

		if (classNameId > 0) {
			AssetRendererFactory assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassNameId(classNameId);

			titleMap = Collections.singletonMap(
				LocaleUtil.getSiteDefault(),
				assetRendererFactory.getTypeName(
					LocaleUtil.getSiteDefault(), classTypeId));
		}

		String layoutType = LayoutConstants.LAYOUT_TYPE_ASSET_DISPLAY;

		if (type == LayoutPageTemplateEntryTypeConstants.TYPE_BASIC) {
			layoutType = LayoutConstants.LAYOUT_TYPE_CONTENT;
		}

		serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);

		Layout layout = _layoutLocalService.addLayout(
			userId, groupId, false, 0, titleMap, titleMap, null, null, null,
			layoutType, StringPool.BLANK, true, true, new HashMap<>(),
			serviceContext);

		return layout.getPlid();
	}

	private final LayoutLocalService _layoutLocalService;
	private final LayoutPrototypeLocalService _layoutPrototypeLocalService;

}