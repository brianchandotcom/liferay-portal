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

package com.liferay.segments.asah.connector.internal.asset.list.asset.entry.query.processor;

import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.list.asset.entry.query.processor.AssetListAssetEntryQueryProcessor;
import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.asah.connector.internal.provider.AsahInterestTermProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + AssetListPortletKeys.ASSET_LIST,
	service = AssetListAssetEntryQueryProcessor.class
)
public class AsahInterestTermAssetListAssetEntryQueryProcessor
	implements AssetListAssetEntryQueryProcessor {

	@Override
	public void processAssetEntryQuery(
		String userId, UnicodeProperties unicodeProperties,
		AssetEntryQuery assetEntryQuery) {

		if (Validator.isNull(userId)) {
			return;
		}

		boolean enableContentRecommendation = GetterUtil.getBoolean(
			unicodeProperties.getProperty("enableContentRecommendation"));

		if (enableContentRecommendation &&
			(_asahInterestTermProvider != null)) {

			String terms = StringUtil.merge(
				_asahInterestTermProvider.getInterestTerms(userId));

			assetEntryQuery.setKeywords(terms);
		}
	}

	@Reference
	private AsahInterestTermProvider _asahInterestTermProvider;

}