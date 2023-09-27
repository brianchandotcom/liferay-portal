/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.taglib.internal.util;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class AssetVocabularyUtil {

	public static List<AssetVocabulary> filterVocabularies(
		List<AssetVocabulary> vocabularies, String className,
		long classTypePK) {

		long classNameId = PortalUtil.getClassNameId(className);

		return ListUtil.filter(
			vocabularies,
			assetVocabulary ->
				assetVocabulary.isAssociatedToClassNameIdAndClassTypePK(
					classNameId, classTypePK));
	}

}