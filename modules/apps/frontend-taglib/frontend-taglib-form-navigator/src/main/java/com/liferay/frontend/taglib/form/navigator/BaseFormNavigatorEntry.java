/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.form.navigator;

import com.liferay.portal.kernel.model.User;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sergio González
 */
public abstract class BaseFormNavigatorEntry<T>
	implements FormNavigatorEntry<T> {

	@Override
	public abstract String getCategoryKey();

	@Override
	public abstract String getFormNavigatorId();

	@Override
	public abstract String getKey();

	@Override
	public abstract String getLabel(Locale locale);

	public abstract ServletContext getServletContext();

	@Override
	public abstract void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException;

	@Override
	public boolean isVisible(User user, T formModelBean) {
		return true;
	}

}