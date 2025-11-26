/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.internal.js.importmaps.extender;

import com.liferay.frontend.js.importmaps.extender.DynamicJSImportMapsContributor;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;
import com.liferay.portal.url.builder.ESModuleAbsolutePortalURLBuilder;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = DynamicJSImportMapsContributor.class)
public class FrontendTaglibClayDynamicJSImportMapsContributor
	implements DynamicJSImportMapsContributor {

	@Override
	public void writeGlobalImports(
			HttpServletRequest httpServletRequest, Writer writer)
		throws IOException {

		AbsolutePortalURLBuilder absolutePortalURLBuilder =
			_absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
				httpServletRequest);

		boolean first = true;

		for (String moduleName : _moduleNames) {
			String escapedModuleName = StringUtil.replace(
				moduleName, CharPool.FORWARD_SLASH, CharPool.DOLLAR);

			ESModuleAbsolutePortalURLBuilder esModuleAbsolutePortalURLBuilder =
				absolutePortalURLBuilder.forESModule(
					"frontend-taglib-clay",
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

	@Activate
	protected void activate() throws IOException, JSONException {
		JSONObject packageJSONObject = _getPackageJSONObject();

		JSONObject dependenciesJSONObject = packageJSONObject.getJSONObject(
			"dependencies");

		_moduleNames.clear();

		for (String moduleName : dependenciesJSONObject.keySet()) {
			if (!moduleName.startsWith("@clayui/")) {
				continue;
			}

			_moduleNames.add(moduleName);
		}
	}

	private JSONObject _getPackageJSONObject()
		throws IOException, JSONException {

		try (InputStream inputStream =
				FrontendTaglibClayDynamicJSImportMapsContributor.class.
					getResourceAsStream("dependencies/package.json")) {

			return _jsonFactory.createJSONObject(StringUtil.read(inputStream));
		}
	}

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

	@Reference
	private JSONFactory _jsonFactory;

	private final List<String> _moduleNames = new ArrayList<>();

}