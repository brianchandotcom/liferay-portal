/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.thread.local;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Jürgen Kappler
 */
public class AssetVocabularyThreadLocal {

	public static boolean isSkipRequiredCategoryValidation() {
		return _skipRequiredCategoryValidation.get();
	}

	public static void setSkipRequiredCategoryValidation(
		boolean skipRequiredCategoryValidation) {

		_skipRequiredCategoryValidation.set(skipRequiredCategoryValidation);
	}

	private static final ThreadLocal<Boolean> _skipRequiredCategoryValidation =
		new CentralizedThreadLocal<>(
			AssetVocabularyThreadLocal.class +
				"._skipRequiredCategoryValidation",
			() -> false);

}