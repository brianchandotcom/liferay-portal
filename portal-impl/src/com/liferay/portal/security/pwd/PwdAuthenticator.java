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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PwdEncryptorException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptorUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Michael C. Han
 */
public class PwdAuthenticator {

	public static boolean authenticate(
			String login, String clearTextPassword,
			String currentEncryptedPassword)
		throws PwdEncryptorException {

		String encryptedPassword = PasswordEncryptorUtil.encrypt(
			clearTextPassword, currentEncryptedPassword);

		if (currentEncryptedPassword.equals(encryptedPassword)) {
			return true;
		}
		else if (GetterUtil.getBoolean(
					PropsUtil.get(PropsKeys.AUTH_MAC_ALLOW))) {

			try {
				MessageDigest digester = MessageDigest.getInstance(
					PropsUtil.get(PropsKeys.AUTH_MAC_ALGORITHM));

				digester.update(login.getBytes(StringPool.UTF8));

				String shardKey = PropsUtil.get(PropsKeys.AUTH_MAC_SHARED_KEY);

				if (Validator.isNull(shardKey)) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Please set the property " +
								PropsKeys.AUTH_MAC_SHARED_KEY);
					}

					return false;
				}

				encryptedPassword = Base64.encode(
					digester.digest(shardKey.getBytes(StringPool.UTF8)));

				if (clearTextPassword.equals(encryptedPassword)) {
					return true;
				}

				return false;
			}
			catch (NoSuchAlgorithmException nsae) {
				throw new SystemException(nsae);
			}
			catch (UnsupportedEncodingException uee) {
				throw new SystemException(uee);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PwdAuthenticator.class.getName());

}