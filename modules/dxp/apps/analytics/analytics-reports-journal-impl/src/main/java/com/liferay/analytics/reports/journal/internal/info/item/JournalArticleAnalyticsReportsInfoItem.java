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
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.layout.seo.kernel.LayoutSEOLink;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
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

import javax.servlet.http.HttpServletRequest;

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
	public String getCanonicalURL(
		JournalArticle journalArticle, Locale locale) {

		Optional<ThemeDisplay> themeDisplayOptional =
			_getThemeDisplayOptional();

		if (!themeDisplayOptional.isPresent()) {
			return StringPool.BLANK;
		}

		return themeDisplayOptional.map(
			themeDisplay -> {
				Optional<Layout> layoutOptional = _getLayoutOptional(
					journalArticle);

				return layoutOptional.map(
					layout -> {
						HttpServletRequest httpServletRequest =
							themeDisplay.getRequest();

						LayoutDisplayPageObjectProvider<?>
							initialLayoutDisplayPageObjectProvider =
								(LayoutDisplayPageObjectProvider<?>)
									httpServletRequest.getAttribute(
										LayoutDisplayPageWebKeys.
											LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER);

						httpServletRequest.setAttribute(
							LayoutDisplayPageWebKeys.
								LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER,
							_getLayoutDisplayPageObjectProvider(
								journalArticle));

						String completeURL = _portal.getCurrentCompleteURL(
							httpServletRequest);

						try {
							String canonicalURL = _portal.getCanonicalURL(
								completeURL, themeDisplay, layout, false,
								false);

							LayoutSEOLink layoutSEOLink =
								_layoutSEOLinkManager.getCanonicalLayoutSEOLink(
									layout, locale, canonicalURL,
									_portal.getAlternateURLs(
										canonicalURL, themeDisplay, layout));

							return layoutSEOLink.getHref();
						}
						catch (PortalException portalException) {
							_log.error(portalException, portalException);

							return StringPool.BLANK;
						}
						finally {
							httpServletRequest.setAttribute(
								LayoutDisplayPageWebKeys.
									LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER,
								initialLayoutDisplayPageObjectProvider);
						}
					}
				).orElse(
					StringPool.BLANK
				);
			}
		).orElse(
			StringPool.BLANK
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
		Optional<Layout> layoutOptional = _getLayoutOptional(journalArticle);

		return layoutOptional.filter(
			Layout::isTypeAssetDisplay
		).filter(
			layout -> !_isEmbeddedPersonalApplicationLayout(layout)
		).filter(
			layout -> {
				try {
					return _hasEditPermission(
						journalArticle, layout,
						PermissionThreadLocal.getPermissionChecker());
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);

					return false;
				}
			}
		).isPresent();
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

	private LayoutDisplayPageObjectProvider<JournalArticle>
		_getLayoutDisplayPageObjectProvider(JournalArticle journalArticle) {

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			_layoutDisplayPageProviderTracker.
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

	private Optional<Layout> _getLayoutOptional(JournalArticle journalArticle) {
		return Optional.ofNullable(
			_getLayoutDisplayPageObjectProvider(journalArticle)
		).filter(
			layoutDisplayPageObjectProvider ->
				layoutDisplayPageObjectProvider.getDisplayObject() != null
		).map(
			layoutDisplayPageObjectProvider -> {
				try {
					return AssetDisplayPageUtil.
						getAssetDisplayPageLayoutPageTemplateEntry(
							layoutDisplayPageObjectProvider.getGroupId(),
							layoutDisplayPageObjectProvider.getClassNameId(),
							layoutDisplayPageObjectProvider.getClassPK(),
							layoutDisplayPageObjectProvider.getClassTypeId());
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);

					return null;
				}
			}
		).map(
			layoutPageTemplateEntry -> _layoutLocalService.fetchLayout(
				layoutPageTemplateEntry.getPlid())
		);
	}

	private Optional<ThemeDisplay> _getThemeDisplayOptional() {
		return Optional.ofNullable(
			ServiceContextThreadLocal.getServiceContext()
		).map(
			ServiceContext::getThemeDisplay
		);
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
	private LayoutSEOLinkManager _layoutSEOLinkManager;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}