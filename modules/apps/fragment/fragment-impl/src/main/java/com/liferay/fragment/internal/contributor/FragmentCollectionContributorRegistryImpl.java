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
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistration;
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.osgi.service.tracker.collections.EagerServiceTrackerCustomizer;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.resource.bundle.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = FragmentCollectionContributorRegistry.class)
public class FragmentCollectionContributorRegistryImpl
	implements FragmentCollectionContributorRegistry {

	@Override
	public FragmentCollectionContributor getFragmentCollectionContributor(
		String fragmentCollectionKey) {

		return _serviceTrackerMap.getService(fragmentCollectionKey);
	}

	@Override
	public List<FragmentCollectionContributor>
		getFragmentCollectionContributors() {

		List<FragmentCollectionContributor> fragmentCollectionContributors =
			new ArrayList<>();

		for (FragmentCollectionContributor fragmentCollectionContributor :
				_serviceTrackerMap.values()) {

			if (MapUtil.isNotEmpty(fragmentCollectionContributor.getNames())) {
				fragmentCollectionContributors.add(
					fragmentCollectionContributor);
			}
		}

		return fragmentCollectionContributors;
	}

	@Override
	public FragmentComposition getFragmentComposition(
		String fragmentCompositionKey) {

		return _fragmentCompositions.get(fragmentCompositionKey);
	}

	@Override
	public Map<String, FragmentEntry> getFragmentEntries() {
		return new HashMap<>(_fragmentEntries);
	}

	@Override
	public Map<String, FragmentEntry> getFragmentEntries(Locale locale) {
		Map<String, FragmentEntry> fragmentEntries = new HashMap<>();

		for (FragmentCollectionContributor fragmentCollectionContributor :
				_serviceTrackerMap.values()) {

			for (int type : _SUPPORTED_FRAGMENT_TYPES) {
				for (FragmentEntry fragmentEntry :
						fragmentCollectionContributor.getFragmentEntries(
							type, locale)) {

					fragmentEntries.put(
						fragmentEntry.getFragmentEntryKey(), fragmentEntry);
				}
			}
		}

		return fragmentEntries;
	}

	@Override
	public FragmentEntry getFragmentEntry(String fragmentEntryKey) {
		if (fragmentEntryKey == null) {
			return null;
		}

		return _fragmentEntries.get(fragmentEntryKey);
	}

	@Override
	public ResourceBundleLoader getResourceBundleLoader() {
		List<ResourceBundleLoader> resourceBundleLoaders = new ArrayList<>();

		for (FragmentCollectionContributor fragmentCollectionContributor :
				_serviceTrackerMap.values()) {

			ResourceBundleLoader resourceBundleLoader =
				fragmentCollectionContributor.getResourceBundleLoader();

			if (resourceBundleLoader != null) {
				resourceBundleLoaders.add(resourceBundleLoader);
			}
		}

		return new AggregateResourceBundleLoader(
			resourceBundleLoaders.toArray(new ResourceBundleLoader[0]));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, FragmentCollectionContributor.class,
			"fragment.collection.key",
			new FragmentCollectionContributorEagerServiceTrackerCustomizer(
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

	private void _updateFragmentEntryLinks(FragmentEntry fragmentEntry) {
		ActionableDynamicQuery actionableDynamicQuery =
			_fragmentEntryLinkLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property property = PropertyFactoryUtil.forName("rendererKey");

				dynamicQuery.add(
					property.eq(fragmentEntry.getFragmentEntryKey()));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<FragmentEntryLink>)
				fragmentEntryLink -> {
					try {
						_fragmentEntryLinkLocalService.updateLatestChanges(
							fragmentEntry, fragmentEntryLink);
					}
					catch (PortalException portalException) {
						_log.error(portalException);
					}
				});

		try {
			actionableDynamicQuery.performActions();
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private boolean _validateFragmentEntry(FragmentEntry fragmentEntry) {
		try {
			fragmentEntryValidator.validateConfiguration(
				fragmentEntry.getConfiguration());
			fragmentEntryValidator.validateTypeOptions(
				fragmentEntry.getType(), fragmentEntry.getTypeOptions());

			fragmentEntryProcessorRegistry.validateFragmentEntryHTML(
				fragmentEntry.getHtml(), fragmentEntry.getConfiguration());

			return true;
		}
		catch (PortalException portalException) {
			_log.error("Unable to validate fragment entry", portalException);
		}

		return false;
	}

	private static final int[] _SUPPORTED_FRAGMENT_TYPES = {
		FragmentConstants.TYPE_COMPONENT, FragmentConstants.TYPE_INPUT,
		FragmentConstants.TYPE_SECTION
	};

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentCollectionContributorRegistryImpl.class);

	private final Map<String, FragmentComposition> _fragmentCompositions =
		new ConcurrentHashMap<>();
	private final Map<String, FragmentEntry> _fragmentEntries =
		new ConcurrentHashMap<>();

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	private final Map<FragmentCollectionContributor, ServiceRegistration<?>>
		_serviceRegistrations = new HashMap<>();
	private ServiceTrackerMap<String, FragmentCollectionContributor>
		_serviceTrackerMap;

	private class FragmentCollectionContributorEagerServiceTrackerCustomizer
		implements EagerServiceTrackerCustomizer
			<FragmentCollectionContributor, FragmentCollectionContributor> {

		@Override
		public FragmentCollectionContributor addingService(
			ServiceReference<FragmentCollectionContributor> serviceReference) {

			FragmentCollectionContributor fragmentCollectionContributor =
				_bundleContext.getService(serviceReference);

			for (FragmentComposition fragmentComposition :
					fragmentCollectionContributor.getFragmentCompositions()) {

				_fragmentCompositions.put(
					fragmentComposition.getFragmentCompositionKey(),
					fragmentComposition);
			}

			for (int type : _SUPPORTED_FRAGMENT_TYPES) {
				for (FragmentEntry fragmentEntry :
						fragmentCollectionContributor.getFragmentEntries(
							type)) {

					if (!_validateFragmentEntry(fragmentEntry)) {
						continue;
					}

					_fragmentEntries.put(
						fragmentEntry.getFragmentEntryKey(), fragmentEntry);

					_updateFragmentEntryLinks(fragmentEntry);
				}
			}

			_serviceRegistrations.put(
				fragmentCollectionContributor,
				_bundleContext.registerService(
					FragmentCollectionContributorRegistration.class,
					new FragmentCollectionContributorRegistration() {
					},
					MapUtil.singletonDictionary(
						"fragment.collection.key",
						serviceReference.getProperty(
							"fragment.collection.key"))));

			return fragmentCollectionContributor;
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

			ServiceRegistration<?> serviceRegistration =
				_serviceRegistrations.remove(fragmentCollectionContributor);

			serviceRegistration.unregister();

			for (FragmentComposition fragmentComposition :
					fragmentCollectionContributor.getFragmentCompositions()) {

				_fragmentCompositions.remove(
					fragmentComposition.getFragmentCompositionKey());
			}

			for (int type : _SUPPORTED_FRAGMENT_TYPES) {
				for (FragmentEntry fragmentEntry :
						fragmentCollectionContributor.getFragmentEntries(
							type)) {

					_fragmentEntries.remove(
						fragmentEntry.getFragmentEntryKey());
				}
			}

			_bundleContext.ungetService(serviceReference);
		}

		private FragmentCollectionContributorEagerServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private final BundleContext _bundleContext;

	}

}