/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.portlet;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.Layout;

/**
 * @author Eudaldo Alonso
 */
@ProviderType
public interface PortletManager {

	public default boolean isDeprecated() {
		return false;
	}

	public boolean isVisible(Layout layout);

}