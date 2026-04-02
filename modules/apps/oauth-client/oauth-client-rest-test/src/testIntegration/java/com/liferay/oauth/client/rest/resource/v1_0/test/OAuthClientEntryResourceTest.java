/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth.client.persistence.constants.OAuthClientEntryConstants;
import com.liferay.oauth.client.rest.client.dto.v1_0.OAuthClientEntry;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;

import org.junit.runner.RunWith;

/**
 * @author Manuele Castro
 */
@FeatureFlags(featureFlags = @FeatureFlag(value = "LPD-49855"))
@RunWith(Arquillian.class)
public class OAuthClientEntryResourceTest
	extends BaseOAuthClientEntryResourceTestCase {

	@Override
	protected OAuthClientEntry randomOAuthClientEntry() throws Exception {
		OAuthClientEntry oAuthClientEntry = super.randomOAuthClientEntry();

		oAuthClientEntry.setAuthRequestParametersJSON(
			JSONUtil.put(
				RandomTestUtil.randomString(), RandomTestUtil.randomString()
			).toString());
		oAuthClientEntry.setAuthServerWellKnownURI(
			"https://accounts.google.com/.well-known/openid-configuration");
		oAuthClientEntry.setCustomClaims(
			JSONUtil.put(
				RandomTestUtil.randomString(), RandomTestUtil.randomString()
			).toString());
		oAuthClientEntry.setInfoJSON(
			JSONUtil.put(
				"client_id", oAuthClientEntry.getClientId()
			).put(
				"client_name", "example_client"
			).put(
				"client_secret", RandomTestUtil.randomString()
			).put(
				"scope", "openid email profile"
			).put(
				"subject_type", "public"
			).toString());
		oAuthClientEntry.setMatcherField("email");
		oAuthClientEntry.setOidcUserInfoMapperJSON(
			OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON);
		oAuthClientEntry.setTokenRequestParametersJSON(
			JSONUtil.put(
				RandomTestUtil.randomString(), RandomTestUtil.randomString()
			).toString());

		return oAuthClientEntry;
	}

	@Override
	protected OAuthClientEntry
			testBatchEngineDeleteImportTask_addOAuthClientEntry()
		throws Exception {

		return oAuthClientEntryResource.postOAuthClientEntry(
			randomOAuthClientEntry());
	}

	@Override
	protected OAuthClientEntry
			testDeleteOAuthClientEntryByExternalReferenceCode_addOAuthClientEntry()
		throws Exception {

		return oAuthClientEntryResource.postOAuthClientEntry(
			randomOAuthClientEntry());
	}

	@Override
	protected OAuthClientEntry
			testGetOAuthClientEntriesPage_addOAuthClientEntry(
				OAuthClientEntry oAuthClientEntry)
		throws Exception {

		return oAuthClientEntryResource.postOAuthClientEntry(oAuthClientEntry);
	}

	@Override
	protected OAuthClientEntry
			testGetOAuthClientEntryByExternalReferenceCode_addOAuthClientEntry()
		throws Exception {

		return oAuthClientEntryResource.postOAuthClientEntry(
			randomOAuthClientEntry());
	}

	@Override
	protected OAuthClientEntry testPostOAuthClientEntry_addOAuthClientEntry(
			OAuthClientEntry oAuthClientEntry)
		throws Exception {

		return oAuthClientEntryResource.postOAuthClientEntry(oAuthClientEntry);
	}

	@Override
	protected OAuthClientEntry
			testPutOAuthClientEntryByExternalReferenceCode_addOAuthClientEntry()
		throws Exception {

		return oAuthClientEntryResource.postOAuthClientEntry(
			randomOAuthClientEntry());
	}

}