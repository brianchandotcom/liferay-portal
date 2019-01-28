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

package com.liferay.headless.foundation.impl.resource;

import com.liferay.headless.foundation.dto.ContentSpace;
import com.liferay.headless.foundation.resource.ContentSpaceResource;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.GroupService;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(immediate = true, service = ContentSpaceResource.class)
public class ContentSpaceResourceImpl implements ContentSpaceResource {

	@Override
	public ContentSpace getContentSpace(long id) {
		try {
			Group group = _groupService.getGroup(id);

			return new ContentSpace(group.getGroupId());
		}
		catch (NoSuchGroupException nsge) {
			throw new NotFoundException(
				"Content Space not found with id " + id, nsge);
		}
		catch (PrincipalException pe) {
			throw new NotAuthorizedException(pe.getMessage(), pe);
		}
		catch (PortalException pe) {
			throw new InternalServerErrorException(
				"Internal Server Error " + pe.getMessage(), pe);
		}
	}

	@Reference
	private GroupService _groupService;

}