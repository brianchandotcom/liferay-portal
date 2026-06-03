/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.style.book.constants.StyleBookActionKeys;
import com.liferay.style.book.constants.StyleBookConstants;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.model.StyleBookTokenSet;
import com.liferay.style.book.service.StyleBookEntryLocalService;
import com.liferay.style.book.service.base.StyleBookTokenSetServiceBaseImpl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Lima
 */
@Component(
	property = {
		"json.web.service.context.name=stylebook",
		"json.web.service.context.path=StyleBookTokenSet"
	},
	service = AopService.class
)
public class StyleBookTokenSetServiceImpl
	extends StyleBookTokenSetServiceBaseImpl {

	@Override
	public StyleBookTokenSet addStyleBookTokenSet(
			String externalReferenceCode, String description,
			String frontendTokenCategoryName, String name,
			long styleBookEntryId, String themeId)
		throws PortalException {

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.getStyleBookEntry(styleBookEntryId);

		_portletResourcePermission.check(
			getPermissionChecker(), styleBookEntry.getGroupId(),
			StyleBookActionKeys.MANAGE_STYLE_BOOK_ENTRIES);

		return styleBookTokenSetLocalService.addStyleBookTokenSet(
			externalReferenceCode, getUserId(), description,
			frontendTokenCategoryName, name, styleBookEntryId, themeId);
	}

	@Reference(
		target = "(resource.name=" + StyleBookConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private StyleBookEntryLocalService _styleBookEntryLocalService;

}