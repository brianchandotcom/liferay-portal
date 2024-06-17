/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.options.web.internal.model;

/**
 * @author Andrea Sbarra
 */
public class ListTypeDefinition {

	public ListTypeDefinition(
		long listTypeDefinitionId, String name, String externalReferenceCode) {

		_listTypeDefinitionId = listTypeDefinitionId;
		_name = name;
		_externalReferenceCode = externalReferenceCode;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public long getListTypeDefinitionId() {
		return _listTypeDefinitionId;
	}

	public String getName() {
		return _name;
	}

	private final String _externalReferenceCode;
	private final long _listTypeDefinitionId;
	private final String _name;

}