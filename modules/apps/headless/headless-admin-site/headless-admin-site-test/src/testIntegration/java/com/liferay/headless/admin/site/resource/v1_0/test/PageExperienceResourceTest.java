/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.PageExperience;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.headless.admin.site.resource.v1_0.test.util.PageElementsTestUtil;
import com.liferay.headless.admin.site.resource.v1_0.test.util.PageExperiencesTestUtil;
import com.liferay.headless.admin.site.resource.v1_0.test.util.ReferencesTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.Collections;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlag("LPD-35443")
@RunWith(Arquillian.class)
public class PageExperienceResourceTest
	extends BasePageExperienceResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_layout = LayoutTestUtil.addTypeContentLayout(testGroup);

		_draftLayout = _layout.fetchDraftLayout();
	}

	@Ignore
	@Override
	@Test
	public void testBatchEngineDeleteImportTask() throws Exception {
		super.testBatchEngineDeleteImportTask();
	}

	@Override
	@Test
	public void testDeleteSitePageExperience() throws Exception {
		PageExperience postPageExperience =
			testPostSitePageSpecificationPageExperience_addPageExperience(
				randomPageExperience());

		Assert.assertNotNull(
			_segmentsExperienceLocalService.
				fetchSegmentsExperienceByExternalReferenceCode(
					postPageExperience.getExternalReferenceCode(),
					testGroup.getGroupId()));

		pageExperienceResource.deleteSitePageExperience(
			testGroup.getExternalReferenceCode(),
			postPageExperience.getExternalReferenceCode());

		Assert.assertNull(
			_segmentsExperienceLocalService.
				fetchSegmentsExperienceByExternalReferenceCode(
					postPageExperience.getExternalReferenceCode(),
					testGroup.getGroupId()));

		try {
			pageExperienceResource.deleteSitePageExperience(
				testGroup.getExternalReferenceCode(),
				postPageExperience.getExternalReferenceCode());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	@Override
	@Test
	public void testGetSitePageExperience() throws Exception {
		PageExperience postPageExperience =
			testPostSitePageSpecificationPageExperience_addPageExperience(
				randomPageExperience());

		PageExperience getPageExperience =
			pageExperienceResource.getSitePageExperience(
				testGroup.getExternalReferenceCode(),
				postPageExperience.getExternalReferenceCode());

		assertEquals(postPageExperience, getPageExperience);
		assertValid(getPageExperience);

		try {
			pageExperienceResource.getSitePageExperience(
				testGroup.getExternalReferenceCode(),
				RandomTestUtil.randomString());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	@Override
	@Test
	public void testPatchSitePageExperience() throws Exception {
		PageExperience postPageExperience =
			testPostSitePageSpecificationPageExperience_addPageExperience(
				randomPageExperience());

		PageExperience pathPageExperience =
			pageExperienceResource.patchSitePageExperience(
				testGroup.getExternalReferenceCode(),
				postPageExperience.getExternalReferenceCode(),
				postPageExperience);

		assertEquals(postPageExperience, pathPageExperience);
		assertValid(pathPageExperience);

		try {
			pageExperienceResource.patchSitePageExperience(
				testGroup.getExternalReferenceCode(),
				RandomTestUtil.randomString(), randomPageExperience());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	@Override
	@Test
	public void testPostSitePageSpecificationPageExperience() throws Exception {
		super.testPostSitePageSpecificationPageExperience();

		_testPostSitePageSpecificationPageExperience(
			PageExperiencesTestUtil.getPageExperience(
				_draftLayout.getExternalReferenceCode(), 1,
				testGroup.getGroupId(), null));

		_testPostSitePageSpecificationPageExperience(
			PageExperiencesTestUtil.getPageExperience(
				_draftLayout.getExternalReferenceCode(), 2,
				testGroup.getGroupId(),
				SegmentsTestUtil.addSegmentsEntry(testGroup.getGroupId())));

		_testPostSitePageSpecificationPageExperience(
			PageExperiencesTestUtil.getPageExperience(
				_draftLayout.getExternalReferenceCode(), 3,
				testGroup.getGroupId(),
				SegmentsTestUtil.addSegmentsEntry(testCompany.getGroupId())));
	}

	@Override
	@Test
	public void testPutSitePageExperience() throws Exception {
		PageExperience pageExperience =
			PageExperiencesTestUtil.getPageExperience(
				_draftLayout.getExternalReferenceCode(), 1,
				testGroup.getGroupId(), null);

		pageExperience = _testPutSitePageExperience(pageExperience);

		pageExperience.setSegmentItemExternalReference(
			() -> ReferencesTestUtil.getItemExternalReference(
				SegmentsTestUtil.addSegmentsEntry(testGroup.getGroupId()),
				testGroup.getGroupId()));

		pageExperience = _testPutSitePageExperience(pageExperience);

		pageExperience.setSegmentItemExternalReference(
			() -> ReferencesTestUtil.getItemExternalReference(
				SegmentsTestUtil.addSegmentsEntry(testCompany.getGroupId()),
				testGroup.getGroupId()));

		_testPutSitePageExperience(pageExperience);
	}

	@Override
	protected void assertValid(PageExperience pageExperience) throws Exception {
		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					additionalAssertFieldName, "externalReferenceCode")) {

				if (pageExperience.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(additionalAssertFieldName, "key")) {
				if (pageExperience.getKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(additionalAssertFieldName, "name_i18n")) {
				if (pageExperience.getName_i18n() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(additionalAssertFieldName, "pageElements")) {
				if (pageExperience.getPageElements() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					additionalAssertFieldName,
					"pageSpecificationExternalReferenceCode")) {

				String pageSpecificationExternalReferenceCode =
					pageExperience.getPageSpecificationExternalReferenceCode();

				if (pageSpecificationExternalReferenceCode == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(additionalAssertFieldName, "priority")) {
				if (pageExperience.getPriority() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					additionalAssertFieldName,
					"segmentItemExternalReference")) {

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"externalReferenceCode", "key", "name_i18n", "pageElements",
			"pageSpecificationExternalReferenceCode", "priority",
			"segmentItemExternalReference"
		};
	}

	@Override
	protected PageExperience randomPageExperience() throws Exception {
		PageExperience pageExperience = _getPageExperience();

		pageExperience.setSegmentItemExternalReference(
			() -> ReferencesTestUtil.getItemExternalReference(
				SegmentsTestUtil.addSegmentsEntry(testGroup.getGroupId()),
				testGroup.getGroupId()));

		return pageExperience;
	}

	@Override
	protected PageExperience
			testGetSitePageSpecificationPageExperiencesPage_addPageExperience(
				String siteExternalReferenceCode,
				String pageSpecificationExternalReferenceCode,
				PageExperience pageExperience)
		throws Exception {

		return pageExperienceResource.postSitePageSpecificationPageExperience(
			siteExternalReferenceCode, pageSpecificationExternalReferenceCode,
			pageExperience);
	}

	@Override
	protected String
			testGetSitePageSpecificationPageExperiencesPage_getPageSpecificationExternalReferenceCode()
		throws Exception {

		return _draftLayout.getExternalReferenceCode();
	}

	@Override
	protected PageExperience
			testPostSitePageSpecificationPageExperience_addPageExperience(
				PageExperience pageExperience)
		throws Exception {

		return pageExperienceResource.postSitePageSpecificationPageExperience(
			testGroup.getExternalReferenceCode(),
			pageExperience.getPageSpecificationExternalReferenceCode(),
			pageExperience);
	}

	private PageExperience _getPageExperience() throws Exception {
		PageExperience pageExperience = super.randomPageExperience();

		pageExperience.setName_i18n(
			Collections.singletonMap("en-US", RandomTestUtil.randomString()));

		pageExperience.setPageElements(
			PageElementsTestUtil.getPageElements(
				2, null, testGroup.getGroupId()));
		pageExperience.setPageSpecificationExternalReferenceCode(
			_draftLayout.getExternalReferenceCode());

		return pageExperience;
	}

	private void _testPostSitePageSpecificationPageExperience(
			PageExperience pageExperience)
		throws Exception {

		PageExperience postPageExperience =
			pageExperienceResource.postSitePageSpecificationPageExperience(
				testGroup.getExternalReferenceCode(),
				pageExperience.getPageSpecificationExternalReferenceCode(),
				pageExperience);

		assertEquals(pageExperience, postPageExperience);
		assertValid(postPageExperience);
	}

	private PageExperience _testPutSitePageExperience(
			PageExperience pageExperience)
		throws Exception {

		PageExperience putSitePageExperience =
			pageExperienceResource.putSitePageExperience(
				testGroup.getExternalReferenceCode(),
				pageExperience.getExternalReferenceCode(), pageExperience);

		assertEquals(pageExperience, putSitePageExperience);
		assertValid(putSitePageExperience);

		return putSitePageExperience;
	}

	private Layout _draftLayout;
	private Layout _layout;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}