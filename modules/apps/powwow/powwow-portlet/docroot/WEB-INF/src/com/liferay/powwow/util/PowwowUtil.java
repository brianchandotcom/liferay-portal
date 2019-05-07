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

package com.liferay.powwow.util;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.petra.content.ContentUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.NotificationEventFactoryUtil;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.powwow.model.PowwowMeeting;
import com.liferay.powwow.model.PowwowParticipant;
import com.liferay.powwow.model.PowwowParticipantConstants;
import com.liferay.powwow.provider.PowwowServiceProviderUtil;
import com.liferay.powwow.service.PowwowMeetingLocalServiceUtil;
import com.liferay.powwow.service.PowwowParticipantLocalServiceUtil;

import java.text.Format;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shinn Lok
 */
public class PowwowUtil {

	public static byte[] exportPowwowMeetingCalendar(long powwowMeetingId)
		throws Exception {

		PowwowMeeting powwowMeeting =
			PowwowMeetingLocalServiceUtil.getPowwowMeeting(powwowMeetingId);

		String calendarBookingString =
			CalendarBookingLocalServiceUtil.exportCalendarBooking(
				powwowMeeting.getCalendarBookingId(),
				CalendarUtil.ICAL_EXTENSION);

		return calendarBookingString.getBytes();
	}

	public static String getHash(long powwowMeetingId) throws Exception {
		PowwowMeeting powwowMeeting =
			PowwowMeetingLocalServiceUtil.getPowwowMeeting(powwowMeetingId);

		return DigesterUtil.digestHex(
			Digester.SHA_1, String.valueOf(powwowMeetingId),
			String.valueOf(powwowMeeting.getUserId()),
			String.valueOf(powwowMeeting.getUserUuid()));
	}

	public static String getInvitationURL(
			long powwowMeetingId, PowwowParticipant powwowParticipant,
			HttpServletRequest httpServletRequest)
		throws Exception {

		StringBundler sb = new StringBundler(9);

		Layout layout = null;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = GroupLocalServiceUtil.fetchGroup(
			themeDisplay.getCompanyId(),
			PortletPropsValues.POWWOW_INVITATION_GROUP_NAME);

		if (group != null) {
			layout = LayoutLocalServiceUtil.fetchLayoutByFriendlyURL(
				group.getGroupId(),
				PortletPropsValues.POWWOW_INVITATION_LAYOUT_PRIVATE,
				PortletPropsValues.POWWOW_INVITATION_LAYOUT_FRIENDLY_URL);
		}

		if (layout == null) {
			group = GroupLocalServiceUtil.getGroup(
				themeDisplay.getCompanyId(), GroupConstants.GUEST);

			layout = LayoutLocalServiceUtil.getLayout(
				group.getDefaultPublicPlid());
		}

		sb.append(PortalUtil.getLayoutURL(layout, themeDisplay));

		sb.append(Portal.FRIENDLY_URL_SEPARATOR);
		sb.append("meetings");
		sb.append(StringPool.SLASH);
		sb.append(powwowMeetingId);
		sb.append(StringPool.SLASH);

		long powwowParticipantId = 0;

		if (powwowParticipant != null) {
			powwowParticipantId = powwowParticipant.getPowwowParticipantId();
		}

		sb.append(powwowParticipantId);

		sb.append(StringPool.SLASH);

		String hash = null;

		if (powwowMeetingId > 0) {
			hash = getHash(powwowMeetingId);
		}

		sb.append(hash);

		return sb.toString();
	}

	public static Sort[] getPowwowMeetingSorts(
		String orderByCol, String orderByType) {

		if (Validator.isNull(orderByCol) || Validator.isNull(orderByType)) {
			return SortFactoryUtil.getDefaultSorts();
		}

		boolean reverse = true;

		if (orderByType.equals("asc")) {
			reverse = false;
		}

		if (orderByCol.equals("created-by")) {
			return new Sort[] {
				SortFactoryUtil.create("creatorName", Sort.STRING_TYPE, reverse)
			};
		}
		else if (orderByCol.equals("date")) {
			return new Sort[] {
				SortFactoryUtil.create("startTime", Sort.LONG_TYPE, reverse)
			};
		}
		else if (orderByCol.equals("name")) {
			return new Sort[] {
				SortFactoryUtil.create(Field.NAME, Sort.STRING_TYPE, reverse)
			};
		}

		return SortFactoryUtil.getDefaultSorts();
	}

