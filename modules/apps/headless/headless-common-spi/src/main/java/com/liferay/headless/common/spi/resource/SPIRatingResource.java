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

import static com.liferay.portal.vulcan.util.TransformUtil.transform;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;

/**
 * @author Javier Gamarra
 */
public class SPIRatingResource<T> {

	public SPIRatingResource(
		String className, RatingsEntryLocalService ratingsEntryLocalService,
		UnsafeFunction<RatingsEntry, T, Exception> transformUnsafeFunction,
		User user) {

		_className = className;
		_ratingsEntryLocalService = ratingsEntryLocalService;
		_transformUnsafeFunction = transformUnsafeFunction;
		_user = user;
	}

	public void deleteRating(Long ratingId) throws Exception {
		_checkPermission();
		_ratingsEntryLocalService.deleteRatingsEntry(ratingId);
	}

	public T getRating(Long ratingId) throws Exception {
		return _transformUnsafeFunction.apply(
			_ratingsEntryLocalService.getRatingsEntry(ratingId));
	}

	public Page<T> getRatingsPage(Long classPK) throws Exception {
		return Page.of(
			transform(
				_ratingsEntryLocalService.getEntries(_className, classPK),
				_transformUnsafeFunction));
	}

	public T postRating(Long messageBoardMessageId, Double ratingValue)
		throws Exception {

		_checkPermission();

		return _transformUnsafeFunction.apply(
			_ratingsEntryLocalService.updateEntry(
				_user.getUserId(), _className, messageBoardMessageId,
				ratingValue, new ServiceContext()));
	}

	public T putRating(Long ratingId, Double ratingValue) throws Exception {
		_checkPermission();

		RatingsEntry ratingsEntry = _ratingsEntryLocalService.getRatingsEntry(
			ratingId);

		return _transformUnsafeFunction.apply(
			_ratingsEntryLocalService.updateEntry(
				_user.getUserId(), ratingsEntry.getClassName(),
				ratingsEntry.getClassPK(), GetterUtil.getDouble(ratingValue),
				new ServiceContext()));
	}

	private void _checkPermission() throws Exception {
		if (_user.isDefaultUser()) {
			throw new PrincipalException();
		}
	}

	private final String _className;
	private final RatingsEntryLocalService _ratingsEntryLocalService;
	private final UnsafeFunction<RatingsEntry, T, Exception>
		_transformUnsafeFunction;
	private final User _user;

}