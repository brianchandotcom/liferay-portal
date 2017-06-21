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

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.service.base.CPDefinitionLinkServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CPDefinitionPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionLinkServiceImpl extends CPDefinitionLinkServiceBaseImpl {

    @Override
    public CPDefinitionLink addCPDefinitionLink(
            long cpDefinitionId1, long cpDefinitionId2, int displayOrder,
            int type, ServiceContext serviceContext)
        throws PortalException {

        CPDefinitionPermission.check(
            getPermissionChecker(), cpDefinitionId1, ActionKeys.UPDATE);

        CPDefinitionPermission.check(
            getPermissionChecker(), cpDefinitionId2, ActionKeys.VIEW);

        return cpDefinitionLinkLocalService.addCPDefinitionLink(
            cpDefinitionId1, cpDefinitionId2, displayOrder, type,
            serviceContext);
    }

    @Override
    public CPDefinitionLink deleteCPDefinitionLink(
            CPDefinitionLink cpDefinitionLink) throws PortalException {

        CPDefinitionPermission.checkCPDefinitionLink(
            getPermissionChecker(), cpDefinitionLink, ActionKeys.UPDATE);

        return cpDefinitionLinkLocalService.deleteCPDefinitionLink(
            cpDefinitionLink);
    }

    @Override
    public CPDefinitionLink deleteCPDefinitionLink(long cpDefinitionLinkId)
        throws PortalException {

        CPDefinitionPermission.checkCPDefinitionLink(
            getPermissionChecker(), cpDefinitionLinkId, ActionKeys.UPDATE);

        return cpDefinitionLinkLocalService.deleteCPDefinitionLink(
            cpDefinitionLinkId);
    }
}