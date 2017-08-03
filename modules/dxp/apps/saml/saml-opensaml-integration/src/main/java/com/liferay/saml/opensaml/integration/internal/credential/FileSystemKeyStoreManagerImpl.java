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

package com.liferay.saml.opensaml.integration.internal.credential;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.runtime.credential.KeyStoreManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
	immediate = true, service = KeyStoreManager.class
)
public class FileSystemKeyStoreManagerImpl extends BaseKeyStoreManagerImpl {

	@Activate
	public void activate(Map<String, Object> properties) throws Exception {
		updateConfigurations(properties);

		String samlKeyStoreType = getSamlKeyStoreType();

		try {
			_keyStore = KeyStore.getInstance(samlKeyStoreType);
		}
		catch (KeyStoreException kse) {
			_log.error(
				"Unable instantiate keystore with type " + samlKeyStoreType,
				kse);

			return;
		}

		loadKeyStore();
	}

	@Override
	public KeyStore getKeyStore() {
		return _keyStore;
	}

	@Override
	public void saveKeyStore(KeyStore keyStore) throws Exception {
		String samlKeyStorePath = getSamlKeyStorePath();

		File samlKeyStoreFile = new File(samlKeyStorePath);

		if (!samlKeyStoreFile.exists()) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Creating a new SAML keystore at " +
						samlKeyStoreFile.getAbsolutePath());
			}

			File parentDir = samlKeyStoreFile.getParentFile();

			if (!parentDir.exists()) {
				parentDir.mkdirs();
			}
		}

		monitorFile(samlKeyStoreFile);

		String samlKeyStorePassword = getSamlKeyStorePassword();

		try (FileOutputStream fileOutputStream =
				new FileOutputStream(samlKeyStoreFile)) {

			_keyStore.store(
				fileOutputStream, samlKeyStorePassword.toCharArray());
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_samlKeyStoreFileWatcher == null) {
			return;
		}

		try {
			_samlKeyStoreFileWatcher.close();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to close file watcher", e);
			}
		}
		finally {
			_samlKeyStoreFileWatcher = null;
		}
	}

	protected void doLoadKeyStore() throws Exception {
		String samlKeyStorePassword = getSamlKeyStorePassword();
		String samlKeyStorePath = getSamlKeyStorePath();

		InputStream inputStream = null;

		if (samlKeyStorePath.startsWith("classpath:")) {
			Class<?> clazz = getClass();

			inputStream = clazz.getResourceAsStream(
				samlKeyStorePath.substring(10));
		}
		else {
			File samlKeyStoreFile = new File(samlKeyStorePath);

			if (!samlKeyStoreFile.exists()) {
				_keyStore.load(null, samlKeyStorePassword.toCharArray());

				if (Validator.isNotNull(samlConfiguration.keyStorePath()) &&
					_log.isWarnEnabled()) {

					_log.warn(
						"No SAML keystore exists at " +
							samlKeyStoreFile.getAbsolutePath());
				}

				return;
			}

			monitorFile(samlKeyStoreFile);

			inputStream = new FileInputStream(samlKeyStoreFile);
		}

		try {
			_keyStore.load(inputStream, samlKeyStorePassword.toCharArray());
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	protected void loadKeyStore() {
		try {
			doLoadKeyStore();
		}
		catch (Exception e) {
			_log.error(
				"Unable to load SAML keystore " + getSamlKeyStorePath(), e);
		}
	}

	protected void monitorFile(File samlKeyStoreFile) throws IOException {
		if (_samlKeyStoreFileWatcher != null) {
			return;
		}

		_samlKeyStoreFileWatcher = new FileWatcher(
			ev -> loadKeyStore(), samlKeyStoreFile.toPath());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileSystemKeyStoreManagerImpl.class);

	private KeyStore _keyStore;
	private volatile FileWatcher _samlKeyStoreFileWatcher;

}