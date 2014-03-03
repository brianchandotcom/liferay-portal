/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class AssetCategoryUtil {

	public static final String[] SELECTED_FIELD_NAMES =
		{Field.COMPANY_ID, Field.GROUP_ID, Field.UID, Field.ASSET_CATEGORY_ID};

	public static List<AssetCategory> getCategories(Hits hits)
		throws PortalException, SystemException {

		List<Document> documents = hits.toList();

		List<AssetCategory> categories = new ArrayList<AssetCategory>(
			documents.size());

		for (Document document : documents) {
			long categoryId = GetterUtil.getLong(
				document.get(Field.ASSET_CATEGORY_ID));

			AssetCategory category = AssetCategoryLocalServiceUtil.getCategory(
				categoryId);

			if (category == null) {
				categories = null;

				Indexer indexer = IndexerRegistryUtil.getIndexer(
					AssetCategory.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (categories != null) {
				categories.add(category);
			}
		}

		return categories;
	}

}