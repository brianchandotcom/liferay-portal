/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.date.facet.display.context.builder;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.date.facet.configuration.DateFacetPortletInstanceConfiguration;
import com.liferay.portal.search.web.internal.date.facet.display.context.DateFacetCalendarDisplayContext;
import com.liferay.portal.search.web.internal.date.facet.display.context.DateFacetDisplayContext;
import com.liferay.portal.search.web.internal.facet.display.context.BucketDisplayContext;
import com.liferay.portal.search.web.internal.util.comparator.BucketDisplayContextComparatorFactoryUtil;

import java.io.Serializable;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.portlet.RenderRequest;

/**
 * @author Petteri Karttunen
 */
public class DateFacetDisplayContextBuilder implements Serializable {

	public DateFacetDisplayContextBuilder(RenderRequest renderRequest)
		throws ConfigurationException {

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_dateFacetPortletInstanceConfiguration =
			ConfigurationProviderUtil.getPortletInstanceConfiguration(
				DateFacetPortletInstanceConfiguration.class, _themeDisplay);
	}

	public DateFacetDisplayContext build() {
		DateFacetDisplayContext dateFacetDisplayContext =
			new DateFacetDisplayContext();

		dateFacetDisplayContext.setBucketDisplayContexts(
			_buildBucketDisplayContexts());
		dateFacetDisplayContext.setCalendarDisplayContext(
			_buildCalendarDisplayContext());
		dateFacetDisplayContext.setCustomRangeBucketDisplayContext(
			_buildCustomRangeBucketDisplayContext());
		dateFacetDisplayContext.setDefaultBucketDisplayContext(
			_buildDefaultBucketDisplayContext());
		dateFacetDisplayContext.setDisplayCaption(getDisplayCaption());
		dateFacetDisplayContext.setDisplayStyleGroupId(
			getDisplayStyleGroupId());
		dateFacetDisplayContext.setDateFacetPortletInstanceConfiguration(
			_dateFacetPortletInstanceConfiguration);
		dateFacetDisplayContext.setNothingSelected(isNothingSelected());
		dateFacetDisplayContext.setPaginationStartParameterName(
			_paginationStartParameterName);
		dateFacetDisplayContext.setParameterName(_parameterName);
		dateFacetDisplayContext.setRenderNothing(isRenderNothing());

		return dateFacetDisplayContext;
	}

	public void setCurrentURL(String currentURL) {
		_currentURL = currentURL;
	}

	public void setCustomDisplayCaption(String customDisplayCaption) {
		_customDisplayCaption = customDisplayCaption;
	}

	public void setFacet(Facet facet) {
		_facet = facet;
	}

	public void setFieldToAggregate(String fieldToAggregate) {
		_fieldToAggregate = fieldToAggregate;
	}

	public void setFrequenciesVisible(boolean frequenciesVisible) {
		_frequenciesVisible = frequenciesVisible;
	}

	public void setFrequencyThreshold(int frequencyThreshold) {
		_frequencyThreshold = frequencyThreshold;
	}

