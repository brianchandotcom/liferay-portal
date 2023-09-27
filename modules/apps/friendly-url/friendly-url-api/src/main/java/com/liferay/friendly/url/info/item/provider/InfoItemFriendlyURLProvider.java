/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.info.item.provider;

import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public interface InfoItemFriendlyURLProvider<T> {

	public String getFriendlyURL(T t, String languageId);

	public List<FriendlyURLEntryLocalization> getFriendlyURLEntryLocalizations(
		T t, String languageId);

}