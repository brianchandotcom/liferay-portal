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

package com.liferay.saml.opensaml.integration.internal.metadata;

import com.liferay.saml.opensaml.integration.internal.BaseSamlTestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.opensaml.saml2.metadata.EntityDescriptor;

import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Mika Koivisto
 */
@RunWith(PowerMockRunner.class)
public class MetadataGeneratorTest extends BaseSamlTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testMetadataGenerator() throws Exception {
		prepareServiceProvider(SP_ENTITY_ID);

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(
				"http://localhost:8080/c/portal/saml/metadata");

		EntityDescriptor entityDescriptor =
			metadataManagerImpl.getEntityDescriptor(mockHttpServletRequest);

		Assert.assertNotNull(entityDescriptor);
	}

}