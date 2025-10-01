/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.ContainerPageElementDefinition;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentViewport;
import com.liferay.headless.admin.site.client.dto.v1_0.HtmlProperties;
import com.liferay.headless.admin.site.client.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.client.dto.v1_0.PageElementDefinition;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
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

	@Override
	@Test
	public void testPostSitePageSpecificationPageExperiencePageElement()
		throws Exception {

		_testPostSitePageSpecificationPageExperiencePageElement(
			_getPageElementContainerPageElementDefinition(
				new String[] {"cssClass1", "cssClass2"}, "customCss", false,
				StringUtil.randomString()));
		_testPostSitePageSpecificationPageExperiencePageElement(
			_getPageElementContainerPageElementDefinition(
				new String[] {"cssClass1", "cssClass2", "cssClass3"},
				"customCss 2", false, StringUtil.randomString()));
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

		String externalReferenceCode = RandomTestUtil.randomString();

		_testPutSitePageSpecificationPageExperiencePageElement(
			_getPageElementContainerPageElementDefinition(
				new String[] {"cssClass1", "cssClass2"}, "customCss", true,
				externalReferenceCode));
		_testPutSitePageSpecificationPageExperiencePageElement(
			_getPageElementContainerPageElementDefinition(
				new String[] {"cssClass1", "cssClass2", "cssClass3"},
				"customCss 2", false, externalReferenceCode));
		_testPutSitePageSpecificationPageExperiencePageElementContainerDefaultValues(
			externalReferenceCode);
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

	private FragmentViewport[] _getFragmentViewports() {
		return new FragmentViewport[] {
			new FragmentViewport() {
				{
					setCustomCSS("mobile custom css");
					setId(ViewportSize.MOBILE_LANDSCAPE::getViewportSizeId);
				}
			},
			new FragmentViewport() {
				{
					setCustomCSS("tablet custom css");
					setId(ViewportSize.TABLET::getViewportSizeId);
				}
			}
		};
	}

	private LayoutStructure _getLayoutStructure() {
		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					testGroup.getGroupId(), _draftLayout.getPlid());

		return LayoutStructure.of(
			layoutPageTemplateStructure.getDefaultSegmentsExperienceData());
	}

	private PageElement _getPageElement(
			PageElementDefinition pageElementDefinition,
			String pageElementExternalReferenceCode)
		throws Exception {

		PageElement pageElement = _randomPageElement(pageElementDefinition);

		pageElement.setExternalReferenceCode(pageElementExternalReferenceCode);
		pageElement.setPosition(0);

		return pageElement;
	}

	private PageElement _getPageElementContainerPageElementDefinition(
			String[] cssClasses, String customCss, boolean indexed,
			String pageElementExternalReferenceCode)
		throws Exception {

		ContainerPageElementDefinition containerPageElementDefinition =
			new ContainerPageElementDefinition();

		containerPageElementDefinition.setContentVisibility(
			ContainerPageElementDefinition.ContentVisibility.AUTO);
		containerPageElementDefinition.setCssClasses(cssClasses);
		containerPageElementDefinition.setCustomCSS(customCss);
		containerPageElementDefinition.setFragmentViewports(
			_getFragmentViewports());
		containerPageElementDefinition.setHtmlProperties(
			() -> new HtmlProperties() {
				{
					setHtmlTag(HtmlTag.DIV);
				}
			});
		containerPageElementDefinition.setIndexed(indexed);
		containerPageElementDefinition.setLayout(
			() -> new com.liferay.headless.admin.site.client.dto.v1_0.Layout() {
				{
					setAlign(Align.END);
					setContentDisplay(ContentDisplay.FLEX_ROW);
					setFlexWrap(FlexWrap.WRAP_REVERSE);
					setJustify(Justify.CENTER);
					setWidthType(WidthType.FIXED);
				}
			});
		containerPageElementDefinition.setType(
			PageElementDefinition.Type.CONTAINER);

		return _getPageElement(
			containerPageElementDefinition, pageElementExternalReferenceCode);
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

	private void _testPostSitePageSpecificationPageExperiencePageElement(
			PageElement pageElement)
		throws Exception {

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				testGroup.getGroupId(), SegmentsExperienceConstants.KEY_DEFAULT,
				_layout.getPlid());

		PageElement postPageElement =
			pageElementResource.
				postSitePageSpecificationPageExperiencePageElement(
					testGroup.getExternalReferenceCode(),
					_draftLayout.getExternalReferenceCode(),
					segmentsExperience.getExternalReferenceCode(), pageElement);

		assertEquals(pageElement, postPageElement);
		assertValid(postPageElement);
	}

	private void _testPutSitePageSpecificationPageExperiencePageElement(
			PageElement pageElement)
		throws Exception {

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

	private void
			_testPutSitePageSpecificationPageExperiencePageElementContainerDefaultValues(
				String pageElementExternalReferenceCode)
		throws Exception {

		_testPutSitePageSpecificationPageExperiencePageElement(
			_getPageElement(
				new ContainerPageElementDefinition() {
					{
						setIndexed(false);
						setType(Type.CONTAINER);
					}
				},
				pageElementExternalReferenceCode));
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