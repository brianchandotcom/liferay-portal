/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.segments.internal.cache.SegmentsEntryCacheUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onAfterUpdate(User originalUser, User newUser)
		throws ModelListenerException {

		SegmentsEntryCacheUtil.clear(originalUser.getUserId());
	}

	@Override
	public void onBeforeRemove(User user) throws ModelListenerException {
		SegmentsEntryCacheUtil.clear(user.getUserId());
	}

}