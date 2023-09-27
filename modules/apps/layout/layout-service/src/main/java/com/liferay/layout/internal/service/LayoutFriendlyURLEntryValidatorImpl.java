/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.service;

import com.liferay.friendly.url.exception.DuplicateFriendlyURLEntryException;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.layout.friendly.url.LayoutFriendlyURLEntryHelper;
import com.liferay.portal.kernel.exception.LayoutFriendlyURLException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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

			if (_log.isDebugEnabled()) {
				_log.debug(duplicateFriendlyURLEntryException);
			}

			LayoutFriendlyURLException layoutFriendlyURLException =
				new LayoutFriendlyURLException(
					LayoutFriendlyURLException.DUPLICATE);

			layoutFriendlyURLException.setDuplicateClassPK(classPK);
			layoutFriendlyURLException.setDuplicateClassName(
				Layout.class.getName());

			throw layoutFriendlyURLException;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutFriendlyURLEntryValidatorImpl.class);

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private LayoutFriendlyURLEntryHelper _layoutFriendlyURLEntryHelper;

}