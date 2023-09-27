/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.esm;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.taglib.aui.ESImport;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;

/**
 * @author Iván Zaera Avellón
 */
public class ESImportUtil {

	public static ESImport getESImport(
		AbsolutePortalURLBuilder absolutePortalURLBuilder, String module) {

		return getESImport(absolutePortalURLBuilder, null, module);
	}

	public static ESImport getESImport(
		AbsolutePortalURLBuilder absolutePortalURLBuilder, String alias,
		String module) {

		if (!isESImport(module)) {
			throw new IllegalArgumentException("Invalid module " + module);
		}

		module = module.trim();

		int fromIndex = module.indexOf("from");

		String parsedAlias = StringPool.BLANK;
		String symbol = module.substring(0, fromIndex);

		if (symbol.contains(StringPool.OPEN_CURLY_BRACE)) {
			symbol = symbol.replaceAll("[{}]", StringPool.BLANK);

			int asIndex = symbol.indexOf(" as ");

			if (asIndex == -1) {
				symbol = symbol.trim();
			}
			else {
				parsedAlias = StringUtil.trim(symbol.substring(asIndex + 4));
				symbol = StringUtil.trim(symbol.substring(0, asIndex));
			}
		}
		else {
			parsedAlias = StringUtil.trim(symbol);
			symbol = StringPool.BLANK;
		}

		if (Validator.isBlank(parsedAlias) && Validator.isBlank(symbol) &&
			!Validator.isBlank(alias)) {

			throw new UnsupportedOperationException(
				"Cannot override alias for imports without symbol");
		}

		return new ESImport(
			(alias == null) ? parsedAlias : alias,
			_getURL(absolutePortalURLBuilder, module.substring(fromIndex + 5)),
			symbol);
	}

	public static boolean isESImport(String module) {
		module = module.trim();

		if (module.contains(" from ") || module.startsWith("from ")) {
			return true;
		}

		return false;
	}

	private static String _getURL(
		AbsolutePortalURLBuilder absolutePortalURLBuilder, String part) {

		String moduleName = part.trim();

		int i = moduleName.indexOf(CharPool.SLASH);

		if (i == -1) {
			return absolutePortalURLBuilder.forESModule(
				moduleName, "index.js"
			).build();
		}

		return absolutePortalURLBuilder.forESModule(
			moduleName.substring(0, i), moduleName.substring(i + 1)
		).build();
	}

}