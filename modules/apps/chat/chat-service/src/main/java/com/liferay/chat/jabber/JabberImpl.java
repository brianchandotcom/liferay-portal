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

package com.liferay.chat.jabber;

import com.liferay.chat.configuration.ChatGroupServiceConfiguration;
import com.liferay.chat.model.Status;
import com.liferay.chat.service.StatusLocalServiceUtil;
import com.liferay.chat.util.comparator.BuddyComparator;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ContactConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 */
@Component(
	configurationPid = "ccom.liferay.chat.configuration.ChatConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = Jabber.class
)
public class JabberImpl implements Jabber {

	@Override
	public void disconnect(long userId) {
		Connection connection = getConnection(userId);

		if (connection == null) {
			return;
		}

		connection.disconnect();

		_connections.remove(userId);

		_onlineUserIds.remove(userId);
	}

	@Override
	public String getResource(String jabberId) {
		String resource = StringUtil.extractLast(jabberId, StringPool.AT);

		resource = StringUtil.extractLast(resource, StringPool.SLASH);

		if (resource == null) {
			return StringPool.BLANK;
		}

		return resource;
	}

	@Override
	public String getScreenName(String jabberId) {
		return StringUtil.extractFirst(jabberId, StringPool.AT);
	}

	@Override
	public List<Object[]> getStatuses(
		long companyId, long userId, List<Object[]> buddies) {

		try {
			Connection connection = getConnection(userId);

			if (connection == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("User " + userId + " is not connected to Jabber");
				}

				return buddies;
			}

			List<Object[]> jabberBuddies = new ArrayList<>();

			jabberBuddies.addAll(buddies);

			Roster roster = connection.getRoster();

			Collection<RosterEntry> rosterEntries = roster.getEntries();

			if (_chatGroupServiceConfiguration.jabberImportUserEnabled()) {
				for (Object[] buddy : buddies) {
					String firstName = (String)buddy[1];
					String lastName = (String)buddy[3];
					String middleName = (String)buddy[5];
					String screenName = (String)buddy[7];

					String fullName = ContactConstants.getFullName(
						firstName, middleName, lastName);

					String jabberId = getFullJabberId(screenName);

					if (!roster.contains(jabberId)) {
						roster.createEntry(jabberId, fullName, null);
					}
				}
			}

			BuddyComparator buddyComparator = new BuddyComparator(true);

			for (RosterEntry rosterEntry : rosterEntries) {
				Presence presence = roster.getPresence(rosterEntry.getUser());

				if (!presence.isAvailable()) {
					continue;
				}

				User user = _userLocalService.getUserByScreenName(
					companyId, getScreenName(rosterEntry.getUser()));

				Object[] jabberBuddy = new Object[10];

				jabberBuddy[0] = true;
				jabberBuddy[1] = user.getFirstName();
				jabberBuddy[2] = user.getGroupId();
				jabberBuddy[3] = user.getLastName();
				jabberBuddy[4] = user.isMale();
				jabberBuddy[5] = user.getMiddleName();
				jabberBuddy[6] = user.getPortraitId();
				jabberBuddy[7] = user.getScreenName();
				jabberBuddy[8] = user.getUserId();
				jabberBuddy[9] = user.getUserUuid();

				if (Collections.binarySearch(
						jabberBuddies, jabberBuddy, buddyComparator) < 0) {

					jabberBuddies.add(jabberBuddy);
				}
			}

			Collections.sort(jabberBuddies, buddyComparator);

			return jabberBuddies;
		}
		catch (Exception e) {
			_log.error("Unable to get Jabber buddies", e);

			return buddies;
		}
	}

	@Override
	public void login(long userId, String password) {
		try {
			connect(userId, password);
		}
		catch (XMPPException xmppe1) {
			String message1 = xmppe1.getMessage();

			if (Validator.isNotNull(message1) &&
				message1.contains("not-authorized")) {

				if (!_chatGroupServiceConfiguration.jabberImportUserEnabled()) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"User " + userId + " cannot connect to Jabber");
					}

					return;
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Importing user " + userId +
							" because he cannot connect to Jabber");
				}

				try {
					importUser(userId, password);

					connect(userId, password);
				}
				catch (XMPPException xmppe2) {
					String message2 = xmppe2.getMessage();

					if (message2.contains("conflict(409)")) {
						_log.error(
							"User " + userId + " already exists but password " +
								"is not synchronized with Jabber");
					}
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void sendMessage(long fromUserId, long toUserId, String content) {
		try {
			if (Validator.isNull(content)) {
				return;
			}

			Connection connection = getConnection(fromUserId);

			if (connection == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"User " + fromUserId + " is not connected to Jabber" +
							" and cannot send messages");
				}

				return;
			}

			User toUser = _userLocalService.getUser(toUserId);

			Roster roster = connection.getRoster();

			String jabberId = getJabberId(toUser.getScreenName());

			if (!roster.contains(jabberId)) {
				return;
			}

			Iterator<Presence> presences = roster.getPresences(jabberId);

			while (presences.hasNext()) {
				Presence presence = presences.next();

				String from = presence.getFrom();

				String resource = getResource(from);

				if (StringUtil.equalsIgnoreCase(
						resource,
						_chatGroupServiceConfiguration.jabberResource())) {

					continue;
				}

				ChatManager chatManager = connection.getChatManager();

				MessageListener messageListener = new JabberMessageListener(
					toUser.getCompanyId(), fromUserId);

				Chat chat = chatManager.createChat(from, messageListener);

				try {
					chat.sendMessage(content);
				}
				catch (XMPPException xmppe) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"User " + fromUserId + " could not send message",
							xmppe);
					}
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void updatePassword(long userId, String password) {
		if (!_chatGroupServiceConfiguration.jabberImportUserEnabled() ||
			(password == null)) {

			return;
		}

		Connection connection = getConnection(userId);

		if (connection == null) {
			return;
		}

		try {
			AccountManager accountManager = connection.getAccountManager();

			accountManager.changePassword(password);
		}
		catch (XMPPException xmppe) {
			_log.error("Unable to update user " + userId + " password", xmppe);
		}
	}

	@Override
	public void updateStatus(long userId, int online) {
		updateStatus(userId, online, null);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_chatGroupServiceConfiguration = ConfigurableUtil.createConfigurable(
			ChatGroupServiceConfiguration.class, properties);
	}

	protected Connection connect() throws Exception {
		long userId = -1;
		String password = null;

		return connect(userId, password);
	}

	protected Connection connect(long userId, String password)
		throws Exception {

		Connection connection = getConnection(userId);

		if (connection != null) {
			return connection;
		}

		connection = new XMPPConnection(getConnectionConfiguration());

		connection.connect();

		if (userId < 0) {
			return connection;
		}

		User user = _userLocalService.getUserById(userId);

		connection.login(
			user.getScreenName(), password,
			_chatGroupServiceConfiguration.jabberResource());

		Status status = StatusLocalServiceUtil.getUserStatus(userId);

		if (status.getOnline()) {
			updateStatus(userId, 1, connection);
		}

		ChatManager chatManager = connection.getChatManager();

		ChatManagerListener chatMessageListener = new JabberChatManagerListener(
			user.getCompanyId(), userId);

		chatManager.addChatListener(chatMessageListener);

		_connections.put(userId, connection);

		return connection;
	}

	protected Connection getConnection(long userId) {
		return _connections.get(userId);
	}

	protected ConnectionConfiguration getConnectionConfiguration()
		throws UnknownHostException {

		if (_connectionConfiguration != null) {
			return _connectionConfiguration;
		}

		String jabberHost = _chatGroupServiceConfiguration.jabberHost();

		if (!Validator.isIPAddress(jabberHost)) {
			InetAddress inetAddress = InetAddress.getByName(jabberHost);

			jabberHost = inetAddress.getHostAddress();
		}

		_connectionConfiguration = new ConnectionConfiguration(
			jabberHost, _chatGroupServiceConfiguration.jabberPort(),
			_chatGroupServiceConfiguration.jabberServiceName());

		_connectionConfiguration.setSendPresence(false);

		SmackConfiguration.setLocalSocks5ProxyEnabled(
			_chatGroupServiceConfiguration.jabberSock5ProxyEnabled());
		SmackConfiguration.setLocalSocks5ProxyPort(
			_chatGroupServiceConfiguration.jabberSock5ProxyPort());

		return _connectionConfiguration;
	}

	protected String getFullJabberId(String screenName) {
		String jabberId = getJabberId(screenName);

		return jabberId.concat(StringPool.SLASH).concat(
			_chatGroupServiceConfiguration.jabberResource());
	}

	protected String getJabberId(String screenName) {
		return screenName.concat(StringPool.AT).concat(
			_chatGroupServiceConfiguration.jabberResource());
	}

	protected void importUser(long userId, String password) throws Exception {
		Connection connection = connect();

		AccountManager accountManager = connection.getAccountManager();

		if (!accountManager.supportsAccountCreation()) {
			_log.error("Jabber server does not support account creation");

			return;
		}

		User user = _userLocalService.getUserById(userId);

		Map<String, String> attributes = new HashMap<>();

		attributes.put("email", user.getEmailAddress());
		attributes.put("first", user.getFirstName());
		attributes.put("last", user.getLastName());
		attributes.put("name", user.getFullName());

		accountManager.createAccount(
			user.getScreenName(), password, attributes);
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	protected void updateStatus(
		long userId, int online, Connection connection) {

		try {
			if (connection == null) {
				connection = getConnection(userId);

				if (connection == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"User " + userId + " is not connected to Jabber");
					}

					return;
				}
			}

			if ((online == 1) && !_onlineUserIds.contains(userId)) {
				Presence presence = new Presence(Presence.Type.available);

				connection.sendPacket(presence);

				_onlineUserIds.add(userId);
			}
			else if ((online == 0) && _onlineUserIds.contains(userId)) {
				Presence presence = new Presence(Presence.Type.unavailable);

				connection.sendPacket(presence);

				_onlineUserIds.remove(userId);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(JabberImpl.class);

	private ChatGroupServiceConfiguration _chatGroupServiceConfiguration;
	private ConnectionConfiguration _connectionConfiguration;
	private final Map<Long, Connection> _connections = new HashMap<>();
	private final Set<Long> _onlineUserIds = new HashSet<>();
	private UserLocalService _userLocalService;

}