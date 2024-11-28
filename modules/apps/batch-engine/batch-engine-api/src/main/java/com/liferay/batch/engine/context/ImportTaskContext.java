/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.context;

/**
 * @author Vendel Toreki
 */
public class ImportTaskContext {

	public String getOriginalUserId() {
		return _originalUserId;
	}

	public void setOriginalUserId(String originalUserId) {
		_originalUserId = originalUserId;
	}

	private String _originalUserId;

}