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

package com.liferay.commerce.user.service.impl;

import com.liferay.commerce.user.service.base.CommerceUserServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.model.impl.AddressImpl;
import com.liferay.portal.model.impl.EmailAddressImpl;

import java.util.List;

/**
 * @author Marco Leo
 */
public class CommerceUserServiceImpl extends CommerceUserServiceBaseImpl {

    public Address getOrganizationPrimaryAddress(long organizationId)
        throws PortalException {

        Organization organization = organizationLocalService.getOrganization(
                organizationId);

        OrganizationPermissionUtil.check(
            getPermissionChecker(), organization, ActionKeys.VIEW);

        List<Address> addresses = organization.getAddresses();

        for(Address address : addresses){
            if(address.isPrimary()){
                return address;
            }
        }

        return new AddressImpl();
    }

    public EmailAddress getOrganizationPrimaryEmailAddress(long organizationId)
            throws PortalException {

        Organization organization = organizationLocalService.getOrganization(
                organizationId);

        OrganizationPermissionUtil.check(
                getPermissionChecker(), organization, ActionKeys.VIEW);

        List<EmailAddress> emailAddresses =
            emailAddressService.getEmailAddresses(
                    Organization.class.getName(), organizationId);

        for(EmailAddress emailAddress : emailAddresses){
            if(emailAddress.isPrimary()){
                return emailAddress;
            }
        }

        return new EmailAddressImpl();
    }

}