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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.service.SocialActivityAchievementLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivityStatsEntryLocalServiceUtil;

/**
 * @author Zsolt Berentey
 */
public class CounterBasedAchievement implements SocialAchievement {

	public String getDescriptionKey() {
		return _descriptionKey;
	}

	public String getIcon() {
		return _icon;
	}

	public String getName() {
		return _name;
	}

	public void processActivity(SocialActivity activity) {

		if (_counterOwner == SocialActivityConstants.OWNER_TYPE_ASSET) {
			return;
		}

		try {
			long classNameId = PortalUtil.getClassNameId(User.class.getName());
			long classPK = activity.getUserId();

			if (_counterOwner != SocialActivityConstants.OWNER_TYPE_ACTOR) {
				classPK = activity.getAssetEntry().getUserId();
			}

			SocialActivityStatsEntry statsEntry =
				SocialActivityStatsEntryLocalServiceUtil.fetchLastEntry(
					activity.getGroupId(), classNameId,	classPK, _counterOwner,
					_counterName);

			if (statsEntry != null) {
				if (!_unlocked && statsEntry.getOverallValue() >= _threshold) {
					SocialActivityAchievementLocalServiceUtil.unlockAchievement(
						activity.getGroupId(), classPK, this);

					_unlocked = true;
				}
			}
		} catch (Exception e) {
			_log.error(e);
		}

	}

	public void setCounterName(String counterName) {
		_counterName = counterName;
	}

	public void setCounterOwner(String owner) {
		if (owner.equals("creator")) {
			_counterOwner = SocialActivityConstants.OWNER_TYPE_CREATOR;
		}
		else if (owner.equals("asset")) {
			_counterOwner = SocialActivityConstants.OWNER_TYPE_ASSET;
		}
		else if (owner.equals("actor")) {
			_counterOwner = SocialActivityConstants.OWNER_TYPE_ACTOR;
		}
	}

	public void setDescriptionKey(String descriptionKey) {
		_descriptionKey = descriptionKey;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setThreshold(String threshold) {
		_threshold = GetterUtil.getInteger(threshold, -1);
	}

	private String _counterName;
	private int _counterOwner;
	private String _descriptionKey;
	private String _icon;
	private Log _log = LogFactoryUtil.getLog(this.getClass());
	private String _name;
	private int _threshold = -1;
	private boolean _unlocked;

}