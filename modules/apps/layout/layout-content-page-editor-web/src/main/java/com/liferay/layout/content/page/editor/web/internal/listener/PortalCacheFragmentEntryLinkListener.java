/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.listener;

import com.liferay.fragment.listener.FragmentEntryLinkListener;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
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
		_clearCache(fragmentEntryLink);
	}

	@Override
	public void onDeleteFragmentEntryLink(FragmentEntryLink fragmentEntryLink) {
		_clearCache(fragmentEntryLink);
	}

	@Override
	public void onUpdateFragmentEntryLink(FragmentEntryLink fragmentEntryLink) {
		_clearCache(fragmentEntryLink);
	}

	@Override
	public void onUpdateFragmentEntryLinkConfigurationValues(
		FragmentEntryLink fragmentEntryLink) {

		_clearCache(fragmentEntryLink);
	}

	@Activate
	protected void activate() {
		_portalCache = (PortalCache<String, String>)_multiVMPool.getPortalCache(
			FragmentEntryLink.class.getName());
	}

	private void _clearCache(FragmentEntryLink fragmentEntryLink) {
		Set<Locale> availableLocales = _language.getAvailableLocales(
			fragmentEntryLink.getGroupId());

		for (Locale locale : availableLocales) {
			_portalCache.remove(
				StringBundler.concat(
					fragmentEntryLink.getFragmentEntryLinkId(), StringPool.DASH,
					locale, StringPool.DASH,
					fragmentEntryLink.getSegmentsExperienceId()));
		}
	}

	@Reference
	private Language _language;

	@Reference
	private MultiVMPool _multiVMPool;

	private PortalCache<String, String> _portalCache;

}