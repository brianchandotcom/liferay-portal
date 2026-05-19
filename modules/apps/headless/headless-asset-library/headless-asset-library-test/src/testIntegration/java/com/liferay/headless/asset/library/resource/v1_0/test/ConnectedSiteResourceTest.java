/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.asset.library.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.asset.library.client.dto.v1_0.ConnectedSite;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@FeatureFlag("LPD-17564")
@RunWith(Arquillian.class)
public class ConnectedSiteResourceTest
	extends BaseConnectedSiteResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_testConnectedSite = _addConnectedSite();
	}

	@Ignore
	@Override
	@Test
	public void testBatchEngineDeleteImportTask() {
	}

	@Override
	@Test
	public void testPutAssetLibraryConnectedSite() throws Exception {
		super.testPutAssetLibraryConnectedSite();

		_testPutAssetLibraryConnectedSiteReturnsTypeSite();
		_testPutAssetLibraryConnectedSiteReturnsTypeSiteTemplate();
	}

	@Override
	protected ConnectedSite randomConnectedSite() throws Exception {
		return _addConnectedSite();
	}

	@Override
	protected ConnectedSite randomIrrelevantConnectedSite() throws Exception {
		return _addConnectedSite();
	}

	@Override
	protected ConnectedSite
			testDeleteAssetLibraryConnectedSite_addConnectedSite()
		throws Exception {

		return connectedSiteResource.putAssetLibraryConnectedSite(
			testDeleteAssetLibraryConnectedSite_getAssetLibraryExternalReferenceCode(),
			_testConnectedSite.getExternalReferenceCode(), new ConnectedSite());
	}

	@Override
	protected String
			testDeleteAssetLibraryConnectedSite_getAssetLibraryExternalReferenceCode()
		throws Exception {

		Group group = testDepotEntry.getGroup();

		return group.getExternalReferenceCode();
	}

	@Override
	protected ConnectedSite testGetAssetLibraryConnectedSite_addConnectedSite()
		throws Exception {

		return connectedSiteResource.putAssetLibraryConnectedSite(
			testGetAssetLibraryConnectedSite_getAssetLibraryExternalReferenceCode(),
			_testConnectedSite.getExternalReferenceCode(), new ConnectedSite());
	}

	@Override
	protected String
			testGetAssetLibraryConnectedSite_getAssetLibraryExternalReferenceCode()
		throws Exception {

		Group group = testDepotEntry.getGroup();

		return group.getExternalReferenceCode();
	}

	@Override
	protected ConnectedSite
			testGetAssetLibraryConnectedSitesPage_addConnectedSite(
				String assetLibraryExternalReferenceCode,
				ConnectedSite connectedSite)
		throws Exception {

		return connectedSiteResource.putAssetLibraryConnectedSite(
			assetLibraryExternalReferenceCode,
			connectedSite.getExternalReferenceCode(), connectedSite);
	}

	@Override
	protected String
			testGetAssetLibraryConnectedSitesPage_getAssetLibraryExternalReferenceCode()
		throws Exception {

		Group group = testDepotEntry.getGroup();

		return group.getExternalReferenceCode();
	}

	@Override
	protected ConnectedSite
		testPutAssetLibraryConnectedSite_addConnectedSite() {

		return _testConnectedSite;
	}

	@Override
	protected String
			testPutAssetLibraryConnectedSite_getAssetLibraryExternalReferenceCode()
		throws Exception {

		Group group = testDepotEntry.getGroup();

		return group.getExternalReferenceCode();
	}

	private ConnectedSite _addConnectedSite() throws Exception {
		Group group = GroupTestUtil.addGroup();

		return new ConnectedSite() {
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

	private void _testPutAssetLibraryConnectedSiteReturnsTypeSite()
		throws Exception {

		Group group = testDepotEntry.getGroup();

		ConnectedSite connectedSite =
			connectedSiteResource.putAssetLibraryConnectedSite(
				group.getExternalReferenceCode(),
				_testConnectedSite.getExternalReferenceCode(),
				new ConnectedSite());

		Assert.assertEquals(ConnectedSite.Type.SITE, connectedSite.getType());
	}

	private void _testPutAssetLibraryConnectedSiteReturnsTypeSiteTemplate()
		throws Exception {

		LayoutSetPrototype layoutSetPrototype =
			_layoutSetPrototypeLocalService.addLayoutSetPrototype(
				TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
				HashMapBuilder.put(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()
				).build(),
				new HashMap<>(), true, true, new ServiceContext());

		Group group1 = testDepotEntry.getGroup();
		Group group2 = layoutSetPrototype.getGroup();

		ConnectedSite connectedSite =
			connectedSiteResource.putAssetLibraryConnectedSite(
				group1.getExternalReferenceCode(),
				group2.getExternalReferenceCode(), new ConnectedSite());

		Assert.assertEquals(
			ConnectedSite.Type.SITE_TEMPLATE, connectedSite.getType());
	}

	@Inject
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

	private ConnectedSite _testConnectedSite;

}