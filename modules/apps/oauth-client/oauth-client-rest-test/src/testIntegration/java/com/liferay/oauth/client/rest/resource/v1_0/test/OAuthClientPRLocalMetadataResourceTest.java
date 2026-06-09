/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth.client.rest.client.dto.v1_0.OAuthClientPRLocalMetadata;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;

import org.junit.runner.RunWith;

/**
 * @author Manuele Castro
 */
@FeatureFlags(featureFlags = @FeatureFlag(value = "LPD-63415"))
@RunWith(Arquillian.class)
public class OAuthClientPRLocalMetadataResourceTest
	extends BaseOAuthClientPRLocalMetadataResourceTestCase {

	@Override
	protected OAuthClientPRLocalMetadata randomOAuthClientPRLocalMetadata()
		throws Exception {

		OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata =
			super.randomOAuthClientPRLocalMetadata();

		oAuthClientPRLocalMetadata.setMetadataJSON(
			JSONUtil.put(
				"authorization_servers",
				JSONUtil.put(
					"https://" +
						StringUtil.toLowerCase(RandomTestUtil.randomString()))
			).put(
				"bearer_methods_supported", JSONUtil.put("header")
			).put(
				"resource_name", RandomTestUtil.randomString()
			).put(
				"scopes_supported", JSONUtil.put("read")
			).toString());
		oAuthClientPRLocalMetadata.setProtectedResourceURI(
			"https://" + StringUtil.toLowerCase(RandomTestUtil.randomString()));

		return oAuthClientPRLocalMetadata;
	}

	@Override
	protected OAuthClientPRLocalMetadata
			testBatchEngineDeleteImportTask_addOAuthClientPRLocalMetadata()
		throws Exception {

		return oAuthClientPRLocalMetadataResource.
			postOAuthClientPRLocalMetadata(randomOAuthClientPRLocalMetadata());
	}

	@Override
	protected OAuthClientPRLocalMetadata
			testDeleteOAuthClientPRLocalMetadataByExternalReferenceCode_addOAuthClientPRLocalMetadata()
		throws Exception {

		return oAuthClientPRLocalMetadataResource.
			postOAuthClientPRLocalMetadata(randomOAuthClientPRLocalMetadata());
	}

	@Override
	protected OAuthClientPRLocalMetadata
			testGetOAuthClientPRLocalMetadataByExternalReferenceCode_addOAuthClientPRLocalMetadata()
		throws Exception {

		return oAuthClientPRLocalMetadataResource.
			postOAuthClientPRLocalMetadata(randomOAuthClientPRLocalMetadata());
	}

	@Override
	protected OAuthClientPRLocalMetadata
			testGetOAuthClientPRLocalMetadatasPage_addOAuthClientPRLocalMetadata(
				OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata)
		throws Exception {

		return oAuthClientPRLocalMetadataResource.
			postOAuthClientPRLocalMetadata(oAuthClientPRLocalMetadata);
	}

	@Override
	protected OAuthClientPRLocalMetadata
			testPostOAuthClientPRLocalMetadata_addOAuthClientPRLocalMetadata(
				OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata)
		throws Exception {

		return oAuthClientPRLocalMetadataResource.
			postOAuthClientPRLocalMetadata(oAuthClientPRLocalMetadata);
	}

	@Override
	protected OAuthClientPRLocalMetadata
			testPutOAuthClientPRLocalMetadataByExternalReferenceCode_addOAuthClientPRLocalMetadata()
		throws Exception {

		return oAuthClientPRLocalMetadataResource.
			postOAuthClientPRLocalMetadata(randomOAuthClientPRLocalMetadata());
	}

}