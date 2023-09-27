/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.context.vocabulary.internal.configuration.persistence.listener;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;
import java.util.Objects;
import java.util.ResourceBundle;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Janis Zhang
 */
public abstract class BaseConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		String entityFieldName = String.valueOf(
			properties.get("entityFieldName"));

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", LocaleThreadLocal.getThemeDisplayLocale(),
			getClass());

		if (Validator.isNull(entityFieldName)) {
			throw new ConfigurationModelListenerException(
				ResourceBundleUtil.getString(
					resourceBundle,
					"please-enter-a-valid-session-property-name"),
				getConfigurationClass(), getClass(), properties);
		}

		if (_isDefined(
				String.valueOf(properties.get("assetVocabularyName")),
				String.valueOf(properties.get("companyId")), entityFieldName,
				pid)) {

			throw new DuplicatedSegmentsContextVocabularyConfigurationModelListenerException(
				ResourceBundleUtil.getString(
					resourceBundle,
					"this-field-is-already-linked-to-one-vocabulary"),
				getConfigurationClass(), getClass(), properties);
		}
	}

	protected abstract Class<?> getConfigurationClass();

	@Reference
	protected ConfigurationAdmin configurationAdmin;

	private boolean _isDefined(
			String assetVocabularyName, String companyId,
			String entityFieldName, String pid)
		throws ConfigurationModelListenerException {

		try {
			Configuration[] configurations =
				configurationAdmin.listConfigurations(
					StringBundler.concat(
						"(", ConfigurationAdmin.SERVICE_FACTORYPID, "=",
						getConfigurationClass().getCanonicalName(), ")"));

			if (configurations == null) {
				return false;
			}

			for (Configuration configuration : configurations) {
				Dictionary<String, Object> properties =
					configuration.getProperties();

				if (Objects.equals(
						entityFieldName, properties.get("entityFieldName")) &&
					!Objects.equals(pid, configuration.getPid()) &&
					(Objects.equals(
						companyId,
						String.valueOf(properties.get("companyId"))) ||
					 Objects.equals(
						 assetVocabularyName,
						 properties.get("assetVocabularyName")))) {

					return true;
				}
			}

			return false;
		}
		catch (Exception exception) {
			throw new ConfigurationModelListenerException(
				exception.getMessage(), getConfigurationClass(), getClass(),
				null);
		}
	}

}