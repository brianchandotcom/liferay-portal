/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal.util;

/**
 * @author Tamas Biro
 */
public class ExtractedParameter {

	public ExtractedParameter(String name, String signature) {
		_name = name;
		_signature = signature;
	}

	public String getName() {
		return _name;
	}

	public String getSignature() {
		return _signature;
	}

	private final String _name;
	private final String _signature;

}