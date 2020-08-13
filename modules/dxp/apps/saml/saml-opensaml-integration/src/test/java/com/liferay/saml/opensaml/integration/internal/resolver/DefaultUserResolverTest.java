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

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.saml.opensaml.integration.internal.BaseSamlTestCase;
import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.opensaml.integration.metadata.MetadataManager;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.exception.SubjectException;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.opensaml.messaging.context.InOutOperationContext;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NameIDType;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.core.SubjectConfirmation;

/**
 * @author Mika Koivisto
 */
public class DefaultUserResolverTest extends BaseSamlTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		MetadataManager metadataManager = mock(MetadataManager.class);

		_defaultUserResolver.setMetadataManager(metadataManager);

		_userLocalService = mock(UserLocalService.class);

		_defaultUserResolver.setUserLocalService(_userLocalService);

		when(
			metadataManager.getUserAttributeMappings(Mockito.eq(IDP_ENTITY_ID))
		).thenReturn(
			_ATTRIBUTE_MAPPINGS
		);

		SamlSpIdpConnection samlSpIdpConnection = mock(
			SamlSpIdpConnection.class);

		when(
			samlSpIdpConnection.isUnknownUsersAreStrangers()
		).thenReturn(
			true
		);

		SamlSpIdpConnectionLocalService samlSpIdpConnectionLocalService = mock(
			SamlSpIdpConnectionLocalService.class);

		when(
			samlSpIdpConnectionLocalService.getSamlSpIdpConnection(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			samlSpIdpConnection
		);

		_defaultUserResolver.setSamlSpIdpConnectionLocalService(
			samlSpIdpConnectionLocalService);

		_company = mock(Company.class);

		when(
			_company.hasCompanyMx(_SUBJECT_NAME_IDENTIFIER_EMAIL_ADDRESS)
		).thenReturn(
			true
		);

		CompanyLocalService companyLocalService = mock(
			CompanyLocalService.class);

		when(
			companyLocalService.getCompany(Mockito.anyLong())
		).thenReturn(
			_company
		);

		_defaultUserResolver.setCompanyLocalService(companyLocalService);

		_initMessageContext();
		_initUnknownUserHandling();
	}

	@Test
	public void testImportUserWithEmailAddress() throws Exception {
		when(
			_company.isStrangers()
		).thenReturn(
			true
		);

		when(
			_company.isStrangersWithMx()
		).thenReturn(
			true
		);

		User resolvedUser = _defaultUserResolver.importUser(
			1, _SUBJECT_NAME_IDENTIFIER_EMAIL_ADDRESS, "emailAddress",
			new UserResolverSAMLContextImpl(_messageContext),
			new ServiceContext());

		Assert.assertNotNull(resolvedUser);
	}

	@Test(expected = SubjectException.class)
	public void testStrangersNotAllowedToCreateAccounts() throws Exception {
		when(
			_company.isStrangers()
		).thenReturn(
			false
		);

		_defaultUserResolver.importUser(
			1, _SUBJECT_NAME_IDENTIFIER_EMAIL_ADDRESS, "emailAddress",
			new UserResolverSAMLContextImpl(_messageContext),
			new ServiceContext());
	}

	@Test(expected = UserEmailAddressException.MustNotUseCompanyMx.class)
	public void testStrangersNotAllowedToCreateAccountsWithCompanyMx()
		throws Exception {

		when(
			_company.isStrangers()
		).thenReturn(
			true
		);

		when(
			_company.isStrangersWithMx()
		).thenReturn(
			false
		);

		_defaultUserResolver.importUser(
			1, _SUBJECT_NAME_IDENTIFIER_EMAIL_ADDRESS, "emailAddress",
			new UserResolverSAMLContextImpl(_messageContext),
			new ServiceContext());
	}

	private void _initMessageContext() {
		Assertion assertion = OpenSamlUtil.buildAssertion();

		NameID subjectNameId = OpenSamlUtil.buildNameId(
			NameIDType.ENTITY, null, "urn:liferay", "value");

		Subject subject = OpenSamlUtil.buildSubject(subjectNameId);

		SubjectConfirmation subjectConfirmation =
			OpenSamlUtil.buildSubjectConfirmation();

		subjectConfirmation.setMethod(SubjectConfirmation.METHOD_BEARER);

		List<SubjectConfirmation> subjectConfirmations =
			subject.getSubjectConfirmations();

		subjectConfirmations.add(subjectConfirmation);

		assertion.setSubject(subject);

		List<AttributeStatement> attributeStatements =
			assertion.getAttributeStatements();

		AttributeStatement attributeStatement =
			OpenSamlUtil.buildAttributeStatement();

		attributeStatements.add(attributeStatement);

		List<Attribute> attributes = attributeStatement.getAttributes();

		attributes.add(
			OpenSamlUtil.buildAttribute(
				"emailAddress", _SUBJECT_NAME_IDENTIFIER_EMAIL_ADDRESS));
		attributes.add(OpenSamlUtil.buildAttribute("firstName", "test"));
		attributes.add(OpenSamlUtil.buildAttribute("lastName", "test"));
		attributes.add(
			OpenSamlUtil.buildAttribute(
				"screenName", _SUBJECT_NAME_IDENTIFIER_SCREEN_NAME));

		_messageContext = new MessageContext<>();

		Response response = Mockito.mock(Response.class);

		when(
			response.getAssertions()
		).thenReturn(
			Arrays.asList(assertion)
		);

		MessageContext<Response> inboundMessageContext = new MessageContext<>();

		inboundMessageContext.addSubcontext(
			new SubjectAssertionContext(assertion));
		inboundMessageContext.setMessage(response);

		InOutOperationContext<Response, Object> inOutOperationContext =
			new InOutOperationContext<>(
				inboundMessageContext, new MessageContext<>());

		_messageContext.addSubcontext(inOutOperationContext);

		SAMLPeerEntityContext samlPeerEntityContext =
			_messageContext.getSubcontext(SAMLPeerEntityContext.class, true);

		samlPeerEntityContext.setEntityId(IDP_ENTITY_ID);
	}

	private void _initUnknownUserHandling() throws Exception {
		when(
			_userLocalService.getUserByEmailAddress(
				1, _SUBJECT_NAME_IDENTIFIER_EMAIL_ADDRESS)
		).thenReturn(
			null
		);

		User user = mock(User.class);

		when(
			_userLocalService.addUser(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean(),
				Mockito.eq(_SUBJECT_NAME_IDENTIFIER_SCREEN_NAME),
				Mockito.eq(_SUBJECT_NAME_IDENTIFIER_EMAIL_ADDRESS),
				Mockito.any(Locale.class), Mockito.eq("test"),
				Mockito.anyString(), Mockito.eq("test"), Mockito.anyInt(),
				Mockito.anyInt(), Mockito.anyBoolean(), Mockito.anyInt(),
				Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(),
				Mockito.any(long[].class), Mockito.any(long[].class),
				Mockito.any(long[].class), Mockito.any(long[].class),
				Mockito.eq(false), Mockito.any(ServiceContext.class))
		).thenReturn(
			user
		);

		when(
			_userLocalService.updateEmailAddressVerified(
				Mockito.anyLong(), Mockito.eq(true))
		).thenReturn(
			user
		);

		when(
			_userLocalService.updatePasswordReset(
				Mockito.anyLong(), Mockito.eq(false))
		).thenReturn(
			user
		);
	}

	private static final String _ATTRIBUTE_MAPPINGS =
		"emailAddress=emailAddress\nfirstName=firstName\nlastName=lastName\n" +
			"screenName=screenName";

	private static final String _SUBJECT_NAME_IDENTIFIER_EMAIL_ADDRESS =
		"test@liferay.com";

	private static final String _SUBJECT_NAME_IDENTIFIER_SCREEN_NAME = "test";

	private Company _company;
	private final DefaultUserResolver _defaultUserResolver =
		new DefaultUserResolver();
	private MessageContext<Response> _messageContext;
	private UserLocalService _userLocalService;

}