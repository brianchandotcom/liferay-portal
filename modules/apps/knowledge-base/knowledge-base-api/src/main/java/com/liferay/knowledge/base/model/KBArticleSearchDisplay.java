/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.model;

import java.io.Serializable;

import java.util.List;

/**
 * @author Peter Shin
 */
public interface KBArticleSearchDisplay extends Serializable {

	public int[] getCurStartValues();

	public List<KBArticle> getResults();

	public int getTotal();

	public void setCurStartValues(int[] curStartValues);

	public void setResults(List<KBArticle> results);

	public void setTotal(int total);

}