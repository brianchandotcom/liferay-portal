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

package com.liferay.frontend.taglib.chart.model.combination;

import com.liferay.frontend.taglib.chart.model.ChartConfig;
import com.liferay.frontend.taglib.chart.model.TypedMultiValueColumn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Iván Zaera Avellón
 */
public class CombinationChartConfig extends ChartConfig<TypedMultiValueColumn> {

	public void addGroup(Collection<String>... group) {
		ArrayList groups = get("groups", ArrayList.class);

		groups.add(group);
	}

	public void addGroup(String... group) {
		ArrayList groups = get("groups", ArrayList.class);

		groups.add(Arrays.asList(group));
	}

}