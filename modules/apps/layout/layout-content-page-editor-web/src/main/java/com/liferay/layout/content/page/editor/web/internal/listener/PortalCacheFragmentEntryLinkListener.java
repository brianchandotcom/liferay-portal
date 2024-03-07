/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.listener;

import com.liferay.fragment.cache.FragmentEntryLinkCache;
import com.liferay.fragment.listener.FragmentEntryLinkListener;
import com.liferay.fragment.model.FragmentEntryLink;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(service = FragmentEntryLinkListener.class)
public class PortalCacheFragmentEntryLinkListener
	implements FragmentEntryLinkListener {

	@Override
	public void onAddFragmentEntryLink(FragmentEntryLink fragmentEntryLink) {
		_fragmentEntryLinkCache.removeFragmentEntryLinkCache(fragmentEntryLink);
	}

	@Override
	public void onDeleteFragmentEntryLink(FragmentEntryLink fragmentEntryLink) {
		_fragmentEntryLinkCache.removeFragmentEntryLinkCache(fragmentEntryLink);
	}

	@Override
	public void onUpdateFragmentEntryLink(FragmentEntryLink fragmentEntryLink) {
		_fragmentEntryLinkCache.removeFragmentEntryLinkCache(fragmentEntryLink);
	}

	@Override
	public void onUpdateFragmentEntryLinkConfigurationValues(
		FragmentEntryLink fragmentEntryLink) {

		_fragmentEntryLinkCache.removeFragmentEntryLinkCache(fragmentEntryLink);
	}

	@Reference
	private FragmentEntryLinkCache _fragmentEntryLinkCache;

}