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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.ContactConstants;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ContactLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class VerifyUser extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
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

			Contact contact = ContactLocalServiceUtil.createContact(contactId);

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
			_log.debug("Processing " + users.size() + " users with no groups");
		}

		for (User user : users) {
			if (_log.isDebugEnabled()) {
				_log.debug("Creating group for user " + user.getUserId());
			}

			GroupLocalServiceUtil.addGroup(
				user.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
				User.class.getName(), user.getUserId(),
				GroupConstants.DEFAULT_LIVE_GROUP_ID, (Map<Locale, String>)null,
				null, 0, true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
				StringPool.SLASH + user.getScreenName(), false, true, null);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Groups verified for users");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(VerifyUser.class);

}