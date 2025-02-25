/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alberto Javier Moreno Lage
 */
public class MissingPortletDataHandlerException extends PortalException {

	public MissingPortletDataHandlerException(String portletDisplayName) {
		_portletDisplayName = portletDisplayName;
	}

	public String getPortletDisplayName() {
		return _portletDisplayName;
	}

	private final String _portletDisplayName;

}