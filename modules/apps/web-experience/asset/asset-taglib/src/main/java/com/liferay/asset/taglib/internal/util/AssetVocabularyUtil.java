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

package com.liferay.asset.taglib.internal.util;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PredicateFilter;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class AssetVocabularyUtil {

	public static List<AssetVocabulary> filterVocabularies(
		List<AssetVocabulary> vocabularies, String className,
		final long classTypePK) {

		final long classNameId = PortalUtil.getClassNameId(className);

		PredicateFilter<AssetVocabulary> predicateFilter =
			new PredicateFilter<AssetVocabulary>() {

				@Override
				public boolean filter(AssetVocabulary assetVocabulary) {
					return
						assetVocabulary.isAssociatedToClassNameIdAndClassTypePK(
							classNameId, classTypePK);
				}

			};

		return ListUtil.filter(vocabularies, predicateFilter);
	}

}