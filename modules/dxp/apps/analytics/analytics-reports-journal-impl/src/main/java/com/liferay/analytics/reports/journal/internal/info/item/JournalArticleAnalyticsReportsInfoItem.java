/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.journal.internal.info.item;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.type.WebImage;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(service = AnalyticsReportsInfoItem.class)
public class JournalArticleAnalyticsReportsInfoItem
	implements AnalyticsReportsInfoItem<JournalArticle> {

	public List<Action> getActions() {
		return Arrays.asList(
			Action.HISTORICAL_READS, Action.HISTORICAL_VIEWS,
			Action.TOTAL_READS, Action.TOTAL_VIEWS, Action.TRAFFIC_CHANNELS);
	}

	@Override
	public String getAuthorName(JournalArticle journalArticle) {
		User user = _getUser(journalArticle);

		if (user == null) {
			return StringPool.BLANK;
		}

		return user.getFullName();
	}

	@Override
	public long getAuthorUserId(JournalArticle journalArticle) {
		User user = _getUser(journalArticle);

		if (user == null) {
			return 0L;
		}

		return user.getUserId();
	}

	@Override
	public WebImage getAuthorWebImage(
		JournalArticle journalArticle, Locale locale) {

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class,
				JournalArticle.class.getName());

		InfoItemFieldValues infoItemFieldValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(journalArticle);

		InfoFieldValue<Object> authorProfileImageInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("authorProfileImage");

		return (WebImage)authorProfileImageInfoFieldValue.getValue(locale);
	}

	@Override
	public List<Locale> getAvailableLocales(JournalArticle journalArticle) {
		return _layoutDisplayPageObjectProviderAnalyticsReportsInfoItem.
			getAvailableLocales(
				_getLayoutDisplayPageObjectProvider(journalArticle));
	}

	@Override
	public String getCanonicalURL(
		JournalArticle journalArticle, Locale locale) {

		return _layoutDisplayPageObjectProviderAnalyticsReportsInfoItem.
			getCanonicalURL(
				_getLayoutDisplayPageObjectProvider(journalArticle), locale);
	}

	@Override
	public Locale getDefaultLocale(JournalArticle journalArticle) {
		return _layoutDisplayPageObjectProviderAnalyticsReportsInfoItem.
			getDefaultLocale(
				_getLayoutDisplayPageObjectProvider(journalArticle));
	}

	@Override
	public Date getPublishDate(JournalArticle journalArticle) {
		return _layoutDisplayPageObjectProviderAnalyticsReportsInfoItem.
			getPublishDate(_getLayoutDisplayPageObjectProvider(journalArticle));
	}

	@Override
	public String getTitle(JournalArticle journalArticle, Locale locale) {
		return _layoutDisplayPageObjectProviderAnalyticsReportsInfoItem.
			getTitle(
				_getLayoutDisplayPageObjectProvider(journalArticle), locale);
	}

	@Override
	public boolean isShow(JournalArticle journalArticle) {
		return _layoutDisplayPageObjectProviderAnalyticsReportsInfoItem.isShow(
			_getLayoutDisplayPageObjectProvider(journalArticle));
	}

	private LayoutDisplayPageObjectProvider<JournalArticle>
		_getLayoutDisplayPageObjectProvider(JournalArticle journalArticle) {

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			_layoutDisplayPageProviderRegistry.
				getLayoutDisplayPageProviderByClassName(
					JournalArticle.class.getName());

		if (layoutDisplayPageProvider == null) {
			return null;
		}

		return (LayoutDisplayPageObjectProvider<JournalArticle>)
			layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				new InfoItemReference(
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey()));
	}

	private User _getUser(JournalArticle journalArticle) {
		JournalArticle latestArticle =
			_journalArticleLocalService.fetchLatestArticle(
				journalArticle.getResourcePrimKey());

		if (latestArticle == null) {
			return null;
		}

		return _userLocalService.fetchUser(latestArticle.getUserId());
	}

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.layout.display.page.LayoutDisplayPageObjectProvider)"
	)
	private AnalyticsReportsInfoItem<LayoutDisplayPageObjectProvider>
		_layoutDisplayPageObjectProviderAnalyticsReportsInfoItem;

	@Reference
	private LayoutDisplayPageProviderRegistry
		_layoutDisplayPageProviderRegistry;

	@Reference
	private UserLocalService _userLocalService;

}