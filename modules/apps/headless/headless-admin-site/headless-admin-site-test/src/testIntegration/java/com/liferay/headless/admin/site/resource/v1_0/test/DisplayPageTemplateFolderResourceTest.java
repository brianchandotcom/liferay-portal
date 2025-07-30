/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.service.StagingLocalService;
import com.liferay.headless.admin.site.client.dto.v1_0.DisplayPageTemplateFolder;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.lazy.referencing.LazyReferencingThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 * @author Bárbara Cabrera
 */
@FeatureFlag("LPD-35443")
@RunWith(Arquillian.class)
public class DisplayPageTemplateFolderResourceTest
	extends BaseDisplayPageTemplateFolderResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	@Test
	public void testDeleteSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder()
		throws Exception {

		DisplayPageTemplateFolder postDisplayPageTemplateFolder =
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPage_addDisplayPageTemplateFolder(
				testGroup.getExternalReferenceCode(),
				randomDisplayPageTemplateFolder());

		displayPageTemplateFolderResource.
			deleteSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
				testGroup.getExternalReferenceCode(),
				postDisplayPageTemplateFolder.getExternalReferenceCode());

		Assert.assertNull(
			_layoutPageTemplateCollectionService.
				fetchLayoutPageTemplateCollection(
					postDisplayPageTemplateFolder.getExternalReferenceCode(),
					testGroup.getGroupId()));

		DisplayPageTemplateFolder liveGroupDisplayPageTemplateFolder =
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPage_addDisplayPageTemplateFolder(
				testGroup.getExternalReferenceCode(),
				randomDisplayPageTemplateFolder());

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST", null,
			() ->
				displayPageTemplateFolderResource.
					deleteSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
						testGroup.getExternalReferenceCode(),
						liveGroupDisplayPageTemplateFolder.
							getExternalReferenceCode()));
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteDisplayPageTemplateFolderPermissionsPage()
		throws Exception {

		super.testGetSiteDisplayPageTemplateFolderPermissionsPage();
	}

	@Override
	@Test
	public void testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder()
		throws Exception {

		DisplayPageTemplateFolder postDisplayPageTemplateFolder =
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPage_addDisplayPageTemplateFolder(
				testGroup.getExternalReferenceCode(),
				randomDisplayPageTemplateFolder());

		DisplayPageTemplateFolder getDisplayPageTemplateFolder =
			displayPageTemplateFolderResource.
				getSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
					testGroup.getExternalReferenceCode(),
					postDisplayPageTemplateFolder.getExternalReferenceCode());

		assertEquals(
			postDisplayPageTemplateFolder, getDisplayPageTemplateFolder);
		assertValid(getDisplayPageTemplateFolder);

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				getLayoutPageTemplateCollection(
					getDisplayPageTemplateFolder.getExternalReferenceCode(),
					testGroup.getGroupId());

		List<LayoutPageTemplateCollection> parentLayoutPageTemplateCollections =
			new ArrayList<>();

		_assertParentDisplayPageTemplateFolder(
			_layoutPageTemplateCollectionService.
				moveLayoutPageTemplateCollection(
					layoutPageTemplateCollection.
						getLayoutPageTemplateCollectionId(),
					_getParentLayoutPageTemplateCollectionId(
						5, parentLayoutPageTemplateCollections)),
			parentLayoutPageTemplateCollections);

		_enableLocalStaging();

		assertEquals(
			postDisplayPageTemplateFolder,
			displayPageTemplateFolderResource.
				getSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
					testGroup.getExternalReferenceCode(),
					postDisplayPageTemplateFolder.getExternalReferenceCode()));
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder()
		throws Exception {

		super.
			testGraphQLGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder();
	}

	@Override
	@Test
	public void testPatchSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder()
		throws Exception {

		DisplayPageTemplateFolder parentDisplayPageTemplateFolder =
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder_addDisplayPageTemplateFolder(
				randomDisplayPageTemplateFolder());

		DisplayPageTemplateFolder displayPageTemplateFolder =
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder_addDisplayPageTemplateFolder(
				randomDisplayPageTemplateFolder());

		Assert.assertNull(
			displayPageTemplateFolder.
				getParentDisplayPageTemplateFolderExternalReferenceCode());

		_testPatchSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
			displayPageTemplateFolder.getExternalReferenceCode(),
			parentDisplayPageTemplateFolder.getExternalReferenceCode());

		_testPatchSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
			displayPageTemplateFolder.getExternalReferenceCode(), null);
		_testPatchSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
			displayPageTemplateFolder.getExternalReferenceCode(),
			StringPool.BLANK);

		_assertProblemException(
			"NOT_FOUND", null,
			() ->
				displayPageTemplateFolderResource.
					patchSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
						testGroup.getExternalReferenceCode(),
						RandomTestUtil.randomString(),
						randomDisplayPageTemplateFolder()));

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST", null,
			() ->
				displayPageTemplateFolderResource.
					patchSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
						testGroup.getExternalReferenceCode(),
						displayPageTemplateFolder.getExternalReferenceCode(),
						displayPageTemplateFolder));
	}

	@Override
	@Test
	public void testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder()
		throws Exception {

		super.
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder();

		_testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderWithExistingParentExternalReferenceCode();

		DisplayPageTemplateFolder postDisplayPageTemplateFolder =
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder_addDisplayPageTemplateFolder(
				randomDisplayPageTemplateFolder());

		_testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderWithInvalidKey(
			postDisplayPageTemplateFolder.getKey(),
			StringBundler.concat(
				"Duplicate display page template folder for group ",
				testGroup.getGroupId(), " with key ",
				postDisplayPageTemplateFolder.getKey()));

		String key =
			RandomTestUtil.randomString() + StringPool.AMPERSAND +
				RandomTestUtil.randomString();

		_testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderWithInvalidKey(
			key,
			StringBundler.concat(
				"Key ", key,
				" must contain only alphanumeric characters, dashes, and ",
				"underscores"));

		key = RandomTestUtil.randomString(80);

		_testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderWithInvalidKey(
			key,
			StringBundler.concat(
				"Key ", key, " must have fewer than 75 characters"));

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST", null,
			() ->
				displayPageTemplateFolderResource.
					postSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
						testGroup.getExternalReferenceCode(),
						randomDisplayPageTemplateFolder()));
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteDisplayPageTemplateFolderPermissionsPage()
		throws Exception {

		super.testPutSiteDisplayPageTemplateFolderPermissionsPage();
	}

	@Override
	@Test
	public void testPutSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder()
		throws Exception {

		DisplayPageTemplateFolder displayPageTemplateFolder =
			_testPutSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
				randomDisplayPageTemplateFolder(), null);

		_assertNoParentDisplayPageTemplateFolder(displayPageTemplateFolder);

		_testPutSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderWithParentDisplayPageTemplateFolder();

		DisplayPageTemplateFolder liveGroupDisplayPageTemplateFolder =
			_testPutSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
				randomDisplayPageTemplateFolder(),
				displayPageTemplateFolder.getExternalReferenceCode());

		_assertParentDisplayPageTemplateFolder(
			liveGroupDisplayPageTemplateFolder, displayPageTemplateFolder);

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST", null,
			() ->
				displayPageTemplateFolderResource.
					putSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
						testGroup.getExternalReferenceCode(),
						liveGroupDisplayPageTemplateFolder.
							getExternalReferenceCode(),
						liveGroupDisplayPageTemplateFolder));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "externalReferenceCode", "name"};
	}

	@Override
	protected DisplayPageTemplateFolder randomDisplayPageTemplateFolder()
		throws Exception {

		DisplayPageTemplateFolder displayPageTemplateFolder =
			super.randomDisplayPageTemplateFolder();

		displayPageTemplateFolder.
			setParentDisplayPageTemplateFolderExternalReferenceCode(
				(String)null);

		return displayPageTemplateFolder;
	}

	@Ignore
	@Override
	@Test
	protected DisplayPageTemplateFolder
			testGetSiteDisplayPageTemplateFolderPermissionsPage_addDisplayPageTemplateFolder()
		throws Exception {

		return super.
			testGetSiteDisplayPageTemplateFolderPermissionsPage_addDisplayPageTemplateFolder();
	}

	@Override
	protected DisplayPageTemplateFolder
			testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPage_addDisplayPageTemplateFolder(
				String siteExternalReferenceCode,
				DisplayPageTemplateFolder displayPageTemplateFolder)
		throws Exception {

		return displayPageTemplateFolderResource.
			postSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
				siteExternalReferenceCode, displayPageTemplateFolder);
	}

	@Override
	protected DisplayPageTemplateFolder
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder_addDisplayPageTemplateFolder(
				DisplayPageTemplateFolder displayPageTemplateFolder)
		throws Exception {

		return testGetSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPage_addDisplayPageTemplateFolder(
			testGroup.getExternalReferenceCode(), displayPageTemplateFolder);
	}

	@Ignore
	@Override
	@Test
	protected DisplayPageTemplateFolder
			testPutSiteDisplayPageTemplateFolderPermissionsPage_addDisplayPageTemplateFolder()
		throws Exception {

		return super.
			testPutSiteDisplayPageTemplateFolderPermissionsPage_addDisplayPageTemplateFolder();
	}

	private void _assertNoParentDisplayPageTemplateFolder(
			DisplayPageTemplateFolder displayPageTemplateFolder)
		throws Exception {

		_assertParentDisplayPageTemplateFolder(
			_layoutPageTemplateCollectionService.
				getLayoutPageTemplateCollection(
					displayPageTemplateFolder.getExternalReferenceCode(),
					testGroup.getGroupId()),
			Collections.emptyList());
	}

	private void _assertParentDisplayPageTemplateFolder(
			DisplayPageTemplateFolder displayPageTemplateFolder,
			DisplayPageTemplateFolder parentDisplayPageTemplateFolder)
		throws Exception {

		_assertParentDisplayPageTemplateFolder(
			_layoutPageTemplateCollectionService.
				getLayoutPageTemplateCollection(
					displayPageTemplateFolder.getExternalReferenceCode(),
					testGroup.getGroupId()),
			ListUtil.fromArray(
				_layoutPageTemplateCollectionService.
					getLayoutPageTemplateCollection(
						parentDisplayPageTemplateFolder.
							getExternalReferenceCode(),
						testGroup.getGroupId())));
	}

	private void _assertParentDisplayPageTemplateFolder(
			LayoutPageTemplateCollection layoutPageTemplateCollection,
			List<LayoutPageTemplateCollection>
				parentLayoutPageTemplateCollections)
		throws Exception {

		DisplayPageTemplateFolder displayPageTemplateFolder =
			displayPageTemplateFolderResource.
				getSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
					testGroup.getExternalReferenceCode(),
					layoutPageTemplateCollection.getExternalReferenceCode());

		if (parentLayoutPageTemplateCollections.isEmpty()) {
			Assert.assertNull(
				displayPageTemplateFolder.getParentDisplayPageTemplateFolder());
			Assert.assertNull(
				displayPageTemplateFolder.
					getParentDisplayPageTemplateFolderExternalReferenceCode());

			return;
		}

		DisplayPageTemplateFolder parentDisplayPageTemplateFolder =
			displayPageTemplateFolder.getParentDisplayPageTemplateFolder();

		Assert.assertEquals(
			parentDisplayPageTemplateFolder.getExternalReferenceCode(),
			displayPageTemplateFolder.
				getParentDisplayPageTemplateFolderExternalReferenceCode());

		for (LayoutPageTemplateCollection parentLayoutPageTemplateCollection :
				parentLayoutPageTemplateCollections) {

			assertEquals(
				displayPageTemplateFolderResource.
					getSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
						testGroup.getExternalReferenceCode(),
						parentLayoutPageTemplateCollection.
							getExternalReferenceCode()),
				parentDisplayPageTemplateFolder);

			parentDisplayPageTemplateFolder =
				parentDisplayPageTemplateFolder.
					getParentDisplayPageTemplateFolder();
		}

		Assert.assertNull(parentDisplayPageTemplateFolder);
	}

	private void _assertProblemException(
			String status, String title,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try {
			unsafeRunnable.run();

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals(status, problem.getStatus());
			Assert.assertEquals(title, problem.getTitle());
		}
	}

	private void _enableLocalStaging() throws Exception {
		_stagingLocalService.enableLocalStaging(
			TestPropsValues.getUserId(), testGroup, true, false,
			ServiceContextTestUtil.getServiceContext(
				testGroup, TestPropsValues.getUserId()));
	}

	private DisplayPageTemplateFolder _getParentDisplayPageTemplateFolder(
			int count)
		throws Exception {

		DisplayPageTemplateFolder parentDisplayPageTemplateFolder =
			randomDisplayPageTemplateFolder();

		for (int i = 0; i < count; i++) {
			DisplayPageTemplateFolder displayPageTemplateFolder =
				randomDisplayPageTemplateFolder();

			displayPageTemplateFolder.setParentDisplayPageTemplateFolder(
				parentDisplayPageTemplateFolder);
			displayPageTemplateFolder.
				setParentDisplayPageTemplateFolderExternalReferenceCode(
					parentDisplayPageTemplateFolder.getExternalReferenceCode());

			parentDisplayPageTemplateFolder = displayPageTemplateFolder;
		}

		return parentDisplayPageTemplateFolder;
	}

	private long _getParentLayoutPageTemplateCollectionId(
			int count,
			List<LayoutPageTemplateCollection>
				parentLayoutPageTemplateCollections)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				testGroup, TestPropsValues.getUserId());

		long parentLayoutPageTemplateCollectionId =
			LayoutPageTemplateConstants.
				PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT;

		for (int i = 0; i < count; i++) {
			LayoutPageTemplateCollection parentLayoutPageTemplateCollection =
				_layoutPageTemplateCollectionService.
					addLayoutPageTemplateCollection(
						null, testGroup.getGroupId(),
						parentLayoutPageTemplateCollectionId, null,
						RandomTestUtil.randomString(),
						RandomTestUtil.randomString(),
						LayoutPageTemplateCollectionTypeConstants.DISPLAY_PAGE,
						serviceContext);

			parentLayoutPageTemplateCollectionId =
				parentLayoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId();

			parentLayoutPageTemplateCollections.add(
				parentLayoutPageTemplateCollection);
		}

		Collections.reverse(parentLayoutPageTemplateCollections);

		return parentLayoutPageTemplateCollectionId;
	}

	private void
			_testPatchSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
				String displayPageTemplateFolderExternalReferenceCode,
				String parentDisplayPageTemplateFolderExternalReferenceCode)
		throws Exception {

		DisplayPageTemplateFolder getDisplayPageTemplateFolder =
			displayPageTemplateFolderResource.
				getSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
					testGroup.getExternalReferenceCode(),
					displayPageTemplateFolderExternalReferenceCode);

		DisplayPageTemplateFolder randomDisplayPageTemplateFolder =
			randomDisplayPageTemplateFolder();

		randomDisplayPageTemplateFolder.setExternalReferenceCode(
			displayPageTemplateFolderExternalReferenceCode);
		randomDisplayPageTemplateFolder.
			setParentDisplayPageTemplateFolderExternalReferenceCode(
				parentDisplayPageTemplateFolderExternalReferenceCode);

		DisplayPageTemplateFolder patchDisplayPageTemplateFolder =
			displayPageTemplateFolderResource.
				patchSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
					testGroup.getExternalReferenceCode(),
					displayPageTemplateFolderExternalReferenceCode,
					randomDisplayPageTemplateFolder);

		assertEquals(
			randomDisplayPageTemplateFolder, patchDisplayPageTemplateFolder);
		assertValid(patchDisplayPageTemplateFolder);

		if (parentDisplayPageTemplateFolderExternalReferenceCode == null) {
			parentDisplayPageTemplateFolderExternalReferenceCode =
				getDisplayPageTemplateFolder.
					getParentDisplayPageTemplateFolderExternalReferenceCode();
		}

		if (Validator.isBlank(
				parentDisplayPageTemplateFolderExternalReferenceCode)) {

			_assertNoParentDisplayPageTemplateFolder(
				patchDisplayPageTemplateFolder);
		}
		else {
			_assertParentDisplayPageTemplateFolder(
				_layoutPageTemplateCollectionService.
					getLayoutPageTemplateCollection(
						patchDisplayPageTemplateFolder.
							getExternalReferenceCode(),
						testGroup.getGroupId()),
				ListUtil.fromArray(
					_layoutPageTemplateCollectionService.
						getLayoutPageTemplateCollection(
							parentDisplayPageTemplateFolderExternalReferenceCode,
							testGroup.getGroupId())));
		}
	}

	private void _testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderWithExistingParentExternalReferenceCode()
		throws Exception {

		DisplayPageTemplateFolder displayPageTemplateFolder =
			randomDisplayPageTemplateFolder();

		displayPageTemplateFolder.setKey(StringPool.BLANK);

		DisplayPageTemplateFolder parentDisplayPageTemplateFolder =
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder_addDisplayPageTemplateFolder(
				displayPageTemplateFolder);

		Assert.assertNotNull(
			Validator.isNotNull(parentDisplayPageTemplateFolder.getKey()));

		DisplayPageTemplateFolder randomDisplayPageTemplateFolder =
			randomDisplayPageTemplateFolder();

		randomDisplayPageTemplateFolder.
			setParentDisplayPageTemplateFolderExternalReferenceCode(
				parentDisplayPageTemplateFolder.getExternalReferenceCode());

		DisplayPageTemplateFolder postDisplayPageTemplateFolder =
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder_addDisplayPageTemplateFolder(
				randomDisplayPageTemplateFolder);

		assertEquals(
			randomDisplayPageTemplateFolder, postDisplayPageTemplateFolder);
		Assert.assertEquals(
			randomDisplayPageTemplateFolder.getKey(),
			postDisplayPageTemplateFolder.getKey());
		Assert.assertEquals(
			randomDisplayPageTemplateFolder.
				getParentDisplayPageTemplateFolderExternalReferenceCode(),
			postDisplayPageTemplateFolder.
				getParentDisplayPageTemplateFolderExternalReferenceCode());
		assertValid(postDisplayPageTemplateFolder);
		_assertParentDisplayPageTemplateFolder(
			postDisplayPageTemplateFolder, parentDisplayPageTemplateFolder);
	}

	private void
			_testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderWithInvalidKey(
				String key, String title)
		throws Exception {

		DisplayPageTemplateFolder displayPageTemplateFolder =
			randomDisplayPageTemplateFolder();

		displayPageTemplateFolder.setKey(key);

		_assertProblemException(
			"CONFLICT", title,
			() ->
				displayPageTemplateFolderResource.
					postSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
						testGroup.getExternalReferenceCode(),
						displayPageTemplateFolder));
	}

	private DisplayPageTemplateFolder
			_testPutSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
				DisplayPageTemplateFolder displayPageTemplateFolder,
				String parentDisplayPageTemplateFolderExternalReferenceCode)
		throws Exception {

		displayPageTemplateFolder.
			setParentDisplayPageTemplateFolderExternalReferenceCode(
				parentDisplayPageTemplateFolderExternalReferenceCode);

		DisplayPageTemplateFolder putDisplayPageTemplateFolder =
			displayPageTemplateFolderResource.
				putSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
					testGroup.getExternalReferenceCode(),
					displayPageTemplateFolder.getExternalReferenceCode(),
					displayPageTemplateFolder);

		assertEquals(displayPageTemplateFolder, putDisplayPageTemplateFolder);
		assertValid(putDisplayPageTemplateFolder);

		return putDisplayPageTemplateFolder;
	}

	private void _testPutSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderWithParentDisplayPageTemplateFolder()
		throws Exception {

		_assertProblemException(
			"BAD_REQUEST", null,
			() ->
				_testPutSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
					randomDisplayPageTemplateFolder(),
					RandomTestUtil.randomString()));

		DisplayPageTemplateFolder parentDisplayPageTemplateFolder =
			testPostSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder_addDisplayPageTemplateFolder(
				randomDisplayPageTemplateFolder());

		DisplayPageTemplateFolder displayPageTemplateFolder =
			_testPutSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
				randomDisplayPageTemplateFolder(),
				parentDisplayPageTemplateFolder.getExternalReferenceCode());

		_assertParentDisplayPageTemplateFolder(
			displayPageTemplateFolder, parentDisplayPageTemplateFolder);

		DisplayPageTemplateFolder putDisplayPageTemplateFolder =
			_testPutSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
				displayPageTemplateFolder, StringPool.BLANK);

		_assertNoParentDisplayPageTemplateFolder(putDisplayPageTemplateFolder);

		parentDisplayPageTemplateFolder = _getParentDisplayPageTemplateFolder(
			5);

		putDisplayPageTemplateFolder.setParentDisplayPageTemplateFolder(
			parentDisplayPageTemplateFolder);
		putDisplayPageTemplateFolder.
			setParentDisplayPageTemplateFolderExternalReferenceCode(
				parentDisplayPageTemplateFolder.getExternalReferenceCode());

		_assertProblemException(
			"BAD_REQUEST", null,
			() ->
				displayPageTemplateFolderResource.
					putSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
						testGroup.getExternalReferenceCode(),
						putDisplayPageTemplateFolder.getExternalReferenceCode(),
						putDisplayPageTemplateFolder));

		try (SafeCloseable safeCloseable =
				LazyReferencingThreadLocal.setEnabledWithSafeCloseable(true)) {

			displayPageTemplateFolderResource.
				putSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
					testGroup.getExternalReferenceCode(),
					putDisplayPageTemplateFolder.getExternalReferenceCode(),
					putDisplayPageTemplateFolder);

			List<LayoutPageTemplateCollection>
				parentLayoutPageTemplateCollections = new ArrayList<>();

			while (parentDisplayPageTemplateFolder != null) {
				parentLayoutPageTemplateCollections.add(
					_layoutPageTemplateCollectionService.
						getLayoutPageTemplateCollection(
							parentDisplayPageTemplateFolder.
								getExternalReferenceCode(),
							testGroup.getGroupId()));

				parentDisplayPageTemplateFolder =
					parentDisplayPageTemplateFolder.
						getParentDisplayPageTemplateFolder();
			}

			_assertParentDisplayPageTemplateFolder(
				_layoutPageTemplateCollectionService.
					getLayoutPageTemplateCollection(
						putDisplayPageTemplateFolder.getExternalReferenceCode(),
						testGroup.getGroupId()),
				parentLayoutPageTemplateCollections);
		}
	}

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Inject
	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

	@Inject
	private StagingLocalService _stagingLocalService;

}