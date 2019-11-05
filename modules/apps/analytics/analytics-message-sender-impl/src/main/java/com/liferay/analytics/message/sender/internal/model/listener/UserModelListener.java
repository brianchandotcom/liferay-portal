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

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;

import java.util.ArrayList;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rachael Koestartyo
 */
@Component(immediate = true, service = ModelListener.class)
public class UserModelListener extends BaseEntityModelListener<User> {

	@Override
	public String getObjectType() {
		return "user";
	}

	@Override
	public void onAfterCreate(User user) throws ModelListenerException {
		send("add", user);
	}

	@Override
	public void onBeforeRemove(User user) throws ModelListenerException {
		send("delete", user);
	}

	@Override
	public void onBeforeUpdate(User newUser) throws ModelListenerException {
		try {
			User oldUser = userLocalService.getUser(newUser.getUserId());

			if (_equals(newUser, oldUser)) {
				return;
			}

			send("update", newUser);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	private boolean _equals(User newUser, User oldUser) {
		if (newUser.isPasswordModified()) {
			return false;
		}

		Set<String> modifiedAttributes = getModifiedAttributes(
			new ArrayList<String>() {
				{
					add("active");
					add("agreedToTermsOfUse");
					add("comments");
					add("emailAddress");
					add("languageId");
					add("reminderQueryAnswer");
					add("reminderQueryQuestion");
					add("screenName");
					add("timeZoneId");
				}
			},
			newUser, oldUser);

		if (!modifiedAttributes.isEmpty()) {
			return false;
		}

		return true;
	}

}