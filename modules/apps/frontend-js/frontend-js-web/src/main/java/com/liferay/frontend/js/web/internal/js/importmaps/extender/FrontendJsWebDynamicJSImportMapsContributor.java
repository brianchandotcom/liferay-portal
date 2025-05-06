/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.js.importmaps.extender;

import com.liferay.frontend.js.importmaps.extender.DynamicJSImportMapsContributor;
import com.liferay.frontend.js.web.internal.configuration.FrontendCachingConfiguration;
import com.liferay.frontend.js.web.internal.hashed.files.HashedFilesRegistry;
import com.liferay.frontend.js.web.internal.hashed.files.request.helper.LanguageRequestHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = DynamicJSImportMapsContributor.class)
public class FrontendJsWebDynamicJSImportMapsContributor
	implements DynamicJSImportMapsContributor {

	@Override
	public void writeGlobalImports(
			HttpServletRequest httpServletRequest, Writer writer)
		throws IOException {

		writer.write(StringPool.QUOTE);
		writer.write(LanguageRequestHelper.LANGUAGE_MODULE_PREFIX);
		writer.write("\": \"");
		writer.write(LanguageRequestHelper.LANGUAGE_URI_PREFIX);
		writer.write(StringPool.QUOTE);

		if (_isESModulesInfiniteCachingDisabled(
				_portal.getCompanyId(httpServletRequest))) {

			return;
		}

		_hashedFilesRegistry.forEachHashedFile(
			(key, value) -> {
				if (!key.endsWith(".js")) {
					return;
				}

				try {
					writer.write(",\"");
					writer.write(key);
					writer.write("\": \"");
					writer.write(value);
					writer.write(StringPool.QUOTE);
				}
				catch (IOException ioException) {
					throw new RuntimeException(ioException);
				}
			});
	}

	@Override
	public void writeScopedImports(
			HttpServletRequest httpServletRequest, Writer writer)
		throws IOException {
	}

	private boolean _isESModulesInfiniteCachingDisabled(long companyId) {
		try {
			FrontendCachingConfiguration frontendCachingConfiguration =
				_configurationProvider.getCompanyConfiguration(
					FrontendCachingConfiguration.class, companyId);

			return frontendCachingConfiguration.
				disableESModulesInfiniteCaching();
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FrontendJsWebDynamicJSImportMapsContributor.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private HashedFilesRegistry _hashedFilesRegistry;

	@Reference
	private Portal _portal;

}