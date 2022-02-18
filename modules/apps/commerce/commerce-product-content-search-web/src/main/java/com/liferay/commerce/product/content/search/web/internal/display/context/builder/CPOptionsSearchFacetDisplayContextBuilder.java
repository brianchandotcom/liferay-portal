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

import com.liferay.commerce.product.content.search.web.internal.display.context.CPOptionsSearchFacetDisplayContext;
import com.liferay.commerce.product.content.search.web.internal.display.context.CPOptionsSearchFacetTermDisplayContext;
import com.liferay.commerce.product.content.search.web.internal.util.CPOptionFacetsUtil;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPOptionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.RenderRequest;

/**
 * @author Andrea Sbarra
 */
public class CPOptionsSearchFacetDisplayContextBuilder implements Serializable {

	public CPOptionsSearchFacetDisplayContextBuilder(
		RenderRequest renderRequest) {

		_renderRequest = renderRequest;
	}

	public CPOptionsSearchFacetDisplayContext build() {
		CPOptionsSearchFacetDisplayContext cpOptionsSearchFacetDisplayContext =
			_createCPOptionsSearchFacetDisplayContext();

		cpOptionsSearchFacetDisplayContext.setFacets(_getFacets());
		cpOptionsSearchFacetDisplayContext.setCPOptionLocalService(
			_cpOptionLocalService);
		cpOptionsSearchFacetDisplayContext.setPortletSharedSearchResponse(
			_portletSharedSearchRequest.search(_renderRequest));
		cpOptionsSearchFacetDisplayContext.setPaginationStartParameterName(
			_paginationStartParameterName);
		cpOptionsSearchFacetDisplayContext.setTermDisplayContexts(
			buildTermDisplayContexts());

		return cpOptionsSearchFacetDisplayContext;
	}

	public void cpOptionLocalService(
		CPOptionLocalService cpOptionLocalService) {

		_cpOptionLocalService = cpOptionLocalService;
	}

	public void displayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void facet(Facet facet) {
		_facet = facet;
	}

	public void frequenciesVisible(boolean frequenciesVisible) {
		_frequenciesVisible = frequenciesVisible;
	}

	public void frequencyThreshold(int frequencyThreshold) {
		_frequencyThreshold = frequencyThreshold;
	}

	public void maxTerms(int maxTerms) {
		_maxTerms = maxTerms;
	}

	public void paginationStartParameterName(
		String paginationStartParameterName) {

		_paginationStartParameterName = paginationStartParameterName;
	}

	public void portal(Portal portal) {
		_portal = portal;
	}

	public void portletSharedSearchRequest(
		PortletSharedSearchRequest portletSharedSearchRequest) {

		_portletSharedSearchRequest = portletSharedSearchRequest;
	}

	protected CPOptionsSearchFacetTermDisplayContext buildTermDisplayContext(
		int frequency, int popularity, boolean selected, String term) {

		CPOptionsSearchFacetTermDisplayContext
			cpOptionsSearchFacetTermDisplayContext =
				new CPOptionsSearchFacetTermDisplayContext();

		cpOptionsSearchFacetTermDisplayContext.setTerm(term);
		cpOptionsSearchFacetTermDisplayContext.setFrequency(frequency);
		cpOptionsSearchFacetTermDisplayContext.setFrequencyVisible(
			_frequenciesVisible);
		cpOptionsSearchFacetTermDisplayContext.setPopularity(popularity);
		cpOptionsSearchFacetTermDisplayContext.setSelected(selected);

		return cpOptionsSearchFacetTermDisplayContext;
	}

