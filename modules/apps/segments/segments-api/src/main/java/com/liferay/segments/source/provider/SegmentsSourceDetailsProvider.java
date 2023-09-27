/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.source.provider;

import java.util.Locale;

/**
 * @author Alejandro Tardín
 */
public interface SegmentsSourceDetailsProvider {

	public String getIconSrc();

	public String getLabel(Locale locale);

}