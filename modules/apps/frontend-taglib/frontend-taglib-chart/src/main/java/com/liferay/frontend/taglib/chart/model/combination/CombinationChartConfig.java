/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.chart.model.combination;

import com.liferay.frontend.taglib.chart.model.ChartConfig;
import com.liferay.frontend.taglib.chart.model.TypedMultiValueColumn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Iván Zaera Avellón
 */
public class CombinationChartConfig extends ChartConfig<TypedMultiValueColumn> {

	public void addGroup(Collection<String>... group) {
		ArrayList<Collection<String>[]> groups = get("groups", ArrayList.class);

		groups.add(group);
	}

	public void addGroup(String... group) {
		ArrayList<List<String>> groups = get("groups", ArrayList.class);

		groups.add(Arrays.asList(group));
	}

}