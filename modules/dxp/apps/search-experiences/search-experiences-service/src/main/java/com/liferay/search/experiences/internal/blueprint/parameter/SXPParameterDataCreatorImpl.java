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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.blueprint.parameter.DateSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.DoubleSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.FloatSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.IntegerArraySXPParameter;
import com.liferay.search.experiences.blueprint.parameter.IntegerSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.LongArraySXPParameter;
import com.liferay.search.experiences.blueprint.parameter.LongSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterDataCreator;
import com.liferay.search.experiences.blueprint.parameter.StringArraySXPParameter;
import com.liferay.search.experiences.blueprint.parameter.StringSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.contributor.SXPParameterContributor;
import com.liferay.search.experiences.internal.blueprint.data.provider.GeoLocationDataProvider;
import com.liferay.search.experiences.internal.blueprint.data.provider.WeatherDataProvider;
import com.liferay.search.experiences.internal.blueprint.parameter.contributor.CommerceSXPParameterContributor;
import com.liferay.search.experiences.internal.blueprint.parameter.contributor.ContextSXPParameterContributor;
import com.liferay.search.experiences.internal.blueprint.parameter.contributor.IPStackParameterContributor;
import com.liferay.search.experiences.internal.blueprint.parameter.contributor.OpenWeatherMapSXPParameterContributor;
import com.liferay.search.experiences.internal.blueprint.parameter.contributor.SystemSXPParameterContributor;
import com.liferay.search.experiences.internal.blueprint.parameter.contributor.TimeSXPParameterContributor;
import com.liferay.search.experiences.internal.blueprint.parameter.contributor.UserSXPParameterContributor;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.segments.SegmentsEntryRetriever;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = SXPParameterDataCreator.class)
public class SXPParameterDataCreatorImpl implements SXPParameterDataCreator {

	@Override
	public SXPParameterData create(
		SearchContext searchContext, SXPBlueprint sxpBlueprint) {

		Set<SXPParameter> sxpParameters = new LinkedHashSet<>();

		String keywords = _addKeywordsSXPParameters(
			searchContext, sxpParameters);

		// TODO Replace with real JSON

		JSONObject jsonObject = JSONUtil.put("test", "test");

		_addIntegerSXPParameter(
			jsonObject.getJSONObject("page"), "page", searchContext,
			sxpParameters);
		_addIntegerSXPParameter(
			jsonObject.getJSONObject("size"), "size", searchContext,
			sxpParameters);

		_addSortSXPParameters(searchContext, sxpBlueprint, sxpParameters);

		_contribute(searchContext, sxpBlueprint, sxpParameters);

		_addCustomSXPParameters(
			jsonObject.getJSONArray("custom"), searchContext, sxpParameters);

		return new SXPParameterDataImpl(keywords, sxpParameters);
	}

	@Activate
	protected void activate() {
		_sxpParameterContributors = new SXPParameterContributor[] {
			new CommerceSXPParameterContributor(),
			new ContextSXPParameterContributor(
				_groupLocalService, _language, _layoutLocalService),
			new IPStackParameterContributor(_geoLocationDataProvider),
			new OpenWeatherMapSXPParameterContributor(
				_geoLocationDataProvider, _weatherDataProvider),
			new SystemSXPParameterContributor(),
			new TimeSXPParameterContributor(_language, _userLocalService),
			new UserSXPParameterContributor(
				_language, _roleLocalService, _segmentsEntryRetriever,
				_userGroupGroupRoleLocalService, _userGroupLocalService,
				_userGroupRoleLocalService, _userLocalService)
		};
	}

