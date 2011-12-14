/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.model;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivityAchievementLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivityCounterLocalServiceUtil;
public class BaseSocialAchievement implements SocialAchievement {

	public int getCounterIncrement() {
		return _counterIncrement;
	}

	public String getCounterName() {
		return _counterName;
	}

	public String getCounterOwner() {
		return _counterOwner;
	}

	public int getCounterPeriodLength() {
		return _counterPeriodLength;
	}

	public int getCounterThreshold() {
		return _counterThreshold;
	}

	public String getDescriptionKey() {
		return _DEFAULT_ACHIEVEMENT_PREFIX + _DESCRIPTION + _name;
	}

	public String getIcon() {
		if (_icon == null) {
			return _name + _ICON + ".jpg";
		}

		return _icon;
	}

	public String getName() {
		return _name;
	}

	public String getNameKey() {
		return _DEFAULT_ACHIEVEMENT_PREFIX + _NAME + _name;
	}

	public void initialize(SocialActivityDefinition activityDefinition) {
		SocialActivityCounterDefinition activityCounterDefinition =
			activityDefinition.getActivityCounterDefinition(_counterName);

		if (activityCounterDefinition == null) {
			activityCounterDefinition = new SocialActivityCounterDefinition();

			activityCounterDefinition.setEnabled(true);
			activityCounterDefinition.setIncrement(_counterIncrement);
			activityCounterDefinition.setName(_counterName);
			activityCounterDefinition.setOwnerType(_counterOwner);

			if (_counterPeriodLength > 0) {
				activityCounterDefinition.setPeriodLength(_counterPeriodLength);
				activityCounterDefinition.setTransient(true);
			}

			activityDefinition.addCounter(activityCounterDefinition);
		}
	}

	public void processActivity(SocialActivity activity) {
		if (_counterThreshold == 0) {
			return;
		}

		try {
			int count =
				SocialActivityAchievementLocalServiceUtil.
					getUserAchievementCount(
						activity.getUserId(), activity.getGroupId(), _name);

			if (count > 0) {
				return;
			}

			doProcessActivity(activity);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to process activity.", e);
			}
		}
	}

	public void setCounterIncrement(int counterIncrement) {
		_counterIncrement = counterIncrement;
	}

	public void setCounterName(String counterName) {
		_counterName = counterName;
	}

	public void setCounterOwner(String counterOwner) {
		_counterOwner = counterOwner;

		if (counterOwner.equalsIgnoreCase("actor")) {
			_ownerType = SocialActivityCounterConstants.TYPE_ACTOR;
		}
		else if (counterOwner.equalsIgnoreCase("asset")) {
			_ownerType = SocialActivityCounterConstants.TYPE_ASSET;
		}
		else if (counterOwner.equalsIgnoreCase("creator")) {
			_ownerType = SocialActivityCounterConstants.TYPE_CREATOR;
		}
	}

	public void setCounterPeriodLength(int counterPeriodLength) {
		_counterPeriodLength = counterPeriodLength;
	}

	public void setCounterThreshold(int counterThreshold) {
		_counterThreshold = counterThreshold;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setName(String name) {
		name = StringUtil.replace(name, " ", StringPool.UNDERLINE);
		name = name.toLowerCase();

		_name = StringUtil.extract(name, _SUPPORTED_CHARS_IN_NAME);
	}

	public void setProperty(String name, String value) {
		if (name.equals("counterIncrement") ||
			name.equals("counterThreshold") ||
			name.equals("counterPeriodLength")) {

			BeanPropertiesUtil.setProperty(
				this, name, GetterUtil.getInteger(value, 0));
		}
		else {
			BeanPropertiesUtil.setProperty(this, name, value);
		}
	}

	protected void doProcessActivity(SocialActivity activity)
		throws PortalException, SystemException {

		long classNameId = activity.getClassNameId();
		long classPK = activity.getClassPK();

		if (_ownerType != SocialActivityCounterConstants.TYPE_ASSET) {
			classNameId = PortalUtil.getClassNameId(User.class);
			classPK = activity.getUserId();
		}

		if (_ownerType == SocialActivityCounterConstants.TYPE_ASSET) {
			AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
				activity.getClassName(), activity.getClassPK());

			if (assetEntry != null) {
				classPK = assetEntry.getUserId();
			}
		}

		SocialActivityCounter activityCounter =
			SocialActivityCounterLocalServiceUtil.fetchLatestActivityCounter(
				activity.getGroupId(), classNameId, classPK, _counterName,
				_ownerType);

		if ((activityCounter != null) &&
			(activityCounter.getCurrentValue() >= _counterThreshold)) {

			SocialActivityAchievementLocalServiceUtil.addActivityAchievement(
				activity.getUserId(), activity.getGroupId(), this);
		}
	}

	private static final String _DEFAULT_ACHIEVEMENT_PREFIX =
		"social.achievement.";
	private static final String _DESCRIPTION = ".description.";
	private static final String _ICON = "-icon";
	private static final String _NAME = ".name.";
	private static final char[] _SUPPORTED_CHARS_IN_NAME =
		"abcdefghijklmnopqrstuvwxyz123456789_-.".toCharArray();

	private static Log _log = LogFactoryUtil.getLog(
		BaseSocialAchievement.class);

	private int _counterIncrement = 1;
	private String _counterName;
	private String _counterOwner;
	private int _counterPeriodLength =
		SocialActivityCounterConstants.PERIOD_LENGTH_SYSTEM;
	private int _counterThreshold;
	private String _icon;
	private String _name;
	private int _ownerType;

}