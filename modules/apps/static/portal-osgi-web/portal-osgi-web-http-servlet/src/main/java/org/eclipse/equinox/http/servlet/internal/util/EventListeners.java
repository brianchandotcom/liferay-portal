/*******************************************************************************
 * Copyright (c) 2014 Raymond Augé and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 ******************************************************************************/

package org.eclipse.equinox.http.servlet.internal.util;

import java.util.*;
import java.util.concurrent.*;
import org.eclipse.equinox.http.servlet.internal.registration.ListenerRegistration;

public class EventListeners {

	public void clear() {
		map.clear();
	}

	public <E extends EventListener> List<E> get(Class<E> clazz) {
		if (clazz == null) {
			throw new NullPointerException("clazz can't be null");
		}

		List<ListenerRegistration> list = map.get(clazz);

		if (list == null) {
			return Collections.emptyList();
		}

		return new ListenerList<E>(list) ;
	}

	public <E extends EventListener> void put(
		Class<E> clazz, ListenerRegistration listenerRegistration) {

		if (clazz == null) {
			throw new NullPointerException("clazz can't be null");
		}

		List<ListenerRegistration> list = map.get(clazz);

		if (list == null) {
			final List<ListenerRegistration> newList =
				new CopyOnWriteArrayList<ListenerRegistration>();

			list = map.putIfAbsent(clazz, newList);

			if (list == null) {
				list = newList;
			}
		}

		list.add(listenerRegistration);
	}

	public void put(
		List<Class<? extends EventListener>> classes,
		ListenerRegistration listenerRegistration) {

		for (Class<? extends EventListener> clazz : classes) {
			put(clazz, listenerRegistration);
		}
	}

	public <E extends EventListener> void remove(
		Class<E> clazz, ListenerRegistration listenerRegistration) {

		if (clazz == null) {
			throw new NullPointerException("clazz can't be null");
		}

		List<ListenerRegistration> list = map.get(clazz);

		if (list == null) {
			return;
		}

		list.remove(listenerRegistration);
	}

	public void remove(
		List<Class<? extends EventListener>> classes,
		ListenerRegistration listenerRegistration) {

		for (Class<? extends EventListener> clazz : classes) {
			remove(clazz, listenerRegistration);
		}
	}

	private ConcurrentMap<Class<? extends EventListener>, List<ListenerRegistration>> map =
		new ConcurrentHashMap<Class<? extends EventListener>, List<ListenerRegistration>>();

	class ListenerList<R extends EventListener> extends AbstractList<R> {

		ListenerList(List<ListenerRegistration> list) {
			this.list = list;
		}

		@Override
		@SuppressWarnings("unchecked")
		public R get(int index) {
			return (R)list.get(index).getT();
		}

		@Override
		public int size() {
			return list.size();
		}

		private List<ListenerRegistration> list;

	}
}