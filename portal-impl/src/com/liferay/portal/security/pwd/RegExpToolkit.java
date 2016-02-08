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

package com.liferay.portal.security.pwd;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.UserPasswordException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.security.pwd.BasicToolkit;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.util.PropsUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class RegExpToolkit extends BasicToolkit {

	public RegExpToolkit() {
		_pattern = PropsUtil.get(PropsKeys.PASSWORDS_REGEXPTOOLKIT_PATTERN);
		_charset = PropsUtil.get(PropsKeys.PASSWORDS_REGEXPTOOLKIT_CHARSET);
		_length = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.PASSWORDS_REGEXPTOOLKIT_LENGTH));
	}

	@Override
	public String generate(PasswordPolicy passwordPolicy) {
		return PwdGenerator.getPassword(_charset, _length);
	}

	@Override
	public void validate(
			long userId, String password1, String password2,
			PasswordPolicy passwordPolicy)
		throws PortalException {

		boolean value = password1.matches(_pattern);

		if (!value) {
			if (_log.isWarnEnabled()) {
				_log.warn("User " + userId + " attempted an invalid password");
			}

			throw new UserPasswordException.MustComplyWithRegex(
				userId, _pattern);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(RegExpToolkit.class);

	private final String _charset;
	private final int _length;
	private final String _pattern;

}