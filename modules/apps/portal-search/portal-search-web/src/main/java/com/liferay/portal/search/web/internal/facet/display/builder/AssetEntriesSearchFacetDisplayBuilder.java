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

package com.liferay.portal.search.web.internal.facet.display.builder;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.security.permission.comparator.ModelResourceComparator;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SortedArrayList;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.facet.display.context.AssetEntriesSearchFacetDisplayContext;
import com.liferay.portal.search.web.internal.facet.display.context.AssetEntriesSearchFacetTermDisplayContext;
import com.liferay.portal.search.web.internal.type.facet.configuration.TypeFacetPortletInstanceConfiguration;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.RenderRequest;

/**
 * @author Lino Alves
 */
public class AssetEntriesSearchFacetDisplayBuilder implements Serializable {

	public AssetEntriesSearchFacetDisplayBuilder(RenderRequest renderRequest)
		throws ConfigurationException {

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		_typeFacetPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				TypeFacetPortletInstanceConfiguration.class);
	}

	public AssetEntriesSearchFacetDisplayContext build() {
		List<AssetEntriesSearchFacetTermDisplayContext> termDisplayContexts =
			buildTermDisplayContexts();

		AssetEntriesSearchFacetDisplayContext
			assetEntriesSearchFacetDisplayContext =
				new AssetEntriesSearchFacetDisplayContext();

		assetEntriesSearchFacetDisplayContext.setDisplayStyleGroupId(
			getDisplayStyleGroupId());
		assetEntriesSearchFacetDisplayContext.setNothingSelected(
			isNothingSelected());
		assetEntriesSearchFacetDisplayContext.setParameterName(_parameterName);
		assetEntriesSearchFacetDisplayContext.setParameterValue(
			getFirstParameterValue());
		assetEntriesSearchFacetDisplayContext.setParameterValues(
			_parameterValues);
		assetEntriesSearchFacetDisplayContext.setRenderNothing(
			ListUtil.isEmpty(termDisplayContexts));
		assetEntriesSearchFacetDisplayContext.setTermDisplayContexts(
			termDisplayContexts);
		assetEntriesSearchFacetDisplayContext.
			setTypeFacetPortletInstanceConfiguration(
				_typeFacetPortletInstanceConfiguration);

		return assetEntriesSearchFacetDisplayContext;
	}

	public AssetEntriesSearchFacetTermDisplayContext buildTermDisplay(
		String typeName, boolean selected, String assetType, int frequency) {

		AssetEntriesSearchFacetTermDisplayContext
			assetEntriesSearchFacetFieldDisplayContext =
				new AssetEntriesSearchFacetTermDisplayContext();

		assetEntriesSearchFacetFieldDisplayContext.setAssetType(assetType);
		assetEntriesSearchFacetFieldDisplayContext.setFrequency(frequency);
		assetEntriesSearchFacetFieldDisplayContext.setFrequencyVisible(
			_frequenciesVisible);
		assetEntriesSearchFacetFieldDisplayContext.setSelected(selected);
		assetEntriesSearchFacetFieldDisplayContext.setTypeName(typeName);

		return assetEntriesSearchFacetFieldDisplayContext;
	}

	public List<AssetEntriesSearchFacetTermDisplayContext>
		buildTermDisplayContexts() {

		if (_facet == null) {
			return Collections.emptyList();
		}

		FacetCollector facetCollector = _facet.getFacetCollector();

		if (facetCollector == null) {
			return Collections.emptyList();
		}

		List<AssetEntriesSearchFacetTermDisplayContext>
			assetEntriesSearchFacetFieldDisplayContexts = new ArrayList<>();

		List<String> assetTypes = new SortedArrayList<>(
			new ModelResourceComparator(_locale));

		for (String className : _classNames) {
			if (assetTypes.contains(className)) {
				continue;
			}

			assetTypes.add(className);
		}

		for (String assetType : assetTypes) {
			TermCollector termCollector = facetCollector.getTermCollector(
				assetType);

			int frequency = 0;

			if (termCollector != null) {
				frequency = termCollector.getFrequency();
			}

			if (_frequencyThreshold > frequency) {
				continue;
			}

			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(assetType);

			boolean selected = _parameterValues.contains(
				termCollector.getTerm());

			AssetEntriesSearchFacetTermDisplayContext
				assetEntriesSearchFacetFieldDisplayContext = buildTermDisplay(
					assetRendererFactory.getTypeName(_locale), selected,
					assetType, termCollector.getFrequency());

			assetEntriesSearchFacetFieldDisplayContexts.add(
				assetEntriesSearchFacetFieldDisplayContext);
		}

		return assetEntriesSearchFacetFieldDisplayContexts;
	}

	public int getPopularity(
		int frequency, int maxCount, int minCount, double multiplier) {

		int popularity = maxCount - (maxCount - (frequency - minCount));

		return (int)(1 + (popularity * multiplier));
	}

	public boolean isNothingSelected() {
		if (_parameterValues.isEmpty()) {
			return true;
		}

		return false;
	}

	public void setClassNames(String[] classNames) {
		_classNames = classNames;
	}

	public void setFacet(Facet facet) {
		_facet = facet;
	}

	public void setFrequenciesVisible(boolean frequenciesVisible) {
		_frequenciesVisible = frequenciesVisible;
	}

	public void setFrequencyThreshold(int frequencyThreshold) {
		_frequencyThreshold = frequencyThreshold;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setParameterName(String parameterName) {
		_parameterName = parameterName;
	}

	public void setParameterValue(String parameterValue) {
		parameterValue = StringUtil.trim(
			Objects.requireNonNull(parameterValue));

		if (parameterValue.isEmpty()) {
			return;
		}

		_parameterValues = Collections.singletonList(parameterValue);
	}

	public void setParameterValues(List<String> paramValues) {
		_parameterValues = paramValues;
	}

	protected long getDisplayStyleGroupId() {
		long displayStyleGroupId =
			_typeFacetPortletInstanceConfiguration.displayStyleGroupId();

		if (displayStyleGroupId <= 0) {
			displayStyleGroupId = _themeDisplay.getScopeGroupId();
		}

		return displayStyleGroupId;
	}

	protected String getFirstParameterValue() {
		if (_parameterValues.isEmpty()) {
			return StringPool.BLANK;
		}

		return _parameterValues.get(0);
	}

	private String[] _classNames;
	private Facet _facet;
	private boolean _frequenciesVisible;
	private int _frequencyThreshold;
	private Locale _locale;
	private String _parameterName;
	private List<String> _parameterValues = Collections.emptyList();
	private final ThemeDisplay _themeDisplay;
	private final TypeFacetPortletInstanceConfiguration
		_typeFacetPortletInstanceConfiguration;

}