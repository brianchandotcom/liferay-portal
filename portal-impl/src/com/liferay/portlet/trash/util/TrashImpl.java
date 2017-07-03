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

package com.liferay.portlet.trash.util;

import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ContainerModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.trash.kernel.model.TrashEntry;
import com.liferay.trash.kernel.model.TrashVersion;
import com.liferay.trash.kernel.service.TrashEntryLocalServiceUtil;
import com.liferay.trash.kernel.service.TrashVersionLocalServiceUtil;
import com.liferay.trash.kernel.util.Trash;
import com.liferay.trash.kernel.util.comparator.EntryCreateDateComparator;
import com.liferay.trash.kernel.util.comparator.EntryTypeComparator;
import com.liferay.trash.kernel.util.comparator.EntryUserNameComparator;

import java.text.Format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 * @author Julio Camarero
 * @deprecated As of 7.0.0
 */
@Deprecated
@DoPrivileged
public class TrashImpl implements Trash {

	@Override
	public void addBaseModelBreadcrumbEntries(
			HttpServletRequest request,
			LiferayPortletResponse liferayPortletResponse, String className,
			long classPK, PortletURL containerModelURL)
		throws PortalException, PortletException {

		addBreadcrumbEntries(
			request, liferayPortletResponse, className, classPK, "classPK",
			containerModelURL, true);
	}

