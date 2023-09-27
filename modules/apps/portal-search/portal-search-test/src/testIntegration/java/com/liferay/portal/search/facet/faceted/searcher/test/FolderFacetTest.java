/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.facet.faceted.searcher.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.test.util.DLAppTestUtil;
import com.liferay.document.library.test.util.search.DLFolderSearchFixture;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.custom.CustomFacetFactory;
import com.liferay.portal.search.facet.folder.FolderFacetFactory;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.FacetsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 */
@RunWith(Arquillian.class)
@Sync
public class FolderFacetTest extends BaseFacetedSearcherTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpDLFolderSearchFixture();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		dlFolderSearchFixture.tearDown();
	}

	@Test
	public void testAggregation() throws Exception {
		String keyword = RandomTestUtil.randomString();

		index(keyword);

		SearchContext searchContext = getSearchContext(keyword);

		Facet facet = folderFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		Assert.assertEquals(hits.toString(), 3, hits.getLength());

		List<String> entryClassNames = Arrays.asList(
			DLFolder.class.getName(), DLFileEntry.class.getName(),
			User.class.getName());

		assertEntryClassNames(entryClassNames, hits, facet, searchContext);

		List<String> dlFolderIds = Arrays.asList(
			ArrayUtil.append(getFolderIds(_dlFolders), "0"));

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits, toMap(dlFolderIds, 1));
	}

	@Test
	public void testAvoidResidualDataFromDDMStructureLocalServiceTest()
		throws Exception {

		// See LPS-58543

		String keyword = "To Do";

		index(keyword);

		SearchContext searchContext = getSearchContext(keyword);

		Facet facet = folderFacetFactory.newInstance(searchContext);

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		Assert.assertEquals(hits.toString(), 3, hits.getLength());

		List<String> entryClassNames = Arrays.asList(
			DLFolder.class.getName(), DLFileEntry.class.getName(),
			User.class.getName());

		assertEntryClassNames(entryClassNames, hits, facet, searchContext);

		List<String> dlFolderIds = Arrays.asList(
			ArrayUtil.append(getFolderIds(_dlFolders), "0"));

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits, toMap(dlFolderIds, 1));
	}

	@Test
	public void testSelection() throws Exception {
		String keyword = RandomTestUtil.randomString();

		index(keyword);

		SearchContext searchContext = getSearchContext(keyword);

		Facet facet = folderFacetFactory.newInstance(searchContext);

		facet.select(getFolderIds(_dlFolders));

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		Assert.assertEquals(hits.toString(), 1, hits.getLength());

		assertEntryClassNames(
			Arrays.asList(DLFileEntry.class.getName()), hits, facet,
			searchContext);

		List<String> dlFolderIds = Arrays.asList(
			ArrayUtil.append(getFolderIds(_dlFolders), "0"));

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits, toMap(dlFolderIds, 1));
	}

	@Test
	public void testTreePathSelection() throws Exception {
		String keyword = RandomTestUtil.randomString();

		index(keyword);

		SearchContext searchContext = getSearchContext(keyword);

		Facet facet = customFacetFactory.newInstance(searchContext);

		facet.setFieldName(Field.TREE_PATH);

		facet.select(getFolderIds(_dlFolders));

		searchContext.addFacet(facet);

		Hits hits = search(searchContext);

		Assert.assertEquals(hits.toString(), 2, hits.getLength());

		assertEntryClassNames(
			Arrays.asList(
				DLFolder.class.getName(), DLFileEntry.class.getName()),
			hits, facet, searchContext);

		List<String> dlFolderIds = Arrays.asList(
			ArrayUtil.append(getFolderIds(_dlFolders), StringPool.BLANK));

		FacetsAssert.assertFrequencies(
			facet.getFieldName(), searchContext, hits, toMap(dlFolderIds, 2));
	}

	protected void assertEntryClassNames(
		List<String> entryClassNames, Hits hits, Facet facet,
		SearchContext searchContext) {

		DocumentsAssert.assertValuesIgnoreRelevance(
			(String)searchContext.getAttribute("queryString"), hits.getDocs(),
			Field.ENTRY_CLASS_NAME, entryClassNames);
	}

	protected String[] getFolderIds(Collection<DLFolder> dlFolders) {
		return TransformUtil.transformToArray(
			dlFolders, dlFolder -> String.valueOf(dlFolder.getFolderId()),
			String.class);
	}

	protected void index(String keyword) throws Exception {
		Group group = userSearchFixture.addGroup();

		User user = addUser(group, keyword);

		String content = "Content: Enterprise. Open Source. For Life.";
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		DLFolder folder = dlFolderSearchFixture.addDLFolderAndDLFileEntry(
			group, user, keyword, content, serviceContext);

		DLAppTestUtil.addFileEntryWithWorkflow(
			user.getUserId(), group.getGroupId(), folder.getFolderId(),
			StringPool.BLANK, keyword, true, serviceContext);

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(false);

		PermissionThreadLocal.setPermissionChecker(
			permissionCheckerFactory.create(user));
	}

	protected void setUpDLFolderSearchFixture() {
		dlFolderSearchFixture = new DLFolderSearchFixture(
			dlAppLocalService, dlFileEntryLocalService, dlFolderLocalService);

		dlFolderSearchFixture.setUp();

		_dlFolders = dlFolderSearchFixture.getDLFolders();
	}

	protected Map<String, Integer> toMap(
		Collection<String> strings, int value) {

		return new HashMap<String, Integer>(strings.size()) {
			{
				for (String string : strings) {
					put(string, value);
				}
			}
		};
	}

	@Inject
	protected CustomFacetFactory customFacetFactory;

	@Inject
	protected DLAppLocalService dlAppLocalService;

	@Inject
	protected DLFileEntryLocalService dlFileEntryLocalService;

	@Inject
	protected DLFolderLocalService dlFolderLocalService;

	protected DLFolderSearchFixture dlFolderSearchFixture;

	@Inject
	protected FolderFacetFactory folderFacetFactory;

	@Inject
	protected PermissionCheckerFactory permissionCheckerFactory;

	private List<DLFolder> _dlFolders;

}