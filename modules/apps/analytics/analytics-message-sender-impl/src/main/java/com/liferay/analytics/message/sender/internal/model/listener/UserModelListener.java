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

import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rachael Koestartyo
 */
@Component(immediate = true, service = ModelListener.class)
public class UserModelListener extends BaseEntityModelListener<User> {

	@Override
	protected List<String> getAttributes() {
		return _attributes;
	}

	@Override
	protected User getOriginalModel(User user) throws Exception {
		return userLocalService.getUser(user.getUserId());
	}

	@Override
	protected String getPrimaryKeyName() {
		return "userId";
	}

	private static final List<String> _attributes = Arrays.asList(
		"active", "agreedToTermsOfUse", "comments", "emailAddress",
		"languageId", "reminderQueryAnswer", "reminderQueryQuestion",
		"screenName", "timeZoneId");

}