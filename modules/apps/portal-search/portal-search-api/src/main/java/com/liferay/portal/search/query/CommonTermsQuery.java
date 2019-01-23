/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.query;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface CommonTermsQuery extends Query {

	public String getAnalyzer();

	public Float getCutoffFrequency();

	public String getField();

	public String getHighFreqMinimumShouldMatch();

	public Operator getHighFreqOperator();

	public String getLowFreqMinimumShouldMatch();

	public Operator getLowFreqOperator();

	public String getText();

	public void setAnalyzer(String analyzer);

	public void setCutoffFrequency(Float cutoffFrequency);

	public void setHighFreqMinimumShouldMatch(
		String highFreqMinimumShouldMatch);

	public void setHighFreqOperator(Operator highFreqOperator);

	public void setLowFreqMinimumShouldMatch(String lowFreqMinimumShouldMatch);

	public void setLowFreqOperator(Operator lowFreqOperator);

}