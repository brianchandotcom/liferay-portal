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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;

import java.util.function.Supplier;

/**
 * @author Javier Gamarra
 */
public class SPIRatingResource<T> {

	public SPIRatingResource(
		String className, RatingsEntryLocalService ratingsEntryLocalService,
		UnsafeFunction<RatingsEntry, T, Exception> transformUnsafeFunction,
		Supplier<User> userSupplier) {

		_className = className;
		_ratingsEntryLocalService = ratingsEntryLocalService;
		_transformUnsafeFunction = transformUnsafeFunction;
		_userSupplier = userSupplier;
	}

	public T addOrUpdateRating(Number ratingValue, long classPK)
		throws Exception {

		_checkPermission();

		User user = _userSupplier.get();

		return _transformUnsafeFunction.apply(
			_ratingsEntryLocalService.updateEntry(
				user.getUserId(), _className, classPK,
				GetterUtil.getDouble(ratingValue), new ServiceContext()));
	}

	public void deleteRating(Long classPK) throws Exception {
		_checkPermission();

		User user = _userSupplier.get();

		_ratingsEntryLocalService.deleteEntry(
			user.getUserId(), _className, classPK);
	}

	public T getRating(Long classPK) throws Exception {
		User user = _userSupplier.get();

		return _transformUnsafeFunction.apply(
			_ratingsEntryLocalService.getEntry(
				user.getUserId(), _className, classPK));
	}

	private void _checkPermission() throws Exception {
		User user = _userSupplier.get();

		if (user.isDefaultUser()) {
			throw new PrincipalException();
		}
	}

	private final String _className;
	private final RatingsEntryLocalService _ratingsEntryLocalService;
	private final UnsafeFunction<RatingsEntry, T, Exception>
		_transformUnsafeFunction;
	private final Supplier<User> _userSupplier;

}