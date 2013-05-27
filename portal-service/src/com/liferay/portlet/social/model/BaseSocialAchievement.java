/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivityAchievementLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivityCounterLocalServiceUtil;

/**
 * @author Zsolt Berentey
 */
public class BaseSocialAchievement implements SocialAchievement {

	public boolean equals(SocialAchievement socialAchievement) {
		if (Validator.equals(_name, socialAchievement.getName())) {
			return true;
		}

		return false;
	}

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

	@Override
	public String getDescriptionKey() {
		return _ACHIEVEMENT_DESCRIPTION_PREFIX.concat(_name);
	}

	@Override
	public String getIcon() {
		if (_icon == null) {
			return _name.concat(_ICON_SUFFIX);
		}

		return _icon;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getNameKey() {
		return _ACHIEVEMENT_NAME_PREFIX.concat(_name);
	}

	@Override
	public void initialize(SocialActivityDefinition activityDefinition) {
		SocialActivityCounterDefinition activityCounterDefinition =
			activityDefinition.getActivityCounterDefinition(_counterName);

		if (activityCounterDefinition != null) {
			return;
		}

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

	@Override
	public void processActivity(SocialActivity activity) {
		try {
			doProcessActivity(activity);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to process activity", e);
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

	@Override
	public void setIcon(String icon) {
		_icon = icon;
	}

	@Override
	public void setName(String name) {
		name = StringUtil.replace(name, StringPool.SPACE, StringPool.UNDERLINE);
		name = name.toLowerCase();

		_name = StringUtil.extract(name, _NAME_SUPPORTED_CHARS);
	}

	@Override
	public void setProperty(String name, String value) {
		if (name.equals("counterIncrement") ||
			name.equals("counterPeriodLength") ||
			name.equals("counterThreshold")) {

			BeanPropertiesUtil.setProperty(
				this, name, GetterUtil.getInteger(value, 0));
		}
		else {
			BeanPropertiesUtil.setProperty(this, name, value);
		}
	}

	protected void doProcessActivity(SocialActivity activity)
		throws PortalException, SystemException {

		if (_counterThreshold == 0) {
			return;
		}

		SocialActivityAchievement achievement =
			SocialActivityAchievementLocalServiceUtil.fetchUserAchievement(
				activity.getUserId(), activity.getGroupId(), _name);

		if (achievement != null) {
			return;
		}

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

	private static final String _ACHIEVEMENT_DESCRIPTION_PREFIX =
		"social.achievement.description.";

	private static final String _ACHIEVEMENT_NAME_PREFIX =
		"social.achievement.name.";

	private static final String _ICON_SUFFIX = "-icon.jpg";

	private static final char[] _NAME_SUPPORTED_CHARS =
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