/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.document.library.internal.info.item;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.analytics.reports.layout.display.page.info.item.LayoutDisplayPageObjectProviderAnalyticsReportsInfoItem;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.type.WebImage;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
		return _getUser(
			fileEntry
		).map(
			User::getFullName
		).orElse(
			StringPool.BLANK
		);
	}

	@Override
	public long getAuthorUserId(FileEntry fileEntity) {
		return _getUser(
			fileEntity
		).map(
			User::getUserId
		).orElse(
			0L
		);
	}

	@Override
	public WebImage getAuthorWebImage(FileEntry fileEntry, Locale locale) {
		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
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
			_layoutDisplayPageProviderTracker.
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

	private Optional<User> _getUser(FileEntry fileEntry) {
		return Optional.ofNullable(
			_userLocalService.fetchUser(fileEntry.getUserId()));
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private LayoutDisplayPageObjectProviderAnalyticsReportsInfoItem
		_layoutDisplayPageObjectProviderAnalyticsReportsInfoItem;

	@Reference
	private LayoutDisplayPageProviderTracker _layoutDisplayPageProviderTracker;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}