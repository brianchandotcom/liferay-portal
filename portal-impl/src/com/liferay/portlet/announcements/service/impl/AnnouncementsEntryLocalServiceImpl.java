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

package com.liferay.portlet.announcements.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.SubscriptionSender;
import com.liferay.portlet.announcements.EntryContentException;
import com.liferay.portlet.announcements.EntryDisplayDateException;
import com.liferay.portlet.announcements.EntryExpirationDateException;
import com.liferay.portlet.announcements.EntryTitleException;
import com.liferay.portlet.announcements.EntryURLException;
import com.liferay.portlet.announcements.model.AnnouncementsDelivery;
import com.liferay.portlet.announcements.model.AnnouncementsEntry;
import com.liferay.portlet.announcements.service.base.AnnouncementsEntryLocalServiceBaseImpl;
import com.liferay.util.ContentUtil;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class AnnouncementsEntryLocalServiceImpl
	extends AnnouncementsEntryLocalServiceBaseImpl {

	@Override
	public AnnouncementsEntry addEntry(
			long userId, long classNameId, long classPK, String title,
			String content, String url, String type, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, boolean displayImmediately,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, int priority, boolean alert)
		throws PortalException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);

		Date displayDate = new Date();

		if (!displayImmediately) {
			displayDate = PortalUtil.getDate(
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, user.getTimeZone(),
				EntryDisplayDateException.class);
		}

		Date expirationDate = PortalUtil.getDate(
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, user.getTimeZone(),
			EntryExpirationDateException.class);

		validate(title, content, url);

		long entryId = counterLocalService.increment();

		AnnouncementsEntry entry = announcementsEntryPersistence.create(
			entryId);

		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setClassNameId(classNameId);
		entry.setClassPK(classPK);
		entry.setTitle(title);
		entry.setContent(content);
		entry.setUrl(url);
		entry.setType(type);
		entry.setDisplayDate(displayDate);
		entry.setExpirationDate(expirationDate);
		entry.setPriority(priority);
		entry.setAlert(alert);

		announcementsEntryPersistence.update(entry);

		// Resources

		resourceLocalService.addResources(
			user.getCompanyId(), 0, user.getUserId(),
			AnnouncementsEntry.class.getName(), entry.getEntryId(), false,
			false, false);

		return entry;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #addEntry(long, long, long,
	 *             String, String, String, String, int, int, int, int, int,
	 *             boolean, int, int, int, int, int, int, boolean)}
	 */
	@Deprecated
	@Override
	public AnnouncementsEntry addEntry(
			long userId, long classNameId, long classPK, String title,
			String content, String url, String type, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute, int priority,
			boolean alert)
		throws PortalException {

		return addEntry(
			userId, classNameId, classPK, title, content, url, type,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, false, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			priority, alert);
	}

	@Override
	public void checkEntries() throws PortalException {
		Date now = new Date();

		if (_previousCheckDate == null) {
			_previousCheckDate = new Date(
				now.getTime() - _ANNOUNCEMENTS_ENTRY_CHECK_INTERVAL);
		}

		List<AnnouncementsEntry> entries =
			announcementsEntryFinder.findByDisplayDate(now, _previousCheckDate);

		if (_log.isDebugEnabled()) {
			_log.debug("Processing " + entries.size() + " entries");
		}

		for (AnnouncementsEntry entry : entries) {
			notifyUsers(entry);
		}

		_previousCheckDate = now;
	}

	@Override
	public void deleteEntry(AnnouncementsEntry entry) throws PortalException {

		// Entry

		announcementsEntryPersistence.remove(entry);

		// Resources

		resourceLocalService.deleteResource(
			entry.getCompanyId(), AnnouncementsEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, entry.getEntryId());

		// Flags

		announcementsFlagLocalService.deleteFlags(entry.getEntryId());
	}

	@Override
	public void deleteEntry(long entryId) throws PortalException {
		AnnouncementsEntry entry =
			announcementsEntryPersistence.findByPrimaryKey(entryId);

		deleteEntry(entry);
	}

	@Override
	public List<AnnouncementsEntry> getEntries(
		long userId, LinkedHashMap<Long, long[]> scopes, boolean alert,
		int flagValue, int start, int end) {

		return getEntries(
			userId, scopes, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, alert, flagValue,
			start, end);
	}

	@Override
	public List<AnnouncementsEntry> getEntries(
		long userId, LinkedHashMap<Long, long[]> scopes, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue, int start,
		int end) {

		return announcementsEntryFinder.findByScopes(
			userId, scopes, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, alert, flagValue, start, end);
	}

	@Override
	public List<AnnouncementsEntry> getEntries(
		long classNameId, long classPK, boolean alert, int start, int end) {

		return announcementsEntryPersistence.findByC_C_A(
			classNameId, classPK, alert, start, end);
	}

	@Override
	public List<AnnouncementsEntry> getEntries(
		long userId, long classNameId, long[] classPKs, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue, int start,
		int end) {

		return announcementsEntryFinder.findByScope(
			userId, classNameId, classPKs, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, alert, flagValue, start,
			end);
	}

	@Override
	public int getEntriesCount(
		long userId, LinkedHashMap<Long, long[]> scopes, boolean alert,
		int flagValue) {

		return getEntriesCount(
			userId, scopes, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, alert, flagValue);
	}

	@Override
	public int getEntriesCount(
		long userId, LinkedHashMap<Long, long[]> scopes, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue) {

		return announcementsEntryFinder.countByScopes(
			userId, scopes, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, alert, flagValue);
	}

	@Override
	public int getEntriesCount(long classNameId, long classPK, boolean alert) {
		return announcementsEntryPersistence.countByC_C_A(
			classNameId, classPK, alert);
	}

	@Override
	public int getEntriesCount(
		long userId, long classNameId, long[] classPKs, boolean alert,
		int flagValue) {

		return getEntriesCount(
			userId, classNameId, classPKs, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, alert,
			flagValue);
	}

	@Override
	public int getEntriesCount(
		long userId, long classNameId, long[] classPKs, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean alert, int flagValue) {

		return announcementsEntryFinder.countByScope(
			userId, classNameId, classPKs, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, alert, flagValue);
	}

	@Override
	public AnnouncementsEntry getEntry(long entryId) throws PortalException {
		return announcementsEntryPersistence.findByPrimaryKey(entryId);
	}

	@Override
	public List<AnnouncementsEntry> getUserEntries(
		long userId, int start, int end) {

		return announcementsEntryPersistence.findByUserId(userId, start, end);
	}

	@Override
	public int getUserEntriesCount(long userId) {
		return announcementsEntryPersistence.countByUserId(userId);
	}

	@Override
	public AnnouncementsEntry updateEntry(
			long userId, long entryId, String title, String content, String url,
			String type, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			boolean displayImmediately, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute, int priority)
		throws PortalException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);

		Date displayDate = new Date();

		if (!displayImmediately) {
			displayDate = PortalUtil.getDate(
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, user.getTimeZone(),
				EntryDisplayDateException.class);
		}

		Date expirationDate = PortalUtil.getDate(
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, user.getTimeZone(),
			EntryExpirationDateException.class);

		validate(title, content, url);

		AnnouncementsEntry entry =
			announcementsEntryPersistence.findByPrimaryKey(entryId);

		entry.setTitle(title);
		entry.setContent(content);
		entry.setUrl(url);
		entry.setType(type);
		entry.setDisplayDate(displayDate);
		entry.setExpirationDate(expirationDate);
		entry.setPriority(priority);

		announcementsEntryPersistence.update(entry);

		// Flags

		announcementsFlagLocalService.deleteFlags(entry.getEntryId());

		return entry;
	}

	protected void notifyUsers(AnnouncementsEntry entry)
		throws PortalException {

		Company company = companyPersistence.findByPrimaryKey(
			entry.getCompanyId());

		String className = entry.getClassName();
		long classPK = entry.getClassPK();

		String toName = PropsValues.ANNOUNCEMENTS_EMAIL_TO_NAME;
		String toAddress = PropsValues.ANNOUNCEMENTS_EMAIL_TO_ADDRESS;

		long teamId = 0;

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("announcementsDeliveryEmailOrSms", entry.getType());

		if (classPK > 0) {
			if (className.equals(Group.class.getName())) {
				Group group = groupPersistence.findByPrimaryKey(classPK);

				toName = group.getDescriptiveName();

				params.put("inherit", Boolean.TRUE);
				params.put("usersGroups", classPK);
			}
			else if (className.equals(Organization.class.getName())) {
				Organization organization =
					organizationPersistence.findByPrimaryKey(classPK);

				toName = organization.getName();

				params.put(
					"usersOrgsTree",
					ListUtil.fromArray(new Organization[] {organization}));
			}
			else if (className.equals(Role.class.getName())) {
				Role role = rolePersistence.findByPrimaryKey(classPK);

				toName = role.getName();

				if (role.getType() == RoleConstants.TYPE_REGULAR) {
					params.put("inherit", Boolean.TRUE);
					params.put("usersRoles", classPK);
				}
				else if (role.isTeam()) {
					teamId = role.getClassPK();
				}
				else {
					params.put(
						"userGroupRole", new Long[] {Long.valueOf(0), classPK});
				}
			}
			else if (className.equals(UserGroup.class.getName())) {
				UserGroup userGroup = userGroupPersistence.findByPrimaryKey(
					classPK);

				toName = userGroup.getName();

				params.put("usersUserGroups", classPK);
			}
		}

		if (className.equals(User.class.getName())) {
			User user = userPersistence.findByPrimaryKey(classPK);

			if (Validator.isNull(user.getEmailAddress())) {
				return;
			}

			notifyUsers(
				ListUtil.fromArray(new User[] {user}), entry,
				company.getLocale(), user.getEmailAddress(),
				user.getFullName());
		}
		else {
			int count = 0;

			if (teamId > 0) {
				count = userLocalService.getTeamUsersCount(teamId);
			}
			else {
				count = userLocalService.searchCount(
					company.getCompanyId(), null,
					WorkflowConstants.STATUS_APPROVED, params);
			}

			int pages = count / Indexer.DEFAULT_INTERVAL;

			for (int i = 0; i <= pages; i++) {
				int start = (i * Indexer.DEFAULT_INTERVAL);
				int end = start + Indexer.DEFAULT_INTERVAL;

				List<User> users = null;

				if (teamId > 0) {
					users = userLocalService.getTeamUsers(teamId, start, end);
				}
				else {
					users = userLocalService.search(
						company.getCompanyId(), null,
						WorkflowConstants.STATUS_APPROVED, params, start, end,
						(OrderByComparator<User>)null);
				}

				notifyUsers(
					users, entry, company.getLocale(), toAddress, toName);
			}
		}
	}

	protected void notifyUsers(
			List<User> users, AnnouncementsEntry entry, Locale locale,
			String toAddress, String toName)
		throws PortalException {

		if (_log.isDebugEnabled()) {
			_log.debug("Notifying " + users.size() + " users");
		}

		boolean notifyUsers = false;

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		for (User user : users) {
			AnnouncementsDelivery announcementsDelivery =
				announcementsDeliveryLocalService.getUserDelivery(
					user.getUserId(), entry.getType());

			if (announcementsDelivery.isEmail()) {
				subscriptionSender.addRuntimeSubscribers(
					user.getEmailAddress(), user.getFullName());

				notifyUsers = true;
			}

			if (announcementsDelivery.isSms()) {
				String smsSn = user.getContact().getSmsSn();

				subscriptionSender.addRuntimeSubscribers(
					smsSn, user.getFullName());

				notifyUsers = true;
			}
		}

		if (!notifyUsers) {
			return;
		}

		String body = ContentUtil.get(PropsValues.ANNOUNCEMENTS_EMAIL_BODY);
		String fromAddress = PrefsPropsUtil.getStringFromNames(
			entry.getCompanyId(), PropsKeys.ANNOUNCEMENTS_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		String fromName = PrefsPropsUtil.getStringFromNames(
			entry.getCompanyId(), PropsKeys.ANNOUNCEMENTS_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
		String subject = ContentUtil.get(
			PropsValues.ANNOUNCEMENTS_EMAIL_SUBJECT);

		subscriptionSender.setBody(body);
		subscriptionSender.setCompanyId(entry.getCompanyId());
		subscriptionSender.setContextAttribute(
			"[$ENTRY_CONTENT$]", entry.getContent(), false);
		subscriptionSender.setContextAttributes(
			"[$ENTRY_ID$]", entry.getEntryId(), "[$ENTRY_TITLE$]",
			entry.getTitle(), "[$ENTRY_TYPE$]",
			LanguageUtil.get(locale, entry.getType()), "[$ENTRY_URL$]",
			entry.getUrl(), "[$PORTLET_NAME$]",
			LanguageUtil.get(
				locale, (entry.isAlert() ? "alert" : "announcement")));
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setMailId("announcements_entry", entry.getEntryId());
		subscriptionSender.setPortletId(PortletKeys.ANNOUNCEMENTS);
		subscriptionSender.setScopeGroupId(entry.getGroupId());
		subscriptionSender.setSubject(subject);

		subscriptionSender.addRuntimeSubscribers(toAddress, toName);

		subscriptionSender.flushNotificationsAsync();
	}

	protected void validate(String title, String content, String url)
		throws PortalException {

		if (Validator.isNull(title)) {
			throw new EntryTitleException();
		}

		if (Validator.isNull(content)) {
			throw new EntryContentException();
		}

		if (Validator.isNotNull(url) && !Validator.isUrl(url)) {
			throw new EntryURLException();
		}
	}

	private static final long _ANNOUNCEMENTS_ENTRY_CHECK_INTERVAL =
		PropsValues.ANNOUNCEMENTS_ENTRY_CHECK_INTERVAL * Time.MINUTE;

	private static final Log _log = LogFactoryUtil.getLog(
		AnnouncementsEntryLocalServiceImpl.class);

	private Date _previousCheckDate;

}