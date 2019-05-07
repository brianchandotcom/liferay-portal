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

import com.liferay.portal.kernel.model.User;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides an interface defining entries that will be used by a specific
 * <code>liferay-ui:form-navigator</code> tag instance to render a new section.
 * Form navigator entries are included within form navigator categories, defined
 * by {@link FormNavigatorCategory} implementations.
 *
 * <p>
 * Implementations must be registered in the OSGi Registry. The order of the
 * form navigator entries inside a category is determined by the service
 * ranking.
 * </p>
 *
 * @author Sergio González
 */
public interface FormNavigatorEntry<T> {

	/**
	 * Returns the category key where the form navigator entry will be included.
	 *
	 * @return the category key where the form navigator entry will be included
	 */
	public String getCategoryKey();

	/**
	 * Returns the form navigator ID where the form navigator entry will be
	 * included. This ID must match the ID attribute of the
	 * <code>liferay-ui:form-navigator</code> tag, where this form navigator
	 * entry is to be included.
	 *
	 * @return the form navigator ID where the form navigator entry will be
	 *         included
	 */
	public String getFormNavigatorId();

	/**
	 * Returns the key for the form navigator entry. This key needs to be unique
	 * in the scope of a category key and form navigator ID.
	 *
	 * @return the key of the form navigator entry
	 */
	public String getKey();

	/**
	 * Returns the label that will be displayed in the user interface when the
	 * form navigator entry is included in the form navigator.
	 *
	 * @param  locale the locale that the label should be retrieved for
	 * @return the label of the form navigator entry
	 */
	public String getLabel(Locale locale);

	/**
	 * Renders the HTML that needs to be displayed when the form navigator entry
	 * is displayed.
	 *
	 * @param  httpServletRequest the request with which the form navigator
	 *         entry is rendered
	 * @param  httpServletResponse the response with which the form navigator
	 *         entry is rendered
	 * @throws IOException if an IO exception occurs
	 */
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException;

	/**
	 * Returns <code>true</code> if the form navigator entry should be
	 * displayed.
	 *
	 * @param  user the user viewing the form navigator entry
	 * @param  formModelBean the bean edited by the form navigator, or
	 *         <code>null</code>
	 * @return <code>true</code> if the form navigator entry should be
	 *         displayed; <code>false</code> otherwise
	 */
	public boolean isVisible(User user, T formModelBean);

}