/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.rescore;

import com.liferay.portal.search.query.Query;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Bryan Engler
 */
@ProviderType
public interface Rescore {

	public Query getQuery();

	public Float getQueryWeight();

	public Float getRescoreQueryWeight();

	public ScoreMode getScoreMode();

	public Integer getWindowSize();

	public enum ScoreMode {

		AVG, MAX, MIN, MULTIPLY, TOTAL

	}

}