	protected List<CPOptionsSearchFacetTermDisplayContext>
		buildTermDisplayContexts() {

		if (ListUtil.isEmpty(_tuples)) {
			return getEmptyTermDisplayContexts();
		}

		List<CPOptionsSearchFacetTermDisplayContext>
			cpOptionsSearchFacetTermDisplayContexts = new ArrayList<>(
				_tuples.size());

		int maxCount = 1;
		int minCount = 1;

		if (_frequenciesVisible && _displayStyle.equals("cloud")) {

			// The cloud style may not list tags in the order of frequency.
			// Keep looking through the results until we reach the maximum
			// number of terms or we run out of terms.

			for (int i = 0, j = 0; i < _tuples.size(); i++, j++) {
				if (j >= _maxTerms) {
					break;
				}

				Tuple tuple = _tuples.get(i);

				Integer frequency = (Integer)tuple.getObject(2);

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

		for (int i = 0, j = 0; i < _tuples.size(); i++, j++) {
			if ((_maxTerms > 0) && (j >= _maxTerms)) {
				break;
			}

			Tuple tuple = _tuples.get(i);

			Integer frequency = (Integer)tuple.getObject(2);

			if (_frequencyThreshold > frequency) {
				j--;

				continue;
			}

			int popularity = (int)getPopularity(
				frequency, maxCount, minCount, multiplier);

			CPOption cpOption = (CPOption)tuple.getObject(0);

			String term = (String)tuple.getObject(1);

			cpOptionsSearchFacetTermDisplayContexts.add(
				buildTermDisplayContext(
					frequency, popularity, isSelected(cpOption, term), term));
		}

		return cpOptionsSearchFacetTermDisplayContexts;
	}

	protected List<CPOptionsSearchFacetTermDisplayContext>
		getEmptyTermDisplayContexts() {

		Stream<Long> categoryIdsStream = _selectedCategoryIds.stream();

		return categoryIdsStream.map(
			this::_getEmptyTermDisplayContext
		).filter(
			Optional::isPresent
		).map(
			Optional::get
		).collect(
			Collectors.toList()
		);
	}

	protected double getPopularity(
		int frequency, int maxCount, int minCount, double multiplier) {

		double popularity = maxCount - (maxCount - (frequency - minCount));

		return 1 + (popularity * multiplier);
	}

	protected boolean isSelected(CPOption cpOption, String fieldValue) {
		PortletSharedSearchResponse portletSharedSearchResponse =
			_portletSharedSearchRequest.search(_renderRequest);

		Optional<String[]> parameterValuesOptional =
			portletSharedSearchResponse.getParameterValues(
				cpOption.getKey(), _renderRequest);

		if (parameterValuesOptional.isPresent()) {
			String[] parameterValues = parameterValuesOptional.get();

			return ArrayUtil.contains(parameterValues, fieldValue);
		}

		return false;
	}

	private CPOptionsSearchFacetDisplayContext
		_createCPOptionsSearchFacetDisplayContext() {

		try {
			return new CPOptionsSearchFacetDisplayContext(
				_portal.getHttpServletRequest(_renderRequest));
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	private Optional<CPOptionsSearchFacetTermDisplayContext>
		_getEmptyTermDisplayContext(long cpOptionId) {

		return Optional.ofNullable(
			_cpOptionLocalService.fetchCPOption(cpOptionId)
		).map(
			cpOption -> buildTermDisplayContext(0, 1, true, StringPool.BLANK)
		);
	}

	private List<Facet> _getFacets() {
		List<Facet> filledFacets = new ArrayList<>();

		FacetCollector facetCollector = _facet.getFacetCollector();

		PortletSharedSearchResponse portletSharedSearchResponse =
			_portletSharedSearchRequest.search(_renderRequest);

		ThemeDisplay themeDisplay = portletSharedSearchResponse.getThemeDisplay(
			_renderRequest);

		for (TermCollector termCollector : facetCollector.getTermCollectors()) {
			CPOption cpOption = _cpOptionLocalService.fetchCPOption(
				themeDisplay.getCompanyId(), termCollector.getTerm());

			if ((cpOption != null) && cpOption.isFacetable()) {
				Facet facet = portletSharedSearchResponse.getFacet(
					CPOptionFacetsUtil.getIndexFieldName(
						termCollector.getTerm(), themeDisplay.getLanguageId()));

				filledFacets.add(facet);
				_tuples = _getTuples(cpOption, facet.getFacetCollector());
			}
		}

		return filledFacets;
	}

	private List<Tuple> _getTuples(
		CPOption cpOption, FacetCollector facetCollector) {

		List<TermCollector> termCollectors = facetCollector.getTermCollectors();

		List<Tuple> tuples = new ArrayList<>(termCollectors.size());

		for (TermCollector termCollector : termCollectors) {
			tuples.add(
				new Tuple(
					cpOption, termCollector.getTerm(),
					termCollector.getFrequency()));
		}

		return tuples;
	}

	private CPOptionLocalService _cpOptionLocalService;
	private String _displayStyle;
	private Facet _facet;
	private boolean _frequenciesVisible;
	private int _frequencyThreshold;
	private int _maxTerms;
	private String _paginationStartParameterName;
	private Portal _portal;
	private PortletSharedSearchRequest _portletSharedSearchRequest;
	private final RenderRequest _renderRequest;
	private final List<Long> _selectedCategoryIds = Collections.emptyList();
	private List<Tuple> _tuples;

}