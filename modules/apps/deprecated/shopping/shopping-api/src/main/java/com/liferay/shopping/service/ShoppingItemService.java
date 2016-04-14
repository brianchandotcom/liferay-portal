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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.shopping.model.ShoppingItem;
import com.liferay.shopping.model.ShoppingItemField;
import com.liferay.shopping.model.ShoppingItemPrice;

import java.io.File;

import java.util.List;

/**
 * Provides the remote service interface for ShoppingItem. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingItemServiceUtil
 * @see com.liferay.shopping.service.base.ShoppingItemServiceBaseImpl
 * @see com.liferay.shopping.service.impl.ShoppingItemServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=shopping", "json.web.service.context.path=ShoppingItem"}, service = ShoppingItemService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ShoppingItemService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ShoppingItemServiceUtil} to access the shopping item remote service. Add custom service methods to {@link com.liferay.shopping.service.impl.ShoppingItemServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public ShoppingItem addItem(long groupId, long categoryId,
		java.lang.String sku, java.lang.String name,
		java.lang.String description, java.lang.String properties,
		java.lang.String fieldsQuantities, boolean requiresShipping,
		int stockQuantity, boolean featured, java.lang.Boolean sale,
		boolean smallImage, java.lang.String smallImageURL, File smallFile,
		boolean mediumImage, java.lang.String mediumImageURL, File mediumFile,
		boolean largeImage, java.lang.String largeImageURL, File largeFile,
		List<ShoppingItemField> itemFields, List<ShoppingItemPrice> itemPrices,
		ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShoppingItem getItem(long itemId) throws PortalException;

	public ShoppingItem updateItem(long itemId, long groupId, long categoryId,
		java.lang.String sku, java.lang.String name,
		java.lang.String description, java.lang.String properties,
		java.lang.String fieldsQuantities, boolean requiresShipping,
		int stockQuantity, boolean featured, java.lang.Boolean sale,
		boolean smallImage, java.lang.String smallImageURL, File smallFile,
		boolean mediumImage, java.lang.String mediumImageURL, File mediumFile,
		boolean largeImage, java.lang.String largeImageURL, File largeFile,
		List<ShoppingItemField> itemFields, List<ShoppingItemPrice> itemPrices,
		ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShoppingItem[] getItemsPrevAndNext(long itemId,
		OrderByComparator<ShoppingItem> obc) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCategoriesItemsCount(long groupId,
		List<java.lang.Long> categoryIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getItemsCount(long groupId, long categoryId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ShoppingItem> getItems(long groupId, long categoryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ShoppingItem> getItems(long groupId, long categoryId,
		int start, int end, OrderByComparator<ShoppingItem> obc);

	public void deleteItem(long itemId) throws PortalException;
}