	private void _addCustomSXPParameters(
		JSONArray jsonArray, SearchContext searchContext,
		Set<SXPParameter> sxpParameters) {

		if ((jsonArray == null) || (jsonArray.length() == 0)) {
			return;
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String name = jsonObject.getString("parameter_name");

			String type = jsonObject.getString("type");

			if (type.equals("date")) {
				_addDateSXPParameter(
					jsonObject, name, searchContext, sxpParameters);
			}
			else if (type.equals("double")) {
				_addDoubleSXPParameter(
					jsonObject, name, searchContext, sxpParameters);
			}
			else if (type.equals("float")) {
				_addFloatSXPParameter(
					jsonObject, name, searchContext, sxpParameters);
			}
			else if (type.equals("integer")) {
				_addIntegerSXPParameter(
					jsonObject, name, searchContext, sxpParameters);
			}
			else if (type.equals("integer_array")) {
				_addIntegerArraySXPParameter(
					jsonObject, name, searchContext, sxpParameters);
			}
			else if (type.equals("long")) {
				_addLongSXPParameter(
					jsonObject, name, searchContext, sxpParameters);
			}
			else if (type.equals("long_array")) {
				_addLongArraySXPParameter(
					jsonObject, name, searchContext, sxpParameters);
			}
			else if (type.equals("string")) {
				_addStringSXPParameter(
					jsonObject, name, searchContext, sxpParameters);
			}
			else if (type.equals("string_array")) {
				_addStringArraySXPParameter(
					jsonObject, name, searchContext, sxpParameters);
			}
			else if (type.equals("time_range")) {
				_addTimeRangeSXPParameter(name, searchContext, sxpParameters);
			}
		}
	}

	private void _addDateSXPParameter(
		JSONObject jsonObject, String name, SearchContext searchContext,
		Set<SXPParameter> sxpParameters) {

		String dateString = _getString(name, searchContext);

		if (Validator.isBlank(dateString)) {
			return;
		}

		LocalDate localDate = LocalDate.parse(
			dateString,
			DateTimeFormatter.ofPattern(jsonObject.getString("date_format")));

		TimeZone timeZone = searchContext.getTimeZone();

		Calendar calendar = GregorianCalendar.from(
			localDate.atStartOfDay(timeZone.toZoneId()));

		Date date = calendar.getTime();

		if (date == null) {
			return;
		}

		sxpParameters.add(new DateSXPParameter(name, true, date));
	}

	private void _addDoubleSXPParameter(
		JSONObject jsonObject, String name, SearchContext searchContext,
		Set<SXPParameter> sxpParameters) {

		Double value = _getDouble(name, searchContext);

		if ((value == null) && jsonObject.has("default")) {
			value = GetterUtil.getDouble(jsonObject.getString("default"));
		}

		if (value == null) {
			return;
		}

		double minValue = GetterUtil.getDouble(
			jsonObject.getString("min_value"), Double.MIN_VALUE);

		if (Double.compare(value, minValue) < 0) {
			value = minValue;
		}
		else {
			double maxValue = GetterUtil.getDouble(
				jsonObject.getString("max_value"), Double.MAX_VALUE);

			if (Double.compare(value, maxValue) > 0) {
				value = maxValue;
			}
		}

		sxpParameters.add(new DoubleSXPParameter(name, true, value));
	}

	private void _addFloatSXPParameter(
		JSONObject jsonObject, String name, SearchContext searchContext,
		Set<SXPParameter> sxpParameters) {

		Float value = _getFloat(name, searchContext);

		if ((value == null) && jsonObject.has("default")) {
			value = GetterUtil.getFloat(jsonObject.getString("default"));
		}

		if (value == null) {
			return;
		}

		float minValue = GetterUtil.getFloat(
			jsonObject.getString("min_value"), Float.MIN_VALUE);

		if (Float.compare(value, minValue) < 0) {
			value = minValue;
		}
		else {
			float maxValue = GetterUtil.getFloat(
				jsonObject.getString("max_value"), Float.MAX_VALUE);

			if (Float.compare(value, maxValue) > 0) {
				value = maxValue;
			}
		}

		sxpParameters.add(new FloatSXPParameter(name, true, value));
	}

	private void _addIntegerArraySXPParameter(
		JSONObject jsonObject, String name, SearchContext searchContext,
		Set<SXPParameter> sxpParameters) {

		Integer[] value = _getIntegerArray(name, searchContext);

		if ((value == null) && jsonObject.has("default")) {
			Stream<String> stream = Arrays.stream(
				JSONUtil.toStringArray(jsonObject.getJSONArray("default")));

			value = stream.map(
				GetterUtil::getInteger
			).toArray(
				Integer[]::new
			);
		}

		if (ArrayUtil.isEmpty(value)) {
			return;
		}

		sxpParameters.add(new IntegerArraySXPParameter(name, true, value));
	}

