/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.asset;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.asset.AssetSubtypeIdentifier;
import com.liferay.portal.search.asset.AssetSubtypeIdentifierBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 */
@Component(service = AssetSubtypeIdentifierBuilder.class)
public class AssetSubtypeIdentifierBuilderImpl
	implements AssetSubtypeIdentifierBuilder {

	@Override
	public AssetSubtypeIdentifier build() {
		return new AssetSubtypeIdentifierImpl(
			_className, _groupExternalReferenceCode,
			_subtypeExternalReferenceCode);
	}

	@Override
	public AssetSubtypeIdentifierBuilder searchableAssetType(
		String searchableAssetType) {

		String[] assetSubtypeIdentifierParts = StringUtil.split(
			searchableAssetType, "&&");

		_className = assetSubtypeIdentifierParts[0];

		if (assetSubtypeIdentifierParts.length != 3) {
			_groupExternalReferenceCode = StringPool.BLANK;
			_subtypeExternalReferenceCode = StringPool.BLANK;

			return this;
		}

		_groupExternalReferenceCode = assetSubtypeIdentifierParts[1];
		_subtypeExternalReferenceCode = assetSubtypeIdentifierParts[2];

		return this;
	}

	private String _className;
	private String _groupExternalReferenceCode;
	private String _subtypeExternalReferenceCode;

}