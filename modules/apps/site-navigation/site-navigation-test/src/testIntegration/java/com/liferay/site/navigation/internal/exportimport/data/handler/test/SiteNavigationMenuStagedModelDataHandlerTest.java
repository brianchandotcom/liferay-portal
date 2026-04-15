/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.test.util.SiteNavigationMenuTestUtil;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class SiteNavigationMenuStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testImportWithExistingExternalReferenceCode() throws Exception {
		initExport();

		SiteNavigationMenu siteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(stagingGroup);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, siteNavigationMenu);

		SiteNavigationMenu existingSiteNavigationMenu =
			SiteNavigationMenuTestUtil.addSiteNavigationMenu(liveGroup);

		existingSiteNavigationMenu.setExternalReferenceCode(
			siteNavigationMenu.getExternalReferenceCode());

		existingSiteNavigationMenu =
			_siteNavigationMenuLocalService.updateSiteNavigationMenu(
				existingSiteNavigationMenu);

		try (SafeCloseable safeCloseable = initImportWithSafeCloseable()) {
			SiteNavigationMenu exportedSiteNavigationMenu =
				(SiteNavigationMenu)readExportedStagedModel(siteNavigationMenu);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, exportedSiteNavigationMenu);

			SiteNavigationMenu importedSiteNavigationMenu =
				_siteNavigationMenuLocalService.
					fetchSiteNavigationMenuByExternalReferenceCode(
						siteNavigationMenu.getExternalReferenceCode(),
						liveGroup.getGroupId());

			Assert.assertEquals(
				existingSiteNavigationMenu.getSiteNavigationMenuId(),
				importedSiteNavigationMenu.getSiteNavigationMenuId());
			Assert.assertEquals(
				siteNavigationMenu.getUuid(),
				importedSiteNavigationMenu.getUuid());
		}
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		return SiteNavigationMenuTestUtil.addSiteNavigationMenu(group);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return _siteNavigationMenuLocalService.
			getSiteNavigationMenuByUuidAndGroupId(uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return SiteNavigationMenu.class;
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		SiteNavigationMenu siteNavigationMenu = (SiteNavigationMenu)stagedModel;
		SiteNavigationMenu importedSiteNavigationMenu =
			(SiteNavigationMenu)importedStagedModel;

		Assert.assertEquals(
			siteNavigationMenu.getName(), importedSiteNavigationMenu.getName());
	}

	@Inject
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

}