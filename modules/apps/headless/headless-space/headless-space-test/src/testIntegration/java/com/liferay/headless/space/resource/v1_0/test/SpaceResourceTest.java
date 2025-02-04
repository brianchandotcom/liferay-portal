/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.space.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.headless.space.client.dto.v1_0.Space;
import com.liferay.headless.space.client.problem.Problem;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@FeatureFlags("LPD-32649")
@RunWith(Arquillian.class)
public class SpaceResourceTest extends BaseSpaceResourceTestCase {

	@Override
	@Test
	public void testDeleteSpace() throws Exception {
		super.testDeleteSpace();

		// Nonexistent space ID

		long siteId = RandomTestUtil.randomLong();

		try {
			spaceResource.deleteSpace(siteId);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	@Override
	@Test
	public void testDeleteSpaceLinkToSite() throws Exception {
		Space space = testDeleteSpaceLinkToSite_addSpace();

		_assertLinkedSites(space);

		space = spaceResource.deleteSpaceLinkToSite(
			space.getId(), testGroup.getGroupId());

		Assert.assertTrue(ArrayUtil.isEmpty(space.getLinkedSiteIds()));
		Assert.assertTrue(
			ArrayUtil.isEmpty(space.getLinkedSitesExternalReferenceCodes()));
	}

	@Override
	@Test
	public void testPatchSpace() throws Exception {
		super.testPatchSpace();
	}

	@Override
	@Test
	public void testPostSpaceLinkToSite() throws Exception {
		Space space = testPostSpaceLinkToSite_addSpace(randomSpace());

		_assertLinkedSites(space);
	}

	@Override
	protected Space randomPatchSpace() throws Exception {
		Space space = randomSpace();

		space.setName(RandomTestUtil.randomString());

		return space;
	}

	@Override
	protected Space testDeleteSpace_addSpace() throws Exception {
		return _addRandomSpace();
	}

	@Override
	protected Space testDeleteSpaceLinkToSite_addSpace() throws Exception {
		Space space = _addRandomSpace();

		DepotEntry depotEntry = _depotEntryLocalService.getGroupDepotEntry(
			space.getId());

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntry.getDepotEntryId(), testGroup.getGroupId());

		return spaceResource.getSpace(space.getId());
	}

	@Override
	protected Space testGetSpace_addSpace() throws Exception {
		return _addRandomSpace();
	}

	@Override
	protected Space testPatchSpace_addSpace() throws Exception {
		return _addRandomSpace();
	}

	@Override
	protected Space testPostSpace_addSpace(Space space) throws Exception {
		return spaceResource.postSpace(space);
	}

	@Override
	protected Space testPostSpaceLinkToSite_addSpace(Space space)
		throws Exception {

		space = spaceResource.postSpace(space);

		DepotEntry depotEntry = _depotEntryLocalService.getGroupDepotEntry(
			space.getId());

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntry.getDepotEntryId(), testGroup.getGroupId());

		return spaceResource.getSpace(space.getId());
	}

	private Space _addRandomSpace() throws Exception {
		return spaceResource.postSpace(randomSpace());
	}

	private void _assertLinkedSites(Space space) {
		Long[] linkedSiteIds = space.getLinkedSiteIds();
		String[] linkedSitesExternalReferenceCodes =
			space.getLinkedSitesExternalReferenceCodes();

		Assert.assertEquals(testGroup.getGroupId(), (long)linkedSiteIds[0]);
		Assert.assertEquals(
			testGroup.getExternalReferenceCode(),
			linkedSitesExternalReferenceCodes[0]);
	}

	@Inject
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

}