	private void _addIntegerSXPParameter(
		JSONObject jsonObject, String name, SearchContext searchContext,
		Set<SXPParameter> sxpParameters) {

		Integer value = _getInteger(name, searchContext);

		if ((value == null) && jsonObject.has("default")) {
			value = GetterUtil.getInteger(jsonObject.getString("default"));
		}

		if (value == null) {
			return;
		}

		int minValue = GetterUtil.getInteger(
			jsonObject.getString("min_value"), Integer.MIN_VALUE);

		if (Integer.compare(value, minValue) < 0) {
			value = minValue;
		}
		else {
			int maxValue = GetterUtil.getInteger(
				jsonObject.getString("max_value"), Integer.MAX_VALUE);

			if (Integer.compare(value, maxValue) > 0) {
				value = maxValue;
			}
		}

		sxpParameters.add(new IntegerSXPParameter(name, true, value));
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

	private void _addLongArraySXPParameter(
		JSONObject jsonObject, String name, SearchContext searchContext,
		Set<SXPParameter> sxpParameters) {

		Long[] value = _getLongArray(name, searchContext);

		if ((value == null) && jsonObject.has("default")) {
			Stream<String> stream = Arrays.stream(
				JSONUtil.toStringArray(jsonObject.getJSONArray("default")));

			value = stream.map(
				GetterUtil::getLong
			).toArray(
				Long[]::new
			);
		}

		if (ArrayUtil.isEmpty(value)) {
			return;
		}

		sxpParameters.add(new LongArraySXPParameter(name, true, value));
	}

	private void _addLongSXPParameter(
		JSONObject jsonObject, String name, SearchContext searchContext,
		Set<SXPParameter> sxpParameters) {

		Long value = _getLong(name, searchContext);

		if ((value == null) && jsonObject.has("default")) {
			value = GetterUtil.getLong(jsonObject.getString("default"));
		}

		if (value == null) {
			return;
		}

		long minValue = GetterUtil.getLong(
			jsonObject.getString("min_value"), Long.MIN_VALUE);

		if (Long.compare(value, minValue) < 0) {
			value = minValue;
		}
		else {
			long maxValue = GetterUtil.getLong(
				jsonObject.getString("max_value"), Long.MAX_VALUE);

			if (Long.compare(value, maxValue) > 0) {
				value = maxValue;
			}
		}

		sxpParameters.add(new LongSXPParameter(name, true, value));
	}

	private void _addSortSXPParameters(
		SearchContext searchContext, SXPBlueprint sxpBlueprint,
		Set<SXPParameter> sxpParameters) {

		// TODO Replace with real JSON

		JSONObject jsonObject = null;

		try {
			jsonObject = _jsonFactory.createJSONObject(
				sxpBlueprint.getConfigurationsJSON());
		}
		catch (JSONException jsonException) {
			return;
		}

		for (String key : jsonObject.keySet()) {
			String value = _getString(key, searchContext);

			if (Validator.isBlank(value) && jsonObject.has("default")) {
				value = GetterUtil.getString(jsonObject.getString("default"));
			}

			if (Validator.isBlank(value)) {
				return;
			}

			sxpParameters.add(new StringSXPParameter(key, true, value));
		}
	}

	private void _addStringArraySXPParameter(
		JSONObject jsonObject, String name, SearchContext searchContext,
		Set<SXPParameter> sxpParameters) {

		String[] value = _getStringArray(name, searchContext);

		if ((value == null) && jsonObject.has("default")) {
			value = JSONUtil.toStringArray(jsonObject.getJSONArray("default"));
		}

		if (ArrayUtil.isEmpty(value)) {
			return;
		}

		sxpParameters.add(new StringArraySXPParameter(name, true, value));
	}

	private void _addStringSXPParameter(
		JSONObject jsonObject, String name, SearchContext searchContext,
		Set<SXPParameter> sxpParameters) {

		String value = _getString(name, searchContext);

		if (Validator.isBlank(value) && jsonObject.has("default")) {
			value = GetterUtil.getString(jsonObject.getString("default"));
		}

		if (Validator.isBlank(value)) {
			return;
		}

		sxpParameters.add(new StringSXPParameter(name, true, value));
	}

	private void _addTimeRangeSXPParameter(
		String name, SearchContext searchContext,
		Set<SXPParameter> sxpParameters) {

		String value = _getString(name, searchContext);

		if (Validator.isBlank(value)) {
			return;
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

		sxpParameters.add(new DateSXPParameter(name, true, calendar.getTime()));
	}

	private void _contribute(
		SearchContext searchContext, SXPBlueprint sxpBlueprint,
		Set<SXPParameter> sxpParameters) {

		for (SXPParameterContributor sxpParameterContributor :
				_sxpParameterContributors) {

			sxpParameterContributor.contribute(
				searchContext, sxpBlueprint, sxpParameters);
		}
	}

	private Double _getDouble(String name, SearchContext searchContext) {
		Object value = searchContext.getAttribute(name);

		if (Objects.isNull(value)) {
			return null;
		}

		return GetterUtil.getDouble(value);
	}

	private Float _getFloat(String name, SearchContext searchContext) {
		Object value = searchContext.getAttribute(name);

		if (Objects.isNull(value)) {
			return null;
		}

		return GetterUtil.getFloat(value);
	}

	private Integer _getInteger(String name, SearchContext searchContext) {
		Object value = searchContext.getAttribute(name);

		if (Objects.isNull(value)) {
			return null;
		}

		return GetterUtil.getInteger(value);
	}

	private Integer[] _getIntegerArray(
		String name, SearchContext searchContext) {

		Object value = searchContext.getAttribute(name);

		if (value == null) {
			return null;
		}

		if (value instanceof Integer[]) {
			Integer[] array = (Integer[])value;

			if (ArrayUtil.isEmpty(array)) {
				return null;
			}

			return array;
		}
		else if (value instanceof int[]) {
			int[] array = (int[])value;

			if (ArrayUtil.isEmpty(array)) {
				return null;
			}

			return ArrayUtil.toArray(array);
		}

		return null;
	}

	private Long _getLong(String name, SearchContext searchContext) {
		Object value = searchContext.getAttribute(name);

		if (Objects.isNull(value)) {
			return null;
		}

		return GetterUtil.getLong(value);
	}

	private Long[] _getLongArray(String name, SearchContext searchContext) {
		Object value = searchContext.getAttribute(name);

		if (value == null) {
			return null;
		}

		if (value instanceof Long[]) {
			Long[] array = (Long[])value;

			if (ArrayUtil.isEmpty(array)) {
				return null;
			}

			return array;
		}
		else if (value instanceof long[]) {
			long[] array = (long[])value;

			if (ArrayUtil.isEmpty(array)) {
				return null;
			}

			return ArrayUtil.toArray(array);
		}

		return null;
	}

	private String _getString(String name, SearchContext searchContext) {
		return GetterUtil.getString(searchContext.getAttribute(name));
	}

	private String[] _getStringArray(String name, SearchContext searchContext) {
		Object value = searchContext.getAttribute(name);

		if ((value == null) || !(value instanceof String[])) {
			return null;
		}

		String[] array = (String[])value;

		if (ArrayUtil.isEmpty(array)) {
			return null;
		}

		return array;
	}

	// TODO (NOTE TO BE REMOVED): wiring this to IPStack
	//  as the parameter contributor in activate() is wired
	// to IPStack config

	@Reference(target = "(name=ipstack)", unbind = "-")
	private GeoLocationDataProvider _geoLocationDataProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

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

	// TODO (NOTE TO BE REMOVED): wiring this to OpenWeatherMap
	//  as the parameter contributor in activate() is wired
	// to OpenWeatherMap config

	@Reference(target = "(name=openweathermap)", unbind = "-")
	private WeatherDataProvider _weatherDataProvider;

}