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

package com.liferay.analytics.reports.layout.internal.info.item;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.type.WebImage;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
		return _getUserOptional(
			layout
		).map(
			User::getFullName
		).orElse(
			StringPool.BLANK
		);
	}

	@Override
	public long getAuthorUserId(Layout layout) {
		return _getUserOptional(
			layout
		).map(
			User::getUserId
		).orElse(
			0L
		);
	}

	@Override
	public WebImage getAuthorWebImage(Layout layout, Locale locale) {
		ThemeDisplay themeDisplay = _getThemeDisplay();

		if (themeDisplay == null) {
			return new WebImage(StringPool.BLANK);
		}

		Optional<User> userOptional = _getUserOptional(layout);

		return userOptional.map(
			user -> {
				try {
					return new WebImage(user.getPortraitURL(themeDisplay));
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);

					return new WebImage(StringPool.BLANK);
				}
			}
		).orElse(
			new WebImage(StringPool.BLANK)
		);
	}

	@Override
	public List<Locale> getAvailableLocales(Layout layout) {
		return Optional.ofNullable(
			_groupLocalService.fetchGroup(layout.getGroupId())
		).map(
			Group::getGroupId
		).map(
			_language::getAvailableLocales
		).map(
			ListUtil::fromCollection
		).orElseGet(
			() -> Collections.singletonList(LocaleUtil.getDefault())
		);
	}

	@Override
	public Locale getDefaultLocale(Layout layout) {
		try {
			return _portal.getSiteDefaultLocale(layout.getGroupId());
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return LocaleUtil.getDefault();
	}

	@Override
	public Date getPublishDate(Layout layout) {
		return layout.getPublishDate();
	}

	@Override
	public String getTitle(Layout layout, Locale locale) {
		return layout.getTitle(locale);
	}

	@Override
	public boolean isShow(Layout layout) {
		if (!layout.isTypeContent() && !layout.isTypePortlet()) {
			return false;
		}

		if (_isEmbeddedPersonalApplicationLayout(layout)) {
			return false;
		}

		try {
			if (!_hasEditPermission(
					layout, PermissionThreadLocal.getPermissionChecker())) {

				return false;
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return false;
		}

		return true;
	}

	private ThemeDisplay _getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getThemeDisplay();
		}

		return null;
	}

	private Optional<User> _getUserOptional(Layout layout) {
		return Optional.ofNullable(
			_userLocalService.fetchUser(layout.getUserId()));
	}

	private boolean _hasEditPermission(
			Layout layout, PermissionChecker permissionChecker)
		throws PortalException {

		if (!LayoutPermissionUtil.contains(
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
		LayoutAnalyticsReportsInfoItem.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}