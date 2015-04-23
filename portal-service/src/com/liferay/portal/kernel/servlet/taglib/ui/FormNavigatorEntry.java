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

package com.liferay.portal.kernel.servlet.taglib.ui;

import com.liferay.portal.model.User;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides an interface defining entries that will be used by a specific
 * <code>liferay-ui:form-navigator</code> taglib instance to render a new
 * section. Form navigator entries are included within form navigator
 * categories, defined by {@link FormNavigatorCategory} implementations.
 *
 * <p>
 * Implementations must be registered in the OSGI Registry. The order of the
 * form navigator entries inside a category is determined by the service
 * ranking.
 * </p>
 *
 * @author Sergio González
 */
public interface FormNavigatorEntry<T> {

	/**
	 * Defines the category key where the form navigator entry will be included.
	 *
	 * @return the category key where the form navigator entry will be included.
	 */
	public String getCategoryKey();

	/**
	 * Defines the form navigator ID where the form navigator entry will be
	 * included. This ID must match the ID attribute of the
	 * <code>liferay-ui:form-navigator</code> taglib where this form navigator
	 * entry is to be included.
	 *
	 * @return the form navigator ID where the form navigator entry will be
	 *         included.
	 */
	public String getFormNavigatorId();

	/**
	 * Defines a key for the form navigator entry. This key needs to be unique
	 * in the scope of a category key and form navigator ID.
	 *
	 * @return the key of the form navigator entry.
	 */
	public String getKey();

	/**
	 * Defines the label that will be displayed in the user interface when the
	 * form navigator entry is included in the form navigator.
	 *
	 * @param  locale the locale that the label should be retrieved for
	 * @return the label of the form navigator entry.
	 */
	public String getLabel(Locale locale);

	/**
	 * Defines the label that will be displayed in the user interface when the
	 * form navigator entry is included in the form navigator.
	 *
	 * @param  user the user viewing the form navigator entry
	 * @param  formModelBean the bean edited by the form navigator
	 * @return whether the form navigator entry should be displayed or not.
	 */
	public boolean isVisible(User user, T formModelBean);

	/**
	 * Renders the HTML that needs to be displayed when the form navigator entry
	 * is displayed.
	 *
	 * @param  request the request with which the form navigator entry is
	 *         rendered
	 * @param  response the response with which the form navigator entry is
	 *         rendered
	 * @throws IOException
	 */
	public void render(HttpServletRequest request, HttpServletResponse response)
		throws IOException;

}