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

package com.liferay.data.engine.web.internal.servlet.data.fetcher;

import com.liferay.data.engine.web.internal.graphql.model.LocalizedValueType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import graphql.GraphQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Leonardo Barros
 */
public abstract class DEBaseDataFetcher {

	protected String getActionMessage(String languageId, String... actionIds) {
		ResourceBundle resourceBundle = getResourceBundle(languageId);

		return Stream.of(
			actionIds
		).map(
			action -> LanguageUtil.get(resourceBundle, "action." + action)
		).collect(
			Collectors.joining(StringPool.COMMA_AND_SPACE)
		);
	}

	protected Map<String, String> getLocalizedValues(
		List<Map<String, Object>> values) {

		if (values == null) {
			return null;
		}

		Stream<Map<String, Object>> stream = values.stream();

		return stream.collect(
			Collectors.toMap(
				entry -> MapUtil.getString(entry, "key"),
				entry -> MapUtil.getString(entry, "value")));
	}

	protected List<LocalizedValueType> getLocalizedValuesType(
		Map<String, String> values) {

		if (values == null) {
			return null;
		}

		Set<Map.Entry<String, String>> set = values.entrySet();

		Stream<Map.Entry<String, String>> stream = set.stream();

		return stream.map(
			this::map
		).collect(
			Collectors.toList()
		);
	}

	protected String getMessage(String languageId, String languageKey) {
		ResourceBundle resourceBundle = getResourceBundle(languageId);

		return LanguageUtil.get(resourceBundle, languageKey);
	}

	protected String getMessage(
		String languageId, String languageKey, Object... parameters) {

		ResourceBundle resourceBundle = getResourceBundle(languageId);

		return LanguageUtil.format(
			resourceBundle, languageKey, parameters, false);
	}

	protected abstract Portal getPortal();

	protected ResourceBundle getResourceBundle(String languageId) {
		List<ResourceBundle> resourceBundles = new ArrayList<>();

		Portal portal = getPortal();

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		ResourceBundle portalResourceBundle = portal.getResourceBundle(locale);

		resourceBundles.add(portalResourceBundle);

		_collectResourceBundles(getClass(), resourceBundles, locale);

		ResourceBundle[] resourceBundlesArray = resourceBundles.toArray(
			new ResourceBundle[resourceBundles.size()]);

		return new AggregateResourceBundle(resourceBundlesArray);
	}

	protected String getRolesMessage(String languageId, String... roleNames) {
		ResourceBundle resourceBundle = getResourceBundle(languageId);

		return Stream.of(
			roleNames
		).map(
			roleName -> LanguageUtil.get(resourceBundle, roleName)
		).collect(
			Collectors.joining(StringPool.COMMA_AND_SPACE)
		);
	}

	protected void handleErrorMessage(String errorMessage) {
		throw new GraphQLException(StringPool.DOUBLE_DOLLAR + errorMessage);
	}

	protected LocalizedValueType map(Map.Entry<String, String> entry) {
		return new LocalizedValueType(entry.getKey(), entry.getValue());
	}

	private void _collectResourceBundles(
		Class<?> clazz, List<ResourceBundle> resourceBundles, Locale locale) {

		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			_collectResourceBundles(interfaceClass, resourceBundles, locale);
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, clazz.getClassLoader());

		if (resourceBundle != null) {
			resourceBundles.add(resourceBundle);
		}
	}

}