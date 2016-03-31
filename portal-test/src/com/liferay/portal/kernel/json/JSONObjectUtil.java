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

package com.liferay.portal.kernel.json;

import com.liferay.portal.kernel.util.ReflectionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;

/**
 * @author Shuyang Zhou
 */
public class JSONObjectUtil {

	public static String toOrderedJSONString(JSONObject jsonObject) {
		return toOrderedJSONString(jsonObject.toString());
	}

	public static String toOrderedJSONString(String jsonString) {
		try {
			org.json.JSONObject jsonObject =
				new org.json.JSONObject(jsonString) {

					@Override
					@SuppressWarnings("rawtypes")
					public Iterator keys() {
						Iterator<?> iterator = super.keys();

						List<Object> list = new ArrayList<>(length());

						while (iterator.hasNext()) {
							list.add(iterator.next());
						}

						Collections.sort(
							list,
							new Comparator<Object>() {

								@Override
								public int compare(Object o1, Object o2) {
									String s1 = o1.toString();

									return s1.compareTo(o2.toString());
								}

							});

						return list.iterator();
					}

				};

			return jsonObject.toString();
		}
		catch (JSONException jsone) {
			return ReflectionUtil.throwException(jsone);
		}
	}

}