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

package com.liferay.saml.opensaml.integration.internal.servlet.profile;

import com.liferay.petra.string.StringBundler;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.opensaml.integration.internal.BaseSamlTestCase;
import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.model.impl.SamlSpIdpConnectionImpl;
import com.liferay.saml.persistence.service.SamlSpAuthRequestLocalService;
import com.liferay.saml.persistence.service.SamlSpAuthRequestLocalServiceUtil;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalServiceUtil;
import com.liferay.saml.persistence.service.SamlSpSessionLocalService;
import com.liferay.saml.persistence.service.SamlSpSessionLocalServiceUtil;

import java.io.ByteArrayOutputStream;

import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import org.apache.xml.security.algorithms.JCEMapper;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.opensaml.messaging.context.InOutOperationContext;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.decoder.MessageDecodingException;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.crypto.SigningUtil;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Mika Koivisto
 */
public class XMLSecurityTest extends BaseSamlTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		SamlSpAuthRequestLocalService samlSpAuthRequestLocalService =
			getMockPortletService(
				SamlSpAuthRequestLocalServiceUtil.class,
				SamlSpAuthRequestLocalService.class);

		SamlSpSessionLocalService samlSpSessionLocalService =
			getMockPortletService(
				SamlSpSessionLocalServiceUtil.class,
				SamlSpSessionLocalService.class);
		SamlSpIdpConnectionLocalService samlSpIdpConnectionLocalService =
			getMockPortletService(
				SamlSpIdpConnectionLocalServiceUtil.class,
				SamlSpIdpConnectionLocalService.class);

		SamlSpIdpConnection samlSpIdpConnection = new SamlSpIdpConnectionImpl();

		when(
			samlSpIdpConnectionLocalService.getSamlSpIdpConnection(
				Mockito.eq(COMPANY_ID), Mockito.eq(IDP_ENTITY_ID))
		).thenReturn(
			samlSpIdpConnection
		);

		_webSsoProfileImpl.setIdentifierGenerationStrategyFactory(
			identifierGenerationStrategyFactory);
		_webSsoProfileImpl.setMetadataManager(metadataManagerImpl);
		_webSsoProfileImpl.setPortal(portal);
		_webSsoProfileImpl.setSamlBindings(samlBindings);
		_webSsoProfileImpl.setSamlProviderConfigurationHelper(
			samlProviderConfigurationHelper);
		_webSsoProfileImpl.setSamlSpAuthRequestLocalService(
			samlSpAuthRequestLocalService);
		_webSsoProfileImpl.setSamlSpIdpConnectionLocalService(
			samlSpIdpConnectionLocalService);
		_webSsoProfileImpl.setSamlSpSessionLocalService(
			samlSpSessionLocalService);

		prepareServiceProvider(SP_ENTITY_ID);
	}

	@Test(expected = MessageDecodingException.class)
	public void testXMLBombBillionLaughs() throws Exception {
		String redirectURL = getAuthnRequestRedirectURL();

		AuthnRequest authnRequest = getAuthnRequest(redirectURL);

		String authnRequestXML = OpenSamlUtil.marshall(authnRequest);

		String samlMessageXML = authnRequestXML.substring(38);

		authnRequestXML =
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE " +
				"saml2p:AuthnRequest [\n<!ENTITY lol1 \"lol\">\n";

		for (int i = 2; i < 10; i++) {
			String lol = "";

			for (int j = 0; j < 10; j++) {
				lol += "&lol" + (i - 1) + ";";
			}

			authnRequestXML =
				authnRequestXML + " <!ENTITY lol" + i + " \"" + lol + "\">\n";
		}

		authnRequestXML += "]>" + samlMessageXML;

		authnRequestXML =
			authnRequestXML.substring(0, authnRequestXML.length() - 22) +
				"&lol9;</saml2p:AuthnRequest>";

		decodeAuthnRequest(authnRequestXML, redirectURL);
	}

	@Test(expected = MessageDecodingException.class)
	public void testXMLBombQuadraticBlowup() throws Exception {
		String redirectURL = getAuthnRequestRedirectURL();

		AuthnRequest authnRequest = getAuthnRequest(redirectURL);

		String authnRequestXML = OpenSamlUtil.marshall(authnRequest);

		String samlMessageXML = authnRequestXML.substring(38);

		authnRequestXML =
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE " +
				"saml2p:AuthnRequest [\n<!ENTITY a \"";

		for (int i = 0; i < 5000; i++) {
			authnRequestXML += "aaaaaaaaaa";
		}

		authnRequestXML = authnRequestXML + "\">\n";

		authnRequestXML += "]>" + samlMessageXML;

		String entity = "";

		for (int i = 0; i < 5000; i++) {
			entity += "&a;&a;&a;&a;&a;&a;&a;&a;&a;&a;";
		}

		authnRequestXML =
			authnRequestXML.substring(0, authnRequestXML.length() - 22) +
				entity + "</saml2p:AuthnRequest>";

		decodeAuthnRequest(authnRequestXML, redirectURL);
	}

	@Test(expected = MessageDecodingException.class)
	public void testXXEGeneralEntities1() throws Exception {
		String redirectURL = getAuthnRequestRedirectURL();

		AuthnRequest authnRequest = getAuthnRequest(redirectURL);

		String authnRequestXML = OpenSamlUtil.marshall(authnRequest);

		StringBundler sb = new StringBundler(4);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE ");
		sb.append("saml2p:AuthnRequest [\n<!ENTITY xxe SYSTEM ");
		sb.append("\"http://localhost/saml-request\">\n]>");
		sb.append(authnRequestXML.substring(38));

		authnRequestXML = sb.toString();

		authnRequestXML =
			authnRequestXML.substring(0, authnRequestXML.length() - 22) +
				"&xxe;</saml2p:AuthnRequest>";

		decodeAuthnRequest(authnRequestXML, redirectURL);
	}

	@Test(expected = MessageDecodingException.class)
	public void testXXEGeneralEntities2() throws Exception {
		String redirectURL = getAuthnRequestRedirectURL();

		AuthnRequest authnRequest = getAuthnRequest(redirectURL);

		String authnRequestXML = OpenSamlUtil.marshall(authnRequest);

		StringBundler sb = new StringBundler(5);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE ");
		sb.append("saml2p:AuthnRequest [\n<!ENTITY xxe PUBLIC ");
		sb.append("\"SOME//PUBLIC//ID\" ");
		sb.append("\"http://localhost/saml-request\">\n]>");
		sb.append(authnRequestXML.substring(38));

		authnRequestXML = sb.toString();

		authnRequestXML =
			authnRequestXML.substring(0, authnRequestXML.length() - 22) +
				"&xxe;</saml2p:AuthnRequest>";

		decodeAuthnRequest(authnRequestXML, redirectURL);
	}

	@Test(expected = MessageDecodingException.class)
	public void testXXEParameterEntities() throws Exception {
		String redirectURL = getAuthnRequestRedirectURL();

		AuthnRequest authnRequest = getAuthnRequest(redirectURL);

		String authnRequestXML = OpenSamlUtil.marshall(authnRequest);

		StringBundler sb = new StringBundler(4);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE ");
		sb.append("saml2p:AuthnRequest [\n<!ENTITY % remote SYSTEM ");
		sb.append("\"http://localhost/saml-request\">\n%remote;\n]>");

		sb.append(authnRequestXML.substring(38));

		decodeAuthnRequest(sb.toString(), redirectURL);
	}

	protected void decodeAuthnRequest(
			String authnRequestXML, String redirectURL)
		throws Exception {

		Credential credential = metadataManagerImpl.getSigningCredential();

		String encodedAuthnRequest = encodeRequest(authnRequestXML);

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(redirectURL);

		mockHttpServletRequest.removeParameter("SAMLRequest");
		mockHttpServletRequest.removeParameter("Signature");

		mockHttpServletRequest.setParameter("SAMLRequest", encodedAuthnRequest);

		String signature = generateSignature(
			credential, mockHttpServletRequest.getParameter("SigAlg"),
			mockHttpServletRequest.getQueryString());

		mockHttpServletRequest.setParameter("Signature", signature);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_webSsoProfileImpl.decodeAuthnRequest(
			mockHttpServletRequest, mockHttpServletResponse);
	}

	protected String encodeRequest(String requestXML) throws Exception {
		Base64.Encoder encoder = _getEncoder();

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		Deflater deflater = new Deflater(Deflater.DEFLATED, true);

		DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(
			byteArrayOutputStream, deflater);

		deflaterOutputStream.write(requestXML.getBytes("UTF-8"));

		deflaterOutputStream.finish();

		return encoder.encodeToString(byteArrayOutputStream.toByteArray());
	}

	protected String generateSignature(
			Credential signingCredential, String algorithmURI,
			String queryString)
		throws Exception {

		Base64.Encoder encoder = _getEncoder();

		byte[] signatureBytes = SigningUtil.sign(
			signingCredential, JCEMapper.translateURItoJCEID(algorithmURI),
			false, queryString.getBytes("UTF-8"));

		return encoder.encodeToString(signatureBytes);
	}

	protected AuthnRequest getAuthnRequest(String redirectURL)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(redirectURL);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		SamlSsoRequestContext samlSsoRequestContext =
			_webSsoProfileImpl.decodeAuthnRequest(
				mockHttpServletRequest, mockHttpServletResponse);

		MessageContext samlMessageContext =
			samlSsoRequestContext.getSAMLMessageContext();

		InOutOperationContext<AuthnRequest, ?> inOutOperationContext =
			samlMessageContext.getSubcontext(InOutOperationContext.class);

		MessageContext<AuthnRequest> inboundMessageContext =
			inOutOperationContext.getInboundMessageContext();

		return inboundMessageContext.getMessage();
	}

	protected String getAuthnRequestRedirectURL() throws Exception {
		SamlSpIdpConnectionLocalService samlSpIdpConnectionLocalService =
			getMockPortletService(
				SamlSpIdpConnectionLocalServiceUtil.class,
				SamlSpIdpConnectionLocalService.class);

		SamlSpIdpConnection samlSpIdpConnection = new SamlSpIdpConnectionImpl();

		samlSpIdpConnection.setSamlIdpEntityId(IDP_ENTITY_ID);

		when(
			samlSpIdpConnectionLocalService.getSamlSpIdpConnection(
				Mockito.eq(COMPANY_ID), Mockito.eq(IDP_ENTITY_ID))
		).thenReturn(
			samlSpIdpConnection
		);

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(LOGIN_URL);

		mockHttpServletRequest.setAttribute(
			SamlWebKeys.SAML_SP_IDP_CONNECTION, samlSpIdpConnection);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_webSsoProfileImpl.doSendAuthnRequest(
			mockHttpServletRequest, mockHttpServletResponse, RELAY_STATE);

		return mockHttpServletResponse.getRedirectedUrl();
	}

	private static Base64.Encoder _getEncoder() {
		Base64.Encoder encoder = Base64.getEncoder();

		return encoder.withoutPadding();
	}

	private final WebSsoProfileImpl _webSsoProfileImpl =
		new WebSsoProfileImpl();

}