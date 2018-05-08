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

package com.liferay.portal.search.internal.contributor.document;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentContributor;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = DocumentContributor.class)
public class AssetTagDocumentContributor implements DocumentContributor {

	@Override
	public void contribute(Document document, BaseModel baseModel) {
		String className = document.get(Field.ENTRY_CLASS_NAME);

		long classNameId = portal.getClassNameId(className);

		long classPK = GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK));

		List<AssetTag> assetTags = assetTagLocalService.getTags(
			classNameId, classPK);

		String[] assetTagNames = ListUtil.toArray(
			assetTags, AssetTag.NAME_ACCESSOR);

		document.addText(Field.ASSET_TAG_NAMES, assetTagNames);

		long[] assetTagsIds = ListUtil.toLongArray(
			assetTags, AssetTag.TAG_ID_ACCESSOR);

		document.addKeyword(Field.ASSET_TAG_IDS, assetTagsIds);
	}

	@Reference
	protected AssetTagLocalService assetTagLocalService;

	@Reference
	protected Portal portal;

}