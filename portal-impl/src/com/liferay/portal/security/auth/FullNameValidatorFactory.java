/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.security.auth.FullNameValidator;

/**
 * @author Amos Fong
 * @author Shuyang Zhou
 * @author Peter Fellwock
 */
public class FullNameValidatorFactory {

	public static FullNameValidator getInstance() {
		return _fullNameValidatorSnapshot.get();
	}

	private FullNameValidatorFactory() {
	}

	private static final Snapshot<FullNameValidator>
		_fullNameValidatorSnapshot = new Snapshot<>(
			FullNameValidatorFactory.class, FullNameValidator.class, null,
			true);

}