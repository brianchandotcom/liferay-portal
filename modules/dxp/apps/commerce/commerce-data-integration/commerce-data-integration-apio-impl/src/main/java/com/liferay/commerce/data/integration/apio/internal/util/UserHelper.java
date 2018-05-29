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

package com.liferay.commerce.data.integration.apio.internal.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author Rodrigo Guedes de Souza
 */
public class UserHelper {

	public static List<Long> convertLongArrayToList(long[] values) {
		if (values == null) {
			return new ArrayList<>();
		}

		LongStream longStream = Arrays.stream(values);

		return longStream.boxed().collect(Collectors.toList());
	}

	public static long[] convertLongListToArray(List<Long> list) {
		if (list == null) {
			return new long[0];
		}

		Stream<Long> stream = list.stream();

		return stream.mapToLong(l -> l).toArray();
	}

}