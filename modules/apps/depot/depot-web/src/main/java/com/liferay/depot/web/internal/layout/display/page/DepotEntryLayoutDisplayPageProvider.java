/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.layout.display.page;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.friendly.url.info.item.provider.InfoItemFriendlyURLProvider;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.BaseLayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.util.GetterUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = LayoutDisplayPageProvider.class)
public class DepotEntryLayoutDisplayPageProvider
	extends BaseLayoutDisplayPageProvider<DepotEntry> {

	@Override
	public String getClassName() {
		return DepotEntry.class.getName();
	}

	@Override
	public String getDefaultURLSeparator() {
		return FriendlyURLResolverConstants.URL_SEPARATOR_ASSET_LIBRARY;
	}

	@Override
	public LayoutDisplayPageObjectProvider<DepotEntry>
		getLayoutDisplayPageObjectProvider(DepotEntry depotEntry) {

		if (depotEntry == null) {
			return null;
		}

		return new DepotEntryLayoutDisplayPageObjectProvider(
			depotEntry, _infoItemFriendlyURLProvider, _language);
	}

	@Override
	public LayoutDisplayPageObjectProvider<DepotEntry>
		getLayoutDisplayPageObjectProvider(long groupId, String urlTitle) {

		DepotEntry depotEntry = _depotEntryLocalService.fetchDepotEntry(
			GetterUtil.getLong(urlTitle));

		return new DepotEntryLayoutDisplayPageObjectProvider(
			depotEntry, _infoItemFriendlyURLProvider, _language);
	}

	@Override
	protected LayoutDisplayPageObjectProvider<DepotEntry>
		doGetLayoutDisplayPageObjectProvider(
			long groupId, InfoItemReference infoItemReference) {

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
			return null;
		}

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		DepotEntry depotEntry = _fetchDepotEntry(
			classPKInfoItemIdentifier.getClassPK());

		if (depotEntry == null) {
			return null;
		}

		return new DepotEntryLayoutDisplayPageObjectProvider(
			depotEntry, _infoItemFriendlyURLProvider, _language);
	}

	private DepotEntry _fetchDepotEntry(long classPK) {
		DepotEntry depotEntry = _depotEntryLocalService.fetchDepotEntry(
			classPK);

		if (depotEntry != null) {
			return depotEntry;
		}

		return _depotEntryLocalService.fetchGroupDepotEntry(classPK);
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference(target = "(item.class.name=com.liferay.depot.model.DepotEntry)")
	private InfoItemFriendlyURLProvider<DepotEntry>
		_infoItemFriendlyURLProvider;

	@Reference
	private Language _language;

}