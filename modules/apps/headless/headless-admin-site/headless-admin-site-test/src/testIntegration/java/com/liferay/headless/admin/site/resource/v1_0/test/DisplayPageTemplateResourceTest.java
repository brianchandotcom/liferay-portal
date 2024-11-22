/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.exportimport.kernel.service.StagingLocalService;
import com.liferay.headless.admin.site.client.dto.v1_0.ClassSubtypeReference;
import com.liferay.headless.admin.site.client.dto.v1_0.DisplayPageTemplate;
import com.liferay.headless.admin.site.client.dto.v1_0.DisplayPageTemplateFolder;
import com.liferay.headless.admin.site.client.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 * @author Lourdes Fernández Besada
 */
@FeatureFlags("LPD-35443")
@RunWith(Arquillian.class)
public class DisplayPageTemplateResourceTest
	extends BaseDisplayPageTemplateResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	@Test
	public void testDeleteSiteSiteByExternalReferenceCodeDisplayPageTemplate()
		throws Exception {

		DisplayPageTemplate postDisplayPageTemplate =
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplate_addDisplayPageTemplate(
				randomDisplayPageTemplate());

		Assert.assertNotNull(
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByExternalReferenceCode(
					postDisplayPageTemplate.getExternalReferenceCode(),
					testGroup.getGroupId()));

		displayPageTemplateResource.
			deleteSiteSiteByExternalReferenceCodeDisplayPageTemplate(
				testGroup.getExternalReferenceCode(),
				postDisplayPageTemplate.getExternalReferenceCode());

		Assert.assertNull(
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByExternalReferenceCode(
					postDisplayPageTemplate.getExternalReferenceCode(),
					testGroup.getGroupId()));

		_assertProblemException(
			"NOT_FOUND",
			() ->
				displayPageTemplateResource.
					deleteSiteSiteByExternalReferenceCodeDisplayPageTemplate(
						testGroup.getExternalReferenceCode(),
						postDisplayPageTemplate.getExternalReferenceCode()));

		DisplayPageTemplate liveGroupDisplayPageTemplate =
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplate_addDisplayPageTemplate(
				randomDisplayPageTemplate());

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				displayPageTemplateResource.
					deleteSiteSiteByExternalReferenceCodeDisplayPageTemplate(
						testGroup.getExternalReferenceCode(),
						liveGroupDisplayPageTemplate.
							getExternalReferenceCode()));
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplate()
		throws Exception {

		DisplayPageTemplate displayPageTemplate =
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplate_addDisplayPageTemplate(
				randomDisplayPageTemplate());

		_testGetSiteSiteByExternalReferenceCodeDisplayPageTemplate(
			displayPageTemplate);

		_assertProblemException(
			"NOT_FOUND",
			() ->
				displayPageTemplateResource.
					getSiteSiteByExternalReferenceCodeDisplayPageTemplate(
						testGroup.getExternalReferenceCode(),
						RandomTestUtil.randomString()));

		_enableLocalStaging();

		_testGetSiteSiteByExternalReferenceCodeDisplayPageTemplate(
			displayPageTemplate);
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplatePermissionsPage()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplatePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplatesPageWithPagination()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplatesPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplatesPageWithSortDateTime()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplatesPageWithSortDateTime();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplatesPageWithSortDouble()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplatesPageWithSortDouble();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplatesPageWithSortInteger()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplatesPageWithSortInteger();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplatesPageWithSortString()
		throws Exception {

		super.
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplatesPageWithSortString();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteExternalReferenceCodeDisplayPageTemplatePermissionsPage()
		throws Exception {

		super.
			testGetSiteSiteExternalReferenceCodeDisplayPageTemplatePermissionsPage();
	}

	@Override
	@Test
	public void testPatchSiteSiteByExternalReferenceCodeDisplayPageTemplate()
		throws Exception {

		DisplayPageTemplate postDisplayPageTemplate =
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplate_addDisplayPageTemplate(
				randomDisplayPageTemplate());

		Assert.assertNull(postDisplayPageTemplate.getParentFolder());

		_testPatchSiteSiteByExternalReferenceCodeDisplayPageTemplate(
			postDisplayPageTemplate.getExternalReferenceCode(),
			new DisplayPageTemplateFolder() {
				{
					setExternalReferenceCode(
						_getLayoutPageTemplateCollectionExternalReferenceCode(
							testGroup.getGroupId()));
				}
			},
			Boolean.FALSE);
		_testPatchSiteSiteByExternalReferenceCodeDisplayPageTemplate(
			postDisplayPageTemplate.getExternalReferenceCode(), null,
			Boolean.FALSE);

		_updateLayoutPageTemplateEntryStatus(
			postDisplayPageTemplate.getExternalReferenceCode());

		_testPatchSiteSiteByExternalReferenceCodeDisplayPageTemplate(
			postDisplayPageTemplate.getExternalReferenceCode(), null,
			Boolean.TRUE);

		_assertProblemException(
			"NOT_FOUND",
			() ->
				displayPageTemplateResource.
					patchSiteSiteByExternalReferenceCodeDisplayPageTemplate(
						testGroup.getExternalReferenceCode(),
						RandomTestUtil.randomString(),
						randomDisplayPageTemplate()));

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				displayPageTemplateResource.
					patchSiteSiteByExternalReferenceCodeDisplayPageTemplate(
						testGroup.getExternalReferenceCode(),
						postDisplayPageTemplate.getExternalReferenceCode(),
						postDisplayPageTemplate));
	}

	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodeDisplayPageTemplate()
		throws Exception {

		super.testPostSiteSiteByExternalReferenceCodeDisplayPageTemplate();

		_testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateWithParentFolder();
	}

	@Ignore
	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplate()
		throws Exception {

		super.
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplate();
	}

	@Ignore
	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodeDisplayPageTemplatePageSpecification()
		throws Exception {

		super.
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplatePageSpecification();
	}

	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodeDisplayPageTemplate()
		throws Exception {

		_testPutSiteSiteByExternalReferenceCodeDisplayPageTemplate(
			randomDisplayPageTemplate());

		DisplayPageTemplate displayPageTemplate =
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplate_addDisplayPageTemplate(
				randomDisplayPageTemplate());

		_testPutSiteSiteByExternalReferenceCodeDisplayPageTemplate(
			displayPageTemplate);

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				displayPageTemplateResource.
					putSiteSiteByExternalReferenceCodeDisplayPageTemplate(
						testGroup.getExternalReferenceCode(),
						displayPageTemplate.getExternalReferenceCode(),
						displayPageTemplate));
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodeDisplayPageTemplatePermissionsPage()
		throws Exception {

		super.
			testPutSiteSiteByExternalReferenceCodeDisplayPageTemplatePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteExternalReferenceCodeDisplayPageTemplatePermissionsPage()
		throws Exception {

		super.
			testPutSiteSiteExternalReferenceCodeDisplayPageTemplatePermissionsPage();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"externalReferenceCode", "name"};
	}

	@Override
	protected DisplayPageTemplate randomDisplayPageTemplate() throws Exception {
		DisplayPageTemplate displayPageTemplate =
			super.randomDisplayPageTemplate();

		displayPageTemplate.setContentTypeReference(
			_getRandomClassSubtypeReference());
		displayPageTemplate.setMarkedAsDefault(Boolean.FALSE);

		return displayPageTemplate;
	}

	@Override
	protected DisplayPageTemplate
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplatesPage_addDisplayPageTemplate(
				String siteExternalReferenceCode,
				String displayPageTemplateFolderExternalReferenceCode,
				DisplayPageTemplate displayPageTemplate)
		throws Exception {

		return displayPageTemplateResource.
			postSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplate(
				siteExternalReferenceCode,
				displayPageTemplateFolderExternalReferenceCode,
				displayPageTemplate);
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplatesPage_getDisplayPageTemplateFolderExternalReferenceCode()
		throws Exception {

		return _getLayoutPageTemplateCollectionExternalReferenceCode(
			testGroup.getGroupId());
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplatesPage_getIrrelevantDisplayPageTemplateFolderExternalReferenceCode()
		throws Exception {

		return _getLayoutPageTemplateCollectionExternalReferenceCode(
			irrelevantGroup.getGroupId());
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplatesPage_getIrrelevantSiteExternalReferenceCode()
		throws Exception {

		return irrelevantGroup.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplatesPage_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected DisplayPageTemplate
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplatesPage_addDisplayPageTemplate(
				String siteExternalReferenceCode,
				DisplayPageTemplate displayPageTemplate)
		throws Exception {

		return displayPageTemplateResource.
			postSiteSiteByExternalReferenceCodeDisplayPageTemplate(
				siteExternalReferenceCode, displayPageTemplate);
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplatesPage_getIrrelevantSiteExternalReferenceCode()
		throws Exception {

		return irrelevantGroup.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplatesPage_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected DisplayPageTemplate
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplate_addDisplayPageTemplate(
				DisplayPageTemplate displayPageTemplate)
		throws Exception {

		return testGetSiteSiteByExternalReferenceCodeDisplayPageTemplatesPage_addDisplayPageTemplate(
			testGroup.getExternalReferenceCode(), displayPageTemplate);
	}

	private void _assertProblemException(
			String status, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try {
			unsafeRunnable.run();

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals(status, problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	private void _enableLocalStaging() throws Exception {
		_stagingLocalService.enableLocalStaging(
			TestPropsValues.getUserId(), testGroup, true, false,
			ServiceContextTestUtil.getServiceContext(
				testGroup, TestPropsValues.getUserId()));
	}

	private String _getLayoutPageTemplateCollectionExternalReferenceCode(
			long groupId)
		throws Exception {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					null, TestPropsValues.getUserId(), groupId,
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					LayoutPageTemplateCollectionTypeConstants.DISPLAY_PAGE,
					ServiceContextTestUtil.getServiceContext(
						groupId, TestPropsValues.getUserId()));

		return layoutPageTemplateCollection.getExternalReferenceCode();
	}

	private ClassSubtypeReference _getRandomClassSubtypeReference() {
		if (RandomTestUtil.randomBoolean()) {
			return new ClassSubtypeReference() {
				{
					setClassName(AssetCategory.class.getName());
				}
			};
		}

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				"com.liferay.journal.model.JournalArticle");

		List<InfoItemFormVariation> infoItemFormVariations = new ArrayList<>(
			infoItemFormVariationsProvider.getInfoItemFormVariations(
				testGroup.getGroupId()));

		Assert.assertFalse(infoItemFormVariations.isEmpty());

		infoItemFormVariations.sort(
			Comparator.comparing(InfoItemFormVariation::getKey));

		InfoItemFormVariation infoItemFormVariation =
			infoItemFormVariations.get(0);

		return new ClassSubtypeReference() {
			{
				setClassName("com.liferay.journal.model.JournalArticle");
				setSubTypeExternalReference(
					() -> new ItemExternalReference() {
						{
							setCollectionType(CollectionType.COLLECTION);
							setExternalReferenceCode(
								infoItemFormVariation::
									getExternalReferenceCode);
						}
					});
			}
		};
	}

	private void _testGetSiteSiteByExternalReferenceCodeDisplayPageTemplate(
			DisplayPageTemplate displayPageTemplate)
		throws Exception {

		DisplayPageTemplate getDisplayPageTemplate =
			displayPageTemplateResource.
				getSiteSiteByExternalReferenceCodeDisplayPageTemplate(
					testGroup.getExternalReferenceCode(),
					displayPageTemplate.getExternalReferenceCode());

		assertEquals(displayPageTemplate, getDisplayPageTemplate);
		assertValid(getDisplayPageTemplate);
	}

	private void _testPatchSiteSiteByExternalReferenceCodeDisplayPageTemplate(
			String displayPageTemplateExternalReferenceCode,
			DisplayPageTemplateFolder displayPageTemplateFolder,
			Boolean markedAsDefault)
		throws Exception {

		DisplayPageTemplate getDisplayPageTemplate =
			displayPageTemplateResource.
				getSiteSiteByExternalReferenceCodeDisplayPageTemplate(
					testGroup.getExternalReferenceCode(),
					displayPageTemplateExternalReferenceCode);

		DisplayPageTemplate randomDisplayPageTemplate =
			randomDisplayPageTemplate();

		randomDisplayPageTemplate.setExternalReferenceCode(
			displayPageTemplateExternalReferenceCode);
		randomDisplayPageTemplate.setMarkedAsDefault(markedAsDefault);
		randomDisplayPageTemplate.setParentFolder(displayPageTemplateFolder);

		DisplayPageTemplate patchDisplayPageTemplate =
			displayPageTemplateResource.
				patchSiteSiteByExternalReferenceCodeDisplayPageTemplate(
					testGroup.getExternalReferenceCode(),
					displayPageTemplateExternalReferenceCode,
					randomDisplayPageTemplate);

		assertEquals(randomDisplayPageTemplate, patchDisplayPageTemplate);
		assertValid(patchDisplayPageTemplate);

		if (displayPageTemplateFolder == null) {
			displayPageTemplateFolder =
				getDisplayPageTemplate.getParentFolder();
		}

		DisplayPageTemplateFolder curDisplayPageTemplateFolder =
			patchDisplayPageTemplate.getParentFolder();

		Assert.assertEquals(
			displayPageTemplateFolder.getExternalReferenceCode(),
			curDisplayPageTemplateFolder.getExternalReferenceCode());

		if (markedAsDefault == null) {
			markedAsDefault = getDisplayPageTemplate.getMarkedAsDefault();
		}

		Assert.assertEquals(
			markedAsDefault, patchDisplayPageTemplate.getMarkedAsDefault());
	}

	private void _testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateWithParentFolder()
		throws Exception {

		DisplayPageTemplate displayPageTemplate = randomDisplayPageTemplate();

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					null, TestPropsValues.getUserId(), testGroup.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					LayoutPageTemplateCollectionTypeConstants.DISPLAY_PAGE,
					ServiceContextTestUtil.getServiceContext(
						testGroup, TestPropsValues.getUserId()));

		displayPageTemplate.setParentFolder(
			() -> new DisplayPageTemplateFolder() {
				{
					setExternalReferenceCode(
						layoutPageTemplateCollection.
							getExternalReferenceCode());
				}
			});

		DisplayPageTemplate postDisplayPageTemplate =
			displayPageTemplateResource.
				postSiteSiteByExternalReferenceCodeDisplayPageTemplate(
					testGroup.getExternalReferenceCode(), displayPageTemplate);

		assertEquals(displayPageTemplate, postDisplayPageTemplate);
		assertValid(postDisplayPageTemplate);

		DisplayPageTemplateFolder displayPageTemplateFolder =
			displayPageTemplate.getParentFolder();
		DisplayPageTemplateFolder postDisplayPageTemplateFolder =
			postDisplayPageTemplate.getParentFolder();

		Assert.assertEquals(
			displayPageTemplateFolder.getExternalReferenceCode(),
			postDisplayPageTemplateFolder.getExternalReferenceCode());
	}

	private void _testPutSiteSiteByExternalReferenceCodeDisplayPageTemplate(
			DisplayPageTemplate displayPageTemplate)
		throws Exception {

		DisplayPageTemplate putDisplayPageTemplate =
			displayPageTemplateResource.
				putSiteSiteByExternalReferenceCodeDisplayPageTemplate(
					testGroup.getExternalReferenceCode(),
					displayPageTemplate.getExternalReferenceCode(),
					displayPageTemplate);

		assertEquals(displayPageTemplate, putDisplayPageTemplate);
		assertValid(putDisplayPageTemplate);

		Assert.assertNull(putDisplayPageTemplate.getParentFolder());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					null, TestPropsValues.getUserId(), testGroup.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					LayoutPageTemplateCollectionTypeConstants.DISPLAY_PAGE,
					ServiceContextTestUtil.getServiceContext(
						testGroup, TestPropsValues.getUserId()));

		displayPageTemplate.setParentFolder(
			new DisplayPageTemplateFolder() {
				{
					setExternalReferenceCode(
						layoutPageTemplateCollection.
							getExternalReferenceCode());
				}
			});

		putDisplayPageTemplate =
			displayPageTemplateResource.
				putSiteSiteByExternalReferenceCodeDisplayPageTemplate(
					testGroup.getExternalReferenceCode(),
					displayPageTemplate.getExternalReferenceCode(),
					displayPageTemplate);

		assertEquals(displayPageTemplate, putDisplayPageTemplate);
		assertValid(putDisplayPageTemplate);

		DisplayPageTemplateFolder curDisplayPageTemplateFolder =
			putDisplayPageTemplate.getParentFolder();

		Assert.assertEquals(
			layoutPageTemplateCollection.getExternalReferenceCode(),
			curDisplayPageTemplateFolder.getExternalReferenceCode());
	}

	private void _updateLayoutPageTemplateEntryStatus(
			String externalReferenceCode)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				getLayoutPageTemplateEntryByExternalReferenceCode(
					externalReferenceCode, testGroup.getGroupId());

		_layoutPageTemplateEntryLocalService.updateStatus(
			TestPropsValues.getUserId(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			WorkflowConstants.STATUS_APPROVED);
	}

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private StagingLocalService _stagingLocalService;

}