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

package com.liferay.saml.web.internal.display.context;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.saml.runtime.SamlException;
import com.liferay.saml.runtime.configuration.SamlConfiguration;
import com.liferay.saml.runtime.exception.CredentialAuthException;
import com.liferay.saml.runtime.metadata.LocalEntityManager;

import java.security.cert.X509Certificate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stian Sigvartsen
 */
public class GeneralTabDefaultViewDisplayContext {

	public GeneralTabDefaultViewDisplayContext(
		LocalEntityManager localEntityManager,
		SamlConfiguration samlConfiguration) {

		_localEntityManager = localEntityManager;
		_samlConfiguration = samlConfiguration;
	}

	public X509CertificateStatus getX509CertificateStatus() {
		return getX509CertificateStatus(
			LocalEntityManager.CertificateUsage.SIGNING);
	}

	public X509CertificateStatus getX509CertificateStatus(
		LocalEntityManager.CertificateUsage certificateUsage) {

		return _x509CertificateStatuses.computeIfAbsent(
			certificateUsage, this::doGetX509CertificateStatus);
	}

	public boolean isRoleIdPAvailable() {
		return _samlConfiguration.idpRoleConfigurationEnabled();
	}

	public static class X509CertificateStatus {

		public X509CertificateStatus(
			X509Certificate x509Certificate, Status status) {

			_x509Certificate = x509Certificate;
			_status = status;
		}

		public Status getStatus() {
			return _status;
		}

		public X509Certificate getX509Certificate() {
			return _x509Certificate;
		}

		public enum Status {

			BOUND, SAML_KEYSTORE_EXCEPTION, SAML_KEYSTORE_PASSWORD_INCORRECT,
			SAML_X509_CERTIFICATE_AUTH_NEEDED, UNBOUND, UNKNOWN_EXCEPTION

		}

		private final Status _status;
		private final X509Certificate _x509Certificate;

	}

	protected X509CertificateStatus doGetX509CertificateStatus(
		LocalEntityManager.CertificateUsage certificateUsage) {

		try {
			X509Certificate x509Certificate =
				_localEntityManager.getLocalEntityCertificate(certificateUsage);

			if (x509Certificate != null) {
				return new X509CertificateStatus(
					x509Certificate, X509CertificateStatus.Status.BOUND);
			}

			return new X509CertificateStatus(
				null, X509CertificateStatus.Status.UNBOUND);
		}
		catch (CredentialAuthException.KeyStorePasswordIncorrect
					keyStorePasswordIncorrect) {

			return _buildX509CertificateStatus(
				keyStorePasswordIncorrect,
				X509CertificateStatus.Status.SAML_KEYSTORE_PASSWORD_INCORRECT,
				true);
		}
		catch (CredentialAuthException.CannotLoadKeyStore cannotLoadKeyStore) {
			return _buildX509CertificateStatus(
				cannotLoadKeyStore,
				X509CertificateStatus.Status.SAML_KEYSTORE_EXCEPTION, true);
		}
		catch (CredentialAuthException.CredentialPasswordIncorrect
					credentialPasswordIncorrect) {

			return _buildX509CertificateStatus(
				credentialPasswordIncorrect,
				X509CertificateStatus.Status.SAML_X509_CERTIFICATE_AUTH_NEEDED,
				false);
		}
		catch (CredentialAuthException credentialAuthException) {
			return _buildX509CertificateStatus(
				credentialAuthException,
				X509CertificateStatus.Status.UNKNOWN_EXCEPTION, true);
		}
		catch (SamlException samlException) {
			return _buildX509CertificateStatus(
				samlException, X509CertificateStatus.Status.UNBOUND, false);
		}
	}

	private X509CertificateStatus _buildX509CertificateStatus(
		Exception exception, X509CertificateStatus.Status status,
		boolean logError) {

		if (_log.isDebugEnabled()) {
			_log.debug(
				String.format(
					"Unable to get local entity certificate: %s",
					exception.getMessage()),
				exception);
		}
		else if (logError) {
			_log.error(
				String.format(
					"Unable to get local entity certificate: %s",
					exception.getMessage()));
		}

		return new X509CertificateStatus(null, status);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GeneralTabDefaultViewDisplayContext.class);

	private final LocalEntityManager _localEntityManager;
	private final SamlConfiguration _samlConfiguration;
	private final Map
		<LocalEntityManager.CertificateUsage, X509CertificateStatus>
			_x509CertificateStatuses = new HashMap<>();

}