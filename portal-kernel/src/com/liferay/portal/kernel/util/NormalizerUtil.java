/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.util;

import java.text.Normalizer;

/**
 * The Normalizer utility class.
 *
 * @author Hai Yu
 */
public class NormalizerUtil {

	public static String normalize(String input) {
		if ((_FORM == null) || Validator.isNull(input) ||
			Normalizer.isNormalized(input, _FORM)) {

			return input;
		}

		return Normalizer.normalize(input, _FORM);
	}

	public static String[] normalize(String[] input) {
		if ((_FORM == null) || ArrayUtil.isEmpty(input)) {
			return input;
		}

		for (int i = 0; i < input.length; i++) {
			if (Validator.isNotNull(input[i]) &&
				!Normalizer.isNormalized(input[i], _FORM)) {

				input[i] = Normalizer.normalize(input[i], _FORM);
			}
		}

		return input;
	}

	private static final Normalizer.Form _FORM;

	static {
		String formString = PropsUtil.get(
			PropsKeys.UNICODE_TEXT_NORMALIZER_FORM);

		if ((formString == null) || formString.isEmpty()) {
			_FORM = null;
		}
		else {
			Normalizer.Form form = null;

			try {
				form = Normalizer.Form.valueOf(formString);
			}
			catch (IllegalArgumentException iae) {
				form = null;
			}

			_FORM = form;
		}
	}

}