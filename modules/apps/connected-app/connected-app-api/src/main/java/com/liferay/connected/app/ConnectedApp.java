/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.connected.app;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Alejandro Tardín
 */
public interface ConnectedApp {

	public String getImageURL();

	public String getKey();

	public String getName(Locale locale);

	public void revoke() throws PortalException;

}