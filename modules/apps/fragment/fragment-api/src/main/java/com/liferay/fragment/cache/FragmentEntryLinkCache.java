/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.cache;

import com.liferay.fragment.model.FragmentEntryLink;

import java.util.Locale;

/**
 * @author Eudaldo Alonso
 */
public interface FragmentEntryLinkCache {

	public String getFragmentEntryLinkContent(
		FragmentEntryLink fragmentEntryLink, Locale locale);

	public void putFragmentEntryLinkContent(
		String content, FragmentEntryLink fragmentEntryLink, Locale locale);

	public void removeFragmentEntryLinkCache(
		FragmentEntryLink fragmentEntryLink);

	public void removeFragmentEntryLinkCache(long fragmentEntryLinkId);

}