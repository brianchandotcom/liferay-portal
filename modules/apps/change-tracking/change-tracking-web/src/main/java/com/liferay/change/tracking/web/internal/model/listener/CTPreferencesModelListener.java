/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.model.listener;

import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.web.internal.helper.SandboxHelper;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = ModelListener.class)
public class CTPreferencesModelListener
	extends BaseModelListener<CTPreferences> {

	@Override
	public void onAfterCreate(CTPreferences ctPreferences)
		throws ModelListenerException {

		try {
			_sandboxHelper.sandbox(ctPreferences);
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Override
	public void onAfterUpdate(
			CTPreferences originalCTPreferences, CTPreferences ctPreferences)
		throws ModelListenerException {

		try {
			_sandboxHelper.sandbox(ctPreferences);
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Reference
	private SandboxHelper _sandboxHelper;

}