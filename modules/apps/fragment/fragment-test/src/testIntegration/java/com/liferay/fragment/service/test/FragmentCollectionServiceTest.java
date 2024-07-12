/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.exception.DuplicateFragmentCollectionExternalReferenceCodeException;
import com.liferay.fragment.exception.NoSuchCollectionException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionService;
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
				StringPool.BLANK, serviceContext);

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
	public void testGetFragmentCollectionFileEntries() throws Exception {
		FragmentCollection fragmentCollection =
			_fragmentCollectionService.addFragmentCollection(
				null, _group.getGroupId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), StringPool.BLANK,
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
				StringPool.BLANK,
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

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private PortletFileRepository _portletFileRepository;

}