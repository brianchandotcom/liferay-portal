/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			String encryptedPassword, boolean upgradeHashSecurity)
		throws PwdEncryptorException {

		if (Validator.isNull(plainTextPassword)) {
			throw new PwdEncryptorException("Unable to encrypt blank password");
		}

		boolean prependAlgorithm = true;

		if (upgradeHashSecurity) {
			algorithm = getDefaultPasswordEncryptionAlgorithm();
			encryptedPassword = null;
		}
		else {
			String encryptedPasswordAlgorithm = _getEncryptedPasswordAlgorithm(
				encryptedPassword);

			if (Validator.isNotNull(encryptedPasswordAlgorithm)) {
				algorithm = encryptedPasswordAlgorithm;
			}

			if (Validator.isNotNull(encryptedPassword) &&
				(encryptedPassword.charAt(0) != CharPool.OPEN_CURLY_BRACE)) {

				prependAlgorithm = false;
			}
			else if (Validator.isNotNull(encryptedPassword) &&
					 (encryptedPassword.charAt(0) ==
						 CharPool.OPEN_CURLY_BRACE)) {

				int index = encryptedPassword.indexOf(
					CharPool.CLOSE_CURLY_BRACE);

				if (index > 0) {
					encryptedPassword = encryptedPassword.substring(index + 1);
				}
			}

			if (Validator.isNull(algorithm)) {
				algorithm = getDefaultPasswordEncryptionAlgorithm();
			}
		}

		PasswordEncryptor passwordEncryptor = _select(algorithm);

		String newEncryptedPassword = passwordEncryptor.encrypt(
			algorithm, plainTextPassword, encryptedPassword, false);

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

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, PasswordEncryptor.class, "type");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private String _getAlgorithmName(String algorithm) {
		int index = algorithm.indexOf(CharPool.SLASH);

		if (index > 0) {
			return algorithm.substring(0, index);
		}

		return algorithm;
	}

	private String _getEncryptedPasswordAlgorithm(String encryptedPassword) {
		String legacyAlgorithm =
			PropsValues.PASSWORDS_ENCRYPTION_ALGORITHM_LEGACY;

		if (_log.isDebugEnabled() && Validator.isNotNull(legacyAlgorithm)) {
			if (Validator.isNull(encryptedPassword)) {
				_log.debug(
					StringBundler.concat(
						"Using legacy detection scheme for algorithm ",
						legacyAlgorithm, " with empty password"));
			}
			else {
				_log.debug(
					StringBundler.concat(
						"Using legacy detection scheme for algorithm ",
						legacyAlgorithm, " with provided password"));
			}
		}

		if (Validator.isNotNull(encryptedPassword) &&
			(encryptedPassword.charAt(0) != CharPool.OPEN_CURLY_BRACE)) {

			if (_log.isDebugEnabled()) {
				_log.debug("Using legacy algorithm " + legacyAlgorithm);
			}

			if (Validator.isNotNull(legacyAlgorithm)) {
				return legacyAlgorithm;
			}

			return getDefaultPasswordEncryptionAlgorithm();
		}
		else if (Validator.isNotNull(encryptedPassword) &&
				 (encryptedPassword.charAt(0) == CharPool.OPEN_CURLY_BRACE)) {

			int index = encryptedPassword.indexOf(CharPool.CLOSE_CURLY_BRACE);

			if (index > 0) {
				String algorithm = encryptedPassword.substring(1, index);

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Upgraded password to use algorithm " + algorithm);
				}

				return algorithm;
			}
		}

		return null;
	}

	private PasswordEncryptor _select(String algorithm) {
		if (Validator.isNull(algorithm)) {
			throw new IllegalArgumentException("Invalid algorithm");
		}

		PasswordEncryptor passwordEncryptor = null;

		if (algorithm.startsWith(TYPE_BCRYPT)) {
			passwordEncryptor = _serviceTrackerMap.getService(TYPE_BCRYPT);
		}
		else if (algorithm.startsWith(TYPE_PBKDF2)) {
			passwordEncryptor = _serviceTrackerMap.getService(TYPE_PBKDF2);
		}
		else if (algorithm.indexOf(CharPool.SLASH) > 0) {
			passwordEncryptor = _serviceTrackerMap.getService(
				algorithm.substring(0, algorithm.indexOf(CharPool.SLASH)));
		}
		else {
			passwordEncryptor = _serviceTrackerMap.getService(algorithm);
		}

		if (passwordEncryptor == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No password encryptor found for " + algorithm);
			}

			passwordEncryptor = _serviceTrackerMap.getService(TYPE_DEFAULT);
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

	private ServiceTrackerMap<String, PasswordEncryptor> _serviceTrackerMap;

}