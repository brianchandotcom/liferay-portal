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

package com.liferay.asset.util.comparator;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.util.CollatorUtil;

import java.io.Serializable;

import java.text.Collator;

import java.util.Comparator;
import java.util.Locale;

/**
 * @author Eduardo García
 */
public class AssetRendererFactoryTypeNameComparator
	implements Comparator<AssetRendererFactory<?>>, Serializable {

	public AssetRendererFactoryTypeNameComparator(Locale locale) {
		_locale = locale;

		_collator = CollatorUtil.getInstance(_locale);
	}

	@Override
	public int compare(
		AssetRendererFactory<?> assetRendererFactory1,
		AssetRendererFactory<?> assetRendererFactory2) {

		String assetRendererFactoryType1 = assetRendererFactory1.getTypeName(
			_locale);
		String assetRendererFactoryType2 = assetRendererFactory2.getTypeName(
			_locale);

		return _collator.compare(
			assetRendererFactoryType1, assetRendererFactoryType2);
	}

	private final Collator _collator;
	private final Locale _locale;

}