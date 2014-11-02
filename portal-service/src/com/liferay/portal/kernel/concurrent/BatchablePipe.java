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

package com.liferay.portal.kernel.concurrent;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Shuyang Zhou
 */
public class BatchablePipe<K, V> {

	public boolean put(IncreasableEntry<K, V> increasableEntry) {
		K key = increasableEntry.getKey();

		while (true) {
			IncreasableEntryWrapper<K, V> previousIncreasableEntryWrapper =
				concurrentMap.putIfAbsent(
					key, new IncreasableEntryWrapper<K, V>(increasableEntry));

			if (previousIncreasableEntryWrapper == null) {
				queue.offer(increasableEntry);

				return true;
			}

			IncreasableEntry<K, V> previousIncreasableEntry =
				previousIncreasableEntryWrapper.increasableEntry;

			IncreasableEntry<K, V> newIncreasableEntry =
				increasableEntry.increase(previousIncreasableEntry.getValue());

			if (concurrentMap.replace(
					key, previousIncreasableEntryWrapper,
				new IncreasableEntryWrapper<K, V>(newIncreasableEntry))) {

				queue.offer(newIncreasableEntry);

				return false;
			}
		}
	}

	public IncreasableEntry<K, V> take() {
		while (true) {
			IncreasableEntry<K, V> increasableEntry = queue.poll();

			if (increasableEntry == null) {
				return null;
			}

			if (concurrentMap.remove(
					increasableEntry.getKey(),
				new IncreasableEntryWrapper<K, V>(increasableEntry))) {

				return increasableEntry;
			}
		}
	}

	protected final ConcurrentMap<K, IncreasableEntryWrapper<K, V>>
		concurrentMap =
			new ConcurrentHashMap<K, IncreasableEntryWrapper<K, V>>();
	protected final Queue<IncreasableEntry<K, V>> queue =
		new ConcurrentLinkedQueue<IncreasableEntry<K, V>>();

	protected static class IncreasableEntryWrapper<K, V> {

		public IncreasableEntryWrapper(
			IncreasableEntry<K, V> increasableEntry) {

			this.increasableEntry = increasableEntry;
		}

		@Override
		public boolean equals(Object obj) {
			IncreasableEntryWrapper<K, V> increasableEntryWrapper =
				(IncreasableEntryWrapper<K, V>)obj;

			if (increasableEntry == increasableEntryWrapper.increasableEntry) {
				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			return increasableEntry.hashCode();
		}

		@Override
		public String toString() {
			return increasableEntry.toString();
		}

		protected final IncreasableEntry<K, V> increasableEntry;

	}

}