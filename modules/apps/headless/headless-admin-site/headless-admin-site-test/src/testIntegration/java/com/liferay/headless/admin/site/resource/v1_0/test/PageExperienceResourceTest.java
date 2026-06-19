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
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlag("LPD-74328")
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
	@TestInfo("LPD-90839")
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

		_testDeleteSitePageExperienceWithPriority();

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
	@TestInfo("LPD-90839")
	public void testPatchSitePageExperience() throws Exception {
		PageExperience postPageExperience =
			testPostSitePageSpecificationPageExperience_addPageExperience(
				randomPageExperience());

		postPageExperience.setName_i18n(
			Collections.singletonMap("en-US", RandomTestUtil.randomString()));

		PageExperience pathPageExperience =
			pageExperienceResource.patchSitePageExperience(
				testGroup.getExternalReferenceCode(),
				postPageExperience.getExternalReferenceCode(),
				new PageExperience() {
					{
						setName_i18n(postPageExperience::getName_i18n);
					}
				});

		assertEquals(postPageExperience, pathPageExperience);
		assertValid(pathPageExperience);

		_testPatchSitePageExperienceWithPriority();

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
	@TestInfo("LPD-90839")
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

		Group companyGroup = _groupLocalService.getGroup(
			testCompany.getGroupId());

		_testPostSitePageSpecificationPageExperienceWithMissingOptionalReference(
			1,
			() -> _testPostSitePageSpecificationPageExperience(
				PageExperiencesTestUtil.getPageExperience(
					_draftLayout.getExternalReferenceCode(), 4,
					testGroup.getGroupId(), RandomTestUtil.randomString(),
					companyGroup.getExternalReferenceCode())));

		_testPostSitePageSpecificationPageExperienceWithMissingOptionalReference(
			1,
			() -> _testPostSitePageSpecificationPageExperience(
				PageExperiencesTestUtil.getPageExperience(
					_draftLayout.getExternalReferenceCode(), 5,
					testGroup.getGroupId(), RandomTestUtil.randomString(),
					null)));

		_testPostSitePageSpecificationPageExperienceWithPriority();
	}

	@Override
	@Test
	@TestInfo("LPD-90839")
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

		pageExperienceResource.deleteSitePageExperience(
			testGroup.getExternalReferenceCode(),
			pageExperience.getExternalReferenceCode());

		_testPutSitePageExperienceWithPriority();
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

			if (Objects.equals(additionalAssertFieldName, "uuid")) {
				if (pageExperience.getUuid() == null) {
					valid = false;
				}

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
			"segmentItemExternalReference", "uuid"
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

	private PageExperience _addPageExperience(int priority) throws Exception {
		PageExperience pageExperience =
			testPostSitePageSpecificationPageExperience_addPageExperience(
				PageExperiencesTestUtil.getPageExperience(
					_draftLayout.getExternalReferenceCode(), priority,
					testGroup.getGroupId(), null));

		Assert.assertEquals(
			Integer.valueOf(priority), pageExperience.getPriority());

		return pageExperience;
	}

	private void _assertPageExperiencePriority(
			int expectedPriority, String pageExperienceExternalReferenceCode)
		throws Exception {

		PageExperience pageExperience =
			pageExperienceResource.getSitePageExperience(
				testGroup.getExternalReferenceCode(),
				pageExperienceExternalReferenceCode);

		Assert.assertEquals(
			Integer.valueOf(expectedPriority), pageExperience.getPriority());
	}

	private PageExperience _getPageExperience() throws Exception {
		PageExperience pageExperience = super.randomPageExperience();

		pageExperience.setName_i18n(
			Collections.singletonMap("en-US", RandomTestUtil.randomString()));

		pageExperience.setPageElements(
			PageElementsTestUtil.getPageElements(
				2, StringPool.BLANK, testGroup.getGroupId()));
		pageExperience.setPageSpecificationExternalReferenceCode(
			_draftLayout.getExternalReferenceCode());

		int lowestPriority = _segmentsExperienceLocalService.getLowestPriority(
			testGroup.getGroupId(), _draftLayout.getPlid());

		pageExperience.setPriority(lowestPriority - 1);

		return pageExperience;
	}

	private void _testDeleteSitePageExperienceWithPriority() throws Exception {
		PageExperience pageExperience1 = _addPageExperience(1);
		PageExperience pageExperience2 = _addPageExperience(2);
		PageExperience pageExperience3 = _addPageExperience(3);

		pageExperienceResource.deleteSitePageExperience(
			testGroup.getExternalReferenceCode(),
			pageExperience2.getExternalReferenceCode());

		_assertPageExperiencePriority(
			1, pageExperience1.getExternalReferenceCode());
		_assertPageExperiencePriority(
			2, pageExperience3.getExternalReferenceCode());
	}

	private void _testPatchSitePageExperienceWithPriority() throws Exception {
		PageExperience pageExperience1 = _addPageExperience(1);
		PageExperience pageExperience2 = _addPageExperience(2);
		PageExperience pageExperience3 = _addPageExperience(3);

		PageExperience patchedPageExperience =
			pageExperienceResource.patchSitePageExperience(
				testGroup.getExternalReferenceCode(),
				pageExperience1.getExternalReferenceCode(),
				new PageExperience() {
					{
						setPriority(100);
					}
				});

		Assert.assertEquals(
			Integer.valueOf(3), patchedPageExperience.getPriority());

		_assertPageExperiencePriority(
			1, pageExperience2.getExternalReferenceCode());
		_assertPageExperiencePriority(
			2, pageExperience3.getExternalReferenceCode());
		_assertPageExperiencePriority(
			3, pageExperience1.getExternalReferenceCode());

		PageExperience swappedPageExperience =
			pageExperienceResource.patchSitePageExperience(
				testGroup.getExternalReferenceCode(),
				pageExperience2.getExternalReferenceCode(),
				new PageExperience() {
					{
						setPriority(2);
					}
				});

		Assert.assertEquals(
			Integer.valueOf(2), swappedPageExperience.getPriority());

		_assertPageExperiencePriority(
			1, pageExperience3.getExternalReferenceCode());
		_assertPageExperiencePriority(
			2, pageExperience2.getExternalReferenceCode());
		_assertPageExperiencePriority(
			3, pageExperience1.getExternalReferenceCode());
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

	private void
			_testPostSitePageSpecificationPageExperienceWithMissingOptionalReference(
				int count, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.headless.admin.site.internal.util.LogUtil",
				LoggerTestUtil.WARN)) {

			unsafeRunnable.run();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(
				logEntries.toString(), count, logEntries.size());

			for (LogEntry logEntry : logEntries) {
				String message = logEntry.getMessage();

				Assert.assertTrue(
					message,
					message.startsWith(
						"Optional reference generated for missing"));
			}
		}
	}

	private void _testPostSitePageSpecificationPageExperienceWithPriority()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(testGroup);

		Layout draftLayout = layout.fetchDraftLayout();

		PageExperience activePageExperience1 =
			testPostSitePageSpecificationPageExperience_addPageExperience(
				PageExperiencesTestUtil.getPageExperience(
					draftLayout.getExternalReferenceCode(), 100,
					testGroup.getGroupId(), null));

		Assert.assertEquals(
			Integer.valueOf(1), activePageExperience1.getPriority());

		PageExperience activePageExperience2 =
			testPostSitePageSpecificationPageExperience_addPageExperience(
				PageExperiencesTestUtil.getPageExperience(
					draftLayout.getExternalReferenceCode(), 50,
					testGroup.getGroupId(), null));

		Assert.assertEquals(
			Integer.valueOf(2), activePageExperience2.getPriority());

		PageExperience inactivePageExperience =
			testPostSitePageSpecificationPageExperience_addPageExperience(
				PageExperiencesTestUtil.getPageExperience(
					draftLayout.getExternalReferenceCode(), -100,
					testGroup.getGroupId(), null));

		Assert.assertEquals(
			Integer.valueOf(-1), inactivePageExperience.getPriority());
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

	private void _testPutSitePageExperienceWithPriority() throws Exception {
		PageExperience pageExperience1 = _addPageExperience(1);
		PageExperience pageExperience2 = _addPageExperience(2);
		PageExperience pageExperience3 = _addPageExperience(3);

		pageExperience1.setPriority(100);

		PageExperience putSitePageExperience =
			pageExperienceResource.putSitePageExperience(
				testGroup.getExternalReferenceCode(),
				pageExperience1.getExternalReferenceCode(), pageExperience1);

		Assert.assertEquals(
			Integer.valueOf(3), putSitePageExperience.getPriority());

		_assertPageExperiencePriority(
			1, pageExperience2.getExternalReferenceCode());
		_assertPageExperiencePriority(
			2, pageExperience3.getExternalReferenceCode());
		_assertPageExperiencePriority(
			3, pageExperience1.getExternalReferenceCode());
	}

	private Layout _draftLayout;

	@Inject
	private GroupLocalService _groupLocalService;

	private Layout _layout;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}