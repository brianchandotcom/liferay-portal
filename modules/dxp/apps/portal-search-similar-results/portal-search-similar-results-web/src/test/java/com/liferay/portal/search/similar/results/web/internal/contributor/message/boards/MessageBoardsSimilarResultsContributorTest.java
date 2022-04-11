/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.similar.results.web.internal.contributor.message.boards;

import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.similar.results.web.internal.builder.DestinationBuilderImpl;
import com.liferay.portal.search.similar.results.web.internal.builder.RouteBuilderImpl;
import com.liferay.portal.search.similar.results.web.internal.builder.SimilarResultsRoute;
import com.liferay.portal.search.similar.results.web.internal.contributor.BaseSimilarResultsContributorTestCase;
import com.liferay.portal.search.similar.results.web.internal.portlet.shared.search.Criteria;
import com.liferay.portal.search.similar.results.web.internal.portlet.shared.search.CriteriaBuilderImpl;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteHelper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
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
		super.setUp();

		_messageBoardsSimilarResultsContributor =
			new MessageBoardsSimilarResultsContributor();
	}

	@Test
	public void testDetectRoute() {
		_messageBoardsSimilarResultsContributor.setHttpHelper(
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
		_messageBoardsSimilarResultsContributor.setAssetEntryLocalService(
			assetEntryLocalService);
		_messageBoardsSimilarResultsContributor.setMbMessageLocalService(
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
			Matchers.anyLong()
		);

		setUpAssetEntryLocalServiceFetchEntry(
			setUpAssetEntry("assetEntryClassName"));

		setUpCriteriaHelper("id", RandomTestUtil.randomLong());
		setUpCriteriaHelper("type", "message");

		_messageBoardsSimilarResultsContributor.resolveCriteria(
			criteriaBuilderImpl, criteriaHelper);

		Optional<Criteria> criteraOptional = criteriaBuilderImpl.build();

		Criteria criteria = criteraOptional.get();

		Assert.assertEquals(
			Optional.of("assetEntryClassName"), criteria.getTypeOptional());
		Assert.assertEquals(
			"assetEntryClassName_PORTLET_1234", criteria.getUID());

		_messageBoardsSimilarResultsContributor.setMbCategoryLocalService(
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
			Matchers.anyLong()
		);

		setUpCriteriaHelper("type", "category");

		_messageBoardsSimilarResultsContributor.resolveCriteria(
			criteriaBuilderImpl, criteriaHelper);

		criteraOptional = criteriaBuilderImpl.build();

		criteria = criteraOptional.get();

		Assert.assertEquals(
			Optional.of(MBCategory.class.getName()),
			criteria.getTypeOptional());
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

	@Mock
	private MBCategoryLocalService _mbCategoryLocalService;

	@Mock
	private MBMessageLocalService _mbMessageLocalService;

	private MessageBoardsSimilarResultsContributor
		_messageBoardsSimilarResultsContributor;

}