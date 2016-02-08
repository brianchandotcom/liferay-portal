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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserIdMapper;
import com.liferay.portal.service.base.UserIdMapperLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class UserIdMapperLocalServiceImpl
	extends UserIdMapperLocalServiceBaseImpl {

	@Override
	public void deleteUserIdMappers(long userId) {
		userIdMapperPersistence.removeByUserId(userId);
	}

	@Override
	public UserIdMapper getUserIdMapper(long userId, String type)
		throws PortalException {

		return userIdMapperPersistence.findByU_T(userId, type);
	}

	@Override
	public UserIdMapper getUserIdMapperByExternalUserId(
			String type, String externalUserId)
		throws PortalException {

		return userIdMapperPersistence.findByT_E(type, externalUserId);
	}

	@Override
	public List<UserIdMapper> getUserIdMappers(long userId) {
		return userIdMapperPersistence.findByUserId(userId);
	}

	@Override
	public UserIdMapper updateUserIdMapper(
		long userId, String type, String description, String externalUserId) {

		UserIdMapper userIdMapper = userIdMapperPersistence.fetchByU_T(
			userId, type);

		if (userIdMapper == null) {
			long userIdMapperId = counterLocalService.increment();

			userIdMapper = userIdMapperPersistence.create(userIdMapperId);
		}

		userIdMapper.setUserId(userId);
		userIdMapper.setType(type);
		userIdMapper.setDescription(description);
		userIdMapper.setExternalUserId(externalUserId);

		userIdMapperPersistence.update(userIdMapper);

		return userIdMapper;
	}

}