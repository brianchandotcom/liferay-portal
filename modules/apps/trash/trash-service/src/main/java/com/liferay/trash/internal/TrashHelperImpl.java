/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.trash.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.model.TrashVersion;
import com.liferay.trash.service.TrashEntryLocalService;
import com.liferay.trash.service.TrashVersionLocalService;

import java.text.Format;

import java.util.Date;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = TrashHelper.class)
public class TrashHelperImpl implements TrashHelper {

	@Override
	public int getMaxAge(Group group) {
		int trashEntriesMaxAge = PrefsPropsUtil.getInteger(
			group.getCompanyId(), PropsKeys.TRASH_ENTRIES_MAX_AGE,
			PropsValues.TRASH_ENTRIES_MAX_AGE);

		UnicodeProperties typeSettingsProperties =
			group.getParentLiveGroupTypeSettingsProperties();

		return GetterUtil.getInteger(
			typeSettingsProperties.getProperty("trashEntriesMaxAge"),
			trashEntriesMaxAge);
	}

	@Override
	public String getNewName(
			ThemeDisplay themeDisplay, String className, long classPK,
			String oldName)
		throws PortalException {

		TrashRenderer trashRenderer = null;

		if (Validator.isNotNull(className) && (classPK > 0)) {
			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(className);

			trashRenderer = trashHandler.getTrashRenderer(classPK);
		}

		StringBundler sb = new StringBundler(3);

		sb.append(StringPool.OPEN_PARENTHESIS);

		Format format = FastDateFormatFactoryUtil.getDateTime(
			themeDisplay.getLocale(), themeDisplay.getTimeZone());

		sb.append(
			StringUtil.replace(
				format.format(new Date()),
				new char[] {CharPool.SLASH, CharPool.COLON},
				new char[] {CharPool.PERIOD, CharPool.PERIOD}));

		sb.append(StringPool.CLOSE_PARENTHESIS);

		if (trashRenderer != null) {
			return trashRenderer.getNewName(oldName, sb.toString());
		}

		return _getNewName(oldName, sb.toString());
	}

	@Override
	public String getOriginalTitle(String title) {
		return _getOriginalTitle(title, "title", _TRASH_PREFIX);
	}

	@Override
	public String getTrashTitle(long entryId) {
		return _getTrashTitle(entryId, _TRASH_PREFIX);
	}

	@Override
	public PortletURL getViewContentURL(
			HttpServletRequest httpServletRequest, String className,
			long classPK)
		throws PortalException {

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			className);

		if (trashHandler.isInTrashContainer(classPK)) {
			com.liferay.trash.kernel.model.TrashEntry trashEntry =
				trashHandler.getTrashEntry(classPK);

			className = trashEntry.getClassName();
			classPK = trashEntry.getClassPK();

			trashHandler = TrashHandlerRegistryUtil.getTrashHandler(className);
		}

		TrashRenderer trashRenderer = trashHandler.getTrashRenderer(classPK);

		if (trashRenderer == null) {
			return null;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, TrashEntry.class.getName(),
			PortletProvider.Action.VIEW);

		portletURL.setParameter("mvcPath", "/view_content.jsp");
		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());

		TrashEntry trashEntry = _trashEntryLocalService.getEntry(
			className, classPK);

		if (trashEntry.getRootEntry() != null) {
			portletURL.setParameter("className", className);
			portletURL.setParameter("classPK", String.valueOf(classPK));
		}
		else {
			portletURL.setParameter(
				"trashEntryId", String.valueOf(trashEntry.getEntryId()));
		}

		portletURL.setParameter("showAssetMetadata", Boolean.TRUE.toString());

		return portletURL;
	}

	@Override
	public boolean isTrashEnabled(Group group) {
		boolean companyTrashEnabled = PrefsPropsUtil.getBoolean(
			group.getCompanyId(), PropsKeys.TRASH_ENABLED);

		if (!companyTrashEnabled) {
			return false;
		}

		UnicodeProperties typeSettingsProperties =
			group.getParentLiveGroupTypeSettingsProperties();

		return GetterUtil.getBoolean(
			typeSettingsProperties.getProperty("trashEnabled"), true);
	}

	@Override
	public boolean isTrashEnabled(long groupId) throws PortalException {
		return isTrashEnabled(_groupLocalService.getGroup(groupId));
	}

	private String _getNewName(String oldName, String token) {
		StringBundler sb = new StringBundler(3);

		sb.append(oldName);
		sb.append(StringPool.SPACE);
		sb.append(token);

		return sb.toString();
	}

	private String _getOriginalTitle(
		String title, String paramName, String prefix) {

		if (!_isValidTrashTitle(title, prefix)) {
			return title;
		}

		title = title.substring(prefix.length());

		long trashEntryId = GetterUtil.getLong(title);

		if (trashEntryId <= 0) {
			return title;
		}

		try {
			TrashEntry trashEntry = _trashEntryLocalService.fetchEntry(
				trashEntryId);

			if (trashEntry == null) {
				TrashVersion trashVersion =
					_trashVersionLocalService.getTrashVersion(trashEntryId);

				title = trashVersion.getTypeSettingsProperty(paramName);
			}
			else {
				title = trashEntry.getTypeSettingsProperty(paramName);
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No trash entry or trash version exists with ID " +
						trashEntryId);
			}
		}

		return title;
	}

	private String _getTrashTitle(long trashEntryId, String prefix) {
		return prefix.concat(String.valueOf(trashEntryId));
	}

	private boolean _isValidTrashTitle(String title, String prefix) {
		if (title.startsWith(prefix)) {
			return true;
		}

		return false;
	}

	private static final String _TRASH_PREFIX = StringPool.SLASH;

	private static final Log _log = LogFactoryUtil.getLog(
		TrashHelperImpl.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private TrashEntryLocalService _trashEntryLocalService;

	@Reference
	private TrashVersionLocalService _trashVersionLocalService;

}