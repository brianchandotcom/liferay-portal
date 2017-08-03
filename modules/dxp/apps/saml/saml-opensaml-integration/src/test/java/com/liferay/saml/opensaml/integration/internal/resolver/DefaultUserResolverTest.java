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

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.saml.opensaml.integration.internal.BaseSamlTestCase;
import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.opensaml.integration.metadata.MetadataManager;

import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.Response;

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
	}

	@Test
	public void testImportUserWithEmailAddress() throws Exception {
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
				Mockito.anyLong(), Mockito.anyString(),
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

		Assertion assertion = OpenSamlUtil.buildAssertion();

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

		SAMLMessageContext<Response, SAMLObject, NameID> samlMessageContext =
			new BasicSAMLMessageContext<>();

		samlMessageContext.setPeerEntityId(IDP_ENTITY_ID);

		User resolvedUser = _defaultUserResolver.importUser(
			1, _SUBJECT_NAME_IDENTIFIER_EMAIL_ADDRESS, "emailAddress",
			assertion, samlMessageContext, new ServiceContext());

		Assert.assertNotNull(resolvedUser);
	}

	private static final String _ATTRIBUTE_MAPPINGS =
		"emailAddress=emailAddress\nfirstName=firstName\nlastName=lastName\n" +
			"screenName=screenName";

	private static final String _SUBJECT_NAME_IDENTIFIER_EMAIL_ADDRESS =
		"test@liferay.com";

	private static final String _SUBJECT_NAME_IDENTIFIER_SCREEN_NAME = "test";

	private final DefaultUserResolver _defaultUserResolver =
		new DefaultUserResolver();
	private UserLocalService _userLocalService;

}