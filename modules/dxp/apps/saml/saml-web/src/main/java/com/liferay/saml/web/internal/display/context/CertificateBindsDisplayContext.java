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
import com.liferay.saml.runtime.metadata.LocalEntityManager;
import com.liferay.saml.runtime.metadata.LocalEntityManager.CertificateUsage;

import java.security.KeyStoreException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stian Sigvartsen
 */
public class CertificateBindsDisplayContext {

	public CertificateBindsDisplayContext(
		LocalEntityManager localEntityManager) {

		_localEntityManager = localEntityManager;
	}

	public CertificateBind getCertificateBind() {
		return getCertificateBind(CertificateUsage.SIGNING);
	}

	public CertificateBind getCertificateBind(
		CertificateUsage certificateUsage) {

		CertificateBind certificateBind = _certificateBinds.get(
			certificateUsage);

		if (certificateBind != null) {
			return certificateBind;
		}

		try {
			X509Certificate x509Certificate =
				_localEntityManager.getLocalEntityCertificate(certificateUsage);

			if (x509Certificate != null) {
				certificateBind = new CertificateBind(
					x509Certificate, CertificateBind.Status.BOUND);
			}
			else {
				certificateBind = new CertificateBind(
					null, CertificateBind.Status.UNBOUND);
			}
		}
		catch (Exception e) {
			Throwable cause = _getCause(e, KeyStoreException.class);
			CertificateBind.Status status;

			if (cause != null) {
				Throwable unrecoverableKeyException;

				unrecoverableKeyException = _getCause(
					cause, UnrecoverableKeyException.class);

				if (unrecoverableKeyException != null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to get local entity certificate because " +
								"of incorrect keystore password",
							cause);
					}

					status =
						CertificateBind.Status.SAML_KEYSTORE_PASSWORD_INCORRECT;
				}
				else {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to get local entity certificate because " +
								"of keystore loading issue",
							cause);
					}

					status = CertificateBind.Status.SAML_KEYSTORE_EXCEPTION;
				}
			}
			else {
				cause = _getCause(e, UnrecoverableKeyException.class);

				if (cause != null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to get local entity certificate because " +
								"of incorrect key credential password",
							cause);
					}

					status =
						CertificateBind.Status.
							SAML_X509_CERTIFICATE_AUTH_NEEDED;
				}
				else {
					String message =
						"Unable to get local entity certificate: " +
							e.getMessage();

					if (_log.isDebugEnabled()) {
						_log.debug(message, e);
					}
					else if (_log.isWarnEnabled()) {
						_log.warn(message);
					}

					status = CertificateBind.Status.UNKNOWN_EXCEPTION;
				}
			}

			certificateBind = new CertificateBind(null, status);
		}

		_certificateBinds.put(certificateUsage, certificateBind);

		return certificateBind;
	}

	public static class CertificateBind {

		public CertificateBind(X509Certificate x509Certificate, Status status) {
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
		CertificateBindsDisplayContext.class);

	private Map<CertificateUsage, CertificateBind> _certificateBinds =
		new HashMap<>();
	private final LocalEntityManager _localEntityManager;

}