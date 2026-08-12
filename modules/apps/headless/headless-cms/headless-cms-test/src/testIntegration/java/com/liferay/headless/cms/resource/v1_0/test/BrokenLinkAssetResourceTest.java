/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cms.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.headless.cms.client.dto.v1_0.BrokenLinkAsset;
import com.liferay.headless.cms.client.pagination.Page;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.Serializable;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@FeatureFlag("LPD-82226")
@RunWith(Arquillian.class)
public class BrokenLinkAssetResourceTest
	extends BaseBrokenLinkAssetResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	@Test
	public void testEscapeRegexInStringFields() throws Exception {
	}

	@Override
	@Test
	public void testGetBrokenLinkAssetsPage() throws Exception {
		_testGetBrokenLinkAssetsPage(1);
		_testGetBrokenLinkAssetsPage(3);
	}

	@Override
	@Test
	public void testGetBrokenLinkAssetsPageWithPagination() throws Exception {
	}

	@Override
	@Test
	public void testGetBrokenLinkAssetsPageWithSortDateTime() throws Exception {
	}

	@Override
	@Test
	public void testGetBrokenLinkAssetsPageWithSortDouble() throws Exception {
	}

	@Override
	@Test
	public void testGetBrokenLinkAssetsPageWithSortInteger() throws Exception {
	}

	@Override
	@Test
	public void testGetBrokenLinkAssetsPageWithSortString() throws Exception {
	}

	private ObjectEntry _addObjectEntry(
			String content, DepotEntry depotEntry,
			ObjectDefinition objectDefinition, String title)
		throws Exception {

		ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					"L_CONTENTS", depotEntry.getGroupId(),
					depotEntry.getCompanyId());

		return _objectEntryLocalService.addObjectEntry(
			depotEntry.getGroupId(), depotEntry.getUserId(),
			objectDefinition.getObjectDefinitionId(),
			objectEntryFolder.getObjectEntryFolderId(), "en_US",
			HashMapBuilder.<String, Serializable>put(
				"content_i18n",
				HashMapBuilder.put(
					"en_US", content
				).build()
			).put(
				"title_i18n",
				HashMapBuilder.put(
					"en_US", title
				).build()
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

	private DepotEntry _addSpaceDepotEntry(ServiceContext serviceContext)
		throws Exception {

		return _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), StringUtil.randomString()
			).build(),
			DepotConstants.TYPE_SPACE, serviceContext);
	}

	private ObjectDefinition _getBasicWebContentObjectDefinition()
		throws Exception {

		Group cmsGroup = _groupLocalService.getGroup(
			TestPropsValues.getCompanyId(), GroupConstants.CMS);

		return _objectDefinitionLocalService.
			getObjectDefinitionByExternalReferenceCode(
				"L_CMS_BASIC_WEB_CONTENT", cmsGroup.getCompanyId());
	}

	private String _getImageHTML(String externalReferenceCode) {
		return StringBundler.concat(
			"<img src=\"/documents/20125/0/image.jpg/", StringUtil.randomId(),
			"?download=true&amp;objectDefinitionExternalReferenceCode=",
			"L_CMS_BASIC_DOCUMENT&amp;objectEntryExternalReferenceCode=",
			externalReferenceCode,
			"&amp;objectFieldExternalReferenceCode=FILE\">");
	}

	private void _testGetBrokenLinkAssetsPage(int expiredAssetCount)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		DepotEntry depotEntry = _addSpaceDepotEntry(serviceContext);

		try {
			ObjectDefinition objectDefinition =
				_getBasicWebContentObjectDefinition();

			StringBundler sb = new StringBundler(expiredAssetCount);

			String firstTargetTitle = null;

			for (int i = 0; i < expiredAssetCount; i++) {
				String targetTitle = RandomTestUtil.randomString();

				if (firstTargetTitle == null) {
					firstTargetTitle = targetTitle;
				}

				ObjectEntry targetObjectEntry = _addObjectEntry(
					RandomTestUtil.randomString(), depotEntry, objectDefinition,
					targetTitle);

				sb.append(
					_getImageHTML(
						targetObjectEntry.getExternalReferenceCode()));

				_objectEntryLocalService.updateStatus(
					TestPropsValues.getUserId(),
					targetObjectEntry.getObjectEntryId(),
					WorkflowConstants.STATUS_EXPIRED, serviceContext);
			}

			String referencingTitle = RandomTestUtil.randomString();

			_addObjectEntry(
				sb.toString(), depotEntry, objectDefinition, referencingTitle);

			Page<BrokenLinkAsset> page =
				brokenLinkAssetResource.getBrokenLinkAssetsPage(
					depotEntry.getDepotEntryId(), null, null, null);

			Assert.assertEquals(1, page.getTotalCount());

			List<BrokenLinkAsset> brokenLinkAssets =
				(List<BrokenLinkAsset>)page.getItems();

			BrokenLinkAsset brokenLinkAsset = brokenLinkAssets.get(0);

			Assert.assertEquals(referencingTitle, brokenLinkAsset.getTitle());
			Assert.assertEquals(
				expiredAssetCount,
				GetterUtil.getInteger(brokenLinkAsset.getBrokenLinkCount()));
			Assert.assertEquals(
				"L_CMS_BASIC_WEB_CONTENT",
				brokenLinkAsset.getObjectDefinitionExternalReferenceCode());

			if (expiredAssetCount == 1) {
				Assert.assertEquals(
					firstTargetTitle, brokenLinkAsset.getBrokenLinkTitle());
			}
		}
		finally {
			_depotEntryLocalService.deleteDepotEntry(
				depotEntry.getDepotEntryId());
		}
	}

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

}