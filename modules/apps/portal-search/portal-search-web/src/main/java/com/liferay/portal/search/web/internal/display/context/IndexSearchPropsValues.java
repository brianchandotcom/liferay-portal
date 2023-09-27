/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.display.context;

/**
 * @author André de Oliveira
 */
public interface IndexSearchPropsValues {

	public int getCollatedSpellCheckResultScoresThreshold();

	public int getQueryIndexingThreshold();

	public int getQuerySuggestionMax();

	public int getQuerySuggestionScoresThreshold();

	public boolean isCollatedSpellCheckResultEnabled();

	public boolean isQueryIndexingEnabled();

	public boolean isQuerySuggestionEnabled();

}