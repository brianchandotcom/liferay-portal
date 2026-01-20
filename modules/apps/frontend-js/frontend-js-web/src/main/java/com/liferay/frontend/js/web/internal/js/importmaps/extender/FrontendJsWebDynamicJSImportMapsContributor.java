/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.js.importmaps.extender;

import com.liferay.frontend.js.importmaps.extender.DynamicJSImportMapsContributor;
import com.liferay.frontend.js.web.internal.resource.handler.LanguageFrontendResourceRequestHandler;
import com.liferay.frontend.js.web.internal.util.FrontendJsWebUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesRegistry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.url.builder.configuration.PortalURLBuilderConfiguration;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.Writer;

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
		writer.write(
			LanguageFrontendResourceRequestHandler.LANGUAGE_MODULE_PREFIX);
		writer.write("\": \"");

		String baseURL = FrontendJsWebUtil.getBaseURL(
			httpServletRequest, _portal);

		writer.write(baseURL);

		writer.write(FrontendJsWebUtil.getPortalContextPath(_portal));
		writer.write(
			LanguageFrontendResourceRequestHandler.LANGUAGE_URI_PREFIX);
		writer.write(StringPool.QUOTE);

		PortalURLBuilderConfiguration portalURLBuilderConfiguration =
			_getPortalURLBuilderConfiguration(httpServletRequest);

		if ((portalURLBuilderConfiguration != null) &&
			!portalURLBuilderConfiguration.enableESModulesHashing()) {

			return;
		}

		_hashedFilesRegistry.forEach(
			(unhashedFileURI, hashedFileURI) -> {
				if (!unhashedFileURI.endsWith(".js")) {
					return;
				}

				try {
					writer.write(", \"");
					writer.write(baseURL);
					writer.write(unhashedFileURI);
					writer.write("\": \"");
					writer.write(baseURL);
					writer.write(hashedFileURI);
					writer.write(StringPool.QUOTE);
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			});
	}

	@Override
	public void writeScopedImports(
			HttpServletRequest httpServletRequest, Writer writer)
		throws IOException {
	}

	private PortalURLBuilderConfiguration _getPortalURLBuilderConfiguration(
		HttpServletRequest httpServletRequest) {

		PortalURLBuilderConfiguration portalURLBuilderConfiguration = null;

		try {
			portalURLBuilderConfiguration =
				_configurationProvider.getCompanyConfiguration(
					PortalURLBuilderConfiguration.class,
					_portal.getCompanyId(httpServletRequest));
		}
		catch (ConfigurationException configurationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get portal URL builder configuration",
					configurationException);
			}
		}

		return portalURLBuilderConfiguration;
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