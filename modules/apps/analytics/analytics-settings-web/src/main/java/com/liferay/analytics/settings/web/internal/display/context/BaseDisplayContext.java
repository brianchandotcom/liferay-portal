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

package com.liferay.analytics.settings.web.internal.display.context;

import com.liferay.analytics.settings.web.internal.util.WizardModeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Riccardo Ferrari
 */
public abstract class BaseDisplayContext implements DisplayContext {

	public BaseDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		this.httpServletRequest = httpServletRequest;
		this.httpServletResponse = httpServletResponse;

		_httpSession = httpServletRequest.getSession();
	}

	public boolean isWizardMode() {
		return WizardModeUtil.isWizardMode(_httpSession);
	}

	protected void setWizardMode(boolean wizardMode) {
		WizardModeUtil.setWizardMode(_httpSession, wizardMode);
	}

	protected final HttpServletRequest httpServletRequest;
	protected final HttpServletResponse httpServletResponse;

	private final HttpSession _httpSession;

}