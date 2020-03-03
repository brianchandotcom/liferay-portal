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

package com.liferay.saml.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.runtime.certificate.CertificateEntityId;
import com.liferay.saml.runtime.certificate.CertificateTool;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.exception.CertificateKeyPasswordException;
import com.liferay.saml.runtime.exception.UnsupportedBindingException;
import com.liferay.saml.runtime.metadata.LocalEntityManager;
import com.liferay.saml.util.PortletPropsKeys;
import com.liferay.saml.web.internal.constants.SamlAdminPortletKeys;

import java.security.KeyPair;
import java.security.cert.X509Certificate;

import java.util.Calendar;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.saml.runtime.configuration.SamlKeyStoreManagerConfiguration",
	immediate = true,
	property = {
		"javax.portlet.name=" + SamlAdminPortletKeys.SAML_ADMIN,
		"mvc.command.name=/admin/updateCertificate"
	},
	service = MVCActionCommand.class
)
public class UpdateCertificateMVCActionCommand extends BaseMVCActionCommand {

	protected void authenticateCertificate(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		LocalEntityManager.CertificateUsage certificateUsage =
			LocalEntityManager.CertificateUsage.valueOf(
				ParamUtil.getString(actionRequest, "certificateUsage"));

		UnicodeProperties unicodeProperties = PropertiesParamUtil.getProperties(
			actionRequest, "settings--");

		String keystoreCredentialPassword = getKeystoreCredentialPassword(
			certificateUsage, unicodeProperties);

		if (Validator.isNotNull(keystoreCredentialPassword)) {
			_samlProviderConfigurationHelper.updateProperties(
				unicodeProperties);
		}

		try {
			X509Certificate x509Certificate =
				_localEntityManager.getLocalEntityCertificate(certificateUsage);

			actionRequest.setAttribute(
				SamlWebKeys.SAML_X509_CERTIFICATE, x509Certificate);
		}
		catch (Exception exception) {
			SessionErrors.add(
				actionRequest, CertificateKeyPasswordException.class);
		}

		actionResponse.setRenderParameter(
			"mvcRenderCommandName", "/admin/updateCertificate");
	}

	protected void deleteCertificate(ActionRequest actionRequest)
		throws Exception {

		_localEntityManager.deleteLocalEntityCertificate(
			LocalEntityManager.CertificateUsage.valueOf(
				ParamUtil.getString(actionRequest, "certificateUsage")));
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.get(actionRequest, "cmd", "auth");

		if (cmd.equals("auth")) {
			authenticateCertificate(actionRequest, actionResponse);
		}
		else if (cmd.equals("replace")) {
			replaceCertificate(actionRequest);
		}
		else if (cmd.equals("delete")) {
			deleteCertificate(actionRequest);
		}
	}

	protected String getKeystoreCredentialPassword(
			LocalEntityManager.CertificateUsage certificateUsage,
			UnicodeProperties unicodeProperties)
		throws UnsupportedBindingException {

		if (certificateUsage == LocalEntityManager.CertificateUsage.SIGNING) {
			return unicodeProperties.getProperty(
				PortletPropsKeys.SAML_KEYSTORE_CREDENTIAL_PASSWORD);
		}
		else if (certificateUsage ==
					LocalEntityManager.CertificateUsage.ENCRYPTION) {

			return unicodeProperties.getProperty(
				PortletPropsKeys.SAML_KEYSTORE_ENCRYPTION_CREDENTIAL_PASSWORD);
		}
		else {
			throw new UnsupportedBindingException(
				"Unsupported certificate usage: " + certificateUsage.name());
		}
	}

	protected void replaceCertificate(ActionRequest actionRequest)
		throws Exception {

		UnicodeProperties unicodeProperties = PropertiesParamUtil.getProperties(
			actionRequest, "settings--");

		LocalEntityManager.CertificateUsage certificateUsage =
			LocalEntityManager.CertificateUsage.valueOf(
				ParamUtil.getString(actionRequest, "certificateUsage"));

		String keystoreCredentialPassword = getKeystoreCredentialPassword(
			certificateUsage, unicodeProperties);

		if (Validator.isNull(keystoreCredentialPassword)) {
			throw new CertificateKeyPasswordException();
		}

		int validityDays = ParamUtil.getInteger(
			actionRequest, "certificateValidityDays");

		if (validityDays == 0) {
			SessionErrors.add(actionRequest, "certificateValidityDays");

			return;
		}

		Calendar startDate = Calendar.getInstance();

		Calendar endDate = (Calendar)startDate.clone();

		endDate.add(Calendar.DAY_OF_YEAR, validityDays);

		if (endDate.get(Calendar.YEAR) > 9999) {
			SessionErrors.add(actionRequest, "certificateValidityDays");

			return;
		}

		String keyAlgorithm = ParamUtil.getString(
			actionRequest, "certificateKeyAlgorithm");
		int keyLength = ParamUtil.getInteger(
			actionRequest, "certificateKeyLength");

		KeyPair keyPair = _certificateTool.generateKeyPair(
			keyAlgorithm, keyLength);

		String commonName = ParamUtil.getString(
			actionRequest, "certificateCommonName");
		String organization = ParamUtil.getString(
			actionRequest, "certificateOrganization");
		String organizationUnit = ParamUtil.getString(
			actionRequest, "certificateOrganizationUnit");
		String locality = ParamUtil.getString(
			actionRequest, "certificateLocality");
		String state = ParamUtil.getString(actionRequest, "certificateState");
		String country = ParamUtil.getString(
			actionRequest, "certificateCountry");

		CertificateEntityId subjectCertificateEntityId =
			new CertificateEntityId(
				commonName, organization, organizationUnit, locality, state,
				country);

		X509Certificate x509Certificate = _certificateTool.generateCertificate(
			keyPair, subjectCertificateEntityId, subjectCertificateEntityId,
			startDate.getTime(), endDate.getTime(),
			_SHA256_PREFIX + keyAlgorithm);

		_localEntityManager.storeLocalEntityCertificate(
			keyPair.getPrivate(), keystoreCredentialPassword, x509Certificate,
			certificateUsage);

		_samlProviderConfigurationHelper.updateProperties(unicodeProperties);

		actionRequest.setAttribute(
			SamlWebKeys.SAML_X509_CERTIFICATE, x509Certificate);
	}

	private static final String _SHA256_PREFIX = "SHA256with";

	@Reference
	private CertificateTool _certificateTool;

	@Reference
	private LocalEntityManager _localEntityManager;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

}