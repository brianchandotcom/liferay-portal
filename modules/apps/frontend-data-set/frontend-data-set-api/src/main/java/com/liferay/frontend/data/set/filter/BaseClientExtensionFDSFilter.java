/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.filter;

/**
 * @author Iván Zaera Avellón
 */
public abstract class BaseClientExtensionFDSFilter implements FDSFilter {

	public abstract String getModuleURL();

	@Override
	public String getType() {
		return "clientExtension";
	}

}