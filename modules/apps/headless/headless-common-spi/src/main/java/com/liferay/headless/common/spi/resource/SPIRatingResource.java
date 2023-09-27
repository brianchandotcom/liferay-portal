/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.common.spi.resource;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
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
		UnsafeFunction<RatingsEntry, T, Exception> transformUnsafeFunction,
		User user) {

		_className = className;
		_ratingsEntryLocalService = ratingsEntryLocalService;
		_transformUnsafeFunction = transformUnsafeFunction;
		_user = user;
	}

	public T addOrUpdateRating(Number ratingValue, long classPK)
		throws Exception {

		_checkPermission();

		return _transformUnsafeFunction.apply(
			_ratingsEntryLocalService.updateEntry(
				_user.getUserId(), _className, classPK,
				GetterUtil.getDouble(ratingValue), new ServiceContext()));
	}

	public void deleteRating(Long classPK) throws Exception {
		_checkPermission();

		getRating(classPK);

		_ratingsEntryLocalService.deleteEntry(
			_user.getUserId(), _className, classPK);
	}

	public T getRating(Long classPK) throws Exception {
		return _transformUnsafeFunction.apply(
			_ratingsEntryLocalService.getEntry(
				_user.getUserId(), _className, classPK));
	}

	private void _checkPermission() throws Exception {
		if (_user.isGuestUser()) {
			throw new PrincipalException();
		}
	}

	private final String _className;
	private final RatingsEntryLocalService _ratingsEntryLocalService;
	private final UnsafeFunction<RatingsEntry, T, Exception>
		_transformUnsafeFunction;
	private final User _user;

}