/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.opensaml.integration.internal.credential;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.runtime.configuration.SamlConfiguration;
import com.liferay.saml.runtime.credential.KeyStoreManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import java.security.KeyStore;
import java.security.KeyStoreException;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Mika Koivisto
 * @author Carlos Sierra Andrés
 * @author Stian Sigvartsen
 */
@Component(
	configurationPid = "com.liferay.saml.runtime.configuration.SamlConfiguration",
	immediate = true, property = "default=true", service = KeyStoreManager.class
)
public class FileSystemKeyStoreManagerImpl extends BaseKeyStoreManagerImpl {

	@Override
	public KeyStore getKeyStore() throws KeyStoreException {
		if (_keyStoreException != null) {
			throw _keyStoreException;
		}

		return _keyStore;
	}

	@Override
	public void saveKeyStore(KeyStore keyStore) throws Exception {
		File samlKeyStoreFile = new File(getSamlKeyStorePath());

		samlKeyStoreFile = samlKeyStoreFile.getAbsoluteFile();

		if (!samlKeyStoreFile.exists()) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Creating a new SAML keystore at " + samlKeyStoreFile);
			}

			File parentDir = samlKeyStoreFile.getParentFile();

			if (!parentDir.exists()) {
				parentDir.mkdirs();
			}
		}

		_monitorFile(samlKeyStoreFile);

		String samlKeyStorePassword = getSamlKeyStorePassword();

		try (FileOutputStream fileOutputStream = new FileOutputStream(
				samlKeyStoreFile)) {

			_keyStore.store(
				fileOutputStream, samlKeyStorePassword.toCharArray());
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		updateConfigurations(properties);

		String samlKeyStoreType = getSamlKeyStoreType();

		try {
			_keyStore = KeyStore.getInstance(samlKeyStoreType);
		}
		catch (KeyStoreException keyStoreException) {
			String message = StringBundler.concat(
				"Unable instantiate keystore with type ", samlKeyStoreType,
				": ", keyStoreException.getMessage());

			_keyStoreException = new KeyStoreException(
				message, keyStoreException);

			if (_log.isDebugEnabled()) {
				_log.debug(message, keyStoreException);
			}
			else {
				_log.error(message);
			}

			return;
		}

		_loadKeyStore();
	}

	@Deactivate
	protected void deactivate() {
		if (_samlKeyStoreFileWatcher == null) {
			return;
		}

		try {
			_samlKeyStoreFileWatcher.close();
		}
		catch (Exception exception) {
			String message =
				"Unable to close file watcher: " + exception.getMessage();

			if (_log.isDebugEnabled()) {
				_log.debug(message, exception);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(message);
			}
		}
		finally {
			_samlKeyStoreFileWatcher = null;
		}
	}

	private void _doLoadKeyStore() throws Exception {
		String samlKeyStorePassword = getSamlKeyStorePassword();

		try (InputStream inputStream = _getInputStream()) {
			_keyStore.load(inputStream, samlKeyStorePassword.toCharArray());
		}
	}

	private InputStream _getInputStream() throws Exception {
		String samlKeyStorePath = getSamlKeyStorePath();

		if (samlKeyStorePath.startsWith("classpath:")) {
			Class<?> clazz = getClass();

			return clazz.getResourceAsStream(samlKeyStorePath.substring(10));
		}

		File samlKeyStoreFile = new File(samlKeyStorePath);

		samlKeyStoreFile = samlKeyStoreFile.getAbsoluteFile();

		if (!samlKeyStoreFile.exists()) {
			if (Validator.isNotNull(samlConfiguration.keyStorePath()) &&
				!SamlConfiguration.KEYSTORE_PATH_DEFAULT.equals(
					samlConfiguration.keyStorePath()) &&
				_log.isWarnEnabled()) {

				_log.warn("No SAML keystore exists at " + samlKeyStoreFile);
			}

			return null;
		}

		_monitorFile(samlKeyStoreFile);

		return new FileInputStream(samlKeyStoreFile);
	}

	private void _loadKeyStore() {
		try {
			_keyStoreException = null;

			_doLoadKeyStore();
		}
		catch (Exception exception) {
			String message = StringBundler.concat(
				"Unable to load SAML keystore ", getSamlKeyStorePath(), ": ",
				exception.getMessage());

			_keyStoreException = new KeyStoreException(message, exception);

			if (_log.isDebugEnabled()) {
				_log.debug(message, exception);
			}
			else {
				_log.error(message);
			}
		}
	}

	private void _monitorFile(File samlKeyStoreFile) throws Exception {
		if (_samlKeyStoreFileWatcher != null) {
			return;
		}

		_samlKeyStoreFileWatcher = new FileWatcher(
			ev -> _loadKeyStore(), samlKeyStoreFile.toPath());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileSystemKeyStoreManagerImpl.class);

	private KeyStore _keyStore;
	private volatile KeyStoreException _keyStoreException;
	private volatile FileWatcher _samlKeyStoreFileWatcher;

}