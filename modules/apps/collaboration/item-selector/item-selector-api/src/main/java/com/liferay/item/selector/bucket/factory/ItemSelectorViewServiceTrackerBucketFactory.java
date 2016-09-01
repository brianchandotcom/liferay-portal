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

package com.liferay.item.selector.bucket.factory;

import com.liferay.item.selector.ItemSelectorView;
import com.liferay.osgi.service.tracker.collections.ServiceReferenceServiceTuple;
import com.liferay.osgi.service.tracker.collections.internal.ServiceReferenceServiceTupleComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerBucket;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerBucketFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.osgi.framework.ServiceReference;

/**
 * @author Roberto Díaz
 */
public class ItemSelectorViewServiceTrackerBucketFactory
	implements ServiceTrackerBucketFactory {

	public ItemSelectorViewServiceTrackerBucketFactory() {
		_comparator = Collections.reverseOrder();
	}

	public ItemSelectorViewServiceTrackerBucketFactory(
		Comparator<ServiceReference<ItemSelectorView>> comparator) {

		_comparator = comparator;
	}

	@Override
	public ServiceTrackerBucket create() {
		return new ListServiceTrackerBucket();
	}

	private final Comparator<ServiceReference<ItemSelectorView>> _comparator;

	private class ListServiceTrackerBucket
		implements ServiceTrackerBucket
			<ItemSelectorView, ItemSelectorView, List<ItemSelectorView>> {

		@Override
		public List<ItemSelectorView> getContent() {
			return _services;
		}

		@Override
		public synchronized boolean isDisposable() {
			return _serviceReferenceServiceTuples.isEmpty();
		}

		@Override
		public synchronized void remove(
			ServiceReferenceServiceTuple<ItemSelectorView, ItemSelectorView>
				serviceReferenceServiceTuple) {

			_serviceReferenceServiceTuples.remove(serviceReferenceServiceTuple);

			rebuild();
		}

		@Override
		public synchronized void store(
			ServiceReferenceServiceTuple<ItemSelectorView, ItemSelectorView>
				serviceReferenceServiceTuple) {

			_serviceReferenceServiceTuples.add(serviceReferenceServiceTuple);

			rebuild();
		}

		protected void rebuild() {
			_services = new ArrayList<>(_serviceReferenceServiceTuples.size());

			ServiceReferenceServiceTupleComparator<ItemSelectorView>
				serviceReferenceServiceTupleComparator =
					new ServiceReferenceServiceTupleComparator<>(_comparator);

			Set<ServiceReferenceServiceTuple
				<ItemSelectorView, ItemSelectorView>>
					filteredServiceReferenceServiceTuples = new TreeSet<>(
						serviceReferenceServiceTupleComparator);

			Map<String, Integer> itemSelectorViewKeyServiceRankingMap =
				new HashMap<>();

			for (ServiceReferenceServiceTuple
					<ItemSelectorView, ItemSelectorView>
						serviceReferenceServiceTuple :
							_serviceReferenceServiceTuples) {

				ServiceReference<ItemSelectorView> serviceReference =
					serviceReferenceServiceTuple.getServiceReference();

				String itemSelectorViewKey = GetterUtil.getString(
					serviceReference.getProperty("item.selector.view.key"));

				if (Validator.isNotNull(itemSelectorViewKey)) {
					_overrideFilteredServiceReferenceServiceTuples(
						filteredServiceReferenceServiceTuples,
						serviceReferenceServiceTuple,
						itemSelectorViewKeyServiceRankingMap);
				}
				else {
					filteredServiceReferenceServiceTuples.add(
						serviceReferenceServiceTuple);
				}
			}

			for (ServiceReferenceServiceTuple
					<ItemSelectorView, ItemSelectorView>
						filteredServiceReferenceServiceTuple :
							filteredServiceReferenceServiceTuples) {

				_services.add(
					filteredServiceReferenceServiceTuple.getService());
			}

			_services = Collections.unmodifiableList(_services);
		}

		private ListServiceTrackerBucket() {
			ServiceReferenceServiceTupleComparator<ItemSelectorView>
				serviceReferenceServiceTupleComparator =
					new ServiceReferenceServiceTupleComparator<>(_comparator);

			_serviceReferenceServiceTuples = new TreeSet<>(
				serviceReferenceServiceTupleComparator);
		}

		private ServiceReferenceServiceTuple<ItemSelectorView, ItemSelectorView>
			_getItemSelectorViewServiceReferenceServiceTuple(
				Set<ServiceReferenceServiceTuple
					<ItemSelectorView, ItemSelectorView>>
						serviceReferenceServiceTuples,
				String itemSelectorViewKey) {

			ServiceReferenceServiceTuple<ItemSelectorView, ItemSelectorView>
				serviceReferenceServiceTuple = null;

			for (ServiceReferenceServiceTuple
					<ItemSelectorView, ItemSelectorView>
						curServiceReferenceServiceTuple :
							serviceReferenceServiceTuples) {

				ServiceReference<ItemSelectorView> curServiceReference =
					curServiceReferenceServiceTuple.getServiceReference();

				String curServiceReferenceItemSelectorViewKey =
					GetterUtil.getString(
						curServiceReference.getProperty(
							"item.selector.view.key"));

				if (curServiceReferenceItemSelectorViewKey.equals(
						itemSelectorViewKey)) {

					serviceReferenceServiceTuple =
						curServiceReferenceServiceTuple;

					break;
				}
			}

			return serviceReferenceServiceTuple;
		}

		private void _overrideFilteredServiceReferenceServiceTuples(
			Set
				<ServiceReferenceServiceTuple
					<ItemSelectorView, ItemSelectorView>>
						filteredServiceReferenceServiceTuples,
			ServiceReferenceServiceTuple<ItemSelectorView, ItemSelectorView>
				serviceReferenceServiceTuple,
			Map<String, Integer> itemSelectorViewKeyServiceRankingMap) {

			ServiceReference<ItemSelectorView> serviceReference =
				serviceReferenceServiceTuple.getServiceReference();

			String itemSelectorViewKey = GetterUtil.getString(
				serviceReference.getProperty("item.selector.view.key"));

			int serviceReferenceServiceRanking = GetterUtil.getInteger(
				serviceReference.getProperty("service.ranking"));

			if (!itemSelectorViewKeyServiceRankingMap.containsKey(
					itemSelectorViewKey)) {

				filteredServiceReferenceServiceTuples.add(
					serviceReferenceServiceTuple);

				itemSelectorViewKeyServiceRankingMap.put(
					itemSelectorViewKey, serviceReferenceServiceRanking);

				return;
			}

			Integer itemSelectorViewServiceRanking =
				itemSelectorViewKeyServiceRankingMap.get(itemSelectorViewKey);

			if (itemSelectorViewServiceRanking >=
					serviceReferenceServiceRanking) {

				return;
			}

			ServiceReferenceServiceTuple<ItemSelectorView, ItemSelectorView>
				overwrittenServiceReferenceServiceTuple =
					_getItemSelectorViewServiceReferenceServiceTuple(
						filteredServiceReferenceServiceTuples,
						itemSelectorViewKey);

			if (overwrittenServiceReferenceServiceTuple != null) {
				filteredServiceReferenceServiceTuples.remove(
					overwrittenServiceReferenceServiceTuple);

				filteredServiceReferenceServiceTuples.add(
					serviceReferenceServiceTuple);

				itemSelectorViewKeyServiceRankingMap.put(
					itemSelectorViewKey, serviceReferenceServiceRanking);
			}
		}

		private final Set
			<ServiceReferenceServiceTuple<ItemSelectorView, ItemSelectorView>>
				_serviceReferenceServiceTuples;
		private List<ItemSelectorView> _services = new ArrayList<>();

	}

}