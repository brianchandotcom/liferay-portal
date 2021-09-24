/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.blueprint.parameter;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.search.experiences.blueprint.parameter.DateSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.DoubleSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.FloatSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.IntegerArraySXPParameter;
import com.liferay.search.experiences.blueprint.parameter.IntegerSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.LongArraySXPParameter;
import com.liferay.search.experiences.blueprint.parameter.LongSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.StringArraySXPParameter;
import com.liferay.search.experiences.blueprint.parameter.StringSXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.contributor.CommerceSXPParameterContributor;
import com.liferay.search.experiences.internal.blueprint.parameter.contributor.ContextSXPParameterContributor;
import com.liferay.search.experiences.internal.blueprint.parameter.contributor.IpstackSXPParameterContributor;
import com.liferay.search.experiences.internal.blueprint.parameter.contributor.OpenWeatherMapSXPParameterContributor;
import com.liferay.search.experiences.internal.blueprint.parameter.contributor.SXPParameterContributor;
import com.liferay.search.experiences.internal.blueprint.parameter.contributor.SystemSXPParameterContributor;
import com.liferay.search.experiences.internal.blueprint.parameter.contributor.TimeSXPParameterContributor;
import com.liferay.search.experiences.internal.blueprint.parameter.contributor.UserSXPParameterContributor;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.SearchParameter;
import com.liferay.segments.SegmentsEntryRetriever;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = SXPParameterDataCreator.class)
public class SXPParameterDataCreator {

	public SXPParameterData create(
		SearchContext searchContext, SXPBlueprint sxpBlueprint) {

		Set<SXPParameter> sxpParameters = new LinkedHashSet<>();

		String keywords = _addKeywordsSXPParameters(
			searchContext, sxpParameters);

		Configuration configuration = sxpBlueprint.getConfiguration();

		Map<String, SearchParameter> parametersMap =
			configuration.getParameters();

		if (!MapUtil.isEmpty(parametersMap)) {
			_addSXPParameter(
				parametersMap.get("page"), searchContext, "page",
				sxpParameters);
			_addSXPParameter(
				parametersMap.get("size"), searchContext, "size",
				sxpParameters);

			_contribute(searchContext, sxpBlueprint, sxpParameters);

			_addSXPParameters(parametersMap, searchContext, sxpParameters);
		}

		return new SXPParameterData(keywords, sxpParameters);
	}

	@Activate
	protected void activate() {
		_sxpParameterContributors = new SXPParameterContributor[] {
			new CommerceSXPParameterContributor(),
			new ContextSXPParameterContributor(
				_groupLocalService, _language, _layoutLocalService),
			new IpstackSXPParameterContributor(_configurationProvider),
			new OpenWeatherMapSXPParameterContributor(_configurationProvider),
			new SystemSXPParameterContributor(),
			new TimeSXPParameterContributor(_language, _userLocalService),
			new UserSXPParameterContributor(
				_language, _roleLocalService, _segmentsEntryRetriever,
				_userGroupGroupRoleLocalService, _userGroupLocalService,
				_userGroupRoleLocalService, _userLocalService)
		};
	}

	private String _addKeywordsSXPParameters(
		SearchContext searchContext, Set<SXPParameter> sxpParameters) {

		String keywords = GetterUtil.getString(searchContext.getKeywords());

		sxpParameters.add(
			new StringSXPParameter("keywords.raw", true, keywords));

		if ((StringUtil.count(keywords, CharPool.QUOTE) % 2) != 0) {
			keywords = StringUtil.replace(
				keywords, CharPool.QUOTE, StringPool.BLANK);
		}

		keywords = keywords.replaceAll("/", "&#8725;");
		keywords = keywords.replaceAll("\"", "\\\\\"");
		keywords = keywords.replaceAll("\\[", "&#91;");
		keywords = keywords.replaceAll("\\\\", "&#92;");
		keywords = keywords.replaceAll("\\]", "&#93;");

		sxpParameters.add(new StringSXPParameter("keywords", true, keywords));

		return keywords;
	}

	private void _addSXPParameter(
		SearchParameter parameter, SearchContext searchContext, String name,
		Set<SXPParameter> sxpParameters) {

		Object object = searchContext.getAttribute(name);

		if (object != null) {
			SXPParameter sxpParameter = _getSXPParameter(
				object, parameter, name, searchContext);

			if (sxpParameter != null) {
				sxpParameters.add(sxpParameter);
			}
		}
	}

	private void _addSXPParameters(
		Map<String, SearchParameter> parametersMap, SearchContext searchContext,
		Set<SXPParameter> sxpParameters) {

		parametersMap.forEach(
			(name, parameter) -> _addSXPParameter(
				parameter, searchContext, name, sxpParameters));
	}

