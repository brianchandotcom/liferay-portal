/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.schema.query.option;

/**
 * @author Iván Zaera
 */
public class FolderQueryOption extends BaseQueryOption {

	public FolderQueryOption(String folderPath) {
		_folderPath = folderPath;
	}

	@Override
	protected String getNodeName() {
		return "Folder";
	}

	@Override
	protected String getNodeText() {
		return _folderPath;
	}

	private final String _folderPath;

}