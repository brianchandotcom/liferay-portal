/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.cache;

import com.liferay.fragment.cache.FragmentEntryLinkCache;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = FragmentEntryLinkCache.class)
public class FragmentEntryLinkCacheImpl implements FragmentEntryLinkCache {

	@Override
	public String getFragmentEntryLinkContent(
		FragmentEntryLink fragmentEntryLink, Locale locale) {

		return _portalCache.get(_getPortalCacheKey(fragmentEntryLink, locale));
	}

	@Override
	public void putFragmentEntryLinkContent(
		String content, FragmentEntryLink fragmentEntryLink, Locale locale) {

		_portalCache.put(
			_getPortalCacheKey(fragmentEntryLink, locale), content);
	}

	@Override
	public void removeFragmentEntryLinkCache(
		FragmentEntryLink fragmentEntryLink) {

		if (!CTCollectionThreadLocal.isProductionMode()) {
			return;
		}

		Set<Locale> availableLocales = _language.getAvailableLocales(
			fragmentEntryLink.getGroupId());

		for (Locale locale : availableLocales) {
			_portalCache.remove(_getPortalCacheKey(fragmentEntryLink, locale));
		}
	}

	@Override
	public void removeFragmentEntryLinkCache(long fragmentEntryLinkId) {
		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLinkId);

		if (fragmentEntryLink == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No fragment entry link exists with the fragment entry " +
						"link ID " + fragmentEntryLinkId);
			}

			return;
		}

		removeFragmentEntryLinkCache(fragmentEntryLink);
	}

	@Activate
	protected void activate() {
		_portalCache = (PortalCache<String, String>)_multiVMPool.getPortalCache(
			FragmentEntryLink.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		_multiVMPool.removePortalCache(FragmentEntryLink.class.getName());
	}

	private String _getPortalCacheKey(
		FragmentEntryLink fragmentEntryLink, Locale locale) {

		StringBundler sb = new StringBundler(5);

		sb.append(fragmentEntryLink.getFragmentEntryLinkId());
		sb.append(StringPool.DASH);
		sb.append(locale);
		sb.append(StringPool.DASH);
		sb.append(fragmentEntryLink.getSegmentsExperienceId());

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryLinkCacheImpl.class);

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private Language _language;

	@Reference
	private MultiVMPool _multiVMPool;

	private PortalCache<String, String> _portalCache;

}