/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.item;

import com.liferay.content.dashboard.item.action.ContentDashboardItemVersionAction;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Jürgen Kappler
 */
public class ContentDashboardItemVersion {

	public ContentDashboardItemVersion(
		String changeLog,
		List<ContentDashboardItemVersionAction>
			contentDashboardItemVersionActions,
		Date createDate, String label, Locale locale, String style,
		String userName, String version) {

		_changeLog = changeLog;
		_contentDashboardItemVersionActions =
			contentDashboardItemVersionActions;
		_createDate = createDate;
		_label = label;
		_locale = locale;
		_style = style;
		_userName = userName;
		_version = version;
	}

	public String getChangeLog() {
		return _changeLog;
	}

	public List<ContentDashboardItemVersionAction>
		getContentDashboardItemVersionActions() {

		return Collections.unmodifiableList(
			_contentDashboardItemVersionActions);
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public String getLabel() {
		return _label;
	}

	public String getStyle() {
		return _style;
	}

	public String getUserName() {
		return _userName;
	}

	public String getVersion() {
		return _version;
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"actions", _getContentDashboardItemVersionActionsJSONArray()
		).put(
			"changeLog", getChangeLog()
		).put(
			"createDate", getCreateDate()
		).put(
			"statusLabel", getLabel()
		).put(
			"statusStyle", getStyle()
		).put(
			"userName", getUserName()
		).put(
			"version", getVersion()
		);
	}

	private JSONArray _getContentDashboardItemVersionActionsJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (ListUtil.isEmpty(_contentDashboardItemVersionActions)) {
			return jsonArray;
		}

		for (ContentDashboardItemVersionAction
				contentDashboardItemVersionAction :
					_contentDashboardItemVersionActions) {

			if ((contentDashboardItemVersionAction == null) ||
				Validator.isNull(contentDashboardItemVersionAction.getURL())) {

				continue;
			}

			jsonArray.put(
				JSONUtil.put(
					"icon", contentDashboardItemVersionAction.getIcon()
				).put(
					"label", contentDashboardItemVersionAction.getLabel(_locale)
				).put(
					"name", contentDashboardItemVersionAction.getName()
				).put(
					"type", contentDashboardItemVersionAction.getType()
				).put(
					"url", contentDashboardItemVersionAction.getURL()
				));
		}

		return jsonArray;
	}

	private final String _changeLog;
	private final List<ContentDashboardItemVersionAction>
		_contentDashboardItemVersionActions;
	private final Date _createDate;
	private final String _label;
	private final Locale _locale;
	private final String _style;
	private final String _userName;
	private final String _version;

}