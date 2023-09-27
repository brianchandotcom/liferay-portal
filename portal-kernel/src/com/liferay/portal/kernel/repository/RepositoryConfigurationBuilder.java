/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Adolfo Pérez
 */
public class RepositoryConfigurationBuilder {

	public RepositoryConfigurationBuilder() {
		this(LanguageUtil.getResourceBundleLoader());
	}

	public RepositoryConfigurationBuilder(
		ResourceBundleLoader resourceBundleLoader, String... names) {

		_resourceBundleLoader = resourceBundleLoader;

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

	private final Collection<RepositoryConfiguration.Parameter> _parameters =
		new ArrayList<>();
	private final ResourceBundleLoader _resourceBundleLoader;

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
			ResourceBundle resourceBundle =
				_resourceBundleLoader.loadResourceBundle(locale);

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