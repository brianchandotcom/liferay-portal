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

package com.liferay.analytics.reports.journal.internal.info.item;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(service = AnalyticsReportsInfoItem.class)
public class JournalArticleAnalyticsReportsInfoItem
	implements AnalyticsReportsInfoItem<JournalArticle> {

	@Override
	public String getAuthorName(JournalArticle journalArticle) {
		return _getUser(
			journalArticle
		).map(
			User::getFullName
		).orElse(
			StringPool.BLANK
		);
	}

	@Override
	public long getAuthorUserId(JournalArticle journalArticle) {
		return _getUser(
			journalArticle
		).map(
			User::getUserId
		).orElse(
			0L
		);
	}

	@Override
	public List<Locale> getAvailableLocales(JournalArticle journalArticle) {
		return Stream.of(
			journalArticle.getAvailableLanguageIds()
		).map(
			LocaleUtil::fromLanguageId
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public Locale getDefaultLocale(JournalArticle journalArticle) {
		return LocaleUtil.fromLanguageId(journalArticle.getDefaultLanguageId());
	}

	@Override
	public Date getPublishDate(JournalArticle journalArticle) {
		AssetDisplayPageEntry assetDisplayPageEntry =
			_assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				journalArticle.getGroupId(),
				_portal.getClassNameId(JournalArticle.class),
				journalArticle.getResourcePrimKey());

		Date date = _getJournalArticleFirstPublishLocalDate(journalArticle);

		if ((assetDisplayPageEntry == null) ||
			date.after(assetDisplayPageEntry.getModifiedDate())) {

			return date;
		}

		return assetDisplayPageEntry.getCreateDate();
	}

	@Override
	public String getTitle(JournalArticle journalArticle, Locale locale) {
		return journalArticle.getTitle(locale);
	}

	private Date _getJournalArticleFirstPublishLocalDate(
		JournalArticle journalArticle) {

		try {
			JournalArticle oldestJournalArticle =
				_journalArticleLocalService.getOldestArticle(
					journalArticle.getGroupId(), journalArticle.getArticleId());

			return oldestJournalArticle.getDisplayDate();
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return journalArticle.getDisplayDate();
		}
	}

	private Optional<User> _getUser(JournalArticle journalArticle) {
		return Optional.ofNullable(
			_journalArticleLocalService.fetchLatestArticle(
				journalArticle.getResourcePrimKey())
		).map(
			latestArticle -> _userLocalService.fetchUser(
				latestArticle.getUserId())
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleAnalyticsReportsInfoItem.class);

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}