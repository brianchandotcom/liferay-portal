/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.document.library.internal.info.item;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.type.WebImage;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yurena Cabrera
 */
@Component(service = AnalyticsReportsInfoItem.class)
public class FileEntryAnalyticsReportsInfoItem
	implements AnalyticsReportsInfoItem<FileEntry> {

	public List<Action> getActions() {
		return Arrays.asList(
			Action.HISTORICAL_VIEWS, Action.TOTAL_VIEWS,
			Action.TRAFFIC_CHANNELS);
	}

	@Override
	public String getAuthorName(FileEntry fileEntry) {
		User user = _getUser(fileEntry);

		if (user == null) {
			return StringPool.BLANK;
		}

		return user.getFullName();
	}

	@Override
	public long getAuthorUserId(FileEntry fileEntity) {
		User user = _getUser(fileEntity);

		if (user == null) {
			return 0L;
		}

		return user.getUserId();
	}

	@Override
	public WebImage getAuthorWebImage(FileEntry fileEntry, Locale locale) {
		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, FileEntry.class.getName());

		InfoItemFieldValues infoItemFieldValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(fileEntry);

		InfoFieldValue<Object> authorProfileImageInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("authorProfileImage");

		return (WebImage)authorProfileImageInfoFieldValue.getValue(locale);
	}

	@Override
	public List<Locale> getAvailableLocales(FileEntry fileEntry) {
		return _layoutDisplayPageObjectProviderAnalyticsReportsInfoItem.
			getAvailableLocales(_getLayoutDisplayPageObjectProvider(fileEntry));
	}

	@Override
	public String getCanonicalURL(FileEntry fileEntry, Locale locale) {
		return _layoutDisplayPageObjectProviderAnalyticsReportsInfoItem.
			getCanonicalURL(
				_getLayoutDisplayPageObjectProvider(fileEntry), locale);
	}

	@Override
	public Locale getDefaultLocale(FileEntry fileEntry) {
		return _layoutDisplayPageObjectProviderAnalyticsReportsInfoItem.
			getDefaultLocale(_getLayoutDisplayPageObjectProvider(fileEntry));
	}

	@Override
	public Date getPublishDate(FileEntry fileEntry) {
		return _layoutDisplayPageObjectProviderAnalyticsReportsInfoItem.
			getPublishDate(_getLayoutDisplayPageObjectProvider(fileEntry));
	}

	@Override
	public String getTitle(FileEntry fileEntry, Locale locale) {
		return _layoutDisplayPageObjectProviderAnalyticsReportsInfoItem.
			getTitle(_getLayoutDisplayPageObjectProvider(fileEntry), locale);
	}

	@Override
	public boolean isShow(FileEntry fileEntry) {
		return _layoutDisplayPageObjectProviderAnalyticsReportsInfoItem.isShow(
			_getLayoutDisplayPageObjectProvider(fileEntry));
	}

	private LayoutDisplayPageObjectProvider<FileEntry>
		_getLayoutDisplayPageObjectProvider(FileEntry fileEntry) {

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			_layoutDisplayPageProviderRegistry.
				getLayoutDisplayPageProviderByClassName(
					FileEntry.class.getName());

		if (layoutDisplayPageProvider == null) {
			return null;
		}

		return (LayoutDisplayPageObjectProvider<FileEntry>)
			layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				new InfoItemReference(
					FileEntry.class.getName(), fileEntry.getPrimaryKey()));
	}

	private User _getUser(FileEntry fileEntry) {
		return _userLocalService.fetchUser(fileEntry.getUserId());
	}

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference(
		target = "(model.class.name=com.liferay.layout.display.page.LayoutDisplayPageObjectProvider)"
	)
	private AnalyticsReportsInfoItem<LayoutDisplayPageObjectProvider>
		_layoutDisplayPageObjectProviderAnalyticsReportsInfoItem;

	@Reference
	private LayoutDisplayPageProviderRegistry
		_layoutDisplayPageProviderRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}