/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.saved.content.service.SavedContentEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	public void onBeforeRemove(User user) throws ModelListenerException {
		_savedContentEntryLocalService.deleteSavedContentEntriesByUserId(
			user.getUserId());
	}

	@Reference
	private SavedContentEntryLocalService _savedContentEntryLocalService;

}