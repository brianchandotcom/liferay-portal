/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.internal.upgrade.v1_6_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth.client.persistence.constants.OAuthClientEntryConstants;
import com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata;
import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.oauth.client.persistence.service.OAuthClientASLocalMetadataLocalService;
import com.liferay.oauth.client.persistence.service.OAuthClientEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.ExternalReferenceCodeModel;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.BaseExternalReferenceCodeUpgradeProcessTestCase;

import org.junit.runner.RunWith;

/**
 * @author Manuele Castro
 */
@RunWith(Arquillian.class)
public class OAuthClientExternalReferenceCodeUpgradeProcessTest
	extends BaseExternalReferenceCodeUpgradeProcessTestCase {

	@Override
	protected ExternalReferenceCodeModel[] addExternalReferenceCodeModels(
			String tableName)
		throws PortalException {

		if (tableName.equals("OAuthClientASLocalMetadata")) {
			String url = Http.HTTPS_WITH_SLASH + RandomTestUtil.randomString();

			return new ExternalReferenceCodeModel[] {
				_oAuthClientASLocalMetadataLocalService.
					addOAuthClientASLocalMetadata(
						null, TestPropsValues.getUserId(), url, url, url, false,
						url, new String[] {RandomTestUtil.randomString()},
						new String[] {RandomTestUtil.randomString()},
						new String[] {"public"}, url, url)
			};
		}

		JSONObject jsonObject = JSONUtil.put(
			"client_id", RandomTestUtil.randomString());

		return new ExternalReferenceCodeModel[] {
			_oAuthClientEntryLocalService.addOAuthClientEntry(
				null, TestPropsValues.getUserId(), jsonObject.toString(),
				"https://accounts.google.com/.well-known/openid-configuration",
				null, jsonObject.toString(), null,
				OAuthClientEntryConstants.METADATA_CACHE_TIME_DEFAULT,
				OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON,
				jsonObject.toString())
		};
	}

	@Override
	protected ExternalReferenceCodeModel fetchExternalReferenceCodeModel(
			ExternalReferenceCodeModel externalReferenceCodeModel,
			String tableName)
		throws PortalException {

		if (tableName.equals("OAuthClientASLocalMetadata")) {
			OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
				(OAuthClientASLocalMetadata)externalReferenceCodeModel;

			return _oAuthClientASLocalMetadataLocalService.
				fetchOAuthClientASLocalMetadata(
					oAuthClientASLocalMetadata.
						getOAuthClientASLocalMetadataId());
		}

		OAuthClientEntry oAuthClientEntry =
			(OAuthClientEntry)externalReferenceCodeModel;

		return _oAuthClientEntryLocalService.fetchOAuthClientEntry(
			oAuthClientEntry.getOAuthClientEntryId());
	}

	@Override
	protected String[] getTableNames() {
		return new String[] {"OAuthClientASLocalMetadata", "OAuthClientEntry"};
	}

	@Override
	protected UpgradeStepRegistrator getUpgradeStepRegistrator() {
		return _upgradeStepRegistrator;
	}

	@Override
	protected Version getVersion() {
		return new Version(1, 5, 1);
	}

	@Inject(
		filter = "(&(component.name=com.liferay.oauth.client.persistence.internal.upgrade.registry.OAuthClientPersistenceServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private OAuthClientASLocalMetadataLocalService
		_oAuthClientASLocalMetadataLocalService;

	@Inject
	private OAuthClientEntryLocalService _oAuthClientEntryLocalService;

}