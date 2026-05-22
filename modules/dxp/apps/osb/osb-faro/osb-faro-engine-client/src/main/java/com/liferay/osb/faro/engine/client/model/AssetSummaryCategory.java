/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class AssetSummaryCategory {

	public Metric getDownloadsMetric() {
		return _downloadsMetric;
	}

	@JsonProperty("_embedded")
	public Map<String, Object> getEmbeddedResources() {
		return _embeddedResources;
	}

	public String getId() {
		return _id;
	}

	public Metric getImpressionsMetric() {
		return _impressionsMetric;
	}

	public String getName() {
		return _name;
	}

	public Metric getViewsMetric() {
		return _viewsMetric;
	}

	public String getVocabularyId() {
		return _vocabularyId;
	}

	public String getVocabularyName() {
		return _vocabularyName;
	}

	public void setDownloadsMetric(Metric downloadsMetric) {
		_downloadsMetric = downloadsMetric;
	}

	public void setEmbeddedResources(Map<String, Object> embeddedResources) {
		_embeddedResources = embeddedResources;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setImpressionsMetric(Metric impressionsMetric) {
		_impressionsMetric = impressionsMetric;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setViewsMetric(Metric viewsMetric) {
		_viewsMetric = viewsMetric;
	}

	public void setVocabularyId(String vocabularyId) {
		_vocabularyId = vocabularyId;
	}

	public void setVocabularyName(String vocabularyName) {
		_vocabularyName = vocabularyName;
	}

	private Metric _downloadsMetric;
	private Map<String, Object> _embeddedResources = new HashMap<>();
	private String _id;
	private Metric _impressionsMetric;
	private String _name;
	private Metric _viewsMetric;
	private String _vocabularyId;
	private String _vocabularyName;

}