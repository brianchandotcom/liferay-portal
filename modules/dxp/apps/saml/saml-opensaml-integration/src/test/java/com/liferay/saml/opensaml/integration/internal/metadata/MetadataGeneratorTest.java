/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.metadata;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.saml.opensaml.integration.internal.BaseSamlTestCase;
import com.liferay.saml.opensaml.integration.internal.bootstrap.SecurityConfigurationBootstrap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.ext.saml2alg.SigningMethod;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;

/**
 * @author Mika Koivisto
 */
public class MetadataGeneratorTest extends BaseSamlTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testMetadataGenerator() throws Exception {
		prepareServiceProvider(SP_ENTITY_ID);

		Assert.assertNotNull(
			metadataManagerImpl.getEntityDescriptor(
				getMockHttpServletRequest(
					"http://localhost:8080/c/portal/saml/metadata")));
	}

	@Test
	public void testMetadataGeneratorListsSignatureAlgorithms()
		throws Exception {

		prepareServiceProvider(SP_ENTITY_ID);

		ReflectionTestUtil.invoke(
			new SecurityConfigurationBootstrap(), "activate",
			new Class<?>[] {Map.class}, Collections.emptyMap());

		Assert.assertTrue(
			_checkMatch(
				metadataManagerImpl.getEntityDescriptor(
					getMockHttpServletRequest(
						"http://localhost:8080/c/portal/saml/metadata"))));
	}

	@Test
	public void testMetadataGeneratorOmitsBlacklistedAlgorithms()
		throws Exception {

		prepareServiceProvider(SP_ENTITY_ID);

		ReflectionTestUtil.invoke(
			new SecurityConfigurationBootstrap(), "activate",
			new Class<?>[] {Map.class},
			HashMapBuilder.<String, Object>put(
				"blacklisted.algorithms",
				new String[] {
					"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"
				}
			).build());

		Assert.assertFalse(
			_checkMatch(
				metadataManagerImpl.getEntityDescriptor(
					getMockHttpServletRequest(
						"http://localhost:8080/c/portal/saml/metadata"))));
	}

	private boolean _checkMatch(EntityDescriptor entityDescriptor) {
		List<XMLObject> xmlObjects = new ArrayList<>();

		List<RoleDescriptor> roleDescriptors =
			entityDescriptor.getRoleDescriptors();

		for (RoleDescriptor roleDescriptor : roleDescriptors) {
			Extensions extensions = roleDescriptor.getExtensions();

			xmlObjects.addAll(extensions.getUnknownXMLObjects());
		}

		for (XMLObject xmlObject : xmlObjects) {
			if (!SigningMethod.class.isInstance(xmlObject)) {
				continue;
			}

			SigningMethod signingMethod = (SigningMethod)xmlObject;

			if (StringUtil.equals(
					signingMethod.getAlgorithm(),
					"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256")) {

				return true;
			}
		}

		return false;
	}

}