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

package com.liferay.portal.vulcan.internal.jaxrs.param.converter.test;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.vulcan.content.space.ContentSpace;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ext.ParamConverter;

/**
 * @author Víctor Galán
 */
public class ContentSpaceParamConverter
	implements ParamConverter<ContentSpace> {

	public ContentSpaceParamConverter(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Override
	public ContentSpace fromString(String s) {
		try {
			Group group = _groupLocalService.getGroup(Long.valueOf(s));

			return new ContentSpace(group.getGroupId());
		}
		catch (Exception e) {
			throw new NotFoundException("No such group exist with id " + s, e);
		}
	}

	@Override
	public String toString(ContentSpace contentSpace) {
		return String.valueOf(contentSpace.getId());
	}

	private final GroupLocalService _groupLocalService;

}