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

package com.liferay.portal.security.password.encryptor.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PwdEncryptorException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptor;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptorUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Michael C. Han
 */
@Component(property = "composite=true", service = PasswordEncryptor.class)
public class CompositePasswordEncryptor
	extends BasePasswordEncryptor implements PasswordEncryptor {

	@Override
	public String encrypt(
			String algorithm, String plainTextPassword,
			String encryptedPassword)
		throws PwdEncryptorException {

		if (Validator.isNull(plainTextPassword)) {
			throw new PwdEncryptorException("Unable to encrypt blank password");
		}

		String legacyAlgorithm =
			PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY;

		if (_log.isDebugEnabled() && Validator.isNotNull(legacyAlgorithm)) {
			if (Validator.isNull(encryptedPassword)) {
				_log.debug(
					StringBundler.concat(
						"Using legacy detection scheme for algorithm ",
						legacyAlgorithm, " with empty current password"));
			}
			else {
				_log.debug(
					StringBundler.concat(
						"Using legacy detection scheme for algorithm ",
						legacyAlgorithm, " with provided current password"));
			}
		}

		boolean prependAlgorithm = true;

		if (Validator.isNotNull(encryptedPassword) &&
			(encryptedPassword.charAt(0) != CharPool.OPEN_CURLY_BRACE)) {

			algorithm = legacyAlgorithm;

			prependAlgorithm = false;

			if (_log.isDebugEnabled()) {
				_log.debug("Using legacy algorithm " + algorithm);
			}
		}
		else if (Validator.isNotNull(encryptedPassword) &&
				 (encryptedPassword.charAt(0) == CharPool.OPEN_CURLY_BRACE)) {

			int index = encryptedPassword.indexOf(CharPool.CLOSE_CURLY_BRACE);

			if (index > 0) {
				algorithm = encryptedPassword.substring(1, index);

				encryptedPassword = encryptedPassword.substring(index + 1);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Upgraded password to use algorithm " + algorithm);
			}
		}

		if (Validator.isNull(algorithm)) {
			algorithm = getDefaultPasswordAlgorithmType();
		}

		PasswordEncryptor passwordEncryptor = _select(algorithm);

		String newEncryptedPassword = passwordEncryptor.encrypt(
			algorithm, plainTextPassword, encryptedPassword);

		if (!prependAlgorithm) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Generated password without algorithm prefix using " +
						algorithm);
			}

			return newEncryptedPassword;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Generated password with algorithm prefix using " + algorithm);
		}

		return StringBundler.concat(
			StringPool.OPEN_CURLY_BRACE, _getAlgorithmName(algorithm),
			StringPool.CLOSE_CURLY_BRACE, newEncryptedPassword);
	}

	@Override
	public String getAlgorithmType() {
		throw new UnsupportedOperationException();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_passwordEncryptors = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, PasswordEncryptor.class, "type");
	}

	@Deactivate
	protected void deactivate() {
		_passwordEncryptors.close();
	}

	private String _getAlgorithmName(String algorithm) {
		int index = algorithm.indexOf(CharPool.SLASH);

		if (index > 0) {
			return algorithm.substring(0, index);
		}

		return algorithm;
	}

	private PasswordEncryptor _select(String algorithm) {
		if (Validator.isNull(algorithm)) {
			throw new IllegalArgumentException("Invalid algorithm");
		}

		PasswordEncryptor passwordEncryptor = null;

		if (algorithm.startsWith(PasswordEncryptorUtil.TYPE_BCRYPT)) {
			passwordEncryptor = _passwordEncryptors.getService(
				PasswordEncryptorUtil.TYPE_BCRYPT);
		}
		else if (algorithm.startsWith(PasswordEncryptorUtil.TYPE_PBKDF2)) {
			passwordEncryptor = _passwordEncryptors.getService(
				PasswordEncryptorUtil.TYPE_PBKDF2);
		}
		else {
			passwordEncryptor = _passwordEncryptors.getService(algorithm);
		}

		if (passwordEncryptor == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No password encryptor found for " + algorithm);
			}

			passwordEncryptor = _passwordEncryptors.getService(
				PasswordEncryptorUtil.TYPE_DEFAULT);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Found ", ClassUtil.getClassName(passwordEncryptor),
					" to encrypt password using ", algorithm));
		}

		return passwordEncryptor;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompositePasswordEncryptor.class);

	private ServiceTrackerMap<String, PasswordEncryptor> _passwordEncryptors;

}