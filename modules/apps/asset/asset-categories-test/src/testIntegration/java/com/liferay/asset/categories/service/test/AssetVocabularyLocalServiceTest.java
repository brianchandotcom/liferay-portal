/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.exception.NoSuchVocabularyException;
import com.liferay.asset.kernel.exception.VocabularyExternalReferenceCodeException;
import com.liferay.asset.kernel.exception.VocabularyVisibilityTypeException;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.lazy.referencing.LazyReferencingThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class AssetVocabularyLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	@TestInfo("LPD-88752")
	public void testAddVocabulary() throws Exception {
		AssertUtils.assertFailure(
			VocabularyVisibilityTypeException.class, null,
			() -> _assetVocabularyLocalService.addVocabulary(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), null, new HashMap<>(), null,
				null, 3, ServiceContextTestUtil.getServiceContext()));

		int maxLength = ModelHintsUtil.getMaxLength(
			AssetVocabulary.class.getName(), "externalReferenceCode");

		AssertUtils.assertFailure(
			VocabularyExternalReferenceCodeException.class,
			StringBundler.concat(
				"External reference code length cannot exceed ", maxLength,
				" characters"),
			() -> _assetVocabularyLocalService.addVocabulary(
				StringUtil.randomString(maxLength + 1),
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), null, new HashMap<>(), null,
				null, AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC,
				ServiceContextTestUtil.getServiceContext()));
	}

	@Test
	public void testGetOrAddEmptyVocabulary() throws Exception {

		// Lazy referencing disabled

		try {
			_assetVocabularyLocalService.getOrAddEmptyVocabulary(
				StringUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId());

			Assert.fail();
		}
		catch (NoSuchVocabularyException noSuchVocabularyException) {
			Assert.assertNotNull(noSuchVocabularyException);
		}

		// Lazy referencing enabled

		try (SafeCloseable safeCloseable =
				LazyReferencingThreadLocal.setEnabledWithSafeCloseable(true)) {

			AssetVocabulary vocabulary =
				_assetVocabularyLocalService.getOrAddEmptyVocabulary(
					StringUtil.randomString(), TestPropsValues.getUserId(),
					_group.getGroupId());

			Assert.assertEquals(
				WorkflowConstants.STATUS_EMPTY, vocabulary.getStatus());
		}
	}

	@Test
	public void testUpdateAssetVocabularyWithLazyReferencingEnabled()
		throws Exception {

		try (SafeCloseable safeCloseable =
				LazyReferencingThreadLocal.setEnabledWithSafeCloseable(true)) {

			AssetVocabulary vocabulary =
				_assetVocabularyLocalService.getOrAddEmptyVocabulary(
					StringUtil.randomString(), TestPropsValues.getUserId(),
					_group.getGroupId());

			Assert.assertEquals(
				WorkflowConstants.STATUS_EMPTY, vocabulary.getStatus());

			String title = RandomTestUtil.randomString();

			vocabulary = _assetVocabularyLocalService.updateVocabulary(
				vocabulary.getExternalReferenceCode(),
				vocabulary.getVocabularyId(),
				HashMapBuilder.put(
					LocaleUtil.getDefault(), title
				).build(),
				null, vocabulary.getSettings(),
				AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL);

			Assert.assertEquals(
				StringUtil.toLowerCase(title), vocabulary.getName());
			Assert.assertEquals(
				title, vocabulary.getTitle(LocaleUtil.getDefault()));
			Assert.assertEquals(
				AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL,
				vocabulary.getVisibilityType());
			Assert.assertEquals(
				WorkflowConstants.STATUS_APPROVED, vocabulary.getStatus());
		}
	}

	@Test
	public void testUpdateVocabulary() throws Exception {
		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssertUtils.assertFailure(
			VocabularyVisibilityTypeException.class, null,
			() -> _assetVocabularyLocalService.updateVocabulary(
				assetVocabulary.getExternalReferenceCode(),
				assetVocabulary.getVocabularyId(), null, null, null, 3));
		AssertUtils.assertFailure(
			VocabularyVisibilityTypeException.class, null,
			() -> _assetVocabularyLocalService.updateVocabulary(
				assetVocabulary.getExternalReferenceCode(),
				assetVocabulary.getVocabularyId(),
				RandomTestUtil.randomString(), null, null, null, 3,
				ServiceContextTestUtil.getServiceContext()));
	}

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@DeleteAfterTestRun
	private Group _group;

}