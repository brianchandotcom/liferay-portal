/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.internal.model.listener;

import com.liferay.cookies.service.ConsentPreferenceService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lucas Miranda
 */
@Component(service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	public void onBeforeRemove(User user) throws ModelListenerException {
		_consentPreferenceService.deleteConsentPreferences(user.getUserId());
	}

	@Reference
	private ConsentPreferenceService _consentPreferenceService;

}