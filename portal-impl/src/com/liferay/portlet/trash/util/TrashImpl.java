/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.model.impl.TrashEntryImpl;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.trash.util.comparator.EntryCreateDateComparator;
import com.liferay.portlet.trash.util.comparator.EntryTypeComparator;
import com.liferay.portlet.trash.util.comparator.EntryUserNameComparator;

import java.text.Format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 * @author Julio Camarero
 */
public class TrashImpl implements Trash {

	public void addContainerModelBreadcrumbEntries(
			HttpServletRequest request, TrashHandler trashHandler,
			ContainerModel containerModel, PortletURL containerModelURL)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String rootContainerModelName = LanguageUtil.get(
			themeDisplay.getLocale(), trashHandler.getRootContainerModelName());

		if (containerModel == null) {
			PortalUtil.addPortletBreadcrumbEntry(
				request, rootContainerModelName, null);

			return;
		}

		List<ContainerModel> containerModels =
			trashHandler.getParentContainerModels(
				containerModel.getContainerModelId());

		Collections.reverse(containerModels);

		containerModelURL.setParameter("containerModelId", "0");

		PortalUtil.addPortletBreadcrumbEntry(
			request, rootContainerModelName, containerModelURL.toString());

		for (ContainerModel curContainerModel : containerModels) {
			containerModelURL.setParameter(
				"containerModelId",
				String.valueOf(curContainerModel.getContainerModelId()));

			PortalUtil.addPortletBreadcrumbEntry(
				request, curContainerModel.getContainerModelName(),
				containerModelURL.toString());
		}

		PortalUtil.addPortletBreadcrumbEntry(
			request, containerModel.getContainerModelName(), null);
	}

	public String appendTrashNamespace(String title) {
		return appendTrashNamespace(title, StringPool.SLASH);
	}

	public String appendTrashNamespace(String title, String separator) {
		StringBundler sb = new StringBundler(3);

		sb.append(title);
		sb.append(separator);
		sb.append(System.currentTimeMillis());

		return sb.toString();
	}

	public void deleteEntriesAttachments(
			long companyId, long repositoryId, Date date,
			String[] attachmentFileNames)
		throws PortalException, SystemException {

		for (String attachmentFileName : attachmentFileNames) {
			String trashTime = TrashUtil.getTrashTime(
				attachmentFileName, TrashUtil.TRASH_TIME_SEPARATOR);

			long timestamp = GetterUtil.getLong(trashTime);

			if (timestamp < date.getTime()) {
				DLStoreUtil.deleteDirectory(
					companyId, repositoryId, attachmentFileName);
			}
		}
	}

	public List<TrashEntry> getEntries(Hits hits) {
		List<TrashEntry> entries = new ArrayList<TrashEntry>();

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

					entry = new TrashEntryImpl();

					entry.setClassName(entryClassName);
					entry.setClassPK(classPK);

					entry.setUserName(userName);
					entry.setCreateDate(removedDate);

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

	public OrderByComparator getEntryOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

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

	public int getMaxAge(Group group) throws PortalException, SystemException {
		if (group.isLayout()) {
			group = group.getParentGroup();
		}

		int trashEntriesMaxAge = PrefsPropsUtil.getInteger(
			group.getCompanyId(), PropsKeys.TRASH_ENTRIES_MAX_AGE,
			GetterUtil.getInteger(
				PropsUtil.get(PropsKeys.TRASH_ENTRIES_MAX_AGE)));

		UnicodeProperties typeSettingsProperties =
			group.getTypeSettingsProperties();

		return GetterUtil.getInteger(
			typeSettingsProperties.getProperty("trashEntriesMaxAge"),
			trashEntriesMaxAge);
	}

	public String getNewName(ThemeDisplay themeDisplay, String oldName) {
		Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
			themeDisplay.getLocale(), themeDisplay.getTimeZone());

		StringBundler sb = new StringBundler(5);

		sb.append(oldName);
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(
			StringUtil.replace(
				dateFormatDateTime.format(new Date()), CharPool.SLASH,
				CharPool.PERIOD));
		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	public String getTrashTime(String title, String separator) {
		int index = title.lastIndexOf(separator);

		if (index < 0) {
			return StringPool.BLANK;
		}

		return title.substring(index + 1, title.length());
	}

	public boolean isInTrash(String className, long classPK)
		throws PortalException, SystemException {

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			className);

		if (trashHandler == null) {
			return false;
		}

		return trashHandler.isInTrash(classPK);
	}

	public boolean isTrashEnabled(long groupId)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		UnicodeProperties typeSettingsProperties =
			group.getParentLiveGroupTypeSettingsProperties();

		int trashEnabledCompany = PrefsPropsUtil.getInteger(
			group.getCompanyId(), PropsKeys.TRASH_ENABLED);

		if (trashEnabledCompany == TrashUtil.TRASH_DISABLED) {
			return false;
		}

		int trashEnabledGroup = GetterUtil.getInteger(
			typeSettingsProperties.getProperty("trashEnabled"),
			TrashUtil.TRASH_DEFAULT_VALUE);

		if ((trashEnabledGroup == TrashUtil.TRASH_ENABLED) ||
			((trashEnabledCompany == TrashUtil.TRASH_ENABLED_BY_DEFAULT) &&
			 (trashEnabledGroup == TrashUtil.TRASH_DEFAULT_VALUE))) {

			return true;
		}

		return false;
	}

	public String stripTrashNamespace(String title) {
		return stripTrashNamespace(title, StringPool.SLASH);
	}

	public String stripTrashNamespace(String title, String separator) {
		int index = title.lastIndexOf(separator);

		if (index < 0) {
			return title;
		}

		return title.substring(0, index);
	}

	private Log _log = LogFactoryUtil.getLog(TrashImpl.class);

}