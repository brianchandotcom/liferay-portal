/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.asset.library.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.asset.library.client.dto.v1_0.Site;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
public class SiteResourceTest extends BaseSiteResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_testSite = _addSite();
	}

	@Ignore
	@Override
	@Test
	public void testBatchEngineDeleteImportTask() {
	}

	@Override
	protected Site randomIrrelevantSite() throws Exception {
		return _addSite();
	}

	@Override
	protected Site randomSite() throws Exception {
		return _addSite();
	}

	@Override
	protected Site
			testDeleteAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeConnectedSiteByExternalReferenceCodeSiteExternalReferenceCode_addSite()
		throws Exception {

		return siteResource.putAssetLibraryConnectedSite(
			testDepotEntry.getGroupId(), _testSite.getId(), new Site());
	}

	@Override
	protected String
			testDeleteAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeConnectedSiteByExternalReferenceCodeSiteExternalReferenceCode_getAssetLibraryExternalReferenceCode()
		throws Exception {

		Group group = testDepotEntry.getGroup();

		return group.getExternalReferenceCode();
	}

	@Override
	protected Site testDeleteAssetLibraryConnectedSite_addSite()
		throws Exception {

		return siteResource.putAssetLibraryConnectedSite(
			testDepotEntry.getGroupId(), _testSite.getId(), new Site());
	}

	@Override
	protected Long testDeleteAssetLibraryConnectedSite_getAssetLibraryId() {
		return testDepotEntry.getGroupId();
	}

	@Override
	protected Site
			testGetAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeConnectedSiteByExternalReferenceCodeSiteExternalReferenceCode_addSite()
		throws Exception {

		return siteResource.putAssetLibraryConnectedSite(
			testDepotEntry.getGroupId(), _testSite.getId(), new Site());
	}

	@Override
	protected String
			testGetAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeConnectedSiteByExternalReferenceCodeSiteExternalReferenceCode_getAssetLibraryExternalReferenceCode()
		throws Exception {

		Group group = testDepotEntry.getGroup();

		return group.getExternalReferenceCode();
	}

	@Override
	protected Site
			testGetAssetLibraryByExternalReferenceCodeConnectedSitesPage_addSite(
				String externalReferenceCode, Site site)
		throws Exception {

		return siteResource.putAssetLibraryConnectedSite(
			testDepotEntry.getGroupId(), site.getId(), new Site());
	}

	@Override
	protected String
			testGetAssetLibraryByExternalReferenceCodeConnectedSitesPage_getExternalReferenceCode()
		throws Exception {

		Group group = testDepotEntry.getGroup();

		return group.getExternalReferenceCode();
	}

	@Override
	protected Site testGetAssetLibraryConnectedSite_addSite() throws Exception {
		return siteResource.putAssetLibraryConnectedSite(
			testDepotEntry.getGroupId(), _testSite.getId(), new Site());
	}

	@Override
	protected Long testGetAssetLibraryConnectedSite_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getGroupId();
	}

	@Override
	protected Site testGetAssetLibraryConnectedSitesPage_addSite(
			Long assetLibraryId, Site site)
		throws Exception {

		return siteResource.putAssetLibraryConnectedSite(
			assetLibraryId, site.getId(), new Site());
	}

	@Override
	protected Site
		testPutAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeConnectedSiteByExternalReferenceCodeSiteExternalReferenceCode_addSite() {

		return _testSite;
	}

	@Override
	protected String
			testPutAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeConnectedSiteByExternalReferenceCodeSiteExternalReferenceCode_getAssetLibraryExternalReferenceCode()
		throws Exception {

		Group group = testDepotEntry.getGroup();

		return group.getExternalReferenceCode();
	}

	@Override
	protected Site testPutAssetLibraryConnectedSite_addSite() {
		return _testSite;
	}

	@Override
	protected Long testPutAssetLibraryConnectedSite_getAssetLibraryId() {
		return testDepotEntry.getGroupId();
	}

	private Site _addSite() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_groups.add(group);

		return new Site() {
			{
				externalReferenceCode = group.getExternalReferenceCode();
				id = group.getGroupId();
				name = group.getName();
				name_i18n = LocalizedMapUtil.getI18nMap(
					true, group.getNameMap());
				searchable = false;
			}
		};
	}

	@DeleteAfterTestRun
	private List<Group> _groups = new ArrayList<>();

	private Site _testSite;

}