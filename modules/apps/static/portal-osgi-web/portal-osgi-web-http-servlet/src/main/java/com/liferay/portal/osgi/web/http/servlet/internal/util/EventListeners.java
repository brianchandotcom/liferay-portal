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

package com.liferay.portal.osgi.web.http.servlet.internal.util;

import com.liferay.portal.osgi.web.http.servlet.internal.registration.ListenerRegistration;

import java.util.AbstractList;
import java.util.Collections;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Raymond Augé
 */
public class EventListeners {

	public void clear() {
		_map.clear();
	}

	public <E extends EventListener> List<E> get(Class<E> clazz) {
		if (clazz == null) {
			throw new NullPointerException("clazz can not be null");
		}

		List<ListenerRegistration> listenerRegistrations = _map.get(clazz);

		if (listenerRegistrations == null) {
			return Collections.emptyList();
		}

		return new ListenerList<>(listenerRegistrations);
	}

	public <E extends EventListener> void put(
		Class<E> clazz, ListenerRegistration listenerRegistration) {

		if (clazz == null) {
			throw new NullPointerException("clazz can not be null");
		}

		List<ListenerRegistration> list = _map.get(clazz);

		if (list == null) {
			List<ListenerRegistration> newList = new CopyOnWriteArrayList<>();

			list = _map.putIfAbsent(clazz, newList);

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
			throw new NullPointerException("clazz can not be null");
		}

		List<ListenerRegistration> list = _map.get(clazz);

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

	private final ConcurrentMap
		<Class<? extends EventListener>, List<ListenerRegistration>> _map =
			new ConcurrentHashMap<>();

	private static class ListenerList<R extends EventListener>
		extends AbstractList<R> {

		public ListenerList(List<ListenerRegistration> listenerRegistrations) {
			_listenerRegistrations = listenerRegistrations;
		}

		@Override
		@SuppressWarnings("unchecked")
		public R get(int index) {
			ListenerRegistration listenerRegistration =
				_listenerRegistrations.get(index);

			return (R)listenerRegistration.getT();
		}

		@Override
		public int size() {
			return _listenerRegistrations.size();
		}

		private final List<ListenerRegistration> _listenerRegistrations;

	}

}