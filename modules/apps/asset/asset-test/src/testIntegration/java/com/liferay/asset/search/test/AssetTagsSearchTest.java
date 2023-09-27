/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Juergen Kappler
 */
@RunWith(Arquillian.class)
public class AssetTagsSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());

		_serviceContext.setCompanyId(TestPropsValues.getCompanyId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@Test
	public void testExactMatchSearch() throws Exception {
		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "tag",
			_serviceContext);

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "tag1",
			_serviceContext);

		BaseModelSearchResult<AssetTag> searchResult =
			AssetTagLocalServiceUtil.searchTags(
				new long[] {_group.getGroupId()}, "\"tag\"", 0, 20, null);

		Assert.assertEquals(1, searchResult.getLength());
	}

	@Test
	public void testNoMatchSearch() throws Exception {
		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "tag1",
			_serviceContext);

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "tag2",
			_serviceContext);

		BaseModelSearchResult<AssetTag> searchResult =
			AssetTagLocalServiceUtil.searchTags(
				new long[] {_group.getGroupId()}, "\"tag\"", 0, 20, null);

		Assert.assertEquals(0, searchResult.getLength());
	}

	@Test
	public void testPartialMatchSearch() throws Exception {
		List<String> searchAssetTags = Arrays.asList("tag1", "tag2", "tag3");

		for (String assetTag : searchAssetTags) {
			AssetTagLocalServiceUtil.addTag(
				TestPropsValues.getUserId(), _group.getGroupId(), assetTag,
				_serviceContext);
		}

		BaseModelSearchResult<AssetTag> searchResult =
			AssetTagLocalServiceUtil.searchTags(
				new long[] {_group.getGroupId()}, "tag", 0, 20, null);

		List<String> assetTagsNames = TransformUtil.transform(
			searchResult.getBaseModels(), assetTag -> assetTag.getName());

		Assert.assertTrue(assetTagsNames.containsAll(searchAssetTags));
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

}