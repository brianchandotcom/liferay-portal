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

package com.liferay.portal.verify;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ContactConstants;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ContactLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.impl.GroupImpl;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class VerifyUser extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyInactive();
		verifyNoContacts();
	}

	protected void verifyInactive() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = null;

			DB db = DBManagerUtil.getDB();

			if (db.getDBType() == DBType.MYSQL) {
				sb = new StringBundler(7);

				sb.append("update Group_ inner join User_ on ");
				sb.append("Group_.companyId = User_.companyId and ");
				sb.append("Group_.classPK = User_.userId set active_ = ");
				sb.append("[$FALSE$] where Group_.classNameId = ");
				sb.append(PortalUtil.getClassNameId(User.class));
				sb.append(" and User_.status = ");
				sb.append(WorkflowConstants.STATUS_INACTIVE);
			}
			else {
				sb = new StringBundler(9);

				sb.append("update Group_ set active_ = [$FALSE$] where ");
				sb.append("groupId in (select Group_.groupId from Group_ ");
				sb.append("inner join User_ on Group_.companyId = ");
				sb.append("User_.companyId and Group_.classPK = User_.userId ");
				sb.append("where Group_.classNameId = ");
				sb.append(PortalUtil.getClassNameId(User.class));
				sb.append(" and User_.status = ");
				sb.append(WorkflowConstants.STATUS_INACTIVE);
				sb.append(")");
			}

			runSQL(sb.toString());

			EntityCacheUtil.clearCache(GroupImpl.class);
			FinderCacheUtil.clearCache(GroupImpl.class.getName());
		}
	}

	protected void verifyNoContacts() throws PortalException {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<User> users = UserLocalServiceUtil.getNoContacts();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Processing " + users.size() + " users with no contacts");
			}

			for (User user : users) {
				if (_log.isDebugEnabled()) {
					_log.debug("Creating contact for user " + user.getUserId());
				}

				long contactId = CounterLocalServiceUtil.increment();

				Contact contact = ContactLocalServiceUtil.createContact(
					contactId);

				Company company = CompanyLocalServiceUtil.getCompanyById(
					user.getCompanyId());

				contact.setCompanyId(user.getCompanyId());
				contact.setUserId(user.getUserId());
				contact.setUserName(StringPool.BLANK);
				contact.setAccountId(company.getAccountId());
				contact.setParentContactId(
					ContactConstants.DEFAULT_PARENT_CONTACT_ID);
				contact.setFirstName(user.getFirstName());
				contact.setMiddleName(user.getMiddleName());
				contact.setLastName(user.getLastName());
				contact.setPrefixId(0);
				contact.setSuffixId(0);
				contact.setJobTitle(user.getJobTitle());

				ContactLocalServiceUtil.updateContact(contact);

				user.setContactId(contactId);

				UserLocalServiceUtil.updateUser(user);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Contacts verified for users");
			}

			users = UserLocalServiceUtil.getNoGroups();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Processing " + users.size() + " users with no groups");
			}

			for (User user : users) {
				if (_log.isDebugEnabled()) {
					_log.debug("Creating group for user " + user.getUserId());
				}

				GroupLocalServiceUtil.addGroup(
					user.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
					User.class.getName(), user.getUserId(),
					GroupConstants.DEFAULT_LIVE_GROUP_ID,
					(Map<Locale, String>)null, null, 0, true,
					GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
					StringPool.SLASH + user.getScreenName(), false, true, null);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Groups verified for users");
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(VerifyUser.class);

}