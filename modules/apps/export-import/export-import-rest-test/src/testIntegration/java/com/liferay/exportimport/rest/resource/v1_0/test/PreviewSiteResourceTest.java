/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.rest.client.dto.v1_0.PreviewSite;
import com.liferay.exportimport.rest.client.pagination.Page;
import com.liferay.exportimport.rest.client.pagination.Pagination;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Petteri Karttunen
 */
@FeatureFlags(
	featureFlags = {
		@FeatureFlag(value = "LPD-57655"), @FeatureFlag("LPD-85946")
	}
)
@RunWith(Arquillian.class)
public class PreviewSiteResourceTest extends BasePreviewSiteResourceTestCase {

	@Override
	@Test
	public void testGetExportPreviewSitesPage() throws Exception {
		super.testGetExportPreviewSitesPage();

		_testGetExportPreviewSitesPage();
		_testGetExportPreviewSitesPageWithInactiveSite();
		_testGetExportPreviewSitesPageWithSearch();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"externalReferenceCode"};
	}

	@Override
	protected PreviewSite testGetExportPreviewSitesPage_addPreviewSite(
			PreviewSite previewSite)
		throws Exception {

		Group group = _addGroup();

		previewSite.setExternalReferenceCode(group.getExternalReferenceCode());

		return previewSite;
	}

	private Group _addGroup() throws Exception {
		return _addGroup(0);
	}

	private Group _addGroup(long parentGroupId) throws Exception {
		Group group = GroupTestUtil.addGroup(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			parentGroupId, RandomTestUtil.randomString());

		_groups.add(0, group);

		return group;
	}

	private PreviewSite _getExportPreviewSite(String externalReferenceCode)
		throws Exception {

		Page<PreviewSite> page = previewSiteResource.getExportPreviewSitesPage(
			null, null, null);

		page = previewSiteResource.getExportPreviewSitesPage(
			null, Pagination.of(1, (int)page.getTotalCount()), null);

		for (PreviewSite previewSite : page.getItems()) {
			if (externalReferenceCode.equals(
					previewSite.getExternalReferenceCode())) {

				return previewSite;
			}
		}

		return null;
	}

	private void _testGetExportPreviewSitesPage() throws Exception {
		Group group = _addGroup();

		Group childGroup = _addGroup(group.getGroupId());

		PreviewSite previewSite = _getExportPreviewSite(
			group.getExternalReferenceCode());

		Assert.assertEquals(
			Integer.valueOf(1), previewSite.getChildSiteCount());
		Assert.assertEquals(
			group.getDescriptiveName(), previewSite.getDescriptiveName());

		PreviewSite childPreviewSite = _getExportPreviewSite(
			childGroup.getExternalReferenceCode());

		Assert.assertEquals(
			previewSite.getPath() + " / " + childGroup.getDescriptiveName(),
			childPreviewSite.getPath());
	}

	private void _testGetExportPreviewSitesPageWithInactiveSite()
		throws Exception {

		Group group = _addGroup();

		group.setActive(false);

		group = _groupLocalService.updateGroup(group);

		Assert.assertNull(
			_getExportPreviewSite(group.getExternalReferenceCode()));
	}

	private void _testGetExportPreviewSitesPageWithSearch() throws Exception {
		Group group = _addGroup();

		_addGroup();

		Page<PreviewSite> page = previewSiteResource.getExportPreviewSitesPage(
			group.getDescriptiveName(), Pagination.of(1, 10), null);

		Assert.assertEquals(1, page.getTotalCount());

		List<PreviewSite> previewSites = (List<PreviewSite>)page.getItems();

		PreviewSite previewSite = previewSites.get(0);

		Assert.assertEquals(
			group.getExternalReferenceCode(),
			previewSite.getExternalReferenceCode());
	}

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private final List<Group> _groups = new ArrayList<>();

}