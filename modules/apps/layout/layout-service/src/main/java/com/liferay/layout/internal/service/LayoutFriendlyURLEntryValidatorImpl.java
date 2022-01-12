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

package com.liferay.layout.internal.service;

import com.liferay.friendly.url.exception.DuplicateFriendlyURLEntryException;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.layout.friendly.url.LayoutFriendlyURLEntryHelper;
import com.liferay.portal.kernel.exception.LayoutFriendlyURLException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutFriendlyURLEntryValidator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noor Najjar
 * @author Roberto Díaz
 */
@Component(service = LayoutFriendlyURLEntryValidator.class)
public class LayoutFriendlyURLEntryValidatorImpl
	implements LayoutFriendlyURLEntryValidator {

	@Override
	public void validateFriendlyURLEntry(
			long groupId, boolean privateLayout, long classPK, String urlTitle)
		throws PortalException {

		try {
			_friendlyURLEntryLocalService.validate(
				groupId,
				_layoutFriendlyURLEntryHelper.getClassNameId(privateLayout),
				classPK, urlTitle);
		}
		catch (DuplicateFriendlyURLEntryException
					duplicateFriendlyURLEntryException) {

			LayoutFriendlyURLException layoutFriendlyURLException =
				new LayoutFriendlyURLException(
					LayoutFriendlyURLException.DUPLICATE);

			layoutFriendlyURLException.setDuplicateClassPK(classPK);
			layoutFriendlyURLException.setDuplicateClassName(
				Layout.class.getName());

			throw layoutFriendlyURLException;
		}
	}

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private LayoutFriendlyURLEntryHelper _layoutFriendlyURLEntryHelper;

}