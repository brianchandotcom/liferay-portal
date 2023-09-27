/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		_collator = CollatorUtil.getInstance(locale);
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