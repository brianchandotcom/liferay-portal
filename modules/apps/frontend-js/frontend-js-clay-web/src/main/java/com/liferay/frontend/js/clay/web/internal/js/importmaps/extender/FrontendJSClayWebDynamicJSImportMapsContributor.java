/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.clay.web.internal.js.importmaps.extender;

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
 * @author Bryce Osterhaus
 */
@Component(service = DynamicJSImportMapsContributor.class)
public class FrontendJSClayWebDynamicJSImportMapsContributor
	implements DynamicJSImportMapsContributor {

	@Override
	public void writeGlobalImports(
			HttpServletRequest httpServletRequest, Writer writer)
		throws IOException {

		boolean first = true;

		AbsolutePortalURLBuilder absolutePortalURLBuilder =
			_absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
				httpServletRequest);

		for (String moduleName : _MODULE_NAMES) {
			if (!first) {
				writer.write(", ");
			}
			else {
				first = false;
			}

			writer.write(StringPool.QUOTE);
			writer.write(moduleName);
			writer.write("\": \"");

			String escapedModuleName = StringUtil.replace(
				moduleName, CharPool.FORWARD_SLASH, CharPool.DOLLAR);

			ESModuleAbsolutePortalURLBuilder esModuleAbsolutePortalURLBuilder =
				absolutePortalURLBuilder.forESModule(
					"frontend-js-clay-web",
					"exports/" + escapedModuleName + ".js");

			writer.write(esModuleAbsolutePortalURLBuilder.build());

			writer.write(StringPool.QUOTE);
		}
	}

	@Override
	public void writeScopedImports(
		HttpServletRequest httpServletRequest, Writer writer) {
	}

	private static final String[] _MODULE_NAMES = {
		"@clayui/alert", "@clayui/autocomplete", "@clayui/badge",
		"@clayui/breadcrumb", "@clayui/button", "@clayui/card",
		"@clayui/color-picker", "@clayui/core", "@clayui/data-provider",
		"@clayui/date-picker", "@clayui/drop-down", "@clayui/empty-state",
		"@clayui/form", "@clayui/icon", "@clayui/label", "@clayui/layout",
		"@clayui/link", "@clayui/list", "@clayui/loading-indicator",
		"@clayui/localized-input", "@clayui/management-toolbar",
		"@clayui/modal", "@clayui/multi-select", "@clayui/multi-step-nav",
		"@clayui/nav", "@clayui/navigation-bar", "@clayui/pagination",
		"@clayui/pagination-bar", "@clayui/panel", "@clayui/popover",
		"@clayui/progress-bar", "@clayui/provider", "@clayui/shared",
		"@clayui/slider", "@clayui/sticker", "@clayui/table", "@clayui/tabs",
		"@clayui/time-picker", "@clayui/toolbar", "@clayui/tooltip",
		"@clayui/upper-toolbar"
	};

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

}