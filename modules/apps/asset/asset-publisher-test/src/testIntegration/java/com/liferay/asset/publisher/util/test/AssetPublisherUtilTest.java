/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.asset.publisher.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.publisher.util.AssetPublisherHelper;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.portlet.MockPortletPreferences;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class AssetPublisherUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetAssetCategoryIdsContainsAllCategories()
		throws Exception {

		long assetCategoryId1 = RandomTestUtil.nextLong();
		long assetCategoryId2 = RandomTestUtil.nextLong();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			true, true, "assetCategories",
			new String[] {
				String.valueOf(assetCategoryId1),
				String.valueOf(assetCategoryId2)
			});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		long[] assetCategoryIds = _assetPublisherHelper.getAssetCategoryIds(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetCategoryIds), 2, assetCategoryIds.length);

		Assert.assertEquals(assetCategoryId1, assetCategoryIds[0]);
		Assert.assertEquals(assetCategoryId2, assetCategoryIds[1]);
	}

	@Test
	public void testGetAssetCategoryIdsContainsAllCategory() throws Exception {
		long assetCategoryId = RandomTestUtil.nextLong();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			true, true, "assetCategories",
			new String[] {String.valueOf(assetCategoryId)});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		long[] assetCategoryIds = _assetPublisherHelper.getAssetCategoryIds(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetCategoryIds), 1, assetCategoryIds.length);

		Assert.assertEquals(assetCategoryId, assetCategoryIds[0]);
	}

	@Test
	public void testGetAssetCategoryIdsContainsAnyCategories()
		throws Exception {

		long assetCategoryId1 = RandomTestUtil.nextLong();
		long assetCategoryId2 = RandomTestUtil.nextLong();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			true, false, "assetCategories",
			new String[] {
				String.valueOf(assetCategoryId1),
				String.valueOf(assetCategoryId2)
			});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		long[] assetCategoryIds = _assetPublisherHelper.getAssetCategoryIds(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetCategoryIds), 0, assetCategoryIds.length);
	}

	@Test
	public void testGetAssetCategoryIdsContainsAnyCategory() throws Exception {
		long assetCategoryId = RandomTestUtil.nextLong();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			true, false, "assetCategories",
			new String[] {String.valueOf(assetCategoryId)});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		long[] assetCategoryIds = _assetPublisherHelper.getAssetCategoryIds(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetCategoryIds), 1, assetCategoryIds.length);

		Assert.assertEquals(assetCategoryId, assetCategoryIds[0]);
	}

	@Test
	public void testGetAssetCategoryIdsNotContainsAllCategories()
		throws Exception {

		long assetCategoryId1 = RandomTestUtil.nextLong();
		long assetCategoryId2 = RandomTestUtil.nextLong();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			false, true, "assetCategories",
			new String[] {
				String.valueOf(assetCategoryId1),
				String.valueOf(assetCategoryId2)
			});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		long[] assetCategoryIds = _assetPublisherHelper.getAssetCategoryIds(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetCategoryIds), 0, assetCategoryIds.length);
	}

	@Test
	public void testGetAssetCategoryIdsNotContainsAllCategory()
		throws Exception {

		long assetCategoryId = RandomTestUtil.nextLong();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			false, true, "assetCategories",
			new String[] {String.valueOf(assetCategoryId)});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		long[] assetCategoryIds = _assetPublisherHelper.getAssetCategoryIds(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetCategoryIds), 0, assetCategoryIds.length);
	}

	@Test
	public void testGetAssetCategoryIdsNotContainsAnyCategories()
		throws Exception {

		long assetCategoryId1 = RandomTestUtil.nextLong();
		long assetCategoryId2 = RandomTestUtil.nextLong();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			false, false, "assetCategories",
			new String[] {
				String.valueOf(assetCategoryId1),
				String.valueOf(assetCategoryId2)
			});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		long[] assetCategoryIds = _assetPublisherHelper.getAssetCategoryIds(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetCategoryIds), 0, assetCategoryIds.length);
	}

	@Test
	public void testGetAssetCategoryIdsNotContainsAnyCategory()
		throws Exception {

		long assetCategoryId = RandomTestUtil.nextLong();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			false, false, "assetCategories",
			new String[] {String.valueOf(assetCategoryId)});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		long[] assetCategoryIds = _assetPublisherHelper.getAssetCategoryIds(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetCategoryIds), 0, assetCategoryIds.length);
	}

	@Test
	public void testGetAssetTagNamesContainsAllTagName() throws Exception {
		String assetTagName = RandomTestUtil.randomString();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			true, true, "assetTags", new String[] {assetTagName});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		String[] assetTagNames = _assetPublisherHelper.getAssetTagNames(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetTagNames), 1, assetTagNames.length);

		Assert.assertEquals(assetTagName, assetTagNames[0]);
	}

	@Test
	public void testGetAssetTagNamesContainsAllTagNames() throws Exception {
		String assetTagName1 = RandomTestUtil.randomString();
		String assetTagName2 = RandomTestUtil.randomString();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			true, true, "assetTags",
			new String[] {assetTagName1, assetTagName2});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		String[] assetTagNames = _assetPublisherHelper.getAssetTagNames(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetTagNames), 2, assetTagNames.length);

		Assert.assertEquals(assetTagName1, assetTagNames[0]);
		Assert.assertEquals(assetTagName2, assetTagNames[1]);
	}

	@Test
	public void testGetAssetTagNamesContainsAnyTagName() throws Exception {
		String assetTagName = RandomTestUtil.randomString();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			true, false, "assetTags", new String[] {assetTagName});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		String[] assetTagNames = _assetPublisherHelper.getAssetTagNames(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetTagNames), 1, assetTagNames.length);

		Assert.assertEquals(assetTagName, assetTagNames[0]);
	}

	@Test
	public void testGetAssetTagNamesContainsAnyTagNames() throws Exception {
		String assetTagName1 = RandomTestUtil.randomString();
		String assetTagName2 = RandomTestUtil.randomString();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			true, false, "assetTags",
			new String[] {assetTagName1, assetTagName2});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		String[] assetTagNames = _assetPublisherHelper.getAssetTagNames(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetTagNames), 0, assetTagNames.length);
	}

	@Test
	public void testGetAssetTagNamesNotContainsAllTagName() throws Exception {
		String assetTagName = RandomTestUtil.randomString();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			false, true, "assetTags", new String[] {assetTagName});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		String[] assetTagNames = _assetPublisherHelper.getAssetTagNames(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetTagNames), 0, assetTagNames.length);
	}

	@Test
	public void testGetAssetTagNamesNotContainsAllTagNames() throws Exception {
		String assetTagName1 = RandomTestUtil.randomString();
		String assetTagName2 = RandomTestUtil.randomString();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			false, true, "assetTags",
			new String[] {assetTagName1, assetTagName2});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		String[] assetTagNames = _assetPublisherHelper.getAssetTagNames(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetTagNames), 0, assetTagNames.length);
	}

	@Test
	public void testGetAssetTagNamesNotContainsAnyTagName() throws Exception {
		String assetTagName = RandomTestUtil.randomString();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			false, false, "assetTags", new String[] {assetTagName});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		String[] assetTagNames = _assetPublisherHelper.getAssetTagNames(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetTagNames), 0, assetTagNames.length);
	}

	@Test
	public void testGetAssetTagNamesNotContainsAnyTagNames() throws Exception {
		String assetTagName1 = RandomTestUtil.randomString();
		String assetTagName2 = RandomTestUtil.randomString();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			false, false, "assetTags",
			new String[] {assetTagName1, assetTagName2});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		String[] assetTagNames = _assetPublisherHelper.getAssetTagNames(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetTagNames), 0, assetTagNames.length);
	}

	protected PortletPreferences getAssetPublisherPortletPreferences(
			List<AssetQueryRule> assetQueryRules)
		throws Exception {

		PortletPreferences portletPreferences = new MockPortletPreferences();

		for (int i = 0; i < assetQueryRules.size(); i++) {
			AssetQueryRule assetQueryRule = assetQueryRules.get(i);

			portletPreferences.setValue(
				"queryAndOperator" + i,
				String.valueOf(assetQueryRule.isAndOperator()));
			portletPreferences.setValue(
				"queryContains" + i,
				String.valueOf(assetQueryRule.isContains()));
			portletPreferences.setValue(
				"queryName" + i, assetQueryRule.getName());
			portletPreferences.setValues(
				"queryValues" + i, assetQueryRule.getValues());
		}

		return portletPreferences;
	}

	@Inject
	private AssetPublisherHelper _assetPublisherHelper;

	private class AssetQueryRule {

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (!(obj instanceof AssetQueryRule)) {
				return false;
			}

			AssetQueryRule assetQueryRule = (AssetQueryRule)obj;

			if ((_contains == assetQueryRule._contains) &&
				(_andOperator == assetQueryRule._andOperator) &&
				Objects.equals(_name, assetQueryRule._name)) {

				return true;
			}

			return false;
		}

		public String getName() {
			return _name;
		}

		public String[] getValues() {
			return _values;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _contains);

			hash = HashUtil.hash(hash, _andOperator);

			return HashUtil.hash(hash, _name);
		}

		public boolean isAndOperator() {
			return _andOperator;
		}

		public boolean isContains() {
			return _contains;
		}

		public void setAndOperator(boolean andOperator) {
			_andOperator = andOperator;
		}

		public void setContains(boolean contains) {
			_contains = contains;
		}

		public void setName(String name) {
			_name = name;
		}

		public void setValues(String[] values) {
			_values = values;
		}

		private AssetQueryRule(
			boolean contains, boolean andOperator, String name,
			String[] values) {

			_contains = contains;
			_andOperator = andOperator;
			_name = name;
			_values = values;
		}

		private boolean _andOperator;
		private boolean _contains;
		private String _name;
		private String[] _values;

	}

}