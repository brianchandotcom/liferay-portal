/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.extender.internal.osgi.util.tracker;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.language.override.exception.PLOEntryImportException;
import com.liferay.portal.language.override.service.PLOEntryLocalService;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.URL;
import java.net.URLConnection;

import java.nio.charset.StandardCharsets;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Thiago Buarque
 */
@Component(service = {})
public class LanguageClientExtensionBundleTrackerCustomizer
	implements BundleTrackerCustomizer<Bundle> {

	@Override
	public Bundle addingBundle(Bundle bundle, BundleEvent event) {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		if (Validator.isNull(headers.get("Liferay-Client-Extension-Batch")) ||
			_isAlreadyProcessed(bundle)) {

			return null;
		}

		Enumeration<URL> enumeration = bundle.findEntries(
			headers.get("Liferay-Client-Extension-Batch"),
			"Language_*.properties", true);

		while ((enumeration != null) && enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			File file = new File(url.getFile());

			String fileName = file.getName();

			try {
				Company company = _companyLocalService.getCompanyByWebId(
					PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID));

				long companyId = company.getCompanyId();

				String languageId = StringUtil.removeSubstring(
					fileName, "Language_");

				languageId = StringUtil.removeSubstring(
					languageId, ".properties");

				URLConnection urlConnection = url.openConnection();

				Reader reader = new InputStreamReader(
					urlConnection.getInputStream(), StandardCharsets.UTF_8);

				Properties properties = new Properties();

				properties.load(reader);

				User user = _userLocalService.getUserByScreenName(
					companyId,
					PropsUtil.get(PropsKeys.DEFAULT_ADMIN_SCREEN_NAME));

				_ploEntryLocalService.importPLOEntries(
					companyId, user.getUserId(), languageId, properties);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Processed language file \"" + fileName +
							"\" successfully");
				}
			}
			catch (PLOEntryImportException.InvalidTranslations
						ploEntryImportException) {

				for (Throwable throwable :
						ploEntryImportException.getSuppressed()) {

					_log.error(
						StringBundler.concat(
							"Unable to process language file \"", fileName,
							"\". ", throwable.getMessage(), StringPool.PERIOD));
				}
			}
			catch (IOException | PortalException exception) {
				_log.error(
					"Unable to process language file \"" + fileName + "\"",
					exception);
			}
		}

		return bundle;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent event, Bundle object) {
	}

	@Override
	public void removedBundle(Bundle bundle, BundleEvent event, Bundle object) {
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE, this);

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	private boolean _isAlreadyProcessed(Bundle bundle) {
		File file = bundle.getDataFile(".liferay-client-extension-language");

		String lastModifiedString = String.valueOf(bundle.getLastModified());

		try {
			if ((file != null) && file.exists() &&
				Objects.equals(FileUtil.read(file), lastModifiedString)) {

				return true;
			}

			if (!file.exists()) {
				file.createNewFile();
			}

			FileUtil.write(file, lastModifiedString, true);
		}
		catch (IOException ioException) {
			ReflectionUtil.throwException(ioException);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LanguageClientExtensionBundleTrackerCustomizer.class);

	private BundleTracker<?> _bundleTracker;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private PLOEntryLocalService _ploEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}