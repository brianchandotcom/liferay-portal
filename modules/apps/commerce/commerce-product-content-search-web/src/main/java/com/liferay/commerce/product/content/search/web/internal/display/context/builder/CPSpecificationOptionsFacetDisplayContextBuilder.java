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

package com.liferay.commerce.product.content.search.web.internal.display.context.builder;

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.content.search.web.internal.configuration.CPSpecificationOptionFacetPortletInstanceConfiguration;
import com.liferay.commerce.product.content.search.web.internal.configuration.CPSpecificationOptionsFacetConfiguration;
import com.liferay.commerce.product.content.search.web.internal.display.context.CPSpecificationOptionFacetsDisplayContext;
import com.liferay.commerce.product.content.search.web.internal.display.context.CPSpecificationOptionsSearchFacetDisplayContext;
import com.liferay.commerce.product.content.search.web.internal.display.context.CPSpecificationOptionsSearchFacetTermDisplayContext;
import com.liferay.commerce.product.content.search.web.internal.portlet.CPSpecificationOptionFacetPortletPreferences;
import com.liferay.commerce.product.content.search.web.internal.util.CPSpecificationOptionFacetsUtil;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.RenderRequest;

/**
 * @author Crescenzo Rega
 */
public class CPSpecificationOptionsFacetDisplayContextBuilder
	implements Serializable {

	public CPSpecificationOptionFacetsDisplayContext build()
		throws PortalException {

		CPSpecificationOptionFacetsDisplayContext
			cpSpecificationOptionFacetsDisplayContext =
				new CPSpecificationOptionFacetsDisplayContext();

		cpSpecificationOptionFacetsDisplayContext.
			setCpSpecificationOptionsSearchFacetDisplayContext(
				_buildCPSpecificationOptionsSearchFacetDisplayContexts());

		cpSpecificationOptionFacetsDisplayContext.setThemeDisplay(
			_themeDisplay);

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		cpSpecificationOptionFacetsDisplayContext.
			setCpSpecificationOptionFacetPortletInstanceConfiguration(
				portletDisplay.getPortletInstanceConfiguration(
					CPSpecificationOptionFacetPortletInstanceConfiguration.
						class));

		cpSpecificationOptionFacetsDisplayContext.setRenderRequest(
			_renderRequest);

		return cpSpecificationOptionFacetsDisplayContext;
	}

	public void setCpSpecificationOptionLocalService(
		CPSpecificationOptionLocalService cpSpecificationOptionLocalService) {

		_cpSpecificationOptionLocalService = cpSpecificationOptionLocalService;
	}

	public void setParameterValues(String... parameterValues) {
		_selectedCPSpecificationOptionIds = Stream.of(
			Objects.requireNonNull(parameterValues)
		).map(
			GetterUtil::getLong
		).filter(
			categoryId -> categoryId > 0
		).collect(
			Collectors.toList()
		);
	}

	public void setPortletSharedSearchRequest(
		PortletSharedSearchRequest portletSharedSearchRequest) {

		_portletSharedSearchRequest = portletSharedSearchRequest;
	}

	public void setRenderRequest(RenderRequest renderRequest) {
		_renderRequest = renderRequest;
	}

	public void setThemeDisplay(ThemeDisplay themeDisplay) {
		_themeDisplay = themeDisplay;
	}

	protected CPSpecificationOption getCPSpecificationOption(String fieldName) {
		return _cpSpecificationOptionLocalService.fetchCPSpecificationOption(
			PortalUtil.getCompanyId(_renderRequest),
			CPSpecificationOptionFacetsUtil.
				getCPSpecificationOptionKeyFromIndexFieldName(fieldName));
	}

	protected String getFirstParameterValueString() {
		if (_selectedCPSpecificationOptionIds.isEmpty()) {
			return StringPool.BLANK;
		}

		return String.valueOf(_selectedCPSpecificationOptionIds.get(0));
	}

	protected String getPaginationStartParameterName(
		PortletSharedSearchResponse portletSharedSearchResponse) {

		SearchResponse searchResponse =
			portletSharedSearchResponse.getSearchResponse();

		SearchRequest searchRequest = searchResponse.getRequest();

		return searchRequest.getPaginationStartParameterName();
	}

	protected double getPopularity(
		int frequency, int maxCount, int minCount, double multiplier) {

		double popularity = maxCount - (maxCount - (frequency - minCount));

		return 1 + (popularity * multiplier);
	}

	protected boolean isCPDefinitionSpecificationOptionValueSelected(
		String fieldName, String fieldValue) {

		CPSpecificationOption cpSpecificationOption = getCPSpecificationOption(
			fieldName);

		Optional<String[]> parameterValuesOptional =
			_portletSharedSearchResponse.getParameterValues(
				cpSpecificationOption.getKey(), _renderRequest);

		if (parameterValuesOptional.isPresent()) {
			String[] parameterValues = parameterValuesOptional.get();

			return ArrayUtil.contains(parameterValues, fieldValue);
		}

		return false;
	}

	private CPSpecificationOptionsSearchFacetDisplayContext
		_buildCPSpecificationOptionsSearchFacetDisplayContext() {

		_buckets = _collectBuckets(_facet.getFacetCollector());

		CPSpecificationOptionsSearchFacetDisplayContext
			cpSpecificationOptionsSearchFacetDisplayContext =
				new CPSpecificationOptionsSearchFacetDisplayContext();

		cpSpecificationOptionsSearchFacetDisplayContext.setFacet(_facet);
		cpSpecificationOptionsSearchFacetDisplayContext.setLocale(_locale);
		cpSpecificationOptionsSearchFacetDisplayContext.setParameterName(
			CPSpecificationOptionFacetsUtil.
				getCPSpecificationOptionKeyFromIndexFieldName(
					_facet.getFieldName()));
		cpSpecificationOptionsSearchFacetDisplayContext.
			setPaginationStartParameterName(_paginationStartParameterName);
		cpSpecificationOptionsSearchFacetDisplayContext.setParameterValue(
			getFirstParameterValueString());
		cpSpecificationOptionsSearchFacetDisplayContext.setRenderRequest(
			_renderRequest);
		cpSpecificationOptionsSearchFacetDisplayContext.setTermDisplayContexts(
			_buildTermDisplayContexts());
		cpSpecificationOptionsSearchFacetDisplayContext.
			setCpSpecificationOptionLocalService(
				_cpSpecificationOptionLocalService);

		return cpSpecificationOptionsSearchFacetDisplayContext;
	}

	private CPSpecificationOptionsSearchFacetDisplayContext
		_buildCPSpecificationOptionsSearchFacetDisplayContext(
			Facet facet,
			PortletSharedSearchResponse portletSharedSearchResponse,
			RenderRequest renderRequest) {

		_facet = facet;

		CPSpecificationOptionFacetsUtil.copy(
			() -> portletSharedSearchResponse.getParameterValues(
				facet.getFieldName(), renderRequest),
			this::setParameterValues);

		return _buildCPSpecificationOptionsSearchFacetDisplayContext();
	}

	private List<CPSpecificationOptionsSearchFacetDisplayContext>
			_buildCPSpecificationOptionsSearchFacetDisplayContexts()
		throws PortalException {

		_portletSharedSearchResponse = _portletSharedSearchRequest.search(
			_renderRequest);

		List<Facet> filledFacets = new ArrayList<>();

		Facet facet = _portletSharedSearchResponse.getFacet(
			CPField.SPECIFICATION_NAMES);

		FacetCollector facetCollector = facet.getFacetCollector();

		CPSpecificationOptionFacetPortletPreferences
			cpSpecificationOptionFacetPortletPreferences =
				new CPSpecificationOptionFacetPortletPreferences(
					_portletSharedSearchResponse.getPortletPreferences(
						_renderRequest));

		_displayStyle =
			cpSpecificationOptionFacetPortletPreferences.getDisplayStyle();

		_frequenciesVisible =
			cpSpecificationOptionFacetPortletPreferences.isFrequenciesVisible();

		CPSpecificationOptionsFacetConfiguration
			cpSpecificationOptionsFacetConfiguration =
				new CPSpecificationOptionsFacetConfiguration(
					facet.getFacetConfiguration());

		_frequencyThreshold =
			cpSpecificationOptionsFacetConfiguration.getFrequencyThreshold();

		_maxTerms = cpSpecificationOptionsFacetConfiguration.getMaxTerms();

		for (TermCollector termCollector : facetCollector.getTermCollectors()) {
			CPSpecificationOption cpSpecificationOption =
				_cpSpecificationOptionLocalService.getCPSpecificationOption(
					_themeDisplay.getCompanyId(), termCollector.getTerm());

			if (cpSpecificationOption.isFacetable()) {
				filledFacets.add(
					_portletSharedSearchResponse.getFacet(
						CPSpecificationOptionFacetsUtil.getIndexFieldName(
							termCollector.getTerm(),
							_themeDisplay.getLanguageId())));
			}
		}

		List<CPSpecificationOptionsSearchFacetDisplayContext>
			cpSpecificationOptionsSearchFacetDisplayContexts =
				new ArrayList<>();

		_paginationStartParameterName = getPaginationStartParameterName(
			_portletSharedSearchResponse);

		_locale = _themeDisplay.getLocale();

		for (Facet filledFacet : filledFacets) {
			CPSpecificationOptionsSearchFacetDisplayContext
				cpSpecificationOptionsSearchFacetDisplayContext =
					_buildCPSpecificationOptionsSearchFacetDisplayContext(
						filledFacet, _portletSharedSearchResponse,
						_renderRequest);

			cpSpecificationOptionsSearchFacetDisplayContexts.add(
				cpSpecificationOptionsSearchFacetDisplayContext);
		}

		return cpSpecificationOptionsSearchFacetDisplayContexts;
	}

	private CPSpecificationOptionsSearchFacetTermDisplayContext
		_buildTermDisplayContext(
			int frequency, boolean selected, int popularity, String term) {

		CPSpecificationOptionsSearchFacetTermDisplayContext
			cpSpecificationOptionsSearchFacetTermDisplayContext =
				new CPSpecificationOptionsSearchFacetTermDisplayContext();

		cpSpecificationOptionsSearchFacetTermDisplayContext.setDisplayName(
			term);
		cpSpecificationOptionsSearchFacetTermDisplayContext.setFrequency(
			frequency);
		cpSpecificationOptionsSearchFacetTermDisplayContext.setFrequencyVisible(
			_frequenciesVisible);
		cpSpecificationOptionsSearchFacetTermDisplayContext.setPopularity(
			popularity);
		cpSpecificationOptionsSearchFacetTermDisplayContext.setSelected(
			selected);

		return cpSpecificationOptionsSearchFacetTermDisplayContext;
	}

	private List<CPSpecificationOptionsSearchFacetTermDisplayContext>
		_buildTermDisplayContexts() {

		if (_buckets.isEmpty()) {
			return Collections.emptyList();
		}

		List<CPSpecificationOptionsSearchFacetTermDisplayContext>
			cpSpecificationOptionsSearchFacetTermDisplayContexts =
				new ArrayList<>(_buckets.size());

		int maxCount = 1;
		int minCount = 1;

		if (_frequenciesVisible && _displayStyle.equals("cloud")) {

			// The cloud style may not list tags in the order of frequency,
			// so keep looking through the results until we reach the maximum
			// number of terms or we run out of terms.

			for (int i = 0, j = 0; i < _buckets.size(); i++, j++) {
				if (j >= _maxTerms) {
					break;
				}

				Tuple tuple = _buckets.get(i);

				Integer frequency = (Integer)tuple.getObject(1);

				if (_frequencyThreshold > frequency) {
					j--;

					continue;
				}

				maxCount = Math.max(maxCount, frequency);
				minCount = Math.min(minCount, frequency);
			}
		}

		double multiplier = 1;

		if (maxCount != minCount) {
			multiplier = (double)5 / (maxCount - minCount);
		}

		for (int i = 0, j = 0; i < _buckets.size(); i++, j++) {
			if ((_maxTerms > 0) && (j >= _maxTerms)) {
				break;
			}

			Tuple tuple = _buckets.get(i);

			Integer frequency = (Integer)tuple.getObject(1);

			if (_frequencyThreshold > frequency) {
				j--;

				continue;
			}

			int popularity = (int)getPopularity(
				frequency, maxCount, minCount, multiplier);

			String fieldName = (String)tuple.getObject(0);

			String fieldValue = (String)tuple.getObject(2);

			cpSpecificationOptionsSearchFacetTermDisplayContexts.add(
				_buildTermDisplayContext(
					frequency,
					isCPDefinitionSpecificationOptionValueSelected(
						fieldName, fieldValue),
					popularity, fieldValue));
		}

		return cpSpecificationOptionsSearchFacetTermDisplayContexts;
	}

	private List<Tuple> _collectBuckets(FacetCollector facetCollector) {
		List<TermCollector> termCollectors = facetCollector.getTermCollectors();

		List<Tuple> buckets = new ArrayList<>(termCollectors.size());

		for (TermCollector termCollector : termCollectors) {
			buckets.add(
				new Tuple(
					facetCollector.getFieldName(), termCollector.getFrequency(),
					termCollector.getTerm()));
		}

		return buckets;
	}

	private List<Tuple> _buckets;
	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;
	private String _displayStyle;
	private Facet _facet;
	private boolean _frequenciesVisible;
	private int _frequencyThreshold;
	private Locale _locale;
	private int _maxTerms;
	private String _paginationStartParameterName;
	private PortletSharedSearchRequest _portletSharedSearchRequest;
	private PortletSharedSearchResponse _portletSharedSearchResponse;
	private RenderRequest _renderRequest;
	private List<Long> _selectedCPSpecificationOptionIds =
		Collections.emptyList();
	private ThemeDisplay _themeDisplay;

}