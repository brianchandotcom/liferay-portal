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

package com.liferay.fragment.internal.contributor;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true, service = FragmentCollectionContributorTracker.class
)
public class FragmentCollectionContributorTrackerImpl
	implements FragmentCollectionContributorTracker {

	@Override
	public FragmentCollectionContributor getFragmentCollectionContributor(
		String fragmentCollectionKey) {

		return _fragmentCollectionContributorsMap.get(fragmentCollectionKey);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by #getFragmentEntries
	 */
	@Deprecated
	@Override
	public Map<String, FragmentEntry>
		getFragmentCollectionContributorEntries() {

		return getFragmentEntries();
	}

	@Override
	public List<FragmentCollectionContributor>
		getFragmentCollectionContributors() {

		return new ArrayList<>(_fragmentCollectionContributorsMap.values());
	}

	@Override
	public Map<String, FragmentEntry> getFragmentEntries() {
		return new HashMap<>(_getFragmentEntries());
	}

	@Override
	public Map<String, FragmentEntry> getFragmentEntries(Locale locale) {
		Collection<FragmentCollectionContributor>
			fragmentCollectionContributors =
				_fragmentCollectionContributorsMap.values();

		Stream<FragmentCollectionContributor> stream =
			fragmentCollectionContributors.stream();

		return stream.map(
			fragmentCollectionContributor -> {
				Map<String, FragmentEntry> fragmentEntries = new HashMap<>();

				for (int type : _SUPPORTED_FRAGMENT_TYPES) {
					for (FragmentEntry fragmentEntry :
							fragmentCollectionContributor.getFragmentEntries(
								type, locale)) {

						fragmentEntries.put(
							fragmentEntry.getFragmentEntryKey(), fragmentEntry);
					}
				}

				return fragmentEntries;
			}
		).flatMap(
			fragmentEntriesMap -> {
				Collection<FragmentEntry> fragmentEntries =
					fragmentEntriesMap.values();

				return fragmentEntries.stream();
			}
		).collect(
			Collectors.toMap(
				FragmentEntry::getFragmentEntryKey,
				fragmentEntry -> fragmentEntry)
		);
	}

	@Override
	public FragmentEntry getFragmentEntry(String fragmentEntryKey) {
		Map<String, FragmentEntry> fragmentEntriesMap = _getFragmentEntries();

		return fragmentEntriesMap.get(fragmentEntryKey);
	}

	public ResourceBundleLoader getResourceBundleLoader() {
		Collection<FragmentCollectionContributor>
			fragmentCollectionContributors =
				_fragmentCollectionContributorsMap.values();

		Stream<FragmentCollectionContributor> stream =
			fragmentCollectionContributors.stream();

		return new AggregateResourceBundleLoader(
			stream.map(
				FragmentCollectionContributor::getResourceBundleLoader
			).filter(
				Objects::nonNull
			).toArray(
				ResourceBundleLoader[]::new
			));
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setFragmentCollectionContributor(
		FragmentCollectionContributor fragmentCollectionContributor) {

		_fragmentCollectionContributorsMap.put(
			fragmentCollectionContributor.getFragmentCollectionKey(),
			fragmentCollectionContributor);

		Map<String, FragmentEntry> fragmentEntries = _fragmentEntries;

		if (fragmentEntries != null) {
			_populateFragmentEntries(
				_fragmentEntries, fragmentCollectionContributor);
		}
	}

	protected void unsetFragmentCollectionContributor(
		FragmentCollectionContributor fragmentCollectionContributor) {

		Map<String, FragmentEntry> fragmentEntries = _fragmentEntries;

		if (fragmentEntries != null) {
			for (int type : _SUPPORTED_FRAGMENT_TYPES) {
				for (FragmentEntry fragmentEntry :
						fragmentCollectionContributor.getFragmentEntries(
							type)) {

					fragmentEntries.remove(fragmentEntry.getFragmentEntryKey());
				}
			}
		}

		_fragmentCollectionContributorsMap.remove(
			fragmentCollectionContributor.getFragmentCollectionKey());
	}

	private synchronized Map<String, FragmentEntry> _getFragmentEntries() {
		if (_fragmentEntries != null) {
			return _fragmentEntries;
		}

		_fragmentEntries = new ConcurrentHashMap<>();

		for (FragmentCollectionContributor fragmentCollectionContributor :
				_fragmentCollectionContributorsMap.values()) {

			_populateFragmentEntries(
				_fragmentEntries, fragmentCollectionContributor);
		}

		return _fragmentEntries;
	}

	private void _populateFragmentEntries(
		Map<String, FragmentEntry> fragmentEntries,
		FragmentCollectionContributor fragmentCollectionContributor) {

		for (int type : _SUPPORTED_FRAGMENT_TYPES) {
			for (FragmentEntry fragmentEntry :
					fragmentCollectionContributor.getFragmentEntries(type)) {

				fragmentEntries.put(
					fragmentEntry.getFragmentEntryKey(), fragmentEntry);
			}
		}
	}

	private static final int[] _SUPPORTED_FRAGMENT_TYPES = {
		FragmentConstants.TYPE_COMPONENT, FragmentConstants.TYPE_SECTION
	};

	private final Map<String, FragmentCollectionContributor>
		_fragmentCollectionContributorsMap = new ConcurrentHashMap<>();
	private volatile Map<String, FragmentEntry> _fragmentEntries;

}