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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.bean.BeanProperties;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.saml.opensaml.integration.internal.BaseSamlTestCase;
import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.opensaml.integration.metadata.MetadataManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.NameIDPolicy;

/**
 * @author Mika Koivisto
 */
public class DefaultNameIdResolverTest extends BaseSamlTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		BeanPropertiesUtil beanPropertiesUtil = new BeanPropertiesUtil();

		_beanProperties = mock(BeanProperties.class);

		beanPropertiesUtil.setBeanProperties(_beanProperties);

		_metadataManager = mock(MetadataManager.class);

		_defaultNameIdResolver.setMetadataManager(_metadataManager);

		when(
			_metadataManager.getNameIdFormat(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			NameID.EMAIL.toString()
		);

		_user = mock(User.class);

		_expandoBridge = mock(ExpandoBridge.class);

		when(
			_user.getExpandoBridge()
		).thenReturn(
			_expandoBridge
		);
	}

	@Test
	public void testResolveEmailAddressNameId() throws Exception {
		when(
			_beanProperties.getObject(
				Mockito.any(User.class), Mockito.eq("emailAddress"))
		).thenReturn(
			"test@liferay.com"
		);

		when(
			_metadataManager.getNameIdAttribute(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			"emailAddress"
		);

		NameID nameId = _defaultNameIdResolver.resolve(
			_user, SP_ENTITY_ID, null);

		Assert.assertNotNull(nameId);
		Assert.assertEquals(NameID.EMAIL.toString(), nameId.getFormat());
		Assert.assertEquals("test@liferay.com", nameId.getValue());
	}

	@Test
	public void testResolveExpandoNameId() throws Exception {
		when(
			_expandoBridge.getAttribute(Mockito.eq("customerId"))
		).thenReturn(
			"12345"
		);

		when(
			_metadataManager.getNameIdAttribute(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			"expando:customerId"
		);

		NameID nameId = _defaultNameIdResolver.resolve(
			_user, SP_ENTITY_ID, null);

		Assert.assertNotNull(nameId);
		Assert.assertEquals(NameID.EMAIL.toString(), nameId.getFormat());
		Assert.assertEquals("12345", nameId.getValue());
	}

	@Test
	public void testResolveNameIdWithPolicy() throws Exception {
		when(
			_beanProperties.getObject(
				Mockito.any(User.class), Mockito.eq("screenName"))
		).thenReturn(
			"test"
		);

		when(
			_metadataManager.getNameIdAttribute(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			"screenName"
		);

		NameIDPolicy nameIdPolicy = OpenSamlUtil.buildNameIdPolicy();

		nameIdPolicy.setFormat(NameID.ENTITY.toString());
		nameIdPolicy.setSPNameQualifier("urn:liferay");

		NameID nameId = _defaultNameIdResolver.resolve(
			_user, SP_ENTITY_ID, nameIdPolicy);

		Assert.assertNotNull(nameId);
		Assert.assertEquals(NameID.ENTITY.toString(), nameId.getFormat());
		Assert.assertEquals("urn:liferay", nameId.getSPNameQualifier());
		Assert.assertEquals("test", nameId.getValue());
	}

	@Test
	public void testResolveScreenNameNameId() throws Exception {
		when(
			_beanProperties.getObject(
				Mockito.any(User.class), Mockito.eq("screenName"))
		).thenReturn(
			"test"
		);

		when(
			_metadataManager.getNameIdAttribute(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			"screenName"
		);

		when(
			_metadataManager.getNameIdFormat(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			NameID.ENTITY.toString()
		);

		NameID nameId = _defaultNameIdResolver.resolve(
			_user, SP_ENTITY_ID, null);

		Assert.assertNotNull(nameId);
		Assert.assertEquals(NameID.ENTITY.toString(), nameId.getFormat());
		Assert.assertEquals("test", nameId.getValue());
	}

	@Test
	public void testResolveStaticNameId() throws Exception {
		when(
			_metadataManager.getNameIdAttribute(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			"static:test@liferay.com"
		);

		NameID nameId = _defaultNameIdResolver.resolve(
			_user, SP_ENTITY_ID, null);

		Assert.assertNotNull(nameId);
		Assert.assertEquals(NameID.EMAIL.toString(), nameId.getFormat());
		Assert.assertEquals("test@liferay.com", nameId.getValue());
	}

	private BeanProperties _beanProperties;
	private final DefaultNameIdResolver _defaultNameIdResolver =
		new DefaultNameIdResolver();
	private ExpandoBridge _expandoBridge;
	private MetadataManager _metadataManager;
	private User _user;

}