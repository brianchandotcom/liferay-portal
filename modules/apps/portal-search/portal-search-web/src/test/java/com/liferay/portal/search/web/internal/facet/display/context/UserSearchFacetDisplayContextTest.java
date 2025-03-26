/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.facet.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.web.internal.BaseFacetDisplayContextTestCase;
import com.liferay.portal.search.web.internal.facet.display.context.builder.UserSearchFacetDisplayContextBuilder;
import com.liferay.portal.search.web.internal.user.facet.configuration.UserFacetPortletInstanceConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Lino Alves
 */
public class UserSearchFacetDisplayContextTest
	extends BaseFacetDisplayContextTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Override
	public FacetDisplayContext createFacetDisplayContext(String parameterValue)
		throws Exception {

		return createFacetDisplayContext(parameterValue, "count:desc");
	}

	@Override
	public FacetDisplayContext createFacetDisplayContext(
			String parameterValue, String order)
		throws Exception {

		configurationProviderUtilMockedStatic.when(
			() -> ConfigurationProviderUtil.getPortletInstanceConfiguration(
				Mockito.any(), Mockito.any())
		).thenReturn(
			Mockito.mock(UserFacetPortletInstanceConfiguration.class)
		);

		UserSearchFacetDisplayContextBuilder
			userSearchFacetDisplayContextBuilder =
				new UserSearchFacetDisplayContextBuilder(getRenderRequest());

		userSearchFacetDisplayContextBuilder.setFacet(facet);
		userSearchFacetDisplayContextBuilder.setFrequenciesVisible(true);
		userSearchFacetDisplayContextBuilder.setFrequencyThreshold(0);
		userSearchFacetDisplayContextBuilder.setLocale(LocaleUtil.getDefault());
		userSearchFacetDisplayContextBuilder.setMaxTerms(0);
		userSearchFacetDisplayContextBuilder.setOrder(order);
		userSearchFacetDisplayContextBuilder.setParamValue(parameterValue);
		userSearchFacetDisplayContextBuilder.setUserLocalService(
			_userLocalService);

		return userSearchFacetDisplayContextBuilder.build();
	}

	@Test
	public void testGetDisplayStyleGroup() throws Exception {
		_setUpDisplayStyleGroupExternalReferenceCode(null);
		_setUpGroupLocalServiceUtil(_getGroup());

		_assertDisplayContext(_getGroup());

		_groupLocalServiceUtilMockedStatic.verifyNoInteractions();
	}

	@Test
	public void testGetDisplayStyleGroupWithConfiguration() throws Exception {
		Group group = _getGroup();

		_setUpGroupLocalServiceUtil(group);

		_setUpDisplayStyleGroupExternalReferenceCode(
			group.getExternalReferenceCode());

		_assertDisplayContext(group);

		_groupLocalServiceUtilMockedStatic.verify(
			() -> GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
				group.getExternalReferenceCode(), 0L),
			Mockito.times(1));
	}

	@Override
	protected String getFilterValue(String term) {
		return String.valueOf(_userId);
	}

	@Override
	protected void setUpAsset(String term) throws Exception {
		_userId = RandomTestUtil.randomLong();

		_addUser(_userId, term);
	}

	@Override
	protected void testOrderBy(
			int[] expectedFrequencies, String[] expectedUserNames,
			int[] frequencies, String order, String[] userNames)
		throws Exception {

		setUpTermCollectors(
			facetCollector,
			_addUsersAndCreateTermCollectors(userNames, frequencies));

		FacetDisplayContext facetDisplayContext = createFacetDisplayContext(
			StringPool.BLANK, order);

		assertFacetOrder(
			facetDisplayContext.getBucketDisplayContexts(), expectedUserNames,
			expectedFrequencies);
	}

	private void _addUser(long userId, String userName) throws Exception {
		User user = Mockito.mock(User.class);

		Mockito.doReturn(
			userName
		).when(
			user
		).getFullName();

		Mockito.doReturn(
			user
		).when(
			_userLocalService
		).fetchUser(
			userId
		);
	}

	private List<TermCollector> _addUsersAndCreateTermCollectors(
			String[] userNames, int[] frequencies)
		throws Exception {

		List<TermCollector> termCollectors = new ArrayList<>();

		for (int i = 1; i <= userNames.length; i++) {
			_addUser(i, userNames[i - 1]);

			termCollectors.add(
				createTermCollector(String.valueOf(i), frequencies[i - 1]));
		}

		return termCollectors;
	}

	private void _assertDisplayContext(Group group) throws Exception {
		UserSearchFacetDisplayContextBuilder
			userSearchFacetDisplayContextBuilder =
				new UserSearchFacetDisplayContextBuilder(
					getRenderRequest(group));

		UserSearchFacetDisplayContext userSearchFacetDisplayContext =
			userSearchFacetDisplayContextBuilder.build();

		Assert.assertEquals(
			group.getGroupId(),
			userSearchFacetDisplayContext.getDisplayStyleGroupId());
	}

	private Group _getGroup() {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getExternalReferenceCode()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			group.getGroupId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		return group;
	}

	private void _setUpDisplayStyleGroupExternalReferenceCode(
		String externalReferenceCode) {

		Mockito.reset(_userFacetPortletInstanceConfiguration);

		Mockito.when(
			_userFacetPortletInstanceConfiguration.
				displayStyleGroupExternalReferenceCode()
		).thenReturn(
			externalReferenceCode
		);

		configurationProviderUtilMockedStatic.when(
			() -> ConfigurationProviderUtil.getPortletInstanceConfiguration(
				Mockito.any(), Mockito.any())
		).thenReturn(
			_userFacetPortletInstanceConfiguration
		);
	}

	private void _setUpGroupLocalServiceUtil(Group group) throws Exception {
		_groupLocalServiceUtilMockedStatic.reset();

		Mockito.when(
			GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
				group.getExternalReferenceCode(), 0L)
		).thenReturn(
			group
		);
	}

	private static final MockedStatic<GroupLocalServiceUtil>
		_groupLocalServiceUtilMockedStatic = Mockito.mockStatic(
			GroupLocalServiceUtil.class);
	private static final UserFacetPortletInstanceConfiguration
		_userFacetPortletInstanceConfiguration = Mockito.mock(
			UserFacetPortletInstanceConfiguration.class);

	private long _userId;
	private final UserLocalService _userLocalService = Mockito.mock(
		UserLocalService.class);

}