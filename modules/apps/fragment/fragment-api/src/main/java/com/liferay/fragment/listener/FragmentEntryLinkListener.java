/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.listener;

import com.liferay.fragment.model.FragmentEntryLink;

/**
 * @author Eudaldo Alonso
 */
public interface FragmentEntryLinkListener {

	public void onAddFragmentEntryLink(FragmentEntryLink fragmentEntryLink);

	public default void onCopyFragmentEntryLink(
		FragmentEntryLink fragmentEntryLink,
		FragmentEntryLink originalFragmentEntryLink) {

		onAddFragmentEntryLink(fragmentEntryLink);
	}

	public void onDeleteFragmentEntryLink(FragmentEntryLink fragmentEntryLink);

	public default void onDuplicateFragmentEntryLink(
		FragmentEntryLink fragmentEntryLink,
		FragmentEntryLink originalFragmentEntryLink) {

		onAddFragmentEntryLink(fragmentEntryLink);
	}

	public void onUpdateFragmentEntryLink(FragmentEntryLink fragmentEntryLink);

	public void onUpdateFragmentEntryLinkConfigurationValues(
		FragmentEntryLink fragmentEntryLink);

}