/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.backgroundtask.display;

import java.io.Serializable;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Andrew Betts
 */
@ProviderType
public interface BackgroundTaskDisplay extends Serializable {

	public String getDisplayName(HttpServletRequest httpServletRequest);

	public int getPercentage();

	public int getStatus();

	public String getStatusLabel();

	public String getStatusLabel(Locale locale);

	public boolean hasPercentage();

	public String renderDisplayTemplate();

	public String renderDisplayTemplate(Locale locale);

}