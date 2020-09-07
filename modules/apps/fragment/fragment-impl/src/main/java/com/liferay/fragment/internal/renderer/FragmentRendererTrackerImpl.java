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

package com.liferay.fragment.internal.renderer;

import com.liferay.fragment.exception.FragmentEntryConfigurationException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.renderer.constants.FragmentRendererConstants;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = FragmentRendererTracker.class)
public class FragmentRendererTrackerImpl implements FragmentRendererTracker {

	@Override
	public FragmentRenderer getFragmentRenderer(String key) {
		if (Validator.isNull(key)) {
			return null;
		}

		return _serviceTrackerMap.getService(key);
	}

	@Override
	public List<FragmentRenderer> getFragmentRenderers() {
		return new ArrayList<>(_serviceTrackerMap.values());
	}

	@Override
	public List<FragmentRenderer> getFragmentRenderers(int type) {
		Collection<FragmentRenderer> fragmentRenderers =
			_serviceTrackerMap.values();

		Stream<FragmentRenderer> stream = fragmentRenderers.stream();

		return stream.filter(
			fragmentRenderer -> fragmentRenderer.getType() == type
		).collect(
			Collectors.toList()
		);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, FragmentRenderer.class, null,
			(serviceReference, emitter) -> {
				FragmentRenderer fragmentRenderer = bundleContext.getService(
					serviceReference);

				emitter.emit(fragmentRenderer.getKey());
			},
			new FragmentRendererTrackerServiceTrackerCustomizer(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentRendererTrackerImpl.class);

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryValidator _fragmentEntryValidator;

	private ServiceTrackerMap<String, FragmentRenderer> _serviceTrackerMap;

	private class FragmentRendererTrackerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<FragmentRenderer, FragmentRenderer> {

		public FragmentRendererTrackerServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public FragmentRenderer addingService(
			ServiceReference<FragmentRenderer> serviceReference) {

			FragmentRenderer fragmentRenderer = _bundleContext.getService(
				serviceReference);

			if (!_isValidFragmentCollectionKey(fragmentRenderer)) {
				_log.error("Fragment renderer collection key is invalid");

				return null;
			}

			try {
				FragmentEntryLink fragmentEntryLink =
					_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

				_fragmentEntryValidator.validateConfiguration(
					fragmentRenderer.getConfiguration(
						new DefaultFragmentRendererContext(fragmentEntryLink)));

				return fragmentRenderer;
			}
			catch (FragmentEntryConfigurationException
						fragmentEntryConfigurationException) {

				_log.error(
					String.format(
						"Fragment renderer with collection key %s and label " +
							"%s could not be registered due to invalid " +
								"configuration",
						fragmentRenderer.getCollectionKey(),
						fragmentRenderer.getLabel(
							LocaleUtil.getMostRelevantLocale())),
					fragmentEntryConfigurationException);
			}

			return null;
		}

		@Override
		public void modifiedService(
			ServiceReference<FragmentRenderer> serviceReference,
			FragmentRenderer fragmentRenderer) {
		}

		@Override
		public void removedService(
			ServiceReference<FragmentRenderer> serviceReference,
			FragmentRenderer fragmentRenderer) {

			_bundleContext.ungetService(serviceReference);
		}

		private boolean _isValidFragmentCollectionKey(
			FragmentRenderer fragmentRenderer) {

			String fragmentCollectionKey = fragmentRenderer.getCollectionKey();

			if (Validator.isNull(fragmentCollectionKey) &&
				!StringUtil.equals(
					fragmentRenderer.getKey(),
					FragmentRendererConstants.
						FRAGMENT_ENTRY_FRAGMENT_RENDERER_KEY) &&
				!StringUtil.equals(
					fragmentRenderer.getKey(),
					FragmentRendererConstants.
						FRAGMENT_ENTRY_FRAGMENT_RENDERER_KEY_REACT)) {

				return false;
			}

			if (Validator.isNotNull(fragmentCollectionKey) &&
				fragmentCollectionKey.contains(StringPool.SPACE)) {

				return false;
			}

			return true;
		}

		private final BundleContext _bundleContext;

	}

}