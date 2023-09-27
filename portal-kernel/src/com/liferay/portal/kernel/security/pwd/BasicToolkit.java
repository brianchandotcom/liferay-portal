/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.pwd;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PasswordPolicy;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BasicToolkit implements Toolkit {

	@Override
	public abstract String generate(PasswordPolicy passwordPolicy);

	@Override
	public abstract void validate(
			long userId, String password1, String password2,
			PasswordPolicy passwordPolicy)
		throws PortalException;

	@Override
	public void validate(
			String password1, String password2, PasswordPolicy passwordPolicy)
		throws PortalException {

		validate(0, password1, password2, passwordPolicy);
	}

}