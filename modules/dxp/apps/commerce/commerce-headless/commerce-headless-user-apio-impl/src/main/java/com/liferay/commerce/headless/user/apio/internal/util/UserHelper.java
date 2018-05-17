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

package com.liferay.commerce.headless.user.apio.internal.util;

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