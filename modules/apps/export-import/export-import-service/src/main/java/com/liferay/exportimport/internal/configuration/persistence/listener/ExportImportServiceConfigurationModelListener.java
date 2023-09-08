/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.configuration.persistence.listener;

import com.liferay.exportimport.configuration.ExportImportServiceConfiguration;
import com.liferay.exportimport.configuration.ExportImportServiceConfigurationWhitelistedURLPatternsHelper;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael Bowerman
 */
@Component(
	property = "model.class.name=com.liferay.exportimport.configuration.ExportImportServiceConfiguration",
	service = ConfigurationModelListener.class
)
public class ExportImportServiceConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onAfterDelete(String pid) {
		ExportImportServiceConfigurationWhitelistedURLPatternsHelper
			exportImportServiceConfigurationWhitelistedURLPatternsHelper =
				_exportImportServiceConfigurationWhitelistedURLPatternsHelperSnapshot.
					get();

		exportImportServiceConfigurationWhitelistedURLPatternsHelper.
			removeURLPatternMappers();
	}

	@Override
	public void onAfterSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		try {
			ExportImportServiceConfigurationWhitelistedURLPatternsHelper
				exportImportServiceConfigurationWhitelistedURLPatternsHelper =
					_exportImportServiceConfigurationWhitelistedURLPatternsHelperSnapshot.
						get();

			exportImportServiceConfigurationWhitelistedURLPatternsHelper.
				removeURLPatternMapper(
					GetterUtil.getLong(properties.get("companyId")));
		}
		catch (Exception exception) {
			throw new ConfigurationModelListenerException(
				exception, ExportImportServiceConfiguration.class, getClass(),
				properties);
		}
	}

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		ExportImportServiceConfiguration exportImportServiceConfiguration =
			ConfigurableUtil.createConfigurable(
				ExportImportServiceConfiguration.class, properties);

		String[] whitelistedURLPatterns =
			exportImportServiceConfiguration.
				validateLayoutReferencesWhitelistedURLPatterns();

		if (whitelistedURLPatterns == null) {
			return;
		}

		for (String whitelistedURLPattern : whitelistedURLPatterns) {
			if (Validator.isBlank(whitelistedURLPattern) ||
				whitelistedURLPattern.equals(StringPool.STAR)) {

				continue;
			}

			if (!whitelistedURLPattern.startsWith(StringPool.SLASH)) {
				throw new ConfigurationModelListenerException(
					_getMessage(
						"please-enter-a-relative-url-that-begins-with-a-slash"),
					ExportImportServiceConfiguration.class, getClass(),
					properties);
			}

			if (whitelistedURLPattern.endsWith(StringPool.SLASH)) {
				throw new ConfigurationModelListenerException(
					_getMessage(
						"please-enter-a-relative-url-that-does-not-end-with-" +
							"a-slash"),
					ExportImportServiceConfiguration.class, getClass(),
					properties);
			}

			if (whitelistedURLPattern.contains(StringPool.DOUBLE_SLASH)) {
				throw new ConfigurationModelListenerException(
					_getMessage(
						"please-enter-a-relative-url-that-does-not-have-" +
							"adjacent-slashes"),
					ExportImportServiceConfiguration.class, getClass(),
					properties);
			}

			for (char c : whitelistedURLPattern.toCharArray()) {
				if (!Validator.isChar(c) && !Validator.isDigit(c) &&
					(c != CharPool.DASH) && (c != CharPool.PERCENT) &&
					(c != CharPool.PERIOD) && (c != CharPool.PLUS) &&
					(c != CharPool.SLASH) && (c != CharPool.STAR) &&
					(c != CharPool.UNDERLINE)) {

					throw new ConfigurationModelListenerException(
						_getMessage(
							"please-enter-a-relative-url-with-valid-" +
								"characters"),
						ExportImportServiceConfiguration.class, getClass(),
						properties);
				}
			}
		}
	}

	private String _getMessage(String key, Object... arguments) {
		try {
			return ResourceBundleUtil.getString(
				_getResourceBundle(), key, arguments);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return null;
		}
	}

	private ResourceBundle _getResourceBundle() {
		return ResourceBundleUtil.getBundle(
			"content.Language", LocaleThreadLocal.getThemeDisplayLocale(),
			getClass());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportServiceConfigurationModelListener.class);

	private final Snapshot
		<ExportImportServiceConfigurationWhitelistedURLPatternsHelper>
			_exportImportServiceConfigurationWhitelistedURLPatternsHelperSnapshot =
				new Snapshot<>(
					ExportImportServiceConfigurationModelListener.class,
					ExportImportServiceConfigurationWhitelistedURLPatternsHelper.class);

}