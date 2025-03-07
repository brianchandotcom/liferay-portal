/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.jakarta.ee.transformer.function;

import com.liferay.portal.tools.jakarta.ee.transformer.TransformerAgent;

import java.util.function.BiFunction;

/**
 * @author Shuyang Zhou
 */
public class TextReplacerBiFunction
	implements BiFunction<String, String, String> {

	public static final BiFunction<String, String, String> INSTANCE =
		new TextReplacerBiFunction();

	@Override
	public String apply(String invoker, String text) {
		String newText = TransformerAgent.replace(
			TransformerAgent.replacementDashDotMap, text);

		if (!_JAKARTA_EE_TRANSFORMER_TEXT_REPLACER_LOGGING_DISABLED &&
			!newText.equals(text)) {

			System.err.println("JakartaEETransformer#TextReplacer#" + invoker);
		}

		return newText;
	}

	private static final boolean
		_JAKARTA_EE_TRANSFORMER_TEXT_REPLACER_LOGGING_DISABLED =
			Boolean.getBoolean(
				"jakarta.ee.transformer.text.replacer.logging.disabled");

}