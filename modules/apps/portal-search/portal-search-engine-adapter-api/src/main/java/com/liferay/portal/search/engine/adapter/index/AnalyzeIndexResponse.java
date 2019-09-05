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

package com.liferay.portal.search.engine.adapter.index;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class AnalyzeIndexResponse implements IndexResponse {

	public void addAnalysisIndexResponseTokens(
		AnalysisIndexResponseToken analysisIndexResponseToken) {

		_analysisIndexResponseTokens.add(analysisIndexResponseToken);
	}

	public String getAnalysisDetails() {
		return _analysisDetails;
	}

	public List<AnalysisIndexResponseToken> getAnalysisIndexResponseTokens() {
		return _analysisIndexResponseTokens;
	}

	public void setAnalysisDetails(String analysisDetails) {
		_analysisDetails = analysisDetails;
	}

	private String _analysisDetails;
	private final List<AnalysisIndexResponseToken>
		_analysisIndexResponseTokens = new ArrayList<>();

}