/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.display.page.portlet;

import com.liferay.info.item.InfoItemReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Locale;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Alejandro Tardín
 */
@ProviderType
public interface AssetDisplayPageFriendlyURLProvider {

	public String getFriendlyURL(
			InfoItemReference infoItemReference, Locale locale,
			ThemeDisplay themeDisplay)
		throws PortalException;

	public String getFriendlyURL(
			InfoItemReference infoItemReference, ThemeDisplay themeDisplay)
		throws PortalException;

}