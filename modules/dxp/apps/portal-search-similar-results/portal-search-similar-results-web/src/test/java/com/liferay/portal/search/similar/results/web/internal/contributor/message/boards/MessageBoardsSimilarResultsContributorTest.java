/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.internal.contributor.message.boards;

import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.similar.results.web.internal.builder.DestinationBuilderImpl;
import com.liferay.portal.search.similar.results.web.internal.builder.RouteBuilderImpl;
import com.liferay.portal.search.similar.results.web.internal.builder.SimilarResultsRoute;
import com.liferay.portal.search.similar.results.web.internal.contributor.BaseSimilarResultsContributorTestCase;
import com.liferay.portal.search.similar.results.web.internal.portlet.shared.search.Criteria;
import com.liferay.portal.search.similar.results.web.internal.portlet.shared.search.CriteriaBuilderImpl;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteHelper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class MessageBoardsSimilarResultsContributorTest
	extends BaseSimilarResultsContributorTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_messageBoardsSimilarResultsContributor =
			new MessageBoardsSimilarResultsContributor();
	}

	@Test
	public void testDetectRoute() {
		ReflectionTestUtil.setFieldValue(
			_messageBoardsSimilarResultsContributor, "_httpHelper",
			setUpHttpHelper());

		RouteBuilderImpl routeBuilderImpl = new RouteBuilderImpl();

		RouteHelper routeHelper = () ->
			"http://localhost:8080/message-board/-/message_boards/message" +
				"/42790";

		_messageBoardsSimilarResultsContributor.detectRoute(
			routeBuilderImpl, routeHelper);

		SimilarResultsRoute similarResultsRoute = routeBuilderImpl.build();

		Assert.assertEquals(
			"message", similarResultsRoute.getRouteParameter("type"));
		Assert.assertEquals(
			42790L, similarResultsRoute.getRouteParameter("id"));

		Assert.assertFalse(routeBuilderImpl.hasNoAttributes());
	}

	@Test
	public void testResolveCriteria() {
		ReflectionTestUtil.setFieldValue(
			_messageBoardsSimilarResultsContributor, "_assetEntryLocalService",
			assetEntryLocalService);
		ReflectionTestUtil.setFieldValue(
			_messageBoardsSimilarResultsContributor, "_mbMessageLocalService",
			_mbMessageLocalService);

		CriteriaBuilderImpl criteriaBuilderImpl = new CriteriaBuilderImpl();

		MBMessage mbMessage = Mockito.mock(MBMessage.class);

		Mockito.doReturn(
			1234L
		).when(
			mbMessage
		).getRootMessageId();

		Mockito.doReturn(
			mbMessage
		).when(
			_mbMessageLocalService
		).fetchMBMessage(
			Mockito.anyLong()
		);

		setUpAssetEntryLocalServiceFetchEntry(
			setUpAssetEntry("assetEntryClassName"));

		setUpCriteriaHelper("id", RandomTestUtil.randomLong());
		setUpCriteriaHelper("type", "message");

		_messageBoardsSimilarResultsContributor.resolveCriteria(
			criteriaBuilderImpl, criteriaHelper);

		Criteria criteria = criteriaBuilderImpl.build();

		Assert.assertEquals("assetEntryClassName", criteria.getType());
		Assert.assertEquals(
			"assetEntryClassName_PORTLET_1234", criteria.getUID());

		ReflectionTestUtil.setFieldValue(
			_messageBoardsSimilarResultsContributor, "_mbCategoryLocalService",
			_mbCategoryLocalService);

		criteriaBuilderImpl = new CriteriaBuilderImpl();

		MBCategory mbCategory = Mockito.mock(MBCategory.class);

		Mockito.doReturn(
			1111L
		).when(
			mbCategory
		).getCategoryId();

		Mockito.doReturn(
			mbCategory
		).when(
			_mbCategoryLocalService
		).fetchMBCategory(
			Mockito.anyLong()
		);

		setUpCriteriaHelper("type", "category");

		_messageBoardsSimilarResultsContributor.resolveCriteria(
			criteriaBuilderImpl, criteriaHelper);

		criteria = criteriaBuilderImpl.build();

		Assert.assertEquals(MBCategory.class.getName(), criteria.getType());
		Assert.assertEquals(
			MBCategory.class.getName() + "_PORTLET_1111", criteria.getUID());
	}

	@Test
	public void testWriteDestination() {
		DestinationBuilderImpl destinationBuilderImpl =
			new DestinationBuilderImpl(
				StringBundler.concat(
					"http://localhost:8080/message-board/-/message_boards",
					"/message/42790#_com_liferay_message_boards",
					"_web_portlet_MBPortlet_message_42790"));

		Mockito.doReturn(
			42791L
		).when(
			destinationHelper
		).getClassPK();

		setUpDestinationHelper(MBCategory.class.getName());

		setUpDestinationHelperGetRouteParameter("type", "message");
		setUpDestinationHelperGetRouteParameter(42790L, "id");

		_messageBoardsSimilarResultsContributor.writeDestination(
			destinationBuilderImpl, destinationHelper);

		Assert.assertEquals(
			StringBundler.concat(
				"http://localhost:8080/category-board/-/category_boards",
				"/category/42791#_com_liferay_category_boards_web_portlet_",
				"MBPortlet_category_42791"),
			destinationBuilderImpl.build());
	}

	private final MBCategoryLocalService _mbCategoryLocalService = Mockito.mock(
		MBCategoryLocalService.class);
	private final MBMessageLocalService _mbMessageLocalService = Mockito.mock(
		MBMessageLocalService.class);
	private MessageBoardsSimilarResultsContributor
		_messageBoardsSimilarResultsContributor;

}