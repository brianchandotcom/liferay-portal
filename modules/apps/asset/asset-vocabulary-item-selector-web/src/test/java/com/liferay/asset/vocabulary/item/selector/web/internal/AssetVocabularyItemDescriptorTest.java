/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.vocabulary.item.selector.web.internal;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Jonathan McCann
 */
public class AssetVocabularyItemDescriptorTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	@TestInfo("LPD-91409")
	public void testGetPayload() throws Exception {
		long groupId = RandomTestUtil.randomLong();
		String externalReferenceCode = RandomTestUtil.randomString();
		String scopeExternalReferenceCode = RandomTestUtil.randomString();
		String title = RandomTestUtil.randomString();
		String uuid = RandomTestUtil.randomString();
		long vocabularyId = RandomTestUtil.randomLong();

		_setUpAssetVocabulary(
			externalReferenceCode, groupId, title, uuid, vocabularyId);

		AssetVocabularyItemDescriptor assetVocabularyItemDescriptor =
			new AssetVocabularyItemDescriptor(
				_assetVocabulary, _httpServletRequest);

		_setUpThemeDisplay(0, groupId);

		Assert.assertEquals(
			JSONUtil.put(
				"assetVocabularyId", String.valueOf(vocabularyId)
			).put(
				"externalReferenceCode", externalReferenceCode
			).put(
				"groupId", String.valueOf(groupId)
			).put(
				"title", title
			).put(
				"uuid", uuid
			).toString(),
			assetVocabularyItemDescriptor.getPayload());

		_setUpThemeDisplay(groupId, RandomTestUtil.randomLong());

		Assert.assertEquals(
			JSONUtil.put(
				"assetVocabularyId", String.valueOf(vocabularyId)
			).put(
				"externalReferenceCode", externalReferenceCode
			).put(
				"groupId", String.valueOf(groupId)
			).put(
				"title", title
			).put(
				"uuid", uuid
			).toString(),
			assetVocabularyItemDescriptor.getPayload());

		_setUpThemeDisplay(0, RandomTestUtil.randomLong());

		try (MockedStatic<GroupLocalServiceUtil>
				groupLocalServiceUtilMockedStatic = Mockito.mockStatic(
					GroupLocalServiceUtil.class)) {

			Group group = Mockito.mock(Group.class);

			Mockito.when(
				group.getExternalReferenceCode()
			).thenReturn(
				scopeExternalReferenceCode
			);

			groupLocalServiceUtilMockedStatic.when(
				() -> GroupLocalServiceUtil.getGroup(groupId)
			).thenReturn(
				group
			);

			Assert.assertEquals(
				JSONUtil.put(
					"assetVocabularyId", String.valueOf(vocabularyId)
				).put(
					"externalReferenceCode", externalReferenceCode
				).put(
					"groupId", String.valueOf(groupId)
				).put(
					"scopeExternalReferenceCode", scopeExternalReferenceCode
				).put(
					"title", title
				).put(
					"uuid", uuid
				).toString(),
				assetVocabularyItemDescriptor.getPayload());
		}
	}

	private void _setUpAssetVocabulary(
		String externalReferenceCode, long groupId, String title, String uuid,
		long vocabularyId) {

		Mockito.when(
			_assetVocabulary.getExternalReferenceCode()
		).thenReturn(
			externalReferenceCode
		);

		Mockito.when(
			_assetVocabulary.getGroupId()
		).thenReturn(
			groupId
		);

		Mockito.when(
			_assetVocabulary.getTitle(LocaleUtil.US)
		).thenReturn(
			title
		);

		Mockito.when(
			_assetVocabulary.getUuid()
		).thenReturn(
			uuid
		);

		Mockito.when(
			_assetVocabulary.getVocabularyId()
		).thenReturn(
			vocabularyId
		);
	}

	private void _setUpThemeDisplay(long refererGroupId, long scopeGroupId) {
		Mockito.when(
			_themeDisplay.getLocale()
		).thenReturn(
			LocaleUtil.US
		);

		Mockito.when(
			_themeDisplay.getRefererGroupId()
		).thenReturn(
			refererGroupId
		);

		Mockito.when(
			_themeDisplay.getScopeGroupId()
		).thenReturn(
			scopeGroupId
		);

		_httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);
	}

	private final AssetVocabulary _assetVocabulary = Mockito.mock(
		AssetVocabulary.class);
	private final HttpServletRequest _httpServletRequest =
		new MockHttpServletRequest();
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}