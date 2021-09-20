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

package com.liferay.search.experiences.internal.blueprint.util;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchRequestBuilder;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * @author Petteri Karttunen
 */
public class SearchContextUtil {

	public static Boolean getBooleanAttribute(
		String attributeName, SearchRequestBuilder searchRequestBuilder) {

		Object object = _getAttributeObject(
			attributeName, searchRequestBuilder);

		if (Objects.isNull(object)) {
			return null;
		}

		return searchRequestBuilder.withSearchContextGet(
			searchContext -> GetterUtil.getBoolean(object));
	}

	public static long getCompanyId(SearchRequestBuilder searchRequestBuilder) {
		return searchRequestBuilder.withSearchContextGet(
			searchContext -> searchContext.getCompanyId());
	}

	public static Double getDoubleAttribute(
		String attributeName, SearchRequestBuilder searchRequestBuilder) {

		Object object = _getAttributeObject(
			attributeName, searchRequestBuilder);

		if (Objects.isNull(object)) {
			return null;
		}

		return searchRequestBuilder.withSearchContextGet(
			searchContext -> GetterUtil.getDouble(object));
	}

	public static Float getFloatAttribute(
		String attributeName, SearchRequestBuilder searchRequestBuilder) {

		Object object = _getAttributeObject(
			attributeName, searchRequestBuilder);

		if (Objects.isNull(object)) {
			return null;
		}

		return searchRequestBuilder.withSearchContextGet(
			searchContext -> GetterUtil.getFloat(object));
	}

	public static Integer[] getIntegerArrayAttribute(
		String attributeName, SearchRequestBuilder searchRequestBuilder) {

		Object object = _getAttributeObject(
			attributeName, searchRequestBuilder);

		if (Objects.isNull(object)) {
			return null;
		}

		if (object instanceof int[]) {
			int[] arr = (int[])object;

			if (arr.length > 0) {
				IntStream intStream = Arrays.stream(arr);

				return intStream.boxed(
				).toArray(
					Integer[]::new
				);
			}
		}
		else if (object instanceof Integer[]) {
			return (Integer[])object;
		}

		return null;
	}

	public static Integer getIntegerAttribute(
		String attributeName, SearchRequestBuilder searchRequestBuilder) {

		Object object = _getAttributeObject(
			attributeName, searchRequestBuilder);

		if (Objects.isNull(object)) {
			return null;
		}

		return searchRequestBuilder.withSearchContextGet(
			searchContext -> GetterUtil.getInteger(object));
	}

	public static String getKeywords(
		SearchRequestBuilder searchRequestBuilder) {

		return searchRequestBuilder.withSearchContextGet(
			searchContext -> searchContext.getKeywords());
	}

	public static Layout getLayout(SearchRequestBuilder searchRequestBuilder) {
		return searchRequestBuilder.withSearchContextGet(
			searchContext -> searchContext.getLayout());
	}

	public static Locale getLocale(SearchRequestBuilder searchRequestBuilder) {
		return searchRequestBuilder.withSearchContextGet(
			searchContext -> searchContext.getLocale());
	}

	public static Long[] getLongArrayAttribute(
		String attributeName, SearchRequestBuilder searchRequestBuilder) {

		Object object = _getAttributeObject(
			attributeName, searchRequestBuilder);

		if (Objects.isNull(object)) {
			return null;
		}

		if (object instanceof long[]) {
			long[] arr = (long[])object;

			if (arr.length > 0) {
				LongStream longStream = Arrays.stream(arr);

				return longStream.boxed(
				).toArray(
					Long[]::new
				);
			}
		}
		else if (object instanceof Long[]) {
			return (Long[])object;
		}

		return null;
	}

	public static Long getLongAttribute(
		String attributeName, SearchRequestBuilder searchRequestBuilder) {

		Object object = _getAttributeObject(
			attributeName, searchRequestBuilder);

		if (Objects.isNull(object)) {
			return null;
		}

		return searchRequestBuilder.withSearchContextGet(
			searchContext -> GetterUtil.getLong(object));
	}

	public static String[] getStringArrayAttribute(
		String attributeName, SearchRequestBuilder searchRequestBuilder) {

		Object object = _getAttributeObject(
			attributeName, searchRequestBuilder);

		if (Objects.isNull(object)) {
			return null;
		}

		if (object instanceof String[]) {
			String[] arr = (String[])object;

			if (arr.length > 0) {
				return arr;
			}
		}

		return null;
	}

	public static String getStringAttribute(
		String attributeName, SearchRequestBuilder searchRequestBuilder) {

		return searchRequestBuilder.withSearchContextGet(
			searchContext -> {
				String attribute = GetterUtil.getString(
					searchContext.getAttribute(attributeName));

				if (Validator.isBlank(attribute)) {
					return null;
				}

				attribute = StringUtil.trim(attribute);

				if (Validator.isBlank(attribute)) {
					return null;
				}

				return attribute;
			});
	}

	public static TimeZone getTimeZone(
		SearchRequestBuilder searchRequestBuilder) {

		return searchRequestBuilder.withSearchContextGet(
			searchContext -> searchContext.getTimeZone());
	}

	public static long getUserId(SearchRequestBuilder searchRequestBuilder) {
		return searchRequestBuilder.withSearchContextGet(
			searchContext -> searchContext.getUserId());
	}

	private static Object _getAttributeObject(
		String attributeName, SearchRequestBuilder searchRequestBuilder) {

		return searchRequestBuilder.withSearchContextGet(
			searchContext -> searchContext.getAttribute(attributeName));
	}

}