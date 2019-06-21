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

package com.liferay.change.tracking.rest.internal.dto.factory.v1_0;

import com.liferay.change.tracking.rest.dto.v1_0.ProcessUser;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Máté Thurzó
 */
@Component(immediate = true, service = ProcessUserFactory.class)
public class ProcessUserFactory {

	public ProcessUser toProcessUser(User user) {
		return new ProcessUser() {
			{
				userId = user.getUserId();
				userInitials = user.getInitials();
				userName = user.getFullName();
				userPortraitURL = _getPortraitURL(user);
			}
		};
	}

	private String _getPortraitURL(User user) {
		Contact contact = user.fetchContact();

		if (contact == null) {
			return UserConstants.getPortraitURL(
				StringPool.BLANK, false, 0, StringPool.BLANK);
		}

		return UserConstants.getPortraitURL(
			StringPool.BLANK, contact.isMale(), user.getPortraitId(),
			user.getUserUuid());
	}

}