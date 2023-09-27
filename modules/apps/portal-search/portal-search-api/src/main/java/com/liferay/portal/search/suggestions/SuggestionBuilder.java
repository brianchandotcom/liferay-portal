/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.suggestions;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Petteri Karttunen
 */
@ProviderType
public interface SuggestionBuilder {

	public SuggestionBuilder attribute(String name, Object value);

	public Suggestion build();

	public SuggestionBuilder score(float score);

	public SuggestionBuilder text(String text);

}