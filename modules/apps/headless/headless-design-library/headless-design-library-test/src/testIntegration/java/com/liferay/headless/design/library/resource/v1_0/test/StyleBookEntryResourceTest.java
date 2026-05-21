/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.design.library.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.design.library.client.dto.v1_0.StyleBookEntry;
import com.liferay.headless.design.library.client.problem.Problem;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@FeatureFlag("LPD-56718")
@RunWith(Arquillian.class)
public class StyleBookEntryResourceTest
	extends BaseStyleBookEntryResourceTestCase {

	@Override
	@Test
	public void testGetAssetLibraryStyleBook() throws Exception {
		super.testGetAssetLibraryStyleBook();

		_testGetAssetLibraryStyleBookEntryExposesActions();

		try {
			styleBookEntryResource.getAssetLibraryStyleBook(
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
	protected StyleBookEntry testDeleteAssetLibraryStyleBook_addStyleBookEntry()
		throws Exception {

		_styleBookEntry = _addAssetLibraryStyleBookEntry(
			randomStyleBookEntry());

		return _styleBookEntry;
	}

	@Override
	protected String
			testDeleteAssetLibraryStyleBook_getStyleBookExternalReferenceCode()
		throws Exception {

		return _styleBookEntry.getExternalReferenceCode();
	}

	@Override
	protected StyleBookEntry testGetAssetLibraryStyleBook_addStyleBookEntry()
		throws Exception {

		_styleBookEntry = _addAssetLibraryStyleBookEntry(
			randomStyleBookEntry());

		return _styleBookEntry;
	}

	@Override
	protected String
			testGetAssetLibraryStyleBook_getStyleBookExternalReferenceCode()
		throws Exception {

		return _styleBookEntry.getExternalReferenceCode();
	}

	@Override
	protected StyleBookEntry
			testGetAssetLibraryStyleBooksPage_addStyleBookEntry(
				String assetLibraryExternalReferenceCode,
				StyleBookEntry styleBookEntry)
		throws Exception {

		Group group = _groupLocalService.fetchGroupByExternalReferenceCode(
			assetLibraryExternalReferenceCode, TestPropsValues.getCompanyId());

		com.liferay.style.book.model.StyleBookEntry persistedStyleBookEntry =
			_addStyleBookEntry(group.getGroupId(), styleBookEntry);

		return styleBookEntryResource.getAssetLibraryStyleBook(
			assetLibraryExternalReferenceCode,
			persistedStyleBookEntry.getExternalReferenceCode());
	}

	private StyleBookEntry _addAssetLibraryStyleBookEntry(
			StyleBookEntry styleBookEntry)
		throws Exception {

		com.liferay.style.book.model.StyleBookEntry persistedStyleBookEntry =
			_addStyleBookEntry(
				testDepotEntryGroup.getGroupId(), styleBookEntry);

		return styleBookEntryResource.getAssetLibraryStyleBook(
			testDepotEntryGroup.getExternalReferenceCode(),
			persistedStyleBookEntry.getExternalReferenceCode());
	}

	private com.liferay.style.book.model.StyleBookEntry _addStyleBookEntry(
			long groupId, StyleBookEntry styleBookEntry)
		throws Exception {

		boolean defaultStyleBookEntry = false;

		if ((styleBookEntry.getDefaultStyleBookEntry() != null) &&
			styleBookEntry.getDefaultStyleBookEntry()) {

			defaultStyleBookEntry = true;
		}

		return _styleBookEntryLocalService.addStyleBookEntry(
			styleBookEntry.getExternalReferenceCode(),
			TestPropsValues.getUserId(), groupId, defaultStyleBookEntry,
			styleBookEntry.getFrontendTokensValues(), styleBookEntry.getName(),
			styleBookEntry.getKey(), styleBookEntry.getThemeId(),
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId()));
	}

	private void _testGetAssetLibraryStyleBookEntryExposesActions()
		throws Exception {

		StyleBookEntry postStyleBookEntry =
			testGetAssetLibraryStyleBook_addStyleBookEntry();

		StyleBookEntry getStyleBookEntry =
			styleBookEntryResource.getAssetLibraryStyleBook(
				testGetAssetLibraryStyleBook_getAssetLibraryExternalReferenceCode(),
				postStyleBookEntry.getExternalReferenceCode());

		Map<String, Map<String, String>> actions =
			getStyleBookEntry.getActions();

		Assert.assertNotNull(actions);
		Assert.assertTrue(actions.containsKey("delete"));
		Assert.assertTrue(actions.containsKey("get"));
	}

	@Inject
	private GroupLocalService _groupLocalService;

	private StyleBookEntry _styleBookEntry;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

}