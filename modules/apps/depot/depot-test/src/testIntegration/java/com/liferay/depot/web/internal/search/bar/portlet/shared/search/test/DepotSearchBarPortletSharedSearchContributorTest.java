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

package com.liferay.depot.web.internal.search.bar.portlet.shared.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.web.constants.SearchBarPortletKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class DepotSearchBarPortletSharedSearchContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();
	}

	@Test
	public void testContributeWithConnectedGroupId() throws Exception {
		DepotEntry depotEntry = _addDepotEntry();

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntry.getDepotEntryId(), _group1.getGroupId());

		PortletSharedSearchSettings portletSharedSearchSettings =
			_getPortletSharedSearchSettings();

		SearchContext searchContext =
			portletSharedSearchSettings.getSearchContext();

		searchContext.setGroupIds(new long[] {_group1.getGroupId()});

		_depotSearchBarPortletSharedSearchContributor.contribute(
			portletSharedSearchSettings);

		long[] groupIds = searchContext.getGroupIds();

		Assert.assertEquals(Arrays.toString(groupIds), 2, groupIds.length);

		Assert.assertEquals(_group1.getGroupId(), groupIds[0]);
		Assert.assertEquals(depotEntry.getGroupId(), groupIds[1]);
	}

	@Test
	public void testContributeWithConnectedGroupIdToSeveralDepotEntries()
		throws Exception {

		DepotEntry depotEntry1 = _addDepotEntry();
		DepotEntry depotEntry2 = _addDepotEntry();

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntry1.getDepotEntryId(), _group1.getGroupId());

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntry2.getDepotEntryId(), _group1.getGroupId());

		PortletSharedSearchSettings portletSharedSearchSettings =
			_getPortletSharedSearchSettings();

		SearchContext searchContext =
			portletSharedSearchSettings.getSearchContext();

		searchContext.setGroupIds(new long[] {_group1.getGroupId()});

		_depotSearchBarPortletSharedSearchContributor.contribute(
			portletSharedSearchSettings);

		long[] groupIds = searchContext.getGroupIds();

		Assert.assertEquals(Arrays.toString(groupIds), 3, groupIds.length);

		Assert.assertEquals(_group1.getGroupId(), groupIds[0]);
		Assert.assertEquals(depotEntry1.getGroupId(), groupIds[1]);
		Assert.assertEquals(depotEntry2.getGroupId(), groupIds[2]);
	}

	@Test
	public void testContributeWithNoGroupIds() throws Exception {
		PortletSharedSearchSettings portletSharedSearchSettings =
			_getPortletSharedSearchSettings();

		_depotSearchBarPortletSharedSearchContributor.contribute(
			portletSharedSearchSettings);

		SearchContext searchContext =
			portletSharedSearchSettings.getSearchContext();

		Assert.assertTrue(ArrayUtil.isEmpty(searchContext.getGroupIds()));
	}

	@Test
	public void testContributeWithSeveralConnectedGroupIds() throws Exception {
		DepotEntry depotEntry1 = _addDepotEntry();
		DepotEntry depotEntry2 = _addDepotEntry();

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntry1.getDepotEntryId(), _group1.getGroupId());

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntry2.getDepotEntryId(), _group2.getGroupId());

		PortletSharedSearchSettings portletSharedSearchSettings =
			_getPortletSharedSearchSettings();

		SearchContext searchContext =
			portletSharedSearchSettings.getSearchContext();

		searchContext.setGroupIds(
			new long[] {_group1.getGroupId(), _group2.getGroupId()});

		_depotSearchBarPortletSharedSearchContributor.contribute(
			portletSharedSearchSettings);

		long[] groupIds = searchContext.getGroupIds();

		Assert.assertEquals(Arrays.toString(groupIds), 4, groupIds.length);

		Assert.assertEquals(_group1.getGroupId(), groupIds[0]);
		Assert.assertEquals(_group2.getGroupId(), groupIds[1]);
		Assert.assertEquals(depotEntry1.getGroupId(), groupIds[2]);
		Assert.assertEquals(depotEntry2.getGroupId(), groupIds[3]);
	}

	@Test
	public void testContributeWithUnconnectedGroupId() throws Exception {
		PortletSharedSearchSettings portletSharedSearchSettings =
			_getPortletSharedSearchSettings();

		SearchContext searchContext =
			portletSharedSearchSettings.getSearchContext();

		searchContext.setGroupIds(new long[] {_group1.getGroupId()});

		_depotSearchBarPortletSharedSearchContributor.contribute(
			portletSharedSearchSettings);

		long[] groupIds = searchContext.getGroupIds();

		Assert.assertEquals(Arrays.toString(groupIds), 1, groupIds.length);

		Assert.assertEquals(_group1.getGroupId(), groupIds[0]);
	}

	@Test
	public void testContributeWithUnsearchableConnectedGroupId()
		throws Exception {

		DepotEntry depotEntry = _addDepotEntry();

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntry.getDepotEntryId(), _group1.getGroupId(), false);

		PortletSharedSearchSettings portletSharedSearchSettings =
			_getPortletSharedSearchSettings();

		SearchContext searchContext =
			portletSharedSearchSettings.getSearchContext();

		searchContext.setGroupIds(new long[] {_group1.getGroupId()});

		_depotSearchBarPortletSharedSearchContributor.contribute(
			portletSharedSearchSettings);

		long[] groupIds = searchContext.getGroupIds();

		Assert.assertEquals(Arrays.toString(groupIds), 1, groupIds.length);

		Assert.assertEquals(_group1.getGroupId(), groupIds[0]);
	}

	private DepotEntry _addDepotEntry() throws Exception {
		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext());

		_depotEntries.add(depotEntry);

		return depotEntry;
	}

	private PortletSharedSearchSettings _getPortletSharedSearchSettings()
		throws Exception {

		SearchContext searchContext = new SearchContext();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLayout(
			_layoutLocalService.addLayout(
				TestPropsValues.getUserId(), _group1.getGroupId(), false, 0,
				"name", "title", "description", LayoutConstants.TYPE_PORTLET,
				false, StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(
					_group1.getGroupId())));

		return new PortletSharedSearchSettings() {

			@Override
			public void addCondition(BooleanClause<Query> booleanClause) {
			}

			@Override
			public void addFacet(Facet facet) {
			}

			@Override
			public SearchRequestBuilder getFederatedSearchRequestBuilder(
				String federatedSearchKey) {

				return _searchRequestBuilderFactory.builder();
			}

			@Override
			public String getKeywordsParameterName() {
				return null;
			}

			@Override
			public Integer getPaginationDelta() {
				return null;
			}

			@Override
			public String getPaginationDeltaParameterName() {
				return null;
			}

			@Override
			public Integer getPaginationStart() {
				return null;
			}

			@Override
			public String getPaginationStartParameterName() {
				return null;
			}

			@Override
			public Optional<String> getParameterOptional(String name) {
				return Optional.empty();
			}

			@Override
			public String[] getParameterValues(String name) {
				return new String[0];
			}

			@Override
			public String getPortletId() {
				return null;
			}

			@Override
			public Optional<PortletPreferences>
				getPortletPreferencesOptional() {

				return Optional.empty();
			}

			@Override
			public QueryConfig getQueryConfig() {
				return null;
			}

			@Override
			public RenderRequest getRenderRequest() {
				return null;
			}

			@Override
			public String getScope() {
				return null;
			}

			@Override
			public String getScopeParameterName() {
				return null;
			}

			@Override
			public SearchContext getSearchContext() {
				return searchContext;
			}

			@Override
			public SearchRequestBuilder getSearchRequestBuilder() {
				return _searchRequestBuilderFactory.builder();
			}

			@Override
			public ThemeDisplay getThemeDisplay() {
				return themeDisplay;
			}

			@Override
			public void setKeywords(String keywords) {
			}

			@Override
			public void setKeywordsParameterName(String keywordsParameterName) {
			}

			@Override
			public void setPaginationDelta(int paginationDelta) {
			}

			@Override
			public void setPaginationDeltaParameterName(
				String paginationDeltaParameterName) {
			}

			@Override
			public void setPaginationStart(int paginationStart) {
			}

			@Override
			public void setPaginationStartParameterName(
				String paginationStartParameterName) {
			}

			@Override
			public void setScope(String scope) {
			}

			@Override
			public void setScopeParameterName(String scopeParameterName) {
			}

		};
	}

	@DeleteAfterTestRun
	private final List<DepotEntry> _depotEntries = new ArrayList<>();

	@Inject
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject(filter = "javax.portlet.name=" + SearchBarPortletKeys.SEARCH_BAR)
	private PortletSharedSearchContributor
		_depotSearchBarPortletSharedSearchContributor;

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}