/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.layout.display.page;

import com.liferay.depot.model.DepotEntry;
import com.liferay.friendly.url.info.item.provider.InfoItemFriendlyURLProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Locale;

/**
 * @author Marco Leo
 */
public class DepotEntryLayoutDisplayPageObjectProvider
	implements LayoutDisplayPageObjectProvider<DepotEntry> {

	public DepotEntryLayoutDisplayPageObjectProvider(
		DepotEntry depotEntry,
		InfoItemFriendlyURLProvider<DepotEntry> infoItemFriendlyURLProvider,
		Language language) {

		_depotEntry = depotEntry;
		_infoItemFriendlyURLProvider = infoItemFriendlyURLProvider;
		_language = language;
	}

	@Override
	public String getClassName() {
		return DepotEntry.class.getName();
	}

	@Override
	public long getClassNameId() {
		return PortalUtil.getClassNameId(DepotEntry.class.getName());
	}

	@Override
	public long getClassPK() {
		return _depotEntry.getDepotEntryId();
	}

	@Override
	public long getClassTypeId() {
		return 0;
	}

	@Override
	public String getDescription(Locale locale) {
		try {
			Group group = _depotEntry.getGroup();

			return group.getDescription(locale);
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	@Override
	public DepotEntry getDisplayObject() {
		return _depotEntry;
	}

	@Override
	public long getGroupId() {
		return _depotEntry.getGroupId();
	}

	@Override
	public String getKeywords(Locale locale) {
		return null;
	}

	@Override
	public String getTitle(Locale locale) {
		try {
			Group group = _depotEntry.getGroup();

			return group.getName(locale);
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	@Override
	public String getURLTitle(Locale locale) {
		return _infoItemFriendlyURLProvider.getFriendlyURL(
			_depotEntry, _language.getLanguageId(locale));
	}

	private final DepotEntry _depotEntry;
	private final InfoItemFriendlyURLProvider<DepotEntry>
		_infoItemFriendlyURLProvider;
	private final Language _language;

}