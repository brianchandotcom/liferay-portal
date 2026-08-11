/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.web.internal.display.context;

import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.exportimport.web.internal.util.ScopeUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.TimeZoneComparator;
import com.liferay.portal.kernel.util.TimeZoneUtil;

import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

/**
 * @author Daniel Raposo
 */
public class PublishSchedulerDisplayContext {

	public PublishSchedulerDisplayContext(Group liveGroup, Locale locale) {
		_liveGroup = liveGroup;
		_locale = locale;
	}

	public String getPublishPreviewAPIURL() {
		return _getScopePath() + "/publish-preview";
	}

	public String getPublishProcessesAPIURL() {
		return _getScopePath() + "/publish-processes";
	}

	public String getScheduledPublishProcessesAPIURL() {
		return _getScopePath() + "/scheduled-publish-processes";
	}

	public JSONArray getTimeZonesJSONArray() {
		Date date = new Date();
		Set<TimeZone> timeZones = new TreeSet<>(new TimeZoneComparator());
		JSONArray timeZonesJSONArray = JSONFactoryUtil.createJSONArray();

		for (String timeZoneId : PropsUtil.getArray(PropsKeys.TIME_ZONES)) {
			timeZones.add(TimeZoneUtil.getTimeZone(timeZoneId));
		}

		for (TimeZone timeZone : timeZones) {
			timeZonesJSONArray.put(
				JSONUtil.put(
					"label", _getTimeZoneLabel(date, timeZone)
				).put(
					"value", timeZone.getID()
				));
		}

		return timeZonesJSONArray;
	}

	public String getTitle(long scheduledPublishProcessId) {
		if (scheduledPublishProcessId <= 0) {
			return LanguageUtil.get(_locale, "new-publishing-process");
		}

		try {
			for (SchedulerResponse schedulerResponse :
					SchedulerEngineHelperUtil.getScheduledJobs(
						StagingUtil.getSchedulerGroupName(
							DestinationNames.LAYOUTS_LOCAL_PUBLISHER,
							_liveGroup.getGroupId()),
						StorageType.PERSISTED)) {

				Message message = schedulerResponse.getMessage();

				if (scheduledPublishProcessId == GetterUtil.getLong(
						message.getPayload())) {

					return schedulerResponse.getDescription();
				}
			}
		}
		catch (SchedulerException schedulerException) {
			_log.error(
				"Unable to get the scheduled publish process " +
					scheduledPublishProcessId,
				schedulerException);
		}

		return LanguageUtil.get(_locale, "new-publishing-process");
	}

	public boolean isCommentsAndRatingsEnabled() {
		return ScopeUtil.isCommentsAndRatingsEnabled(_liveGroup);
	}

	public boolean isLookAndFeelEnabled() {
		return ScopeUtil.isLookAndFeelEnabled(_liveGroup);
	}

	private String _getScopePath() {
		return StringBundler.concat(
			_BASE_PATH, "/sites/", _liveGroup.getExternalReferenceCode());
	}

	private String _getTimeZoneLabel(Date date, TimeZone timeZone) {
		StringBundler sb = new StringBundler(7);

		sb.append("(UTC");

		int offset = timeZone.getOffset(date.getTime());

		if (offset != 0) {
			sb.append(
				String.format(
					" %+03d:%02d", offset / Time.HOUR,
					Math.abs(offset % Time.HOUR) / Time.MINUTE));
		}

		sb.append(") ");
		sb.append(
			timeZone.getDisplayName(
				timeZone.inDaylightTime(date), TimeZone.LONG, _locale));

		String timeZoneId = timeZone.getID();

		if (timeZoneId.contains("Phoenix")) {
			sb.append(" (");
			sb.append(timeZoneId);
			sb.append(")");
		}

		return sb.toString();
	}

	private static final String _BASE_PATH = "/o/export-import/v1.0";

	private static final Log _log = LogFactoryUtil.getLog(
		PublishSchedulerDisplayContext.class);

	private final Group _liveGroup;
	private final Locale _locale;

}