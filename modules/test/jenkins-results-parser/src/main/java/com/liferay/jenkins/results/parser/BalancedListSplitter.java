/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Peter Yoo
 */
public abstract class BalancedListSplitter<T extends WeightedItem> {

	public BalancedListSplitter(long maxListWeight) {
		_maxListWeight = maxListWeight;
	}

	public List<List<T>> split(List<T> list) {
		Set<ListItemList> candidateListItemLists = new TreeSet<>();
		List<ListItemList> listItemLists = new ArrayList<>();

		for (T item : list) {
			ListItem listItem = new ListItem(item);

			ListItemList chosenListItemList = null;

			if (listItem.isIsolated()) {
				chosenListItemList = new ListItemList(getMaxListWeight());

				chosenListItemList.add(listItem);

				listItemLists.add(chosenListItemList);

				continue;
			}

			for (ListItemList candidateListItemList : candidateListItemLists) {
				long weightAfterAdding =
					candidateListItemList.getWeightAfterAdding(listItem);

				if (weightAfterAdding <= getMaxListWeight()) {
					chosenListItemList = candidateListItemList;

					break;
				}
			}

			if (chosenListItemList == null) {
				chosenListItemList = new ListItemList(getMaxListWeight());

				candidateListItemLists.add(chosenListItemList);
			}

			chosenListItemList.add(listItem);
		}

		listItemLists.addAll(candidateListItemLists);

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

	protected long getMaxListWeight() {
		return _maxListWeight;
	}

	protected class ListItem implements Comparable<ListItem> {

		public ListItem(T item) {
			_item = item;
		}

		@Override
		public int compareTo(ListItem otherListItem) {
			Long weight = getWeight();

			return -1 * weight.compareTo(otherListItem.getWeight());
		}

		public T getItem() {
			return _item;
		}

		public long getOverheadWeight() {
			return _item.getOverheadWeight();
		}

		public long getWeight() {
			return _item.getWeight();
		}

		public boolean isIsolated() {
			return _item.isIsolated();
		}

		private final T _item;

	}

	protected class ListItemList
		extends ArrayList<ListItem> implements Comparable<ListItemList> {

		public ListItemList(Long targetWeight) {
			_targetWeight = targetWeight;
		}

		@Override
		public boolean add(ListItem listItem) {
			_totalOverheadWeight += listItem.getOverheadWeight();
			_totalWeight += listItem.getWeight();

			return super.add(listItem);
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
			if (isEmpty()) {
				return 0L;
			}

			long averageOverheadWeight = _totalOverheadWeight / size();

			return averageOverheadWeight + _totalWeight;
		}

		public long getWeightAfterAdding(ListItem listItem) {
			if (isEmpty()) {
				return listItem.getWeight() + listItem.getOverheadWeight();
			}

			long totalWeight = _totalWeight + listItem.getWeight();
			long totalOverheadWeight =
				_totalOverheadWeight + listItem.getOverheadWeight();

			return totalWeight + ((totalOverheadWeight / size()) + 1);
		}

		public List<T> toList() {
			List<T> list = new ArrayList<>(size());

			for (ListItem listItem : this) {
				list.add(listItem._item);
			}

			return list;
		}

		private final Long _targetWeight;
		private long _totalOverheadWeight = 0L;
		private long _totalWeight = 0L;

	}

	private final long _maxListWeight;

}