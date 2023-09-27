/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.normalizer.internal;

import com.liferay.ibm.icu.text.Transliterator;
import com.liferay.normalizer.Normalizer;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@Component(service = Normalizer.class)
public class NormalizerImpl implements Normalizer {

	@Override
	public String normalizeToAscii(String s) {
		if (!_hasNonasciiCode(s)) {
			return s;
		}

		String normalizedText = TransliteratorHolder.transform(s);

		return StringUtil.replace(
			normalizedText, _UNICODE_TEXT, _NORMALIZED_TEXT);
	}

	private boolean _hasNonasciiCode(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) > 127) {
				return true;
			}
		}

		return false;
	}

	private static final String[] _NORMALIZED_TEXT = {"l", "'", "\""};

	private static final String[] _UNICODE_TEXT = {
		"\u0142", "\u02B9", "\u02BA"
	};

	private static class TransliteratorHolder {

		public static String transform(String source) {
			return _transliterator.transform(source);
		}

		private static final Transliterator _transliterator =
			Transliterator.getInstance(
				"Greek-Latin; Cyrillic-Latin; NFD; [:Nonspacing Mark:] " +
					"Remove; NFC");

	}

}