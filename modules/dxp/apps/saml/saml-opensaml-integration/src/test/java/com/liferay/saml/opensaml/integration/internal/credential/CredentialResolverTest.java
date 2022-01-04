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

package com.liferay.saml.opensaml.integration.internal.credential;

import com.liferay.saml.opensaml.integration.internal.BaseSamlTestCase;

import net.shibboleth.utilities.java.support.resolver.CriteriaSet;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.security.credential.Credential;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Mika Koivisto
 */
@RunWith(PowerMockRunner.class)
public class CredentialResolverTest extends BaseSamlTestCase {

	@Test
	public void testResolveIdpCredential() throws Exception {
		prepareIdentityProvider(IDP_ENTITY_ID);
		_testResolveCredential(IDP_ENTITY_ID);
	}

	@Test
	public void testResolveNonexistingCredential() throws Exception {
		EntityIdCriterion entityIDCriterion = new EntityIdCriterion("na");

		CriteriaSet criteriaSet = new CriteriaSet();

		criteriaSet.add(entityIDCriterion);

		Credential credential = credentialResolver.resolveSingle(criteriaSet);

		Assert.assertNull(credential);
	}

	@Test
	public void testResolveSpCredential() throws Exception {
		prepareServiceProvider(SP_ENTITY_ID);
		_testResolveCredential(SP_ENTITY_ID);
	}

	private void _testResolveCredential(String spEntityId) throws Exception {
		EntityIdCriterion entityIDCriterion = new EntityIdCriterion(spEntityId);

		CriteriaSet criteriaSet = new CriteriaSet();

		criteriaSet.add(entityIDCriterion);

		Credential credential = credentialResolver.resolveSingle(criteriaSet);

		Assert.assertNotNull(credential);
	}

}