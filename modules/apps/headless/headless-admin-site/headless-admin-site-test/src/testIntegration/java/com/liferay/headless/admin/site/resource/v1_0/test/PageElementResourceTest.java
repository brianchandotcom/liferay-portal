/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.CollectionItemPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.CollectionPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.ColumnPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.ContainerPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.DropZonePageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.FormPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.FormStepContainerPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.FormStepPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentDropZonePageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentInstancePageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.HtmlProperties;
import com.liferay.headless.admin.site.client.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.client.dto.v1_0.PageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.RowPageElementDefinition;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.headless.admin.site.dto.v1_0.DefaultFragmentReference;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.FeatureFlag;
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
@FeatureFlag("LPD-35443")
@RunWith(Arquillian.class)
public class PageElementResourceTest extends BasePageElementResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_layout = LayoutTestUtil.addTypeContentLayout(testGroup);

		_draftLayout = _layout;
	}

	@Ignore
	@Override
	@Test
	public void testBatchEngineDeleteImportTask() throws Exception {
		super.testBatchEngineDeleteImportTask();
	}

	@Override
	@Test
	public void testDeleteSitePageSpecificationPageExperiencePageElement()
		throws Exception {

		PageElement pageElement =
			testPostSitePageSpecificationPageExperiencePageElement_addPageElement(
				randomPageElement());

		LayoutStructure layoutStructure = _getLayoutStructure();

		Assert.assertNotNull(
			layoutStructure.getLayoutStructureItem(
				pageElement.getExternalReferenceCode()));

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				testGroup.getGroupId(), SegmentsExperienceConstants.KEY_DEFAULT,
				_layout.getPlid());

		pageElementResource.
			deleteSitePageSpecificationPageExperiencePageElement(
				testGroup.getExternalReferenceCode(),
				_draftLayout.getExternalReferenceCode(),
				segmentsExperience.getExternalReferenceCode(),
				pageElement.getExternalReferenceCode());

		_draftLayout = _layoutLocalService.fetchLayout(_draftLayout.getPlid());

		layoutStructure = _getLayoutStructure();

		Assert.assertNull(
			layoutStructure.getLayoutStructureItem(
				pageElement.getExternalReferenceCode()));

		try {
			pageElementResource.
				deleteSitePageSpecificationPageExperiencePageElement(
					testGroup.getExternalReferenceCode(),
					_draftLayout.getExternalReferenceCode(),
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
	public void testGetSitePageSpecificationPageExperiencePageElement()
		throws Exception {

		PageElement postPageElement =
			testPostSitePageSpecificationPageExperiencePageElement_addPageElement(
				randomPageElement());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				testGroup.getGroupId(), SegmentsExperienceConstants.KEY_DEFAULT,
				_layout.getPlid());

		PageElement getPageElement =
			pageElementResource.
				getSitePageSpecificationPageExperiencePageElement(
					testGroup.getExternalReferenceCode(),
					_draftLayout.getExternalReferenceCode(),
					segmentsExperience.getExternalReferenceCode(),
					postPageElement.getExternalReferenceCode());

		assertEquals(postPageElement, getPageElement);
		assertValid(getPageElement);

		try {
			pageElementResource.
				getSitePageSpecificationPageExperiencePageElement(
					testGroup.getExternalReferenceCode(),
					_draftLayout.getExternalReferenceCode(),
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

	@Override
	@Test
	public void testPatchSitePageSpecificationPageExperiencePageElement()
		throws Exception {

		PageElement postPageElement =
			testPostSitePageSpecificationPageExperiencePageElement_addPageElement(
				randomPageElement());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				testGroup.getGroupId(), SegmentsExperienceConstants.KEY_DEFAULT,
				_layout.getPlid());

		PageElement pathPageElement =
			pageElementResource.
				patchSitePageSpecificationPageExperiencePageElement(
					testGroup.getExternalReferenceCode(),
					_draftLayout.getExternalReferenceCode(),
					segmentsExperience.getExternalReferenceCode(),
					postPageElement.getExternalReferenceCode(),
					postPageElement);

		assertEquals(postPageElement, pathPageElement);
		assertValid(pathPageElement);

		try {
			pageElementResource.
				patchSitePageSpecificationPageExperiencePageElement(
					testGroup.getExternalReferenceCode(),
					_draftLayout.getExternalReferenceCode(),
					segmentsExperience.getExternalReferenceCode(),
					RandomTestUtil.randomString(), randomPageElement());

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
	public void testPostSitePageSpecificationPageExperiencePageElement()
		throws Exception {

		super.testPostSitePageSpecificationPageExperiencePageElement();

		_assertPostSitePageSpecificationPageExperiencePageElement(
			_randomPageElement(
				new CollectionPageElementDefinition() {
					{
						setType(Type.COLLECTION);
					}
				}));
		_assertPostSitePageSpecificationPageExperiencePageElement(
			_randomPageElement(
				new CollectionItemPageElementDefinition() {
					{
						setType(Type.COLLECTION_ITEM);
					}
				}));
		_assertPostSitePageSpecificationPageExperiencePageElement(
			_randomPageElement(
				new ColumnPageElementDefinition() {
					{
						setType(Type.COLUMN);
					}
				}));
		_assertPostSitePageSpecificationPageExperiencePageElement(
			_randomPageElement(
				new ContainerPageElementDefinition() {
					{
						setContentVisibility(ContentVisibility.AUTO);
						setHtmlProperties(new HtmlProperties());
						setIndexed(Boolean.FALSE);
						setType(Type.CONTAINER);
					}
				}));
		_assertPostSitePageSpecificationPageExperiencePageElement(
			_randomPageElement(
				new DropZonePageElementDefinition() {
					{
						setType(Type.DROP_ZONE);
					}
				}));
		_assertPostSitePageSpecificationPageExperiencePageElement(
			_randomPageElement(
				new FormPageElementDefinition() {
					{
						setType(Type.FORM);
					}
				}));
		_assertPostSitePageSpecificationPageExperiencePageElement(
			_randomPageElement(
				new FormStepPageElementDefinition() {
					{
						setType(Type.FORM_STEP);
					}
				}));
		_assertPostSitePageSpecificationPageExperiencePageElement(
			_randomPageElement(
				new FormStepContainerPageElementDefinition() {
					{
						setType(Type.FORM_STEP_CONTAINER);
					}
				}));
		_assertPostSitePageSpecificationPageExperiencePageElement(
			_randomPageElement(
				new FragmentDropZonePageElementDefinition() {
					{
						setType(Type.FRAGMENT_DROP_ZONE);
					}
				}));
		_assertPostSitePageSpecificationPageExperiencePageElement(
			_randomPageElement(
				new FragmentInstancePageElementDefinition() {
					{
						setFragmentReference(
							new DefaultFragmentReference() {
								{
									setDefaultFragmentKey(
										() -> "BASIC_COMPONENT-heading");
								}
							});
						setType(Type.FRAGMENT);
					}
				}));
		_assertPostSitePageSpecificationPageExperiencePageElement(
			_randomPageElement(
				new RowPageElementDefinition() {
					{
						setType(Type.ROW);
					}
				}));
	}

	@Ignore
	@Override
	@Test
	public void testPostSitePageSpecificationPageExperiencePageElementFragmentComposition()
		throws Exception {

		super.
			testPostSitePageSpecificationPageExperiencePageElementFragmentComposition();
	}

	@Override
	@Test
	public void testPutSitePageSpecificationPageExperiencePageElement()
		throws Exception {

		PageElement pageElement = randomPageElement();

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				testGroup.getGroupId(), SegmentsExperienceConstants.KEY_DEFAULT,
				_layout.getPlid());

		PageElement putPageElement =
			pageElementResource.
				putSitePageSpecificationPageExperiencePageElement(
					testGroup.getExternalReferenceCode(),
					_draftLayout.getExternalReferenceCode(),
					segmentsExperience.getExternalReferenceCode(),
					pageElement.getExternalReferenceCode(), pageElement);

		assertEquals(pageElement, putPageElement);
		assertValid(putPageElement);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"externalReferenceCode", "pageElementDefinition",
			"parentExternalReferenceCode", "position"
		};
	}

	@Override
	protected PageElement randomPageElement() throws Exception {
		return _randomPageElement(
			new ContainerPageElementDefinition() {
				{
					setContentVisibility(ContentVisibility.AUTO);
					setHtmlProperties(new HtmlProperties());
					setIndexed(Boolean.FALSE);
					setType(Type.CONTAINER);
				}
			});
	}

	@Override
	protected String
			testGetSitePageSpecificationPageExperiencePageElement_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected PageElement
			testGetSitePageSpecificationPageExperiencePageElementPageElementsPage_addPageElement(
				String siteExternalReferenceCode,
				String pageSpecificationExternalReferenceCode,
				String pageExperienceExternalReferenceCode,
				String pageElementExternalReferenceCode,
				PageElement pageElement)
		throws Exception {

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				testGroup.getGroupId(), SegmentsExperienceConstants.KEY_DEFAULT,
				_layout.getPlid());

		return pageElementResource.
			postSitePageSpecificationPageExperiencePageElement(
				testGroup.getExternalReferenceCode(),
				pageSpecificationExternalReferenceCode,
				segmentsExperience.getExternalReferenceCode(), pageElement);
	}

	@Override
	protected String
			testGetSitePageSpecificationPageExperiencePageElementPageElementsPage_getPageElementExternalReferenceCode()
		throws Exception {

		LayoutStructure layoutStructure = _getLayoutStructure();

		return layoutStructure.getMainItemId();
	}

	@Override
	protected String
			testGetSitePageSpecificationPageExperiencePageElementPageElementsPage_getPageExperienceExternalReferenceCode()
		throws Exception {

		return testGetSitePageSpecificationPageExperiencePageElementsPage_getPageExperienceExternalReferenceCode();
	}

	@Override
	protected String
			testGetSitePageSpecificationPageExperiencePageElementPageElementsPage_getPageSpecificationExternalReferenceCode()
		throws Exception {

		return _draftLayout.getExternalReferenceCode();
	}

	@Override
	protected PageElement
			testGetSitePageSpecificationPageExperiencePageElementsPage_addPageElement(
				String siteExternalReferenceCode,
				String sitePageExternalReferenceCode,
				String pageExperienceExternalReferenceCode,
				PageElement pageElement)
		throws Exception {

		return pageElementResource.
			postSitePageSpecificationPageExperiencePageElement(
				testGroup.getExternalReferenceCode(),
				sitePageExternalReferenceCode,
				pageExperienceExternalReferenceCode, pageElement);
	}

	@Override
	protected String
			testGetSitePageSpecificationPageExperiencePageElementsPage_getIrrelevantPageSpecificationExternalReferenceCode()
		throws Exception {

		return irrelevantGroup.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetSitePageSpecificationPageExperiencePageElementsPage_getPageExperienceExternalReferenceCode()
		throws Exception {

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				testGroup.getGroupId(), SegmentsExperienceConstants.KEY_DEFAULT,
				_layout.getPlid());

		return segmentsExperience.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetSitePageSpecificationPageExperiencePageElementsPage_getPageSpecificationExternalReferenceCode()
		throws Exception {

		return _draftLayout.getExternalReferenceCode();
	}

	@Override
	protected PageElement
			testPostSitePageSpecificationPageExperiencePageElement_addPageElement(
				PageElement pageElement)
		throws Exception {

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				testGroup.getGroupId(), SegmentsExperienceConstants.KEY_DEFAULT,
				_layout.getPlid());

		return pageElementResource.
			postSitePageSpecificationPageExperiencePageElement(
				testGroup.getExternalReferenceCode(),
				_draftLayout.getExternalReferenceCode(),
				segmentsExperience.getExternalReferenceCode(), pageElement);
	}

	private void _assertPostSitePageSpecificationPageExperiencePageElement(
			PageElement pageElement)
		throws Exception {

		PageElement postPageElement =
			testPostSitePageSpecificationPageExperiencePageElement_addPageElement(
				pageElement);

		assertEquals(pageElement, postPageElement);
		assertValid(postPageElement);
	}

	private LayoutStructure _getLayoutStructure() {
		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					testGroup.getGroupId(), _draftLayout.getPlid());

		return LayoutStructure.of(
			layoutPageTemplateStructure.getDefaultSegmentsExperienceData());
	}

	private PageElement _randomPageElement(
			PageElementDefinition pageElementDefinition)
		throws Exception {

		PageElement pageElement = super.randomPageElement();

		pageElement.setPageElementDefinition(pageElementDefinition);
		pageElement.setPageElements(new PageElement[0]);
		pageElement.setParentExternalReferenceCode(StringPool.BLANK);
		pageElement.setPosition(_position++);

		return pageElement;
	}

	private Layout _draftLayout;
	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	private int _position;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}