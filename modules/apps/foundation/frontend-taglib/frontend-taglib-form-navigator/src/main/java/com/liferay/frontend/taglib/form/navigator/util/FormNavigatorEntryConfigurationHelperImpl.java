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

package com.liferay.frontend.taglib.form.navigator.util;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntryConfigurationHelper;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = FormNavigatorEntryConfigurationHelper.class)
public class FormNavigatorEntryConfigurationHelperImpl
	implements FormNavigatorEntryConfigurationHelper {

	@Override
	public <T> Optional<List<FormNavigatorEntry<T>>> getFormNavigatorEntries(
		String formNavigatorId, String categoryKey, String variant) {

		return _formNavigatorEntryConfigurationRetriever.
			getFormNavigatorEntryKeys(formNavigatorId, categoryKey, variant).
			map(keys -> _convertKeysToServices(formNavigatorId, keys));
	}

	private <T> List<FormNavigatorEntry<T>> _convertKeysToServices(
		String formNavigatorId, List<String> formNavigatorEntryKeys) {

		return formNavigatorEntryKeys.stream().map(
			key -> this.<T>_getFormNavigatorEntry(key, formNavigatorId)
		).collect(Collectors.toList());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_formNavigatorEntriesMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, FormNavigatorEntry.class, null,
			(serviceReference, emitter) -> {

				FormNavigatorEntry service = bundleContext.getService(
					serviceReference);

				emitter.emit(
					_getKey(
						service.getKey(), service.getFormNavigatorId()));

				bundleContext.ungetService(serviceReference);
			});
	}

	@Deactivate
	protected void deactivate() {
		_formNavigatorEntriesMap.close();
	}

	private <T> FormNavigatorEntry<T> _getFormNavigatorEntry(
		String key, String formNavigatorId) {

		List<FormNavigatorEntry> formNavigatorEntries =
			(List)_formNavigatorEntriesMap.getService(
				_getKey(key, formNavigatorId));

		if (formNavigatorEntries.isEmpty()) {
			throw new RuntimeException("fix this");
		}

		return formNavigatorEntries.get(0);
	}

	private String _getKey(String key, String formNavigatorId) {
		return formNavigatorId + StringPool.PERIOD + key;
	}

	private ServiceTrackerMap<String, List<FormNavigatorEntry>>
		_formNavigatorEntriesMap;

	@Reference
	private FormNavigatorEntryConfigurationRetriever
		_formNavigatorEntryConfigurationRetriever;

}