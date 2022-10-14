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

import com.liferay.analytics.message.sender.model.listener.BaseEntityModelListener;
import com.liferay.analytics.message.sender.model.listener.EntityModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rachael Koestartyo
 */
@Component(service = {EntityModelListener.class, ModelListener.class})
public class UserModelListener extends BaseEntityModelListener<User> {

	@Override
	public List<String> getAttributeNames(long companyId) {
		return getUserAttributeNames(companyId);
	}

	@Override
	protected User getModel(long id) throws Exception {
		return userLocalService.getUser(id);
	}

	@Override
	protected String getPrimaryKeyName() {
		return "userId";
	}

	@Override
	protected boolean isExcluded(User user) {
		return isUserExcluded(user);
	}

}