	@Override
	public void addContainerModelBreadcrumbEntries(
			HttpServletRequest request,
			LiferayPortletResponse liferayPortletResponse, String className,
			long classPK, PortletURL containerModelURL)
		throws PortalException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			className);

		String rootContainerModelTitle = LanguageUtil.get(
			themeDisplay.getLocale(), trashHandler.getRootContainerModelName());

		if (classPK == 0) {
			PortalUtil.addPortletBreadcrumbEntry(
				request, rootContainerModelTitle, null);

			return;
		}

		containerModelURL.setParameter("containerModelId", "0");

		PortalUtil.addPortletBreadcrumbEntry(
			request, rootContainerModelTitle, containerModelURL.toString());

		addBreadcrumbEntries(
			request, liferayPortletResponse, className, classPK,
			"containerModelId", containerModelURL, false);
	}

	@Override
	public void addTrashSessionMessages(
		ActionRequest actionRequest, List<TrashedModel> trashedModels) {

		addTrashSessionMessages(
			actionRequest, trashedModels, Constants.MOVE_TO_TRASH);
	}

	@Override
	public void addTrashSessionMessages(
		ActionRequest actionRequest, List<TrashedModel> trashedModels,
		String cmd) {

		Map<String, Object> data = new HashMap<>();

		data.put(Constants.CMD, new String[] {cmd});
		data.put("trashedModels", trashedModels);

		SessionMessages.add(
			actionRequest,
			PortalUtil.getPortletId(actionRequest) +
				SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA,
			data);
	}

	@Override
	public void addTrashSessionMessages(
		ActionRequest actionRequest, TrashedModel trashedModel) {

		addTrashSessionMessages(
			actionRequest, trashedModel, Constants.MOVE_TO_TRASH);
	}

	@Override
	public void addTrashSessionMessages(
		ActionRequest actionRequest, TrashedModel trashedModel, String cmd) {

		List<TrashedModel> trashedModels = new ArrayList<>();

		trashedModels.add(trashedModel);

		addTrashSessionMessages(actionRequest, trashedModels, cmd);
	}

	@Override
	public void deleteEntriesAttachments(
		long companyId, long repositoryId, Date date,
		String[] attachmentFileNames) {

		for (String attachmentFileName : attachmentFileNames) {
			String trashTime = getTrashTime(
				attachmentFileName, TRASH_TIME_SEPARATOR);

			long timestamp = GetterUtil.getLong(trashTime);

			if (timestamp < date.getTime()) {
				DLStoreUtil.deleteDirectory(
					companyId, repositoryId, attachmentFileName);
			}
		}
	}

	@Override
	public Group disableTrash(Group group) {
		UnicodeProperties typeSettingsProperties =
			group.getParentLiveGroupTypeSettingsProperties();

		typeSettingsProperties.setProperty("trashEnabled", StringPool.FALSE);

		group.setTypeSettingsProperties(typeSettingsProperties);

		return GroupLocalServiceUtil.updateGroup(group);
	}

	@Override
	public List<TrashEntry> getEntries(Hits hits) {
		List<TrashEntry> entries = new ArrayList<>();

		for (Document document : hits.getDocs()) {
			String entryClassName = GetterUtil.getString(
				document.get(Field.ENTRY_CLASS_NAME));
			long classPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			try {
				TrashEntry entry = TrashEntryLocalServiceUtil.fetchEntry(
					entryClassName, classPK);

				if (entry == null) {
					String userName = GetterUtil.getString(
						document.get(Field.REMOVED_BY_USER_NAME));

					Date removedDate = document.getDate(Field.REMOVED_DATE);

					entry = TrashEntryLocalServiceUtil.createTrashEntry(0);

					entry.setUserName(userName);
					entry.setCreateDate(removedDate);

					TrashHandler trashHandler =
						TrashHandlerRegistryUtil.getTrashHandler(
							entryClassName);

					TrashRenderer trashRenderer = trashHandler.getTrashRenderer(
						classPK);

					entry.setClassName(trashRenderer.getClassName());
					entry.setClassPK(trashRenderer.getClassPK());

					String rootEntryClassName = GetterUtil.getString(
						document.get(Field.ROOT_ENTRY_CLASS_NAME));
					long rootEntryClassPK = GetterUtil.getLong(
						document.get(Field.ROOT_ENTRY_CLASS_PK));

					TrashEntry rootTrashEntry =
						TrashEntryLocalServiceUtil.fetchEntry(
							rootEntryClassName, rootEntryClassPK);

					if (rootTrashEntry != null) {
						entry.setRootEntry(rootTrashEntry);
					}
				}

				entries.add(entry);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to find trash entry for " + entryClassName +
							" with primary key " + classPK);
				}
			}
		}

		return entries;
	}

	@Override
	public OrderByComparator<TrashEntry> getEntryOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<TrashEntry> orderByComparator = null;

		if (orderByCol.equals("removed-by")) {
			orderByComparator = new EntryUserNameComparator(orderByAsc);
		}
		else if (orderByCol.equals("removed-date")) {
			orderByComparator = new EntryCreateDateComparator(orderByAsc);
		}
		else if (orderByCol.equals("type")) {
			orderByComparator = new EntryTypeComparator(orderByAsc);
		}

		return orderByComparator;
	}

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
	public String getNewName(String oldName, String token) {
		StringBundler sb = new StringBundler(3);

		sb.append(oldName);
		sb.append(StringPool.SPACE);
		sb.append(token);

		return sb.toString();
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
		else {
			return getNewName(oldName, sb.toString());
		}
	}

	@Override
	public String getOriginalTitle(String title) {
		return getOriginalTitle(title, "title", TRASH_PREFIX);
	}

	@Override
	public String getOriginalTitle(String title, String paramName) {
		return getOriginalTitle(title, paramName, TRASH_PREFIX);
	}

	@Override
	public String getTrashTime(String title, String separator) {
		int index = title.lastIndexOf(separator);

		if (index < 0) {
			return StringPool.BLANK;
		}

		return title.substring(index + 1, title.length());
	}

	@Override
	public String getTrashTitle(long trashEntryId) {
		return getTrashTitle(trashEntryId, TRASH_PREFIX);
	}

	@Override
	public PortletURL getViewContentURL(
			HttpServletRequest request, long trashEntryId)
		throws PortalException {

		TrashEntry trashEntry = TrashEntryLocalServiceUtil.fetchEntry(
			trashEntryId);

		return getViewContentURL(
			request, trashEntry.getClassName(), trashEntry.getClassPK());
	}

	@Override
	public PortletURL getViewContentURL(
			HttpServletRequest request, String className, long classPK)
		throws PortalException {

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			className);

		if (trashHandler.isInTrashContainer(classPK)) {
			TrashEntry trashEntry = trashHandler.getTrashEntry(classPK);

			className = trashEntry.getClassName();
			classPK = trashEntry.getClassPK();

			trashHandler = TrashHandlerRegistryUtil.getTrashHandler(className);
		}

		TrashRenderer trashRenderer = trashHandler.getTrashRenderer(classPK);

		if (trashRenderer == null) {
			return null;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			request, TrashEntry.class.getName(), PortletProvider.Action.VIEW);

		portletURL.setParameter("mvcPath", "/view_content.jsp");
		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());

		TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(
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
	public PortletURL getViewURL(HttpServletRequest request)
		throws PortalException {

		return PortletProviderUtil.getPortletURL(
			request, TrashEntry.class.getName(), PortletProvider.Action.VIEW);
	}

	@Override
	public boolean isInTrash(String className, long classPK)
		throws PortalException {

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			className);

		if (trashHandler == null) {
			return false;
		}

		return trashHandler.isInTrash(classPK);
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
		return isTrashEnabled(GroupLocalServiceUtil.getGroup(groupId));
	}

	@Override
	public boolean isValidTrashTitle(String title) {
		return isValidTrashTitle(title, TRASH_PREFIX);
	}

	protected void addBreadcrumbEntries(
			HttpServletRequest request,
			LiferayPortletResponse liferayPortletResponse, String className,
			long classPK, String paramName, PortletURL containerModelURL,
			boolean checkInTrashContainers)
		throws PortalException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletURLUtil.clone(
			containerModelURL, liferayPortletResponse);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			className);

		List<ContainerModel> containerModels =
			trashHandler.getParentContainerModels(classPK);

		Collections.reverse(containerModels);

		for (ContainerModel containerModel : containerModels) {
			TrashHandler containerModelTrashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					containerModel.getModelClassName());

			if (checkInTrashContainers &&
				!containerModelTrashHandler.isInTrash(
					containerModel.getContainerModelId())) {

				continue;
			}

			portletURL.setParameter(
				paramName,
				String.valueOf(containerModel.getContainerModelId()));

			String name = containerModel.getContainerModelName();

			if (containerModelTrashHandler.isInTrash(
					containerModel.getContainerModelId())) {

				name = getOriginalTitle(name);
			}

			PortalUtil.addPortletBreadcrumbEntry(
				request, name, portletURL.toString());
		}

		TrashRenderer trashRenderer = trashHandler.getTrashRenderer(classPK);

		PortalUtil.addPortletBreadcrumbEntry(
			request, trashRenderer.getTitle(themeDisplay.getLocale()), null);
	}

	protected String getOriginalTitle(
		String title, String paramName, String prefix) {

		if (!isValidTrashTitle(title, prefix)) {
			return title;
		}

		title = title.substring(prefix.length());

		long trashEntryId = GetterUtil.getLong(title);

		if (trashEntryId <= 0) {
			return title;
		}

		try {
			TrashEntry trashEntry = TrashEntryLocalServiceUtil.fetchEntry(
				trashEntryId);

			if (trashEntry == null) {
				TrashVersion trashVersion =
					TrashVersionLocalServiceUtil.getTrashVersion(trashEntryId);

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

	protected String getTrashTitle(long trashEntryId, String prefix) {
		return prefix.concat(String.valueOf(trashEntryId));
	}

	protected boolean isValidTrashTitle(String title, String prefix) {
		if (title.startsWith(prefix)) {
			return true;
		}

		return false;
	}

	protected final String TRASH_PREFIX = StringPool.SLASH;

	private static final Log _log = LogFactoryUtil.getLog(TrashImpl.class);

}