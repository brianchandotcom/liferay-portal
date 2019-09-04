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

package com.liferay.chat.internal.jabber;

import com.liferay.chat.internal.configuration.ChatGroupServiceConfiguration;
import com.liferay.chat.jabber.Jabber;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 * @author Peter Fellwock
 */
@Component(
	configurationPid = "com.liferay.chat.configuration.ChatConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, enabled = false,
	immediate = true, service = JabberUtil.class
)
public class JabberUtil {

	public static void disconnect(long userId) {
		if (!_jabberEnabled) {
			return;
		}

		getJabber().disconnect(userId);
	}

	public static String getResource(String jabberId) {
		return getJabber().getResource(jabberId);
	}

	public static String getScreenName(String jabberId) {
		return getJabber().getScreenName(jabberId);
	}

	public static List<Object[]> getStatuses(
		long companyId, long userId, List<Object[]> buddies) {

		if (!_jabberEnabled) {
			return buddies;
		}

		return getJabber().getStatuses(companyId, userId, buddies);
	}

	public static void login(long userId, String password) {
		if (!_jabberEnabled) {
			return;
		}

		getJabber().login(userId, password);
	}

	public static void sendMessage(
		long fromUserId, long toUserId, String content) {

		if (!_jabberEnabled) {
			return;
		}

		getJabber().sendMessage(fromUserId, toUserId, content);
	}

	public static void updatePassword(long userId, String password) {
		if (!_jabberEnabled) {
			return;
		}

		getJabber().updatePassword(userId, password);
	}

	public static void updateStatus(long userId, int online) {
		if (!_jabberEnabled) {
			return;
		}

		getJabber().updateStatus(userId, online);
	}

	protected static Jabber getJabber() {
		return _jabber;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_chatGroupServiceConfiguration = ConfigurableUtil.createConfigurable(
			ChatGroupServiceConfiguration.class, properties);

		_jabberEnabled = _chatGroupServiceConfiguration.jabberEnabled();
	}

	@Reference(unbind = "-")
	protected void setJabber(Jabber jabber) {
		_jabber = jabber;
	}

	private static ChatGroupServiceConfiguration _chatGroupServiceConfiguration;
	private static Jabber _jabber;
	private static boolean _jabberEnabled;

}