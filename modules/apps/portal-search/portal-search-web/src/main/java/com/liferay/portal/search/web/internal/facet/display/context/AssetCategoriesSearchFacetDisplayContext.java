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

package com.liferay.portal.search.web.internal.facet.display.context;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.configuration.CategoryFacetFieldConfiguration;
import com.liferay.portal.search.web.internal.category.facet.configuration.CategoryFacetPortletInstanceConfiguration;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lino Alves
 */
public class AssetCategoriesSearchFacetDisplayContext
	implements FacetDisplayContext, Serializable {

	public AssetCategoriesSearchFacetDisplayContext(
			HttpServletRequest httpServletRequest)
		throws ConfigurationException {

		_httpServletRequest = httpServletRequest;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_categoryFacetPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CategoryFacetPortletInstanceConfiguration.class);

		CategoryFacetFieldConfiguration categoryFacetFieldConfiguration =
			ConfigurationProviderUtil.getSystemConfiguration(
				CategoryFacetFieldConfiguration.class);

		_legacyFieldSelected = _isLegacyFieldSelected(
			categoryFacetFieldConfiguration.categoryFacetField());
	}

	public List<BucketDisplayContext> getBucketDisplayContexts() {
		return _bucketDisplayContexts;
	}

	public List<BucketDisplayContext> getBucketDisplayContexts(
		String vocabularyName) {

		return _bucketDisplayContextsMap.get(vocabularyName);
	}

	public CategoryFacetPortletInstanceConfiguration
		getCategoryFacetPortletInstanceConfiguration() {

		return _categoryFacetPortletInstanceConfiguration;
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_categoryFacetPortletInstanceConfiguration.displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = themeDisplay.getScopeGroupId();
		}

		return _displayStyleGroupId;
	}

	public String getPaginationStartParameterName() {
		return _paginationStartParameterName;
	}

	public String getParameterName() {
		return _parameterName;
	}

	public String getParameterValue() {
		return _parameterValue;
	}

	public List<String> getParameterValues() {
		return _parameterValues;
	}

	public List<String> getVocabularyNames() {
		return _vocabularyNames;
	}

	public boolean isCloud() {
		return _cloud;
	}

	public boolean isLegacyFieldSelected() {
		return _legacyFieldSelected;
	}

	public boolean isNothingSelected() {
		return _nothingSelected;
	}

	public boolean isRenderNothing() {
		return _renderNothing;
	}

	public void setBucketDisplayContexts(
		List<BucketDisplayContext> bucketDisplayContexts) {

		_bucketDisplayContexts = bucketDisplayContexts;
	}

	public void setBucketDisplayContextsMap(
		Map<String, List<BucketDisplayContext>> bucketDisplayContextsMap) {

		_bucketDisplayContextsMap = bucketDisplayContextsMap;
	}

	public void setCloud(boolean cloud) {
		_cloud = cloud;
	}

	public void setNothingSelected(boolean nothingSelected) {
		_nothingSelected = nothingSelected;
	}

	public void setPaginationStartParameterName(
		String paginationStartParameterName) {

		_paginationStartParameterName = paginationStartParameterName;
	}

	public void setParameterName(String parameterName) {
		_parameterName = parameterName;
	}

	public void setParameterValue(String paramValue) {
		_parameterValue = paramValue;
	}

	public void setParameterValues(List<String> parameterValues) {
		_parameterValues = parameterValues;
	}

	public void setRenderNothing(boolean renderNothing) {
		_renderNothing = renderNothing;
	}

	public void setVocabularyNames(List<String> vocabularyNames) {
		_vocabularyNames = vocabularyNames;
	}

	private boolean _isLegacyFieldSelected(String fieldName) {
		if (fieldName.equals("assetCategoryIds")) {
			return true;
		}

		return false;
	}

	private List<BucketDisplayContext> _bucketDisplayContexts;
	private Map<String, List<BucketDisplayContext>> _bucketDisplayContextsMap;
	private final CategoryFacetPortletInstanceConfiguration
		_categoryFacetPortletInstanceConfiguration;
	private boolean _cloud;
	private long _displayStyleGroupId;
	private final HttpServletRequest _httpServletRequest;
	private final boolean _legacyFieldSelected;
	private boolean _nothingSelected;
	private String _paginationStartParameterName;
	private String _parameterName;
	private String _parameterValue;
	private List<String> _parameterValues;
	private boolean _renderNothing;
	private List<String> _vocabularyNames;

}