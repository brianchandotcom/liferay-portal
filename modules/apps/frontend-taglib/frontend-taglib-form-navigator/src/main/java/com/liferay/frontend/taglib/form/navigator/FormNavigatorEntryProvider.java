/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.form.navigator;

import com.liferay.portal.kernel.model.User;

import java.util.List;
import java.util.Locale;

/**
 * @author Sergio González
 */
public interface FormNavigatorEntryProvider {

	public <T> List<FormNavigatorEntry<T>> getFormNavigatorEntries(
		String formNavigatorId, String categoryKey, User user, T formModelBean);

	public <T> List<FormNavigatorEntry<T>> getFormNavigatorEntries(
		String formNavigatorId, User user, T formModelBean);

	public <T> String[] getKeys(
		String formNavigatorId, String categoryKey, User user, T formModelBean);

	public <T> String[] getLabels(
		String formNavigatorId, String categoryKey, User user, T formModelBean,
		Locale locale);

}