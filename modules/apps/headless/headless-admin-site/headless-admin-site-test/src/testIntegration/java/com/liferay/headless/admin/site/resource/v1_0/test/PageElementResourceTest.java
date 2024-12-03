/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlags("LPD-35443")
@RunWith(Arquillian.class)
public class PageElementResourceTest extends BasePageElementResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_layout = LayoutTestUtil.addTypeContentLayout(testGroup);
	}

	@Override
	@Test
	public void testDeleteSiteSiteByExternalReferenceCodePageElement()
		throws Exception {

		PageElement pageElement =
			testPostSiteSiteByExternalReferenceCodePageExperiencePageElement_addPageElement(
				randomPageElement());

		_layout = _layoutLocalService.fetchLayout(_layout.getPlid());

		LayoutStructure layoutStructure = _getLayoutStructure();

		Assert.assertNotNull(
			layoutStructure.getLayoutStructureItem(
				pageElement.getExternalReferenceCode()));

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				testGroup.getGroupId(), SegmentsExperienceConstants.KEY_DEFAULT,
				_layout.getPlid());

		pageElementResource.deleteSiteSiteByExternalReferenceCodePageElement(
			testGroup.getExternalReferenceCode(),
			_layout.getExternalReferenceCode(),
			segmentsExperience.getExternalReferenceCode(),
			pageElement.getExternalReferenceCode());

		_layout = _layoutLocalService.fetchLayout(_layout.getPlid());

		layoutStructure = _getLayoutStructure();

		Assert.assertNull(
			layoutStructure.getLayoutStructureItem(
				pageElement.getExternalReferenceCode()));

		try {
			pageElementResource.
				deleteSiteSiteByExternalReferenceCodePageElement(
					testGroup.getExternalReferenceCode(),
					_layout.getExternalReferenceCode(),
					segmentsExperience.getExternalReferenceCode(),
					pageElement.getExternalReferenceCode());

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
	public void testGetSiteSiteByExternalReferenceCodePageElement()
		throws Exception {

		PageElement postPageElement =
			testPostSiteSiteByExternalReferenceCodePageExperiencePageElement_addPageElement(
				randomPageElement());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				testGroup.getGroupId(), SegmentsExperienceConstants.KEY_DEFAULT,
				_layout.getPlid());

		PageElement getPageElement =
			pageElementResource.getSiteSiteByExternalReferenceCodePageElement(
				testGroup.getExternalReferenceCode(),
				_layout.getExternalReferenceCode(),
				segmentsExperience.getExternalReferenceCode(),
				postPageElement.getExternalReferenceCode());

		assertEquals(postPageElement, getPageElement);
		assertValid(getPageElement);

		try {
			pageElementResource.getSiteSiteByExternalReferenceCodePageElement(
				testGroup.getExternalReferenceCode(),
				_layout.getExternalReferenceCode(),
				segmentsExperience.getExternalReferenceCode(),
				RandomTestUtil.randomString());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	@Ignore
	@Override
	@Test
	public void testPatchSiteSiteByExternalReferenceCodePageElement()
		throws Exception {

		super.testPatchSiteSiteByExternalReferenceCodePageElement();
	}

	@Ignore
	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodePageElementFragmentComposition()
		throws Exception {

		super.
			testPostSiteSiteByExternalReferenceCodePageElementFragmentComposition();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodePageElement()
		throws Exception {

		super.testPutSiteSiteByExternalReferenceCodePageElement();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"externalReferenceCode", "parentExternalReferenceCode", "position",
			"type"
		};
	}

	@Override
	protected PageElement randomPageElement() throws Exception {
		LayoutStructure layoutStructure = _getLayoutStructure();

		PageElement pageElement = super.randomPageElement();

		pageElement.setType(PageElement.Type.CONTAINER);
		pageElement.setPageElements(new PageElement[0]);
		pageElement.setParentExternalReferenceCode(
			layoutStructure.getMainItemId());
		pageElement.setPosition(0);

		return pageElement;
	}

	@Override
	protected PageElement
			testGetSiteSiteByExternalReferenceCodePageElementPageElementsPage_addPageElement(
				String siteExternalReferenceCode,
				String pageSpecificationExternalReferenceCode,
				String pageExperienceExternalReferenceCode,
				String pageElementExternalReferenceCode,
				PageElement pageElement)
		throws Exception {

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				_layout.getGroupId(), SegmentsExperienceConstants.KEY_DEFAULT,
				_layout.getPlid());

		return pageElementResource.
			postSiteSiteByExternalReferenceCodePageExperiencePageElement(
				testGroup.getExternalReferenceCode(),
				pageSpecificationExternalReferenceCode,
				segmentsExperience.getExternalReferenceCode(), pageElement);
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodePageElementPageElementsPage_getPageElementExternalReferenceCode()
		throws Exception {

		LayoutStructure layoutStructure = _getLayoutStructure();

		return layoutStructure.getMainItemId();
	}

	protected String
			testGetSiteSiteByExternalReferenceCodePageElementPageElementsPage_getPageExperienceExternalReferenceCode()
		throws Exception {

		return testGetSiteSiteByExternalReferenceCodePageExperiencePageElementsPage_getPageExperienceExternalReferenceCode();
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodePageElementPageElementsPage_getPageSpecificationExternalReferenceCode()
		throws Exception {

		return _layout.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodePageElementPageElementsPage_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected PageElement
			testGetSiteSiteByExternalReferenceCodePageExperiencePageElementsPage_addPageElement(
				String siteExternalReferenceCode,
				String sitePageExternalReferenceCode,
				String pageExperienceExternalReferenceCode,
				PageElement pageElement)
		throws Exception {

		return pageElementResource.
			postSiteSiteByExternalReferenceCodePageExperiencePageElement(
				testGroup.getExternalReferenceCode(),
				sitePageExternalReferenceCode,
				pageExperienceExternalReferenceCode, pageElement);
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodePageExperiencePageElementsPage_getIrrelevantSiteExternalReferenceCode()
		throws Exception {

		return irrelevantGroup.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodePageExperiencePageElementsPage_getPageExperienceExternalReferenceCode()
		throws Exception {

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				_layout.getGroupId(), SegmentsExperienceConstants.KEY_DEFAULT,
				_layout.getPlid());

		return segmentsExperience.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodePageExperiencePageElementsPage_getPageSpecificationExternalReferenceCode()
		throws Exception {

		return _layout.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodePageExperiencePageElementsPage_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected PageElement
			testPostSiteSiteByExternalReferenceCodePageExperiencePageElement_addPageElement(
				PageElement pageElement)
		throws Exception {

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				_layout.getGroupId(), SegmentsExperienceConstants.KEY_DEFAULT,
				_layout.getPlid());

		return pageElementResource.
			postSiteSiteByExternalReferenceCodePageExperiencePageElement(
				testGroup.getExternalReferenceCode(),
				_layout.getExternalReferenceCode(),
				segmentsExperience.getExternalReferenceCode(), pageElement);
	}

	private LayoutStructure _getLayoutStructure() {
		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_layout.getGroupId(), _layout.getPlid());

		return LayoutStructure.of(
			layoutPageTemplateStructure.getDefaultSegmentsExperienceData());
	}

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}