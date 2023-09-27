/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.form.navigator;

import java.util.List;
import java.util.Locale;

/**
 * @author Sergio González
 */
public interface FormNavigatorCategoryProvider {

	public List<FormNavigatorCategory> getFormNavigatorCategories(
		String formNavigatorId);

	public String[] getKeys(String formNavigatorId);

	public String[] getLabels(String formNavigatorId, Locale locale);

}