	public static List<PowwowParticipant> getPowwowParticipants(
			ActionRequest actionRequest)
		throws Exception {

		List<PowwowParticipant> powwowParticipants = new ArrayList<>();

		long powwowMeetingId = ParamUtil.getLong(
			actionRequest, "powwowMeetingId");

		String participantsJSON = ParamUtil.getString(
			actionRequest, "participantsJSON");

		JSONArray participantsJSONArray = JSONFactoryUtil.createJSONArray(
			participantsJSON);

		for (int i = 0; i < participantsJSONArray.length(); i++) {
			JSONObject participantJSONObject =
				participantsJSONArray.getJSONObject(i);

			String name = participantJSONObject.getString("name");
			String emailAddress = participantJSONObject.getString(
				"emailAddress");

			if (Validator.isNull(name) && Validator.isNull(emailAddress)) {
				continue;
			}

			long participantUserId = participantJSONObject.getLong(
				"participantUserId");
			int type = participantJSONObject.getInt("type");

			PowwowParticipant powwowParticipant = _getPowwowParticipant(
				powwowMeetingId, name, participantUserId, emailAddress, type);

			powwowParticipants.add(powwowParticipant);
		}

		return powwowParticipants;
	}

	public static PowwowSubscriptionSender getPowwowSubscriptionSender(
			long powwowMeetingId, ServiceContext serviceContext)
		throws Exception {

		PowwowSubscriptionSender powwowSubscriptionSender =
			new PowwowSubscriptionSender();

		PowwowMeeting powwowMeeting =
			PowwowMeetingLocalServiceUtil.getPowwowMeeting(powwowMeetingId);

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.getPreferences(
				powwowMeeting.getCompanyId(), powwowMeeting.getGroupId(),
				PowwowPortletKeys.PREFS_OWNER_TYPE_GROUP,
				LayoutConstants.DEFAULT_PLID,
				PowwowPortletKeys.POWWOW_MEETINGS);

		if (powwowMeeting.getCalendarBookingId() > 0) {
			powwowSubscriptionSender.addFileAttachment(
				FileUtil.createTempFile(
					exportPowwowMeetingCalendar(powwowMeetingId)),
				"invite.ics");
		}

		powwowSubscriptionSender.setCompanyId(powwowMeeting.getCompanyId());

		String startDateString = StringPool.BLANK;
		String startTimeString = StringPool.BLANK;
		String timeZoneDisplayName = StringPool.BLANK;

		if (powwowMeeting.getCalendarBookingId() > 0) {
			CalendarBooking calendarBooking =
				CalendarBookingLocalServiceUtil.getCalendarBooking(
					powwowMeeting.getCalendarBookingId());

			User user = UserLocalServiceUtil.getUser(powwowMeeting.getUserId());

			Format format = FastDateFormatFactoryUtil.getSimpleDateFormat(
				"EEEE, dd MMMMM yyyy", user.getLocale(), user.getTimeZone());

			startDateString = format.format(calendarBooking.getStartTime());

			Format timeFormatDate = FastDateFormatFactoryUtil.getTime(
				user.getLocale(), user.getTimeZone());

			startTimeString = timeFormatDate.format(
				calendarBooking.getStartTime());

			TimeZone timeZone = user.getTimeZone();

			timeZoneDisplayName = timeZone.getDisplayName();
		}

		powwowSubscriptionSender.setContextAttributes(
			"[$MEETING_DATE$]", startDateString, "[$MEETING_DESCRIPTION$]",
			powwowMeeting.getDescription(),
			"[$MEETING_JOIN_BY_PHONE_ACCESS_CODE$]",
			PowwowServiceProviderUtil.getJoinByPhoneAccessCode(powwowMeetingId),
			"[$MEETING_JOIN_BY_PHONE_ACCESS_CODE_LABEL$]",
			LanguageUtil.get(
				serviceContext.getLocale(),
				PowwowServiceProviderUtil.getJoinByPhoneAccessCodeLabel(
					powwowMeeting.getProviderType())),
			"[$MEETING_NAME$]", powwowMeeting.getName(), "[$MEETING_PASSWORD$]",
			PowwowServiceProviderUtil.getOptionPassword(
				powwowMeeting.getPowwowMeetingId()),
			"[$MEETING_TIME$]", startTimeString, "[$MEETING_TIME_ZONE$]",
			timeZoneDisplayName, "[$MEETING_URL$]",
			getInvitationURL(
				powwowMeeting.getPowwowMeetingId(), null,
				serviceContext.getRequest()));

		powwowSubscriptionSender.setContextCreatorUserPrefix("MEETING");

		String fromName = PrefsPropsUtil.getString(
			powwowMeeting.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME);
		String fromAddress = PrefsPropsUtil.getString(
			powwowMeeting.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);

		powwowSubscriptionSender.setFrom(fromAddress, fromName);

		powwowSubscriptionSender.setHtmlFormat(true);
		powwowSubscriptionSender.setBody(
			portletPreferences.getValue(
				"emailBody_" + powwowMeeting.getLanguageId(),
				ContentUtil.get(
					PowwowUtil.class.getClassLoader(),
					PortletPropsValues.POWWOW_INVITATION_EMAIL_BODY)));
		powwowSubscriptionSender.setSubject(
			portletPreferences.getValue(
				"emailSubject_" + powwowMeeting.getLanguageId(),
				ContentUtil.get(
					PowwowUtil.class.getClassLoader(),
					PortletPropsValues.POWWOW_INVITATION_EMAIL_SUBJECT)));
		powwowSubscriptionSender.setMailId(
			"powwowMeeting", powwowMeeting.getPowwowMeetingId());
		powwowSubscriptionSender.setPortletId(
			PowwowPortletKeys.POWWOW_MEETINGS);
		powwowSubscriptionSender.setReplyToAddress(fromAddress);
		powwowSubscriptionSender.setScopeGroupId(powwowMeeting.getGroupId());
		powwowSubscriptionSender.setServiceContext(serviceContext);
		powwowSubscriptionSender.setCurrentUserId(powwowMeeting.getUserId());

		return powwowSubscriptionSender;
	}

