/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.bean.BeanProperties;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.saml.opensaml.integration.internal.BaseSamlTestCase;
import com.liferay.saml.opensaml.integration.internal.metadata.MetadataManager;
import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NameIDPolicy;

/**
 * @author Mika Koivisto
 */
public class DefaultNameIdResolverTest extends BaseSamlTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_beanProperties = Mockito.mock(BeanProperties.class);

		ReflectionTestUtil.setFieldValue(
			_defaultNameIdResolver, "_beanProperties", _beanProperties);

		_metadataManager = Mockito.mock(MetadataManager.class);

		ReflectionTestUtil.setFieldValue(
			_defaultNameIdResolver, "_metadataManager", _metadataManager);

		Mockito.when(
			_metadataManager.getNameIdFormat(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			NameID.EMAIL
		);

		_user = Mockito.mock(User.class);

		_expandoBridge = Mockito.mock(ExpandoBridge.class);

		Mockito.when(
			_user.getExpandoBridge()
		).thenReturn(
			_expandoBridge
		);
	}

	@Test
	public void testResolveEmailAddressNameId() throws Exception {
		Mockito.when(
			_beanProperties.getObject(
				Mockito.any(User.class), Mockito.eq("emailAddress"))
		).thenReturn(
			"test@liferay.com"
		);

		Mockito.when(
			_metadataManager.getNameIdAttribute(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			"emailAddress"
		);

		String nameId = _defaultNameIdResolver.resolve(
			_user, SP_ENTITY_ID, null, null, false, null);

		Assert.assertNotNull(nameId);
		Assert.assertEquals("test@liferay.com", nameId);
	}

	@Test
	public void testResolveExpandoNameId() throws Exception {
		Mockito.when(
			_expandoBridge.getAttribute(Mockito.eq("customerId"))
		).thenReturn(
			"12345"
		);

		Mockito.when(
			_metadataManager.getNameIdAttribute(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			"expando:customerId"
		);

		String nameId = _defaultNameIdResolver.resolve(
			_user, SP_ENTITY_ID, null, null, false, null);

		Assert.assertNotNull(nameId);
		Assert.assertEquals("12345", nameId);
	}

	@Test
	public void testResolveNameIdWithPolicy() throws Exception {
		Mockito.when(
			_beanProperties.getObject(
				Mockito.any(User.class), Mockito.eq("screenName"))
		).thenReturn(
			"test"
		);

		Mockito.when(
			_metadataManager.getNameIdAttribute(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			"screenName"
		);

		NameIDPolicy nameIDPolicy = OpenSamlUtil.buildNameIdPolicy();

		nameIDPolicy.setFormat(NameID.ENTITY);
		nameIDPolicy.setSPNameQualifier("urn:liferay");

		String nameId = _defaultNameIdResolver.resolve(
			_user, SP_ENTITY_ID, NameID.ENTITY, null, false, null);

		Assert.assertNotNull(nameId);
		Assert.assertEquals("test", nameId);
	}

	@Test
	public void testResolveScreenNameNameId() throws Exception {
		Mockito.when(
			_beanProperties.getObject(
				Mockito.any(User.class), Mockito.eq("screenName"))
		).thenReturn(
			"test"
		);

		Mockito.when(
			_metadataManager.getNameIdAttribute(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			"screenName"
		);

		Mockito.when(
			_metadataManager.getNameIdFormat(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			NameID.ENTITY
		);

		String nameId = _defaultNameIdResolver.resolve(
			_user, SP_ENTITY_ID, null, null, false, null);

		Assert.assertNotNull(nameId);
		Assert.assertEquals("test", nameId);
	}

	@Test
	public void testResolveStaticNameId() throws Exception {
		Mockito.when(
			_metadataManager.getNameIdAttribute(Mockito.eq(SP_ENTITY_ID))
		).thenReturn(
			"static:test@liferay.com"
		);

		String nameId = _defaultNameIdResolver.resolve(
			_user, SP_ENTITY_ID, null, null, false, null);

		Assert.assertNotNull(nameId);
		Assert.assertEquals("test@liferay.com", nameId);
	}

	private BeanProperties _beanProperties;
	private final DefaultNameIdResolver _defaultNameIdResolver =
		new DefaultNameIdResolver();
	private ExpandoBridge _expandoBridge;
	private MetadataManager _metadataManager;
	private User _user;

}