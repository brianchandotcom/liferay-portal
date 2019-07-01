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

package com.liferay.segments.asah.connector.internal.asset.list;

import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.asset.list.util.AssetListEntryQueryProcessor;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.segments.asah.connector.internal.provider.AsahInterestTermProvider;

import java.util.Arrays;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + AssetListPortletKeys.ASSET_LIST,
	service = AssetListEntryQueryProcessor.class
)
public class AsahInterestTermAssetListEntryQueryProcessor
	implements AssetListEntryQueryProcessor {

	@Override
	public void processAssetEntryQuery(
		String userId, UnicodeProperties unicodeProperties,
		AssetEntryQuery assetEntryQuery) {

		boolean enableContentRecommendation = GetterUtil.getBoolean(
			unicodeProperties.getProperty("enableContentRecommendation"));

		if (enableContentRecommendation &&
			(_asahInterestTermProvider != null)) {

			String[] interestTerms = _asahInterestTermProvider.getInterestTerms(
				userId);

			String terms = Arrays.toString(interestTerms);

			assetEntryQuery.setKeywords(terms);
		}
	}

	@Reference
	private AsahInterestTermProvider _asahInterestTermProvider;

}