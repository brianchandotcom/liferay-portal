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
public abstract class CommonTermsQuery extends Query {

	public abstract String getAnalyzer();

	public abstract Float getCutoffFrequency();

	public abstract String getField();

	public abstract String getHighFreqMinimumShouldMatch();

	public abstract Operator getHighFreqOperator();

	public abstract String getLowFreqMinimumShouldMatch();

	public abstract Operator getLowFreqOperator();

	public abstract String getText();

	public abstract void setAnalyzer(String analyzer);

	public abstract void setCutoffFrequency(Float cutoffFrequency);

	public abstract void setHighFreqMinimumShouldMatch(
		String highFreqMinimumShouldMatch);

	public abstract void setHighFreqOperator(Operator highFreqOperator);

	public abstract void setLowFreqMinimumShouldMatch(
		String lowFreqMinimumShouldMatch);

	public abstract void setLowFreqOperator(Operator lowFreqOperator);

}