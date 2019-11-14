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

package com.liferay.talend.common.daikon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.talend.daikon.NamedThing;
import org.talend.daikon.SimpleNamedThing;

/**
 * @author Igor Beslic
 */
public class DaikonUtil {

	public static List<NamedThing> toNamedThings(Map<String, String> names) {
		List<NamedThing> namedThings = new ArrayList<>();

		names.forEach(
			(key, value) -> namedThings.add(new SimpleNamedThing(value, key)));

		return namedThings;
	}

	public static List<NamedThing> toNamedThings(Set<String> names) {
		List<NamedThing> namedThings = new ArrayList<>();

		names.forEach(
			name -> namedThings.add(new SimpleNamedThing(name, name)));

		return namedThings;
	}

}