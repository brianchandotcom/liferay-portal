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

package com.liferay.layout.page.template.internal.upgrade.v4_0_0;

import com.liferay.layout.helper.CollectionPaginationHelper;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.List;

/**
 * @author Rubén Pulido
 */
public class LayoutPageTemplateStructureRelUpgradeProcess
	extends UpgradeProcess {

	public LayoutPageTemplateStructureRelUpgradeProcess(
		CollectionPaginationHelper collectionPaginationHelper) {

		_collectionPaginationHelper = collectionPaginationHelper;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeLayoutPageTemplateStructureRel();
	}

	private JSONObject _getItemsJSONObject(String data) {
		try {
			JSONObject layoutStructureJSONObject =
				JSONFactoryUtil.createJSONObject(data);

			return layoutStructureJSONObject.getJSONObject("items");
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}
		}

		return JSONFactoryUtil.createJSONObject();
	}

	private String _getPaginatiopnType(
		CollectionStyledLayoutStructureItem collectionStyledLayoutStructureItem,
		JSONObject itemsJSONObject) {

		JSONObject itemJSONObject = itemsJSONObject.getJSONObject(
			collectionStyledLayoutStructureItem.getItemId());

		JSONObject configJSONObject = itemJSONObject.getJSONObject("config");

		return configJSONObject.getString("paginationType");
	}

	private String _upgradeLayoutData(String data) {
		JSONObject itemsJSONObject = _getItemsJSONObject(data);

		LayoutStructure layoutStructure = LayoutStructure.of(data);

		List<LayoutStructureItem> layoutStructureItems =
			layoutStructure.getLayoutStructureItems();

		for (LayoutStructureItem layoutStructureItem : layoutStructureItems) {
			if (layoutStructureItem instanceof
					CollectionStyledLayoutStructureItem) {

				CollectionStyledLayoutStructureItem
					collectionStyledLayoutStructureItem =
						(CollectionStyledLayoutStructureItem)
							layoutStructureItem;

				collectionStyledLayoutStructureItem.setDisplayAllItems(false);

				String paginationType = _getPaginatiopnType(
					collectionStyledLayoutStructureItem, itemsJSONObject);

				if (_collectionPaginationHelper.isPaginationEnabled(
						paginationType)) {

					collectionStyledLayoutStructureItem.setDisplayAllPages(
						collectionStyledLayoutStructureItem.isShowAllItems());

					int numberOfItemsPerPage =
						collectionStyledLayoutStructureItem.
							getNumberOfItemsPerPage();

					if (numberOfItemsPerPage > 0) {
						int numberOfItems =
							collectionStyledLayoutStructureItem.
								getNumberOfItems();

						collectionStyledLayoutStructureItem.setNumberOfPages(
							(int)Math.ceil(
								numberOfItems / (double)numberOfItemsPerPage));
					}
				}
				else {
					paginationType =
						CollectionPaginationHelper.PAGINATION_TYPE_NONE;
				}

				collectionStyledLayoutStructureItem.setPaginationType(
					paginationType);
			}
		}

		JSONObject jsonObject = layoutStructure.toJSONObject();

		return jsonObject.toString();
	}

	private void _upgradeLayoutPageTemplateStructureRel() throws Exception {
		try (Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(
				"select lPageTemplateStructureRelId, data_ from " +
					"LayoutPageTemplateStructureRel");
			PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update LayoutPageTemplateStructureRel set data_ = ? " +
							"where lPageTemplateStructureRelId = ?"))) {

			while (resultSet.next()) {
				long layoutPageTemplateStructureRelId = resultSet.getLong(
					"lPageTemplateStructureRelId");

				String data = resultSet.getString("data_");

				preparedStatement.setString(1, _upgradeLayoutData(data));

				preparedStatement.setLong(2, layoutPageTemplateStructureRelId);

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateStructureRelUpgradeProcess.class);

	private final CollectionPaginationHelper _collectionPaginationHelper;

}