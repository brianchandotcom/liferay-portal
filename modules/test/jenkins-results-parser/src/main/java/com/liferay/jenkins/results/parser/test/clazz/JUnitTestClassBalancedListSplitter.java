/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz;

import com.liferay.jenkins.results.parser.BalancedListSplitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Peter Yoo
 * @author Michael Hashimoto
 */
public class JUnitTestClassBalancedListSplitter
	extends BalancedListSplitter<TestClass> {

	public JUnitTestClassBalancedListSplitter(long maxListWeight) {
		super(maxListWeight);
	}

	@Override
	public long getWeight(ListItemList listItemList) {
		if ((listItemList == null) || listItemList.isEmpty()) {
			return 0L;
		}

		long averageDuration = 0L;
		long averageOverheadDuration = 0L;

		Map<String, Long> testTaskDurations = new HashMap<>();

		for (ListItem listItem : listItemList) {
			TestClass testClass = listItem.getItem();

			averageDuration += testClass.getAverageDuration();
			averageOverheadDuration += testClass.getAverageOverheadDuration();

			String testTaskName = testClass.getTestTaskName();

			if (testTaskDurations.containsKey(testTaskName)) {
				continue;
			}

			testTaskDurations.put(
				testTaskName, testClass.getAverageTestTaskDuration());
		}

		averageDuration += averageOverheadDuration / listItemList.size();

		for (Long testTaskDuration : testTaskDurations.values()) {
			averageDuration += testTaskDuration;
		}

		return averageDuration;
	}

	@Override
	public long getWeight(TestClass item) {
		return item.getAverageDuration() + item.getAverageOverheadDuration() +
			item.getAverageTestTaskDuration();
	}

	@Override
	public List<List<TestClass>> split(List<TestClass> list) {
		ListItemList listItems = new ListItemList(this);

		for (TestClass item : list) {
			listItems.add(new ListItem(this, item));
		}

		Collections.sort(listItems);

		List<ListItemList> listItemLists = new ArrayList<>();

		for (ListItem listItem : listItems) {
			boolean addedToList = false;

			for (ListItemList listItemList : listItemLists) {
				long availableWeight = listItemList.getAvailableWeight();

				if (availableWeight <= 0L) {
					continue;
				}

				if (availableWeight >= listItem.getWeight(listItemList)) {
					listItemList.add(listItem);

					addedToList = true;

					break;
				}
			}

			if (addedToList) {
				continue;
			}

			ListItemList newListItemList = new ListItemList(
				this, getMaxListWeight());

			newListItemList.add(listItem);

			listItemLists.add(newListItemList);
		}

		List<List<TestClass>> lists = new ArrayList<>(listItemLists.size());

		for (ListItemList listItemList : listItemLists) {
			List<TestClass> newList = listItemList.toList();

			if ((newList == null) || newList.isEmpty()) {
				continue;
			}

			lists.add(newList);
		}

		return lists;
	}

}