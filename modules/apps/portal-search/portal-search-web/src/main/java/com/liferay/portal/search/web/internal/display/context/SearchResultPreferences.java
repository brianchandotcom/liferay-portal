/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.display.context;

/**
 * @author André de Oliveira
 */
public interface SearchResultPreferences {

	public String getFieldsToDisplay();

	public boolean isDisplayResultsInDocumentForm();

	public boolean isHighlightEnabled();

	public boolean isViewInContext();

}