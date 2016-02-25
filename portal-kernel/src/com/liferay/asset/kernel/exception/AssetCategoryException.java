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