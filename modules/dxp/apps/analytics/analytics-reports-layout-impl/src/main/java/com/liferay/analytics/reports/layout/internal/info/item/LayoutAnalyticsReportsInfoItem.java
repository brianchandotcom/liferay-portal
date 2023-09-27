/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.layout.internal.info.item;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.info.type.WebImage;
import com.liferay.layout.seo.kernel.LayoutSEOLink;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(service = AnalyticsReportsInfoItem.class)
public class LayoutAnalyticsReportsInfoItem
	implements AnalyticsReportsInfoItem<Layout> {

	@Override
	public String getAuthorName(Layout layout) {
		return null;
	}

	@Override
	public long getAuthorUserId(Layout layout) {
		return 0L;
	}

	@Override
	public WebImage getAuthorWebImage(Layout layout, Locale locale) {
		return null;
	}

	@Override
	public List<Locale> getAvailableLocales(Layout layout) {
		Group group = _groupLocalService.fetchGroup(layout.getGroupId());

		if (group == null) {
			return Collections.singletonList(LocaleUtil.getDefault());
		}

		Set<Locale> availableLocales = _language.getAvailableLocales(
			group.getGroupId());

		if (availableLocales == null) {
			return Collections.singletonList(LocaleUtil.getDefault());
		}

		return ListUtil.fromCollection(availableLocales);
	}

	@Override
	public String getCanonicalURL(Layout layout, Locale locale) {
		ThemeDisplay themeDisplay = _getThemeDisplay();

		if (themeDisplay == null) {
			return StringPool.BLANK;
		}

		try {
			String canonicalURL = _portal.getCanonicalURL(
				_getCompleteURL(themeDisplay), themeDisplay, layout, false,
				false);

			LayoutSEOLink layoutSEOLink =
				_layoutSEOLinkManager.getCanonicalLayoutSEOLink(
					layout, locale, canonicalURL, themeDisplay);

			String href = layoutSEOLink.getHref();

			if (href == null) {
				return StringPool.BLANK;
			}

			return href;
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return StringPool.BLANK;
		}
	}

	@Override
	public Locale getDefaultLocale(Layout layout) {
		try {
			return _portal.getSiteDefaultLocale(layout.getGroupId());
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return LocaleUtil.getDefault();
	}

	@Override
	public Date getPublishDate(Layout layout) {
		Date date = layout.getPublishDate();

		if (date == null) {
			date = layout.getModifiedDate();
		}

		return date;
	}

	@Override
	public String getTitle(Layout layout, Locale locale) {
		String title = layout.getTitle(locale);

		if (Validator.isNull(title)) {
			return layout.getName(locale);
		}

		return title;
	}

	@Override
	public boolean isShow(Layout layout) {
		if (layout.isEmbeddedPersonalApplication() ||
			(!layout.isTypeContent() && !layout.isTypePortlet())) {

			return false;
		}

		try {
			if (!_hasEditPermission(
					layout, PermissionThreadLocal.getPermissionChecker())) {

				return false;
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return false;
		}

		return true;
	}

	private String _getCompleteURL(ThemeDisplay themeDisplay) {
		try {
			return _portal.getLayoutURL(themeDisplay);
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return _portal.getCurrentCompleteURL(themeDisplay.getRequest());
		}
	}

	private ThemeDisplay _getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return null;
		}

		return serviceContext.getThemeDisplay();
	}

	private boolean _hasEditPermission(
			Layout layout, PermissionChecker permissionChecker)
		throws PortalException {

		if (!LayoutPermissionUtil.containsLayoutRestrictedUpdatePermission(
				permissionChecker, layout)) {

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutAnalyticsReportsInfoItem.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private LayoutSEOLinkManager _layoutSEOLinkManager;

	@Reference
	private Portal _portal;

}