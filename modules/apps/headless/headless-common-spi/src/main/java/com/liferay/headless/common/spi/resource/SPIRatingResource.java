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

package com.liferay.headless.common.spi.resource;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;

/**
 * @author Javier Gamarra
 */
public class SPIRatingResource<T> {

	public SPIRatingResource(
		String className, RatingsEntryLocalService ratingsEntryLocalService,
		UnsafeFunction<RatingsEntry, T, Exception> transformUnsafeFunction) {

		_className = className;
		_ratingsEntryLocalService = ratingsEntryLocalService;
		_transformUnsafeFunction = transformUnsafeFunction;
	}

	public T addOrUpdateRating(Number ratingValue, long classPK)
		throws Exception {

		User user = _getUser();

		_checkPermission(user);

		return _transformUnsafeFunction.apply(
			_ratingsEntryLocalService.updateEntry(
				user.getUserId(), _className, classPK,
				GetterUtil.getDouble(ratingValue), new ServiceContext()));
	}

	public void deleteRating(Long classPK) throws Exception {
		User user = _getUser();

		_checkPermission(user);

		_ratingsEntryLocalService.deleteEntry(
			user.getUserId(), _className, classPK);
	}

	public T getRating(Long classPK) throws Exception {
		User user = _getUser();

		return _transformUnsafeFunction.apply(
			_ratingsEntryLocalService.getEntry(
				user.getUserId(), _className, classPK));
	}

	private void _checkPermission(User user) throws Exception {
		if (user.isDefaultUser()) {
			throw new PrincipalException();
		}
	}

	private User _getUser() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return permissionChecker.getUser();
	}

	private final String _className;
	private final RatingsEntryLocalService _ratingsEntryLocalService;
	private final UnsafeFunction<RatingsEntry, T, Exception>
		_transformUnsafeFunction;

}