	public void setFromParameterValue(String from) {
		_from = from;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setOrder(String order) {
		_order = order;
	}

	public void setPaginationStartParameterName(
		String paginationStartParameterName) {

		_paginationStartParameterName = paginationStartParameterName;
	}

	public void setParameterName(String parameterName) {
		_parameterName = parameterName;
	}

	public void setParameterValues(String... parameterValues) {
		_selectedRanges = ListUtil.fromArray(parameterValues);
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

	protected String getDisplayCaption() {
		String customDisplayCaption = StringUtil.trim(_customDisplayCaption);

		if (Validator.isNotNull(customDisplayCaption)) {
			return customDisplayCaption;
		}

		String fieldToAggregate = StringUtil.trim(_fieldToAggregate);

		if (Validator.isNotNull(fieldToAggregate)) {
			return fieldToAggregate;
		}

		return "date";
	}

	protected long getDisplayStyleGroupId() {
		long displayStyleGroupId =
			_dateFacetPortletInstanceConfiguration.displayStyleGroupId();

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

	protected TermCollector getTermCollector(String key) {
		if (_facet == null) {
			return null;
		}

		FacetCollector facetCollector = _facet.getFacetCollector();

		if (facetCollector == null) {
			return null;
		}

		return facetCollector.getTermCollector(key);
	}

	protected boolean isNothingSelected() {
		if (!_selectedRanges.isEmpty() ||
			(!Validator.isBlank(_from) && !Validator.isBlank(_to))) {

			return false;
		}

		return true;
	}

	protected boolean isRenderNothing() {
		if (!Validator.isBlank(_fieldToAggregate) && (_totalHits > 0)) {
			return false;
		}

		return isNothingSelected();
	}

	private BucketDisplayContext _buildBucketDisplayContext(String label) {
		BucketDisplayContext bucketDisplayContext = new BucketDisplayContext();

		bucketDisplayContext.setBucketText(label);
		bucketDisplayContext.setFilterValue(_getLabeledRangeURL(label));
		bucketDisplayContext.setFrequency(
			getFrequency(getTermCollector(label)));
		bucketDisplayContext.setFrequencyVisible(_frequenciesVisible);
		bucketDisplayContext.setSelected(_selectedRanges.contains(label));

		return bucketDisplayContext;
	}

	private List<BucketDisplayContext> _buildBucketDisplayContexts() {
		JSONArray rangesJSONArray = _getRangesJSONArray();

		if (rangesJSONArray == null) {
			return null;
		}

		List<BucketDisplayContext> bucketDisplayContexts = new ArrayList<>();

		for (int i = 0; i < rangesJSONArray.length(); i++) {
			JSONObject jsonObject = rangesJSONArray.getJSONObject(i);

			String label = jsonObject.getString("label");

			if (label.equals("custom-range") ||
				((_frequencyThreshold > 0) &&
				 (_frequencyThreshold > getFrequency(
					 getTermCollector(label))))) {

				continue;
			}

			bucketDisplayContexts.add(_buildBucketDisplayContext(label));
		}

		if (!_order.equals("rangesConfiguration")) {
			bucketDisplayContexts.sort(
				BucketDisplayContextComparatorFactoryUtil.
					getBucketDisplayContextComparator(_order));
		}

		return bucketDisplayContexts;
	}

	private DateFacetCalendarDisplayContext _buildCalendarDisplayContext() {
		DateFacetCalendarDisplayContextBuilder
			dateFacetCalendarDisplayContextBuilder =
				new DateFacetCalendarDisplayContextBuilder();

		for (String selectedRange : _selectedRanges) {
			if (selectedRange.startsWith(StringPool.OPEN_CURLY_BRACE)) {
				dateFacetCalendarDisplayContextBuilder.setRangeString(
					selectedRange);
			}
		}

		dateFacetCalendarDisplayContextBuilder.setFrom(_from);
		dateFacetCalendarDisplayContextBuilder.setLocale(_locale);
		dateFacetCalendarDisplayContextBuilder.setTimeZone(_timeZone);
		dateFacetCalendarDisplayContextBuilder.setTo(_to);

		return dateFacetCalendarDisplayContextBuilder.build();
	}

	private BucketDisplayContext _buildCustomRangeBucketDisplayContext() {
		boolean selected = _isCustomRangeSelected();

		BucketDisplayContext bucketDisplayContext = new BucketDisplayContext();

		bucketDisplayContext.setBucketText("custom-range");
		bucketDisplayContext.setFilterValue(_getCustomRangeURL());
		bucketDisplayContext.setFrequency(
			getFrequency(_getCustomRangeTermCollector(selected)));
		bucketDisplayContext.setFrequencyVisible(_frequenciesVisible);
		bucketDisplayContext.setSelected(selected);

		return bucketDisplayContext;
	}

	private BucketDisplayContext _buildDefaultBucketDisplayContext() {
		if (_facet == null) {
			return null;
		}

		FacetConfiguration facetConfiguration = _facet.getFacetConfiguration();

		String label = facetConfiguration.getLabel();

		BucketDisplayContext bucketDisplayContext = new BucketDisplayContext();

		bucketDisplayContext.setBucketText(label);
		bucketDisplayContext.setSelected(true);

		return bucketDisplayContext;
	}

	private TermCollector _getCustomRangeTermCollector(boolean selected) {
		if (!selected) {
			return null;
		}

		FacetCollector facetCollector = _facet.getFacetCollector();

		return facetCollector.getTermCollector("custom-range");
	}

	private String _getCustomRangeURL() {
		DateFormat format = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd");

		Calendar calendar = CalendarFactoryUtil.getCalendar(_timeZone);

		String to = format.format(calendar.getTime());

		calendar.add(Calendar.DATE, -1);

		String from = format.format(calendar.getTime());

		String rangeURL = HttpComponentsUtil.removeParameter(
			_currentURL, _parameterName);

		rangeURL = HttpComponentsUtil.removeParameter(
			rangeURL, _paginationStartParameterName);

		rangeURL = HttpComponentsUtil.setParameter(
			rangeURL, _parameterName + "From", from);

		return HttpComponentsUtil.setParameter(
			rangeURL, _parameterName + "To", to);
	}

	private String _getLabeledRangeURL(String label) {
		String rangeURL = HttpComponentsUtil.removeParameter(
			_currentURL, _parameterName + "From");

		rangeURL = HttpComponentsUtil.removeParameter(
			rangeURL, _parameterName + "To");

		rangeURL = HttpComponentsUtil.removeParameter(
			rangeURL, _paginationStartParameterName);

		return HttpComponentsUtil.setParameter(rangeURL, _parameterName, label);
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

	private String _currentURL;
	private String _customDisplayCaption;
	private final DateFacetPortletInstanceConfiguration
		_dateFacetPortletInstanceConfiguration;
	private Facet _facet;
	private String _fieldToAggregate;
	private boolean _frequenciesVisible;
	private int _frequencyThreshold;
	private String _from;
	private Locale _locale;
	private String _order;
	private String _paginationStartParameterName;
	private String _parameterName;
	private List<String> _selectedRanges = Collections.emptyList();
	private final ThemeDisplay _themeDisplay;
	private TimeZone _timeZone;
	private String _to;
	private int _totalHits;

}