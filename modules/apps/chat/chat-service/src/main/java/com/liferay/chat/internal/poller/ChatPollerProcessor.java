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

package com.liferay.chat.internal.poller;

import com.liferay.chat.constants.ChatPortletKeys;
import com.liferay.chat.internal.configuration.ChatGroupServiceConfiguration;
import com.liferay.chat.internal.util.ChatConstants;
import com.liferay.chat.model.Entry;
import com.liferay.chat.model.Status;
import com.liferay.chat.service.EntryLocalServiceUtil;
import com.liferay.chat.service.StatusLocalServiceUtil;
import com.liferay.chat.util.BuddyFinder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.NoSuchLayoutSetException;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ContactConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.poller.BasePollerProcessor;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.poller.PollerRequest;
import com.liferay.portal.kernel.poller.PollerResponse;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Time;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Fellwock
 */
@Component(
	configurationPid = "com.liferay.chat.configuration.ChatConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, enabled = false,
	immediate = true, property = "javax.portlet.name=" + ChatPortletKeys.CHAT,
	service = PollerProcessor.class
)
public class ChatPollerProcessor extends BasePollerProcessor {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_chatGroupServiceConfiguration = ConfigurableUtil.createConfigurable(
			ChatGroupServiceConfiguration.class, properties);
	}

	protected void addEntry(PollerRequest pollerRequest) throws Exception {
		long toUserId = getLong(pollerRequest, "toUserId");

		if (toUserId > 0) {
			String content = getString(pollerRequest, "content");

			EntryLocalServiceUtil.addEntry(
				pollerRequest.getTimestamp(), pollerRequest.getUserId(),
				toUserId, content);
		}
	}

	@Override
	protected PollerResponse doReceive(PollerRequest pollerRequest)
		throws Exception {

		PollerResponse pollerResponse = pollerRequest.createPollerResponse();

		getBuddies(pollerRequest, pollerResponse);
		getEntries(pollerRequest, pollerResponse);

		return pollerResponse;
	}

	@Override
	protected void doSend(PollerRequest pollerRequest) throws Exception {
		if (pollerRequest.isStartPolling()) {
			_processedEntryIds.clear();
		}

		addEntry(pollerRequest);
		updateStatus(pollerRequest);
	}

	protected void getBuddies(
			PollerRequest pollerRequest, PollerResponse pollerResponse)
		throws Exception {

		List<Object[]> buddies = _buddyFinder.getBuddies(
			pollerRequest.getCompanyId(), pollerRequest.getUserId());

		JSONArray buddiesJSONArray = JSONFactoryUtil.createJSONArray();

		for (Object[] buddy : buddies) {
			boolean awake = (Boolean)buddy[0];
			String firstName = (String)buddy[1];
			long groupId = (Long)buddy[2];
			String lastName = (String)buddy[3];
			boolean male = (Boolean)buddy[4];
			String middleName = (String)buddy[5];
			long portraitId = (Long)buddy[6];
			String screenName = (String)buddy[7];
			long userId = (Long)buddy[8];
			String userUuid = (String)buddy[9];

			Status buddyStatus = StatusLocalServiceUtil.getUserStatus(userId);

			awake = buddyStatus.isAwake();

			JSONObject curUserJSONObject = JSONUtil.put("awake", awake);

			String displayURL = StringPool.BLANK;

			try {
				LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
					groupId, false);

				if (layoutSet.getPageCount() > 0) {
					displayURL = _portal.getLayoutSetDisplayURL(
						layoutSet, false);

					displayURL = _http.removeDomain(displayURL);
				}
			}
			catch (NoSuchLayoutSetException nslse) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(nslse, nslse);
				}
			}

			curUserJSONObject.put("displayURL", displayURL);

			String fullName = ContactConstants.getFullName(
				firstName, middleName, lastName);

			curUserJSONObject.put(
				"fullName", fullName
			).put(
				"groupId", groupId
			).put(
				"portraitId", portraitId
			);

			String portraitURL = UserConstants.getPortraitURL(
				StringPool.BLANK, male, portraitId, userUuid);

			curUserJSONObject.put(
				"portraitURL", portraitURL
			).put(
				"screenName", screenName
			);

			String statusMessage = buddyStatus.getMessage();

			curUserJSONObject.put(
				"statusMessage", statusMessage
			).put(
				"userId", userId
			);

			buddiesJSONArray.put(curUserJSONObject);
		}

		pollerResponse.setParameter("buddies", buddiesJSONArray);
	}

	protected void getEntries(
			PollerRequest pollerRequest, PollerResponse pollerResponse)
		throws Exception {

		JSONArray entriesJSONArray = JSONFactoryUtil.createJSONArray();

		boolean hasProcessedEntry = false;

		Status status = StatusLocalServiceUtil.getUserStatus(
			pollerRequest.getUserId());

		long createDate = 0;

		if (pollerRequest.isInitialRequest()) {
			createDate = status.getModifiedDate() - Time.DAY;
		}

		List<Entry> entries = EntryLocalServiceUtil.getNewEntries(
			pollerRequest.getUserId(), createDate, 0,
			_chatGroupServiceConfiguration.buddyListMaxBuddies());

		entries = ListUtil.copy(entries);

		Collections.reverse(entries);

		for (Entry entry : entries) {
			hasProcessedEntry = _processedEntryIds.contains(entry.getEntryId());

			_processedEntryIds.add(entry.getEntryId());

			JSONObject entryJSONObject = JSONUtil.put(
				"createDate", entry.getCreateDate()
			).put(
				"entryId", entry.getEntryId()
			).put(
				"fromUserId", entry.getFromUserId()
			);

			if (entry.getFromUserId() != pollerRequest.getUserId()) {
				try {
					User fromUser = _userLocalService.getUserById(
						entry.getFromUserId());

					entryJSONObject.put(
						"fromFullName", fromUser.getFullName()
					).put(
						"fromPortraitId", fromUser.getPortraitId()
					);
				}
				catch (NoSuchUserException nsue) {

					// LPS-52675

					if (_log.isDebugEnabled()) {
						_log.debug(nsue, nsue);
					}

					continue;
				}
			}

			entryJSONObject.put(
				"content", HtmlUtil.escape(entry.getContent())
			).put(
				"flag", entry.getFlag()
			).put(
				"toUserId", entry.getToUserId()
			);

			entriesJSONArray.put(entryJSONObject);
		}

		pollerResponse.setParameter("entries", entriesJSONArray);

		if (!entries.isEmpty() && !hasProcessedEntry) {
			pollerResponse.setParameter(
				PollerResponse.POLLER_HINT_HIGH_CONNECTIVITY,
				Boolean.TRUE.toString());
		}

		boolean updatePresence = getBoolean(pollerRequest, "updatePresence");

		if (updatePresence) {
		}
		else if (!entries.isEmpty()) {
			updatePresence = true;
		}
		else {
			long onlineTimestamp =
				status.getModifiedDate() + ChatConstants.ONLINE_DELTA -
					ChatConstants.MAX_POLL_LATENCY;

			if (onlineTimestamp < pollerRequest.getTimestamp()) {
				updatePresence = true;
			}
		}

		if (updatePresence) {
			StatusLocalServiceUtil.updateStatus(
				pollerRequest.getUserId(), pollerRequest.getTimestamp());
		}
	}

	protected void updateStatus(PollerRequest pollerRequest) throws Exception {
		int online = getInteger(pollerRequest, "online");
		int awake = getInteger(pollerRequest, "awake");
		String activePanelIds = getString(pollerRequest, "activePanelIds");
		String statusMessage = getString(pollerRequest, "statusMessage");
		int playSound = getInteger(pollerRequest, "playSound");

		if ((online != -1) || (awake != -1) || (activePanelIds != null) ||
			(statusMessage != null) || (playSound != -1)) {

			StatusLocalServiceUtil.updateStatus(
				pollerRequest.getUserId(), -1, online, awake, activePanelIds,
				statusMessage, playSound);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ChatPollerProcessor.class);

	@Reference
	private BuddyFinder _buddyFinder;

	private ChatGroupServiceConfiguration _chatGroupServiceConfiguration;

	@Reference
	private Http _http;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private Portal _portal;

	private final Set<Long> _processedEntryIds = new HashSet<>();

	@Reference
	private UserLocalService _userLocalService;

}