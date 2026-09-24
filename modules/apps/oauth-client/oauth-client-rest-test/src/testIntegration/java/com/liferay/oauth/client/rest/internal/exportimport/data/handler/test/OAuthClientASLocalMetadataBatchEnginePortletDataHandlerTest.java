/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.rest.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.oauth.client.persistence.model.OAuthClientASLocalMetadata;
import com.liferay.oauth.client.persistence.service.OAuthClientASLocalMetadataLocalService;
import com.liferay.oauth.client.rest.resource.v1_0.OAuthClientASLocalMetadataResource;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@FeatureFlags(featureFlags = @FeatureFlag(value = "LPD-49855"))
@RunWith(Arquillian.class)
public class OAuthClientASLocalMetadataBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected String addEntry(long groupId, long userId, Date dateModified)
		throws Exception {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			_oAuthClientASLocalMetadataLocalService.
				addOAuthClientASLocalMetadata(
					RandomTestUtil.randomString(), userId, _randomURL(),
					_randomURL(), _randomURL(), false, _randomURL(),
					new String[] {"authorization_code"},
					new String[] {"openid"}, new String[] {"public"},
					_randomURL(), _randomURL());

		oAuthClientASLocalMetadata.setModifiedDate(dateModified);

		oAuthClientASLocalMetadata =
			_oAuthClientASLocalMetadataLocalService.
				updateOAuthClientASLocalMetadata(oAuthClientASLocalMetadata);

		return oAuthClientASLocalMetadata.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(long groupId, String externalReferenceCode)
		throws Exception {

		_oAuthClientASLocalMetadataLocalService.
			deleteOAuthClientASLocalMetadata(
				_getOAuthClientASLocalMetadata(groupId, externalReferenceCode));
	}

	@Override
	protected long getCreatorUserId(long groupId, String externalReferenceCode)
		throws Exception {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			_getOAuthClientASLocalMetadata(groupId, externalReferenceCode);

		return oAuthClientASLocalMetadata.getUserId();
	}

	@Override
	protected Object getEntryValue(long groupId, String externalReferenceCode)
		throws Exception {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			_getOAuthClientASLocalMetadata(groupId, externalReferenceCode);

		JSONObject metadataJSONObject = _jsonFactory.createJSONObject(
			oAuthClientASLocalMetadata.getMetadataJSON());

		return metadataJSONObject.getString("token_endpoint");
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			OAuthClientASLocalMetadataResource.class);
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		long companyId = _getCompanyId(groupId);

		return TransformUtil.transform(
			ListUtil.filter(
				_oAuthClientASLocalMetadataLocalService.
					getOAuthClientASLocalMetadatas(
						QueryUtil.ALL_POS, QueryUtil.ALL_POS),
				oAuthClientASLocalMetadata ->
					oAuthClientASLocalMetadata.getCompanyId() == companyId),
			OAuthClientASLocalMetadata::getExternalReferenceCode);
	}

	@Override
	protected long getPrimaryKey(long groupId, String externalReferenceCode)
		throws Exception {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			_getOAuthClientASLocalMetadata(groupId, externalReferenceCode);

		return oAuthClientASLocalMetadata.getOAuthClientASLocalMetadataId();
	}

	@Override
	protected Scope getScope() {
		return Scope.COMPANY;
	}

	@Override
	protected boolean supportsComments() {
		return false;
	}

	@Override
	protected boolean supportsEmptyEntries() {
		return false;
	}

	@Override
	protected boolean supportsPermissions() {
		return true;
	}

	@Override
	protected void updateEntry(long groupId, String externalReferenceCode)
		throws Exception {

		OAuthClientASLocalMetadata oAuthClientASLocalMetadata =
			_getOAuthClientASLocalMetadata(groupId, externalReferenceCode);

		_oAuthClientASLocalMetadataLocalService.
			updateOAuthClientASLocalMetadata(
				oAuthClientASLocalMetadata.getOAuthClientASLocalMetadataId(),
				_randomURL(), oAuthClientASLocalMetadata.getIssuer(),
				_randomURL(),
				oAuthClientASLocalMetadata.isLocalWellKnownEnabled(),
				_randomURL(), new String[] {"authorization_code"},
				new String[] {"openid"}, new String[] {"public"}, _randomURL(),
				_randomURL());
	}

	private long _getCompanyId(long groupId) throws Exception {
		Group group = _groupLocalService.getGroup(groupId);

		return group.getCompanyId();
	}

	private OAuthClientASLocalMetadata _getOAuthClientASLocalMetadata(
			long groupId, String externalReferenceCode)
		throws Exception {

		return _oAuthClientASLocalMetadataLocalService.
			fetchOAuthClientASLocalMetadataByExternalReferenceCode(
				externalReferenceCode, _getCompanyId(groupId));
	}

	private String _randomURL() {
		return "http://localhost/" + RandomTestUtil.randomString();
	}

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private OAuthClientASLocalMetadataLocalService
		_oAuthClientASLocalMetadataLocalService;

}