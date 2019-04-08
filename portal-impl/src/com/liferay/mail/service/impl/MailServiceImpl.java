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

package com.liferay.mail.service.impl;

import com.liferay.mail.kernel.model.Account;
import com.liferay.mail.kernel.model.Filter;
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.mail.kernel.util.Hook;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Session;

/**
 * @author Brian Wing Shun Chan
 */
public class MailServiceImpl implements IdentifiableOSGiService, MailService {

	@Override
	public void addForward(
		long companyId, long userId, List<Filter> filters,
		List<String> emailAddresses, boolean leaveCopy) {

		if (_log.isDebugEnabled()) {
			_log.debug("addForward");
		}

		MethodHandler methodHandler = new MethodHandler(
			_addForwardMethodKey, companyId, userId, filters, emailAddresses,
			leaveCopy);

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodHandler);
	}

	@Override
	public void addUser(
		long companyId, long userId, String password, String firstName,
		String middleName, String lastName, String emailAddress) {

		if (_log.isDebugEnabled()) {
			_log.debug("addUser");
		}

		MethodHandler methodHandler = new MethodHandler(
			_addUserMethodKey, companyId, userId, password, firstName,
			middleName, lastName, emailAddress);

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodHandler);
	}

	@Override
	public void addVacationMessage(
		long companyId, long userId, String emailAddress,
		String vacationMessage) {

		if (_log.isDebugEnabled()) {
			_log.debug("addVacationMessage");
		}

		MethodHandler methodHandler = new MethodHandler(
			_addVacationMessageMethodKey, companyId, userId, emailAddress,
			vacationMessage);

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodHandler);
	}

	@Clusterable
	@Override
	public void clearSession() {
		_session = null;
	}

	@Override
	public void deleteEmailAddress(long companyId, long userId) {
		if (_log.isDebugEnabled()) {
			_log.debug("deleteEmailAddress");
		}

		MethodHandler methodHandler = new MethodHandler(
			_deleteEmailAddressMethodKey, companyId, userId);

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodHandler);
	}

	@Override
	public void deleteUser(long companyId, long userId) {
		if (_log.isDebugEnabled()) {
			_log.debug("deleteUser");
		}

		MethodHandler methodHandler = new MethodHandler(
			_deleteUserMethodKey, companyId, userId);

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodHandler);
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return MailService.class.getName();
	}

	@Override
	public Session getSession() {
		if (_session != null) {
			return _session;
		}

		Session session = InfrastructureUtil.getMailSession();

		if (!PrefsPropsUtil.getBoolean(
				PropsKeys.MAIL_SESSION_MAIL, PropsValues.MAIL_SESSION_MAIL)) {

			_session = session;

			return _session;
		}

		String advancedPropertiesString = PrefsPropsUtil.getString(
			PropsKeys.MAIL_SESSION_MAIL_ADVANCED_PROPERTIES,
			PropsValues.MAIL_SESSION_MAIL_ADVANCED_PROPERTIES);
		String pop3Host = PrefsPropsUtil.getString(
			PropsKeys.MAIL_SESSION_MAIL_POP3_HOST,
			PropsValues.MAIL_SESSION_MAIL_POP3_HOST);
		String pop3Password = PrefsPropsUtil.getString(
			PropsKeys.MAIL_SESSION_MAIL_POP3_PASSWORD,
			PropsValues.MAIL_SESSION_MAIL_POP3_PASSWORD);
		int pop3Port = PrefsPropsUtil.getInteger(
			PropsKeys.MAIL_SESSION_MAIL_POP3_PORT,
			PropsValues.MAIL_SESSION_MAIL_POP3_PORT);
		String pop3User = PrefsPropsUtil.getString(
			PropsKeys.MAIL_SESSION_MAIL_POP3_USER,
			PropsValues.MAIL_SESSION_MAIL_POP3_USER);
		String smtpHost = PrefsPropsUtil.getString(
			PropsKeys.MAIL_SESSION_MAIL_SMTP_HOST,
			PropsValues.MAIL_SESSION_MAIL_SMTP_HOST);
		String smtpPassword = PrefsPropsUtil.getString(
			PropsKeys.MAIL_SESSION_MAIL_SMTP_PASSWORD,
			PropsValues.MAIL_SESSION_MAIL_SMTP_PASSWORD);
		int smtpPort = PrefsPropsUtil.getInteger(
			PropsKeys.MAIL_SESSION_MAIL_SMTP_PORT,
			PropsValues.MAIL_SESSION_MAIL_SMTP_PORT);
		String smtpUser = PrefsPropsUtil.getString(
			PropsKeys.MAIL_SESSION_MAIL_SMTP_USER,
			PropsValues.MAIL_SESSION_MAIL_SMTP_USER);
		String storeProtocol = PrefsPropsUtil.getString(
			PropsKeys.MAIL_SESSION_MAIL_STORE_PROTOCOL,
			PropsValues.MAIL_SESSION_MAIL_STORE_PROTOCOL);
		String transportProtocol = PrefsPropsUtil.getString(
			PropsKeys.MAIL_SESSION_MAIL_TRANSPORT_PROTOCOL,
			PropsValues.MAIL_SESSION_MAIL_TRANSPORT_PROTOCOL);

		Properties properties = session.getProperties();

		// Incoming

		if (!storeProtocol.equals(Account.PROTOCOL_POPS)) {
			storeProtocol = Account.PROTOCOL_POP;
		}

		properties.setProperty("mail.store.protocol", storeProtocol);

		String storePrefix = "mail." + storeProtocol + ".";

		properties.setProperty(storePrefix + "host", pop3Host);
		properties.setProperty(storePrefix + "password", pop3Password);
		properties.setProperty(storePrefix + "port", String.valueOf(pop3Port));
		properties.setProperty(storePrefix + "user", pop3User);

		// Outgoing

		if (!transportProtocol.equals(Account.PROTOCOL_SMTPS)) {
			transportProtocol = Account.PROTOCOL_SMTP;
		}

		properties.setProperty("mail.transport.protocol", transportProtocol);

		String transportPrefix = "mail." + transportProtocol + ".";

		boolean smtpAuth = false;

		if (Validator.isNotNull(smtpPassword) ||
			Validator.isNotNull(smtpUser)) {

			smtpAuth = true;
		}

		properties.setProperty(
			transportPrefix + "auth", String.valueOf(smtpAuth));
		properties.setProperty(transportPrefix + "host", smtpHost);
		properties.setProperty(transportPrefix + "password", smtpPassword);
		properties.setProperty(
			transportPrefix + "port", String.valueOf(smtpPort));
		properties.setProperty(transportPrefix + "user", smtpUser);

		// Advanced

		try {
			if (Validator.isNotNull(advancedPropertiesString)) {
				Properties advancedProperties = PropertiesUtil.load(
					advancedPropertiesString);

				for (Map.Entry<Object, Object> entry :
						advancedProperties.entrySet()) {

					String key = (String)entry.getKey();
					String value = (String)entry.getValue();

					properties.setProperty(key, value);
				}
			}
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe, ioe);
			}
		}

		_session = Session.getInstance(properties);

		return _session;
	}

	@Override
	public void sendEmail(MailMessage mailMessage) {
		if (_log.isDebugEnabled()) {
			_log.debug("sendEmail");
		}

		MessageBusUtil.sendMessage(DestinationNames.MAIL, mailMessage);
	}

	@Override
	public void updateBlocked(
		long companyId, long userId, List<String> blocked) {

		if (_log.isDebugEnabled()) {
			_log.debug("updateBlocked");
		}

		MethodHandler methodHandler = new MethodHandler(
			_updateBlockedMethodKey, companyId, userId, blocked);

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodHandler);
	}

	@Override
	public void updateEmailAddress(
		long companyId, long userId, String emailAddress) {

		if (_log.isDebugEnabled()) {
			_log.debug("updateEmailAddress");
		}

		MethodHandler methodHandler = new MethodHandler(
			_updateEmailAddressMethodKey, companyId, userId, emailAddress);

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodHandler);
	}

	@Override
	public void updatePassword(long companyId, long userId, String password) {
		if (_log.isDebugEnabled()) {
			_log.debug("updatePassword");
		}

		MethodHandler methodHandler = new MethodHandler(
			_updatePasswordMethodKey, companyId, userId, password);

		MessageBusUtil.sendMessage(DestinationNames.MAIL, methodHandler);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MailServiceImpl.class);

	private static final MethodKey _addForwardMethodKey = new MethodKey(
		Hook.class, "addForward", long.class, long.class, List.class,
		List.class, boolean.class);
	private static final MethodKey _addUserMethodKey = new MethodKey(
		Hook.class, "addUser", long.class, long.class, String.class,
		String.class, String.class, String.class, String.class);
	private static final MethodKey _addVacationMessageMethodKey = new MethodKey(
		Hook.class, "addVacationMessage", long.class, long.class, String.class,
		String.class);
	private static final MethodKey _deleteEmailAddressMethodKey = new MethodKey(
		Hook.class, "deleteEmailAddress", long.class, long.class);
	private static final MethodKey _deleteUserMethodKey = new MethodKey(
		Hook.class, "deleteUser", long.class, long.class);
	private static final MethodKey _updateBlockedMethodKey = new MethodKey(
		Hook.class, "updateBlocked", long.class, long.class, List.class);
	private static final MethodKey _updateEmailAddressMethodKey = new MethodKey(
		Hook.class, "updateEmailAddress", long.class, long.class, String.class);
	private static final MethodKey _updatePasswordMethodKey = new MethodKey(
		Hook.class, "updatePassword", long.class, long.class, String.class);

	private Session _session;

}