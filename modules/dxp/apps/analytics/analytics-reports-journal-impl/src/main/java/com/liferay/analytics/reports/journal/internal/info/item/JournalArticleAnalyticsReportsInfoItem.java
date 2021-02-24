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
import com.liferay.asset.display.page.util.AssetDisplayPageUtil;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.type.WebImage;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

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
	public WebImage getAuthorWebImage(
		JournalArticle journalArticle, Locale locale) {

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
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

	@Override
	public boolean isShow(JournalArticle journalArticle) {
		Layout layout = _getLayout(journalArticle);

		if (layout == null) {
			return false;
		}

		if (!layout.isTypeAssetDisplay()) {
			return false;
		}

		if (_isEmbeddedPersonalApplicationLayout(layout)) {
			return false;
		}

		try {
			if (!_hasEditPermission(
					journalArticle, layout,
					PermissionThreadLocal.getPermissionChecker())) {

				return false;
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return false;
		}

		return true;
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

	private Layout _getLayout(JournalArticle journalArticle) {
		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			_getLayoutDisplayPageObjectProvider(journalArticle);

		if ((layoutDisplayPageObjectProvider == null) ||
			(layoutDisplayPageObjectProvider.getDisplayObject() == null)) {

			return null;
		}

		try {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				AssetDisplayPageUtil.getAssetDisplayPageLayoutPageTemplateEntry(
					layoutDisplayPageObjectProvider.getGroupId(),
					layoutDisplayPageObjectProvider.getClassNameId(),
					layoutDisplayPageObjectProvider.getClassPK(),
					layoutDisplayPageObjectProvider.getClassTypeId());

			if (layoutPageTemplateEntry == null) {
				return null;
			}

			return _layoutLocalService.fetchLayout(
				layoutPageTemplateEntry.getPlid());
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return null;
	}

	private LayoutDisplayPageObjectProvider<?>
		_getLayoutDisplayPageObjectProvider(JournalArticle journalArticle) {

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			_layoutDisplayPageProviderTracker.
				getLayoutDisplayPageProviderByClassName(
					JournalArticle.class.getName());

		if (layoutDisplayPageProvider == null) {
			return null;
		}

		return layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
			new InfoItemReference(
				JournalArticle.class.getName(),
				journalArticle.getResourcePrimKey()));
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

	private boolean _hasEditPermission(
			JournalArticle journalArticle, Layout layout,
			PermissionChecker permissionChecker)
		throws PortalException {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				JournalArticle.class.getName());

		AssetRenderer<?> assetRenderer = null;

		if (assetRendererFactory != null) {
			assetRenderer = assetRendererFactory.getAssetRenderer(
				journalArticle.getResourcePrimKey());
		}

		if (((assetRenderer == null) ||
			 !assetRenderer.hasEditPermission(permissionChecker)) &&
			!LayoutPermissionUtil.contains(
				permissionChecker, layout, ActionKeys.UPDATE)) {

			return false;
		}

		return true;
	}

	private boolean _isEmbeddedPersonalApplicationLayout(Layout layout) {
		if (layout.isTypeControlPanel()) {
			return false;
		}

		String layoutFriendlyURL = layout.getFriendlyURL();

		if (layout.isSystem() &&
			layoutFriendlyURL.equals(
				PropsUtil.get(PropsKeys.CONTROL_PANEL_LAYOUT_FRIENDLY_URL))) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleAnalyticsReportsInfoItem.class);

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private LayoutDisplayPageProviderTracker _layoutDisplayPageProviderTracker;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}