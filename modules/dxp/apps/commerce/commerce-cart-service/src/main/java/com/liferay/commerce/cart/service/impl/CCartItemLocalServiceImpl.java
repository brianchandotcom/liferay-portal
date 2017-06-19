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

package com.liferay.commerce.cart.service.impl;

import com.liferay.commerce.cart.model.CCartItem;
import com.liferay.commerce.cart.service.base.CCartItemLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CCartItemLocalServiceImpl extends CCartItemLocalServiceBaseImpl {

    @Override
    public CCartItem addCCartItem(
            long cCartId, long cpDefinitionId, long cpInstanceId, int quantity,
            String json, ServiceContext serviceContext)
        throws PortalException {

        User user = userLocalService.getUser(serviceContext.getUserId());
        long groupId = serviceContext.getScopeGroupId();

        long cCartItemId = counterLocalService.increment();

        CCartItem cCartItem = cCartItemPersistence.create(cCartItemId);

        cCartItem.setUuid(serviceContext.getUuid());
        cCartItem.setGroupId(groupId);
        cCartItem.setCompanyId(user.getCompanyId());
        cCartItem.setUserId(user.getUserId());
        cCartItem.setUserName(user.getFullName());
        cCartItem.setCCartId(cCartId);
        cCartItem.setCPDefinitionId(cpDefinitionId);
        cCartItem.setCPInstanceId(cpInstanceId);
        cCartItem.setQuantity(quantity);
        cCartItem.setJson(json);

        cCartItemPersistence.update(cCartItem);

        return cCartItem;
    }

    @Override
    @SystemEvent(type = SystemEventConstants.TYPE_DELETE)
    public CCartItem deleteCCartItem(CCartItem cCartItem)
        throws PortalException {

        cCartItemPersistence.remove(cCartItem);

        return cCartItem;
    }

    @Override
    public CCartItem deleteCCartItem(long cCartItemId) throws PortalException {
        CCartItem cCartItem = cCartItemPersistence.findByPrimaryKey(
            cCartItemId);

        return cCartItemLocalService.deleteCCartItem(cCartItem);
    }

    @Override
    public List<CCartItem> getCCartItems(
        long cCartId, int start, int end,
        OrderByComparator<CCartItem> orderByComparator) {

        return cCartItemPersistence.findByCCartId(
            cCartId, start, end, orderByComparator);
    }

    @Override
    public int getCCartItemsCount(long cCartId) {
        return cCartItemPersistence.countByCCartId(cCartId);
    }

    @Override
    public CCartItem updateCCartItem(
            long cCartItemId, int quantity, String json)
        throws PortalException {

        CCartItem cCartItem = cCartItemPersistence.findByPrimaryKey(
            cCartItemId);

        cCartItem.setQuantity(quantity);
        cCartItem.setJson(json);

        cCartItemPersistence.update(cCartItem);

        return cCartItem;
    }

}