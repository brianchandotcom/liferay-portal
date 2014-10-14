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

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Shuyang Zhou
 */
public class BatchablePipe<K, V> {

	public BatchablePipe() {
		_headEntry = new Entry<K, V>(null);
		_lastEntryReference = new AtomicReference<Entry<K, V>>(_headEntry);
	}

	public boolean put(IncreasableEntry<K, V> increasableEntry) {
		Entry<K, V> newEntry = new Entry<K, V>(increasableEntry);

		while (true) {
			if (_doIncrease(increasableEntry)) {
				return false;
			}

			Entry<K, V> lastEntryLink = _lastEntryReference.get();
			Entry<K, V> nextEntryLink = lastEntryLink._nextEntry.getReference();

			if (nextEntryLink == null) {
				if (lastEntryLink._nextEntry.compareAndSet(
						null, newEntry, false, false)) {

					_lastEntryReference.set(newEntry);

					return true;
				}
			}
			else {
				_lastEntryReference.compareAndSet(lastEntryLink, nextEntryLink);
			}
		}
	}

	public IncreasableEntry<K, V> take() {
		boolean[] marked = {false};

		take:
		while (true) {
			Entry<K, V> predecessorEntry = _headEntry;
			Entry<K, V> currentEntry =
				predecessorEntry._nextEntry.getReference();

			while (currentEntry != null) {
				Entry<K, V> successorEntry = currentEntry._nextEntry.get(
					marked);

				if (marked[0]) {
					if (!predecessorEntry._nextEntry.compareAndSet(
							currentEntry, successorEntry, false, false)) {

						continue take;
					}

					currentEntry = predecessorEntry._nextEntry.getReference();

					continue;
				}

				if (currentEntry._nextEntry.compareAndSet(
						successorEntry, successorEntry, false, true)) {

					return currentEntry._increasableEntry;
				}

				continue take;
			}

			return null;
		}
	}

	private boolean _doIncrease(IncreasableEntry<K, V> increasableEntry) {
		boolean[] marked = {false};

		retry:
		while (true) {
			Entry<K, V> predecessorEntry = _headEntry;
			Entry<K, V> currentEntry =
				predecessorEntry._nextEntry.getReference();

			while (currentEntry != null) {
				Entry<K, V> successorEntry = currentEntry._nextEntry.get(
					marked);

				if (marked[0]) {
					if (!predecessorEntry._nextEntry.compareAndSet(
							currentEntry, successorEntry, false, false)) {

						continue retry;
					}

					currentEntry = predecessorEntry._nextEntry.getReference();

					continue;
				}

				if (currentEntry._increasableEntry.getKey().equals(
						increasableEntry.getKey())) {

					return currentEntry._increasableEntry.increase(
						increasableEntry.getValue());
				}

				predecessorEntry = currentEntry;
				currentEntry = successorEntry;
			}

			_lastEntryReference.set(predecessorEntry);

			return false;
		}
	}

	private final Entry<K, V> _headEntry;
	private final AtomicReference<Entry<K, V>> _lastEntryReference;

	private static class Entry<K, V> {

		private Entry(IncreasableEntry<K, V> increasableEntry) {
			_increasableEntry = increasableEntry;
			_nextEntry = new AtomicMarkableReference<Entry<K, V>>(null, false);
		}

		private final IncreasableEntry<K, V> _increasableEntry;
		private final AtomicMarkableReference<Entry<K, V>> _nextEntry;

	}

}