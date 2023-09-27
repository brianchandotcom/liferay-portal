/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.model;

/**
 * @author Alessio Antonio Rendina
 */
public class TimelineModel {

	public TimelineModel(
		long id, String date, String description, String title) {

		_id = id;
		_date = date;
		_description = description;
		_title = title;
	}

	public String getDate() {
		return _date;
	}

	public String getDescription() {
		return _description;
	}

	public long getId() {
		return _id;
	}

	public String getTitle() {
		return _title;
	}

	private final String _date;
	private final String _description;
	private final long _id;
	private final String _title;

}