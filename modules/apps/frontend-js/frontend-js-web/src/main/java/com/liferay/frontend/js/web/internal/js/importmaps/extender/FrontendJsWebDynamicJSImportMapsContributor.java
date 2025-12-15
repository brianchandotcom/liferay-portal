/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.js.importmaps.extender;

import com.liferay.frontend.js.importmaps.extender.DynamicJSImportMapsContributor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
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
public class FrontendJsWebDynamicJSImportMapsContributor
	implements DynamicJSImportMapsContributor {

	@Override
	public void writeGlobalImports(
			HttpServletRequest httpServletRequest, Writer writer)
		throws IOException {

		writer.write("\"@liferay/language/\": \"");

		String cdnHost = _getCDNHost(httpServletRequest);

		writer.write(cdnHost);

		writer.write(_portal.getPathContext(httpServletRequest));
		writer.write("/o/js/language/\"");

		_hashedFilesRegistry.forEach(
			(unhashedFileURI, hashedFileURI) -> {
				if (!unhashedFileURI.endsWith(".js")) {
					return;
				}

				try {
					writer.write(", \"");
					writer.write(unhashedFileURI);
					writer.write("\": \"");
					writer.write(cdnHost);
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

	private String _getCDNHost(HttpServletRequest httpServletRequest) {
		try {
			return _portal.getCDNHost(httpServletRequest);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Reference
	private HashedFilesRegistry _hashedFilesRegistry;

	@Reference
	private Portal _portal;

}