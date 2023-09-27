/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.machine.learning.recommendation.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.machine.learning.recommendation.UserCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.UserCommerceMLRecommendationManager;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Riccardo Ferrari
 */
@RunWith(Arquillian.class)
public class UserCommerceMLRecommendationManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_userCommerceMLRecommendations = _addUserCommerceMLRecommendations();
	}

	@Test
	public void testGetUserCommerceMLRecommendations() throws Exception {
		UserCommerceMLRecommendation userCommerceMLRecommendation =
			_userCommerceMLRecommendations.get(
				RandomTestUtil.randomInt(
					0, _userCommerceMLRecommendations.size() - 1));

		Comparator<UserCommerceMLRecommendation>
			userCommerceMLRecommendationComparator = Comparator.comparingDouble(
				UserCommerceMLRecommendation::getScore);

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				_assetResultEquals(
					userCommerceMLRecommendation.getEntryClassPK(), null,
					ListUtil.sort(
						ListUtil.filter(
							_userCommerceMLRecommendations,
							recommendation ->
								recommendation.getEntryClassPK() ==
									userCommerceMLRecommendation.
										getEntryClassPK()),
						userCommerceMLRecommendationComparator.reversed()));

				return null;
			});
	}

	@Test
	public void testGetUserCommerceMLRecommendationsContextAware()
		throws Exception {

		UserCommerceMLRecommendation userCommerceMLRecommendation =
			_userCommerceMLRecommendations.get(
				RandomTestUtil.randomInt(
					0, _userCommerceMLRecommendations.size() - 1));

		Comparator<UserCommerceMLRecommendation>
			userCommerceMLRecommendationComparator = Comparator.comparingDouble(
				UserCommerceMLRecommendation::getScore);

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			() -> {
				_assetResultEquals(
					userCommerceMLRecommendation.getEntryClassPK(),
					userCommerceMLRecommendation.getAssetCategoryIds(),
					ListUtil.sort(
						TransformUtil.transform(
							_userCommerceMLRecommendations,
							recommendation -> {
								if ((recommendation.getEntryClassPK() !=
										userCommerceMLRecommendation.
											getEntryClassPK()) ||
									!_filterAssetCategories(
										recommendation.getAssetCategoryIds(),
										userCommerceMLRecommendation.
											getAssetCategoryIds())) {

									return null;
								}

								return recommendation;
							}),
						userCommerceMLRecommendationComparator.reversed()));

				return null;
			});
	}

	private List<UserCommerceMLRecommendation>
			_addUserCommerceMLRecommendations()
		throws Exception {

		List<UserCommerceMLRecommendation> userCommerceMLRecommendations =
			new ArrayList<>();

		for (int i = 0; i < _USER_COUNT; i++) {
			long entryClassPK = RandomTestUtil.randomLong();

			for (int j = 0; j < _RECOMMENDATION_COUNT; j++) {
				Set<Long> assetCategoryIds = new HashSet<>();

				int assetCategoryIdsSize = RandomTestUtil.randomInt(
					1, _MAX_ASSET_CATEGORY_COUNT);

				float score = 1.0F - (j / (float)_RECOMMENDATION_COUNT);

				for (int k = 0; k <= assetCategoryIdsSize; k++) {
					assetCategoryIds.add(
						(long)RandomTestUtil.randomInt(
							1, _MAX_ASSET_CATEGORY_COUNT));
				}

				userCommerceMLRecommendations.add(
					_createUserCommerceMLRecommendation(
						ArrayUtil.toLongArray(assetCategoryIds), entryClassPK,
						score));
			}
		}

		Collections.shuffle(userCommerceMLRecommendations);

		for (UserCommerceMLRecommendation userCommerceMLRecommendation :
				userCommerceMLRecommendations) {

			_userCommerceMLRecommendationManager.
				addUserCommerceMLRecommendation(userCommerceMLRecommendation);
		}

		return userCommerceMLRecommendations;
	}

	private void _assetResultEquals(
			long expectedEntryClassPK, long[] expectedAssetCategoryIds,
			List<UserCommerceMLRecommendation>
				expectedUserCommerceMLRecommendations)
		throws PortalException {

		List<UserCommerceMLRecommendation> userCommerceMLRecommendations =
			_userCommerceMLRecommendationManager.
				getUserCommerceMLRecommendations(
					TestPropsValues.getCompanyId(), expectedEntryClassPK,
					expectedAssetCategoryIds);

		int expectedRecommendationsSize = Math.min(
			10, expectedUserCommerceMLRecommendations.size());

		Assert.assertEquals(
			"Recommendation list size", expectedRecommendationsSize,
			userCommerceMLRecommendations.size());

		for (int i = 0; i < expectedRecommendationsSize; i++) {
			UserCommerceMLRecommendation expectedUserCommerceMLRecommendation =
				expectedUserCommerceMLRecommendations.get(i);

			UserCommerceMLRecommendation userCommerceMLRecommendation =
				userCommerceMLRecommendations.get(i);

			Assert.assertEquals(
				expectedUserCommerceMLRecommendation.getEntryClassPK(),
				userCommerceMLRecommendation.getEntryClassPK());

			Assert.assertEquals(
				expectedUserCommerceMLRecommendation.
					getRecommendedEntryClassPK(),
				userCommerceMLRecommendation.getRecommendedEntryClassPK());

			Assert.assertEquals(
				expectedUserCommerceMLRecommendation.getScore(),
				userCommerceMLRecommendation.getScore(), 0.0);
		}
	}

	private UserCommerceMLRecommendation _createUserCommerceMLRecommendation(
			long[] assetCategoryIds, long entryClassPK, float score)
		throws Exception {

		UserCommerceMLRecommendation userCommerceMLRecommendation =
			_userCommerceMLRecommendationManager.create();

		userCommerceMLRecommendation.setAssetCategoryIds(assetCategoryIds);
		userCommerceMLRecommendation.setEntryClassPK(entryClassPK);
		userCommerceMLRecommendation.setCompanyId(
			TestPropsValues.getCompanyId());
		userCommerceMLRecommendation.setCreateDate(new Date());
		userCommerceMLRecommendation.setRecommendedEntryClassPK(
			RandomTestUtil.randomLong());
		userCommerceMLRecommendation.setScore(score);

		return userCommerceMLRecommendation;
	}

	private boolean _filterAssetCategories(
		long[] assetCategoryIds, long[] expectedAssetCategoryIds) {

		for (long expectedAssetCategoryId : expectedAssetCategoryIds) {
			if (!ArrayUtil.contains(
					assetCategoryIds, expectedAssetCategoryId)) {

				return false;
			}
		}

		return true;
	}

	private static final int _MAX_ASSET_CATEGORY_COUNT = 5;

	private static final int _RECOMMENDATION_COUNT = 11;

	private static final int _USER_COUNT = 4;

	@Inject
	private UserCommerceMLRecommendationManager
		_userCommerceMLRecommendationManager;

	private List<UserCommerceMLRecommendation> _userCommerceMLRecommendations;

}