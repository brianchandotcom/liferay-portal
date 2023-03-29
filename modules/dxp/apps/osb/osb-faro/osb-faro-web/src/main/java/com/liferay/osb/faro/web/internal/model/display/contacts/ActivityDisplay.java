/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.constants.ActivityConstants;
import com.liferay.osb.faro.engine.client.model.Activity;
import com.liferay.osb.faro.web.internal.model.display.main.EntityDisplay;

import java.util.Date;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class ActivityDisplay extends EntityDisplay {

	public ActivityDisplay() {
	}

	public ActivityDisplay(Activity activity) {
		setId(activity.getId());

		_action = ActivityConstants.getAction(activity.getEventId());
		_activityKey = activity.getActivityKey();
		_assetType = activity.getApplicationId();
		_day = activity.getDay();
		_eventId = activity.getEventId();
		_groupName = activity.getGroupName();
		_startTime = activity.getStartTime();

		Activity.ActionObject actionObject = activity.getObject();

		_canonicalUrl = actionObject.getCanonicalUrl();
		_dataSourceAssetPK = actionObject.getDataSourceAssetPK();
		_name = actionObject.getName();
		_url = actionObject.getUrl();
	}

	private int _action;
	private String _activityKey;
	private String _assetType;
	private String _canonicalUrl;
	private String _dataSourceAssetPK;
	private Date _day;
	private String _eventId;
	private String _groupName;
	private String _name;
	private Date _startTime;
	private String _url;

}