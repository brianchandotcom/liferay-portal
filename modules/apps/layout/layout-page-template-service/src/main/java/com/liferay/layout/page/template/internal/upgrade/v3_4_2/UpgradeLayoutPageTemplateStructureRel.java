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

package com.liferay.layout.page.template.internal.upgrade.v3_4_2;

import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeLayoutPageTemplateStructureRel extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeLayoutPageTemplateStructureRel();
	}

	private boolean _isEmpty(JSONObject jsonObject) {
		Set<String> keySet = jsonObject.keySet();

		return keySet.isEmpty();
	}

	private String _upgradeLayoutData(String data) {
		LayoutStructure layoutStructure = LayoutStructure.of(data);

		List<LayoutStructureItem> layoutStructureItems =
			layoutStructure.getLayoutStructureItems();

		for (LayoutStructureItem layoutStructureItem : layoutStructureItems) {
			if (layoutStructureItem instanceof ColumnLayoutStructureItem) {
				ColumnLayoutStructureItem columnLayoutStructureItem =
					(ColumnLayoutStructureItem)layoutStructureItem;

				Map<String, JSONObject> viewportConfigurations =
					columnLayoutStructureItem.getViewportConfigurations();

				JSONObject mobileLandscapeJSONObject =
					viewportConfigurations.get(
						ViewportSize.MOBILE_LANDSCAPE.getViewportSizeId());

				JSONObject portraitMobileJSONObject =
					viewportConfigurations.get(
						ViewportSize.PORTRAIT_MOBILE.getViewportSizeId());

				JSONObject tabletJSONObject = viewportConfigurations.get(
					ViewportSize.TABLET.getViewportSizeId());

				if (_isEmpty(mobileLandscapeJSONObject) &&
					_isEmpty(portraitMobileJSONObject) &&
					_isEmpty(tabletJSONObject)) {

					columnLayoutStructureItem.setViewportConfiguration(
						ViewportSize.MOBILE_LANDSCAPE.getViewportSizeId(),
						JSONUtil.put("size", "12"));
				}
			}
		}

		JSONObject jsonObject = layoutStructure.toJSONObject();

		return jsonObject.toString();
	}

	private void _upgradeLayoutPageTemplateStructureRel() throws Exception {
		try (Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(
				"select lPageTemplateStructureRelId, segmentsExperienceId, " +
					"data_ from LayoutPageTemplateStructureRel");
			PreparedStatement ps = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update LayoutPageTemplateStructureRel set data_ = ? " +
						"where lPageTemplateStructureRelId = ?"))) {

			while (rs.next()) {
				long layoutPageTemplateStructureRelId = rs.getLong(
					"lPageTemplateStructureRelId");

				String data = rs.getString("data_");

				ps.setString(1, _upgradeLayoutData(data));

				ps.setLong(2, layoutPageTemplateStructureRelId);

				ps.addBatch();
			}

			ps.executeBatch();
		}
	}

}