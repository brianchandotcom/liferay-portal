/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.react.web.internal.js.importmaps.extender;

import com.liferay.frontend.js.importmaps.extender.DynamicJSImportMapsContributor;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;
import com.liferay.portal.url.builder.ESModuleAbsolutePortalURLBuilder;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.Writer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = DynamicJSImportMapsContributor.class)
public class FrontendJSReactWebDynamicJSImportMapsContributor
	implements DynamicJSImportMapsContributor {

	@Override
	public void writeGlobalImports(
			HttpServletRequest httpServletRequest, Writer writer)
		throws IOException {

		AbsolutePortalURLBuilder absolutePortalURLBuilder =
			_absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
				httpServletRequest);

		boolean first = true;

		for (String moduleName : _MODULE_NAMES) {
			String escapedModuleName = StringUtil.replace(
				moduleName, CharPool.FORWARD_SLASH, CharPool.DOLLAR);

			ESModuleAbsolutePortalURLBuilder esModuleAbsolutePortalURLBuilder =
				absolutePortalURLBuilder.forESModule(
					"frontend-js-react-web",
					"exports/" + escapedModuleName + ".js");

			if (!first) {
				writer.write(", ");
			}
			else {
				first = false;
			}

			writer.write(StringPool.QUOTE);
			writer.write(moduleName);
			writer.write("\": \"");
			writer.write(esModuleAbsolutePortalURLBuilder.build());
			writer.write(StringPool.QUOTE);
		}
	}

	@Override
	public void writeScopedImports(
		HttpServletRequest httpServletRequest, Writer writer) {
	}

	private static final String[] _MODULE_NAMES = {
		"react", "react-16", "react-18", "react-dom", "react-dom/client",
		"react-dom-16", "react-dom-18", "react-dom-18/client"
	};

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

}