	public static void sendNotifications(
			long powwowMeetingId, ServiceContext serviceContext)
		throws Exception {

		PowwowSubscriptionSender powwowSubscriptionSender =
			getPowwowSubscriptionSender(powwowMeetingId, serviceContext);

		List<PowwowParticipant> powwowParticipants =
			PowwowParticipantLocalServiceUtil.getPowwowParticipants(
				powwowMeetingId);

		for (PowwowParticipant powwowParticipant : powwowParticipants) {
			if (powwowParticipant.getStatus() ==
					PowwowParticipantConstants.STATUS_INVITED) {

				continue;
			}

			powwowSubscriptionSender.addRuntimeSubscribers(
				powwowParticipant.getEmailAddress(),
				powwowParticipant.getName());

			_sendNotificationEvent(powwowParticipant);

			PowwowParticipantLocalServiceUtil.updateStatus(
				powwowParticipant.getPowwowParticipantId(),
				PowwowParticipantConstants.STATUS_INVITED);
		}

		powwowSubscriptionSender.flushNotificationsAsync();
	}

	private static PowwowParticipant _getPowwowParticipant(
		long powwowMeetingId, String name, long participantUserId,
		String emailAddress, int type) {

		PowwowParticipant powwowParticipant = null;

		if (powwowMeetingId > 0) {
			if (participantUserId > 0) {
				powwowParticipant =
					PowwowParticipantLocalServiceUtil.fetchPowwowParticipant(
						powwowMeetingId, participantUserId);
			}
			else {
				powwowParticipant =
					PowwowParticipantLocalServiceUtil.fetchPowwowParticipant(
						powwowMeetingId, emailAddress);
			}
		}

		if (powwowParticipant == null) {
			powwowParticipant =
				PowwowParticipantLocalServiceUtil.createPowwowParticipant(0);
		}

		powwowParticipant.setName(name);
		powwowParticipant.setParticipantUserId(participantUserId);
		powwowParticipant.setEmailAddress(emailAddress);
		powwowParticipant.setType(type);

		return powwowParticipant;
	}

	private static void _sendNotificationEvent(
			PowwowParticipant powwowParticipant)
		throws Exception {

		if (powwowParticipant.getParticipantUserId() <= 0) {
			return;
		}

		if (!UserNotificationManagerUtil.isDeliver(
				powwowParticipant.getParticipantUserId(),
				PowwowPortletKeys.POWWOW_MEETINGS, 0,
				PowwowParticipantConstants.STATUS_INVITED,
				UserNotificationDeliveryConstants.TYPE_WEBSITE)) {

			return;
		}

		JSONObject notificationEventJSONObject = JSONUtil.put(
			"classPK", powwowParticipant.getPowwowMeetingId()
		).put(
			"userId", powwowParticipant.getUserId()
		);

		NotificationEvent notificationEvent =
			NotificationEventFactoryUtil.createNotificationEvent(
				System.currentTimeMillis(), PowwowPortletKeys.POWWOW_MEETINGS,
				notificationEventJSONObject);

		notificationEvent.setDeliveryRequired(0);

		UserNotificationEventLocalServiceUtil.addUserNotificationEvent(
			powwowParticipant.getParticipantUserId(), notificationEvent);
	}

}