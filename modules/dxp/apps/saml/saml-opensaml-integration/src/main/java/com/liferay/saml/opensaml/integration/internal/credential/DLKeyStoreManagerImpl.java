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

import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.util.FileUtil;
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
import org.osgi.service.component.annotations.Modified;

/**
 * @author Mika Koivisto
 */
@Component(
	configurationPid = "com.liferay.saml.runtime.configuration.SamlConfiguration",
	immediate = true, service = KeyStoreManager.class
)
public class DLKeyStoreManagerImpl extends BaseKeyStoreManagerImpl {

	@Override
	public KeyStore getKeyStore() {
		KeyStore keyStore = null;

		String samlKeyStoreType = getSamlKeyStoreType();

		try {
			keyStore = KeyStore.getInstance(samlKeyStoreType);
		}
		catch (KeyStoreException kse) {
			_log.error(
				"Unable instantiate keystore with type " + samlKeyStoreType,
				kse);

			return null;
		}

		try (InputStream inputStream = DLStoreUtil.getFileAsStream(
				getCompanyId(), CompanyConstants.SYSTEM, _SAML_KEYSTORE_PATH)) {

			String samlKeyStorePassword = getSamlKeyStorePassword();

			keyStore.load(inputStream, samlKeyStorePassword.toCharArray());
		}
		catch (NoSuchFileException nsfe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsfe, nsfe);
			}

			try {
				keyStore.load(null, null);
			}
			catch (Exception e) {
				_log.error("Unable to load blank keystore ", e);
			}
		}
		catch (Exception e) {
			_log.error(
				"Unable to load keystore: " + getCompanyId() + "/" +
					_SAML_KEYSTORE_PATH,
				e);
		}

		return keyStore;
	}

	@Override
	public void saveKeyStore(KeyStore keyStore) throws Exception {
		File tempFile = FileUtil.createTempFile("jks");

		try {
			String samlKeyStorePassword = getSamlKeyStorePassword();

			keyStore.store(
				new FileOutputStream(tempFile),
				samlKeyStorePassword.toCharArray());

			if (!DLStoreUtil.hasDirectory(
					getCompanyId(), CompanyConstants.SYSTEM,
					_SAML_KEYSTORE_DIR_NAME)) {

				DLStoreUtil.addDirectory(
					getCompanyId(), CompanyConstants.SYSTEM,
					_SAML_KEYSTORE_DIR_NAME);
			}

			if (DLStoreUtil.hasFile(
					getCompanyId(), CompanyConstants.SYSTEM,
					_SAML_KEYSTORE_PATH)) {

				DLStoreUtil.deleteFile(
					getCompanyId(), CompanyConstants.SYSTEM,
					_SAML_KEYSTORE_PATH);
			}

			DLStoreUtil.addFile(
				getCompanyId(), CompanyConstants.SYSTEM, _SAML_KEYSTORE_PATH,
				new FileInputStream(tempFile));
		}
		finally {
			tempFile.delete();
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) throws Exception {
		updateConfigurations(properties);
	}

	private static final String _SAML_KEYSTORE_DIR_NAME = "/saml";

	private static final String _SAML_KEYSTORE_PATH = "/saml/keystore.jks";

	private static final Log _log = LogFactoryUtil.getLog(
		DLKeyStoreManagerImpl.class);

}