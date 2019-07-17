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

package com.liferay.powwow.meetings.portlet;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarBookingConstants;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.calendar.service.CalendarLocalServiceUtil;
import com.liferay.calendar.service.CalendarResourceLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.powwow.model.PowwowMeeting;
import com.liferay.powwow.model.PowwowMeetingConstants;
import com.liferay.powwow.model.PowwowParticipant;
import com.liferay.powwow.model.PowwowParticipantConstants;
import com.liferay.powwow.provider.PowwowServiceProvider;
import com.liferay.powwow.provider.PowwowServiceProviderUtil;
import com.liferay.powwow.service.PowwowMeetingLocalServiceUtil;
import com.liferay.powwow.service.PowwowMeetingServiceUtil;
import com.liferay.powwow.service.PowwowParticipantLocalServiceUtil;
import com.liferay.powwow.util.PowwowSubscriptionSender;
import com.liferay.powwow.util.PowwowUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Shinn Lok
 * @author Marco Calderon
 * @author Evan Thibodeau
 */
public class MeetingsPortlet extends MVCPortlet {

	public void deletePowwowMeeting(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		long powwowMeetingId = ParamUtil.getLong(
			actionRequest, "powwowMeetingId");

		try {
			if (PowwowServiceProviderUtil.isPowwowMeetingCreated(
					powwowMeetingId)) {

				if (PowwowServiceProviderUtil.isPowwowMeetingRunning(
						powwowMeetingId)) {

					PowwowServiceProviderUtil.endPowwowMeeting(powwowMeetingId);
				}

				PowwowServiceProviderUtil.deletePowwowMeeting(powwowMeetingId);
			}

			PowwowMeetingServiceUtil.deletePowwowMeeting(powwowMeetingId);

			jsonObject.put("success", true);
		}
		catch (Exception e) {
			jsonObject.put(
				"message",
				translate(actionRequest, "the-meeting-could-not-be-deleted")
			).put(
				"success", false
			);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	public void endPowwowMeeting(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long powwowMeetingId = ParamUtil.getLong(
			actionRequest, "powwowMeetingId");

		PowwowServiceProviderUtil.endPowwowMeeting(powwowMeetingId);
	}

	public void joinPowwowMeeting(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long powwowMeetingId = ParamUtil.getLong(
			actionRequest, "powwowMeetingId");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (powwowMeetingId > 0) {
			String hash = ParamUtil.getString(actionRequest, "hash");

			if (!hash.equals(PowwowUtil.getHash(powwowMeetingId))) {
				jsonObject.put("success", Boolean.FALSE);

				writeJSON(actionRequest, actionResponse, jsonObject);

				return;
			}
		}

		long powwowParticipantId = ParamUtil.getLong(
			actionRequest, "powwowParticipantId");

		PowwowParticipant powwowParticipant =
			PowwowParticipantLocalServiceUtil.fetchPowwowParticipant(
				powwowParticipantId);

		try {
			PowwowMeeting powwowMeeting =
				PowwowMeetingLocalServiceUtil.getPowwowMeeting(powwowMeetingId);

			String name = StringPool.BLANK;

			if (PowwowServiceProviderUtil.isSupportsPresettingParticipantName(
					powwowMeeting.getProviderType())) {

				name = ParamUtil.getString(actionRequest, "name");

				if ((powwowParticipant != null) &&
					!name.equals(powwowParticipant.getName())) {

					powwowParticipant.setName(name);

					PowwowParticipantLocalServiceUtil.updatePowwowParticipant(
						powwowParticipant);
				}
			}

			int type = PowwowParticipantConstants.TYPE_ATTENDEE;

			if (powwowParticipant != null) {
				type = powwowParticipant.getType();
			}

			if (powwowMeeting.getPowwowServerId() ==
					PowwowMeetingConstants.POWWOW_SERVER_ID_DEFAULT) {

				if (type == PowwowParticipantConstants.TYPE_ATTENDEE) {
					jsonObject.put("retry", Boolean.TRUE);

					writeJSON(actionRequest, actionResponse, jsonObject);

					return;
				}

				long powwowServerId =
					PowwowServiceProviderUtil.getPowwowServerId(
						powwowMeeting.getProviderType());

				Map<String, Serializable> providerTypeMetadataMap =
					PowwowServiceProviderUtil.addPowwowMeeting(
						powwowMeeting.getUserId(), powwowServerId,
						powwowMeetingId, powwowMeeting.getName(),
						powwowMeeting.getProviderType(),
						new HashMap<String, String>());

				powwowMeeting.setPowwowServerId(powwowServerId);

				powwowMeeting.setProviderTypeMetadata(
					JSONFactoryUtil.serialize(providerTypeMetadataMap));

				PowwowMeetingLocalServiceUtil.updatePowwowMeeting(
					powwowMeeting);
			}

			if (!PowwowServiceProviderUtil.isPowwowMeetingRunning(
					powwowMeetingId)) {

				if (type == PowwowParticipantConstants.TYPE_ATTENDEE) {
					jsonObject.put("retry", Boolean.TRUE);

					writeJSON(actionRequest, actionResponse, jsonObject);

					return;
				}

				powwowMeeting.setStatus(
					PowwowMeetingConstants.STATUS_IN_PROGRESS);

				PowwowMeetingLocalServiceUtil.updatePowwowMeeting(
					powwowMeeting);
			}

			String joinPowwowMeetingURL =
				PowwowServiceProviderUtil.getJoinPowwowMeetingURL(
					powwowMeetingId, name, type);

			jsonObject.put(
				"joinPowwowMeetingURL", joinPowwowMeetingURL
			).put(
				"success", Boolean.TRUE
			);
		}
		catch (Exception e) {
			jsonObject.putException(e);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		String resourceID = resourceRequest.getResourceID();

		if (resourceID.equals("exportPowwowMeetingCalendar")) {
			exportPowwowMeetingCalendar(resourceRequest, resourceResponse);
		}
		else if (resourceID.equals("getEmailNotificationPreview")) {
			getEmailNotificationPreview(resourceRequest, resourceResponse);
		}
		else if (resourceID.equals("getUsers")) {
			getUsers(resourceRequest, resourceResponse);
		}
		else {
			super.serveResource(resourceRequest, resourceResponse);
		}
	}

	public void updatePowwowMeeting(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long powwowMeetingId = ParamUtil.getLong(
			actionRequest, "powwowMeetingId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String languageId = ParamUtil.getString(actionRequest, "languageId");

		PowwowMeeting powwowMeeting = null;

		if (powwowMeetingId > 0) {
			powwowMeeting = PowwowMeetingServiceUtil.getPowwowMeeting(
				powwowMeetingId);
		}

		List<PowwowParticipant> powwowParticipants =
			PowwowUtil.getPowwowParticipants(actionRequest);

		long hostUserId = getHostUserId(
			themeDisplay.getCompanyId(), powwowParticipants);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			PowwowMeeting.class.getName(), actionRequest);

		CalendarBooking calendarBooking = updateCalendarBooking(
			actionRequest, powwowMeeting, powwowParticipants, serviceContext);

		Map<String, String> options = new HashMap<>();

		boolean autoStartVideo = ParamUtil.getBoolean(
			actionRequest, "autoStartVideo");

		options.put(
			PowwowMeetingConstants.OPTION_AUTO_START_VIDEO,
			Boolean.toString(autoStartVideo));

		boolean requirePassword = ParamUtil.getBoolean(
			actionRequest, "requirePassword");

		String password = ParamUtil.getString(actionRequest, "password");

		if (requirePassword && !password.equals(StringPool.BLANK)) {
			options.put(PowwowMeetingConstants.OPTION_PASSWORD, password);
		}

		Map<String, Serializable> providerTypeMetadataMap = new HashMap<>();

		if (powwowMeetingId <= 0) {
			long powwowServerId =
				PowwowMeetingConstants.POWWOW_SERVER_ID_DEFAULT;

			String providerType = ParamUtil.getString(
				actionRequest, "providerType");

			int addPowwowMeetingStrategy =
				PowwowServiceProviderUtil.getAddPowwowMeetingStrategy(
					providerType);

			if (addPowwowMeetingStrategy ==
					PowwowServiceProvider.ADD_POWWOW_MEETING_STRATEGY_EAGER) {

				powwowServerId = PowwowServiceProviderUtil.getPowwowServerId(
					providerType);

				providerTypeMetadataMap =
					PowwowServiceProviderUtil.addPowwowMeeting(
						hostUserId, powwowServerId, powwowMeetingId, name,
						providerType, options);
			}

			PowwowMeetingServiceUtil.addPowwowMeeting(
				themeDisplay.getScopeGroupId(),
				PortalUtil.getPortletId(actionRequest), powwowServerId, name,
				description, providerType, providerTypeMetadataMap, languageId,
				calendarBooking.getCalendarBookingId(),
				PowwowMeetingConstants.STATUS_SCHEDULED, powwowParticipants,
				serviceContext);
		}
		else {
			int addPowwowMeetingStrategy =
				PowwowServiceProviderUtil.getAddPowwowMeetingStrategy(
					powwowMeeting.getProviderType());

			if (addPowwowMeetingStrategy ==
					PowwowServiceProvider.ADD_POWWOW_MEETING_STRATEGY_EAGER) {

				providerTypeMetadataMap =
					PowwowServiceProviderUtil.updatePowwowMeeting(
						powwowMeetingId, name, powwowMeeting.getProviderType(),
						hostUserId, options);
			}

			PowwowMeetingServiceUtil.updatePowwowMeeting(
				powwowMeetingId, powwowMeeting.getPowwowServerId(), name,
				description, powwowMeeting.getProviderType(),
				providerTypeMetadataMap, languageId,
				calendarBooking.getCalendarBookingId(),
				PowwowMeetingConstants.STATUS_SCHEDULED, powwowParticipants,
				serviceContext);
		}
	}

	protected void exportPowwowMeetingCalendar(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		Long powwowMeetingId = ParamUtil.getLong(
			resourceRequest, "powwowMeetingId");

		try {
			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			byteArrayOutputStream.write(
				PowwowUtil.exportPowwowMeetingCalendar(powwowMeetingId));

			resourceResponse.setContentLength(byteArrayOutputStream.size());

			resourceResponse.setContentType(ContentTypes.TEXT_CALENDAR);

			String contentDispositionHeader =
				HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT +
					"; filename=invite.ics";

			resourceResponse.addProperty(
				HttpHeaders.CONTENT_DISPOSITION, contentDispositionHeader);

			OutputStream outputStream =
				resourceResponse.getPortletOutputStream();

			byteArrayOutputStream.writeTo(outputStream);

			outputStream.flush();

			outputStream.close();
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}

	protected String getCalendarBookingDescription(
			ActionRequest actionRequest, PowwowMeeting powwowMeeting,
			ServiceContext serviceContext)
		throws Exception {

		if (powwowMeeting == null) {
			return StringPool.BLANK;
		}

		String description = ParamUtil.getString(actionRequest, "description");

		String invitationURLMarkup = getInvitationURLMarkup(
			powwowMeeting.getPowwowMeetingId(), serviceContext);

		return invitationURLMarkup + description;
	}

	protected long getCalendarId(long userId, ServiceContext serviceContext)
		throws Exception {

		long classNameId = PortalUtil.getClassNameId(User.class);

		CalendarResource calendarResource =
			CalendarResourceLocalServiceUtil.fetchCalendarResource(
				classNameId, userId);

		if (calendarResource == null) {
			User user = UserLocalServiceUtil.getUser(userId);

			Group userGroup = null;

			String userName = user.getFullName();

			if (user.isDefaultUser()) {
				userGroup = GroupLocalServiceUtil.getGroup(
					serviceContext.getCompanyId(), GroupConstants.GUEST);

				userName = GroupConstants.GUEST;
			}
			else {
				userGroup = GroupLocalServiceUtil.getUserGroup(
					serviceContext.getCompanyId(), userId);
			}

			Map<Locale, String> nameMap = new HashMap<>();

			nameMap.put(LocaleUtil.getDefault(), userName);

			Map<Locale, String> descriptionMap = new HashMap<>();

			calendarResource =
				CalendarResourceLocalServiceUtil.addCalendarResource(
					userId, userGroup.getGroupId(),
					PortalUtil.getClassNameId(User.class), userId, null, null,
					nameMap, descriptionMap, true, serviceContext);

			if (calendarResource.getDefaultCalendarId() <= 0) {
				CalendarLocalServiceUtil.addCalendar(
					userId, calendarResource.getGroupId(),
					calendarResource.getCalendarResourceId(),
					calendarResource.getNameMap(),
					calendarResource.getDescriptionMap(),
					calendarResource.getTimeZoneId(), 0, true, false, false,
					serviceContext);
			}
		}

		return calendarResource.getDefaultCalendarId();
	}

	protected long[] getChildCalendarIds(
			long companyId, List<PowwowParticipant> powwowParticipants,
			ServiceContext serviceContext)
		throws Exception {

		List<Long> childCalendarIds = new ArrayList<>();

		for (PowwowParticipant powwowParticipant : powwowParticipants) {
			User user = UserLocalServiceUtil.fetchUserByEmailAddress(
				companyId, powwowParticipant.getEmailAddress());

			if (user == null) {
				continue;
			}

			long calendarId = getCalendarId(user.getUserId(), serviceContext);

			if (calendarId > 0) {
				childCalendarIds.add(calendarId);
			}
		}

		return ArrayUtil.toArray(childCalendarIds.toArray(new Long[0]));
	}

	protected void getEmailNotificationPreview(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			Long powwowMeetingId = ParamUtil.getLong(
				resourceRequest, "powwowMeetingId");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				PowwowMeeting.class.getName(), resourceRequest);

			PowwowSubscriptionSender powwowSubscriptionSender =
				PowwowUtil.getPowwowSubscriptionSender(
					powwowMeetingId, serviceContext);

			powwowSubscriptionSender.initialize();

			jsonObject.put(
				"emailBody",
				powwowSubscriptionSender.getEmailNotificationBody(
					serviceContext.getLocale())
			).put(
				"emailSubject",
				powwowSubscriptionSender.getEmailNotificationSubject(
					serviceContext.getLocale())
			).put(
				"success", Boolean.TRUE
			);
		}
		catch (Exception e) {
			jsonObject.put(
				"message",
				translate(
					resourceRequest, "unable-to-render-meeting-invitation")
			).put(
				"success", Boolean.FALSE
			);
		}

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	protected long getHostUserId(
			long companyId, List<PowwowParticipant> powwowParticipants)
		throws PortalException {

		for (PowwowParticipant powwowParticipant : powwowParticipants) {
			if (powwowParticipant.getType() ==
					PowwowParticipantConstants.TYPE_HOST) {

				return UserLocalServiceUtil.getUserIdByEmailAddress(
					companyId, powwowParticipant.getEmailAddress());
			}
		}

		return 0;
	}

	protected String getInvitationURLMarkup(
			long powwowMeetingId, ServiceContext serviceContext)
		throws Exception {

		String meetingURL = PowwowUtil.getInvitationURL(
			powwowMeetingId, null, serviceContext.getRequest());

		StringBundler sb = new StringBundler(5);

		sb.append("<a href=\"");
		sb.append(meetingURL);
		sb.append("\" target=\"_blank\">");
		sb.append(LanguageUtil.get(serviceContext.getLocale(), "join-meeting"));
		sb.append("</a><br />");

		return sb.toString();
	}

	protected Map<Locale, String> getLocalizationMap(String key) {
		Set<Locale> locales = LanguageUtil.getAvailableLocales();

		Map<Locale, String> map = new HashMap<>();

		for (Locale locale : locales) {
			map.put(locale, key);
		}

		return map;
	}

	protected long getTime(
			PortletRequest portletRequest, String name, TimeZone timeZone)
		throws Exception {

		int month = ParamUtil.getInteger(portletRequest, name + "Month");
		int day = ParamUtil.getInteger(portletRequest, name + "Day");
		int year = ParamUtil.getInteger(portletRequest, name + "Year");
		int hour = ParamUtil.getInteger(portletRequest, name + "Hour");
		int minute = ParamUtil.getInteger(portletRequest, name + "Minute");

		int amPm = ParamUtil.getInteger(portletRequest, name + "AmPm");

		if (amPm == Calendar.PM) {
			hour += 12;
		}

		Date date = PortalUtil.getDate(
			month, day, year, hour, minute, timeZone, PortalException.class);

		return date.getTime();
	}

	protected void getUsers(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		try {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

			ThemeDisplay themeDisplay =
				(ThemeDisplay)resourceRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			String userName = ParamUtil.getString(resourceRequest, "name");

			List<User> users = UserLocalServiceUtil.search(
				themeDisplay.getCompanyId(), userName,
				WorkflowConstants.STATUS_ANY, null, 0, 10,
				(OrderByComparator)null);

			for (User user : users) {
				JSONObject jsonObject = JSONUtil.put(
					"emailAddress", user.getEmailAddress()
				).put(
					"fullName", user.getFullName()
				).put(
					"portraitURL", user.getPortraitURL(themeDisplay)
				).put(
					"userId", user.getUserId()
				);

				jsonArray.put(jsonObject);
			}

			writeJSON(resourceRequest, resourceResponse, jsonArray);
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}

	protected CalendarBooking updateCalendarBooking(
			ActionRequest actionRequest, PowwowMeeting powwowMeeting,
			List<PowwowParticipant> powwowParticipants,
			ServiceContext serviceContext)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		CalendarBooking calendarBooking = null;

		if (powwowMeeting != null) {
			calendarBooking =
				CalendarBookingLocalServiceUtil.fetchCalendarBooking(
					powwowMeeting.getCalendarBookingId());
		}

		long calendarId = getCalendarId(
			themeDisplay.getUserId(), serviceContext);
		long[] childCalendarIds = getChildCalendarIds(
			themeDisplay.getCompanyId(), powwowParticipants, serviceContext);
		Map<Locale, String> titleMap = getLocalizationMap(
			ParamUtil.getString(actionRequest, "name"));
		Map<Locale, String> descriptionMap = getLocalizationMap(
			getCalendarBookingDescription(
				actionRequest, powwowMeeting, serviceContext));

		long startTime = getTime(
			actionRequest, "startTime", themeDisplay.getTimeZone());
		long endTime = getTime(
			actionRequest, "endTime", themeDisplay.getTimeZone());

		serviceContext.setAttribute("sendNotification", Boolean.FALSE);

		if (calendarBooking != null) {
			if (calendarBooking.isInTrash()) {
				CalendarBookingLocalServiceUtil.restoreCalendarBookingFromTrash(
					themeDisplay.getUserId(),
					calendarBooking.getCalendarBookingId());
			}

			calendarBooking =
				CalendarBookingLocalServiceUtil.updateCalendarBooking(
					themeDisplay.getUserId(),
					calendarBooking.getCalendarBookingId(),
					calendarBooking.getCalendarId(), childCalendarIds, titleMap,
					descriptionMap, StringPool.BLANK, startTime, endTime, false,
					null, 0, "email", 0, "email", serviceContext);
		}
		else {
			calendarBooking =
				CalendarBookingLocalServiceUtil.addCalendarBooking(
					themeDisplay.getUserId(), calendarId, childCalendarIds,
					CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
					CalendarBookingConstants.
						RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
					titleMap, descriptionMap, StringPool.BLANK, startTime,
					endTime, false, null, 0, "email", 0, "email",
					serviceContext);

			calendarBooking.setStatus(WorkflowConstants.STATUS_INACTIVE);

			CalendarBookingLocalServiceUtil.updateCalendarBooking(
				calendarBooking);
		}

		return calendarBooking;
	}

}