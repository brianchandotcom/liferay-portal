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

package com.liferay.user.associated.data.web.internal.user.action.contributor;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.users.admin.user.action.contributor.UserActionContributor;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = UserActionContributor.class)
public class ErasePersonalDataUserActionContributor
	extends BaseUADUserActionContributor {

	@Override
	public String getConfirmationMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)),
			"the-user-must-be-deactivated-before-starting-the-data-erasure-" +
				"process.-are-you-sure-you-want-to-deactivate-the-user");
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse,
		User user, User selectedUser) {

		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			portletRequest, UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
			PortletRequest.ACTION_PHASE);

		liferayPortletURL.setParameter(
			ActionRequest.ACTION_NAME, "/erase_personal_data");
		liferayPortletURL.setParameter(
			"p_u_i_d", String.valueOf(selectedUser.getUserId()));

		return liferayPortletURL.toString();
	}

	@Override
	public boolean isShowConfirmationMessage(User selUser) {
		if (selUser.isActive()) {
			return true;
		}

		return false;
	}

	@Override
	protected String getKey() {
		return "delete-personal-data";
	}

	@Override
	protected String getMVCRenderCommandName() {
		return null;
	}

}