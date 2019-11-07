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

package com.liferay.calevent.importer.internal.verify;

import com.liferay.asset.kernel.exception.NoSuchVocabularyException;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.calendar.exception.NoSuchBookingException;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.notification.NotificationType;
import com.liferay.calendar.recurrence.Frequency;
import com.liferay.calendar.recurrence.PositionalWeekday;
import com.liferay.calendar.recurrence.Recurrence;
import com.liferay.calendar.recurrence.RecurrenceSerializer;
import com.liferay.calendar.recurrence.Weekday;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.service.CalendarResourceLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.expando.kernel.model.ExpandoRow;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBDiscussionLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cal.DayAndPosition;
import com.liferay.portal.kernel.cal.TZSRecurrence;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.model.RatingsStats;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.service.SocialActivityLocalService;
import com.liferay.subscription.model.Subscription;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jabsorb.JSONSerializer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(
	immediate = true,
	property = "verify.process.name=com.liferay.calevent.importer",
	service = VerifyProcess.class
)
public class CalEventImporterVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			importCalEvents();
		}
	}

	protected void importCalEvents() throws Exception {
		if (!hasTable("CalEvent")) {
			return;
		}

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler(6);

			sb.append("select uuid_, eventId, groupId, companyId, userId, ");
			sb.append("userName, createDate, modifiedDate, title, ");
			sb.append("description, location, startDate, endDate, ");
			sb.append("durationHour, durationMinute, allDay, type_, ");
			sb.append("repeating, recurrence, remindBy, firstReminder, ");
			sb.append("secondReminder from CalEvent");

			try (PreparedStatement ps = connection.prepareStatement(
					sb.toString())) {

				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					String uuid = rs.getString("uuid_");
					long eventId = rs.getLong("eventId");
					long groupId = rs.getLong("groupId");
					long companyId = rs.getLong("companyId");
					long userId = rs.getLong("userId");
					String userName = rs.getString("userName");
					Timestamp createDate = rs.getTimestamp("createDate");
					Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
					String title = rs.getString("title");
					String description = rs.getString("description");
					String location = rs.getString("location");
					Timestamp startDate = rs.getTimestamp("startDate");
					int durationHour = rs.getInt("durationHour");
					int durationMinute = rs.getInt("durationMinute");
					boolean allDay = rs.getBoolean("allDay");
					String type = rs.getString("type_");
					String recurrence = rs.getString("recurrence");
					int remindBy = rs.getInt("remindBy");
					int firstReminder = rs.getInt("firstReminder");
					int secondReminder = rs.getInt("secondReminder");

					CalendarBooking calendarBooking = _importCalEvent(
						uuid, eventId, groupId, companyId, userId, userName,
						createDate, modifiedDate, title, description, location,
						startDate, durationHour, durationMinute, allDay, type,
						recurrence, remindBy, firstReminder, secondReminder);

					if (_log.isInfoEnabled()) {
						_log.info(
							"CalendarBooking: " + calendarBooking +
								" imported sucessfully.");
					}
				}
			}
		}
	}

	private void _addAssetEntry(
		long entryId, long groupId, long companyId, long userId,
		String userName, Date createDate, Date modifiedDate, long classNameId,
		long classPK, String classUuid, boolean visible, Date startDate,
		Date endDate, Date publishDate, Date expirationDate, String mimeType,
		String title, String description, String summary, String url,
		String layoutUuid, int height, int width, double priority,
		int viewCount) {

		AssetEntry assetEntry = _assetEntryLocalService.createAssetEntry(
			entryId);

		assetEntry.setGroupId(groupId);
		assetEntry.setCompanyId(companyId);
		assetEntry.setUserId(userId);
		assetEntry.setUserName(userName);
		assetEntry.setCreateDate(createDate);
		assetEntry.setModifiedDate(modifiedDate);
		assetEntry.setClassNameId(classNameId);
		assetEntry.setClassPK(classPK);
		assetEntry.setClassUuid(classUuid);
		assetEntry.setVisible(visible);
		assetEntry.setStartDate(startDate);
		assetEntry.setEndDate(endDate);
		assetEntry.setPublishDate(publishDate);
		assetEntry.setExpirationDate(expirationDate);
		assetEntry.setMimeType(mimeType);
		assetEntry.setTitle(title);
		assetEntry.setDescription(description);
		assetEntry.setSummary(summary);
		assetEntry.setUrl(url);
		assetEntry.setLayoutUuid(layoutUuid);
		assetEntry.setHeight(height);
		assetEntry.setWidth(width);
		assetEntry.setPriority(priority);
		assetEntry.setViewCount(viewCount);

		_assetEntryLocalService.updateAssetEntry(assetEntry);
	}

	private void _addAssetLink(
		long linkId, long companyId, long userId, String userName,
		Date createDate, long entryId1, long entryId2, int type, int weight) {

		AssetLink assetLink = _assetLinkLocalService.createAssetLink(linkId);

		assetLink.setCompanyId(companyId);
		assetLink.setUserId(userId);
		assetLink.setUserName(userName);
		assetLink.setCreateDate(createDate);
		assetLink.setEntryId1(entryId1);
		assetLink.setEntryId2(entryId2);
		assetLink.setType(type);
		assetLink.setWeight(weight);

		_assetLinkLocalService.updateAssetLink(assetLink);
	}

	private CalendarBooking _addCalendarBooking(
		String uuid, long calendarBookingId, long companyId, long groupId,
		long userId, String userName, Timestamp createDate,
		Timestamp modifiedDate, long calendarId, long calendarResourceId,
		String title, String description, String location, long startTime,
		long endTime, boolean allDay, String recurrence, int firstReminder,
		NotificationType firstReminderType, int secondReminder,
		NotificationType secondReminderType) {

		CalendarBooking calendarBooking =
			_calendarBookingLocalService.createCalendarBooking(
				calendarBookingId);

		calendarBooking.setUuid(uuid);
		calendarBooking.setGroupId(groupId);
		calendarBooking.setCompanyId(companyId);
		calendarBooking.setUserId(userId);
		calendarBooking.setUserName(userName);
		calendarBooking.setCreateDate(createDate);
		calendarBooking.setModifiedDate(modifiedDate);
		calendarBooking.setCalendarId(calendarId);
		calendarBooking.setCalendarResourceId(calendarResourceId);
		calendarBooking.setParentCalendarBookingId(calendarBookingId);
		calendarBooking.setVEventUid(uuid);
		calendarBooking.setTitle(title);
		calendarBooking.setDescription(description);
		calendarBooking.setLocation(location);
		calendarBooking.setStartTime(startTime);
		calendarBooking.setEndTime(endTime);
		calendarBooking.setAllDay(allDay);
		calendarBooking.setRecurrence(recurrence);
		calendarBooking.setFirstReminder(firstReminder);
		calendarBooking.setFirstReminderType(firstReminderType.toString());
		calendarBooking.setSecondReminder(secondReminder);
		calendarBooking.setSecondReminderType(secondReminderType.toString());
		calendarBooking.setStatus(WorkflowConstants.STATUS_APPROVED);
		calendarBooking.setStatusByUserId(userId);
		calendarBooking.setStatusByUserName(userName);
		calendarBooking.setStatusDate(createDate);

		return _calendarBookingLocalService.updateCalendarBooking(
			calendarBooking);
	}

	private void _addMBDiscussion(
		String uuid, long discussionId, long groupId, long companyId,
		long userId, String userName, Date createDate, Date modifiedDate,
		long classNameId, long classPK, long threadId) {

		MBDiscussion mbDiscussion =
			_mbDiscussionLocalService.createMBDiscussion(discussionId);

		mbDiscussion.setUuid(uuid);
		mbDiscussion.setGroupId(groupId);
		mbDiscussion.setCompanyId(companyId);
		mbDiscussion.setUserId(userId);
		mbDiscussion.setUserName(userName);
		mbDiscussion.setCreateDate(createDate);
		mbDiscussion.setModifiedDate(modifiedDate);
		mbDiscussion.setClassNameId(classNameId);
		mbDiscussion.setClassPK(classPK);
		mbDiscussion.setThreadId(threadId);

		_mbDiscussionLocalService.updateMBDiscussion(mbDiscussion);
	}

	private void _addMBMessage(
			String uuid, long messageId, long groupId, long companyId,
			long userId, String userName, Date createDate, Date modifiedDate,
			long classNameId, long classPK, long categoryId, long threadId,
			long rootMessageId, long parentMessageId, String subject,
			String body, String format, boolean anonymous, double priority,
			boolean allowPingbacks, boolean answer, int status,
			long statusByUserId, String statusByUserName, Date statusDate,
			Map<Long, Long> mbMessageIds)
		throws PortalException {

		if (parentMessageId == MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) {
			rootMessageId = messageId;
		}
		else {
			rootMessageId = _importMBMessage(
				rootMessageId, threadId, classPK, mbMessageIds);

			parentMessageId = _importMBMessage(
				parentMessageId, threadId, classPK, mbMessageIds);
		}

		MBMessage mbMessage = _mbMessageLocalService.createMBMessage(messageId);

		mbMessage.setUuid(uuid);
		mbMessage.setGroupId(groupId);
		mbMessage.setCompanyId(companyId);
		mbMessage.setUserId(userId);
		mbMessage.setUserName(userName);
		mbMessage.setCreateDate(createDate);
		mbMessage.setModifiedDate(modifiedDate);
		mbMessage.setClassNameId(classNameId);
		mbMessage.setClassPK(classPK);
		mbMessage.setCategoryId(categoryId);
		mbMessage.setThreadId(threadId);
		mbMessage.setRootMessageId(rootMessageId);
		mbMessage.setParentMessageId(parentMessageId);
		mbMessage.setSubject(subject);
		mbMessage.setBody(body);
		mbMessage.setFormat(format);
		mbMessage.setAnonymous(anonymous);
		mbMessage.setPriority(priority);
		mbMessage.setAllowPingbacks(allowPingbacks);
		mbMessage.setAnswer(answer);
		mbMessage.setStatus(status);
		mbMessage.setStatusByUserId(statusByUserId);
		mbMessage.setStatusByUserName(statusByUserName);
		mbMessage.setStatusDate(statusDate);

		_mbMessageLocalService.updateMBMessage(mbMessage);
	}

	private void _addMBThread(
		String uuid, long threadId, long groupId, long companyId, long userId,
		String userName, Date createDate, Date modifiedDate, long categoryId,
		long rootMessageId, long rootMessageUserId, String title,
		int messageCount, int viewCount, long lastPostByUserId,
		Date lastPostDate, double priority, boolean question, int status,
		long statusByUserId, String statusByUserName, Date statusDate) {

		MBThread mbThread = _mbThreadLocalService.createMBThread(threadId);

		mbThread.setUuid(uuid);
		mbThread.setGroupId(groupId);
		mbThread.setCompanyId(companyId);
		mbThread.setUserId(userId);
		mbThread.setUserName(userName);
		mbThread.setCreateDate(createDate);
		mbThread.setModifiedDate(modifiedDate);
		mbThread.setCategoryId(categoryId);
		mbThread.setRootMessageId(rootMessageId);
		mbThread.setRootMessageUserId(rootMessageUserId);
		mbThread.setTitle(title);
		mbThread.setMessageCount(messageCount);
		mbThread.setViewCount(viewCount);
		mbThread.setLastPostByUserId(lastPostByUserId);
		mbThread.setLastPostDate(lastPostDate);
		mbThread.setPriority(priority);
		mbThread.setQuestion(question);
		mbThread.setStatus(status);
		mbThread.setStatusByUserId(statusByUserId);
		mbThread.setStatusByUserName(statusByUserName);
		mbThread.setStatusDate(statusDate);

		_mbThreadLocalService.updateMBThread(mbThread);
	}

	private RatingsEntry _addRatingsEntry(
		long entryId, long companyId, long userId, String userName,
		Date createDate, Date modifiedDate, String className, long classPK,
		double score) {

		long classNameId = _classNameLocalService.getClassNameId(className);

		RatingsEntry ratingsEntry =
			_ratingsEntryLocalService.createRatingsEntry(entryId);

		ratingsEntry.setCompanyId(companyId);
		ratingsEntry.setUserId(userId);
		ratingsEntry.setUserName(userName);
		ratingsEntry.setCreateDate(createDate);
		ratingsEntry.setModifiedDate(modifiedDate);
		ratingsEntry.setClassNameId(classNameId);
		ratingsEntry.setClassPK(classPK);
		ratingsEntry.setScore(score);

		return _ratingsEntryLocalService.updateRatingsEntry(ratingsEntry);
	}

	private RatingsStats _addRatingsStats(
		long statsId, String className, long classPK, int totalEntries,
		double totalScore, double averageScore) {

		RatingsStats ratingsStats =
			_ratingsStatsLocalService.createRatingsStats(statsId);

		ratingsStats.setClassNameId(
			_classNameLocalService.getClassNameId(className));

		ratingsStats.setClassPK(classPK);
		ratingsStats.setTotalEntries(totalEntries);
		ratingsStats.setTotalScore(totalScore);
		ratingsStats.setAverageScore(averageScore);

		return _ratingsStatsLocalService.updateRatingsStats(ratingsStats);
	}

	private void _addSocialActivity(
		long activityId, long groupId, long companyId, long userId,
		long createDate, long mirrorActivityId, long classNameId, long classPK,
		int type, String extraData, long receiverUserId) {

		SocialActivity socialActivity =
			_socialActivityLocalService.createSocialActivity(activityId);

		socialActivity.setGroupId(groupId);
		socialActivity.setCompanyId(companyId);
		socialActivity.setUserId(userId);
		socialActivity.setCreateDate(createDate);
		socialActivity.setMirrorActivityId(mirrorActivityId);
		socialActivity.setClassNameId(classNameId);
		socialActivity.setClassPK(classPK);
		socialActivity.setType(type);
		socialActivity.setExtraData(extraData);
		socialActivity.setReceiverUserId(receiverUserId);

		_socialActivityLocalService.updateSocialActivity(socialActivity);
	}

	private void _addSubscription(
		long subscriptionId, long companyId, long userId, String userName,
		Date createDate, Date modifiedDate, long classNameId, long classPK,
		String frequency) {

		Subscription subscription =
			_subscriptionLocalService.createSubscription(subscriptionId);

		subscription.setCompanyId(companyId);
		subscription.setUserId(userId);
		subscription.setUserName(userName);
		subscription.setCreateDate(createDate);
		subscription.setModifiedDate(modifiedDate);
		subscription.setClassNameId(classNameId);
		subscription.setClassPK(classPK);
		subscription.setFrequency(frequency);

		_subscriptionLocalService.updateSubscription(subscription);
	}

	private String _convertRecurrence(String originalRecurrence)
		throws Exception {

		if (Validator.isNull(originalRecurrence)) {
			return null;
		}

		TZSRecurrence tzsRecurrence = null;

		try {
			tzsRecurrence = (TZSRecurrence)JSONFactoryUtil.deserialize(
				originalRecurrence);
		}
		catch (IllegalStateException ise) {

			// LPS-65972

			JSONSerializer jsonSerializer = _getJSONSerializer();

			tzsRecurrence = (TZSRecurrence)jsonSerializer.fromJSON(
				originalRecurrence);
		}

		if (tzsRecurrence == null) {
			return null;
		}

		Recurrence recurrence = new Recurrence();

		Frequency frequency = _frequencies.get(tzsRecurrence.getFrequency());

		int interval = tzsRecurrence.getInterval();

		List<PositionalWeekday> positionalWeekdays = new ArrayList<>();

		if ((frequency == Frequency.DAILY) && (interval == 0)) {
			frequency = Frequency.WEEKLY;

			interval = 1;

			positionalWeekdays.add(new PositionalWeekday(Weekday.MONDAY, 0));
			positionalWeekdays.add(new PositionalWeekday(Weekday.TUESDAY, 0));
			positionalWeekdays.add(new PositionalWeekday(Weekday.WEDNESDAY, 0));
			positionalWeekdays.add(new PositionalWeekday(Weekday.THURSDAY, 0));
			positionalWeekdays.add(new PositionalWeekday(Weekday.FRIDAY, 0));
		}
		else {
			DayAndPosition[] dayAndPositions = tzsRecurrence.getByDay();

			if (dayAndPositions != null) {
				for (DayAndPosition dayAndPosition : dayAndPositions) {
					Weekday weekday = _weekdays.get(
						dayAndPosition.getDayOfWeek());

					PositionalWeekday positionalWeekday = new PositionalWeekday(
						weekday, dayAndPosition.getDayPosition());

					positionalWeekdays.add(positionalWeekday);
				}
			}

			int[] months = tzsRecurrence.getByMonth();

			if (ArrayUtil.isNotEmpty(months)) {
				List<Integer> monthsList = new ArrayList<>();

				for (int month : months) {
					monthsList.add(month);
				}

				recurrence.setMonths(monthsList);
			}
		}

		recurrence.setInterval(interval);
		recurrence.setFrequency(frequency);
		recurrence.setPositionalWeekdays(positionalWeekdays);

		Calendar untilJCalendar = tzsRecurrence.getUntil();

		int ocurrence = tzsRecurrence.getOccurrence();

		if (untilJCalendar != null) {
			recurrence.setUntilJCalendar(untilJCalendar);
		}
		else if (ocurrence > 0) {
			recurrence.setCount(ocurrence);
		}

		return RecurrenceSerializer.serialize(recurrence);
	}

	private String[] _getActionIds(
		ResourcePermission resourcePermission, String oldClassName,
		List<String> modelResourceActions) {

		List<String> actionIds = new ArrayList<>();

		List<ResourceAction> resourceActions =
			_resourceActionLocalService.getResourceActions(oldClassName);

		for (ResourceAction resourceAction : resourceActions) {
			if (resourcePermission.hasAction(resourceAction) &&
				modelResourceActions.contains(resourceAction.getActionId())) {

				actionIds.add(resourceAction.getActionId());
			}
		}

		return actionIds.toArray(new String[0]);
	}

	private AssetCategory _getAssetCategory(
			long userId, long companyId, long groupId, String name)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);

		User user = null;

		try {
			user = _userLocalService.getUserById(companyId, userId);
		}
		catch (NoSuchUserException nsue) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsue, nsue);
			}

			user = _userLocalService.getDefaultUser(companyId);

			userId = user.getUserId();
		}

		serviceContext.setUserId(userId);

		AssetVocabulary assetVocabulary = null;

		try {
			assetVocabulary = _assetVocabularyLocalService.getGroupVocabulary(
				groupId, _ASSET_VOCABULARY_NAME);
		}
		catch (NoSuchVocabularyException nsve) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsve, nsve);
			}

			assetVocabulary = _assetVocabularyLocalService.addVocabulary(
				userId, groupId, _ASSET_VOCABULARY_NAME, serviceContext);
		}

		List<AssetCategory> assetCategories =
			_assetCategoryLocalService.getVocabularyRootCategories(
				assetVocabulary.getVocabularyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		for (AssetCategory assetCategory : assetCategories) {
			if (name.equals(assetCategory.getName())) {
				return assetCategory;
			}
		}

		return _assetCategoryLocalService.addCategory(
			userId, groupId, name, assetVocabulary.getVocabularyId(),
			serviceContext);
	}

	private CalendarResource _getCalendarResource(long companyId, long groupId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);

		long userId = _userLocalService.getDefaultUserId(companyId);

		serviceContext.setUserId(userId);

		Group group = _groupLocalService.getGroup(groupId);

		if (group.isUser()) {
			userId = group.getClassPK();

			CalendarResource calendarResource =
				_calendarResourceLocalService.fetchCalendarResource(
					_classNameLocalService.getClassNameId(User.class), userId);

			if (calendarResource != null) {
				return calendarResource;
			}

			User user = _userLocalService.getUser(userId);

			Group userGroup = null;

			String userName = user.getFullName();

			if (user.isDefaultUser()) {
				userGroup = _groupLocalService.getGroup(
					serviceContext.getCompanyId(), GroupConstants.GUEST);

				userName = GroupConstants.GUEST;
			}
			else {
				userGroup = _groupLocalService.getUserGroup(
					serviceContext.getCompanyId(), userId);
			}

			Map<Locale, String> nameMap = HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), userName
			).build();

			Map<Locale, String> descriptionMap = new HashMap<>();

			return _calendarResourceLocalService.addCalendarResource(
				userId, userGroup.getGroupId(),
				_classNameLocalService.getClassNameId(User.class), userId, null,
				null,
				LocalizationUtil.populateLocalizationMap(
					nameMap,
					LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()),
					groupId),
				descriptionMap, true, serviceContext);
		}

		CalendarResource calendarResource =
			_calendarResourceLocalService.fetchCalendarResource(
				_classNameLocalService.getClassNameId(Group.class), groupId);

		if (calendarResource != null) {
			return calendarResource;
		}

		userId = group.getCreatorUserId();

		User user = _userLocalService.fetchUserById(userId);

		if ((user == null) || user.isDefaultUser()) {
			Role role = _roleLocalService.getRole(
				group.getCompanyId(), RoleConstants.ADMINISTRATOR);

			long[] userIds = _userLocalService.getRoleUserIds(role.getRoleId());

			userId = userIds[0];
		}

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.getSiteDefault(), group.getDescriptiveName()
		).build();

		Map<Locale, String> descriptionMap = new HashMap<>();

		return _calendarResourceLocalService.addCalendarResource(
			userId, groupId, _classNameLocalService.getClassNameId(Group.class),
			groupId, null, null,
			LocalizationUtil.populateLocalizationMap(
				nameMap, LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()),
				groupId),
			descriptionMap, true, serviceContext);
	}

	private JSONSerializer _getJSONSerializer() throws Exception {
		if (_jsonSerializer == null) {
			_jsonSerializer = new JSONSerializer();

			_jsonSerializer.registerDefaultSerializers();
		}

		return _jsonSerializer;
	}

	private void _importAssetLink(
			AssetLink assetLink, long oldEntryId, long newEntryId)
		throws Exception {

		long entryId1 = 0;
		long entryId2 = 0;

		AssetEntry linkedAssetEntry;

		if (assetLink.getEntryId1() == oldEntryId) {
			entryId1 = newEntryId;

			entryId2 = assetLink.getEntryId2();

			linkedAssetEntry = _assetEntryLocalService.fetchAssetEntry(
				entryId2);
		}
		else {
			entryId1 = assetLink.getEntryId1();

			entryId2 = newEntryId;

			linkedAssetEntry = _assetEntryLocalService.fetchAssetEntry(
				entryId2);
		}

		if (linkedAssetEntry.getClassNameId() ==
				_classNameLocalService.getClassNameId(_CLASS_NAME)) {

			CalendarBooking calendarBooking = _importCalEvent(
				linkedAssetEntry.getClassPK());

			CalendarResource calendarResource = _getCalendarResource(
				calendarBooking.getCompanyId(), calendarBooking.getGroupId());

			linkedAssetEntry = _assetEntryLocalService.getEntry(
				calendarResource.getGroupId(), calendarBooking.getUuid());

			if (assetLink.getEntryId1() == oldEntryId) {
				entryId2 = linkedAssetEntry.getEntryId();
			}
			else {
				entryId1 = linkedAssetEntry.getEntryId();
			}

			if (_isAssetLinkImported(entryId1, entryId2, assetLink.getType())) {
				return;
			}
		}

		long linkId = _counterLocalService.increment();

		_addAssetLink(
			linkId, assetLink.getCompanyId(), assetLink.getUserId(),
			assetLink.getUserName(), assetLink.getCreateDate(), entryId1,
			entryId2, assetLink.getType(), assetLink.getWeight());
	}

	private void _importAssets(
			String uuid, long companyId, long groupId, long userId, String type,
			long eventId, long calendarBookingId)
		throws Exception {

		// Asset entry

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			_CLASS_NAME, eventId);

		if (assetEntry == null) {
			return;
		}

		long entryId = _counterLocalService.increment();

		_addAssetEntry(
			entryId, assetEntry.getGroupId(), assetEntry.getCompanyId(),
			assetEntry.getUserId(), assetEntry.getUserName(),
			assetEntry.getCreateDate(), assetEntry.getModifiedDate(),
			_classNameLocalService.getClassNameId(
				CalendarBooking.class.getName()),
			calendarBookingId, uuid, assetEntry.isVisible(),
			assetEntry.getStartDate(), assetEntry.getEndDate(),
			assetEntry.getPublishDate(), assetEntry.getExpirationDate(),
			assetEntry.getMimeType(), assetEntry.getTitle(),
			assetEntry.getDescription(), assetEntry.getSummary(),
			assetEntry.getUrl(), assetEntry.getLayoutUuid(),
			assetEntry.getHeight(), assetEntry.getWidth(),
			assetEntry.getPriority(), assetEntry.getViewCount());

		// Asset categories

		List<AssetCategory> assetCategories = new ArrayList<>();

		assetCategories.addAll(assetEntry.getCategories());

		if (Validator.isNotNull(type)) {
			assetCategories.add(
				_getAssetCategory(userId, companyId, groupId, type));
		}

		for (AssetCategory assetCategory : assetCategories) {
			_assetEntryLocalService.addAssetCategoryAssetEntry(
				assetCategory.getCategoryId(), entryId);
		}

		// Asset links

		List<AssetLink> assetLinks = _assetLinkLocalService.getLinks(
			assetEntry.getEntryId());

		for (AssetLink assetLink : assetLinks) {
			_importAssetLink(assetLink, assetEntry.getEntryId(), entryId);
		}

		// Asset tags

		List<AssetTag> assetTags = assetEntry.getTags();

		for (AssetTag assetTag : assetTags) {
			_assetEntryLocalService.addAssetTagAssetEntry(
				assetTag.getTagId(), entryId);
		}
	}

	private void _importCalendarBookingResourcePermission(
			ResourcePermission resourcePermission, long calendarBookingId,
			List<String> modelResourceActions)
		throws PortalException {

		CalendarBooking calendarBooking =
			_calendarBookingLocalService.getCalendarBooking(calendarBookingId);

		String[] actionIds = _getActionIds(
			resourcePermission, _CLASS_NAME, modelResourceActions);

		_resourcePermissionLocalService.setResourcePermissions(
			calendarBooking.getCompanyId(), CalendarBooking.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(calendarBookingId), resourcePermission.getRoleId(),
			actionIds);
	}

	private void _importCalendarBookingResourcePermissions(
			long companyId, long eventId, long calendarBookingId)
		throws PortalException {

		List<String> modelResourceActions =
			ResourceActionsUtil.getModelResourceActions(
				CalendarBooking.class.getName());

		List<ResourcePermission> resourcePermissions =
			_resourcePermissionLocalService.getResourcePermissions(
				companyId, _CLASS_NAME, ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(eventId));

		for (ResourcePermission resourcePermission : resourcePermissions) {
			_importCalendarBookingResourcePermission(
				resourcePermission, calendarBookingId, modelResourceActions);
		}
	}

	private CalendarBooking _importCalEvent(long calEventId) throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler(6);

			sb.append("select uuid_, eventId, groupId, companyId, userId, ");
			sb.append("userName, createDate, modifiedDate, title, ");
			sb.append("description, location, startDate, endDate, ");
			sb.append("durationHour, durationMinute, allDay, type_, ");
			sb.append("repeating, recurrence, remindBy, firstReminder, ");
			sb.append("secondReminder from CalEvent where eventId = ?");

			try (PreparedStatement ps = connection.prepareStatement(
					sb.toString())) {

				ps.setLong(1, calEventId);

				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					String uuid = rs.getString("uuid_");
					long eventId = rs.getLong("eventId");
					long groupId = rs.getLong("groupId");
					long companyId = rs.getLong("companyId");
					long userId = rs.getLong("userId");
					String userName = rs.getString("userName");
					Timestamp createDate = rs.getTimestamp("createDate");
					Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
					String title = rs.getString("title");
					String description = rs.getString("description");
					String location = rs.getString("location");
					Timestamp startDate = rs.getTimestamp("startDate");
					int durationHour = rs.getInt("durationHour");
					int durationMinute = rs.getInt("durationMinute");
					boolean allDay = rs.getBoolean("allDay");
					String type = rs.getString("type_");
					String recurrence = rs.getString("recurrence");
					int remindBy = rs.getInt("remindBy");
					int firstReminder = rs.getInt("firstReminder");
					int secondReminder = rs.getInt("secondReminder");

					return _importCalEvent(
						uuid, eventId, groupId, companyId, userId, userName,
						createDate, modifiedDate, title, description, location,
						startDate, durationHour, durationMinute, allDay, type,
						recurrence, remindBy, firstReminder, secondReminder);
				}

				throw new NoSuchBookingException();
			}
		}
	}

	private CalendarBooking _importCalEvent(
			String uuid, long eventId, long groupId, long companyId,
			long userId, String userName, Timestamp createDate,
			Timestamp modifiedDate, String title, String description,
			String location, Timestamp startDate, int durationHour,
			int durationMinute, boolean allDay, String type, String recurrence,
			int remindBy, int firstReminder, int secondReminder)
		throws Exception {

		// Calendar booking

		CalendarResource calendarResource = _getCalendarResource(
			companyId, groupId);

		CalendarBooking calendarBooking =
			_calendarBookingLocalService.fetchCalendarBookingByUuidAndGroupId(
				uuid, calendarResource.getGroupId());

		if (calendarBooking != null) {
			return calendarBooking;
		}

		long calendarBookingId = _counterLocalService.increment();

		long startTime = startDate.getTime();

		long endTime =
			startTime + durationHour * Time.HOUR + durationMinute * Time.MINUTE;

		if (allDay) {
			endTime = endTime - 1;
		}

		if (remindBy == _REMIND_BY_NONE) {
			firstReminder = 0;
			secondReminder = 0;
		}

		calendarBooking = _addCalendarBooking(
			uuid, calendarBookingId, companyId, groupId, userId, userName,
			createDate, modifiedDate, calendarResource.getDefaultCalendarId(),
			calendarResource.getCalendarResourceId(), title, description,
			location, startTime, endTime, allDay,
			_convertRecurrence(recurrence), firstReminder,
			NotificationType.EMAIL, secondReminder, NotificationType.EMAIL);

		// Resources

		_importCalendarBookingResourcePermissions(
			companyId, eventId, calendarBookingId);

		// Subscriptions

		_importSubscriptions(companyId, eventId, calendarBookingId);

		// Asset

		_importAssets(
			uuid, companyId, groupId, userId, type, eventId, calendarBookingId);

		// Expando

		_importExpando(companyId, eventId, calendarBookingId);

		// Message boards

		_importMBDiscussion(eventId, calendarBookingId);

		// Ratings

		_importRatings(
			_CLASS_NAME, eventId, CalendarBooking.class.getName(),
			calendarBookingId);

		// Social

		_importSocialActivities(eventId, calendarBookingId);

		return calendarBooking;
	}

	private void _importExpando(
			long companyId, long eventId, long calendarBookingId)
		throws PortalException {

		long oldClassNameId = _classNameLocalService.getClassNameId(
			_CLASS_NAME);

		ExpandoTable expandoTable = _expandoTableLocalService.fetchTable(
			companyId, oldClassNameId, "CUSTOM_FIELDS");

		if (expandoTable == null) {
			return;
		}

		ExpandoRow expandoRow = _expandoRowLocalService.fetchRow(
			expandoTable.getTableId(), eventId);

		expandoRow.setClassPK(calendarBookingId);

		_expandoRowLocalService.updateExpandoRow(expandoRow);

		long calendarBookingClassNameId = _classNameLocalService.getClassNameId(
			CalendarBooking.class);

		expandoTable.setClassNameId(calendarBookingClassNameId);

		_expandoTableLocalService.updateExpandoTable(expandoTable);

		List<ExpandoValue> expandoValues =
			_expandoValueLocalService.getRowValues(expandoRow.getRowId());

		for (ExpandoValue expandoValue : expandoValues) {
			expandoValue.setClassNameId(calendarBookingClassNameId);

			expandoValue.setClassPK(calendarBookingId);

			_expandoValueLocalService.updateExpandoValue(expandoValue);
		}
	}

	private void _importMBDiscussion(long eventId, long calendarBookingId)
		throws PortalException {

		MBDiscussion mbDiscussion = _mbDiscussionLocalService.fetchDiscussion(
			_CLASS_NAME, eventId);

		if (mbDiscussion == null) {
			return;
		}

		long threadId = _importMBThread(
			mbDiscussion.getThreadId(), calendarBookingId);

		_addMBDiscussion(
			PortalUUIDUtil.generate(), _counterLocalService.increment(),
			mbDiscussion.getGroupId(), mbDiscussion.getCompanyId(),
			mbDiscussion.getUserId(), mbDiscussion.getUserName(),
			mbDiscussion.getCreateDate(), mbDiscussion.getModifiedDate(),
			_classNameLocalService.getClassNameId(
				CalendarBooking.class.getName()),
			calendarBookingId, threadId);
	}

	private long _importMBMessage(
			long messageId, long threadId, long calendarBookingId,
			Map<Long, Long> mbMessageIds)
		throws PortalException {

		return _importMBMessage(
			_mbMessageLocalService.getMBMessage(messageId), threadId,
			calendarBookingId, mbMessageIds);
	}

	private long _importMBMessage(
			MBMessage mbMessage, long threadId, long calendarBookingId,
			Map<Long, Long> mbMessageIds)
		throws PortalException {

		Long messageId = mbMessageIds.get(mbMessage.getMessageId());

		if (messageId != null) {
			return messageId;
		}

		messageId = _counterLocalService.increment();

		_addMBMessage(
			PortalUUIDUtil.generate(), messageId, mbMessage.getGroupId(),
			mbMessage.getCompanyId(), mbMessage.getUserId(),
			mbMessage.getUserName(), mbMessage.getCreateDate(),
			mbMessage.getModifiedDate(),
			_classNameLocalService.getClassNameId(
				CalendarBooking.class.getName()),
			calendarBookingId, mbMessage.getCategoryId(), threadId,
			mbMessage.getRootMessageId(), mbMessage.getParentMessageId(),
			mbMessage.getSubject(), mbMessage.getBody(), mbMessage.getFormat(),
			mbMessage.isAnonymous(), mbMessage.getPriority(),
			mbMessage.isAllowPingbacks(), mbMessage.isAnswer(),
			mbMessage.getStatus(), mbMessage.getStatusByUserId(),
			mbMessage.getStatusByUserName(), mbMessage.getStatusDate(),
			mbMessageIds);

		_importRatings(
			MBDiscussion.class.getName(), mbMessage.getMessageId(),
			MBDiscussion.class.getName(), messageId);

		mbMessageIds.put(mbMessage.getMessageId(), messageId);

		return messageId;
	}

	private long _importMBThread(long threadId, long calendarBookingId)
		throws PortalException {

		MBThread mbThread = _mbThreadLocalService.fetchMBThread(threadId);

		return _importMBThread(mbThread, calendarBookingId);
	}

	private long _importMBThread(MBThread mbThread, long calendarBookingId)
		throws PortalException {

		long threadId = _counterLocalService.increment();

		_addMBThread(
			PortalUUIDUtil.generate(), threadId, mbThread.getGroupId(),
			mbThread.getCompanyId(), mbThread.getUserId(),
			mbThread.getUserName(), mbThread.getCreateDate(),
			mbThread.getModifiedDate(), mbThread.getCategoryId(), 0,
			mbThread.getRootMessageUserId(), mbThread.getTitle(),
			mbThread.getMessageCount(), mbThread.getViewCount(),
			mbThread.getLastPostByUserId(), mbThread.getLastPostDate(),
			mbThread.getPriority(), mbThread.isQuestion(), mbThread.getStatus(),
			mbThread.getStatusByUserId(), mbThread.getStatusByUserName(),
			mbThread.getStatusDate());

		Map<Long, Long> mbMessageIds = new HashMap<>();

		List<MBMessage> mbMessages = _mbMessageLocalService.getThreadMessages(
			mbThread.getThreadId(), WorkflowConstants.STATUS_ANY);

		for (MBMessage mbMessage : mbMessages) {
			_importMBMessage(
				mbMessage, threadId, calendarBookingId, mbMessageIds);
		}

		_updateMBThreadRootMessageId(
			threadId, mbMessageIds.get(mbThread.getRootMessageId()));

		return threadId;
	}

	private void _importRatings(
		String oldClassName, long oldClassPK, String className, long classPK) {

		List<RatingsEntry> ratingsEntries =
			_ratingsEntryLocalService.getEntries(oldClassName, oldClassPK);

		for (RatingsEntry ratingsEntry : ratingsEntries) {
			_addRatingsEntry(
				_counterLocalService.increment(), ratingsEntry.getCompanyId(),
				ratingsEntry.getUserId(), ratingsEntry.getUserName(),
				ratingsEntry.getCreateDate(), ratingsEntry.getModifiedDate(),
				className, classPK, ratingsEntry.getScore());
		}

		RatingsStats ratingsStats = _ratingsStatsLocalService.fetchStats(
			oldClassName, oldClassPK);

		if (ratingsStats == null) {
			return;
		}

		_addRatingsStats(
			_counterLocalService.increment(), className, classPK,
			ratingsStats.getTotalEntries(), ratingsStats.getTotalScore(),
			ratingsStats.getAverageScore());
	}

	private void _importSocialActivities(long eventId, long calendarBookingId) {
		List<SocialActivity> socialActivities =
			_socialActivityLocalService.getActivities(
				_CLASS_NAME, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (SocialActivity socialActivity : socialActivities) {
			if (socialActivity.getClassPK() == eventId) {
				_importSocialActivity(socialActivity, calendarBookingId);
			}
		}
	}

	private void _importSocialActivity(
		SocialActivity socialActivity, long calendarBookingId) {

		_addSocialActivity(
			_counterLocalService.increment(SocialActivity.class.getName()),
			socialActivity.getGroupId(), socialActivity.getCompanyId(),
			socialActivity.getUserId(), socialActivity.getCreateDate(),
			socialActivity.getMirrorActivityId(),
			_classNameLocalService.getClassNameId(CalendarBooking.class),
			calendarBookingId, socialActivity.getType(),
			socialActivity.getExtraData(), socialActivity.getReceiverUserId());
	}

	private void _importSubscription(
		Subscription subscription, long calendarBookingId) {

		_addSubscription(
			_counterLocalService.increment(), subscription.getCompanyId(),
			subscription.getUserId(), subscription.getUserName(),
			subscription.getCreateDate(), subscription.getModifiedDate(),
			_classNameLocalService.getClassNameId(CalendarBooking.class),
			calendarBookingId, subscription.getFrequency());
	}

	private void _importSubscriptions(
		long companyId, long eventId, long calendarBookingId) {

		List<Subscription> subscriptions =
			_subscriptionLocalService.getSubscriptions(
				companyId, _CLASS_NAME, eventId);

		for (Subscription subscription : subscriptions) {
			_importSubscription(subscription, calendarBookingId);
		}
	}

	private boolean _isAssetLinkImported(long entryId1, long entryId2, int type)
		throws SQLException {

		StringBundler sb = new StringBundler(3);

		sb.append("select count(*) from AssetLink where ((entryId1 = ? and ");
		sb.append("entryId2 = ?) or (entryId2 = ? and entryId1 = ?)) and ");
		sb.append("type_ = ?");

		String sql = sb.toString();

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, entryId1);
			ps.setLong(2, entryId2);
			ps.setLong(3, entryId1);
			ps.setLong(4, entryId2);
			ps.setInt(5, type);

			ResultSet rs = ps.executeQuery();

			rs.next();

			int count = rs.getInt(1);

			if (count > 0) {
				return true;
			}

			return false;
		}
	}

	private void _updateMBThreadRootMessageId(long threadId, long rootMessageId)
		throws PortalException {

		MBThread mbThread = _mbThreadLocalService.getMBThread(threadId);

		mbThread.setRootMessageId(rootMessageId);

		_mbThreadLocalService.updateMBThread(mbThread);
	}

	private static final String _ASSET_VOCABULARY_NAME = "Calendar Event Types";

	private static final String _CLASS_NAME =
		"com.liferay.portlet.calendar.model.CalEvent";

	private static final int _REMIND_BY_NONE = 0;

	private static final Log _log = LogFactoryUtil.getLog(
		CalEventImporterVerifyProcess.class);

	private static final Map<Integer, Frequency> _frequencies =
		HashMapBuilder.<Integer, Frequency>put(
			TZSRecurrence.DAILY, Frequency.DAILY
		).put(
			TZSRecurrence.MONTHLY, Frequency.MONTHLY
		).put(
			TZSRecurrence.WEEKLY, Frequency.WEEKLY
		).put(
			TZSRecurrence.YEARLY, Frequency.YEARLY
		).build();
	private static final Map<Integer, Weekday> _weekdays =
		HashMapBuilder.<Integer, Weekday>put(
			Calendar.FRIDAY, Weekday.FRIDAY
		).put(
			Calendar.MONDAY, Weekday.MONDAY
		).put(
			Calendar.SATURDAY, Weekday.SATURDAY
		).put(
			Calendar.SUNDAY, Weekday.SUNDAY
		).put(
			Calendar.THURSDAY, Weekday.THURSDAY
		).put(
			Calendar.TUESDAY, Weekday.TUESDAY
		).put(
			Calendar.WEDNESDAY, Weekday.WEDNESDAY
		).build();

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetLinkLocalService _assetLinkLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private CalendarBookingLocalService _calendarBookingLocalService;

	@Reference
	private CalendarResourceLocalService _calendarResourceLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private ExpandoRowLocalService _expandoRowLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private ExpandoValueLocalService _expandoValueLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	private JSONSerializer _jsonSerializer;

	@Reference
	private MBDiscussionLocalService _mbDiscussionLocalService;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference
	private RatingsEntryLocalService _ratingsEntryLocalService;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SocialActivityLocalService _socialActivityLocalService;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}