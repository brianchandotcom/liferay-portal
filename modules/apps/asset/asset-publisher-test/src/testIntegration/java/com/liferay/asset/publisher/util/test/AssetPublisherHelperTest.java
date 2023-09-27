/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.publisher.util.AssetEntryResult;
import com.liferay.asset.publisher.util.AssetPublisherHelper;
import com.liferay.asset.publisher.util.AssetQueryRule;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockPortletPreferences;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletPreferences;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class AssetPublisherHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_assetPublisherWebConfiguration = _configurationAdmin.getConfiguration(
			"com.liferay.asset.publisher.web.internal.configuration." +
				"AssetPublisherWebConfiguration",
			StringPool.QUESTION);

		ConfigurationTestUtil.saveConfiguration(
			_assetPublisherWebConfiguration,
			HashMapDictionaryBuilder.<String, Object>put(
				"searchWithIndex", false
			).build());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(
			_assetPublisherWebConfiguration);
	}

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();
	}

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
		Assert.assertEquals(
			StringUtil.toLowerCase(assetTagName), assetTagNames[0]);
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
		Assert.assertEquals(
			StringUtil.toLowerCase(assetTagName1), assetTagNames[0]);
		Assert.assertEquals(
			StringUtil.toLowerCase(assetTagName2), assetTagNames[1]);
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
		Assert.assertEquals(
			StringUtil.toLowerCase(assetTagName), assetTagNames[0]);
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

	@Test
	public void testNotGetAssetWithTagsFromDifferentSite() throws Exception {
		String assetTagName1 = RandomTestUtil.randomString();

		AssetTestUtil.addTag(_group1.getGroupId(), assetTagName1);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId());

		serviceContext.setAssetTagNames(new String[] {assetTagName1});

		JournalTestUtil.addArticle(
			_group1.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);

		Layout layout = LayoutTestUtil.addTypePortletLayout(
			_group2.getGroupId());

		String assetTagName2 = RandomTestUtil.randomString();

		AssetTestUtil.addTag(_group2.getGroupId(), assetTagName2);

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			true, true, "assetTags", new String[] {assetTagName2});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		long[] overrideAllAssetCategoryIds = new long[0];
		String[] overrideAllAssetTagNames = {assetTagName2};
		String[] overrideAllKeywords = new String[0];

		AssetEntryQuery assetEntryQuery =
			_assetPublisherHelper.getAssetEntryQuery(
				portletPreferences, _group2.getGroupId(), layout,
				overrideAllAssetCategoryIds, overrideAllAssetTagNames,
				overrideAllKeywords);

		assetEntryQuery.setClassNameIds(
			new long[] {
				_classNameLocalService.getClassNameId(
					JournalArticle.class.getName())
			});

		long[] tagIds = assetEntryQuery.getAllTagIds();

		Assert.assertTrue(tagIds.length > 0);

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		SearchContainer<AssetEntry> searchContainer = new SearchContainer<>();

		searchContainer.setResultsAndTotal(Collections::emptyList, 10);

		List<AssetEntryResult> assetEntryResults =
			_assetPublisherHelper.getAssetEntryResults(
				searchContainer, assetEntryQuery, layout, portletPreferences,
				StringPool.BLANK, null, null, company.getCompanyId(),
				_group1.getGroupId(), TestPropsValues.getUserId(),
				assetEntryQuery.getClassNameIds(), null);

		Assert.assertTrue(assetEntryResults.isEmpty());
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

	private static Configuration _assetPublisherWebConfiguration;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	@Inject
	private AssetPublisherHelper _assetPublisherHelper;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@Inject(
		filter = "segments.criteria.contributor.key=user",
		type = SegmentsCriteriaContributor.class
	)
	private SegmentsCriteriaContributor _segmentsCriteriaContributor;

}