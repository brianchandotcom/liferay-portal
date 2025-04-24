/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.search.asset;

import com.liferay.portal.search.asset.AssetSubtypeIdentifier;

/**
 * @author Joshua Cords
 */
public class AssetSubtypeIdentifierImpl implements AssetSubtypeIdentifier {

	public AssetSubtypeIdentifierImpl(
		String groupExternalReferenceCode,
		String subtypeExternalReferenceCode) {

		_groupExternalReferenceCode = groupExternalReferenceCode;
		_subtypeExternalReferenceCode = subtypeExternalReferenceCode;
	}

	@Override
	public String getGroupExternalReferenceCode() {
		return _groupExternalReferenceCode;
	}

	@Override
	public String getSubtypeExternalReferenceCode() {
		return _subtypeExternalReferenceCode;
	}

	private final String _groupExternalReferenceCode;
	private final String _subtypeExternalReferenceCode;

}