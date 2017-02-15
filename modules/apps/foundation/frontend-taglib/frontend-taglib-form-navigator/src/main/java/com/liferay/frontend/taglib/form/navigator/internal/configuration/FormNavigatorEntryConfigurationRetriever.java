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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Alejandro Tardín
 */
@Component(service = FormNavigatorEntryConfigurationRetriever.class)
public class FormNavigatorEntryConfigurationRetriever {

	public Optional<Set<String>> getFormNavigatorEntryKeys(
		String formNavigatorId, String categoryKey, String context) {

		return _getFormNavigatorEntryConfigurationParsers(
			formNavigatorId).stream().map(
				formNavigatorEntryConfigurationParser ->
					formNavigatorEntryConfigurationParser.
						getFormNavigatorEntryKeys(categoryKey, context)).reduce(
				Optional.empty(), this::_mergeFormNavigatorEntryKeys);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	public void setFormNavigatorEntryConfigurationParser(
		FormNavigatorEntryConfigurationParser
			formNavigatorEntryConfigurationParser) {

		List<FormNavigatorEntryConfigurationParser>
			formNavigatorEntryConfigurationParsers =
				_getFormNavigatorEntryConfigurationParsers(
					formNavigatorEntryConfigurationParser.getFormNavigatorId());

		formNavigatorEntryConfigurationParsers.add(
			formNavigatorEntryConfigurationParser);
	}

	public void unsetFormNavigatorEntryConfigurationParser(
		FormNavigatorEntryConfigurationParser
			formNavigatorEntryConfigurationParser) {

		List<FormNavigatorEntryConfigurationParser>
			formNavigatorEntryConfigurationParsers =
				_getFormNavigatorEntryConfigurationParsers(
					formNavigatorEntryConfigurationParser.getFormNavigatorId());

		formNavigatorEntryConfigurationParsers.remove(
			formNavigatorEntryConfigurationParser);
	}

	private List<FormNavigatorEntryConfigurationParser>
		_getFormNavigatorEntryConfigurationParsers(String formNavigatorId) {

		return _formNavigatorEntryConfigurationParserMap.computeIfAbsent(
			formNavigatorId, key -> new CopyOnWriteArrayList<>());
	}

	private Optional<Set<String>> _mergeFormNavigatorEntryKeys(
		Optional<Set<String>> previous, Optional<Set<String>> current) {

		if (current.isPresent()) {
			return current;
		}

		return previous;
	}

	private volatile Map<String, List<FormNavigatorEntryConfigurationParser>>
		_formNavigatorEntryConfigurationParserMap = new ConcurrentHashMap<>();

}