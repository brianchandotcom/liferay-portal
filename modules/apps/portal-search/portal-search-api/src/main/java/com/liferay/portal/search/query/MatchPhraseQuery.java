/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public abstract class MatchPhraseQuery extends Query {

	public abstract String getAnalyzer();

	public abstract String getField();

	public abstract Integer getSlop();

	public abstract Object getValue();

	public abstract void setAnalyzer(String analyzer);

	public abstract void setSlop(Integer slop);

}