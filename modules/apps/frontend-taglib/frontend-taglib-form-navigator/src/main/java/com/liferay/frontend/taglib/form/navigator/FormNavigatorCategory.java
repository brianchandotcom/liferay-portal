/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.form.navigator;

import java.util.Locale;

/**
 * Provides an interface defining categories that will be used by a specific
 * <code>liferay-ui:form-navigator</code> tag instance to render a new category.
 * Form navigator categories includes form navigator entries, defined by {@link
 * FormNavigatorEntry} implementations.
 *
 * <p>
 * Implementations must be registered in the OSGi Registry. The order of the
 * form navigator categories is determined by the service ranking.
 * </p>
 *
 * @author Sergio González
 */
public interface FormNavigatorCategory {

	/**
	 * Returns the form navigator ID where the form navigator category will be
	 * included. This ID must match the ID attribute of the
	 * <code>liferay-ui:form-navigator</code> tag where this form navigator
	 * category is to be included.
	 *
	 * @return the form navigator ID where the form navigator category will be
	 *         included
	 */
	public String getFormNavigatorId();

	/**
	 * Returns the key for the form navigator category. This key needs to be
	 * unique in the scope of a form navigator ID.
	 *
	 * <p>
	 * This key will be referred by the form navigator entries to be added to
	 * this form navigator category.
	 * </p>
	 *
	 * @return the key of the form navigator category
	 */
	public String getKey();

	/**
	 * Defines the label that will be displayed in the user interface when the
	 * form navigator category is included in the form navigator.
	 *
	 * @param  locale the locale that the label should be retrieved for
	 * @return the label of the form navigator category
	 */
	public String getLabel(Locale locale);

}