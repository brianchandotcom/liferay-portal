/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.exception.DuplicateFragmentCollectionExternalReferenceCodeException;
import com.liferay.fragment.exception.NoSuchCollectionException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.test.util.FragmentCompositionTestUtil;
import com.liferay.fragment.test.util.FragmentEntryTestUtil;
import com.liferay.fragment.test.util.FragmentTestUtil;
import com.liferay.fragment.util.comparator.FragmentCollectionCreateDateComparator;
import com.liferay.fragment.util.comparator.FragmentCollectionNameComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
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
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class FragmentCollectionServiceTest {

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
	public void testAddFragmentCollection() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		String name = RandomTestUtil.randomString();

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				null, _group.getGroupId(), name, StringPool.BLANK,
				serviceContext);

		fragmentCollection = _fragmentCollectionService.fetchFragmentCollection(
			fragmentCollection.getFragmentCollectionId());

		Assert.assertEquals(name, fragmentCollection.getName());
	}

	@Test(expected = PrincipalException.class)
	public void testAddFragmentCollectionByExternalReferenceCodeWithoutPermissions()
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			Company company = _companyLocalService.fetchCompany(
				TestPropsValues.getCompanyId());

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(company.getGuestUser()));

			String externalReferenceCode = StringUtil.randomString();

			_fragmentCollectionService.addFragmentCollection(
				externalReferenceCode, _group.getGroupId(),
				RandomTestUtil.randomString(), null,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
	}

	@Test(
		expected = DuplicateFragmentCollectionExternalReferenceCodeException.class
	)
	public void testAddFragmentCollectionWithExistingExternalReferenceCode()
		throws Exception {

		String externalReferenceCode = StringUtil.randomString();

		_fragmentCollectionService.addFragmentCollection(
			externalReferenceCode, _group.getGroupId(),
			RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
		_fragmentCollectionService.addFragmentCollection(
			externalReferenceCode, _group.getGroupId(),
			RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test
	public void testAddFragmentCollectionWithFragmentCollectionKey()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		String name = RandomTestUtil.randomString();

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				null, _group.getGroupId(), RandomTestUtil.randomString(), name,
				StringPool.BLANK, false, serviceContext);

		fragmentCollection = _fragmentCollectionService.fetchFragmentCollection(
			fragmentCollection.getFragmentCollectionId());

		Assert.assertEquals(name, fragmentCollection.getName());
	}

	@Test
	public void testDeleteFragmentCollection() throws Exception {
		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		_fragmentCollectionService.deleteFragmentCollection(
			fragmentCollection.getFragmentCollectionId());

		Assert.assertNull(
			_fragmentCollectionService.fetchFragmentCollection(
				fragmentCollection.getFragmentCollectionId()));
	}

	@Test(expected = NoSuchCollectionException.class)
	public void testDeleteFragmentCollectionByExternalReferenceCode()
		throws Exception {

		String externalReferenceCode = StringUtil.randomString();

		_fragmentCollectionService.addFragmentCollection(
			externalReferenceCode, _group.getGroupId(),
			RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_fragmentCollectionService.deleteFragmentCollection(
			externalReferenceCode, _group.getGroupId());

		_fragmentCollectionService.getFragmentCollectionByExternalReferenceCode(
			externalReferenceCode, _group.getGroupId());
	}

	@Test(expected = PrincipalException.class)
	public void testDeleteFragmentCollectionByExternalReferenceCodeWithoutPermissions()
		throws Exception {

		String externalReferenceCode = StringUtil.randomString();

		_fragmentCollectionService.addFragmentCollection(
			externalReferenceCode, _group.getGroupId(),
			RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			Company company = _companyLocalService.fetchCompany(
				TestPropsValues.getCompanyId());

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(company.getGuestUser()));

			_fragmentCollectionService.deleteFragmentCollection(
				externalReferenceCode, _group.getGroupId());

			Assert.assertNull(
				_fragmentCollectionService.
					getFragmentCollectionByExternalReferenceCode(
						externalReferenceCode, _group.getGroupId()));
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
	}

	@Test
	public void testDeleteFragmentCollections() throws Exception {
		FragmentCollection fragmentCollection1 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentCollection fragmentCollection2 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		_fragmentCollectionService.deleteFragmentCollections(
			new long[] {
				fragmentCollection1.getFragmentCollectionId(),
				fragmentCollection2.getFragmentCollectionId()
			});

		Assert.assertNull(
			_fragmentCollectionService.fetchFragmentCollection(
				fragmentCollection1.getFragmentCollectionId()));
		Assert.assertNull(
			_fragmentCollectionService.fetchFragmentCollection(
				fragmentCollection2.getFragmentCollectionId()));
	}

	@Test
	public void testFetchFragmentCollection() throws Exception {
		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		Assert.assertNotNull(
			_fragmentCollectionService.fetchFragmentCollection(
				fragmentCollection.getFragmentCollectionId()));
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetExportableFragmentCollectionsByFragmentCollectionIds()
		throws Exception {

		FragmentCollection fragmentCollection1 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentCollection fragmentCollection2 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentCollection fragmentCollection3 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection1.getFragmentCollectionId());
		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection2.getFragmentCollectionId());

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionService.getExportableFragmentCollections(
				new long[] {_group.getGroupId()},
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
			_fragmentCollectionService.getExportableFragmentCollections(
				new long[] {_group.getGroupId()},
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
			_fragmentCollectionService.getExportableFragmentCollections(
				new long[] {_group.getGroupId()},
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
		FragmentCollection fragmentCollection1 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentCollection fragmentCollection2 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection1.getFragmentCollectionId());
		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection2.getFragmentCollectionId());

		Assert.assertEquals(
			2,
			_fragmentCollectionService.getExportableFragmentCollectionsCount(
				new long[] {_group.getGroupId()}));
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
			_fragmentCollectionService.getExportableFragmentCollectionsCount(
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
			_fragmentCollectionService.getExportableFragmentCollectionsCount(
				new long[] {_group.getGroupId()}));
	}

	@Test
	@TestInfo("LPD-83557")
	public void testGetExportableFragmentCollectionsCountWithNameFilter()
		throws Exception {

		String keyword = RandomTestUtil.randomString();

		FragmentCollection fragmentCollection1 =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString() + keyword);

		FragmentCollection fragmentCollection2 =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString());

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), keyword + RandomTestUtil.randomString());

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection1.getFragmentCollectionId());
		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection2.getFragmentCollectionId());

		Assert.assertEquals(
			1,
			_fragmentCollectionService.getExportableFragmentCollectionsCount(
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
			_fragmentCollectionService.getExportableFragmentCollectionsCount(
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
			_fragmentCollectionService.getExportableFragmentCollectionsCount(
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
			_fragmentCollectionService.getExportableFragmentCollectionsCount(
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
			_fragmentCollectionService.getExportableFragmentCollections(
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

		FragmentCollection fragmentCollection2 =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), RandomTestUtil.randomString());

		FragmentCollection fragmentCollection3 =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), keyword + RandomTestUtil.randomString());

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection1.getFragmentCollectionId());
		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection2.getFragmentCollectionId());
		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection3.getFragmentCollectionId());

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionService.getExportableFragmentCollections(
				new long[] {_group.getGroupId()}, keyword, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

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
			_fragmentCollectionService.getExportableFragmentCollections(
				new long[] {_group.getGroupId()}, keyword, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

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

		FragmentCollection fragmentCollection2 =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(),
				"BB" + keyword + RandomTestUtil.randomString());

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection1.getFragmentCollectionId());
		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection2.getFragmentCollectionId());

		FragmentCollectionNameComparator fragmentCollectionNameComparator =
			FragmentCollectionNameComparator.getInstance(true);

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionService.getExportableFragmentCollections(
				new long[] {_group.getGroupId()}, keyword, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, fragmentCollectionNameComparator);

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
			_fragmentCollectionService.getExportableFragmentCollections(
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

		FragmentCollection fragmentCollection2 =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "BB Exportable Collection");

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection1.getFragmentCollectionId());
		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection2.getFragmentCollectionId());

		FragmentCollectionNameComparator fragmentCollectionNameComparator =
			FragmentCollectionNameComparator.getInstance(true);

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionService.getExportableFragmentCollections(
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

		FragmentCollection fragmentCollection2 =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "BB Exportable Collection");

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection1.getFragmentCollectionId());
		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection2.getFragmentCollectionId());

		FragmentCollectionNameComparator fragmentCollectionNameComparator =
			FragmentCollectionNameComparator.getInstance(false);

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionService.getExportableFragmentCollections(
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

		FragmentCollection fragmentCollection2 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentCollection fragmentCollection3 =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection1.getFragmentCollectionId());
		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection2.getFragmentCollectionId());
		FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection3.getFragmentCollectionId());

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionService.getExportableFragmentCollections(
				new long[] {_group.getGroupId()}, 0, 2, null);

		Assert.assertEquals(
			fragmentCollections.toString(), 2, fragmentCollections.size());
	}

	@Test
	public void testGetFragmentCollectionFileEntries() throws Exception {
		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				null, _group.getGroupId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), StringPool.BLANK, false,
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId()));

		List<FileEntry> originalFragmentCollectionFileEntries =
			_fragmentCollectionService.getFragmentCollectionFileEntries(
				fragmentCollection.getFragmentCollectionId());

		_portletFileRepository.addPortletFileEntry(
			_group.getGroupId(), TestPropsValues.getUserId(),
			FragmentCollection.class.getName(),
			fragmentCollection.getFragmentCollectionId(),
			FragmentPortletKeys.FRAGMENT,
			fragmentCollection.getResourcesFolderId(), new byte[0],
			RandomTestUtil.randomString(), ContentTypes.IMAGE_PNG, false);

		List<FileEntry> actualFragmentCollectionFileEntries =
			_fragmentCollectionService.getFragmentCollectionFileEntries(
				fragmentCollection.getFragmentCollectionId());

		Assert.assertEquals(
			actualFragmentCollectionFileEntries.toString(),
			originalFragmentCollectionFileEntries.size() + 1,
			actualFragmentCollectionFileEntries.size());
	}

	@Test
	public void testGetFragmentCollections() throws Exception {
		List<FragmentCollection> originalFragmentCollections =
			_fragmentCollectionService.getFragmentCollections(
				_group.getGroupId());

		FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		List<FragmentCollection> actualFragmentCollections =
			_fragmentCollectionService.getFragmentCollections(
				_group.getGroupId());

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
			_fragmentCollectionService.getFragmentCollections(
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

		localDateTime = localDateTime.plus(1, ChronoUnit.SECONDS);

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "B Fragment Collection",
			Timestamp.valueOf(localDateTime));

		FragmentCollectionCreateDateComparator
			fragmentCollectionCreateDateComparator =
				FragmentCollectionCreateDateComparator.getInstance(false);

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionService.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				fragmentCollectionCreateDateComparator);

		FragmentCollection lastFragmentCollection = fragmentCollections.get(
			fragmentCollections.size() - 1);

		Assert.assertEquals(fragmentCollection, lastFragmentCollection);
	}

	@Test
	public void testGetFragmentCollectionsByOrderByNameComparatorAndKeywordsAsc()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "AA Fragment Collection");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "AB Fragment");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "AC Fragment Collection");

		FragmentCollectionNameComparator fragmentCollectionNameComparator =
			FragmentCollectionNameComparator.getInstance(true);

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionService.getFragmentCollections(
				_group.getGroupId(), "Collection", QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, fragmentCollectionNameComparator);

		FragmentCollection firstFragmentCollection = fragmentCollections.get(0);

		Assert.assertEquals(
			fragmentCollections.toString(), firstFragmentCollection.getName(),
			fragmentCollection.getName());
	}

	@Test
	public void testGetFragmentCollectionsByOrderByNameComparatorAndKeywordsDesc()
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(
				_group.getGroupId(), "AA Fragment Collection");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "AB Fragment");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "AC Fragment Collection");

		FragmentCollectionNameComparator fragmentCollectionNameComparator =
			FragmentCollectionNameComparator.getInstance(false);

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionService.getFragmentCollections(
				_group.getGroupId(), "Collection", QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, fragmentCollectionNameComparator);

		FragmentCollection lastFragmentCollection = fragmentCollections.get(
			fragmentCollections.size() - 1);

		Assert.assertEquals(
			fragmentCollections.toString(), lastFragmentCollection.getName(),
			fragmentCollection.getName());
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
			_fragmentCollectionService.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				fragmentCollectionNameComparator);

		FragmentCollection firstFragmentCollection = fragmentCollections.get(0);

		Assert.assertEquals(
			fragmentCollection.toString(), firstFragmentCollection.getName(),
			fragmentCollection.getName());
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
			_fragmentCollectionService.getFragmentCollections(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				fragmentCollectionNameComparator);

		FragmentCollection lastFragmentCollection = fragmentCollections.get(
			fragmentCollections.size() - 1);

		Assert.assertEquals(
			fragmentCollection.toString(), lastFragmentCollection.getName(),
			fragmentCollection.getName());
	}

	@Test
	public void testGetFragmentCollectionsByRange() throws Exception {
		int collectionSize = 5;

		for (int i = 0; i < collectionSize; i++) {
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());
		}

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionService.getFragmentCollections(
				_group.getGroupId(), 1, 4, null);

		Assert.assertEquals(
			fragmentCollections.toString(), collectionSize - 2,
			fragmentCollections.size());
	}

	@Test
	public void testGetFragmentCollectionsCount() throws Exception {
		int originalFragmentCollectionsCount =
			_fragmentCollectionService.getFragmentCollectionsCount(
				_group.getGroupId());

		FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		int actualFragmentCollectionsCount =
			_fragmentCollectionService.getFragmentCollectionsCount(
				_group.getGroupId());

		Assert.assertEquals(
			originalFragmentCollectionsCount + 2,
			actualFragmentCollectionsCount);
	}

	@Test
	public void testGetFragmentCollectionsCountWithName() throws Exception {
		int originalFragmentCollectionsCount =
			_fragmentCollectionService.getFragmentCollectionsCount(
				_group.getGroupId(), "Test");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "Collection Name");

		FragmentTestUtil.addFragmentCollection(
			_group.getGroupId(), "Collection Test Name");

		int actualFragmentCollectionsCount =
			_fragmentCollectionService.getFragmentCollectionsCount(
				_group.getGroupId(), "Test");

		Assert.assertEquals(
			originalFragmentCollectionsCount + 1,
			actualFragmentCollectionsCount);
	}

	@Test
	public void testUpdateFragmentCollection() throws Exception {
		String name = RandomTestUtil.randomString();

		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				null, _group.getGroupId(), RandomTestUtil.randomString(), name,
				StringPool.BLANK, false,
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId()));

		fragmentCollection = _fragmentCollectionService.fetchFragmentCollection(
			fragmentCollection.getFragmentCollectionId());

		Assert.assertEquals(name, fragmentCollection.getName());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private FragmentCollectionService _fragmentCollectionService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private PortletFileRepository _portletFileRepository;

}