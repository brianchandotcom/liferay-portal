/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.design.library.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.design.library.client.dto.v1_0.StyleBook;
import com.liferay.headless.design.library.client.problem.Problem;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@FeatureFlag("LPD-57283")
@RunWith(Arquillian.class)
public class StyleBookResourceTest extends BaseStyleBookResourceTestCase {

	@Override
	@Test
	public void testGetAssetLibraryStyleBook() throws Exception {
		super.testGetAssetLibraryStyleBook();

		_testGetAssetLibraryStyleBookExposesActions();

		try {
			styleBookResource.getAssetLibraryStyleBook(
				testGetAssetLibraryStyleBook_getAssetLibraryExternalReferenceCode(),
				RandomTestUtil.randomString());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
		}
	}

	@Override
	protected StyleBook testDeleteAssetLibraryStyleBook_addStyleBook()
		throws Exception {

		return _addAssetLibraryStyleBook(randomStyleBook());
	}

	@Override
	protected StyleBook testGetAssetLibraryStyleBook_addStyleBook()
		throws Exception {

		return _addAssetLibraryStyleBook(randomStyleBook());
	}

	@Override
	protected StyleBook testGetAssetLibraryStyleBooksPage_addStyleBook(
			String assetLibraryExternalReferenceCode, StyleBook styleBook)
		throws Exception {

		Group group = _groupLocalService.fetchGroupByExternalReferenceCode(
			assetLibraryExternalReferenceCode, TestPropsValues.getCompanyId());

		StyleBookEntry styleBookEntry = _addStyleBookEntry(
			group.getGroupId(), styleBook);

		return styleBookResource.getAssetLibraryStyleBook(
			assetLibraryExternalReferenceCode,
			styleBookEntry.getExternalReferenceCode());
	}

	private StyleBook _addAssetLibraryStyleBook(StyleBook styleBook)
		throws Exception {

		StyleBookEntry styleBookEntry = _addStyleBookEntry(
			testDepotEntryGroup.getGroupId(), styleBook);

		return styleBookResource.getAssetLibraryStyleBook(
			testDepotEntryGroup.getExternalReferenceCode(),
			styleBookEntry.getExternalReferenceCode());
	}

	private StyleBookEntry _addStyleBookEntry(long groupId, StyleBook styleBook)
		throws Exception {

		boolean defaultStyleBook = false;

		if ((styleBook.getDefaultStyleBook() != null) &&
			styleBook.getDefaultStyleBook()) {

			defaultStyleBook = true;
		}

		return _styleBookEntryLocalService.addStyleBookEntry(
			styleBook.getExternalReferenceCode(), TestPropsValues.getUserId(),
			groupId, defaultStyleBook, styleBook.getFrontendTokensValues(),
			styleBook.getName(), styleBook.getKey(), styleBook.getThemeId(),
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId()));
	}

	private void _testGetAssetLibraryStyleBookExposesActions()
		throws Exception {

		StyleBook postStyleBook = testGetAssetLibraryStyleBook_addStyleBook();

		StyleBook getStyleBook = styleBookResource.getAssetLibraryStyleBook(
			testGetAssetLibraryStyleBook_getAssetLibraryExternalReferenceCode(),
			postStyleBook.getExternalReferenceCode());

		Map<String, Map<String, String>> actions = getStyleBook.getActions();

		Assert.assertNotNull(actions);
		Assert.assertTrue(actions.containsKey("delete"));
		Assert.assertTrue(actions.containsKey("get"));
	}

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

}