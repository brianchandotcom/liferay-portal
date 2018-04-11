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

package com.liferay.meris.asset.category.demo.internal;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.meris.MerisRule;
import com.liferay.meris.MerisSegment;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Eduardo Garcia
 */
public class AssetCategoryMerisSegment
	implements MerisSegment, Comparable<AssetCategoryMerisSegment> {

	public AssetCategoryMerisSegment(AssetVocabulary assetVocabulary) {
		_assetVocabulary = assetVocabulary;

		for (AssetCategory assetCategory : assetVocabulary.getCategories()) {
			_merisRules.add(new AssetCategoryMerisRule(assetCategory));
		}
	}

	@Override
	public int compareTo(AssetCategoryMerisSegment assetCategoryMerisSegment) {
		String merisSegmentId = getMerisSegmentId();

		return merisSegmentId.compareTo(
			assetCategoryMerisSegment.getMerisSegmentId());
	}

	@Override
	public String getDescription(Locale locale) {
		return _assetVocabulary.getDescription(locale);
	}

	@Override
	public List<MerisRule> getMerisRules(
		int start, int end, Comparator<MerisRule> comparator) {

		List merisRules = ListUtil.sort(_merisRules, comparator);

		ListUtil.subList(merisRules, start, end);

		return Collections.unmodifiableList(merisRules);
	}

	@Override
	public String getMerisSegmentId() {
		return Objects.toString(_assetVocabulary.getVocabularyId());
	}

	@Override
	public String getName(Locale locale) {
		return _assetVocabulary.getTitle(locale);
	}

	@Override
	public String getScopeId() {
		return Objects.toString(_assetVocabulary.getGroupId());
	}

	private final AssetVocabulary _assetVocabulary;
	private List<MerisRule> _merisRules = new ArrayList();

}