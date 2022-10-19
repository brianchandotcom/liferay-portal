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