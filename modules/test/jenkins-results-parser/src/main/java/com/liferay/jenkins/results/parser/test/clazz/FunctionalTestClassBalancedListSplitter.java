/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz;

import com.liferay.jenkins.results.parser.BalancedListSplitter;

/**
 * @author Peter Yoo
 * @author Michael Hashimoto
 */
public class FunctionalTestClassBalancedListSplitter
	extends BalancedListSplitter<TestClass> {

	public FunctionalTestClassBalancedListSplitter(long maxListWeight) {
		super(maxListWeight);
	}

	@Override
	public long getWeight(ListItemList listItemList) {
		if ((listItemList == null) || listItemList.isEmpty()) {
			return 0L;
		}

		long averageDuration = 0L;
		long averageOverheadDuration = 0L;

		for (ListItem listItem : listItemList) {
			TestClass testClass = listItem.getItem();

			averageDuration += testClass.getAverageDuration();
			averageOverheadDuration += testClass.getAverageOverheadDuration();
		}

		averageDuration += averageOverheadDuration / listItemList.size();

		return averageDuration;
	}

	@Override
	public long getWeight(TestClass item) {
		return item.getAverageDuration() + item.getAverageOverheadDuration();
	}

}