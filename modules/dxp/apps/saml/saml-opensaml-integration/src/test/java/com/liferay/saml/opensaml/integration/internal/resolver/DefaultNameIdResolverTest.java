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

		_beanProperties = mock(BeanProperties.class);

		ReflectionTestUtil.setFieldValue(
			_defaultNameIdResolver, "_beanProperties", _beanProperties);

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

		String nameId = _defaultNameIdResolver.resolve(
			_user, SP_ENTITY_ID, null, null, false, null);

		Assert.assertNotNull(nameId);
		Assert.assertEquals("test@liferay.com", nameId);
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

		String nameId = _defaultNameIdResolver.resolve(
			_user, SP_ENTITY_ID, null, null, false, null);

		Assert.assertNotNull(nameId);
		Assert.assertEquals("12345", nameId);
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

		NameIDPolicy nameIDPolicy = OpenSamlUtil.buildNameIdPolicy();

		nameIDPolicy.setFormat(NameID.ENTITY.toString());
		nameIDPolicy.setSPNameQualifier("urn:liferay");

		String nameId = _defaultNameIdResolver.resolve(
			_user, SP_ENTITY_ID, NameID.ENTITY.toString(), null, false, null);

		Assert.assertNotNull(nameId);
		Assert.assertEquals("test", nameId);
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

		String nameId = _defaultNameIdResolver.resolve(
			_user, SP_ENTITY_ID, null, null, false, null);

		Assert.assertNotNull(nameId);
		Assert.assertEquals("test", nameId);
	}

	@Test
	public void testResolveStaticNameId() throws Exception {
		when(
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