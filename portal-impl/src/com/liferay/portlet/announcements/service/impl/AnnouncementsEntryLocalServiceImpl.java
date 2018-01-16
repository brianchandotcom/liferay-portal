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

import com.liferay.announcements.kernel.exception.EntryContentException;
import com.liferay.announcements.kernel.exception.EntryDisplayDateException;
import com.liferay.announcements.kernel.exception.EntryExpirationDateException;
import com.liferay.announcements.kernel.exception.EntryTitleException;
import com.liferay.announcements.kernel.exception.EntryURLException;
import com.liferay.announcements.kernel.model.AnnouncementsDelivery;
import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.mail.kernel.template.MailTemplate;
import com.liferay.mail.kernel.template.MailTemplateContext;
import com.liferay.mail.kernel.template.MailTemplateContextBuilder;
import com.liferay.mail.kernel.template.MailTemplateFactoryUtil;
import com.liferay.petra.content.ContentUtil;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.interval.IntervalActionProcessor;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.util.EscapableLocalizableFunction;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.announcements.service.base.AnnouncementsEntryLocalServiceBaseImpl;

import java.io.IOException;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.internet.InternetAddress;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Roberto Díaz
 */
public class AnnouncementsEntryLocalServiceImpl
	extends AnnouncementsEntryLocalServiceBaseImpl {

	@Override
	public AnnouncementsEntry addEntry(
			long userId, long classNameId, long classPK, String title,
			String content, String url, String type, Date displayDate,
			Date expirationDate, int priority, boolean alert)
		throws PortalException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);

		validate(title, content, url, displayDate, expirationDate);

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
	 * @deprecated As of 7.0.0, replaced by {@link #addEntry(long, long, long,
	 *             String, String, String, String, Date, Date, int, boolean)}
	 */
	@Deprecated
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

		return addEntry(
			userId, classNameId, classPK, title, content, url, type,
			displayDate, expirationDate, priority, alert);
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
	public void deleteEntries(long classNameId, long classPK)
		throws PortalException {

		List<AnnouncementsEntry> entries =
			announcementsEntryPersistence.findByC_C(classNameId, classPK);

		for (AnnouncementsEntry entry : entries) {
			deleteEntry(entry);
		}
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

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateEntry(long, String,
	 *             String, String, String, Date, Date, int)}
	 */
	@Deprecated
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

		return updateEntry(
			entryId, title, content, url, type, displayDate, expirationDate,
			priority);
	}

	@Override
	public AnnouncementsEntry updateEntry(
			long entryId, String title, String content, String url, String type,
			Date displayDate, Date expirationDate, int priority)
		throws PortalException {

		validate(title, content, url, displayDate, expirationDate);

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
			notifyUsers(entry, teamId, params, toName, toAddress, company);
		}
	}

	protected void notifyUsers(
			final AnnouncementsEntry entry, final long teamId,
			final LinkedHashMap<String, Object> params, final String toName,
			final String toAddress, final Company company)
		throws PortalException {

		int total = 0;

		if (teamId > 0) {
			total = userLocalService.getTeamUsersCount(teamId);
		}
		else {
			total = userLocalService.searchCount(
				company.getCompanyId(), null, WorkflowConstants.STATUS_APPROVED,
				params);
		}

		final IntervalActionProcessor<Void> intervalActionProcessor =
			new IntervalActionProcessor<>(total);

		intervalActionProcessor.setPerformIntervalActionMethod(
			new IntervalActionProcessor.PerformIntervalActionMethod<Void>() {

				@Override
				public Void performIntervalAction(int start, int end)
					throws PortalException {

					List<User> users = null;

					if (teamId > 0) {
						users = userLocalService.getTeamUsers(
							teamId, start, end);
					}
					else {
						users = userLocalService.search(
							company.getCompanyId(), null,
							WorkflowConstants.STATUS_APPROVED, params, start,
							end, (OrderByComparator<User>)null);
					}

					notifyUsers(
						users, entry, company.getLocale(), toAddress, toName);

					intervalActionProcessor.incrementStart(users.size());

					return null;
				}

			});

		intervalActionProcessor.performIntervalActions();
	}

	protected void notifyUsers(
			List<User> users, AnnouncementsEntry entry, Locale locale,
			String toAddress, String toName)
		throws PortalException {

		if (_log.isDebugEnabled()) {
			_log.debug("Notifying " + users.size() + " users");
		}

		Map<String, String> notifyUsersFullNames = new HashMap<>();

		for (User user : users) {
			AnnouncementsDelivery announcementsDelivery =
				announcementsDeliveryLocalService.getUserDelivery(
					user.getUserId(), entry.getType());

			if (announcementsDelivery.isEmail()) {
				notifyUsersFullNames.put(
					user.getEmailAddress(), user.getFullName());
			}

			if (announcementsDelivery.isSms()) {
				Contact contact = user.getContact();

				notifyUsersFullNames.put(
					contact.getSmsSn(), user.getFullName());
			}
		}

		if (notifyUsersFullNames.isEmpty()) {
			return;
		}

		Class<?> clazz = getClass();

		String body = ContentUtil.get(
			clazz.getClassLoader(), PropsValues.ANNOUNCEMENTS_EMAIL_BODY);

		String fromAddress = PrefsPropsUtil.getStringFromNames(
			entry.getCompanyId(), PropsKeys.ANNOUNCEMENTS_EMAIL_FROM_ADDRESS,
			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		String fromName = PrefsPropsUtil.getStringFromNames(
			entry.getCompanyId(), PropsKeys.ANNOUNCEMENTS_EMAIL_FROM_NAME,
			PropsKeys.ADMIN_EMAIL_FROM_NAME);
		String subject = ContentUtil.get(
			clazz.getClassLoader(), PropsValues.ANNOUNCEMENTS_EMAIL_SUBJECT);

		Company company = companyLocalService.getCompany(entry.getCompanyId());

		_sendNotificationEmail(
			fromAddress, fromName, toAddress, toName, subject, body, company,
			entry);

		for (String curToAddress : notifyUsersFullNames.keySet()) {
			String curToName = notifyUsersFullNames.get(toAddress);

			_sendNotificationEmail(
				fromAddress, fromName, curToAddress, curToName, subject, body,
				company, entry);
		}
	}

	protected void validate(
			String title, String content, String url, Date displayDate,
			Date expirationDate)
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

		if ((expirationDate != null) &&
			(expirationDate.before(new Date()) ||
			 ((displayDate != null) && expirationDate.before(displayDate)))) {

			throw new EntryExpirationDateException(
				"Expiration date " + expirationDate + " is in the past");
		}
	}

	@BeanReference(type = MailService.class)
	protected MailService mailService;

	private void _sendNotificationEmail(
			String fromAddress, String fromName, String toAddress,
			String toName, String subject, String body, Company company,
			AnnouncementsEntry entry)
		throws PortalException {

		String portalURL = company.getPortalURL(0);

		MailTemplateContextBuilder mailTemplateContextBuilder =
			MailTemplateFactoryUtil.createMailTemplateContextBuilder();

		mailTemplateContextBuilder.put(
			"[$COMPANY_ID$]", String.valueOf(company.getCompanyId()));
		mailTemplateContextBuilder.put("[$COMPANY_MX$]", company.getMx());
		mailTemplateContextBuilder.put("[$COMPANY_NAME$]", company.getName());
		mailTemplateContextBuilder.put("[$ENTRY_CONTENT$]", entry.getContent());
		mailTemplateContextBuilder.put(
			"[$ENTRY_ID$]", String.valueOf(entry.getEntryId()));
		mailTemplateContextBuilder.put("[$ENTRY_TITLE$]", entry.getTitle());
		mailTemplateContextBuilder.put(
			"[$ENTRY_TYPE$]",
			new EscapableLocalizableFunction(
				locale -> LanguageUtil.get(locale, entry.getType())));
		mailTemplateContextBuilder.put("[$ENTRY_URL$]", entry.getUrl());
		mailTemplateContextBuilder.put("[$FROM_ADDRESS$]", fromAddress);
		mailTemplateContextBuilder.put("[$FROM_NAME$]", fromName);
		mailTemplateContextBuilder.put("[$PORTAL_URL$]", portalURL);
		mailTemplateContextBuilder.put(
			"[$PORTLET_NAME$]",
			new EscapableLocalizableFunction(
				locale -> LanguageUtil.get(
					locale, entry.isAlert() ? "alert" : "announcement")));

		if (entry.getGroupId() > 0) {
			Group group = groupLocalService.getGroup(entry.getGroupId());

			mailTemplateContextBuilder.put(
				"[$SITE_NAME$]", group.getDescriptiveName());
		}

		mailTemplateContextBuilder.put("[$TO_ADDRESS$]", toAddress);
		mailTemplateContextBuilder.put("[$TO_NAME$]", toName);

		MailTemplateContext mailTemplateContext =
			mailTemplateContextBuilder.build();

		try {
			MailTemplate subjectTemplate =
				MailTemplateFactoryUtil.createMailTemplate(subject, false);

			MailTemplate bodyTemplate =
				MailTemplateFactoryUtil.createMailTemplate(body, true);

			User user = userLocalService.fetchUserByEmailAddress(
				entry.getCompanyId(), toAddress);

			Locale locale = LocaleUtil.getSiteDefault();

			if (user != null) {
				locale = user.getLocale();
			}

			MailMessage mailMessage = new MailMessage(
				new InternetAddress(fromAddress, fromName),
				new InternetAddress(toAddress, toName),
				subjectTemplate.renderAsString(locale, mailTemplateContext),
				bodyTemplate.renderAsString(locale, mailTemplateContext), true);

			mailMessage.setMessageId(
				PortalUtil.getMailId(
					company.getMx(), "announcements_entry",
					entry.getEntryId()));

			mailService.sendEmail(mailMessage);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	private static final long _ANNOUNCEMENTS_ENTRY_CHECK_INTERVAL =
		PropsValues.ANNOUNCEMENTS_ENTRY_CHECK_INTERVAL * Time.MINUTE;

	private static final Log _log = LogFactoryUtil.getLog(
		AnnouncementsEntryLocalServiceImpl.class);

	private Date _previousCheckDate;

}