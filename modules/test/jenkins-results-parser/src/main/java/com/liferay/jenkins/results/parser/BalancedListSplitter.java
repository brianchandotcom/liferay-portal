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

	public long getWeight(ListItemList listItemList, ListItem newListItem) {
		long weight = newListItem.weight;

		for (ListItem listItem : listItemList) {
			weight += listItem.weight;
		}

		return weight;
	}

	public abstract long getWeight(T item);

	public List<List<T>> split(List<T> list) {
		ListItemList sortedListItemList = new ListItemList();

		for (T item : list) {
			sortedListItemList.add(new ListItem(item));
		}

		Collections.sort(sortedListItemList);

		List<ListItemList> listItemLists = new ArrayList<>();

		while (!sortedListItemList.isEmpty()) {
			ListItemList listItemList = new ListItemList();

			for (ListItem listItem : sortedListItemList) {
				if (listItemList.isEmpty() ||
					(getWeight(listItemList, listItem) <= _maxListWeight)) {

					listItemList.add(listItem);

					continue;
				}

				break;
			}

			listItemLists.add(listItemList);

			sortedListItemList.removeAll(listItemList);
		}

		List<List<T>> lists = new ArrayList<>(listItemLists.size());

		for (ListItemList listItemList : listItemLists) {
			lists.add(listItemList.toList());
		}

		return lists;
	}

	protected class ListItem implements Comparable<ListItem> {

		public ListItem(T item) {
			this.item = item;
			weight = getWeight(item);
		}

		@Override
		public int compareTo(ListItem otherListItem) {
			return -1 * weight.compareTo(otherListItem.weight);
		}

		public T getItem() {
			return item;
		}

		public T item;
		public Long weight;

	}

	protected class ListItemList extends ArrayList<ListItem> {

		public List<T> toList() {
			List<T> list = new ArrayList<>(size());

			for (ListItem listItem : this) {
				list.add(listItem.item);
			}

			return list;
		}

	}

	private final long _maxListWeight;

}