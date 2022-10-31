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
import com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyCompanyConfiguration;

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
 * @author Yurena Cabrera
 */
@Component(
	property = "model.class.name=com.liferay.segments.context.vocabulary.internal.configuration.SegmentsContextVocabularyCompanyConfiguration",
	service = ConfigurationModelListener.class
)
public class SegmentsContextVocabularyCompanyConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		String entityFieldName = String.valueOf(
			properties.get("entityFieldName"));

		if (Validator.isNull(entityFieldName)) {
			throw new ConfigurationModelListenerException(
				ResourceBundleUtil.getString(
					_getResourceBundle(),
					"please-enter-a-valid-session-property-name"),
				SegmentsContextVocabularyCompanyConfiguration.class, getClass(),
				properties);
		}

		if (_isDefined(
				String.valueOf(properties.get("assetVocabularyName")),
				String.valueOf(properties.get("companyId")), entityFieldName)) {

			throw new DuplicatedSegmentsContextVocabularyConfigurationModelListenerException(
				ResourceBundleUtil.getString(
					_getResourceBundle(),
					"this-field-is-already-linked-to-one-vocabulary"),
				SegmentsContextVocabularyCompanyConfiguration.class, getClass(),
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
		String assetVocabularyName, String companyId,
		Configuration configuration, String entityFieldName) {

		Dictionary<String, Object> properties = configuration.getProperties();

		if ((Objects.equals(
				assetVocabularyName, properties.get("assetVocabularyName")) &&
			 Objects.equals(
				 entityFieldName, properties.get("entityFieldName"))) ||
			(Objects.equals(
				companyId, String.valueOf(properties.get("companyId"))) &&
			 Objects.equals(
				 entityFieldName, properties.get("entityFieldName")))) {

			return true;
		}

		return false;
	}

	private boolean _isDefined(
			String assetVocabularyName, String companyId,
			String entityFieldName)
		throws ConfigurationModelListenerException {

		try {
			Stream<Configuration> companyConfigurationsStream = Stream.of(
				Optional.ofNullable(
					_configurationAdmin.listConfigurations(
						StringBundler.concat(
							"(", ConfigurationAdmin.SERVICE_FACTORYPID, "=",
							SegmentsContextVocabularyCompanyConfiguration.class.
								getCanonicalName(),
							")"))
				).orElse(
					new Configuration[0]
				));
			Stream<Configuration> configurationsStream = Stream.of(
				Optional.ofNullable(
					_configurationAdmin.listConfigurations(
						StringBundler.concat(
							"(", ConfigurationAdmin.SERVICE_FACTORYPID, "=",
							SegmentsContextVocabularyCompanyConfiguration.class.
								getCanonicalName(),
							")"))
				).orElse(
					new Configuration[0]
				));

			return Stream.concat(
				companyConfigurationsStream, configurationsStream
			).filter(
				configuration -> _isDefined(
					assetVocabularyName, companyId, configuration,
					entityFieldName)
			).findFirst(
			).isPresent();
		}
		catch (Exception exception) {
			throw new ConfigurationModelListenerException(
				exception.getMessage(),
				SegmentsContextVocabularyCompanyConfiguration.class, getClass(),
				null);
		}
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	private ResourceBundle _resourceBundle;

}