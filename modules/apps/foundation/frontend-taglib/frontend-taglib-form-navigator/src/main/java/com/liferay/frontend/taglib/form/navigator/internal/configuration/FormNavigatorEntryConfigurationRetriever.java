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

package com.liferay.frontend.taglib.form.navigator.internal.configuration;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alejandro Tardín
 */
@Component(service = FormNavigatorEntryConfigurationRetriever.class)
public class FormNavigatorEntryConfigurationRetriever {

	public Optional<Set<String>> getFormNavigatorEntryKeys(
		String formNavigatorId, String categoryKey, String context) {

		return _serviceTrackerMap.getService(formNavigatorId).stream().map(
			formNavigatorEntryConfigurationParser ->
				formNavigatorEntryConfigurationParser.getFormNavigatorEntryKeys(
					categoryKey, context)).reduce(
				Optional.empty(), this::_mergeFormNavigatorEntryKeys);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, FormNavigatorEntryConfigurationParser.class, null,
			(serviceReference, emitter) -> {
				FormNavigatorEntryConfigurationParser
					formNavigatorEntryConfigurationParser =
						bundleContext.getService(serviceReference);

				emitter.emit(
					formNavigatorEntryConfigurationParser.getFormNavigatorId());

				bundleContext.ungetService(serviceReference);
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private Optional<Set<String>> _mergeFormNavigatorEntryKeys(
		Optional<Set<String>> previous, Optional<Set<String>> current) {

		if (current.isPresent()) {
			return current;
		}

		return previous;
	}

	private ServiceTrackerMap
		<String, List<FormNavigatorEntryConfigurationParser>>
			_serviceTrackerMap;

}