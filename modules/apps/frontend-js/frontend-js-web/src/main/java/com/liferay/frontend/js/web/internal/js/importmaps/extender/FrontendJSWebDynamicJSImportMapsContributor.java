/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.js.importmaps.extender;

import com.liferay.frontend.js.importmaps.extender.DynamicJSImportMapsContributor;
import com.liferay.frontend.js.web.internal.resource.handler.JavaScriptFrontendResourceRequestHandler;
import com.liferay.frontend.js.web.internal.resource.handler.LanguageFrontendResourceRequestHandler;
import com.liferay.frontend.js.web.internal.util.FrontendJSWebUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.frontend.hashed.files.CachingStrategy;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesRegistry;
import com.liferay.portal.kernel.util.Portal;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.Writer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = DynamicJSImportMapsContributor.class)
public class FrontendJSWebDynamicJSImportMapsContributor
	implements DynamicJSImportMapsContributor {

	@Override
	public void writeGlobalImports(
			HttpServletRequest httpServletRequest, Writer writer)
		throws IOException {

		writer.write(StringPool.QUOTE);
		writer.write(
			LanguageFrontendResourceRequestHandler.LANGUAGE_MODULE_PREFIX);
		writer.write("\": \"");

		String baseURL = FrontendJSWebUtil.getBaseURL(
			httpServletRequest, _portal);

		writer.write(baseURL);

		writer.write(FrontendJSWebUtil.getPortalContextPath(_portal));
		writer.write(
			LanguageFrontendResourceRequestHandler.LANGUAGE_URI_PREFIX);
		writer.write(StringPool.QUOTE);

		CachingStrategy cachingStrategy =
			_hashedFilesRegistry.getCachingStrategy(httpServletRequest);

		if (cachingStrategy == CachingStrategy.USE_ONE_HASH_PER_FILE) {
			_hashedFilesRegistry.forEachHashedFileURI(
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
		else if (cachingStrategy ==
					CachingStrategy.USE_ONE_HASH_PER_WEB_CONTEXT) {

			_hashedFilesRegistry.forEachServletContextHash(
				(servletContextName, hash) -> {
					try {
						writer.write(", \"");
						writer.write(baseURL);
						writer.write(
							JavaScriptFrontendResourceRequestHandler.
								getBundleHashedFileURIPrefix(_portal));
						writer.write(servletContextName);
						writer.write(StringPool.SLASH);
						writer.write("\": \"");
						writer.write(baseURL);
						writer.write(
							JavaScriptFrontendResourceRequestHandler.
								getBundleHashedFileURIPrefix(_portal));
						writer.write(servletContextName);
						writer.write(StringPool.OPEN_PARENTHESIS);
						writer.write(hash);
						writer.write(")/");
						writer.write(StringPool.QUOTE);
					}
					catch (Exception exception) {
						throw new RuntimeException(exception);
					}
				});
		}
	}

	@Override
	public void writeScopedImports(
			HttpServletRequest httpServletRequest, Writer writer)
		throws IOException {
	}

	@Reference
	private HashedFilesRegistry _hashedFilesRegistry;

	@Reference
	private Portal _portal;

}