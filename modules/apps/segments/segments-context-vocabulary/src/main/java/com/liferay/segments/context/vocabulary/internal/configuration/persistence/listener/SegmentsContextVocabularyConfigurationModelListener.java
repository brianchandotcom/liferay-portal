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

package com.liferay.segments.context.vocabulary.internal.configuration.persistence.listener;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyConfiguration;

import java.util.Dictionary;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyConfiguration",
	service = ConfigurationModelListener.class
)
public class SegmentsContextVocabularyConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		String entityField = String.valueOf(properties.get("entityField"));

		if (Validator.isNull(entityField)) {
			throw new ConfigurationModelListenerException(
				ResourceBundleUtil.getString(
					_getResourceBundle(),
					"please-enter-a-valid-session-property-name"),
				SegmentsContextVocabularyConfiguration.class, getClass(),
				properties);
		}

		if (_isDefined(pid, entityField)) {
			throw new DuplicatedSegmentsContextVocabularyConfigurationModelListenerException(
				ResourceBundleUtil.getString(
					_getResourceBundle(),
					"this-field-is-already-linked-to-one-vocabulary"),
				SegmentsContextVocabularyConfiguration.class, getClass(),
				properties);
		}
	}

	private ResourceBundle _getResourceBundle() {
		if (_resourceBundle == null) {
			Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

			return ResourceBundleUtil.getBundle(
				"content.Language", locale, getClass());
		}

		return _resourceBundle;
	}

	private boolean _isDefined(
		Configuration configuration, String entityField, String pid) {

		Dictionary<String, Object> properties = configuration.getProperties();

		if (Objects.equals(entityField, properties.get("entityField")) &&
			!Objects.equals(pid, configuration.getPid())) {

			return true;
		}

		return false;
	}

	private boolean _isDefined(String pid, String entityField)
		throws ConfigurationModelListenerException {

		try {
			return Stream.of(
				Optional.ofNullable(
					_configurationAdmin.listConfigurations(
						StringBundler.concat(
							"(", ConfigurationAdmin.SERVICE_FACTORYPID, "=",
							SegmentsContextVocabularyConfiguration.class.
								getCanonicalName(),
							")"))
				).orElse(
					new Configuration[0]
				)
			).filter(
				configuration -> _isDefined(configuration, entityField, pid)
			).findFirst(
			).isPresent();
		}
		catch (Exception exception) {
			throw new ConfigurationModelListenerException(
				exception.getMessage(),
				SegmentsContextVocabularyConfiguration.class, getClass(), null);
		}
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	private ResourceBundle _resourceBundle;

}