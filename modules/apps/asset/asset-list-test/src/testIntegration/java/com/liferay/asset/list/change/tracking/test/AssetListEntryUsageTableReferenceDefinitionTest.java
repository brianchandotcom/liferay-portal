/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryUsageLocalService;
import com.liferay.asset.list.util.AssetListTestUtil;
import com.liferay.change.tracking.test.util.BaseTableReferenceDefinitionTestCase;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Cheryl Tang
 */
@RunWith(Arquillian.class)
public class AssetListEntryUsageTableReferenceDefinitionTest
	extends BaseTableReferenceDefinitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_assetListEntry = AssetListTestUtil.addAssetListEntry(
			group.getGroupId());
		_layout = LayoutTestUtil.addTypePortletLayout(group.getGroupId());
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return _assetListEntryUsageLocalService.addAssetListEntryUsage(
			TestPropsValues.getUserId(), group.getGroupId(),
			_portal.getClassNameId(AssetListEntry.class),
			AssetListEntryUsageTableReferenceDefinitionTest.class.
				getSimpleName(),
			_portal.getClassNameId(Portlet.class),
			String.valueOf(_assetListEntry.getAssetListEntryId()),
			_layout.getPlid(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	private AssetListEntry _assetListEntry;

	@Inject
	private AssetListEntryUsageLocalService _assetListEntryUsageLocalService;

	private Layout _layout;

	@Inject
	private Portal _portal;

}