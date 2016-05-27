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

package com.liferay.shopping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.shopping.model.ShoppingCategory;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for ShoppingCategory. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCategoryLocalServiceUtil
 * @see com.liferay.shopping.service.base.ShoppingCategoryLocalServiceBaseImpl
 * @see com.liferay.shopping.service.impl.ShoppingCategoryLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ShoppingCategoryLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ShoppingCategoryLocalServiceUtil} to access the shopping category local service. Add custom service methods to {@link com.liferay.shopping.service.impl.ShoppingCategoryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	public ShoppingCategory addCategory(long userId, long parentCategoryId,
		java.lang.String name, java.lang.String description,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Adds the shopping category to the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingCategory the shopping category
	* @return the shopping category that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public ShoppingCategory addShoppingCategory(
		ShoppingCategory shoppingCategory);

	/**
	* Creates a new shopping category with the primary key. Does not add the shopping category to the database.
	*
	* @param categoryId the primary key for the new shopping category
	* @return the new shopping category
	*/
	public ShoppingCategory createShoppingCategory(long categoryId);

	/**
	* Deletes the shopping category from the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingCategory the shopping category
	* @return the shopping category that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public ShoppingCategory deleteShoppingCategory(
		ShoppingCategory shoppingCategory);

	/**
	* Deletes the shopping category with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param categoryId the primary key of the shopping category
	* @return the shopping category that was removed
	* @throws PortalException if a shopping category with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public ShoppingCategory deleteShoppingCategory(long categoryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShoppingCategory fetchShoppingCategory(long categoryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShoppingCategory getCategory(long categoryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShoppingCategory getCategory(long groupId,
		java.lang.String categoryName);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShoppingCategory getParentCategory(ShoppingCategory category)
		throws PortalException;

	/**
	* Returns the shopping category with the primary key.
	*
	* @param categoryId the primary key of the shopping category
	* @return the shopping category
	* @throws PortalException if a shopping category with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShoppingCategory getShoppingCategory(long categoryId)
		throws PortalException;

	public ShoppingCategory updateCategory(long categoryId,
		long parentCategoryId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentCategory,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Updates the shopping category in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param shoppingCategory the shopping category
	* @return the shopping category that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public ShoppingCategory updateShoppingCategory(
		ShoppingCategory shoppingCategory);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCategoriesCount(long groupId, long parentCategoryId);

	/**
	* Returns the number of shopping categories.
	*
	* @return the number of shopping categories
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getShoppingCategoriesCount();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ShoppingCategory> getCategories(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ShoppingCategory> getCategories(long groupId,
		long parentCategoryId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ShoppingCategory> getParentCategories(ShoppingCategory category)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ShoppingCategory> getParentCategories(long categoryId)
		throws PortalException;

	/**
	* Returns a range of all the shopping categories.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingCategoryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping categories
	* @param end the upper bound of the range of shopping categories (not inclusive)
	* @return the range of shopping categories
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ShoppingCategory> getShoppingCategories(int start, int end);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	public void addCategoryResources(ShoppingCategory category,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws PortalException;

	public void addCategoryResources(ShoppingCategory category,
		ModelPermissions modelPermissions) throws PortalException;

	public void addCategoryResources(long categoryId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws PortalException;

	public void addCategoryResources(long categoryId,
		ModelPermissions modelPermissions) throws PortalException;

	public void deleteCategories(long groupId) throws PortalException;

	public void deleteCategory(ShoppingCategory category)
		throws PortalException;

	public void deleteCategory(long categoryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void getSubcategoryIds(List<java.lang.Long> categoryIds,
		long groupId, long categoryId);
}