	private void _contribute(
		SearchContext searchContext, SXPBlueprint sxpBlueprint,
		Set<SXPParameter> sxpParameters) {

		if (ArrayUtil.isEmpty(_sxpParameterContributors)) {
			return;
		}

		for (SXPParameterContributor sxpParameterContributor :
				_sxpParameterContributors) {

			sxpParameterContributor.contribute(
				searchContext, sxpBlueprint, sxpParameters);
		}
	}

	private Double _fit(Double value, Double minValue, Double maxValue) {
		if ((minValue != null) && (value < minValue)) {
			return minValue;
		}

		if ((maxValue != null) && (value > maxValue)) {
			return maxValue;
		}

		return value;
	}

	private Float _fit(Float value, Float minValue, Float maxValue) {
		if ((minValue != null) && (value < minValue)) {
			return minValue;
		}

		if ((maxValue != null) && (value > maxValue)) {
			return maxValue;
		}

		return value;
	}

	private Integer _fit(Integer value, Integer minValue, Integer maxValue) {
		if ((minValue != null) && (value < minValue)) {
			return minValue;
		}

		if ((maxValue != null) && (value > maxValue)) {
			return maxValue;
		}

		return value;
	}

	private Long _fit(Long value, Long minValue, Long maxValue) {
		if ((minValue != null) && (value < minValue)) {
			return minValue;
		}

		if ((maxValue != null) && (value > maxValue)) {
			return maxValue;
		}

		return value;
	}

	private SXPParameter _getDateSXPParameter(
		Object object, SearchParameter parameter, String name,
		SearchContext searchContext) {

		String value = _getString(object, null);

		if (value == null) {
			return null;
		}

		LocalDate localDate = LocalDate.parse(
			value, DateTimeFormatter.ofPattern(parameter.getDateFormat()));

		TimeZone timeZone = searchContext.getTimeZone();

		Calendar calendar = GregorianCalendar.from(
			localDate.atStartOfDay(timeZone.toZoneId()));

		Date date = calendar.getTime();

		if (date == null) {
			return null;
		}

		return new DateSXPParameter(name, true, date);
	}

	private Double _getDouble(Object object, Double defaultValue) {
		if (object != null) {
			return GetterUtil.getDouble(object);
		}

		if (defaultValue != null) {
			return defaultValue;
		}

		return null;
	}

	private SXPParameter _getDoubleSXPParameter(
		Object object, SearchParameter parameter, String name) {

		Double value = _getDouble(object, parameter.getDefaultValueDouble());

		if (value == null) {
			return null;
		}

		return new DoubleSXPParameter(
			name, true,
			_fit(
				value, parameter.getMinValueDouble(),
				parameter.getMaxValueDouble()));
	}

	private Float _getFloat(Object object, Float defaultValue) {
		if (object != null) {
			return GetterUtil.getFloat(object);
		}

		if (defaultValue != null) {
			return defaultValue;
		}

		return null;
	}

	private SXPParameter _getFloatSXPParameter(
		Object object, SearchParameter parameter, String name) {

		Float value = _getFloat(object, parameter.getDefaultValueFloat());

		if (value == null) {
			return null;
		}

		return new FloatSXPParameter(
			name, true,
			_fit(
				value, parameter.getMinValueFloat(),
				parameter.getMaxValueFloat()));
	}

	private Integer _getInteger(Object object, Integer defaultValue) {
		if (object != null) {
			return GetterUtil.getInteger(object);
		}

		if (defaultValue != null) {
			return defaultValue;
		}

		return null;
	}

	private SXPParameter _getIntegerArraySXPParameter(
		Object object, SearchParameter parameter, String name) {

		Integer[] values = _getIntegerValues(
			object, parameter.getDefaultValuesIntegerArray());

		if (ArrayUtil.isEmpty(values)) {
			return null;
		}

		return new IntegerArraySXPParameter(name, true, values);
	}

	private SXPParameter _getIntegerSXPParameter(
		Object object, SearchParameter parameter, String name) {

		Integer value = _getInteger(object, parameter.getDefaultValueInteger());

		if (value == null) {
			return null;
		}

		return new IntegerSXPParameter(
			name, true,
			_fit(
				value, parameter.getMinValueInteger(),
				parameter.getMaxValueInteger()));
	}

	private Integer[] _getIntegerValues(
		Object object, Integer[] defaultValues) {

		if (object != null) {
			return ArrayUtil.toArray(GetterUtil.getIntegerValues(object));
		}

		if (defaultValues != null) {
			return defaultValues;
		}

		return null;
	}

	private Long _getLong(Object object, Long defaultValue) {
		if (object != null) {
			return GetterUtil.getLong(object);
		}

		if (defaultValue != null) {
			return defaultValue;
		}

		return null;
	}

