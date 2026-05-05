/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.provider;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutSet;

/**
 * @author Amos Fong
 */
public interface LayoutSetSEORobotsProvider {

	public String getRobots(LayoutSet layoutSet, boolean secure)
		throws PortalException;

	public String getRobotsContributions(LayoutSet layoutSet);

}