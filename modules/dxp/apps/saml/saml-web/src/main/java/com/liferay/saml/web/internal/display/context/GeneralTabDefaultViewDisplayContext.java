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
import com.liferay.saml.runtime.configuration.SamlConfiguration;
import com.liferay.saml.runtime.metadata.LocalEntityManager;

import java.security.KeyStoreException;
import java.security.UnrecoverableKeyException;
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

		X509CertificateStatus x509CertificateStatus =
			_x509CertificateStatuses.get(certificateUsage);

		if (x509CertificateStatus != null) {
			return x509CertificateStatus;
		}

		try {
			X509Certificate x509Certificate =
				_localEntityManager.getLocalEntityCertificate(certificateUsage);

			if (x509Certificate != null) {
				x509CertificateStatus = new X509CertificateStatus(
					x509Certificate, X509CertificateStatus.Status.BOUND);
			}
			else {
				x509CertificateStatus = new X509CertificateStatus(
					null, X509CertificateStatus.Status.UNBOUND);
			}
		}
		catch (Exception exception) {
			Throwable cause = _getCause(exception, KeyStoreException.class);
			X509CertificateStatus.Status status;

			if (cause != null) {
				Throwable unrecoverableKeyException = _getCause(
					cause, UnrecoverableKeyException.class);

				if (unrecoverableKeyException != null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to get local entity certificate because " +
								"of incorrect keystore password",
							cause);
					}

					status =
						X509CertificateStatus.Status.
							SAML_KEYSTORE_PASSWORD_INCORRECT;
				}
				else {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to get local entity certificate because " +
								"of keystore loading issue",
							cause);
					}

					status =
						X509CertificateStatus.Status.SAML_KEYSTORE_EXCEPTION;
				}
			}
			else {
				cause = _getCause(exception, UnrecoverableKeyException.class);

				if (cause != null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to get local entity certificate because " +
								"of incorrect key credential password",
							cause);
					}

					status =
						X509CertificateStatus.Status.
							SAML_X509_CERTIFICATE_AUTH_NEEDED;
				}
				else {
					String message =
						"Unable to get local entity certificate: " +
							exception.getMessage();

					if (_log.isDebugEnabled()) {
						_log.debug(message, exception);
					}
					else if (_log.isWarnEnabled()) {
						_log.warn(message);
					}

					status = X509CertificateStatus.Status.UNKNOWN_EXCEPTION;
				}
			}

			x509CertificateStatus = new X509CertificateStatus(null, status);
		}

		_x509CertificateStatuses.put(certificateUsage, x509CertificateStatus);

		return x509CertificateStatus;
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

	private Throwable _getCause(Throwable e, Class<?> exceptionType) {
		if (e == null) {
			return null;
		}

		Throwable cause = e.getCause();

		while (cause != null) {
			if (exceptionType.isInstance(cause)) {
				return cause;
			}

			cause = cause.getCause();
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GeneralTabDefaultViewDisplayContext.class);

	private final LocalEntityManager _localEntityManager;
	private final SamlConfiguration _samlConfiguration;
	private Map<LocalEntityManager.CertificateUsage, X509CertificateStatus>
		_x509CertificateStatuses = new HashMap<>();

}