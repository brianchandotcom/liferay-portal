/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.facet.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.web.internal.BaseFacetDisplayContextTestCase;
import com.liferay.portal.search.web.internal.facet.display.context.builder.ScopeSearchFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.site.facet.configuration.SiteFacetPortletInstanceConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.ClassRule;
import org.junit.Rule;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class ScopeSearchFacetDisplayContextTest
	extends BaseFacetDisplayContextTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Override
	public FacetDisplayContext createFacetDisplayContext(String parameterValue)
		throws ConfigurationException {

		return createFacetDisplayContext(parameterValue, "count:desc");
	}

	@Override
	public FacetDisplayContext createFacetDisplayContext(
			String parameterValue, String order)
		throws ConfigurationException {

		configurationProviderUtilMockedStatic.when(
			() -> ConfigurationProviderUtil.getPortletInstanceConfiguration(
				Mockito.any(), Mockito.any())
		).thenReturn(
			Mockito.mock(SiteFacetPortletInstanceConfiguration.class)
		);

		ScopeSearchFacetDisplayContextBuilder
			scopeSearchFacetDisplayContextBuilder =
				new ScopeSearchFacetDisplayContextBuilder(getRenderRequest());

		scopeSearchFacetDisplayContextBuilder.setFacet(facet);
		scopeSearchFacetDisplayContextBuilder.setFrequenciesVisible(true);
		scopeSearchFacetDisplayContextBuilder.setGroupLocalService(
			_groupLocalService);
		scopeSearchFacetDisplayContextBuilder.setOrder(order);
		scopeSearchFacetDisplayContextBuilder.setParameterValue(parameterValue);

		return scopeSearchFacetDisplayContextBuilder.build();
	}

	@Override
	public String getFacetDisplayContextParameterValue() {
		return "0";
	}

	protected Group createGroup(long groupId, String name) throws Exception {
		Group group = Mockito.mock(Group.class);

		Mockito.doReturn(
			name
		).when(
			group
		).getDescriptiveName(
			Mockito.<Locale>any()
		);

		Mockito.doReturn(
			groupId
		).when(
			group
		).getGroupId();

		return group;
	}

	protected String getFilterValue(String term) {
		return String.valueOf(_groupId);
	}

	@Override
	protected void setUpAsset(String term) throws Exception {
		_groupId = RandomTestUtil.randomLong();

		_addGroup(_groupId, term);
	}

	@Override
	protected void testOrderBy(
			int[] expectedFrequencies, String[] expectedGroupNames,
			int[] frequencies, String order, String[] groupNames)
		throws Exception {

		setUpTermCollectors(
			facetCollector, _getTermCollectors(groupNames, frequencies));

		FacetDisplayContext facetDisplayContext = createFacetDisplayContext(
			StringPool.BLANK, order);

		assertFacetOrder(
			facetDisplayContext.getBucketDisplayContexts(), expectedGroupNames,
			expectedFrequencies);
	}

	private void _addGroup(long groupId, String name) throws Exception {
		Mockito.doReturn(
			createGroup(groupId, name)
		).when(
			_groupLocalService
		).fetchGroup(
			groupId
		);
	}

	private List<TermCollector> _getTermCollectors(
			String[] groupNames, int[] frequencies)
		throws Exception {

		List<TermCollector> termCollectors = new ArrayList<>();

		for (int i = 1; i <= groupNames.length; i++) {
			_addGroup(i, groupNames[i - 1]);

			termCollectors.add(
				createTermCollector(String.valueOf(i), frequencies[i - 1]));
		}

		return termCollectors;
	}

	private long _groupId;
	private final GroupLocalService _groupLocalService = Mockito.mock(
		GroupLocalService.class);

}