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

package com.liferay.commerce.product.content.search.web.internal.display.builder;

import com.liferay.commerce.product.content.search.web.internal.display.context.CPSpecificationOptionsSearchFacetDisplayContext;
import com.liferay.commerce.product.content.search.web.internal.display.context.CPSpecificationOptionsSearchFacetTermDisplayContext;
import com.liferay.commerce.product.content.search.web.internal.util.CPSpecificationOptionFacetsUtil;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Tuple;
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
public class CPSpecificationOptionsSearchFacetDisplayBuilder
	implements Serializable {

	public CPSpecificationOptionsSearchFacetDisplayBuilder(
		RenderRequest renderRequest) {

		_renderRequest = renderRequest;
	}

	public CPSpecificationOptionsSearchFacetDisplayContext build()
		throws PortalException {

		_buckets = _collectBuckets(_facet.getFacetCollector());

		CPSpecificationOptionsSearchFacetDisplayContext
			cpSpecificationOptionsSearchFacetDisplayContext =
				_createCPSpecificationOptionsSearchFacetDisplayContext();

		cpSpecificationOptionsSearchFacetDisplayContext.setCloud(_isCloud());
		cpSpecificationOptionsSearchFacetDisplayContext.setNothingSelected(
			isNothingSelected());
		cpSpecificationOptionsSearchFacetDisplayContext.
			setPaginationStartParameterName(_paginationStartParameterName);

		cpSpecificationOptionsSearchFacetDisplayContext.setParameterValue(
			getFirstParameterValueString());
		cpSpecificationOptionsSearchFacetDisplayContext.setParameterValues(
			getParameterValueStrings());
		cpSpecificationOptionsSearchFacetDisplayContext.setRenderRequest(
			_renderRequest);
		cpSpecificationOptionsSearchFacetDisplayContext.setRenderNothing(
			isRenderNothing());
		cpSpecificationOptionsSearchFacetDisplayContext.setTermDisplayContexts(
			buildTermDisplayContexts());
		cpSpecificationOptionsSearchFacetDisplayContext.
			setCpSpecificationOptionLocalService(
				_cpSpecificationOptionLocalService);
		cpSpecificationOptionsSearchFacetDisplayContext.setLocale(_locale);

		cpSpecificationOptionsSearchFacetDisplayContext.setFacet(_facet);

		cpSpecificationOptionsSearchFacetDisplayContext.setParameterName(
			CPSpecificationOptionFacetsUtil.
				getCPSpecificationOptionKeyFromIndexFieldName(
					_facet.getFieldName()));

		return cpSpecificationOptionsSearchFacetDisplayContext;
	}

	public CPSpecificationOption getCPSpecificationOption(String fieldName)
		throws PortalException {

		String key =
			CPSpecificationOptionFacetsUtil.
				getCPSpecificationOptionKeyFromIndexFieldName(fieldName);

		return _cpSpecificationOptionLocalService.fetchCPSpecificationOption(
			PortalUtil.getCompanyId(_renderRequest), key);
	}

	public boolean isCPDefinitionSpecificationOptionValueSelected(
			String fieldName, String fieldValue)
		throws PortalException {

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

	public void setCPSpecificationOptionLocalService(
		CPSpecificationOptionLocalService cpSpecificationOptionLocalService) {

		_cpSpecificationOptionLocalService = cpSpecificationOptionLocalService;
	}

	public void setCPSpecificationOptionPermissionChecker(
		CPSpecificationOptionPermissionChecker
			cpSpecificationOptionPermissionChecker) {

		_cpSpecificationOptionPermissionChecker =
			cpSpecificationOptionPermissionChecker;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setExcludedGroupId(long excludedGroupId) {
		_excludedGroupId = excludedGroupId;
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

	public void setMaxTerms(int maxTerms) {
		_maxTerms = maxTerms;
	}

	public void setPaginationStartParameterName(
		String paginationStartParameterName) {

		_paginationStartParameterName = paginationStartParameterName;
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

	public void setPortal(Portal portal) {
		_portal = portal;
	}

	public void setPortletSharedSearchResponse(
		PortletSharedSearchResponse portletSharedSearchResponse) {

		_portletSharedSearchResponse = portletSharedSearchResponse;
	}

	protected CPSpecificationOptionsSearchFacetTermDisplayContext
		buildTermDisplayContext(
			CPSpecificationOption cpSpecificationOption, int frequency,
			boolean selected, int popularity, String term) {

		CPSpecificationOptionsSearchFacetTermDisplayContext
			cpSpecificationOptionsSearchFacetTermDisplayContext =
				new CPSpecificationOptionsSearchFacetTermDisplayContext();

		cpSpecificationOptionsSearchFacetTermDisplayContext.
			setCPSpecificationOptionId(
				cpSpecificationOption.getCPSpecificationOptionId());
		cpSpecificationOptionsSearchFacetTermDisplayContext.setFrequency(
			frequency);
		cpSpecificationOptionsSearchFacetTermDisplayContext.setFrequencyVisible(
			_frequenciesVisible);
		cpSpecificationOptionsSearchFacetTermDisplayContext.setPopularity(
			popularity);
		cpSpecificationOptionsSearchFacetTermDisplayContext.setSelected(
			selected);
		cpSpecificationOptionsSearchFacetTermDisplayContext.setDisplayName(
			term);

		return cpSpecificationOptionsSearchFacetTermDisplayContext;
	}

	protected List<CPSpecificationOptionsSearchFacetTermDisplayContext>
			buildTermDisplayContexts()
		throws PortalException {

		if (_buckets.isEmpty()) {
			return getEmptyTermDisplayContexts();
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
				buildTermDisplayContext(
					getCPSpecificationOption(fieldName), frequency,
					isCPDefinitionSpecificationOptionValueSelected(
						fieldName, fieldValue),
					popularity, fieldValue));
		}

		return cpSpecificationOptionsSearchFacetTermDisplayContexts;
	}

	protected List<CPSpecificationOptionsSearchFacetTermDisplayContext>
		getEmptyTermDisplayContexts() {

		return Collections.emptyList();
	}

	protected String getFirstParameterValueString() {
		if (_selectedCPSpecificationOptionIds.isEmpty()) {
			return StringPool.BLANK;
		}

		return String.valueOf(_selectedCPSpecificationOptionIds.get(0));
	}

	protected List<String> getParameterValueStrings() {
		Stream<Long> categoryIdsStream =
			_selectedCPSpecificationOptionIds.stream();

		return categoryIdsStream.map(
			String::valueOf
		).collect(
			Collectors.toList()
		);
	}

	protected double getPopularity(
		int frequency, int maxCount, int minCount, double multiplier) {

		double popularity = maxCount - (maxCount - (frequency - minCount));

		return 1 + (popularity * multiplier);
	}

	protected boolean isNothingSelected() {
		if (_selectedCPSpecificationOptionIds.isEmpty()) {
			return true;
		}

		return false;
	}

	protected boolean isRenderNothing() {
		if (isNothingSelected() && _buckets.isEmpty()) {
			return true;
		}

		return false;
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

	private CPSpecificationOptionsSearchFacetDisplayContext
		_createCPSpecificationOptionsSearchFacetDisplayContext() {

		try {
			return new CPSpecificationOptionsSearchFacetDisplayContext(
				_portal.getHttpServletRequest(_renderRequest));
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

	private boolean _isCloud() {
		if (_frequenciesVisible && _displayStyle.equals("cloud")) {
			return true;
		}

		return false;
	}

	private List<Tuple> _buckets;
	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;
	private CPSpecificationOptionPermissionChecker
		_cpSpecificationOptionPermissionChecker;
	private String _displayStyle;
	private long _excludedGroupId;
	private Facet _facet;
	private boolean _frequenciesVisible;
	private int _frequencyThreshold;
	private Locale _locale;
	private int _maxTerms;
	private String _paginationStartParameterName;
	private Portal _portal;
	private PortletSharedSearchResponse _portletSharedSearchResponse;
	private final RenderRequest _renderRequest;
	private List<Long> _selectedCPSpecificationOptionIds =
		Collections.emptyList();

}