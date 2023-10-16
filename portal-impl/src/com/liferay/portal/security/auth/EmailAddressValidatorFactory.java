/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.security.auth.EmailAddressValidator;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Peter Fellwock
 */
public class EmailAddressValidatorFactory {

	public static EmailAddressValidator getInstance() {
		return _emailAddressValidatorSnapshot.get();
	}

	private EmailAddressValidatorFactory() {
	}

	private static final Snapshot<EmailAddressValidator>
		_emailAddressValidatorSnapshot = new Snapshot<>(
			EmailAddressValidatorFactory.class, EmailAddressValidator.class,
			null, true);

}