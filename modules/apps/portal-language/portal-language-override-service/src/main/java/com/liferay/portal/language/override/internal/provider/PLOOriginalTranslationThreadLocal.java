/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.override.internal.provider;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Drew Brokke
 */
public class PLOOriginalTranslationThreadLocal {

	public static Boolean isUseOriginalTranslation() {
		Boolean useOriginalTranslation = _useOriginalTranslation.get();

		if (_log.isDebugEnabled()) {
			_log.debug("use original translation: " + useOriginalTranslation);
		}

		return useOriginalTranslation;
	}

	public static SafeCloseable setWithSafeCloseable(
		Boolean useOriginalTranslation) {

		return _useOriginalTranslation.setWithSafeCloseable(
			useOriginalTranslation);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PLOOriginalTranslationThreadLocal.class);

	private static final CentralizedThreadLocal<Boolean>
		_useOriginalTranslation = new CentralizedThreadLocal<>(
			PLOOriginalTranslationThreadLocal.class +
				"._useOriginalTranslation",
			() -> Boolean.FALSE);

}