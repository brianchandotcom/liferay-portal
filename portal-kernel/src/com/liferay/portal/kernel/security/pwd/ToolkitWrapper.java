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
public class ToolkitWrapper implements Toolkit {

	public ToolkitWrapper(Toolkit toolkit) {
		_toolkit = toolkit;

		_originalToolkit = toolkit;
	}

	@Override
	public String generate(PasswordPolicy passwordPolicy) {
		return _toolkit.generate(passwordPolicy);
	}

	public void setToolkit(Toolkit toolkit) {
		if (toolkit == null) {
			_toolkit = _originalToolkit;
		}
		else {
			_toolkit = toolkit;
		}
	}

	@Override
	public void validate(
			long userId, String password1, String password2,
			PasswordPolicy passwordPolicy)
		throws PortalException {

		_toolkit.validate(userId, password1, password2, passwordPolicy);
	}

	@Override
	public void validate(
			String password1, String password2, PasswordPolicy passwordPolicy)
		throws PortalException {

		_toolkit.validate(password1, password2, passwordPolicy);
	}

	private final Toolkit _originalToolkit;
	private Toolkit _toolkit;

}