/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.search.web.internal.display.context.builder;

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.content.search.web.internal.display.context.CPSpecificationOptionFacetsDisplayContext;
import com.liferay.commerce.product.content.search.web.internal.display.context.CPSpecificationOptionsSearchFacetDisplayContext;
import com.liferay.commerce.product.content.search.web.internal.display.context.CPSpecificationOptionsSearchFacetTermDisplayContext;
import com.liferay.commerce.product.content.search.web.internal.util.CPSpecificationOptionFacetsUtil;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletPreferences;
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
				new CPSpecificationOptionFacetsDisplayContext(
					_portal.getHttpServletRequest(_renderRequest));

		cpSpecificationOptionFacetsDisplayContext.
			setCPSpecificationOptionsSearchFacetDisplayContexts(
				_buildCPSpecificationOptionsSearchFacetDisplayContexts());

		return cpSpecificationOptionFacetsDisplayContext;
	}

	public void cpSpecificationOptionLocalService(
		CPSpecificationOptionLocalService cpSpecificationOptionLocalService) {

		_cpSpecificationOptionLocalService = cpSpecificationOptionLocalService;
	}

	public void parameterValues(String... parameterValues) {
		_parameterValues = parameterValues;
	}

	public void portal(Portal portal) {
		_portal = portal;
	}

	public void portletSharedSearchRequest(
		PortletSharedSearchRequest portletSharedSearchRequest) {

		_portletSharedSearchRequest = portletSharedSearchRequest;
	}

	public void renderRequest(RenderRequest renderRequest) {
		_renderRequest = renderRequest;
	}

	private CPSpecificationOptionsSearchFacetDisplayContext
		_buildCPSpecificationOptionsSearchFacetDisplayContext() {

		_tuples = _getTuples(_facet.getFacetCollector());

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
			_getFirstParameterValueString());
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

		parameterValues(
			portletSharedSearchResponse.getParameterValues(
				facet.getFieldName(), renderRequest));

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

		PortletPreferences portletPreferences =
			_portletSharedSearchResponse.getPortletPreferences(_renderRequest);

		if (portletPreferences != null) {
			_displayStyle = portletPreferences.getValue(
				"displayStyle", _displayStyle);
			_frequencyThreshold = GetterUtil.getInteger(
				portletPreferences.getValue("frequencyThreshold", null),
				_frequencyThreshold);
			_frequenciesVisible = GetterUtil.getBoolean(
				portletPreferences.getValue("frequenciesVisible", "true"),
				_frequenciesVisible);
			_maxTerms = GetterUtil.getInteger(
				portletPreferences.getValue("maxTerms", null), _maxTerms);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		for (TermCollector termCollector : facetCollector.getTermCollectors()) {
			CPSpecificationOption cpSpecificationOption =
				_cpSpecificationOptionLocalService.getCPSpecificationOption(
					themeDisplay.getCompanyId(), termCollector.getTerm());

			if (cpSpecificationOption.isFacetable()) {
				filledFacets.add(
					_portletSharedSearchResponse.getFacet(
						CPSpecificationOptionFacetsUtil.getIndexFieldName(
							termCollector.getTerm(),
							themeDisplay.getLanguageId())));
			}
		}

		List<CPSpecificationOptionsSearchFacetDisplayContext>
			cpSpecificationOptionsSearchFacetDisplayContexts =
				new ArrayList<>();

		_paginationStartParameterName = _getPaginationStartParameterName(
			_portletSharedSearchResponse);

		_locale = themeDisplay.getLocale();

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

		if (_tuples.isEmpty()) {
			return Collections.emptyList();
		}

		List<CPSpecificationOptionsSearchFacetTermDisplayContext>
			cpSpecificationOptionsSearchFacetTermDisplayContexts =
				new ArrayList<>(_tuples.size());

		int maxCount = 1;
		int minCount = 1;

		if (_frequenciesVisible &&
			_displayStyle.equals(
				"ddmTemplate_CP-SPECIFICATION-OPTION-FACET-CLOUD-FTL")) {

			// The cloud style may not list tags in the order of frequency.
			// Keep looking through the results until we reach the maximum
			// number of terms or we run out of terms.

			for (int i = 0, j = 0; i < _tuples.size(); i++, j++) {
				if (j >= _maxTerms) {
					break;
				}

				Tuple tuple = _tuples.get(i);

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

		for (int i = 0, j = 0; i < _tuples.size(); i++, j++) {
			if ((_maxTerms > 0) && (j >= _maxTerms)) {
				break;
			}

			Tuple tuple = _tuples.get(i);

			Integer frequency = (Integer)tuple.getObject(1);

			if (_frequencyThreshold > frequency) {
				j--;

				continue;
			}

			int popularity = (int)_getPopularity(
				frequency, maxCount, minCount, multiplier);

			String fieldName = (String)tuple.getObject(0);

			String fieldValue = (String)tuple.getObject(2);

			cpSpecificationOptionsSearchFacetTermDisplayContexts.add(
				_buildTermDisplayContext(
					frequency,
					_isCPDefinitionSpecificationOptionValueSelected(
						fieldName, fieldValue),
					popularity, fieldValue));
		}

		return cpSpecificationOptionsSearchFacetTermDisplayContexts;
	}

	private CPSpecificationOption _getCPSpecificationOption(String fieldName) {
		return _cpSpecificationOptionLocalService.fetchCPSpecificationOption(
			PortalUtil.getCompanyId(_renderRequest),
			CPSpecificationOptionFacetsUtil.
				getCPSpecificationOptionKeyFromIndexFieldName(fieldName));
	}

	private String _getFirstParameterValueString() {
		if (_parameterValues != null) {
			for (String parameterValue : _parameterValues) {
				if (GetterUtil.getLong(parameterValue) > 0) {
					return parameterValue;
				}
			}
		}

		return StringPool.BLANK;
	}

	private String _getPaginationStartParameterName(
		PortletSharedSearchResponse portletSharedSearchResponse) {

		SearchResponse searchResponse =
			portletSharedSearchResponse.getSearchResponse();

		SearchRequest searchRequest = searchResponse.getRequest();

		return searchRequest.getPaginationStartParameterName();
	}

	private double _getPopularity(
		int frequency, int maxCount, int minCount, double multiplier) {

		double popularity = maxCount - (maxCount - (frequency - minCount));

		return 1 + (popularity * multiplier);
	}

	private List<Tuple> _getTuples(FacetCollector facetCollector) {
		List<TermCollector> termCollectors = facetCollector.getTermCollectors();

		List<Tuple> tuples = new ArrayList<>(termCollectors.size());

		for (TermCollector termCollector : termCollectors) {
			tuples.add(
				new Tuple(
					facetCollector.getFieldName(), termCollector.getFrequency(),
					termCollector.getTerm()));
		}

		return tuples;
	}

	private boolean _isCPDefinitionSpecificationOptionValueSelected(
		String fieldName, String fieldValue) {

		CPSpecificationOption cpSpecificationOption = _getCPSpecificationOption(
			fieldName);

		return ArrayUtil.contains(
			_portletSharedSearchResponse.getParameterValues(
				cpSpecificationOption.getKey(), _renderRequest),
			fieldValue);
	}

	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;
	private String _displayStyle = StringPool.BLANK;
	private Facet _facet;
	private boolean _frequenciesVisible = true;
	private int _frequencyThreshold = 1;
	private Locale _locale;
	private int _maxTerms = 10;
	private String _paginationStartParameterName;
	private String[] _parameterValues;
	private Portal _portal;
	private PortletSharedSearchRequest _portletSharedSearchRequest;
	private PortletSharedSearchResponse _portletSharedSearchResponse;
	private RenderRequest _renderRequest;
	private List<Tuple> _tuples;

}