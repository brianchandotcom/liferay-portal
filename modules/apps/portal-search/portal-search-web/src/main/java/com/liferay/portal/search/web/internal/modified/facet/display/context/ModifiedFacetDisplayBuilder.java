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

package com.liferay.portal.search.web.internal.modified.facet.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactory;
import com.liferay.portal.kernel.util.DateFormatFactory;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.modified.facet.builder.DateRangeFactory;
import com.liferay.portal.search.web.internal.modified.facet.configuration.ModifiedFacetPortletInstanceConfiguration;

import java.io.Serializable;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.stream.Stream;

import javax.portlet.RenderRequest;

/**
 * @author Lino Alves
 * @author Adam Brandizzi
 */
public class ModifiedFacetDisplayBuilder implements Serializable {

	public ModifiedFacetDisplayBuilder(
			CalendarFactory calendarFactory,
			DateFormatFactory dateFormatFactory, Http http,
			RenderRequest renderRequest)
		throws ConfigurationException {

		_calendarFactory = calendarFactory;
		_dateFormatFactory = dateFormatFactory;
		_http = http;

		_dateRangeFactory = new DateRangeFactory(dateFormatFactory);

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		_modifiedFacetPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				ModifiedFacetPortletInstanceConfiguration.class);
	}

	public ModifiedFacetDisplayContext build() {
		ModifiedFacetDisplayContext modifiedFacetDisplayContext =
			new ModifiedFacetDisplayContext();

		if (_calendarFactory != null) {
			modifiedFacetDisplayContext.setCalendarDisplayContext(
				_buildCalendarDisplayContext());
		}

		if ((_dateFormatFactory != null) && (_dateRangeFactory != null)) {
			modifiedFacetDisplayContext.
				setCustomRangeModifiedFacetTermDisplayContext(
					_buildCustomRangeModifiedTermDisplayContext());
		}

		modifiedFacetDisplayContext.setDefaultModifiedFacetTermDisplayContext(
			_buildDefaultModifiedFacetTermDisplayContext());
		modifiedFacetDisplayContext.setDisplayStyleGroupId(
			getDisplayStyleGroupId());
		modifiedFacetDisplayContext.
			setModifiedFacetPortletInstanceConfiguration(
				_modifiedFacetPortletInstanceConfiguration);
		modifiedFacetDisplayContext.setModifiedFacetTermDisplayContexts(
			_buildTermDisplayContexts());
		modifiedFacetDisplayContext.setNothingSelected(isNothingSelected());
		modifiedFacetDisplayContext.setPaginationStartParameterName(
			_paginationStartParameterName);
		modifiedFacetDisplayContext.setParameterName(_parameterName);
		modifiedFacetDisplayContext.setRenderNothing(isRenderNothing());

		return modifiedFacetDisplayContext;
	}

	public void setCurrentURL(String currentURL) {
		_currentURL = currentURL;
	}

	public void setFacet(Facet facet) {
		_facet = facet;
	}

	public void setFromParameterValue(String from) {
		_from = from;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setPaginationStartParameterName(
		String paginationStartParameterName) {

		_paginationStartParameterName = paginationStartParameterName;
	}

	public void setParameterName(String parameterName) {
		_parameterName = parameterName;
	}

	public void setParameterValues(String... parameterValues) {
		_selectedRanges = Arrays.asList(
			Objects.requireNonNull(parameterValues));
	}

	public void setTimeZone(TimeZone timeZone) {
		_timeZone = timeZone;
	}

	public void setToParameterValue(String to) {
		_to = to;
	}

	public void setTotalHits(int totalHits) {
		_totalHits = totalHits;
	}

	protected long getDisplayStyleGroupId() {
		long displayStyleGroupId =
			_modifiedFacetPortletInstanceConfiguration.displayStyleGroupId();

		if (displayStyleGroupId <= 0) {
			displayStyleGroupId = _themeDisplay.getScopeGroupId();
		}

		return displayStyleGroupId;
	}

	protected int getFrequency(TermCollector termCollector) {
		if (termCollector != null) {
			return termCollector.getFrequency();
		}

		return 0;
	}

	protected TermCollector getTermCollector(String range) {
		if (_facet == null) {
			return null;
		}

		FacetCollector facetCollector = _facet.getFacetCollector();

		if (facetCollector == null) {
			return null;
		}

		return facetCollector.getTermCollector(range);
	}

	protected boolean isNothingSelected() {
		if (!_selectedRanges.isEmpty() ||
			(!Validator.isBlank(_from) && !Validator.isBlank(_to))) {

			return false;
		}

		return true;
	}

	protected boolean isRenderNothing() {
		if (_totalHits > 0) {
			return false;
		}

		return isNothingSelected();
	}

	private ModifiedFacetCalendarDisplayContext _buildCalendarDisplayContext() {
		ModifiedFacetCalendarDisplayBuilder
			modifiedFacetCalendarDisplayBuilder =
				new ModifiedFacetCalendarDisplayBuilder(_calendarFactory);

		Stream<String> selectedRangesStream = _selectedRanges.stream();

		selectedRangesStream.filter(
			s -> s.startsWith(StringPool.OPEN_CURLY_BRACE)
		).findAny(
		).ifPresent(
			modifiedFacetCalendarDisplayBuilder::setRangeString
		);

		modifiedFacetCalendarDisplayBuilder.setFrom(_from);
		modifiedFacetCalendarDisplayBuilder.setLocale(_locale);
		modifiedFacetCalendarDisplayBuilder.setTimeZone(_timeZone);
		modifiedFacetCalendarDisplayBuilder.setTo(_to);

		return modifiedFacetCalendarDisplayBuilder.build();
	}

	private ModifiedFacetTermDisplayContext
		_buildCustomRangeModifiedTermDisplayContext() {

		boolean selected = _isCustomRangeSelected();

		ModifiedFacetTermDisplayContext modifiedFacetTermDisplayContext =
			new ModifiedFacetTermDisplayContext();

		modifiedFacetTermDisplayContext.setFrequency(
			getFrequency(_getCustomRangeTermCollector(selected)));
		modifiedFacetTermDisplayContext.setLabel("custom-range");
		modifiedFacetTermDisplayContext.setRange("custom-range");
		modifiedFacetTermDisplayContext.setRangeURL(_getCustomRangeURL());
		modifiedFacetTermDisplayContext.setSelected(selected);

		return modifiedFacetTermDisplayContext;
	}

	private ModifiedFacetTermDisplayContext
		_buildDefaultModifiedFacetTermDisplayContext() {

		if (_facet == null) {
			return null;
		}

		FacetConfiguration facetConfiguration = _facet.getFacetConfiguration();

		String label = facetConfiguration.getLabel();

		ModifiedFacetTermDisplayContext modifiedFacetTermDisplayContext =
			new ModifiedFacetTermDisplayContext();

		modifiedFacetTermDisplayContext.setLabel(label);
		modifiedFacetTermDisplayContext.setRange(label);
		modifiedFacetTermDisplayContext.setSelected(true);

		return modifiedFacetTermDisplayContext;
	}

	private ModifiedFacetTermDisplayContext _buildTermDisplayContext(
		String label, String range) {

		ModifiedFacetTermDisplayContext modifiedFacetTermDisplayContext =
			new ModifiedFacetTermDisplayContext();

		modifiedFacetTermDisplayContext.setFrequency(
			getFrequency(getTermCollector(range)));
		modifiedFacetTermDisplayContext.setLabel(label);
		modifiedFacetTermDisplayContext.setRange(range);
		modifiedFacetTermDisplayContext.setRangeURL(_getLabeledRangeURL(label));
		modifiedFacetTermDisplayContext.setSelected(
			_selectedRanges.contains(label));

		return modifiedFacetTermDisplayContext;
	}

	private List<ModifiedFacetTermDisplayContext> _buildTermDisplayContexts() {
		JSONArray rangesJSONArray = _getRangesJSONArray();

		if (rangesJSONArray == null) {
			return null;
		}

		List<ModifiedFacetTermDisplayContext> modifiedFacetTermDisplayContexts =
			new ArrayList<>();

		for (int i = 0; i < rangesJSONArray.length(); i++) {
			JSONObject jsonObject = rangesJSONArray.getJSONObject(i);

			modifiedFacetTermDisplayContexts.add(
				_buildTermDisplayContext(
					jsonObject.getString("label"),
					jsonObject.getString("range")));
		}

		return modifiedFacetTermDisplayContexts;
	}

	private TermCollector _getCustomRangeTermCollector(boolean selected) {
		if (!selected) {
			return null;
		}

		FacetCollector facetCollector = _facet.getFacetCollector();

		return facetCollector.getTermCollector(
			_dateRangeFactory.getRangeString(_from, _to));
	}

	private String _getCustomRangeURL() {
		DateFormat format = _dateFormatFactory.getSimpleDateFormat(
			"yyyy-MM-dd");

		Calendar calendar = _calendarFactory.getCalendar(_timeZone);

		String to = format.format(calendar.getTime());

		calendar.add(Calendar.DATE, -1);

		String from = format.format(calendar.getTime());

		String rangeURL = _http.removeParameter(_currentURL, "modified");

		rangeURL = _http.removeParameter(
			rangeURL, _paginationStartParameterName);

		rangeURL = _http.setParameter(rangeURL, "modifiedFrom", from);

		return _http.setParameter(rangeURL, "modifiedTo", to);
	}

	private String _getLabeledRangeURL(String label) {
		String rangeURL = _http.removeParameter(_currentURL, "modifiedFrom");

		rangeURL = _http.removeParameter(rangeURL, "modifiedTo");

		rangeURL = _http.removeParameter(
			rangeURL, _paginationStartParameterName);

		return _http.setParameter(rangeURL, "modified", label);
	}

	private JSONArray _getRangesJSONArray() {
		if (_facet == null) {
			return null;
		}

		FacetConfiguration facetConfiguration = _facet.getFacetConfiguration();

		JSONObject dataJSONObject = facetConfiguration.getData();

		return dataJSONObject.getJSONArray("ranges");
	}

	private boolean _isCustomRangeSelected() {
		if (Validator.isBlank(_from) && Validator.isBlank(_to)) {
			return false;
		}

		return true;
	}

	private final CalendarFactory _calendarFactory;
	private String _currentURL;
	private final DateFormatFactory _dateFormatFactory;
	private final DateRangeFactory _dateRangeFactory;
	private Facet _facet;
	private String _from;
	private final Http _http;
	private Locale _locale;
	private final ModifiedFacetPortletInstanceConfiguration
		_modifiedFacetPortletInstanceConfiguration;
	private String _paginationStartParameterName;
	private String _parameterName;
	private List<String> _selectedRanges = Collections.emptyList();
	private final ThemeDisplay _themeDisplay;
	private TimeZone _timeZone;
	private String _to;
	private int _totalHits;

}