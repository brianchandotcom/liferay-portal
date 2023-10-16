/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.security.auth.ScreenNameValidator;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Peter Fellwock
 */
public class ScreenNameValidatorFactory {

	public static ScreenNameValidator getInstance() {
		return _screenNameValidatorSnapshot.get();
	}

	private ScreenNameValidatorFactory() {
	}

	private static final Snapshot<ScreenNameValidator>
		_screenNameValidatorSnapshot = new Snapshot<>(
			ScreenNameValidatorFactory.class, ScreenNameValidator.class, null,
			true);

}