/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author Alessio Antonio Rendina
 */
@ProviderType
public interface VirtualCPTypeHelper {

	public CPDefinitionVirtualSetting getCPDefinitionVirtualSetting(
			long cpDefinitionId, long cpInstanceId)
		throws PortalException;

	public String getSampleURL(
			long cpDefinitionId, long cpInstanceId, ThemeDisplay themeDisplay)
		throws PortalException;

}