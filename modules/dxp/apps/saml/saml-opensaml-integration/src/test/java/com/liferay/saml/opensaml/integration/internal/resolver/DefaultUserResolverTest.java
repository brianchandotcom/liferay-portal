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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ContactLocalService;
import com.liferay.portal.kernel.service.ContactLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.saml.opensaml.integration.field.expression.handler.registry.UserFieldExpressionHandlerRegistry;
import com.liferay.saml.opensaml.integration.field.expression.resolver.UserFieldExpressionResolver;
import com.liferay.saml.opensaml.integration.field.expression.resolver.registry.UserFieldExpressionResolverRegistry;
import com.liferay.saml.opensaml.integration.internal.BaseSamlTestCase;
import com.liferay.saml.opensaml.integration.internal.field.expression.handler.DefaultUserFieldExpressionHandler;
import com.liferay.saml.opensaml.integration.internal.metadata.MetadataManager;
import com.liferay.saml.opensaml.integration.internal.processor.factory.UserProcessorFactoryImpl;
import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.opensaml.integration.resolver.UserResolver;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlPeerBindingLocalService;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.exception.SubjectException;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.internal.util.collections.Sets;

import org.opensaml.messaging.context.InOutOperationContext;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.common.messaging.context.SAMLSubjectNameIdentifierContext;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AttributeStatement;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NameIDType;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.core.SubjectConfirmation;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Mika Koivisto
 * @author Stian Sigvartsen
 */
