/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.asset.model.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.AssetRendererFactoryCustomizer;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.exportimport.kernel.service.StagingLocalService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.io.ByteArrayInputStream;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class DepotAssetRendererFactoryWrapperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetAssetRenderer() throws Exception {
		_testGetAssetRenderer(
			assetRenderer -> Assert.assertNull(assetRenderer), false);
		_testGetAssetRenderer(
			assetRenderer -> Assert.assertNotNull(assetRenderer), true);
	}

	private DLFileEntry _addDLFileEntry(Group group) throws Exception {
		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		return _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, new ByteArrayInputStream(bytes), bytes.length, null, null,
			null,
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));
	}

	private Group _enableLocalStaging(Group group) throws Exception {
		_stagingLocalService.enableLocalStaging(
			TestPropsValues.getUserId(), group, false, false,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		return group.getStagingGroup();
	}

	private ServiceContext _getServiceContext(Group group) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setScopeGroupId(group.getGroupId());

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		serviceContext.setRequest(httpServletRequest);

		return serviceContext;
	}

	private void _testGetAssetRenderer(
			UnsafeConsumer<AssetRenderer<?>, Exception> unsafeConsumer,
			boolean addDepotEntryGroupRel)
		throws Exception {

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			new HashMap<>(), DepotConstants.TYPE_ASSET_LIBRARY,
			ServiceContextTestUtil.getServiceContext());

		Group group = GroupTestUtil.addGroup();

		DLFileEntry dlFileEntry = _addDLFileEntry(depotEntry.getGroup());

		_enableLocalStaging(depotEntry.getGroup());

		if (addDepotEntryGroupRel) {
			_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
				depotEntry.getDepotEntryId(), group.getGroupId());
		}

		Group stagedGroup = _enableLocalStaging(group);

		AssetRendererFactory<Object> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				DLFileEntry.class.getName());

		AssetRendererFactory<?> customizedAssetRendererFactory =
			_assetRendererFactory.customize(assetRendererFactory);

		ServiceContextThreadLocal.pushServiceContext(
			_getServiceContext(stagedGroup));

		try {
			unsafeConsumer.accept(
				customizedAssetRendererFactory.getAssetRenderer(
					dlFileEntry.getFileEntryId()));
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();

			_stagingLocalService.disableStaging(
				group, ServiceContextTestUtil.getServiceContext());

			_groupLocalService.deleteGroup(group);

			_stagingLocalService.disableStaging(
				depotEntry.getGroup(),
				ServiceContextTestUtil.getServiceContext());

			_depotEntryLocalService.deleteDepotEntry(depotEntry);
		}
	}

	@Inject
	private AssetRendererFactoryCustomizer _assetRendererFactory;

	@Inject
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private StagingLocalService _stagingLocalService;

}