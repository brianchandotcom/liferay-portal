/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.asset;

import com.liferay.portal.search.asset.AssetSubtypeIdentifier;

/**
 * @author Joshua Cords
 */
public class AssetSubtypeIdentifierImpl implements AssetSubtypeIdentifier {

	public AssetSubtypeIdentifierImpl(
		String className, String groupExternalReferenceCode,
		String subtypeExternalReferenceCode) {

		_className = className;
		_groupExternalReferenceCode = groupExternalReferenceCode;
		_subtypeExternalReferenceCode = subtypeExternalReferenceCode;
	}

	@Override
	public String getClassName() {
		return _className;
	}

	@Override
	public String getGroupExternalReferenceCode() {
		return _groupExternalReferenceCode;
	}

	@Override
	public String getSubtypeExternalReferenceCode() {
		return _subtypeExternalReferenceCode;
	}

	private final String _className;
	private final String _groupExternalReferenceCode;
	private final String _subtypeExternalReferenceCode;

}