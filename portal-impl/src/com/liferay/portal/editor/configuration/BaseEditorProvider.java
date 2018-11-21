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

package com.liferay.portal.editor.configuration;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.util.StringPlus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * @author Sergio González
 * @author Preston Crary
 */
public abstract class BaseEditorProvider<T> {

	public BaseEditorProvider(Class<T> editorContributorClass) {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			editorContributorClass,
			new EditorContributorServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void destroy() {
		_serviceTracker.close();
	}

	protected void visitEditorContributors(
		Consumer<T> consumer, String portletName, String editorConfigKey,
		String editorName) {

		List<EditorContributorProvider<T>> editorContributorProviders =
			_editorContributorsProviders.get();

		for (EditorContributorProvider<T> editorContributorProvider :
				editorContributorProviders) {

			if (editorContributorProvider.matches(
					portletName, editorConfigKey, editorName)) {

				consumer.accept(editorContributorProvider.get());
			}
		}
	}

	private final AtomicReference<List<EditorContributorProvider<T>>>
		_editorContributorsProviders = new AtomicReference<>();
	private final ServiceTracker<T, ?> _serviceTracker;

	private static class EditorContributorProvider<T>
		implements Comparable<EditorContributorProvider<T>> {

		@Override
		public int compareTo(
			EditorContributorProvider<T> editorContributorProvider) {

			int result = Integer.compare(
				_priority, editorContributorProvider._priority);

			if (result == 0) {
				return Integer.compare(
					editorContributorProvider._serviceRanking, _serviceRanking);
			}

			return result;
		}

		public T get() {
			return _editorContributor;
		}

		public boolean matches(
			String portletName, String editorConfigKey, String editorName) {

			if (_matches(portletName, _portletNames) &&
				_matches(editorConfigKey, _editorConfigKeys) &&
				_matches(editorName, _editorNames)) {

				return true;
			}

			return false;
		}

		private EditorContributorProvider(
			T editorContributor, List<String> portletNames,
			List<String> editorConfigKeys, List<String> editorNames,
			int serviceRanking) {

			_editorContributor = editorContributor;

			_portletNames = _toSet(portletNames);
			_editorConfigKeys = _toSet(editorConfigKeys);
			_editorNames = _toSet(editorNames);

			_serviceRanking = serviceRanking;

			_priority = _getPriority();
		}

		private int _getPriority() {
			int index = 0;

			if (!_portletNames.isEmpty()) {
				index |= 0b100;
			}

			if (!_editorConfigKeys.isEmpty()) {
				index |= 0b010;
			}

			if (!_editorNames.isEmpty()) {
				index |= 0b001;
			}

			return _PRIORITIES[index];
		}

		private boolean _matches(String name, Set<String> names) {
			if (names.isEmpty() || names.contains(name)) {
				return true;
			}

			return false;
		}

		private Set<String> _toSet(List<String> names) {
			if (names.isEmpty()) {
				return Collections.emptySet();
			}

			return new HashSet<>(names);
		}

		/**
		 * Use 3 bits to represent the appearance of portletName (highest bit),
		 * editorConfig (middle bit) and editorName (lowest bit). 1 means have
		 * it, 0 means don't have it.
		 */
		private static final int[] _PRIORITIES = {
			0b111, 0b110, 0b100, 0b010, 0b101, 0b011, 0b001, 0b000
		};

		private final Set<String> _editorConfigKeys;
		private final T _editorContributor;
		private final Set<String> _editorNames;
		private final Set<String> _portletNames;
		private final int _priority;
		private final int _serviceRanking;

	}

	private class EditorContributorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<T, T> {

		@Override
		public T addingService(ServiceReference<T> serviceReference) {
			Registry registry = RegistryUtil.getRegistry();

			T editorOptionsContributor = registry.getService(serviceReference);

			List<String> portletNames = StringPlus.asList(
				serviceReference.getProperty("javax.portlet.name"));
			List<String> editorConfigKeys = StringPlus.asList(
				serviceReference.getProperty("editor.config.key"));
			List<String> editorNames = StringPlus.asList(
				serviceReference.getProperty("editor.name"));

			int serviceRanking = GetterUtil.getInteger(
				serviceReference.getProperty("service.ranking"));

			EditorContributorProvider<T> editorContributorProvider =
				new EditorContributorProvider<>(
					editorOptionsContributor, portletNames, editorConfigKeys,
					editorNames, serviceRanking);

			_editorContributorsProviders.updateAndGet(
				editorContributorProviders -> {
					if (editorContributorProviders == null) {
						editorContributorProviders = new ArrayList<>();
					}
					else {
						editorContributorProviders = new ArrayList<>(
							editorContributorProviders);
					}

					int index = Collections.binarySearch(
						editorContributorProviders, editorContributorProvider,
						Comparator.reverseOrder());

					if (index < 0) {
						index = -index - 1;
					}

					editorContributorProviders.add(
						index, editorContributorProvider);

					return editorContributorProviders;
				});

			return editorOptionsContributor;
		}

		@Override
		public void modifiedService(
			ServiceReference<T> serviceReference, T editorOptionsContributor) {

			removedService(serviceReference, editorOptionsContributor);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<T> serviceReference, T editorContributor) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_editorContributorsProviders.updateAndGet(
				editorContributorProviders -> {
					editorContributorProviders = new ArrayList<>(
						editorContributorProviders);

					editorContributorProviders.removeIf(
						editorContributorProvider ->
							editorContributorProvider._editorContributor ==
								editorContributor);

					if (editorContributorProviders.isEmpty()) {
						return null;
					}

					return editorContributorProviders;
				});
		}

	}

}