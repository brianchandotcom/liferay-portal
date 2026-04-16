/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.exception.DuplicateFragmentCollectionExternalReferenceCodeException;
import com.liferay.fragment.exception.FragmentCollectionNameException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.test.util.FragmentCompositionTestUtil;
import com.liferay.fragment.test.util.FragmentEntryTestUtil;
import com.liferay.fragment.test.util.FragmentTestUtil;
import com.liferay.fragment.util.comparator.FragmentCollectionCreateDateComparator;
import com.liferay.fragment.util.comparator.FragmentCollectionNameComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class FragmentCollectionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddFragmentCollection() throws PortalException {
		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection");

		fragmentCollection =
			_fragmentCollectionLocalService.
				fetchFragmentCollectionByUuidAndGroupId(
					fragmentCollection.getUuid(),
					fragmentCollection.getGroupId());

		Assert.assertEquals(
			"Fragment Collection", fragmentCollection.getName());
	}

	@Test(expected = FragmentCollectionNameException.class)
	public void testAddFragmentCollectionWithEmptyName() throws Exception {
		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), StringPool.BLANK);
	}

	@Test(
		expected = DuplicateFragmentCollectionExternalReferenceCodeException.class
	)
	public void testAddFragmentCollectionWithExistingExternalReferenceCode()
		throws Exception {

		String externalReferenceCode = StringUtil.randomString();

		_fragmentCollectionLocalService.addFragmentCollection(
			externalReferenceCode, TestPropsValues.getUserId(),
			_group.getGroupId(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
		_fragmentCollectionLocalService.addFragmentCollection(
			externalReferenceCode, TestPropsValues.getUserId(),
			_group.getGroupId(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test
	public void testAddFragmentCollectionWithFragmentCollectionKey()
		throws PortalException {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				"FRAGMENTCOLLECTIONKEY");

		fragmentCollection =
			_fragmentCollectionLocalService.
				fetchFragmentCollectionByUuidAndGroupId(
					fragmentCollection.getUuid(),
					fragmentCollection.getGroupId());

		Assert.assertEquals(
			StringUtil.toLowerCase("FRAGMENTCOLLECTIONKEY"),
			fragmentCollection.getFragmentCollectionKey());
	}

	@Test(expected = FragmentCollectionNameException.class)
	public void testAddFragmentCollectionWithNullName() throws Exception {
		FragmentTestUtil.addFragmentCollection(_group.getGroupId(), null);
	}

	@Test
	public void testAddMultipleFragmentCollections() throws PortalException {
		int originalFragmentCollectionsCount =
			_fragmentCollectionLocalService.getFragmentCollectionsCount();

		FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		int actualFragmentCollectionsCount =
			_fragmentCollectionLocalService.getFragmentCollectionsCount();

		Assert.assertEquals(
			originalFragmentCollectionsCount + 2,
			actualFragmentCollectionsCount);
	}

	@Test
	public void testDeleteFragmentCollection() throws PortalException {
		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		_fragmentCollectionLocalService.deleteFragmentCollection(
			fragmentCollection.getFragmentCollectionId());

		Assert.assertNull(
			_fragmentCollectionLocalService.fetchFragmentCollection(
				fragmentCollection.getFragmentCollectionId()));

		Assert.assertNull(
			PortletFileRepositoryUtil.fetchPortletRepository(
				_group.getGroupId(), FragmentPortletKeys.FRAGMENT));
	}

	@Test
	public void testDeleteFragmentCollectionByExternalReferenceCode()
		throws Exception {

		String externalReferenceCode = StringUtil.randomString();

		_fragmentCollectionLocalService.addFragmentCollection(
			externalReferenceCode, TestPropsValues.getUserId(),
			_group.getGroupId(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_fragmentCollectionLocalService.deleteFragmentCollection(
			externalReferenceCode, _group.getGroupId());

		Assert.assertNull(
			_fragmentCollectionLocalService.
				fetchFragmentCollectionByExternalReferenceCode(
					externalReferenceCode, _group.getGroupId()));
	}

	@Test
	public void testDeleteFragmentCollectionByFragmentCollectionId()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		_fragmentCollectionLocalService.deleteFragmentCollection(
			fragmentCollection.getFragmentCollectionId());

		Assert.assertNull(
			_fragmentCollectionLocalService.fetchFragmentCollection(
				fragmentCollection.getFragmentCollectionId()));
	}

	@Test
	public void testDeleteFragmentCollectionWithFragmentEntries()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection.getFragmentCollectionId());

		_fragmentCollectionLocalService.deleteFragmentCollection(
			fragmentCollection.getFragmentCollectionId());

		Assert.assertNull(
			_fragmentCollectionLocalService.fetchFragmentCollection(
				fragmentCollection.getFragmentCollectionId()));
	}

	@Test
	public void testFetchFragmentCollectionByFragmentCollectionId()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		Assert.assertEquals(
			fragmentCollection,
			_fragmentCollectionLocalService.fetchFragmentCollection(
				fragmentCollection.getFragmentCollectionId()));
	}

	@Test
	public void testFetchFragmentCollectionByGroupIdAndFragmentCollectionKey()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString(),
				"FRAGMENTCOLLECTIONKEY");

		Assert.assertEquals(
			fragmentCollection,
			_fragmentCollectionLocalService.fetchFragmentCollection(
				_group.getGroupId(), "FRAGMENTCOLLECTIONKEY"));
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetExportableFragmentCollectionsByFragmentCollectionIds()
		throws Exception {

		FragmentCollection fragmentCollection1 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection1.getFragmentCollectionId());

		FragmentCollection fragmentCollection2 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection2.getFragmentCollectionId());

		FragmentCollection fragmentCollection3 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.getExportableFragmentCollections(
				new long[] {
					fragmentCollection1.getFragmentCollectionId(),
					fragmentCollection2.getFragmentCollectionId(),
					fragmentCollection3.getFragmentCollectionId()
				});

		Assert.assertTrue(fragmentCollections.contains(fragmentCollection1));
		Assert.assertTrue(fragmentCollections.contains(fragmentCollection2));
		Assert.assertEquals(
			fragmentCollections.toString(), 2, fragmentCollections.size());
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetExportableFragmentCollectionsByFragmentCollectionIdsWithNonexistentIds()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection.getFragmentCollectionId());

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.getExportableFragmentCollections(
				new long[] {
					fragmentCollection.getFragmentCollectionId(),
					RandomTestUtil.randomLong()
				});

		Assert.assertTrue(fragmentCollections.contains(fragmentCollection));
		Assert.assertEquals(
			fragmentCollections.toString(), 1, fragmentCollections.size());
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetExportableFragmentCollectionsByFragmentCollectionIdsWithNotExportableFragmentCollections()
		throws Exception {

		FragmentCollection fragmentCollection1 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());
		FragmentCollection fragmentCollection2 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.getExportableFragmentCollections(
				new long[] {
					fragmentCollection1.getFragmentCollectionId(),
					fragmentCollection2.getFragmentCollectionId()
				});

		Assert.assertEquals(
			fragmentCollections.toString(), 0, fragmentCollections.size());
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetExportableFragmentCollectionsCount() throws Exception {
		FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentCollection fragmentCollection1 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection1.getFragmentCollectionId());

		FragmentCollection fragmentCollection2 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection2.getFragmentCollectionId());

		Assert.assertEquals(
			2,
			_fragmentCollectionLocalService.
				getExportableFragmentCollectionsCount(
					new long[] {_group.getGroupId()}));
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetExportableFragmentCollectionsCountWithApprovedResource()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		Repository repository = _portletFileRepository.addPortletRepository(
			_group.getGroupId(), FragmentPortletKeys.FRAGMENT,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Folder folder = _portletFileRepository.addPortletFolder(
			TestPropsValues.getUserId(), repository.getRepositoryId(),
			fragmentCollection.getResourcesFolderId(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());

		_portletFileRepository.addPortletFileEntry(
			_group.getGroupId(), TestPropsValues.getUserId(),
			FragmentCollection.class.getName(),
			fragmentCollection.getFragmentCollectionId(),
			FragmentPortletKeys.FRAGMENT, folder.getFolderId(), new byte[0],
			RandomTestUtil.randomString(), ContentTypes.IMAGE_PNG, false);

		Assert.assertEquals(
			1,
			_fragmentCollectionLocalService.
				getExportableFragmentCollectionsCount(
					new long[] {_group.getGroupId()}));

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.
				getExportableFragmentCollectionsByGroupId(
					new long[] {_group.getGroupId()}, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

		Assert.assertTrue(fragmentCollections.contains(fragmentCollection));
		Assert.assertEquals(
			fragmentCollections.toString(), 1, fragmentCollections.size());
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetExportableFragmentCollectionsCountWithFragmentComposition()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentCompositionTestUtil.addFragmentComposition(
			fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString());

		Assert.assertEquals(
			1,
			_fragmentCollectionLocalService.
				getExportableFragmentCollectionsCount(
					new long[] {_group.getGroupId()}));
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetExportableFragmentCollectionsCountWithMarketplaceFragmentEntry()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection.getFragmentCollectionId());

		fragmentEntry.setMarketplace(true);

		_fragmentEntryLocalService.updateFragmentEntry(fragmentEntry);

		Assert.assertEquals(
			0,
			_fragmentCollectionLocalService.
				getExportableFragmentCollectionsCount(
					new long[] {_group.getGroupId()}));
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetExportableFragmentCollectionsCountWithNameFilter()
		throws Exception {

		String keyword = RandomTestUtil.randomString();

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), keyword + RandomTestUtil.randomString());

		FragmentCollection fragmentCollection1 =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString() + keyword);

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection1.getFragmentCollectionId());

		FragmentCollection fragmentCollection2 =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString());

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection2.getFragmentCollectionId());

		Assert.assertEquals(
			1,
			_fragmentCollectionLocalService.
				getExportableFragmentCollectionsCount(
					new long[] {_group.getGroupId()}, keyword));
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetExportableFragmentCollectionsCountWithNameFilterAndNoExportableFragmentCollections()
		throws Exception {

		String keyword = RandomTestUtil.randomString();

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), keyword + RandomTestUtil.randomString());

		Assert.assertEquals(
			0,
			_fragmentCollectionLocalService.
				getExportableFragmentCollectionsCount(
					new long[] {_group.getGroupId()}, keyword));
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetExportableFragmentCollectionsCountWithNoExportableFragmentCollections()
		throws Exception {

		FragmentTestUtil.addFragmentCollection(_group.getGroupId());
		FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		Assert.assertEquals(
			0,
			_fragmentCollectionLocalService.
				getExportableFragmentCollectionsCount(
					new long[] {_group.getGroupId()}));
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetExportableFragmentCollectionsCountWithReactFragmentEntry()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentEntryTestUtil.addFragmentEntryByType(
			fragmentCollection.getFragmentCollectionId(),
			FragmentConstants.TYPE_REACT);

		Assert.assertEquals(
			0,
			_fragmentCollectionLocalService.
				getExportableFragmentCollectionsCount(
					new long[] {_group.getGroupId()}));
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetExportableFragmentCollectionsWithExportableFragmentCollections()
		throws Exception {

		FragmentCollection fragmentCollection1 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentCollection fragmentCollection2 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection1.getFragmentCollectionId());
		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection2.getFragmentCollectionId());

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.
				getExportableFragmentCollectionsByGroupId(
					new long[] {_group.getGroupId()}, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

		Assert.assertTrue(fragmentCollections.contains(fragmentCollection1));
		Assert.assertTrue(fragmentCollections.contains(fragmentCollection2));
		Assert.assertEquals(
			fragmentCollections.toString(), 2, fragmentCollections.size());
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetExportableFragmentCollectionsWithNameFilter()
		throws Exception {

		String keyword = RandomTestUtil.randomString();

		FragmentCollection fragmentCollection1 =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString() + keyword);

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection1.getFragmentCollectionId());

		FragmentCollection fragmentCollection2 =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString());

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection2.getFragmentCollectionId());

		FragmentCollection fragmentCollection3 =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), keyword + RandomTestUtil.randomString());

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection3.getFragmentCollectionId());

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.
				getExportableFragmentCollectionsByGroupId(
					new long[] {_group.getGroupId()}, keyword,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertTrue(fragmentCollections.contains(fragmentCollection1));
		Assert.assertTrue(fragmentCollections.contains(fragmentCollection3));
		Assert.assertEquals(
			fragmentCollections.toString(), 2, fragmentCollections.size());
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetExportableFragmentCollectionsWithNameFilterAndNotExportableFragmentCollections()
		throws Exception {

		String keyword = RandomTestUtil.randomString();

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), keyword + RandomTestUtil.randomString());

		FragmentCollection exportableFragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString());

		FragmentEntryTestUtil.addFragmentEntry(
			exportableFragmentCollection.getFragmentCollectionId());

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.
				getExportableFragmentCollectionsByGroupId(
					new long[] {_group.getGroupId()}, keyword,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			fragmentCollections.toString(), 0, fragmentCollections.size());
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetExportableFragmentCollectionsWithNameFilterAndOrderByNameComparatorAsc()
		throws Exception {

		String keyword = RandomTestUtil.randomString();

		FragmentCollection fragmentCollection1 =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(),
				"AA" + keyword + RandomTestUtil.randomString());

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection1.getFragmentCollectionId());

		FragmentCollection fragmentCollection2 =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(),
				"BB" + keyword + RandomTestUtil.randomString());

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection2.getFragmentCollectionId());

		FragmentCollectionNameComparator fragmentCollectionNameComparator =
			FragmentCollectionNameComparator.getInstance(true);

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.
				getExportableFragmentCollectionsByGroupId(
					new long[] {_group.getGroupId()}, keyword,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					fragmentCollectionNameComparator);

		FragmentCollection firstFragmentCollection = fragmentCollections.get(0);

		Assert.assertEquals(
			fragmentCollection1.getName(), firstFragmentCollection.getName());
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetExportableFragmentCollectionsWithNoExportableFragmentCollections()
		throws Exception {

		FragmentTestUtil.addFragmentCollection(_group.getGroupId());
		FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.
				getExportableFragmentCollectionsByGroupId(
					new long[] {_group.getGroupId()}, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			fragmentCollections.toString(), 0, fragmentCollections.size());
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetExportableFragmentCollectionsWithOrderByNameComparatorAsc()
		throws Exception {

		FragmentCollection fragmentCollection1 =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "AA Exportable Collection");

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection1.getFragmentCollectionId());

		FragmentCollection fragmentCollection2 =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "BB Exportable Collection");

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection2.getFragmentCollectionId());

		FragmentCollectionNameComparator fragmentCollectionNameComparator =
			FragmentCollectionNameComparator.getInstance(true);

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.
				getExportableFragmentCollectionsByGroupId(
					new long[] {_group.getGroupId()}, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, fragmentCollectionNameComparator);

		FragmentCollection firstFragmentCollection = fragmentCollections.get(0);

		Assert.assertEquals(
			fragmentCollection1.getName(), firstFragmentCollection.getName());
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetExportableFragmentCollectionsWithOrderByNameComparatorDesc()
		throws Exception {

		FragmentCollection fragmentCollection1 =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "AA Exportable Collection");

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection1.getFragmentCollectionId());

		FragmentCollection fragmentCollection2 =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "BB Exportable Collection");

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection2.getFragmentCollectionId());

		FragmentCollectionNameComparator fragmentCollectionNameComparator =
			FragmentCollectionNameComparator.getInstance(false);

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.
				getExportableFragmentCollectionsByGroupId(
					new long[] {_group.getGroupId()}, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, fragmentCollectionNameComparator);

		FragmentCollection firstFragmentCollection = fragmentCollections.get(0);

		Assert.assertEquals(
			fragmentCollection2.getName(), firstFragmentCollection.getName());
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetExportableFragmentCollectionsWithPagination()
		throws Exception {

		FragmentCollection fragmentCollection1 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection1.getFragmentCollectionId());

		FragmentCollection fragmentCollection2 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection2.getFragmentCollectionId());

		FragmentCollection fragmentCollection3 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection3.getFragmentCollectionId());

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.
				getExportableFragmentCollectionsByGroupId(
					new long[] {_group.getGroupId()}, 0, 2, null);

		Assert.assertEquals(
			fragmentCollections.toString(), 2, fragmentCollections.size());
	}

	@Test
	public void testGetFragmentCollectionsByKeywords() throws Exception {
		String fragmentCollectionName = RandomTestUtil.randomString();

		List<FragmentCollection> originalFragmentCollections =
			_fragmentCollectionLocalService.getFragmentCollections(
				_group.getGroupId(), fragmentCollectionName, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), fragmentCollectionName);

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), fragmentCollectionName);

		List<FragmentCollection> actualFragmentCollections =
			_fragmentCollectionLocalService.getFragmentCollections(
				_group.getGroupId(), fragmentCollectionName, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			actualFragmentCollections.toString(),
			originalFragmentCollections.size() + 2,
			actualFragmentCollections.size());
	}

	@Test
	public void testGetFragmentCollectionsByOrderByCreateDateComparatorAsc()
		throws Exception {

		LocalDateTime localDateTime = LocalDateTime.now();

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection",
				Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "A Fragment Collection",
			Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "B Fragment Collection",
			Timestamp.valueOf(localDateTime));

		FragmentCollectionCreateDateComparator
			fragmentCollectionCreateDateComparator =
				FragmentCollectionCreateDateComparator.getInstance(true);

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				fragmentCollectionCreateDateComparator);

		FragmentCollection firstFragmentCollection = fragmentCollections.get(0);

		Assert.assertEquals(fragmentCollection, firstFragmentCollection);
	}

	@Test
	public void testGetFragmentCollectionsByOrderByCreateDateComparatorDesc()
		throws Exception {

		LocalDateTime localDateTime = LocalDateTime.now();

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection",
				Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "A Fragment Collection",
			Timestamp.valueOf(localDateTime));

		localDateTime = localDateTime.plus(1, ChronoUnit.MINUTES);

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "B Fragment Collection",
			Timestamp.valueOf(localDateTime));

		FragmentCollectionCreateDateComparator
			fragmentCollectionCreateDateComparator =
				FragmentCollectionCreateDateComparator.getInstance(false);

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				fragmentCollectionCreateDateComparator);

		FragmentCollection lastFragmentCollection = fragmentCollections.get(
			fragmentCollections.size() - 1);

		Assert.assertEquals(fragmentCollection, lastFragmentCollection);
	}

	@Test
	public void testGetFragmentCollectionsByOrderByNameComparatorAsc()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "AA Fragment Collection");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "AB Fragment Collection");

		FragmentCollectionNameComparator fragmentCollectionNameComparator =
			FragmentCollectionNameComparator.getInstance(true);

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				fragmentCollectionNameComparator);

		FragmentCollection firstFragmentCollection = fragmentCollections.get(0);

		Assert.assertEquals(
			fragmentCollections.toString(), fragmentCollection.getName(),
			firstFragmentCollection.getName());
	}

	@Test
	public void testGetFragmentCollectionsByOrderByNameComparatorDesc()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "AA Fragment Collection");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "AB Fragment Collection");

		FragmentCollectionNameComparator fragmentCollectionNameComparator =
			FragmentCollectionNameComparator.getInstance(false);

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				fragmentCollectionNameComparator);

		FragmentCollection lastFragmentCollection = fragmentCollections.get(
			fragmentCollections.size() - 1);

		Assert.assertEquals(
			fragmentCollections.toString(), fragmentCollection.getName(),
			lastFragmentCollection.getName());
	}

	@Test
	public void testGetFragmentCollectionsByOrderByRange() throws Exception {
		int collectionSize = 5;

		for (int i = 0; i < collectionSize; i++) {
			String fragmentCollectionName = "Fragment Collection " + i;

			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), fragmentCollectionName);
		}

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionLocalService.getFragmentCollections(
				_group.getGroupId(), 1, 4, null);

		Assert.assertEquals(
			fragmentCollections.toString(), collectionSize - 2,
			fragmentCollections.size());

		FragmentCollection firstFragmentCollection = fragmentCollections.get(0);

		FragmentCollection lastFragmentCollection = fragmentCollections.get(
			fragmentCollections.size() - 1);

		Assert.assertEquals(
			"Fragment Collection 1", firstFragmentCollection.getName());

		Assert.assertEquals(
			"Fragment Collection 3", lastFragmentCollection.getName());
	}

	@Test
	public void testGetOSGIServiceIdentifier() {
		Assert.assertEquals(
			_fragmentCollectionLocalService.getOSGiServiceIdentifier(),
			FragmentCollectionLocalService.class.getName());
	}

	@Test
	public void testUpdateFragmentCollection() throws Exception {
		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "Fragment Collection");

		fragmentCollection =
			_fragmentCollectionLocalService.updateFragmentCollection(
				fragmentCollection.getFragmentCollectionId(),
				"Fragment Collection New", "Fragment Description");

		Assert.assertEquals(
			"Fragment Collection New", fragmentCollection.getName());

		Assert.assertEquals(
			"Fragment Description", fragmentCollection.getDescription());
	}

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private PortletFileRepository _portletFileRepository;

}