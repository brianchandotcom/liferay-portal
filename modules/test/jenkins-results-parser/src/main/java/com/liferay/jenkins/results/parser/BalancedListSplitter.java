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
			weight += listItem.getWeight(null);
		}

		return weight;
	}

	public abstract long getWeight(T item);

	public List<List<T>> split(List<T> list) {
		ListItemList listItems = new ListItemList(this);

		for (T item : list) {
			listItems.add(new ListItem(this, item));
		}

		Collections.sort(listItems);

		long totalWeight = listItems.getWeight();

		int minNumberOfLists = (int)(totalWeight / _maxListWeight);

		if ((totalWeight % _maxListWeight) > 0) {
			minNumberOfLists++;
		}

		List<ListItemList> listItemLists = _createListItemSortedSetList(
			minNumberOfLists);

		for (ListItem listItem : listItems) {
			Collections.sort(listItemLists);

			ListItemList emptiestListItemList = listItemLists.get(0);

			if (emptiestListItemList.isEmpty() ||
				(emptiestListItemList.getAvailableWeight() >=
					listItem.getWeight(emptiestListItemList))) {

				emptiestListItemList.add(listItem);

				continue;
			}

			ListItemList newListItemList = new ListItemList(
				this, _maxListWeight);

			newListItemList.add(listItem);

			listItemLists.add(newListItemList);
		}

		List<List<T>> lists = new ArrayList<>(listItemLists.size());

		for (ListItemList listItemList : listItemLists) {
			List<T> newList = listItemList.toList();

			if ((newList == null) || newList.isEmpty()) {
				continue;
			}

			lists.add(newList);
		}

		return lists;
	}

	protected class ListItem implements Comparable<ListItem> {

		public ListItem(BalancedListSplitter<T> balancedListSplitter, T item) {
			_balancedListSplitter = balancedListSplitter;
			_item = item;
		}

		@Override
		public int compareTo(ListItem otherListItem) {
			Long weight = getWeight(null);

			return -1 * weight.compareTo(otherListItem.getWeight(null));
		}

		public T getItem() {
			return _item;
		}

		public long getWeight(ListItemList listItemList) {
			if (listItemList == null) {
				return _getWeight();
			}

			ListItemList tempListItemList = new ListItemList(
				_balancedListSplitter);

			tempListItemList.addAll(listItemList);

			long originalWeight = tempListItemList.getWeight();

			tempListItemList.add(this);

			return tempListItemList.getWeight() - originalWeight;
		}

		private long _getWeight() {
			return _balancedListSplitter.getWeight(_item);
		}

		private BalancedListSplitter<T> _balancedListSplitter;
		private final T _item;

	}

	protected class ListItemList
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
				list.add(listItem._item);
			}

			return list;
		}

		private BalancedListSplitter<T> _balancedListSplitter;
		private Long _targetWeight;

	}

	private List<ListItemList> _createListItemSortedSetList(int size) {
		List<ListItemList> listItemSortedSetList = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			listItemSortedSetList.add(new ListItemList(this, _maxListWeight));
		}

		return listItemSortedSetList;
	}

	private final long _maxListWeight;

}