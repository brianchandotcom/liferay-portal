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
			String attributeName, 
			SearchRequestBuilder searchRequestBuilder) {

		Object obj = _getAttribute(attributeName, searchRequestBuilder);
		
		if (Objects.isNull(obj)) {
			return null;
		}
		
		return searchRequestBuilder.withSearchContextGet(
				searchContext -> 
					GetterUtil.getBoolean(obj));
		}

	public static long getCompanyId(SearchRequestBuilder searchRequestBuilder) {
		return searchRequestBuilder.withSearchContextGet(
				searchContext -> {
					return searchContext.getCompanyId();
				});
	}

	public static Double getDoubleAttribute(
			String attributeName, 
			SearchRequestBuilder searchRequestBuilder) {

		Object obj = _getAttribute(attributeName, searchRequestBuilder);
		
		if (Objects.isNull(obj)) {
			return null;
		}
		
		return searchRequestBuilder.withSearchContextGet(
				searchContext -> 
					GetterUtil.getDouble(obj));
		}
	
	public static Float getFloatAttribute(
			String attributeName, 
			SearchRequestBuilder searchRequestBuilder) {

		Object obj = _getAttribute(attributeName, searchRequestBuilder);
		
		if (Objects.isNull(obj)) {
			return null;
		}
		
		return searchRequestBuilder.withSearchContextGet(
				searchContext -> 
					GetterUtil.getFloat(obj));
		}
	
	public static Integer[] getIntegerArrayAttribute(String attributeName, SearchRequestBuilder searchRequestBuilder) {

		Object obj = _getAttribute(attributeName, searchRequestBuilder);
		
		if (Objects.isNull(obj)) {
			return null;
		}
		
		if (obj instanceof int[]) {
			int[] arr = (int[])obj;

			if (arr.length > 0) {
				IntStream intStream = Arrays.stream(arr);

				Integer[] boxedArray = intStream.boxed(
				).toArray(
					Integer[]::new
				);

				return boxedArray;
			}
		} else if (obj instanceof Integer[]) {
			Integer[] arr = (Integer[])obj;

			return  arr;
		}

		return null;
	}

	public static Integer getIntegerAttribute(
			String attributeName, 
			SearchRequestBuilder searchRequestBuilder) {

		Object obj = _getAttribute(attributeName, searchRequestBuilder);
		
		if (Objects.isNull(obj)) {
			return null;
		}

		return searchRequestBuilder.withSearchContextGet(
				searchContext -> 
					 GetterUtil.getInteger(obj));
		}
	

	
				
	public static String getKeywords( 
			SearchRequestBuilder searchRequestBuilder) {

		return searchRequestBuilder.withSearchContextGet(
				searchContext -> {
					return searchContext.getKeywords();
				});
	}

	public static Layout getLayout(SearchRequestBuilder searchRequestBuilder) {
		
		return searchRequestBuilder.withSearchContextGet(
				searchContext -> {
					return searchContext.getLayout();
				});

	}
	
	public static Locale getLocale(
			SearchRequestBuilder searchRequestBuilder) {

		return searchRequestBuilder.withSearchContextGet(
				searchContext -> 
					searchContext.getLocale());
		}
	


	public static Long[] getLongArrayAttribute(String attributeName, SearchRequestBuilder searchRequestBuilder) {

		Object obj = _getAttribute(attributeName, searchRequestBuilder);
		
		if (Objects.isNull(obj)) {
			return null;
		}

		if (obj instanceof long[]) {
			long[] arr = (long[])obj;

			if (arr.length > 0) {
				LongStream longStream = Arrays.stream(arr);

				Long[] boxedArray = longStream.boxed(
				).toArray(
					Long[]::new
				);

				return boxedArray;
			}
		} else if (obj instanceof Long[]) {
			Long[] arr = (Long[])obj;

			return  arr;
		}

		return null;
	}
	
	public static Long getLongAttribute(
			String attributeName, 
			SearchRequestBuilder searchRequestBuilder) {

		Object obj = _getAttribute(attributeName, searchRequestBuilder);
		
		if (Objects.isNull(obj)) {
			return null;
		}
		
		return searchRequestBuilder.withSearchContextGet(
				searchContext -> 
					GetterUtil.getLong(obj));
		}
		
	public static String[] getStringArrayAttribute(
			String attributeName, 
			SearchRequestBuilder searchRequestBuilder) {

		Object obj = _getAttribute(attributeName, searchRequestBuilder);
		
		if (Objects.isNull(obj)) {
			return null;
		}

		if (obj  instanceof String[]) {
			String[] arr = (String[])obj;

			if (arr.length > 0) {
				return arr;
			}
		}

		return null;
	}
	
	public static String getStringAttribute(String attributeName, 
			SearchRequestBuilder searchRequestBuilder) {

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
				searchContext -> {
					return searchContext.getTimeZone();
				});
	}
	
	
	public static long getUserId( 
			SearchRequestBuilder searchRequestBuilder) {

		return searchRequestBuilder.withSearchContextGet(
				searchContext -> {
					return searchContext.getUserId();
				});
	}
	
	private static Object _getAttribute(String attributeName, 
			SearchRequestBuilder searchRequestBuilder) {
		
		return searchRequestBuilder.withSearchContextGet(
				searchContext -> searchContext.getAttribute(attributeName));
	}


}
