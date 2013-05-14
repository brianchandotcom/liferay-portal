/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.pwd;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.PasswordPolicy;

/**
 * @author Brian Wing Shun Chan
 */
public class ToolkitWrapper implements Toolkit {

	public ToolkitWrapper(Toolkit toolkit) {
		_originalToolkit = toolkit;
		_toolkit = toolkit;
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
		throws PortalException, SystemException {

		_toolkit.validate(userId, password1, password2, passwordPolicy);
	}

	@Override
	public void validate(
			String password1, String password2, PasswordPolicy passwordPolicy)
		throws PortalException, SystemException {

		_toolkit.validate(password1, password2, passwordPolicy);
	}

	private Toolkit _originalToolkit;
	private Toolkit _toolkit;

}