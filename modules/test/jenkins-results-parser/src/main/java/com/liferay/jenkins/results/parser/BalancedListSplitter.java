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

/**
 * @author Peter Yoo
 */
public abstract class BalancedListSplitter<T> {

	public BalancedListSplitter(long maxListWeight) {
		_maxListWeight = maxListWeight;
	}

	public long getWeight(ListItemList listItemList) {
		long weight = 0;

		for (ListItem listItem : listItemList) {
			weight += listItem.weight;
		}

		return weight;
	}

	public abstract long getWeight(T item);

	public List<List<T>> split(List<T> list) {
		ListItemList listItems = new ListItemList(this);

		for (T item : list) {
			listItems.add(new ListItem(item));
		}

		Collections.sort(listItems);

		long totalWeight = listItems.getWeight();

		int minNumberOfLists = (int)(totalWeight / _maxListWeight);

		if ((totalWeight % _maxListWeight) > 0) {
			minNumberOfLists++;
		}

		List<ListItemList> listItemSortedSetList = _createListItemSortedSetList(
			minNumberOfLists);

		for (ListItem listItem : listItems) {
			Collections.sort(listItemSortedSetList);

			ListItemList emptiestListItemSortedSet = listItemSortedSetList.get(
				0);

			if (emptiestListItemSortedSet.isEmpty() ||
				(emptiestListItemSortedSet.getAvailableWeight() >=
					listItem.weight)) {

				emptiestListItemSortedSet.add(listItem);

				continue;
			}

			ListItemList newListItemSortedSet = new ListItemList(
				this, _maxListWeight);

			newListItemSortedSet.add(listItem);

			listItemSortedSetList.add(newListItemSortedSet);
		}

		List<List<T>> lists = new ArrayList<>(listItemSortedSetList.size());

		for (ListItemList listItemSortedSet : listItemSortedSetList) {
			lists.add(listItemSortedSet.toList());
		}

		return lists;
	}

	private List<ListItemList> _createListItemSortedSetList(int size) {
		List<ListItemList> listItemSortedSetList = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			listItemSortedSetList.add(new ListItemList(this, _maxListWeight));
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

	private class ListItemList
		extends ArrayList<ListItem> implements Comparable<ListItemList> {

		public ListItemList(BalancedListSplitter<T> balancedListSplitter) {
			_balancedListSplitter = balancedListSplitter;
		}

		public ListItemList(
			BalancedListSplitter<T> balancedListSplitter, Long targetWeight) {

			this(balancedListSplitter);

			_targetWeight = targetWeight;
		}

		@Override
		public int compareTo(ListItemList otherListItemSortedSet) {
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
			return _balancedListSplitter.getWeight(this);
		}

		public List<T> toList() {
			List<T> list = new ArrayList<>(size());

			for (ListItem listItem : this) {
				list.add(listItem.item);
			}

			return list;
		}

		private BalancedListSplitter<T> _balancedListSplitter;
		private Long _targetWeight;

	}

}