/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.RequiredUserException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.util.PortalInstances;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onBeforeRemove(User user) throws ModelListenerException {
		if (PortalInstances.isCurrentCompanyInDeletionProcess()) {
			return;
		}

		if (user.getType() == UserConstants.TYPE_DEFAULT_SERVICE_ACCOUNT) {
			throw new ModelListenerException(
				new RequiredUserException(
					"Default service account cannot be removed"));
		}
	}

	@Override
	public void onBeforeUpdate(User originalUser, User user)
		throws ModelListenerException {

		if (originalUser.getType() != user.getType()) {
			throw new ModelListenerException("User's type cannot be changed");
		}

		if ((originalUser.getType() ==
				UserConstants.TYPE_DEFAULT_SERVICE_ACCOUNT) &&
			(originalUser.getScreenName() != user.getScreenName())) {

			throw new ModelListenerException(
				new RequiredUserException(
					"Screen name for default service account cannot be " +
						"changed"));
		}
	}

}