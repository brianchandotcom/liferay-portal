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

package com.liferay.portal.kernel.repository;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.Portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Adolfo Pérez
 */
public class RepositoryConfigurationBuilder {

	public RepositoryConfigurationBuilder() {
		this(Portal.class, "content.Language");
	}

	public RepositoryConfigurationBuilder(
		Class<?> clazz, String resourceBundleBaseName, String... names) {

		_clazz = clazz;
		_resourceBundleBaseName = resourceBundleBaseName;

		for (String name : names) {
			addParameter(name);
		}
	}

	public RepositoryConfigurationBuilder addParameter(String name) {
		String labelKey = HtmlUtil.escape(
			StringUtil.replace(
				StringUtil.toLowerCase(name), CharPool.UNDERLINE,
				CharPool.DASH));

		return addParameter(name, labelKey);
	}

	public RepositoryConfigurationBuilder addParameter(
		String labelKey, String name) {

		_parameters.add(new ParameterImpl(labelKey, name));

		return this;
	}

	public RepositoryConfiguration build() {
		return new RepositoryConfigurationImpl(new ArrayList<>(_parameters));
	}

	private final Class<?> _clazz;
	private final Collection<RepositoryConfiguration.Parameter> _parameters =
		new ArrayList<>();
	private final String _resourceBundleBaseName;

	private static class RepositoryConfigurationImpl
		implements RepositoryConfiguration {

		public RepositoryConfigurationImpl(Collection<Parameter> parameters) {
			_parameters = parameters;
		}

		@Override
		public Collection<Parameter> getParameters() {
			return _parameters;
		}

		private final Collection<Parameter> _parameters;

	}

	private class ParameterImpl implements RepositoryConfiguration.Parameter {

		public ParameterImpl(String name, String labelKey) {
			_name = name;
			_labelKey = labelKey;
		}

		@Override
		public String getLabel(Locale locale) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				_resourceBundleBaseName, locale, _clazz);

			return LanguageUtil.get(resourceBundle, _labelKey);
		}

		@Override
		public String getName() {
			return _name;
		}

		private final String _labelKey;
		private final String _name;

	}

}