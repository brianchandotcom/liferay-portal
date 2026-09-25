/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.rest.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.oauth.client.persistence.constants.OAuthClientEntryConstants;
import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.oauth.client.persistence.service.OAuthClientEntryLocalService;
import com.liferay.oauth.client.rest.resource.v1_0.OAuthClientEntryResource;
import com.liferay.oauth.client.test.util.OpenIdConnectProviderHttpServer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@FeatureFlags(featureFlags = @FeatureFlag(value = "LPD-49855"))
@RunWith(Arquillian.class)
public class OAuthClientEntryBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_openIdConnectProviderHttpServer =
			new OpenIdConnectProviderHttpServer();
	}

	@After
	public void tearDown() {
		if (_openIdConnectProviderHttpServer != null) {
			_openIdConnectProviderHttpServer.close();

			_openIdConnectProviderHttpServer = null;
		}
	}

	@Override
	protected String addEntry(long groupId, long userId, Date dateModified)
		throws Exception {

		OAuthClientEntry oAuthClientEntry =
			_oAuthClientEntryLocalService.addOAuthClientEntry(
				RandomTestUtil.randomString(), userId, _randomJSON(),
				_openIdConnectProviderHttpServer.getURL(), _randomJSON(),
				_getInfoJSON(), "email", 0,
				OAuthClientEntryConstants.OIDC_USER_INFO_MAPPER_JSON, 0,
				_randomJSON());

		oAuthClientEntry.setModifiedDate(dateModified);

		oAuthClientEntry = _oAuthClientEntryLocalService.updateOAuthClientEntry(
			oAuthClientEntry);

		return oAuthClientEntry.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(long groupId, String externalReferenceCode)
		throws Exception {

		_oAuthClientEntryLocalService.deleteOAuthClientEntry(
			_getOAuthClientEntry(groupId, externalReferenceCode));
	}

	@Override
	protected long getCreatorUserId(long groupId, String externalReferenceCode)
		throws Exception {

		OAuthClientEntry oAuthClientEntry = _getOAuthClientEntry(
			groupId, externalReferenceCode);

		return oAuthClientEntry.getUserId();
	}

	@Override
	protected Object getEntryValue(long groupId, String externalReferenceCode)
		throws Exception {

		OAuthClientEntry oAuthClientEntry = _getOAuthClientEntry(
			groupId, externalReferenceCode);

		return oAuthClientEntry.getMatcherField();
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			OAuthClientEntryResource.class);
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		return TransformUtil.transform(
			_oAuthClientEntryLocalService.getCompanyOAuthClientEntries(
				_getCompanyId(groupId)),
			OAuthClientEntry::getExternalReferenceCode);
	}

	@Override
	protected long getPrimaryKey(long groupId, String externalReferenceCode)
		throws Exception {

		OAuthClientEntry oAuthClientEntry = _getOAuthClientEntry(
			groupId, externalReferenceCode);

		return oAuthClientEntry.getOAuthClientEntryId();
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
		return false;
	}

	@Override
	protected void updateEntry(long groupId, String externalReferenceCode)
		throws Exception {

		OAuthClientEntry oAuthClientEntry = _getOAuthClientEntry(
			groupId, externalReferenceCode);

		_oAuthClientEntryLocalService.updateOAuthClientEntry(
			oAuthClientEntry.getOAuthClientEntryId(),
			oAuthClientEntry.getAuthRequestParametersJSON(),
			oAuthClientEntry.getAuthServerWellKnownURI(),
			oAuthClientEntry.getCustomClaimsJSON(),
			oAuthClientEntry.getInfoJSON(), RandomTestUtil.randomString(),
			oAuthClientEntry.getMetadataCacheTime(),
			oAuthClientEntry.getOIDCUserInfoMapperJSON(),
			oAuthClientEntry.getTokenConnectionTimeout(),
			oAuthClientEntry.getTokenRequestParametersJSON());
	}

	private long _getCompanyId(long groupId) throws Exception {
		Group group = _groupLocalService.getGroup(groupId);

		return group.getCompanyId();
	}

	private String _getInfoJSON() {
		return JSONUtil.put(
			"client_id", RandomTestUtil.randomString()
		).put(
			"client_name", "example_client"
		).put(
			"client_secret", RandomTestUtil.randomString()
		).put(
			"scope", "openid email profile"
		).put(
			"subject_type", "public"
		).toString();
	}

	private OAuthClientEntry _getOAuthClientEntry(
			long groupId, String externalReferenceCode)
		throws Exception {

		return _oAuthClientEntryLocalService.
			fetchOAuthClientEntryByExternalReferenceCode(
				externalReferenceCode, _getCompanyId(groupId));
	}

	private String _randomJSON() {
		return JSONUtil.put(
			RandomTestUtil.randomString(), RandomTestUtil.randomString()
		).toString();
	}

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private OAuthClientEntryLocalService _oAuthClientEntryLocalService;

	private OpenIdConnectProviderHttpServer _openIdConnectProviderHttpServer;

}