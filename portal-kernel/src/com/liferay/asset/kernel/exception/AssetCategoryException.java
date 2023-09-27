/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.exception;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Juan Fernández
 */
public class AssetCategoryException extends PortalException {

	public static final int AT_LEAST_ONE_CATEGORY = 1;

	public static final int TOO_MANY_CATEGORIES = 2;

	public AssetCategoryException(AssetVocabulary vocabulary, int type) {
		_vocabulary = vocabulary;
		_type = type;
	}

	public int getType() {
		return _type;
	}

	public AssetVocabulary getVocabulary() {
		return _vocabulary;
	}

	private final int _type;
	private final AssetVocabulary _vocabulary;

}