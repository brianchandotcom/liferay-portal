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