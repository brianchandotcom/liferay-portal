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
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.model.FragmentEntryLinkTable;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.string.CharPool;
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
@Component(service = FragmentCollectionContributorRegistry.class)
public class FragmentCollectionContributorRegistryImpl
	implements FragmentCollectionContributorRegistry {

	@Override
	public FragmentCollectionContributor getFragmentCollectionContributor(
		String fragmentCollectionKey) {

		FragmentCollectionBag fragmentCollectionBag =
			_serviceTrackerMap.getService(fragmentCollectionKey);

		if (fragmentCollectionBag == null) {
			return null;
		}

		return fragmentCollectionBag._fragmentCollectionContributor;
	}

	@Override
	public List<FragmentCollectionContributor>
		getFragmentCollectionContributors() {

		List<FragmentCollectionContributor> fragmentCollectionContributors =
			new ArrayList<>();

		for (FragmentCollectionBag fragmentCollectionBag :
				_serviceTrackerMap.values()) {

			FragmentCollectionContributor fragmentCollectionContributor =
				fragmentCollectionBag._fragmentCollectionContributor;

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

		int index = fragmentCompositionKey.indexOf("-composition-");

		if (index == -1) {
			return null;
		}

		FragmentCollectionBag fragmentCollectionBag =
			_serviceTrackerMap.getService(
				fragmentCompositionKey.substring(0, index));

		if (fragmentCollectionBag == null) {
			return null;
		}

		Map<String, FragmentComposition> fragmentCompostions =
			fragmentCollectionBag._fragmentCompostions;

		return fragmentCompostions.get(fragmentCompositionKey);
	}

	@Override
	public Map<String, FragmentEntry> getFragmentEntries() {
		Map<String, FragmentEntry> fragmentEntries = new HashMap<>();

		for (FragmentCollectionBag fragmentCollectionBag :
				_serviceTrackerMap.values()) {

			fragmentEntries.putAll(fragmentCollectionBag._fragmentEntries);
		}

		return fragmentEntries;
	}

	@Override
	public Map<String, FragmentEntry> getFragmentEntries(Locale locale) {
		Map<String, FragmentEntry> fragmentEntries = new HashMap<>();

		for (FragmentCollectionBag fragmentCollectionBag :
				_serviceTrackerMap.values()) {

			FragmentCollectionContributor fragmentCollectionContributor =
				fragmentCollectionBag._fragmentCollectionContributor;

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

		int index = fragmentEntryKey.indexOf(CharPool.DASH);

		if (index == -1) {
			return null;
		}

		FragmentCollectionBag fragmentCollectionBag =
			_serviceTrackerMap.getService(fragmentEntryKey.substring(0, index));

		if (fragmentCollectionBag == null) {
			return null;
		}

		Map<String, FragmentEntry> fragmentEntries =
			fragmentCollectionBag._fragmentEntries;

		return fragmentEntries.get(fragmentEntryKey);
	}

	@Override
	public ResourceBundleLoader getResourceBundleLoader() {
		List<ResourceBundleLoader> resourceBundleLoaders = new ArrayList<>();

		for (FragmentCollectionBag fragmentCollectionBag :
				_serviceTrackerMap.values()) {

			FragmentCollectionContributor fragmentCollectionContributor =
				fragmentCollectionBag._fragmentCollectionContributor;

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
			new FragmentCollectionContributorServiceTrackerCustomizer(
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

	private List<FragmentEntryLink> _getFragmentEntryLinks(
		FragmentEntry fragmentEntry) {

		DSLQuery dslQuery = DSLQueryFactoryUtil.select(
			FragmentEntryLinkTable.INSTANCE
		).from(
			FragmentEntryLinkTable.INSTANCE
		).where(
			FragmentEntryLinkTable.INSTANCE.rendererKey.eq(
				fragmentEntry.getFragmentEntryKey()
			).and(
				Predicate.withParentheses(
					DSLFunctionFactoryUtil.castClobText(
						FragmentEntryLinkTable.INSTANCE.configuration
					).neq(
						fragmentEntry.getConfiguration()
					).or(
						DSLFunctionFactoryUtil.castClobText(
							FragmentEntryLinkTable.INSTANCE.css
						).neq(
							fragmentEntry.getCss()
						)
					).or(
						DSLFunctionFactoryUtil.castClobText(
							FragmentEntryLinkTable.INSTANCE.html
						).neq(
							fragmentEntry.getHtml()
						)
					).or(
						DSLFunctionFactoryUtil.castClobText(
							FragmentEntryLinkTable.INSTANCE.js
						).neq(
							fragmentEntry.getJs()
						)
					).or(
						FragmentEntryLinkTable.INSTANCE.type.neq(
							fragmentEntry.getType())
					))
			)
		).orderBy(
			FragmentEntryLinkTable.INSTANCE.fragmentEntryLinkId.ascending()
		);

		return _fragmentEntryLinkLocalService.dslQuery(dslQuery);
	}

	private void _updateFragmentEntryLinks(FragmentEntry fragmentEntry) {
		List<FragmentEntryLink> fragmentEntryLinks = _getFragmentEntryLinks(
			fragmentEntry);

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			try {
				_fragmentEntryLinkLocalService.updateLatestChanges(
					fragmentEntry, fragmentEntryLink);
			}
			catch (PortalException portalException) {
				_log.error(portalException);
			}
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

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	private ServiceTrackerMap<String, FragmentCollectionBag> _serviceTrackerMap;

	private static class FragmentCollectionBag {

		private FragmentCollectionBag(
			FragmentCollectionContributor fragmentCollectionContributor,
			Map<String, FragmentComposition> fragmentCompostions,
			Map<String, FragmentEntry> fragmentEntries) {

			_fragmentCollectionContributor = fragmentCollectionContributor;
			_fragmentCompostions = fragmentCompostions;
			_fragmentEntries = fragmentEntries;
		}

		private final FragmentCollectionContributor
			_fragmentCollectionContributor;
		private final Map<String, FragmentComposition> _fragmentCompostions;
		private final Map<String, FragmentEntry> _fragmentEntries;

	}

	private class FragmentCollectionContributorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<FragmentCollectionContributor, FragmentCollectionBag> {

		@Override
		public FragmentCollectionBag addingService(
			ServiceReference<FragmentCollectionContributor> serviceReference) {

			FragmentCollectionContributor fragmentCollectionContributor =
				_bundleContext.getService(serviceReference);

			Map<String, FragmentComposition> fragmentCompositions =
				new HashMap<>();
			Map<String, FragmentEntry> fragmentEntries = new HashMap<>();

			for (FragmentComposition fragmentComposition :
					fragmentCollectionContributor.getFragmentCompositions()) {

				fragmentCompositions.put(
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

					fragmentEntries.put(
						fragmentEntry.getFragmentEntryKey(), fragmentEntry);

					_updateFragmentEntryLinks(fragmentEntry);
				}
			}

			return new FragmentCollectionBag(
				fragmentCollectionContributor, fragmentCompositions,
				fragmentEntries);
		}

		@Override
		public void modifiedService(
			ServiceReference<FragmentCollectionContributor> serviceReference,
			FragmentCollectionBag fragmentCollectionBag) {
		}

		@Override
		public void removedService(
			ServiceReference<FragmentCollectionContributor> serviceReference,
			FragmentCollectionBag fragmentCollectionBag) {

			_bundleContext.ungetService(serviceReference);
		}

		private FragmentCollectionContributorServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private final BundleContext _bundleContext;

	}

}