@PrepareForTest(CalendarFactoryUtil.class)
@RunWith(PowerMockRunner.class)
public class DefaultUserResolverTest extends BaseSamlTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		mockStatic(CalendarFactoryUtil.class);

		when(
			CalendarFactoryUtil.getCalendar()
		).thenReturn(
			new GregorianCalendar()
		);

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

		MetadataManager metadataManager = mock(MetadataManager.class);

		when(
			metadataManager.getUserAttributeMappings(Mockito.eq(IDP_ENTITY_ID))
		).thenReturn(
			_ATTRIBUTE_MAPPINGS
		);

		_defaultUserResolver.setMetadataManager(metadataManager);

		_samlSpIdpConnection = mock(SamlSpIdpConnection.class);

		when(
			_samlSpIdpConnection.getNormalizedUserAttributeMappings()
		).thenReturn(
			PropertiesUtil.load(_ATTRIBUTE_MAPPINGS)
		);

		when(
			_samlSpIdpConnection.isUnknownUsersAreStrangers()
		).thenReturn(
			true
		);

		SamlSpIdpConnectionLocalService samlSpIdpConnectionLocalService = mock(
			SamlSpIdpConnectionLocalService.class);

		when(
			samlSpIdpConnectionLocalService.getSamlSpIdpConnection(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			_samlSpIdpConnection
		);

		_defaultUserResolver.setSamlSpIdpConnectionLocalService(
			samlSpIdpConnectionLocalService);

		SamlPeerBindingLocalService samlPeerBindingLocalService = mock(
			SamlPeerBindingLocalService.class);

		when(
			samlPeerBindingLocalService.fetchSamlPeerBinding(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString())
		).thenReturn(
			null
		);

		_defaultUserResolver.setSamlPeerBindingLocalService(
			samlPeerBindingLocalService);

		_samlProviderConfigurationHelper = mock(
			SamlProviderConfigurationHelper.class);

		_defaultUserResolver.setSamlProviderConfigurationHelper(
			_samlProviderConfigurationHelper);

		_userFieldExpressionHandlerRegistry = mock(
			UserFieldExpressionHandlerRegistry.class);

		DefaultUserFieldExpressionHandler defaultUserFieldExpressionHandler =
			new DefaultUserFieldExpressionHandler();

		when(
			_userFieldExpressionHandlerRegistry.getFieldExpressionHandler(
				Mockito.anyString())
		).thenReturn(
			defaultUserFieldExpressionHandler
		);

		when(
			_userFieldExpressionHandlerRegistry.
				getFieldExpressionHandlerPrefixes()
		).thenReturn(
			new HashSet<>(Sets.newSet(""))
		);

		_defaultUserResolver.setUserFieldExpressionHandlerRegistry(
			_userFieldExpressionHandlerRegistry);

		_userFieldExpressionResolverRegistry = mock(
			UserFieldExpressionResolverRegistry.class);

		_testUserFieldExpressionResolver =
			new TestUserFieldExpressionResolver();

		when(
			_userFieldExpressionResolverRegistry.getUserFieldExpressionResolver(
				Mockito.anyString())
		).thenReturn(
			_testUserFieldExpressionResolver
		);

		_defaultUserResolver.setUserFieldExpressionResolverRegistry(
			_userFieldExpressionResolverRegistry);

		User user = _createBlankUser();
		_userLocalService = mock(UserLocalService.class);

		when(
			_userLocalService.createUser(Mockito.eq(0L))
		).thenReturn(
			user
		);

		_defaultUserResolver.setUserLocalService(_userLocalService);

		_defaultUserResolver.setUserProcessorFactory(
			new UserProcessorFactoryImpl());

		when(
			_samlProviderConfigurationHelper.isLDAPImportEnabled()
		).thenReturn(
			false
		);

		DigesterUtil digesterUtil = new DigesterUtil();

		Digester digester = Mockito.mock(Digester.class);

		Mockito.when(
			digester.digest(Mockito.anyString())
		).thenReturn(
			RandomTestUtil.randomString()
		);

		digesterUtil.setDigester(digester);

		defaultUserFieldExpressionHandler.setUserLocalService(
			_userLocalService);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(new LanguageImpl());

		getMockPortalService(
			OrganizationLocalServiceUtil.class, OrganizationLocalService.class);

		_initMessageContext(true);
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

		_testUserFieldExpressionResolver.setUserFieldExpression("emailAddress");

		User resolvedUser = _defaultUserResolver.importUser(
			1L, _samlSpIdpConnection, _SUBJECT_NAME_IDENTIFIER_EMAIL_ADDRESS,
			NameIDType.EMAIL, new UserResolverSAMLContextImpl(_messageContext),
			new ServiceContext());

		Assert.assertNotNull(resolvedUser);
	}

	@Test
	public void testImportUserWithScreenNameAttribute() throws Exception {
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

		_testUserFieldExpressionResolver.setUserFieldExpression("screenName");

		User resolvedUser = _defaultUserResolver.importUser(
			1L, _samlSpIdpConnection, _SAML_NAME_IDENTIFIER_VALUE,
			NameIDType.UNSPECIFIED,
			new UserResolverSAMLContextImpl(_messageContext),
			new ServiceContext());

		Assert.assertNotNull(resolvedUser);
	}

	@Test
	public void testMatchingUserWithEmailAddressAttribute() throws Exception {
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

		_initMatchingUserHandling();

		_testUserFieldExpressionResolver.setUserFieldExpression("emailAddress");

		User existingUser = _defaultUserResolver.importUser(
			1L, _samlSpIdpConnection, _SAML_NAME_IDENTIFIER_VALUE,
			NameIDType.EMAIL, new UserResolverSAMLContextImpl(_messageContext),
			new ServiceContext());

		Assert.assertNotNull(existingUser);
	}

	@Test
	public void testMatchingUserWithSAMLNameIDValue() throws Exception {
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

		_initMatchingUserHandling();
		_initMessageContext(false);

		_testUserFieldExpressionResolver.setUserFieldExpression("screenName");

		User existingUser = _defaultUserResolver.importUser(
			1L, _samlSpIdpConnection, _SUBJECT_NAME_IDENTIFIER_SCREEN_NAME,
			NameIDType.UNSPECIFIED,
			new UserResolverSAMLContextImpl(_messageContext),
			new ServiceContext());

		Assert.assertNotNull(existingUser);
	}

	@Test
	public void testMatchingUserWithScreenNameAttribute() throws Exception {
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

		_initMatchingUserHandling();

		_testUserFieldExpressionResolver.setUserFieldExpression("screenName");

		User existingUser = _defaultUserResolver.importUser(
			1L, _samlSpIdpConnection, _SAML_NAME_IDENTIFIER_VALUE,
			NameIDType.UNSPECIFIED,
			new UserResolverSAMLContextImpl(_messageContext),
			new ServiceContext());

		Assert.assertNotNull(existingUser);
	}

	@Test(expected = SubjectException.class)
	public void testStrangersNotAllowedToCreateAccounts() throws Exception {
		when(
			_company.isStrangers()
		).thenReturn(
			false
		);

		_testUserFieldExpressionResolver.setUserFieldExpression("emailAddress");

		_defaultUserResolver.importUser(
			1L, _samlSpIdpConnection, _SUBJECT_NAME_IDENTIFIER_EMAIL_ADDRESS,
			NameIDType.EMAIL, new UserResolverSAMLContextImpl(_messageContext),
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

		_testUserFieldExpressionResolver.setUserFieldExpression("emailAddress");

		_defaultUserResolver.importUser(
			1L, _samlSpIdpConnection, _SUBJECT_NAME_IDENTIFIER_EMAIL_ADDRESS,
			NameIDType.EMAIL, new UserResolverSAMLContextImpl(_messageContext),
			new ServiceContext());
	}

	private User _createBlankUser() {
		User user = new UserImpl();

		user.setNew(true);
		user.setPrimaryKey(0);

		return user;
	}

	private void _initMatchingUserHandling() throws Exception {
		ContactLocalService contactLocalService = getMockPortalService(
			ContactLocalServiceUtil.class, ContactLocalService.class);

		Contact contactUser = mock(Contact.class);

		when(
			contactUser.getBirthday()
		).thenReturn(
			new Date()
		);

		when(
			contactLocalService.getContact(Mockito.anyLong())
		).thenReturn(
			contactUser
		);

		User existingUser = new UserImpl();

		existingUser.setScreenName("test");
		existingUser.setEmailAddress(_SUBJECT_NAME_IDENTIFIER_EMAIL_ADDRESS);
		existingUser.setFirstName("test");
		existingUser.setLastName("test");

		when(
			existingUser.getContact()
		).thenReturn(
			contactUser
		);

		when(
			_userLocalService.addUser(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.anyBoolean(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean(),
				Mockito.anyString(), Mockito.anyString(),
				Mockito.any(Locale.class), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(),
				Mockito.anyInt(), Mockito.anyBoolean(), Mockito.anyInt(),
				Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(),
				Mockito.any(long[].class), Mockito.any(long[].class),
				Mockito.any(long[].class), Mockito.any(long[].class),
				Mockito.anyBoolean(), Mockito.any(ServiceContext.class))
		).thenReturn(
			null
		);

		when(
			_userLocalService.getUserByEmailAddress(
				Mockito.anyLong(),
				Mockito.eq(_SUBJECT_NAME_IDENTIFIER_EMAIL_ADDRESS))
		).thenReturn(
			existingUser
		);

		when(
			_userLocalService.getUserByScreenName(
				Mockito.anyLong(),
				Mockito.eq(_SUBJECT_NAME_IDENTIFIER_SCREEN_NAME))
		).thenReturn(
			existingUser
		);

		when(
			_userLocalService.updateEmailAddress(
				Mockito.anyLong(), Mockito.eq(StringPool.BLANK),
				Mockito.anyString(), Mockito.anyString())
		).thenReturn(
			existingUser
		);

		when(
			_userLocalService.updateUser(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyBoolean(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyBoolean(), Mockito.any(byte[].class),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong(),
				Mockito.anyBoolean(), Mockito.anyInt(), Mockito.anyInt(),
				Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.any(long[].class),
				Mockito.any(long[].class), Mockito.any(long[].class),
				Mockito.anyListOf(UserGroupRole.class),
				Mockito.any(long[].class), Mockito.any(ServiceContext.class))
		).thenReturn(
			existingUser
		);
	}

	private void _initMessageContext(boolean addScreenNameAttribute) {
		Assertion assertion = OpenSamlUtil.buildAssertion();

		NameID subjectNameID = OpenSamlUtil.buildNameId(
			NameIDType.ENTITY, null, "urn:liferay",
			_SAML_NAME_IDENTIFIER_VALUE);

		Subject subject = OpenSamlUtil.buildSubject(subjectNameID);

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

		if (addScreenNameAttribute) {
			attributes.add(
				OpenSamlUtil.buildAttribute(
					"screenName", _SUBJECT_NAME_IDENTIFIER_SCREEN_NAME));
		}

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

		SAMLSubjectNameIdentifierContext samlSubjectNameIdentifierContext =
			new SAMLSubjectNameIdentifierContext();

		samlSubjectNameIdentifierContext.setSubjectNameIdentifier(
			subjectNameID);

		_messageContext.addSubcontext(samlSubjectNameIdentifierContext);

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

	private static final String _SAML_NAME_IDENTIFIER_VALUE = "testNameIdValue";

	private static final String _SUBJECT_NAME_IDENTIFIER_EMAIL_ADDRESS =
		"test@liferay.com";

	private static final String _SUBJECT_NAME_IDENTIFIER_SCREEN_NAME = "test";

	private Company _company;
	private final DefaultUserResolver _defaultUserResolver =
		new DefaultUserResolver();
	private MessageContext<Response> _messageContext;
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;
	private SamlSpIdpConnection _samlSpIdpConnection;
	private TestUserFieldExpressionResolver _testUserFieldExpressionResolver;
	private UserFieldExpressionHandlerRegistry
		_userFieldExpressionHandlerRegistry;
	private UserFieldExpressionResolverRegistry
		_userFieldExpressionResolverRegistry;
	private UserLocalService _userLocalService;

	private static class TestUserFieldExpressionResolver
		implements UserFieldExpressionResolver {

		@Override
		public String getDescription(Locale locale) {
			return null;
		}

		@Override
		public String resolveUserFieldExpression(
				Map<String, List<Serializable>> incomingAttributeValues,
				UserResolver.UserResolverSAMLContext userResolverSAMLContext)
			throws Exception {

			return _userFieldExpression;
		}

		public void setUserFieldExpression(String userFieldExpression) {
			_userFieldExpression = userFieldExpression;
		}

		private String _userFieldExpression;

	}

}