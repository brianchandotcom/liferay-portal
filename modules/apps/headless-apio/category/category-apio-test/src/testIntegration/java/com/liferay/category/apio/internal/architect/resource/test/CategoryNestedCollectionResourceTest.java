/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.category.apio.internal.architect.resource.test;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.category.apio.architect.model.Category;
import com.liferay.category.apio.internal.architect.resource.test.model.CategoryImpl;
import com.liferay.portal.apio.test.util.PaginationRequest;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.lang.reflect.Method;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class CategoryNestedCollectionResourceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_assetVocabulary = AssetVocabularyLocalServiceUtil.addDefaultVocabulary(
			_group.getGroupId());
	}

	@Test
	public void testAddAssetCategory() throws Exception {
		Category category = new CategoryImpl(
			"testAddAssetCategory category name",
			"testAddAssetCategory category description");

		AssetCategory assetCategory = _addAssetCategory(
			_assetVocabulary.getVocabularyId(), category);

		Assert.assertNotNull(assetCategory.getCreateDate());

		Map<Locale, String> descriptionMap = category.getDescriptionMap(
			LocaleUtil.getDefault());

		Assert.assertEquals(
			descriptionMap.get(LocaleUtil.getDefault()),
			assetCategory.getDescription(LocaleUtil.getDefault()));

		Assert.assertNotNull(assetCategory.getModifiedDate());

		Map<Locale, String> nameMap = category.getNameMap(
			LocaleUtil.getDefault());

		Assert.assertEquals(
			nameMap.get(LocaleUtil.getDefault()), assetCategory.getName());
	}

	@Test
	public void testGetAssetCategory() throws Exception {
		Category category = new CategoryImpl(
			"testGetAssetCategory category name",
			"testGetAssetCategory category description");

		AssetCategory assetCategory = _addAssetCategory(
			_assetVocabulary.getVocabularyId(), category);

		AssetCategory assetCategoryRetrieved = _getAssetCategory(
			assetCategory.getCategoryId());

		Map<Locale, String> descriptionMap = category.getDescriptionMap(
			LocaleUtil.getDefault());

		Assert.assertEquals(
			descriptionMap.get(LocaleUtil.getDefault()),
			assetCategoryRetrieved.getDescription(LocaleUtil.getDefault()));

		Map<Locale, String> nameMap = category.getNameMap(
			LocaleUtil.getDefault());

		Assert.assertEquals(
			nameMap.get(LocaleUtil.getDefault()),
			assetCategoryRetrieved.getName());
	}

	/**
	 * Test get page items.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetPageItems() throws Exception {
		Category category = new CategoryImpl(
			"testGetPageItems category name",
			"testGetPageItems category description");

		_addAssetCategory(_assetVocabulary.getVocabularyId(), category);

		PageItems<AssetCategory> pageItems = _getPageItems(
			PaginationRequest.of(10, 1), _assetVocabulary.getVocabularyId());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<AssetCategory> assetCategories =
			(List<AssetCategory>)pageItems.getItems();

		Assert.assertEquals(
			assetCategories.toString(), 1, assetCategories.size());

		AssetCategory assetCategory = assetCategories.get(0);

		Map<Locale, String> descriptionMap = category.getDescriptionMap(
			LocaleUtil.getDefault());

		Assert.assertEquals(
			descriptionMap.get(LocaleUtil.getDefault()),
			assetCategory.getDescription(LocaleUtil.getDefault()));

		Map<Locale, String> nameMap = category.getNameMap(
			LocaleUtil.getDefault());

		Assert.assertEquals(
			nameMap.get(LocaleUtil.getDefault()), assetCategory.getName());
	}

	@Test
	public void testGetPageItemsWithGuestPermissionAndGroupPermissionAndAdminUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);

		_addAssetCategory(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			PageItems<AssetCategory> pageItems = _getPageItems(
				PaginationRequest.of(10, 1),
				_assetVocabulary.getVocabularyId());

			Assert.assertEquals(1, pageItems.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetPageItemsWithGuestPermissionAndGroupPermissionAndGuestUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);

		_addAssetCategory(_group.getGroupId(), serviceContext);

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			PageItems<AssetCategory> pageItems = _getPageItems(
				PaginationRequest.of(10, 1),
				_assetVocabulary.getVocabularyId());

			Assert.assertEquals(1, pageItems.getTotalCount());
		}
	}

	@Test
	public void testGetPageItemsWithGuestPermissionAndGroupPermissionAndNoSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);

		_addAssetCategory(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			PageItems<AssetCategory> pageItems = _getPageItems(
				PaginationRequest.of(10, 1),
				_assetVocabulary.getVocabularyId());

			Assert.assertEquals(1, pageItems.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetPageItemsWithGuestPermissionAndGroupPermissionAndSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);

		_addAssetCategory(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addUser(_group.getGroupId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			PageItems<AssetCategory> pageItems = _getPageItems(
				PaginationRequest.of(10, 1),
				_assetVocabulary.getVocabularyId());

			Assert.assertEquals(1, pageItems.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetPageItemsWithGuestPermissionAndNoGroupPermissionAndAdminUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(false);

		_addAssetCategory(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			PageItems<AssetCategory> pageItems = _getPageItems(
				PaginationRequest.of(10, 1),
				_assetVocabulary.getVocabularyId());

			Assert.assertEquals(1, pageItems.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetPageItemsWithGuestPermissionAndNoGroupPermissionAndGuestUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(false);

		_addAssetCategory(_group.getGroupId(), serviceContext);

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			PageItems<AssetCategory> pageItems = _getPageItems(
				PaginationRequest.of(10, 1),
				_assetVocabulary.getVocabularyId());

			Assert.assertEquals(1, pageItems.getTotalCount());
		}
	}

	@Test
	public void testGetPageItemsWithGuestPermissionAndNoGroupPermissionAndNoSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(false);

		_addAssetCategory(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			PageItems<AssetCategory> pageItems = _getPageItems(
				PaginationRequest.of(10, 1),
				_assetVocabulary.getVocabularyId());

			Assert.assertEquals(1, pageItems.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetPageItemsWithGuestPermissionAndNoGroupPermissionAndSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(false);

		_addAssetCategory(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addUser(_group.getGroupId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			PageItems<AssetCategory> pageItems = _getPageItems(
				PaginationRequest.of(10, 1),
				_assetVocabulary.getVocabularyId());

			Assert.assertEquals(1, pageItems.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetPageItemsWithNoGuestPermissionAndGroupPermissionAndAdminUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(true);

		_addAssetCategory(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			PageItems<AssetCategory> pageItems = _getPageItems(
				PaginationRequest.of(10, 1),
				_assetVocabulary.getVocabularyId());

			Assert.assertEquals(1, pageItems.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetPageItemsWithNoGuestPermissionAndGroupPermissionAndGuestUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(true);

		_addAssetCategory(_group.getGroupId(), serviceContext);

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			PageItems<AssetCategory> pageItems = _getPageItems(
				PaginationRequest.of(10, 1),
				_assetVocabulary.getVocabularyId());

			Assert.assertEquals(0, pageItems.getTotalCount());
		}
	}

	@Test
	public void testGetPageItemsWithNoGuestPermissionAndGroupPermissionAndNoSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(true);

		_addAssetCategory(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			PageItems<AssetCategory> pageItems = _getPageItems(
				PaginationRequest.of(10, 1),
				_assetVocabulary.getVocabularyId());

			Assert.assertEquals(0, pageItems.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetPageItemsWithNoGuestPermissionAndGroupPermissionAndSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(true);

		_addAssetCategory(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addUser(_group.getGroupId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			PageItems<AssetCategory> pageItems = _getPageItems(
				PaginationRequest.of(10, 1),
				_assetVocabulary.getVocabularyId());

			Assert.assertEquals(1, pageItems.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetPageItemsWithNoGuestPermissionAndNoGroupPermissionAndAdminUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		_addAssetCategory(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			PageItems<AssetCategory> pageItems = _getPageItems(
				PaginationRequest.of(10, 1),
				_assetVocabulary.getVocabularyId());

			Assert.assertEquals(1, pageItems.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetPageItemsWithNoGuestPermissionAndNoGroupPermissionAndGuestUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		_addAssetCategory(_group.getGroupId(), serviceContext);

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			PageItems<AssetCategory> pageItems = _getPageItems(
				PaginationRequest.of(10, 1),
				_assetVocabulary.getVocabularyId());

			Assert.assertEquals(0, pageItems.getTotalCount());
		}
	}

	@Test
	public void testGetPageItemsWithNoGuestPermissionAndNoGroupPermissionAndNoSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		_addAssetCategory(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			PageItems<AssetCategory> pageItems = _getPageItems(
				PaginationRequest.of(10, 1),
				_assetVocabulary.getVocabularyId());

			Assert.assertEquals(0, pageItems.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetPageItemsWithNoGuestPermissionAndNoGroupPermissionAndSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		_addAssetCategory(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addUser(_group.getGroupId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			PageItems<AssetCategory> pageItems = _getPageItems(
				PaginationRequest.of(10, 1),
				_assetVocabulary.getVocabularyId());

			Assert.assertEquals(0, pageItems.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testUpdateAssetCategory() throws Exception {
		Category category = new CategoryImpl(
			RandomTestUtil.randomString(10), RandomTestUtil.randomString(10));

		AssetCategory assetCategory = _addAssetCategory(
			_assetVocabulary.getVocabularyId(), category);

		Category updatedCategory = new CategoryImpl(
			"testUpdateAssetCategory category name",
			"testUpdateAssetCategory category description");

		AssetCategory updatedAssetCategory = _updateAssetCategory(
			assetCategory.getCategoryId(), updatedCategory);

		Map<Locale, String> descriptionMap = updatedCategory.getDescriptionMap(
			LocaleUtil.getDefault());

		Assert.assertEquals(
			descriptionMap.get(LocaleUtil.getDefault()),
			updatedAssetCategory.getDescription(LocaleUtil.getDefault()));

		Map<Locale, String> nameMap = updatedCategory.getNameMap(
			LocaleUtil.getDefault());

		Assert.assertEquals(
			nameMap.get(LocaleUtil.getDefault()),
			updatedAssetCategory.getName());
	}

	private AssetCategory _addAssetCategory(
			long vocabularyId, Category category)
		throws Exception {

		NestedCollectionResource nestedCollectionResource =
			_getNestedCollectionResource();

		Class<?> clazz = nestedCollectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_addAssetCategory", long.class, Category.class);

		method.setAccessible(true);

		return (AssetCategory)method.invoke(
			_getNestedCollectionResource(), vocabularyId, category);
	}

	private AssetCategory _addAssetCategory(
			long groupId, ServiceContext serviceContext)
		throws Exception {

		return AssetCategoryServiceUtil.addCategory(
			groupId, RandomTestUtil.randomString(10),
			_assetVocabulary.getVocabularyId(), serviceContext);
	}

	private AssetCategory _getAssetCategory(long assetCategoryId)
		throws Exception {

		NestedCollectionResource nestedCollectionResource =
			_getNestedCollectionResource();

		Class<?> clazz = nestedCollectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_getAssetCategory", long.class);

		method.setAccessible(true);

		return (AssetCategory)method.invoke(
			_getNestedCollectionResource(), assetCategoryId);
	}

	private NestedCollectionResource _getNestedCollectionResource()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		Collection<NestedCollectionResource> collection = registry.getServices(
			NestedCollectionResource.class,
			"(component.name=com.liferay.category.apio.internal.architect." +
				"resource.CategoryNestedCollectionResource)");

		Iterator<NestedCollectionResource> iterator = collection.iterator();

		return iterator.next();
	}

	private PageItems<AssetCategory> _getPageItems(
			Pagination pagination, long groupId)
		throws Exception {

		NestedCollectionResource nestedCollectionResource =
			_getNestedCollectionResource();

		Class<?> clazz = nestedCollectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_getPageItems", Pagination.class, long.class);

		method.setAccessible(true);

		return (PageItems)method.invoke(
			_getNestedCollectionResource(), pagination, groupId);
	}

	private AssetCategory _updateAssetCategory(
			long assetCategoryId, Category category)
		throws Exception {

		NestedCollectionResource nestedCollectionResource =
			_getNestedCollectionResource();

		Class<?> clazz = nestedCollectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_updateAssetCategory", long.class, Category.class);

		method.setAccessible(true);

		return (AssetCategory)method.invoke(
			_getNestedCollectionResource(), assetCategoryId, category);
	}

	private AssetVocabulary _assetVocabulary;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private UserLocalService _userLocalService;

}