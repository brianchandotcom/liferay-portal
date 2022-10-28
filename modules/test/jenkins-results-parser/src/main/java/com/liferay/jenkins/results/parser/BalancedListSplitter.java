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

package com.liferay.jenkins.results.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 * @author Peter Yoo
 */
public abstract class BalancedListSplitter<T> {

	public BalancedListSplitter(long maxListWeight) {
		_maxListWeight = maxListWeight;
	}

	public abstract long getWeight(T item);

	public List<List<T>> split(List<T> list) {
		ListItemTreeSet listItems = new ListItemTreeSet();

		for (T item : list) {
			listItems.add(new ListItem(item));
		}

		long totalWeight = listItems.getWeight();

		int minNumberOfLists = (int)(totalWeight / _maxListWeight);

		if ((totalWeight % _maxListWeight) > 0) {
			minNumberOfLists++;
		}

		List<ListItemTreeSet> listItemSortedSetList =
			_createListItemSortedSetList(minNumberOfLists);

		for (ListItem listItem : listItems) {
			Collections.sort(listItemSortedSetList);

			ListItemTreeSet emptiestListItemSortedSet =
				listItemSortedSetList.get(0);

			if (emptiestListItemSortedSet.isEmpty() ||
				(emptiestListItemSortedSet.getAvailableWeight() >=
					listItem.weight)) {

				emptiestListItemSortedSet.add(listItem);

				continue;
			}

			ListItemTreeSet newListItemSortedSet = new ListItemTreeSet(
				_maxListWeight);

			newListItemSortedSet.add(listItem);

			listItemSortedSetList.add(newListItemSortedSet);
		}

		List<List<T>> lists = new ArrayList<>(listItemSortedSetList.size());

		for (ListItemTreeSet listItemSortedSet : listItemSortedSetList) {
			lists.add(listItemSortedSet.toList());
		}

		return lists;
	}

	private List<ListItemTreeSet> _createListItemSortedSetList(int size) {
		List<ListItemTreeSet> listItemSortedSetList = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			listItemSortedSetList.add(new ListItemTreeSet(_maxListWeight));
		}

		return listItemSortedSetList;
	}

	private final long _maxListWeight;

	private class ListItem implements Comparable<ListItem> {

		public ListItem(T item) {
			this.item = item;
			weight = getWeight(item);
		}

		@Override
		public int compareTo(ListItem otherListItem) {
			return -1 * weight.compareTo(otherListItem.weight);
		}

		public T item;
		public Long weight;

	}

	private class ListItemTreeSet
		extends TreeSet<ListItem> implements Comparable<ListItemTreeSet> {

		public ListItemTreeSet() {
		}

		public ListItemTreeSet(Long targetWeight) {
			_targetWeight = targetWeight;
		}

		@Override
		public int compareTo(ListItemTreeSet otherListItemSortedSet) {
			Long availableWeight = getAvailableWeight();
			Long otherAvailableWeight =
				otherListItemSortedSet.getAvailableWeight();

			if ((availableWeight == null) && (otherAvailableWeight == null)) {
				return 0;
			}

			if (availableWeight == null) {
				return 1;
			}

			if (otherAvailableWeight == null) {
				return -1;
			}

			return -1 * availableWeight.compareTo(otherAvailableWeight);
		}

		public Long getAvailableWeight() {
			if (_targetWeight == null) {
				return null;
			}

			long availableWeight = _targetWeight - getWeight();

			if (availableWeight <= 0) {
				return 0L;
			}

			return availableWeight;
		}

		public long getWeight() {
			long weight = 0;

			for (ListItem listItem : this) {
				weight += listItem.weight;
			}

			return weight;
		}

		public List<T> toList() {
			List<T> list = new ArrayList<>(size());

			for (ListItem listItem : this) {
				list.add(listItem.item);
			}

			return list;
		}

		private Long _targetWeight;

	}

}