	private SXPParameter _getLongArraySXPParameter(
		Object object, SearchParameter parameter, String name) {

		Long[] values = _getLongValues(
			object, parameter.getDefaultValuesLongArray());

		if (ArrayUtil.isEmpty(values)) {
			return null;
		}

		return new LongArraySXPParameter(name, true, values);
	}

	private SXPParameter _getLongSXPParameter(
		Object object, SearchParameter parameter, String name) {

		Long value = _getLong(object, parameter.getDefaultValueLong());

		if (value == null) {
			return null;
		}

		return new LongSXPParameter(
			name, true,
			_fit(
				value, parameter.getMinValueLong(),
				parameter.getMaxValueLong()));
	}

	private Long[] _getLongValues(Object object, Long[] defaultValues) {
		if (object != null) {
			return ArrayUtil.toArray(GetterUtil.getLongValues(object));
		}

		if (defaultValues != null) {
			return defaultValues;
		}

		return null;
	}

	private String _getString(Object object, String defaultValue) {
		if (object != null) {
			return GetterUtil.getString(object);
		}

		if (defaultValue != null) {
			return defaultValue;
		}

		return null;
	}

	private SXPParameter _getStringArraySXPParameter(
		Object object, SearchParameter parameter, String name) {

		String[] values = _getStringValues(
			object, parameter.getDefaultValuesStringArray());

		if (ArrayUtil.isEmpty(values)) {
			return null;
		}

		return new StringArraySXPParameter(name, true, values);
	}

	private SXPParameter _getStringSXPParameter(
		Object object, SearchParameter parameter, String name) {

		String value = _getString(object, parameter.getDefaultValueString());

		if (value == null) {
			return null;
		}

		return new StringSXPParameter(name, true, value);
	}

	private String[] _getStringValues(Object object, String[] defaultValues) {
		if (object != null) {
			return GetterUtil.getStringValues(object);
		}

		if (defaultValues != null) {
			return defaultValues;
		}

		return null;
	}

	private SXPParameter _getSXPParameter(
		Object object, SearchParameter parameter, String name,
		SearchContext searchContext) {

		SearchParameter.ParameterType parameterType =
			parameter.getParameterType();

		if (parameterType.equals(SearchParameter.ParameterType.DATE)) {
			return _getDateSXPParameter(object, parameter, name, searchContext);
		}
		else if (parameterType.equals(SearchParameter.ParameterType.DOUBLE)) {
			return _getDoubleSXPParameter(object, parameter, name);
		}
		else if (parameterType.equals(SearchParameter.ParameterType.FLOAT)) {
			return _getFloatSXPParameter(object, parameter, name);
		}
		else if (parameterType.equals(SearchParameter.ParameterType.INTEGER)) {
			return _getIntegerSXPParameter(object, parameter, name);
		}
		else if (parameterType.equals(
					SearchParameter.ParameterType.INTEGER_ARRAY)) {

			return _getIntegerArraySXPParameter(object, parameter, name);
		}
		else if (parameterType.equals(SearchParameter.ParameterType.LONG)) {
			return _getLongSXPParameter(object, parameter, name);
		}
		else if (parameterType.equals(
					SearchParameter.ParameterType.LONG_ARRAY)) {

			return _getLongArraySXPParameter(object, parameter, name);
		}
		else if (parameterType.equals(SearchParameter.ParameterType.STRING)) {
			return _getStringSXPParameter(object, parameter, name);
		}
		else if (parameterType.equals(
					SearchParameter.ParameterType.STRING_ARRAY)) {

			return _getStringArraySXPParameter(object, parameter, name);
		}
		else if (parameterType.equals(
					SearchParameter.ParameterType.TIME_RANGE)) {

			return _getTimeRangeSXPParameter(object, name);
		}

		throw new IllegalArgumentException();
	}

	private SXPParameter _getTimeRangeSXPParameter(Object object, String name) {
		String value = _getString(object, null);

		if (value == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();

		if (value.equals("last-day")) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		else if (value.equals("last-hour")) {
			calendar.add(Calendar.HOUR_OF_DAY, -1);
		}
		else if (value.equals("last-month")) {
			calendar.add(Calendar.MONTH, -1);
		}
		else if (value.equals("last-week")) {
			calendar.add(Calendar.WEEK_OF_MONTH, -1);
		}
		else if (value.equals("last-year")) {
			calendar.add(Calendar.YEAR, -1);
		}

		return new DateSXPParameter(name, true, calendar.getTime());
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SegmentsEntryRetriever _segmentsEntryRetriever;

	private SXPParameterContributor[] _sxpParameterContributors;

	@Reference
	private UserGroupGroupRoleLocalService _userGroupGroupRoleLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}