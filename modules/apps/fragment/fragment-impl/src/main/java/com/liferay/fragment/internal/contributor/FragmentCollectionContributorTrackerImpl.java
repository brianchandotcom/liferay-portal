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
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

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

		return _serviceTrackerMap.getService(fragmentCollectionKey);
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

		return new ArrayList<>(_serviceTrackerMap.values());
	}

	@Override
	public Map<String, FragmentEntry> getFragmentEntries() {
		return new HashMap<>(_getFragmentEntries());
	}

	@Override
	public Map<String, FragmentEntry> getFragmentEntries(Locale locale) {
		Collection<FragmentCollectionContributor>
			fragmentCollectionContributors = _serviceTrackerMap.values();

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
			fragmentCollectionContributors = _serviceTrackerMap.values();

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

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, FragmentCollectionContributor.class, null,
			(serviceReference, emitter) -> {
				FragmentCollectionContributor fragmentCollectionContributor =
					bundleContext.getService(serviceReference);

				emitter.emit(
					fragmentCollectionContributor.getFragmentCollectionKey());
			},
			new FragmentCollectionContributorTrackerServiceTrackerCustomizer(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Reference
	protected FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry;

	@Reference
	protected FragmentEntryValidator fragmentEntryValidator;

	private synchronized Map<String, FragmentEntry> _getFragmentEntries() {
		Map<String, FragmentEntry> fragmentEntries = _fragmentEntries;

		if (fragmentEntries == null) {
			fragmentEntries = new HashMap<>();

			for (FragmentCollectionContributor fragmentCollectionContributor :
					_serviceTrackerMap.values()) {

				fragmentEntries.putAll(
					_getFragmentEntries(fragmentCollectionContributor));
			}

			_fragmentEntries = fragmentEntries;
		}

		return new HashMap<>(fragmentEntries);
	}

	private Map<String, FragmentEntry> _getFragmentEntries(
		FragmentCollectionContributor fragmentCollectionContributor) {

		Map<String, FragmentEntry> fragmentEntries = new HashMap<>();

		for (int type : _SUPPORTED_FRAGMENT_TYPES) {
			for (FragmentEntry fragmentEntry :
					fragmentCollectionContributor.getFragmentEntries(type)) {

				if (!_validateFragmentEntry(fragmentEntry)) {
					continue;
				}

				fragmentEntries.put(
					fragmentEntry.getFragmentEntryKey(), fragmentEntry);

				_updateFragmentEntryLinks(fragmentEntry);
			}
		}

		return fragmentEntries;
	}

	private void _updateFragmentEntryLinks(FragmentEntry fragmentEntry) {
		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				fragmentEntry.getFragmentEntryKey());

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			fragmentEntryLink.setCss(fragmentEntry.getCss());
			fragmentEntryLink.setHtml(fragmentEntry.getHtml());
			fragmentEntryLink.setJs(fragmentEntry.getJs());
			fragmentEntryLink.setConfiguration(
				fragmentEntry.getConfiguration());

			_fragmentEntryLinkLocalService.updateFragmentEntryLink(
				fragmentEntryLink);
		}
	}

	private boolean _validateFragmentEntry(FragmentEntry fragmentEntry) {
		try {
			fragmentEntryValidator.validateConfiguration(
				fragmentEntry.getConfiguration());

			fragmentEntryProcessorRegistry.validateFragmentEntryHTML(
				fragmentEntry.getHtml(), fragmentEntry.getConfiguration());

			return true;
		}
		catch (PortalException pe) {
			_log.error("Unable to validate fragment entry", pe);
		}

		return false;
	}

	private static final int[] _SUPPORTED_FRAGMENT_TYPES = {
		FragmentConstants.TYPE_COMPONENT, FragmentConstants.TYPE_SECTION
	};

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentCollectionContributorTrackerImpl.class);

	private volatile Map<String, FragmentEntry> _fragmentEntries;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	private ServiceTrackerMap<String, FragmentCollectionContributor>
		_serviceTrackerMap;

	private class FragmentCollectionContributorTrackerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<FragmentCollectionContributor, FragmentCollectionContributor> {

		public FragmentCollectionContributorTrackerServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public FragmentCollectionContributor addingService(
			ServiceReference<FragmentCollectionContributor> serviceReference) {

			_fragmentEntries = null;

			return _bundleContext.getService(serviceReference);
		}

		@Override
		public void modifiedService(
			ServiceReference<FragmentCollectionContributor> serviceReference,
			FragmentCollectionContributor fragmentCollectionContributor) {
		}

		@Override
		public void removedService(
			ServiceReference<FragmentCollectionContributor> serviceReference,
			FragmentCollectionContributor fragmentCollectionContributor) {

			_fragmentEntries = null;

			_bundleContext.ungetService(serviceReference);
		}

		private final BundleContext _bundleContext;

	}

}