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

package com.liferay.friendly.url.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.friendly.url.exception.DuplicateFriendlyURLException;
import com.liferay.friendly.url.exception.FriendlyURLLengthException;
import com.liferay.friendly.url.model.FriendlyURL;
import com.liferay.friendly.url.service.base.FriendlyURLLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ModelHintsUtil;

/**
 * @author Adolfo Pérez
 */
@ProviderType
public class FriendlyURLLocalServiceImpl
	extends FriendlyURLLocalServiceBaseImpl {

	@Override
	public void validate(
			long companyId, long groupId, Class<?> clazz, String urlTitle)
		throws PortalException {

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURL.class.getName(), "urlTitle");

		if (urlTitle.length() > maxLength) {
			throw new FriendlyURLLengthException();
		}

		long classNameId = classNameLocalService.getClassNameId(clazz);

		int count = friendlyURLPersistence.countByC_G_C_U(
			companyId, groupId, classNameId, urlTitle);

		if (count > 0) {
			throw new DuplicateFriendlyURLException();
		}
	}

}